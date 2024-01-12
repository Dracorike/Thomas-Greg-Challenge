package com.petech.thomasgregchallenge.ui.register.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petech.thomasgregchallenge.R;
import com.petech.thomasgregchallenge.databinding.FragmentInputUserDataBinding;


public class InputUserDataFragment extends Fragment {
    private FragmentInputUserDataBinding binding;

    public InputUserDataFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInputUserDataBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}