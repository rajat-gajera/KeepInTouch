package com.example.keepintouch.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.keepintouch.Adapter.MemberAdapter;
import com.example.keepintouch.MainActivity;
import com.example.keepintouch.Model.GroupItem;
import com.example.keepintouch.Model.User;
import com.example.keepintouch.R;
import com.example.keepintouch.ViewModel.MemberListViewModel;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MembersActivity extends AppCompatActivity {
    private String TAG="mem_act_tager";
    private ArrayList<User> mMemberList;
    private RecyclerView mRecyclerView;
    private MemberAdapter mMemberAdapter;
    public String currentgroupid=null;
    private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private Boolean flag=true;
    ProgressDialog progressDialog ;
     FloatingActionButton mapbutton;
    static MembersActivity INSTANCE;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case  R.id.group_chat:
                Log.d(TAG,currentgroupid+ "this is current group id:");
                Intent intent  = new Intent(this, GroupChatActivity.class);
                intent.putExtra("currentGroupId",currentgroupid);
                startActivity(intent);
                return true;
            case R.id.gallery:
                Intent gallery = new Intent(this, GalleryActivity.class);
                gallery.putExtra("currentGroupId",currentgroupid);
                startActivity(gallery);
                return true;
            case R.id.admin_detail:
                Intent adminIntent = new Intent(this, AdminDetailsActivity.class);
                adminIntent.putExtra("currentGroupId",currentgroupid);
                startActivity(adminIntent);
                return true;
            case R.id.red_zone_member:
                Log.d(TAG,currentgroupid+ "this is current group id:");

                Intent Rintent  = new Intent(this, RedZoneMemberActivity.class);
                Rintent.putExtra("currentGroupId",currentgroupid);
                startActivity(Rintent);
                return true;
            case R.id.delete_group:
                Intent grp = new Intent(this, MainActivity.class);

                        FirebaseFirestore.getInstance().collection("Groups").document(currentgroupid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    GroupItem groupItem = task.getResult().toObject(GroupItem.class);
                                    String adminid = groupItem.getAdminId();
                                    String cuserid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    if (adminid.equals(cuserid)) {
                                        Log.d(TAG,"Id same eeee-----");
                                        AlertDialog.Builder dialog = new AlertDialog.Builder(getBaseContext());
                                        dialog.setMessage("Are you sure you want to delete ?");
                                        dialog.setCancelable(true);

                                        dialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                FirebaseFirestore.getInstance().collection("Groups").document(currentgroupid).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful())
                                                            Toast.makeText(getApplicationContext(),"Group Deleted Successfully!!",Toast.LENGTH_SHORT).show();
                                                        else
                                                            Toast.makeText(getApplicationContext(),"Failed to Delete!!",Toast.LENGTH_SHORT).show();

                                                    }
                                                });
                                                FirebaseFirestore.getInstance().collection("Zone").document(currentgroupid).delete();
                                            }
                                        });

                                    }
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"You Can Not Delete Group!!",Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                        return false;



        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

         mapbutton = findViewById(R.id.mapbutton);

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        INSTANCE = this;


          
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if (extra == null) {
                currentgroupid = null;
                System.out.println("didn't get current group id");
            } else {
                currentgroupid = extra.getString("groupId");

            }
        } else {
            currentgroupid = (String) savedInstanceState.getSerializable("groupId");

        }
      GroupsActivity.getGroupsActivityInstance().setCurrentgroupid(currentgroupid);
        mMemberList = new ArrayList<>();
        //creatememberlist();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        setTitle("Group Members");




         mRecyclerView = findViewById(R.id.memberrecyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mMemberAdapter = new MemberAdapter(mMemberList);
        mRecyclerView.setAdapter(mMemberAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getParent()));
        MemberListViewModel memberListViewModel = new ViewModelProvider(this).get(MemberListViewModel.class);
        memberListViewModel.mutableMemberList.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                mMemberList = (ArrayList<User>) users;
                mMemberAdapter.setList(users);
                mMemberAdapter.notifyDataSetChanged();
              //  Log.d(TAG,"OnChanged!"+ users.size());
            }
        });
        memberListViewModel.getMemberList(currentgroupid);


        mMemberAdapter.setOnItemClickListener(new MemberAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                User item=mMemberList.get(position);
                Intent intent=new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+item.getPhone()));
                getMemberActivityInstance().startActivity(intent);
//                getApplicationContext().startActivity(intent);
            }
        });


         setFlag();
         setFlag();


         progressDialog.dismiss();
    }
    public static MembersActivity getMemberActivityInstance()
    {
        return INSTANCE;
    }
    public String getCurrentgroupid()
    {
        return this.currentgroupid;
    }




    public void openMap(View view) {

         Intent intent = new Intent(MembersActivity.this, MapsActivity.class);
        intent.putExtra("flag",flag);
        intent.putExtra("currentGroupId",currentgroupid);
        startActivity( intent);
    }
    public void setFlag()
    {
        mFirebaseFirestore.collection("Groups").document(currentgroupid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    GroupItem gi = task.getResult().toObject(GroupItem.class);
                    Log.d(TAG,(gi.getAdminId())+"   " +mFirebaseAuth.getCurrentUser().getUid());
                    if((gi.getAdminId()).equals(mFirebaseAuth.getCurrentUser().getUid()))
                        flag=true;
                    else flag=false;
                    Log.d(TAG,flag+"");
                }
            }
        });
    }


}