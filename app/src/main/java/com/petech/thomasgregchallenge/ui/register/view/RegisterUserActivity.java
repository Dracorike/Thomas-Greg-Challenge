package com.petech.thomasgregchallenge.ui.register.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.petech.thomasgregchallenge.application.App;
import com.petech.thomasgregchallenge.databinding.ActivityRegisterUserBinding;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserViewModel;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserViewModelFactory;

public class RegisterUserActivity extends AppCompatActivity {
    private ActivityRegisterUserBinding binding;
    private RegisterUserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupViewModel();
    }

    private void setupViewModel() {
        RegisterUserViewModelFactory factory = ((App) getApplication()).getRegisterUserViewModel();
        viewModel = new ViewModelProvider(this, factory).get(RegisterUserViewModel.class);
    }
}