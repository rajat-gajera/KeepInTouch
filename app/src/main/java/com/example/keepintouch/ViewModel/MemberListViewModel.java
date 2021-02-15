package com.example.keepintouch.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.keepintouch.App;
import com.example.keepintouch.Model.User;
 import com.example.keepintouch.Repository.MemberListRepository;
import com.example.keepintouch.ui.MembersActivity;

import java.util.List;

public class MemberListViewModel extends  ViewModel{
    private String TAG="mem_mv_tager";
    public MemberListRepository memberListRepository = new MemberListRepository();
    public MutableLiveData<List<User>> mutableMemberList = new MutableLiveData<>();

    public void getMemberList(String currentgroupId)
    {
        memberListRepository.mutableMemberListLiveData.observeForever(new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
               // Log.d(TAG,"MembeListViewModel:");
                mutableMemberList.postValue(users);
            }
        });
        memberListRepository.getMutableMemberListLiveData(currentgroupId);
        memberListRepository.getMutableMemberListLiveData(currentgroupId);
    }


}
