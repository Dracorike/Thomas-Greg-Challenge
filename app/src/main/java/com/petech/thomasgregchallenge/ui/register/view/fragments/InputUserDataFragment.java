package com.petech.thomasgregchallenge.ui.register.view.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.petech.thomasgregchallenge.R;
import com.petech.thomasgregchallenge.databinding.FragmentInputUserDataBinding;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserError;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserViewModel;
import com.petech.thomasgregchallenge.utils.AppUtils;
import com.petech.thomasgregchallenge.utils.ComponentsUtils;


public class InputUserDataFragment extends Fragment {
    private static final String TAG = "InputUserDataFragment: ";
    private static final String WARNING_TAG = TAG + "warning-tag";
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private FragmentInputUserDataBinding binding;
    private RegisterUserViewModel viewModel;
    private String imagePath;

    public InputUserDataFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInputUserDataBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(RegisterUserViewModel.class);

        setupObservables();
        setupPickImage();
        setupClicks();
        setupTextFields();

        return binding.getRoot();
    }

    private void setupClicks() {
        binding.imageUserPhotoPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });

        binding.buttonNextFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.inputUserData(
                        imagePath,
                        binding.inputTextFullNameField.getText().toString(),
                        binding.inputTextNicknameField.getText().toString(),
                        binding.inputTextEmailAddressField.getText().toString()
                );
            }
        });
    }

    private void setupTextFields() {
        ComponentsUtils.dismissInputErrorTextWatcher(binding.inputTextFullNameField);
        ComponentsUtils.dismissInputErrorTextWatcher(binding.inputTextNicknameField);
        ComponentsUtils.dismissInputErrorTextWatcher(binding.inputTextEmailAddressField);
    }

    private void setupPickImage() {
        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                handlePickedImage(uri);
            } else {
                Log.d("PhotoPicker", "No media selected");
            }
        });
    }

    private void setupObservables() {
        viewModel.getRegisterError().observe(getViewLifecycleOwner(), new Observer<RegisterUserError>() {
            @Override
            public void onChanged(RegisterUserError registerUserError) {
                handleRegisterUserDataError(registerUserError);
            }
        });
    }

    private void handlePickedImage(Uri selectedImageUri) {
        if (selectedImageUri != null) {
            imagePath = selectedImageUri.toString();
            Log.i(TAG, imagePath);
            binding.imageUserPhotoPicker.setImageURI(selectedImageUri);
        } else {
            Toast.makeText(getContext(), getString(R.string.no_image_selected), Toast.LENGTH_LONG).show();
        }
    }

    private void handleRegisterUserDataError(RegisterUserError registerUserError) {
        switch (registerUserError) {
            case DATA_FIELD_EMPTY:
                validateIfFieldsIsEmpty();
                break;
            case NAME_MUST_BE_COMPLETE:
                binding.inputTextFullNameField.setError(getString(R.string.name_must_be_complete_error_hint));
                break;
            case USER_NAME_ALREADY_EXISTS:
                binding.inputTextNicknameField.setError(getString(R.string.user_name_already_exists_error_hint));
                break;
            case EMAIL_INVALID:
                binding.inputTextEmailAddressField.setError(getString(R.string.email_invalid_error_message));
                break;
        }
    }

    private void validateIfFieldsIsEmpty() {
        ComponentsUtils.validateIfIsEmptyTextWatcher(
                binding.inputTextFullNameField,
                getString(R.string.required_field_error_string)
        );
        ComponentsUtils.validateIfIsEmptyTextWatcher(
                binding.inputTextNicknameField,
                getString(R.string.required_field_error_string)
        );
        ComponentsUtils.validateIfIsEmptyTextWatcher(
                binding.inputTextEmailAddressField,
                getString(R.string.required_field_error_string)
        );

        if (imagePath == null) {
            AppUtils.showError(
                    requireActivity(),
                    getString(R.string.choose_profile_picture_error_message),
                    WARNING_TAG,
                    getChildFragmentManager()
            );
        }
    }
}