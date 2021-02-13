package com.example.keepintouch.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keepintouch.Model.GroupItem;
import com.example.keepintouch.R;

import java.util.ArrayList;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    private ArrayList<GroupItem> mGroupList=new ArrayList<>();
    private OnItemClickListener mListener;

    public GroupAdapter(ArrayList<GroupItem> mGroupList) {
        this.mGroupList = mGroupList;
    }

    public void setList(List<GroupItem> groupItems) {
    this.mGroupList = (ArrayList<GroupItem>) groupItems;
    notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(  OnItemClickListener listener){

        mListener=listener;
    }

    public static  class GroupViewHolder extends RecyclerView.ViewHolder{
        public  TextView mGroupName,mAdminEmail,mDate;


        public GroupViewHolder(@NonNull View itemView,final  OnItemClickListener listener) {
            super(itemView);
            mAdminEmail =  itemView.findViewById(R.id.admin_email);
            mGroupName =  itemView.findViewById(R.id.group_name);
            mDate = itemView.findViewById(R.id.Created_date);
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


    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false);
        GroupViewHolder gvh = new GroupViewHolder(v,mListener);
        return gvh;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
    GroupItem currentGroup = (GroupItem) mGroupList.get(position);
    holder.mGroupName.setText(currentGroup.getGroupName());
    holder.mAdminEmail.setText(currentGroup.getCode());
    holder.mDate.setText(currentGroup.getDate());
     }

    @Override
    public int getItemCount() {
        return mGroupList.size();
    }




}
