package com.example.keepintouch;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.firestore.v1.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.*;

public class GroupsActivity extends Fragment {

    private static final String TAG = "";
    private ArrayList<GroupItem> mGroupList;
    private RecyclerView mRecyclerView;
    public GroupAdapter mAdapter;
    private ProgressDialog progressDialog;
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
    //private FloatingActionButton fab;
    private FloatingActionButton mJoinButton, mCreateGroupButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Groups");
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        createGroupList();


        View rootview = inflater.inflate(R.layout.fragment_groups, container, false);
        mRecyclerView = rootview.findViewById(R.id.recyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new GroupAdapter(mGroupList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mJoinButton = rootview.findViewById(R.id.join_button);
        mCreateGroupButton = rootview.findViewById(R.id.create_button);


        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        progressDialog.dismiss();

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
