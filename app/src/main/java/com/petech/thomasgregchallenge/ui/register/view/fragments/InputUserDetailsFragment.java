package com.petech.thomasgregchallenge.ui.register.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petech.thomasgregchallenge.R;
import com.petech.thomasgregchallenge.databinding.FragmentInputUserDetailsBinding;


public class InputUserDetailsFragment extends Fragment {
    private FragmentInputUserDetailsBinding binding;

    public InputUserDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInputUserDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}