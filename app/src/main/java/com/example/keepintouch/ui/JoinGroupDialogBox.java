package com.example.keepintouch.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.keepintouch.Model.GroupItem;
import com.example.keepintouch.R;
import com.example.keepintouch.ViewModel.JoinGroupViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class JoinGroupDialogBox extends AppCompatDialogFragment {
    EditText mJoinGroupCode;
    private JoinGroupViewModel joinGroupViewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.join_group_dialogbox, null);
       joinGroupViewModel = ViewModelProviders.of(this).get(JoinGroupViewModel.class);

        mJoinGroupCode = view.findViewById(R.id.enter_code);
        builder.setView(view);
        builder.setTitle("Join Group");
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("Join", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String code = mJoinGroupCode.getText().toString().trim();
                if (code.length() ==0) {
                     Toast.makeText(getContext(), "Invalid Code!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    joinGroupViewModel.JoinGroup(code);

                }
            }
        });

        return builder.create();
    }
}
