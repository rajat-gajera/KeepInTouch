package com.example.keepintouch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemberAdapter  extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder>{

    private ArrayList<User> mMemberList = null;
    private MemberAdapter.OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(  MemberAdapter.OnItemClickListener listener){

        mListener=listener;
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder
    {   public TextView mMemberName,mMemberMobile;


        public MemberViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mMemberName = itemView.findViewById(R.id.user_name);
            mMemberMobile= itemView.findViewById(R.id.user_mobile);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!= null)
                    {
                        int pos= getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }

    public MemberAdapter(ArrayList<User> MemberList){
        this.mMemberList= MemberList;
    }
    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        MemberViewHolder mvh = new MemberViewHolder(v, (OnItemClickListener) mListener);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        User currentMember = (User)mMemberList.get(position);
        //System.out.println(currentMember.getName());
        holder.mMemberName.setText(currentMember.getName());
        holder.mMemberMobile.setText(currentMember.getPhone());
    }

    @Override
    public int getItemCount() {
        return mMemberList.size();
    }


}
