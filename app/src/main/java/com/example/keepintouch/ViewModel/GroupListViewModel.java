package com.example.keepintouch.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.keepintouch.Model.GroupItem;
import com.example.keepintouch.Repository.GroupListRepository;

import java.nio.channels.MulticastChannel;
import java.util.List;
import java.util.Observable;

public class GroupListViewModel extends ViewModel {
    private String TAG = "grp_vmd_tager";
    public GroupListRepository groupListRepository = new GroupListRepository();
    public MutableLiveData<List<GroupItem>> groupItemList = new MutableLiveData<>();

    public void getGroupData() {
        groupListRepository.mutableGroupListLiveData.observeForever(new Observer<List<GroupItem>>() {
            @Override
            public void onChanged(List<GroupItem> groupItems) {

                         Log.d(TAG, "onChangedViewmodel: ");
                        groupItemList.postValue(groupItems);


            }
        });
        groupListRepository.getMutableGroupListLiveData();
    }

}
