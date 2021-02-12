package com.example.keepintouch.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.keepintouch.Repository.CreateGroupRepository;

public class CreateGroupViewModel extends AndroidViewModel {
    private CreateGroupRepository createGroupRepository;
    String Code;
    public CreateGroupViewModel(Application application)
    {
        super(application);
        createGroupRepository = new CreateGroupRepository(application);
        double v = (Math.random() * 100000);
        int value = (int) v;
       Code = Integer.toString(value);
    }
    public String getCode()
    {
        return  Code;
    }
    public void CreateGroup(String GroupName)
    {
        createGroupRepository.CreateGroup(GroupName,Code);

    }
}
