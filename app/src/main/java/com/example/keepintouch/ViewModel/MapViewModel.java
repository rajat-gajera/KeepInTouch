package com.example.keepintouch.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.keepintouch.Model.User;
import com.example.keepintouch.Repository.MapRepository;
import com.example.keepintouch.ui.MembersActivity;

import java.util.List;

public class MapViewModel extends ViewModel {
    private  String TAG="map_mv_tager";
    public MutableLiveData<List<User>> memberListLiveData = new MutableLiveData<>();
    private  MapRepository mapRepository=  new MapRepository(MembersActivity.getMemberActivityInstance().getCurrentgroupid());
    public void getMapMemberListData()
    {
       mapRepository.mapMemberListMutableLiveData.observeForever(new Observer<List<User>>() {
           @Override
           public void onChanged(List<User> lists) {
                memberListLiveData.postValue(lists);
           }
       });

        mapRepository.run();
        mapRepository.run();


    }



}
