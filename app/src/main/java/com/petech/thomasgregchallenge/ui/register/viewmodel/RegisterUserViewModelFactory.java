package com.petech.thomasgregchallenge.ui.register.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.petech.thomasgregchallenge.ui.register.model.RegisterUserModel;

public class RegisterUserViewModelFactory implements ViewModelProvider.Factory {
    private final RegisterUserModel model;

    public RegisterUserViewModelFactory(RegisterUserModel model) {
        this.model = model;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegisterUserViewModel.class)) {
            return (T) new RegisterUserViewModel(model);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
