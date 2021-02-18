package com.example.keepintouch.ui;

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
                Intent intent  = new Intent(this, GroupChatActivity.class);
                intent.putExtra("currentGroupId",currentgroupid);
                startActivity(intent);
                return true;
            case R.id.alarm:
                return true;
            case R.id.admin_detail:
                return true;
            case R.id.red_zone_member:
                return true;


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
        setTitle("Members");




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
                ////////////////////////////////////
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