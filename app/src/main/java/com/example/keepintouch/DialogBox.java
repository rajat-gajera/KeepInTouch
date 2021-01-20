package com.example.keepintouch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import java.security.acl.Group;

public class DialogBox  extends AppCompatDialogFragment {
    private EditText mGroupName;
    private TextView joinCode;
    private DialogBoxListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_box,null);

        mGroupName = view.findViewById(R.id.group_name);
        joinCode = view.findViewById(R.id.join_code);
        joinCode.setText("RAAJA");


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
                String AdminName = "Rajat";
                if (GroupName.length() == 0) {
                    Toast.makeText(getContext(), "Group Name Should Not be Empty!",Toast.LENGTH_SHORT).show();
                } else {
                    listener.createGroup(DialogBox.this);

                }
            }
        });
        return  builder.create();

    }
    public interface DialogBoxListener {
         void createGroup(AppCompatDialogFragment dialogFragment);

    }

    @Override
    public void onAttach(@NonNull Context context) {


        super.onAttach(context);
        try {
            listener = (DialogBoxListener) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString() + "Must implement DialogboxListner");
        }
    }
}
