package com.example.keepintouch.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.keepintouch.Model.GroupItem;
import com.example.keepintouch.Model.Message;
import com.example.keepintouch.Repository.GroupChatRepository;
import com.example.keepintouch.Repository.GroupListRepository;

import java.time.chrono.MinguoEra;
import java.util.List;

public class GroupChatViewModel extends ViewModel {
    private String TAG = "grp_chat_vmd_tager";
    public GroupChatRepository groupChatRepository = new GroupChatRepository();
    public MutableLiveData<List<Message>> groupMessageList = new MutableLiveData<>();

    public void getGroupData(String currentGroupId) {
        groupChatRepository.mutableMessageLiveData.observeForever(new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> groupItems) {

                //  Log.d(TAG, "onChangedViewmodel: ");
                groupMessageList.postValue(groupItems);


            }
        });
        groupChatRepository.getMutableLiveMessages(currentGroupId);
        groupChatRepository.getMutableLiveMessages(currentGroupId);
    }
}
