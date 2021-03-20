package com.example.keepintouch.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.keepintouch.Repository.JoinGroupRepository;

public class JoinGroupViewModel  extends AndroidViewModel {
    private JoinGroupRepository joinGroupRepository;

    public JoinGroupViewModel(@NonNull Application application) {
        super(application);
        joinGroupRepository = new JoinGroupRepository(application);

    }
    public void JoinGroup(String Code)
    {
        joinGroupRepository.JoinGroup(Code);
    }

}
