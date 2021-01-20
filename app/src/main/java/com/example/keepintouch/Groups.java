package com.example.keepintouch;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.security.acl.Group;
import java.util.ArrayList;

public class Groups extends Fragment implements DialogBox.DialogBoxListener {
    private static final String GROUPNAME ="",ADMINNAME="" ;
    private ArrayList<GroupItem> mGroupList;
    private  TextView mGroupName,mAdminName;
    private RecyclerView  mRecyclerView;
    private GroupAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //private FloatingActionButton fab;
    private FloatingActionButton mJoinButton,mCreateGroupButton;
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

         mJoinButton = rootview.findViewById(R.id.join_button);
        mCreateGroupButton = rootview.findViewById(R.id.create_button);
        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mCreateGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDialog();
            }
        });




        mAdapter.setOnItemClickListener(new GroupAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });


        return  rootview;
    }

    private void OpenDialog() {
        DialogBox dialogBox = new DialogBox();
        dialogBox.show(getFragmentManager(),"Create Group");
    }

    private void createGroupList() {
        mGroupList = new ArrayList<>();
        mGroupList.add(new GroupItem("Gajera","Rajat"));
        mGroupList.add(new GroupItem("Gajera","Rajat"));
        mGroupList.add(new GroupItem("Gajera","Rajat"));
    }


    @SuppressLint("ResourceType")
    @Override
    public void createGroup(AppCompatDialogFragment dialogFragment) {

       String groupname,adminname;
       groupname=dialogFragment.getString(R.id.group_name);
       adminname = dialogFragment.getString(R.id.admin_name);

       mGroupList.add(new GroupItem(groupname,adminname));

    }
}
