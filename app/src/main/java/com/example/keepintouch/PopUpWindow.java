package com.example.keepintouch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import androidx.fragment.app.FragmentContainer;


public class PopUpWindow  extends DialogFragment {
    private FragmentContainer mFragmentContainer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pop_up_window, container);

        return  view;
    }

}
