package com.petech.thomasgregchallenge.ui.update.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.petech.thomasgregchallenge.R;
import com.petech.thomasgregchallenge.databinding.FragmentChangePasswordBinding;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserError;
import com.petech.thomasgregchallenge.ui.update.viewmodel.UserDetailsViewModel;
import com.petech.thomasgregchallenge.ui.update.viewmodel.UserUpdateResult;
import com.petech.thomasgregchallenge.utils.AppUtils;
import com.petech.thomasgregchallenge.utils.ComponentsUtils;


public class ChangePasswordFragment extends Fragment {
    private static final String TAG = "ChangePasswordFragment: ";
    private static final String WARNING_TAG = TAG + "warning-tag";

    private FragmentChangePasswordBinding binding;
    private UserDetailsViewModel viewModel;

    public ChangePasswordFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(UserDetailsViewModel.class);

        setupPasswordFields();
        setupObservables();
        setupClicks();

        return binding.getRoot();
    }

    private void setupPasswordFields() {
        ComponentsUtils.dismissInputErrorTextWatcher(binding.inputTextPasswordUpdate);
        ComponentsUtils.dismissInputErrorTextWatcher(binding.inputTextConfirmPasswordUpdate);
    }

    private void setupObservables() {
        viewModel.getRegisterError().observe(getViewLifecycleOwner(), new Observer<RegisterUserError>() {
            @Override
            public void onChanged(RegisterUserError registerUserError) {
                handleRegisterUserError(registerUserError);
            }
        });

        viewModel.getUserUpdated().observe(getViewLifecycleOwner(), new Observer<UserUpdateResult>() {
            @Override
            public void onChanged(UserUpdateResult userUpdateResult) {
                handleUpdateResult(userUpdateResult);
            }
        });
    }

    private void setupClicks() {
        binding.buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.changePassword(
                        binding.inputTextPasswordUpdate.getText().toString(),
                        binding.inputTextConfirmPasswordUpdate.getText().toString()
                );
            }
        });

    }

    private void handleRegisterUserError(RegisterUserError registerUserError) {
        switch (registerUserError) {
            case USER_PASSWORD_EMPTY:
                validateInputFieldsIsEmpty();
                break;
            case PASSWORD_DONT_MATCHES:
                binding.inputTextConfirmPasswordUpdate.setError(getString(R.string.different_passwords_error_hint));
                break;
            case INVALID_PASSWORD:
                binding.inputTextPasswordUpdate.setError(getString(R.string.invalid_password_error_hint));
                break;
        }
    }

    private void handleUpdateResult(UserUpdateResult updateResult) {
        switch (updateResult) {
            case USER_UPDATED:
                Toast.makeText(
                        requireContext(),
                        getString(R.string.password_change_success_message),
                        Toast.LENGTH_LONG
                ).show();
                requireActivity().finish();
                break;
            case UPDATE_ERROR:
                showError(getString(R.string.password_change_error_message));
                break;
        }
    }

    private void validateInputFieldsIsEmpty() {
        ComponentsUtils.validateIfIsEmptyTextWatcher(
                binding.inputTextPasswordUpdate,
                getString(R.string.required_field_error_string)
        );

        ComponentsUtils.validateIfIsEmptyTextWatcher(
                binding.inputTextConfirmPasswordUpdate,
                getString(R.string.required_field_error_string)
        );
    }

    private void showError(String msg) {
        AppUtils.showError(requireContext(), msg, WARNING_TAG, getChildFragmentManager());
    }
}