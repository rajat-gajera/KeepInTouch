package com.example.keepintouch.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keepintouch.Model.EmergencyItem;
import com.example.keepintouch.R;

import java.util.ArrayList;

public class EmergencyAdapter extends RecyclerView.Adapter<EmergencyAdapter.ExampleViewHolder> {
    private ArrayList<EmergencyItem> mEmergencyNumberList;
    private onItemClickListener mListener;
    public interface onItemClickListener{
        void onItemClick(int pos);
    }

    public void setOnItemClickListener(onItemClickListener listener)
    {
        mListener=listener;
    }
    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mEmergencyImage,mCallIcon;
        public TextView mEmergencyName;
        public TextView mEmergencyNumber;
        public ExampleViewHolder(@NonNull View itemView,final onItemClickListener listener) {
            super(itemView);
            mEmergencyImage = itemView.findViewById(R.id.emergencyImageView);
            mEmergencyName = itemView.findViewById(R.id.emergencyName);
            mEmergencyNumber = itemView.findViewById(R.id.emergencyNumber);
            mCallIcon = itemView.findViewById(R.id.call_icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null) {
                        int pos = getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
    public EmergencyAdapter(ArrayList<EmergencyItem> exampleList){
        mEmergencyNumberList =exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.emergency_item,parent,false);
        ExampleViewHolder evh=new ExampleViewHolder(v,mListener);
        return evh;
    }


    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        EmergencyItem currentItem= mEmergencyNumberList.get(position);

        holder.mEmergencyImage.setImageResource(currentItem.getEmergencyImageResource());
        holder.mEmergencyName.setText(currentItem.getEmergencyName());
        holder.mEmergencyNumber.setText(currentItem.getEmergencyNumber());
        holder.mCallIcon.setImageResource(R.drawable.ic_call);

    }

    @Override
    public int getItemCount() {
        return mEmergencyNumberList.size();
    }


}
