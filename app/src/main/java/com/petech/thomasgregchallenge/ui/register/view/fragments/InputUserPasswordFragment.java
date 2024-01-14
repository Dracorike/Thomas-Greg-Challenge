package com.petech.thomasgregchallenge.ui.register.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petech.thomasgregchallenge.R;
import com.petech.thomasgregchallenge.databinding.FragmentInputUserPasswordBinding;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserError;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserViewModel;
import com.petech.thomasgregchallenge.utils.ComponentsUtils;

public class InputUserPasswordFragment extends Fragment {
    private FragmentInputUserPasswordBinding binding;
    private RegisterUserViewModel viewModel;

    public InputUserPasswordFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInputUserPasswordBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(RegisterUserViewModel.class);

        setupClicks();
        setupPasswordFields();
        setupObservables();

        return binding.getRoot();
    }

    private void setupClicks() {
        binding.buttonFinishRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.inputUserPassword(
                        binding.inputTextUserPasswordField.getText().toString(),
                        binding.inputTextConfirmPasswordField.getText().toString()
                );
            }
        });
    }

    private void setupPasswordFields() {
        binding.inputTextUserPasswordField.addTextChangedListener(
                ComponentsUtils.dismissInputErrorTextWatcher(binding.inputTextUserPasswordField)
        );
        binding.inputTextConfirmPasswordField.addTextChangedListener(
                ComponentsUtils.dismissInputErrorTextWatcher(binding.inputTextConfirmPasswordField)
        );
    }

    private void setupObservables() {
        viewModel.getRegisterError().observe(getViewLifecycleOwner(), new Observer<RegisterUserError>() {
            @Override
            public void onChanged(RegisterUserError registerUserError) {
                handleRegisterUserError(registerUserError);
            }
        });
    }

    private void handleRegisterUserError(RegisterUserError registerUserError) {
        switch (registerUserError) {
            case USER_PASSWORD_EMPTY:
                validateInputFieldsIsEmpty();
                break;
            case PASSWORD_DONT_MATCHES:
                binding.inputTextConfirmPasswordField.setError(getString(R.string.different_passwords_error_hint));
                break;
            case INVALID_PASSWORD:
                binding.inputTextUserPasswordField.setError(getString(R.string.invalid_password_error_hint));
                break;
        }
    }

    private void validateInputFieldsIsEmpty() {
        ComponentsUtils.validateIfIsEmptyTextWatcher(
                binding.inputTextUserPasswordField,
                getString(R.string.required_field_error_string)
        );

        ComponentsUtils.validateIfIsEmptyTextWatcher(
                binding.inputTextConfirmPasswordField,
                getString(R.string.required_field_error_string)
        );
    }
}