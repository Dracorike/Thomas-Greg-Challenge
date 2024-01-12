package com.petech.thomasgregchallenge.ui.register.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.petech.thomasgregchallenge.databinding.FragmentInputUserDocumentBinding;


public class InputUserDocumentFragment extends Fragment {
    private FragmentInputUserDocumentBinding binding;

    public InputUserDocumentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInputUserDocumentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}