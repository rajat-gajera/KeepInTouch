package com.example.keepintouch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.keepintouch.ui.UploadPostActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {


    CardView btnUpload;
    FirebaseFirestore firebaseFirestore;
    List<Post> postList;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    String currentGroupId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if (extra == null) {
                currentGroupId = null;
                System.out.println("didn't get current group id");
            } else {
                currentGroupId = extra.getString("currentGroupId");

            }
        } else {
            currentGroupId = (String) savedInstanceState.getSerializable("currentGroupId");

        }
        btnUpload=findViewById(R.id.btnUpload);
        recyclerView=findViewById(R.id.recyclerView);

        firebaseFirestore=FirebaseFirestore.getInstance();
        postList=new ArrayList<>();

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading...");

        progressDialog.show();

        loadGalleryList();

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GalleryActivity.this, UploadPostActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void loadGalleryList()
    {
        firebaseFirestore.collection("Gallery").document("Gallery").collection(currentGroupId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty())
                {
                    List<DocumentSnapshot> dsList=queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot d : dsList)
                    {
                        Post post=d.toObject(Post.class);
                        postList.add(post);

                    }
                    progressDialog.dismiss();
                    Collections.reverse(postList);

                    GalleryListAdapter galleryListAdapter=new GalleryListAdapter(GalleryActivity.this,postList);
                    recyclerView.setAdapter(galleryListAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(GalleryActivity.this));

                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(GalleryActivity.this, "No Post Available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
