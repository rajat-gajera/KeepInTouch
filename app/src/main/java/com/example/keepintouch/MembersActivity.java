package com.example.keepintouch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.List;

public class MembersActivity extends AppCompatActivity {

    private ArrayList<User> mMemberList;
    private RecyclerView mRecyclerView;
    private MemberAdapter mMemberAdapter;
    private String currentgroupid;
    private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    ProgressDialog progressDialog ;
     FloatingActionButton mapbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mapbutton = findViewById(R.id.mapbutton);
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
                currentgroupid = extra.getString("groupId");
            }
        } else {
            currentgroupid = (String) savedInstanceState.getSerializable("groupId");

        }
        mMemberList = new ArrayList<>();
        creatememberlist();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        setTitle("Members");


        mRecyclerView = findViewById(R.id.memberrecyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mMemberAdapter = new MemberAdapter(mMemberList);
        mRecyclerView.setAdapter(mMemberAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getParent()));



        mMemberAdapter.setOnItemClickListener(new MemberAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ////////////////////////////////////
            }
        });




        progressDialog.dismiss();
    }

    private void creatememberlist() {

        final ArrayList<String>[] memberids = new ArrayList[]{new ArrayList<>()};
        mFirebaseFirestore.collection("Zone").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
            List<DocumentSnapshot> dslist = queryDocumentSnapshots.getDocuments();
                memberids[0].clear();
                for (DocumentSnapshot d : dslist) {
                    if (d.getId().equals(currentgroupid) ){
                        Object o = d.get("memberList");
                        memberids[0] = (ArrayList<String>) o;
                        //System.out.println(memberids[0]+"+++++++++++++++++");
                    }
                }

                mFirebaseFirestore.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : dsList) {
                            User memberuser = d.toObject(User.class);
                            String id = memberuser.getUserId();
                            if(memberids[0].contains(id))
                            {
                                //System.out.println(memberuser.getName()+"----------");
                                mMemberList.add(memberuser);
                            }
                        }
                        System.out.println(mMemberList.toString()+"+++++++++++++++++++");
                        mMemberAdapter.notifyDataSetChanged();

                    }

                });
//                mMemberAdapter.notifyDataSetChanged();
//                System.out.println(mMemberList.toString()+"+++++++++++++++++++");


            }

        });


    }


    private void createrMemberList() {
        ///fire base mathi user member ly ne list  banavani che aya

        // mMemberAdapter.notifyDataSetChanged();
        mFirebaseFirestore.collection("Zone").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<DocumentSnapshot> zonelist = value.getDocuments();
                Zone currentZone = zonelist.get(0).toObject(Zone.class);
                for (DocumentSnapshot z : zonelist) {
                    Zone tempzone = z.toObject(Zone.class);
                    if ((currentgroupid.equals(tempzone.getGroupId()))) {
                        currentZone = tempzone;
                        break;
                    }
                }
                ArrayList<String> zonememberlist = currentZone.getMemberList();
                mFirebaseFirestore.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> members = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : members) {
//                            System.out.println(d.toObject(User.class) + "_______________");
                            User cu = d.toObject(User.class);
                            String id = cu.getUserId();
                            if (zonememberlist.contains(id)) {
                                mMemberList.add(cu);
                            }

                        }
                    }
                });
                mMemberAdapter.notifyDataSetChanged();
            }
        });

    }


    public void openMap(View view) {

        startActivity( new Intent(MembersActivity.this,MapsActivity.class));
    }
}