package com.example.keepintouch;

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

import com.example.keepintouch.Model.GroupItem;
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
    FirebaseFirestore mFirebaseFirestore;
    FirebaseAuth mFirebaseAuth;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.join_group_dialogbox, null);
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
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
                if (code.length() != 5) {
                    //Toast..
                    Toast.makeText(getContext(), "Invalid Code!", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayList<String> codes = new ArrayList<>();
                    mFirebaseFirestore.collection("Groups").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots.isEmpty()) {
                                Log.d("", "onSuccess : No Grpup Found!");
                                return;
                            }
                            String groupId = null;
                            List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : dsList) {
                                GroupItem group = d.toObject(GroupItem.class);
                                String groupCode = group.getCode();
                                if (groupCode.equals(code)) {
                                    groupId = group.getGroupId();
                                    break;
                                }

                            }
                            if (groupId == null) {
                                Toast.makeText(getContext(), "Group Not Found! ", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            String userId = mFirebaseAuth.getCurrentUser().getUid();
                            mFirebaseFirestore.collection("Group'sCode").document(userId).update("CodeList", FieldValue.arrayUnion(code));
                            mFirebaseFirestore.collection("Zone").document(groupId).update("memberList",FieldValue.arrayUnion(userId));

                        }

                    });
                }
            }
        });

        return builder.create();
    }
}
