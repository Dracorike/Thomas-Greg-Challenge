package com.petech.thomasgregchallenge.ui.register.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.petech.thomasgregchallenge.databinding.FragmentRegistrationUserSuccessBinding;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserViewModel;

public class RegistrationUserSuccessFragment extends Fragment {
    private FragmentRegistrationUserSuccessBinding binding;
    private RegisterUserViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegistrationUserSuccessBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(RegisterUserViewModel.class);

        setupClicks();

        return binding.getRoot();
    }

    private void setupClicks() {
        binding.buttonConcludeRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.nextFragment();
            }
        });
    }
}