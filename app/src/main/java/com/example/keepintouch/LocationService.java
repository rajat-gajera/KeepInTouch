package com.example.keepintouch;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.example.keepintouch.Model.MyLocation;
import com.example.keepintouch.ui.GroupsActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.keepintouch.App.CHANNEL_ID;

public class LocationService extends Service {
    private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private static final String TAG = "lct_src_tager";
    private FusedLocationProviderClient mLocationClient;
    private LocationCallback mLocationCallback;


    @Override
    public void onCreate() {
        super.onCreate();
        mLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
         mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                 //   Log.d(TAG, "onLocationResult:locationError");
                    return;
                }

                List<Location> locationList = locationResult.getLocations();
              //  Log.d(TAG, "LocationResultget" + locationList.toString());
                // Toast.makeText(getApplicationContext(),""+locationList.toString(),Toast.LENGTH_SHORT).show();
                for (Location l : locationList) {
                    String lati = Double.toString(l.getLatitude());
                    String longi = Double.toString(l.getLongitude());
                    Date date = new Date(l.getTime());
                     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                    String time = dateFormat.format(date);
                    Log.d(TAG,l.toString());
                    mFirebaseFirestore.collection("Users").document(mFirebaseAuth.getCurrentUser().getUid()).update("latitude", lati,"logitude", longi,"time", time);
                    String cid = GroupsActivity.getGroupsActivityInstance().getCurrentgroupid();
                    if (cid != null) {
                        MyLocation myLocation = new MyLocation(mFirebaseAuth.getCurrentUser().getUid(),l);
                        mFirebaseFirestore.collection("Zone").document(cid).collection("memberList").document(mFirebaseAuth.getCurrentUser().getUid()).set(myLocation);
                    }
                }

            }
        };
       // Log.d(TAG, "OnCreate:location");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,
                notificationIntent,
                0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Keep IN Touch")
                .setContentText("Location Service")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        getLocatoinUpdates();
        return START_STICKY;

    }

    private void getLocatoinUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(4000);
        locationRequest.setMaxWaitTime(15 * 1000);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Location Pemission Rqquired!!", Toast.LENGTH_SHORT).show();
            return;

        }
        mLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
      //  Log.d(TAG, "onDestroy");
        mLocationClient.removeLocationUpdates(mLocationCallback);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
