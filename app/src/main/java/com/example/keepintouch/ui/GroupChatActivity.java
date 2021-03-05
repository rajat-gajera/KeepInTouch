package com.example.keepintouch.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.keepintouch.Adapter.MessageAdapter;
 import com.example.keepintouch.Model.Message;
import com.example.keepintouch.R;
import com.example.keepintouch.Repository.GroupChatRepository;
import com.example.keepintouch.ViewModel.GroupChatViewModel;
 import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupChatActivity extends AppCompatActivity {
    String currentGroupId;
    private static final String TAG = "grp_chat_tager";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    private static final int RC_PHOTO_PICKER = 2;
    private MessageAdapter mMessageAdapter;
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private GroupChatRepository groupChatRepository;
  //  private ProgressBar mProgressBar;
   // private ImageButton mPhotoPickerButton;
    private EditText mMessageEditText;
    private Button mSendButton;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d(TAG,currentGroupId+" got curent gorup id  in grup chat actviity" );

        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if (extra == null) {
                currentGroupId = null;
                System.out.println("didn't get current group id");
            } else {
                currentGroupId = extra.getString("currentGroupId");
                Log.d(TAG,currentGroupId+" got curent gorup id @2" );


            }
        } else {
            currentGroupId = (String) savedInstanceState.getSerializable("currentGroupId");
            Log.d(TAG,currentGroupId+" got curent gorup id @3" );

        }
        Log.d(TAG,currentGroupId+" got curent gorup id  in red zone actviity" );

        // Initialize references to views
       // mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        //mMessageListView =  findViewById(R.id.messageListView);
       // mPhotoPickerButton = (ImageButton) findViewById(R.id.photoPickerButton);
        mMessageEditText = (EditText) findViewById(R.id.messageEditText);
        mSendButton = (Button) findViewById(R.id.sendButton);


        ArrayList<Message> mMessageList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.messageListView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMessageAdapter = new MessageAdapter(mMessageList);
        mRecyclerView.setAdapter(mMessageAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getParent()));
//
//        GroupChatViewModel groupChatViewModel = new ViewModelProvider(this).get(GroupChatViewModel.class);
//        groupChatViewModel.groupMessageList.observe(this, new Observer<List<Message>>() {
//            @Override
//            public void onChanged(List<Message> groupItems) {
//                //    Log.d(TAG, "onChanged: " + groupItems.size());
//                 Log.d(TAG,groupItems.toString());
//                mMessageAdapter.setList(groupItems);
//                mMessageAdapter.notifyDataSetChanged();
//            }
//        });
//        groupChatViewModel.getGroupData(currentGroupId);




        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        groupChatRepository = new GroupChatRepository();
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

        // Send button sends a message and clears the EditText
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click
                Message mMessage =  new Message(mMessageEditText.getText().toString(),"");
                if(currentGroupId == null){
                    Log.d(TAG,"Current group Id" + currentGroupId);
                    return;
                }
                groupChatRepository.sendMessage(mMessage,currentGroupId);

                // Clear input box
                mMessageEditText.setText("");
                mMessageAdapter.notifyDataSetChanged();

            }
        });


        FirebaseDatabase.getInstance().getReference("GroupChat").child(currentGroupId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                System.out.println(TAG + snapshot.getValue().toString());
                mMessageList.add((Message) snapshot.getValue(Message.class));
                mMessageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}