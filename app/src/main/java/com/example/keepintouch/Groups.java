package com.example.keepintouch;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.security.acl.Group;
import java.util.ArrayList;

public class Groups extends Fragment {
    private ArrayList<GroupItem> mGroupList;
    private  TextView mGroupName,mAdminName;
    private RecyclerView  mRecyclerView;
    private GroupAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fab;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       getActivity().setTitle("Groups");

        createGroupList();
        View rootview = inflater.inflate(R.layout.fragment_groups,container,false);
        mRecyclerView = rootview.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new GroupAdapter(mGroupList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        fab = rootview.findViewById(R.id.floating_btn);




        mAdapter.setOnItemClickListener(new GroupAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }


        });
        return  rootview;
    }

    private void createGroupList() {
        mGroupList = new ArrayList<>();
        mGroupList.add(new GroupItem("Gajera","Rajat"));
        mGroupList.add(new GroupItem("Gajera","Rajat"));
        mGroupList.add(new GroupItem("Gajera","Rajat"));
    }



}
