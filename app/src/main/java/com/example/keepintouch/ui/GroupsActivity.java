package com.example.keepintouch.ui;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keepintouch.Adapter.GroupAdapter;
import com.example.keepintouch.MainActivity;
import com.example.keepintouch.Model.GroupItem;
import com.example.keepintouch.R;
import com.example.keepintouch.ViewModel.GroupListViewModel;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GroupsActivity extends Fragment {

    private String TAG="grp_frg_tager";
    private ArrayList<GroupItem> mGroupList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    public GroupAdapter mAdapter;
    private String currentgroupid=null;
    private FloatingActionButton mJoinButton, mCreateGroupButton;
    private static  GroupsActivity INSTANCE;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Groups");

        View rootview = inflater.inflate(R.layout.fragment_groups, container, false);

        //Log.d(TAG, "onCreateView: ");
        mRecyclerView = rootview.findViewById(R.id.recyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new GroupAdapter(mGroupList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mJoinButton = rootview.findViewById(R.id.join_button);
        mCreateGroupButton = rootview.findViewById(R.id.create_button);
        INSTANCE = this;
        GroupListViewModel groupListViewModel = new ViewModelProvider(this).get(GroupListViewModel.class);
        groupListViewModel.groupItemList.observe(getActivity(), new Observer<List<GroupItem>>() {
            @Override
            public void onChanged(List<GroupItem> groupItems) {
            //    Log.d(TAG, "onChanged: " + groupItems.size());
                mGroupList = (ArrayList<GroupItem>) groupItems;
                mAdapter.setList(groupItems);
                mAdapter.notifyDataSetChanged();
            }
        });
        groupListViewModel.getGroupData();


        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                groupListViewModel.getGroupData();
                JoinGroupDialogBox joinGroupDialogBox = new JoinGroupDialogBox();
                joinGroupDialogBox.show(getFragmentManager(), "Join Group");
            }
        });

        mCreateGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Log.d(TAG,"Creating Group");
                CreateGroupDialogBox createGroupDialogBox = new CreateGroupDialogBox();
                createGroupDialogBox.show(getFragmentManager(), "Create Group");
                groupListViewModel.getGroupData();
              // Log.d(TAG,"Created Group");

                mAdapter.notifyDataSetChanged();

            }
        });


        mAdapter.setOnItemClickListener(new GroupAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), MembersActivity.class);
                GroupItem gi = mGroupList.get(position);
                String groupid = gi.getGroupId();

                intent.putExtra("groupId", groupid);
                startActivity(intent);

            }
        });


        return rootview;
    }
    public static GroupsActivity getGroupsActivityInstance()
    {
        return INSTANCE;
    }
    public String getCurrentgroupid()
    {
        return this.currentgroupid;
    }
    public void setCurrentgroupid(String cid){currentgroupid=cid;}


}
