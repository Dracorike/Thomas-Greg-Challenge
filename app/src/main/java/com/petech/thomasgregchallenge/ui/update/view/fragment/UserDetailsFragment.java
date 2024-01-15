package com.petech.thomasgregchallenge.ui.update.view.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.petech.thomasgregchallenge.R;
import com.petech.thomasgregchallenge.data.entities.User;
import com.petech.thomasgregchallenge.data.entities.enums.UserType;
import com.petech.thomasgregchallenge.databinding.FragmentUserDetailsBinding;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserError;
import com.petech.thomasgregchallenge.ui.update.view.utils.ViewUtil;
import com.petech.thomasgregchallenge.ui.update.viewmodel.UserDetailsViewModel;
import com.petech.thomasgregchallenge.ui.update.viewmodel.UserUpdateResult;
import com.petech.thomasgregchallenge.utils.AppUtils;
import com.petech.thomasgregchallenge.utils.ComponentsUtils;
import com.petech.thomasgregchallenge.utils.MaskEditUtil;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class UserDetailsFragment extends Fragment {
    private static final String TAG = "UserDetailsFragment: ";
    private static final String WARNING_TAG = TAG + "warning-tag";
    private User selectedUser;

    private FragmentUserDetailsBinding binding;

    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private UserDetailsViewModel viewModel;
    private UserType userType;
    private String imagePath;
    private TextWatcher cpfMask;
    private TextWatcher cnpjMask;

    public UserDetailsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(UserDetailsViewModel.class);

        initGlobalValues();

        setupPickImage();
        setupInputFields();
        setupClicks();
        populateFields();
        setupObservables();

        return binding.getRoot();
    }

    private void initGlobalValues() {
        selectedUser = viewModel.getSelectedUser();
        imagePath = selectedUser.getUserPhoto();
        userType = selectedUser.getUserType();
        cpfMask = MaskEditUtil.mask(binding.inputTextCpfCnpjUpdate, MaskEditUtil.FORMAT_CPF);
        cnpjMask = MaskEditUtil.mask(binding.inputTextCpfCnpjUpdate, MaskEditUtil.FORMAT_CNPJ);
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

    private void setupInputFields() {
        ComponentsUtils.dismissInputErrorTextWatcher(binding.inputTextFullNameUpdate);
        ComponentsUtils.dismissInputErrorTextWatcher(binding.inputTextNicknameUpdate);
        ComponentsUtils.dismissInputErrorTextWatcher(binding.inputTextEmailUpdate);
        ComponentsUtils.dismissInputErrorTextWatcher(binding.inputTextBirthDateUpdate);
        ComponentsUtils.dismissInputErrorTextWatcher(binding.inputTextAddressUpdate);
        ComponentsUtils.dismissInputErrorTextWatcher(binding.inputTextCpfCnpjUpdate);
    }

    private void setupClicks() {
        binding.imageViewUserProfileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });
        binding.radioGroupCpfCnpjUpdate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_button_cpf) {
                    setupInputTextToCPF();
                }

                if (checkedId == R.id.radio_button_cnpj) {
                    setupInputTextToCNPJ();
                }
            }
        });

        binding.buttonSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User userUpdated = ViewUtil.getUserByView(binding, imagePath);
                userUpdated.set_id(selectedUser.get_id());
                userUpdated.setPassword(selectedUser.getPassword());

                viewModel.saveUserDetailsChanges(userUpdated);
            }
        });
    }

    private void populateFields() {
        populateImageView();
        binding.inputTextFullNameUpdate.setText(selectedUser.getName());
        binding.inputTextNicknameUpdate.setText(selectedUser.getUserName());
        binding.inputTextEmailUpdate.setText(selectedUser.getEmail());
        binding.inputTextAddressUpdate.setText(selectedUser.getAddress());
        binding.inputTextBirthDateUpdate.setText(AppUtils.formateLocalDateToString(selectedUser.getBirthDate()));

        if (selectedUser.isGender()) {
            binding.radioOptionManUpdate.toggle();
        } else {
            binding.radioOptionWomanUpdate.toggle();
        }

        switch (selectedUser.getUserType()) {
            case CPF:
                binding.radioOptionCpfUpdate.toggle();
                break;
            case CNPJ:
                binding.radioOptionCnpjUpdate.toggle();
                break;
        }

        binding.inputTextCpfCnpjUpdate.setText(selectedUser.getDocumentNumber());
    }

    private void setupObservables() {
        viewModel.getRegisterError().observe(getViewLifecycleOwner(), new Observer<RegisterUserError>() {
            @Override
            public void onChanged(RegisterUserError registerUserError) {
                handleViewModelErrors(registerUserError);
            }
        });

        viewModel.getUserUpdated().observe(getViewLifecycleOwner(), new Observer<UserUpdateResult>() {
            @Override
            public void onChanged(UserUpdateResult userUpdateResult) {
                handleUserUpdateResult(userUpdateResult);
            }
        });
    }

    private void populateImageView() {
        if (checkReadFilesPermission()) {
            Uri imageUri = Uri.parse(selectedUser.getUserPhoto());
            try {
                InputStream inputStream = requireActivity().getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                binding.imageViewUserProfileUpdate.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupInputTextToCPF() {
        if (userType == UserType.CNPJ && cnpjMask != null) {
            binding.inputTextCpfCnpjUpdate.removeTextChangedListener(cnpjMask);

            cpfMask = null;
        }
        userType = UserType.CPF;
        cpfMask = MaskEditUtil.mask(binding.inputTextCpfCnpjUpdate, MaskEditUtil.FORMAT_CPF);

        binding.inputTextCpfCnpjUpdate.setHint(requireActivity().getString(R.string.cpf_string));
        binding.inputTextCpfCnpjUpdate.addTextChangedListener(cpfMask);
    }

    private void setupInputTextToCNPJ() {
        if (userType == UserType.CPF && cpfMask != null) {
            binding.inputTextCpfCnpjUpdate.removeTextChangedListener(cpfMask);

            cnpjMask = null;
        }

        userType = UserType.CNPJ;
        cnpjMask = MaskEditUtil.mask(binding.inputTextCpfCnpjUpdate, MaskEditUtil.FORMAT_CNPJ);

        binding.inputTextCpfCnpjUpdate.setHint(requireActivity().getString(R.string.cnpj_string));
        binding.inputTextCpfCnpjUpdate.addTextChangedListener(cnpjMask);
    }

    private void handlePickedImage(Uri selectedImageUri) {
        if (selectedImageUri != null) {
            imagePath = selectedImageUri.toString();
            binding.imageViewUserProfileUpdate.setImageURI(selectedImageUri);
        } else {
            Toast.makeText(getContext(), getString(R.string.no_image_selected), Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkReadFilesPermission() {
        int result = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void handleViewModelErrors(RegisterUserError registerUserError) {
        switch (registerUserError) {
            case USER_DETAILS_EMPTY:
            case USER_DOCUMENT_EMPTY:
            case USER_PASSWORD_EMPTY:
            case DATA_FIELD_EMPTY:
                showError(getString(R.string.update_empty_field_error_message));
                break;
            case NAME_MUST_BE_COMPLETE:
                showError(getString(R.string.name_must_be_complete_error_message));
                break;
            case EMAIL_INVALID:
                showError(getString(R.string.email_invalid_error_message));
                break;
            case USER_NAME_ALREADY_EXISTS:
                showError(getString(R.string.user_name_already_exists_error_message));
                break;
            case USER_UNDER_AGE:
                showError(getString(R.string.user_under_age_update_error_message));
                break;
            case INVALID_CPF:
                showError(getString(R.string.invalid_cpf_error_message));
                break;
            case INVALID_CNPJ:
                showError(getString(R.string.invalid_cnpj_error_message));
                break;
            case INVALID_PASSWORD:
                showError(getString(R.string.invalid_password_error_message));
                break;
            case UNKNOWN_ERROR:
                showError(getString(R.string.unknown_error_message));
                break;
        }
    }

    private void handleUserUpdateResult(UserUpdateResult userUpdateResult) {
        switch (userUpdateResult) {
            case USER_UPDATED:
                Toast.makeText(
                        requireContext(),
                        getString(R.string.user_update_success_message),
                        Toast.LENGTH_LONG
                ).show();
                break;
            case USER_IS_SAME:
                showError(getString(R.string.user_is_same_message));
                break;
            case UPDATE_ERROR:
                showError(getString(R.string.update_error_message));
                break;
        }
    }

    private void showError(String msg) {
        AppUtils.showError(requireContext(), msg, WARNING_TAG, getChildFragmentManager());
    }
}