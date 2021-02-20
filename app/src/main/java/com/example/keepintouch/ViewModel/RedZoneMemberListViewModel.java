package com.example.keepintouch.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.keepintouch.Model.User;
import com.example.keepintouch.Repository.MemberListRepository;
import com.example.keepintouch.Repository.RedZoneMemberListRepository;

import java.util.List;

public class RedZoneMemberListViewModel extends ViewModel {
    private String TAG="red_mem_mv_tager";
    public RedZoneMemberListRepository redZoneMemberListRepository = new RedZoneMemberListRepository();
    public MutableLiveData<List<User>> redMutableMemberList = new MutableLiveData<>();

    public void getMemberList(String currentgroupId)
    {
        redZoneMemberListRepository.mutableRedMemberListLiveData.observeForever(new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                // Log.d(TAG,"MembeListViewModel:");
                redMutableMemberList.postValue(users);
            }
        });
        redZoneMemberListRepository.getMutableMemberListLiveData(currentgroupId);
        redZoneMemberListRepository.getMutableMemberListLiveData(currentgroupId);
    }
}
