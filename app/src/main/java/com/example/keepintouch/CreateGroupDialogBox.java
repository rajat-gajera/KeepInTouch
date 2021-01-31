package com.example.keepintouch;

import android.app.AlertDialog;
import android.app.Dialog;
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

import com.example.keepintouch.Model.GroupItem;
import com.example.keepintouch.Model.Zone;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateGroupDialogBox extends AppCompatDialogFragment {
    private EditText mGroupName;
    private TextView joinCode;
    private ImageView copyCode;

    private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    Map<String, Object> mp= new HashMap<>();

    private static ArrayList<String> clist;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.create_group_dialogbox, null);

        copyCode = view.findViewById(R.id.copycode);
        mGroupName = view.findViewById(R.id.group_name);
        joinCode = view.findViewById(R.id.join_code);
        double v = (Math.random() * 100000);
        int value = (int) v;
        joinCode.setText(Integer.toString(value));
        String Code = Integer.toString(value);


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
                String Radius = "0";
                String GroupName = mGroupName.getText().toString().trim();
                String AdminId = mFirebaseAuth.getCurrentUser().getUid();
                String AdminEmail = mFirebaseAuth.getCurrentUser().getEmail();



                if (GroupName.length() == 0) {
                    Toast.makeText(getContext(), "Group Name Should Not be Empty!", Toast.LENGTH_SHORT).show();
                } else {


                    DocumentReference col =  mFirebaseFirestore.collection("Groups").document();
                    String GroupId= col.getId();
                    mFirebaseFirestore.collection("Groups").document(GroupId).set(new GroupItem(GroupName,GroupId, AdminId, AdminEmail, Code, Radius));
                    mFirebaseFirestore.collection("Group'sCode").document(mFirebaseAuth.getCurrentUser().getUid()).update("CodeList", FieldValue.arrayUnion(Code));
                    ArrayList<String> mlist= new ArrayList<>();
                    mlist.add(AdminId);
                    mFirebaseFirestore.collection("Zone").document(GroupId).set(new Zone(GroupId,Code,"0","0","0",mlist));
                    Toast.makeText(getContext(),"Group Created.",Toast.LENGTH_SHORT).show();
                }
            }
        });
          
        return builder.create();

    }




}
