package com.example.keepintouch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;

public class CreateGroupDialogBox extends AppCompatDialogFragment {
    private EditText mGroupName;
    private TextView joinCode;
    private ImageView copyCode;
    private DialogBoxListener listener;
    private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();

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
                    mFirebaseFirestore.collection("Groups").document().set(new GroupItem(GroupName, AdminId, AdminEmail, Code, Radius));

                }
            }
        });
          
        return builder.create();

    }

    public interface DialogBoxListener {
        void createGroup(AppCompatDialogFragment dialogFragment);

    }

    @Override
    public void onAttach(@NonNull Context context) {


        super.onAttach(context);
//        try {
//            listener = (DialogBoxListener) context;
//        } catch (ClassCastException e) {
//            throw  new ClassCastException(context.toString() + "Must implement DialogboxListner");
//        }
    }


}
