package com.petech.thomasgregchallenge.ui.update.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.petech.thomasgregchallenge.data.entities.User;
import com.petech.thomasgregchallenge.ui.update.model.UserDetailsModel;

public class UserDetailsViewModel extends ViewModel {
    private MutableLiveData<UserDetailsErrors> userDetailsErrors = new MutableLiveData<>();
    private MutableLiveData<Boolean> userFound = new MutableLiveData<>();
    private final UserDetailsModel userDetailsModel;

    public UserDetailsViewModel(UserDetailsModel userDetailsModel) {
        this.userDetailsModel = userDetailsModel;
    }

    public void findSelectedUser(int userId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    userDetailsModel.findUserById(userId);
                    userFound.postValue(true);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    userDetailsErrors.postValue(UserDetailsErrors.INITIAL_ERROR);
                }
            }
        }).start();
    }

    public User getSelectedUser() {
        return userDetailsModel.getUser();
    }

    public LiveData<UserDetailsErrors> getUserDetailsErrors() {
        return userDetailsErrors;
    }

    public LiveData<Boolean> getUserFound() {
        return userFound;
    }
}
