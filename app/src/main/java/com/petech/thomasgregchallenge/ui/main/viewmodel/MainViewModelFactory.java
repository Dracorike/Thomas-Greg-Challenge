package com.petech.thomasgregchallenge.ui.main.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.petech.thomasgregchallenge.ui.main.model.MainModel;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final MainModel mainModel;

    public MainViewModelFactory(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(mainModel);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
