package com.example.keepintouch.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.keepintouch.Model.GroupItem;
import com.example.keepintouch.Model.Zone;
import com.example.keepintouch.R;
import com.example.keepintouch.ViewModel.CreateGroupViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static androidx.core.content.ContextCompat.getSystemService;

public class CreateGroupDialogBox extends AppCompatDialogFragment {
    private EditText mGroupName;
    private TextView joinCode;
    private ImageView copyCode;
    private ClipboardManager clipboardManager;
    private ClipData clipData;
    private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private CreateGroupViewModel createGroupViewModel;
      @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

         AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.create_group_dialogbox, null);
        createGroupViewModel = ViewModelProviders.of(this).get(CreateGroupViewModel.class);
        String value = createGroupViewModel.getCode();
        copyCode = view.findViewById(R.id.copycode);
        mGroupName = view.findViewById(R.id.group_name);
        joinCode = view.findViewById(R.id.join_code);
         joinCode.setText(value);


        builder.setView(view);
        builder.setTitle("Create Group");
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                 String GroupName = mGroupName.getText().toString().trim();
                if (GroupName.length() == 0) {
                    Toast.makeText(getContext(), "Group Name Should Not be Empty!", Toast.LENGTH_SHORT).show();
                } else {

                    createGroupViewModel.CreateGroup(GroupName);

                }
            }
        });
          copyCode.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
            ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                  ClipData myClip;

                  myClip = ClipData.newPlainText("text", value);
                  clipboardManager.setPrimaryClip(myClip);
                  Toast.makeText(getContext(),"Code Copied!",Toast.LENGTH_SHORT).show();
              }
          });
        return builder.create();

    }




}
