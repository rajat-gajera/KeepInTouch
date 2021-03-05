package com.example.keepintouch;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.keepintouch.Locations.LocationService;
import com.example.keepintouch.ui.EmergencyActivity;
import com.example.keepintouch.ui.FaqsFragment;
import com.example.keepintouch.ui.GroupsActivity;
import com.example.keepintouch.ui.LoginActivity;
import com.example.keepintouch.ui.SurvivalKitActivity;
import com.example.keepintouch.ui.SettingsActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String ALERT_SERVICE_CHANNEL_ID = "AlertServiceChannel";
    public static final String ALERT_SERVICE_CHANNEL_NAME = "KeepInTouch";
    public static final String ALERT_SERVICE_CHANNEL_DESC = "KeepInTouch Notification";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final String TAG = "main_act_tager";
    private Boolean mLocationPermissionsGranted = false;
    private TextView mUserName;
    private TextView mUserEId;
    FirebaseAuth mFirebaseAuth;
    DrawerLayout drawer;
    private ApiInterface apiService;/////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAuth = FirebaseAuth.getInstance();
        Intent locationintent = new Intent(this, LocationService.class);
        startService(locationintent);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        apiService =Client.getClient("https://fcm.googleapis.com/").create(ApiInterface.class);///////////


        drawer = findViewById(R.id.draw_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        mUserName = navigationView.getHeaderView(0).findViewById(R.id.login_user_name);
        mUserEId = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_Eid);
        mUserName.setText(mFirebaseAuth.getCurrentUser().getDisplayName());
        mUserEId.setText(mFirebaseAuth.getCurrentUser().getEmail());
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GroupsActivity()).commit();
            navigationView.setCheckedItem(R.id.groups);

        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocationPermission();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(ALERT_SERVICE_CHANNEL_ID, ALERT_SERVICE_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(ALERT_SERVICE_CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.groups:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GroupsActivity()).commit();
                break;
            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsActivity()).commit();
                break;
            case R.id.emergency:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EmergencyActivity()).commit();
                break;
            case R.id.servival_kit:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SurvivalKitActivity()).commit();
                break;
            case R.id.share:
                    sendNotification("fEU_6J2UT1-Waqo-QeUnjg:APA91bHTS6N4FccJrcdu0h_SuQmURnm6PJx9Hm7QwMlf2Er2PA09eQiTwzLqQvGd0awLwG6UvQOXGX5eY-XjifwWOZKuY4GD9JnZ_qW2bUqZYO0_E2f2EH6XwKrnQ-cV4nPuJZ80Sewo","raja","gajera");
                     Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.faqs:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FaqsFragment()).commit();
                break;

            case R.id.log_out:
                mFirebaseAuth.signOut();
                Intent logoutintent = new Intent(this, LocationService.class);
                logoutintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                stopService(logoutintent);
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;



        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

    private void getLocationPermission() {
        //Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public void sendNotification(String userToken, String title,String message)
    {
        Data data = new Data(title,message);
        NotificationSender sender = new NotificationSender(data,userToken);
        apiService.sendNotification(sender).enqueue(new Callback<MyNotification>() {
            @Override
            public void onResponse(Call<MyNotification> call, Response<MyNotification> response) {
                if(response.code()==200)
                if(response.body().success!=1)
                {
                    Toast.makeText(MainActivity.this,"Doesn't send",Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG,response.code()+"/////////////////////////");
                Toast.makeText(MainActivity.this,"ssssssssend",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<MyNotification> call, Throwable t) {
                Toast.makeText(MainActivity.this,"failed Doesn't send",Toast.LENGTH_SHORT).show();

            }
        });

    }

}

