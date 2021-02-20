package com.example.keepintouch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.keepintouch.Adapter.MemberAdapter;
import com.example.keepintouch.Model.GroupItem;
import com.example.keepintouch.Model.User;
import com.example.keepintouch.ViewModel.MemberListViewModel;
import com.example.keepintouch.ViewModel.RedZoneMemberListViewModel;
import com.example.keepintouch.ui.GroupChatActivity;
import com.example.keepintouch.ui.GroupsActivity;
import com.example.keepintouch.ui.MapsActivity;
import com.example.keepintouch.ui.MembersActivity;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RedZoneMemberActivity extends AppCompatActivity {
    private String TAG="red_mem_act_tager";
    private ArrayList<User> mMemberList;
    private RecyclerView mRecyclerView;
    private MemberAdapter mMemberAdapter;
    public String currentgroupid=null;
    private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();

    ProgressDialog progressDialog ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if (extra == null) {
                currentgroupid = null;
                System.out.println("didn't get current group id");
            } else {
                currentgroupid = extra.getString("currentGroupId");

            }
        } else {
            currentgroupid = (String) savedInstanceState.getSerializable("currentGroupId");

        }
         mMemberList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_zone_member);
        setTitle("RedZone Members");




        mRecyclerView = findViewById(R.id.redzonememberrecyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mMemberAdapter = new MemberAdapter(mMemberList);
        mRecyclerView.setAdapter(mMemberAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getParent()));
        RedZoneMemberListViewModel redZoneMemberListViewModel= new ViewModelProvider(this).get(RedZoneMemberListViewModel.class);
        redZoneMemberListViewModel.redMutableMemberList.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                mMemberList = (ArrayList<User>) users;
                mMemberAdapter.setList(users);
                mMemberAdapter.notifyDataSetChanged();
                //  Log.d(TAG,"OnChanged!"+ users.size());
            }
        });
        redZoneMemberListViewModel.getMemberList(currentgroupid);


        mMemberAdapter.setOnItemClickListener(new MemberAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ////////////////////////////////////
            }
        });



        progressDialog.dismiss();
    }




}