package com.example.keepintouch.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keepintouch.Model.FAQsItem;
import com.example.keepintouch.R;

import java.util.ArrayList;

public class FAQsAdapter extends RecyclerView.Adapter<FAQsAdapter.ExampleViewHolder> {
    private ArrayList<FAQsItem> mExampleList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public TextView mTextView2;
        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView1= itemView.findViewById(R.id.question);
            mTextView2= itemView.findViewById(R.id.answer);
        }
    }
    public FAQsAdapter(ArrayList<FAQsItem> exampleList){
        mExampleList=exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.faqs_item,parent,false);
        ExampleViewHolder evh=new ExampleViewHolder(v);
        return evh;
    }


    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        FAQsItem currentItem= mExampleList.get(position);
        holder.mTextView1.setText(currentItem.getmText1());
        holder.mTextView2.setText(currentItem.getmText2());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}

