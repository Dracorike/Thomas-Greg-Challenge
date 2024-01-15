package com.petech.thomasgregchallenge.ui.update.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserViewModel;
import com.petech.thomasgregchallenge.ui.update.model.UserDetailsModel;

public class UserDetailsViewModelFactory implements ViewModelProvider.Factory {
    private final UserDetailsModel userDetailsModel;

    public UserDetailsViewModelFactory(UserDetailsModel userDetailsModel) {
        this.userDetailsModel = userDetailsModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserDetailsViewModel.class)) {
            return (T) new UserDetailsViewModel(userDetailsModel);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
