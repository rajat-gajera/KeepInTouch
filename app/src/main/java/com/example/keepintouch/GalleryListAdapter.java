package com.example.keepintouch;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.artjimlop.altex.AltexImageDownloader;
import com.bumptech.glide.Glide;
import com.example.keepintouch.ui.MembersActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class GalleryListAdapter extends RecyclerView.Adapter<GalleryListAdapter.ViewHolder> {

    List<Post> postList;
    Context context;
    public static Context dContext;
    FirebaseFirestore firebaseFirestore;

    public GalleryListAdapter(Context context,List<Post> postList) {
        this.postList = postList;
        this.context = context;
        this.dContext=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_list_recycler_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.userName.setText(postList.get(position).getUserName());
        holder.nameCaption.setText(postList.get(position).getUserName());
        holder.caption.setText(postList.get(position).getCaption());
        holder.time.setText(postList.get(position).getDate()+" / "+postList.get(position).getTime());
//        Animation animation= AnimationUtils.loadAnimation(context,R.anim.scale_fade_anim);
//        holder.layout.startAnimation(animation);
        Glide.with(context)
                .load(postList.get(position).getImgURL())
                .into(holder.postImg);

        holder.postImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final ProgressDialog progressDialog=new ProgressDialog(context);
                progressDialog.setCancelable(true);
                progressDialog.setMessage("Deleting...");
                progressDialog.show();
                AlertDialog.Builder dialog=new AlertDialog.Builder(context);
                dialog.setMessage("Are you sure you want to delete ?");
                dialog.setCancelable(true);
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebaseFirestore=FirebaseFirestore.getInstance();
                        firebaseFirestore.collection("Gallery").document("Gallery")
                                .collection(MembersActivity.getMemberActivityInstance().currentgroupid)
                                .document(postList.get(position).getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(context,GalleryActivity.class);
                                context.startActivity(intent);
                                Activity activity=(Activity)context;
                                activity.finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressDialog.dismiss();
                    }
                });
                dialog.show();
                return false;
            }
        });

        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                            PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else {
                        Toast.makeText(context, "Downloading..", Toast.LENGTH_LONG).show();
                        AltexImageDownloader.writeToDisk(context, postList.get(position).getImgURL(),"TrackMate");
                    }
                }
            }
        });


//                DownloadManager downloadManager=(DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
//                Uri uri=Uri.parse(postList.get(position).getImgURL());
//                DownloadManager.Request request=new DownloadManager.Request(uri);
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                Long reference=downloadManager.enqueue(request);


    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView userName;
        TextView time;
        TextView caption;
        ImageView postImg;
        TextView nameCaption;
        RelativeLayout layout;
        ImageView btnDownload;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName=itemView.findViewById(R.id.userName);
            caption=itemView.findViewById(R.id.caption);
            time=itemView.findViewById(R.id.time);
            parentLayout=itemView.findViewById(R.id.parentLayout);
            btnDownload=itemView.findViewById(R.id.btnDownload);
            postImg=itemView.findViewById(R.id.postImg);
            layout=itemView.findViewById(R.id.relativeLayout);
            nameCaption=itemView.findViewById(R.id.nameCaption);
        }
    }
}
