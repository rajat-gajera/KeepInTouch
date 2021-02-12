package com.example.keepintouch.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.keepintouch.Model.GroupItem;
import com.example.keepintouch.Repository.GroupListRepository;

import java.nio.channels.MulticastChannel;
import java.util.List;

public class GroupListViewModel extends ViewModel {
    private String TAG="grp_vmd_tager";
    GroupListRepository groupListRepository = new GroupListRepository();
    MutableLiveData<List<GroupItem>> mutableLiveData = new MutableLiveData<>();


    public void   getGroupData()
    {
        mutableLiveData = groupListRepository.getMutableGroupList();
    }



    public MutableLiveData<List<GroupItem>> getMutableLiveData()
    {
        return mutableLiveData;
    }



}
