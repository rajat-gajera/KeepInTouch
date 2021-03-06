package com.example.keepintouch.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keepintouch.Model.Message;
import com.example.keepintouch.Model.User;
import com.example.keepintouch.R;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter  extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    private ArrayList<Message> mMessageList = null;

    public void setList(List<Message> messages) {
        mMessageList = (ArrayList<Message>) messages;

    }



    public static class MessageViewHolder extends RecyclerView.ViewHolder
    {   public TextView  mMessageText,mSenderText;
        public LinearLayout itemBody;


        public MessageViewHolder(@NonNull View itemView ) {
            super(itemView);
            mMessageText = itemView.findViewById(R.id.messageTextView);
            mSenderText= itemView.findViewById(R.id.nameTextView);

        }
    }

    public MessageAdapter(ArrayList<Message> MessageList){
        this.mMessageList= MessageList;
    }
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,parent,false);
        MessageViewHolder mvh = new MessageViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message  currentMessage = (Message)mMessageList.get(position);
        //System.out.println(currentMember.getName());
        holder.mMessageText.setText(currentMessage.getText());
        holder.mSenderText.setText(currentMessage.getName());
        
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }


}
