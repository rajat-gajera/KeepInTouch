package com.example.keepintouch.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keepintouch.Model.SurvivalKitItem;
import com.example.keepintouch.R;


import java.util.ArrayList;

public class SurvivalKitAdapter extends RecyclerView.Adapter<SurvivalKitAdapter.ExampleViewHolder> {
    private ArrayList<SurvivalKitItem> mExampleList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView= itemView.findViewById(R.id.survivalkit_item_imageview);
            mTextView1= itemView.findViewById(R.id.survivalkit_item_title_text);
            mTextView2= itemView.findViewById(R.id.survivalkit_item_body_textview);
        }
    }
    public SurvivalKitAdapter(ArrayList<SurvivalKitItem> exampleList){
        mExampleList=exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.survivalkit_item,parent,false);
        ExampleViewHolder evh=new ExampleViewHolder(v);
        return evh;
    }


    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        SurvivalKitItem currentItem= mExampleList.get(position);

        holder.mImageView.setImageResource(currentItem.getImageSource());
        holder.mTextView1.setText(currentItem.getmText1());
        holder.mTextView2.setText(currentItem.getmText2());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
