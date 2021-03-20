package com.example.keepintouch.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.keepintouch.Adapter.EmergencyAdapter;
import com.example.keepintouch.Model.EmergencyItem;
import com.example.keepintouch.R;

import java.util.ArrayList;

public class EmergencyActivity extends Fragment {

    private RecyclerView mRecyclerView;
    private EmergencyAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Emergency");

        View rootview = inflater.inflate(R.layout.fragment_emergency, container, false);


        ArrayList<EmergencyItem> exampleList=new ArrayList<>();
        exampleList.add(new EmergencyItem(R.drawable.ic_hospital,"Ambulance","108"));
        exampleList.add(new EmergencyItem(R.drawable.ic_police,"Police","100"));
        exampleList.add(new EmergencyItem(R.drawable.ic_fire,"Fire Brigade","101"));
        exampleList.add(new EmergencyItem(R.drawable.ic_prg_women,"Women Helpline","1091"));
        exampleList.add(new EmergencyItem(R.drawable.ic__single_person,"Senior Citizen Helpline","1291"));
        exampleList.add(new EmergencyItem(R.drawable.ic_road_accident,"Road Accident Emergency Service","1073"));
        exampleList.add(new EmergencyItem(R.drawable.ic_child,"Children in Difficult Situation","1098"));
        exampleList.add(new EmergencyItem(R.drawable.ic_rail,"Rail Accident Emergency Service","1072"));
        exampleList.add(new EmergencyItem(R.drawable.ic_plane,"Air Ambulance","9540161344"));
        exampleList.add(new EmergencyItem(R.drawable.ic_national_emergency_number,"National Emergency Number","112"));

        mRecyclerView=rootview.findViewById(R.id.emergencyrecyclerView);
        mRecyclerView.setHasFixedSize(true);
         mAdapter= new EmergencyAdapter(exampleList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new EmergencyAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                        EmergencyItem item=exampleList.get(pos);
                        Intent intent=new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:"+item.getEmergencyNumber()));
                        getContext().startActivity(intent);
            }
        });
        return rootview;
    }
}