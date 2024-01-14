package com.petech.thomasgregchallenge.ui.main.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.petech.thomasgregchallenge.data.entities.User;
import com.petech.thomasgregchallenge.ui.main.model.MainModel;

import java.util.List;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<User>> userList = new MutableLiveData<>();

    private final MainModel mainModel;

    public MainViewModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    public void getUsersListFromDatabase() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<User> users = mainModel.getAllUsers();
                    userList.postValue(users);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }).start();
    }

    public LiveData<List<User>> getUserList() {
        return userList;
    }
}
