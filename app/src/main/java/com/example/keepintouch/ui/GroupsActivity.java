package com.example.keepintouch.ui;

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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keepintouch.Adapter.GroupAdapter;
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
    private ProgressDialog progressDialog;
    private final FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
    private FloatingActionButton mJoinButton, mCreateGroupButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Groups");

        View rootview = inflater.inflate(R.layout.fragment_groups, container, false);

        Log.d(TAG, "onCreateView: ");
        mRecyclerView = rootview.findViewById(R.id.recyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new GroupAdapter(mGroupList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mJoinButton = rootview.findViewById(R.id.join_button);
        mCreateGroupButton = rootview.findViewById(R.id.create_button);

        GroupListViewModel groupListViewModel = ViewModelProviders.of(getActivity()).get(GroupListViewModel.class);
        groupListViewModel.getGroupData();
        groupListViewModel.getMutableLiveData().observe(getActivity(), new Observer<List<GroupItem>>() {
            @Override
            public void onChanged(List<GroupItem> groupItems) {
                Log.d(TAG, "onChanged: "+groupItems.size());
                mGroupList = (ArrayList<GroupItem>) groupItems;
                mAdapter.setList(groupItems);
                mAdapter.notifyDataSetChanged();
            }
        });


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

                CreateGroupDialogBox createGroupDialogBox = new CreateGroupDialogBox();
                createGroupDialogBox.show(getFragmentManager(), "Create Group");
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


    private void createGroupList() {


        progressDialog.show();
        mGroupList = new ArrayList<>();

        final ArrayList<String>[] usercodes = new ArrayList[]{new ArrayList<>()};
        mFirebaseFirestore.collection("Group'sCode").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<DocumentSnapshot> dslist = value.getDocuments();
                usercodes[0].clear();
                for (DocumentSnapshot d : dslist) {
                    if (d.getId().equals(mFirebaseAuth.getCurrentUser().getUid())) {
                        Object o = d.get("CodeList");
                        usercodes[0] = (ArrayList<String>) o;

                    }
                }
                mFirebaseFirestore.collection("Groups").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot value) {
                        mGroupList.clear();
                        List<DocumentSnapshot> dsList = value.getDocuments();
                        for (DocumentSnapshot d : dsList) {
                            GroupItem group = d.toObject(GroupItem.class);
                            String code = group.getCode();
                            System.out.println(code);
                            for(String c: usercodes[0]) {
                                if (c.equals(code)) {
                                    System.out.println(code);
                                    mGroupList.add(group);
                                }
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }


}
