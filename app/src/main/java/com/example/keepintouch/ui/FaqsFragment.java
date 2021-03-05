package com.example.keepintouch.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keepintouch.Adapter.FAQsAdapter;
import com.example.keepintouch.Model.FAQsItem;
import com.example.keepintouch.R;

import java.util.ArrayList;

public class FaqsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FAQsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("FAQsItem");
        View rootView = inflater.inflate(R.layout.fragment_faqs, container, false);
        ArrayList<FAQsItem> faqlist=new ArrayList<>();
        faqlist.add(new FAQsItem("1. How can i Send message to a Group ?","Just go to dashboard and select message section.\nIn the Message section there is a button on top right corner of the Screen click on that button and you can send messages to Group."));
        faqlist.add(new FAQsItem("2. How to Create Group?","Anyone can create group.\nFor that just go to home screen and select plus button on bottom right corner and then select create group button and enter the group name which you want."));
        faqlist.add(new FAQsItem("3. How to Join Group?","Anyone can join group using 5-digit secret code.\nJust go to home screen and select plus button on bottom right corner and then select Join group button and enter secret code for join the group and hurray.. you joined the Group."));
        faqlist.add(new FAQsItem("4. What is the meaning of 'Alarm Available'?","It means that there is alarm which is set by the captain of your team and you should have to follow the instructions which was given by your captain."));
        faqlist.add(new FAQsItem("5. What if i am not able to get location?","First check your phone permissions and give permission if not given and then start GPS location then restart application and refresh location."));
        faqlist.add(new FAQsItem("6. How to set Radius?","This Functionality is only accessible for Group admin.\nFor that goto the Group and select location button at bottom most right corner then type the radius value in meter."));
        faqlist.add(new FAQsItem("7. How can i call other members of my group ?","Just go to the Dashboard and select My Group section.\nIn the My Group section you will get the details of your group and you will able to call that member directly"));
        faqlist.add(new FAQsItem("8. What should I do if I lost and my internet connection is not working ?","Go to the Dashboard and select Survival Kit section.\nKindly follow the Instruction which are given in that section until your captain will find your location"));
        mRecyclerView=rootView.findViewById(R.id.faq_fragment_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager= new LinearLayoutManager(getContext());
        mAdapter= new FAQsAdapter(faqlist);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


    return rootView;
    }

}