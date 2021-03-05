package com.example.keepintouch.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keepintouch.Adapter.MemberAdapter;
import com.example.keepintouch.Model.User;
import com.example.keepintouch.R;
import com.example.keepintouch.ViewModel.RedZoneMemberListViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RedZoneMemberActivity extends AppCompatActivity {
    private String TAG = "red_mem_act_tager";
    private ArrayList<User> mMemberList;
    private RecyclerView mRecyclerView;
    private MemberAdapter mMemberAdapter;
    public String currentgroupid = null;
    private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_zone_member);
        progressDialog = new ProgressDialog(this);
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
        Log.d(TAG, currentgroupid + " got curent gorup id  in red zone actviity");
        mMemberList = new ArrayList<>();

        setTitle("RedZone Members");


        mRecyclerView = findViewById(R.id.redzonememberrecyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mMemberAdapter = new MemberAdapter(mMemberList);
        mRecyclerView.setAdapter(mMemberAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getParent()));
        RedZoneMemberListViewModel redZoneMemberListViewModel = new ViewModelProvider(this).get(RedZoneMemberListViewModel.class);
        redZoneMemberListViewModel.getMemberList(currentgroupid);

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
                User item=mMemberList.get(position);
                Intent intent=new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+item.getPhone()));
                getApplicationContext().startActivity(intent);
            }
        });


        progressDialog.dismiss();
    }


}