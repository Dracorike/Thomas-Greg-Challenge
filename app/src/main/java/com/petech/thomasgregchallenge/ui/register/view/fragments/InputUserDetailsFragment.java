package com.petech.thomasgregchallenge.ui.register.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.petech.thomasgregchallenge.R;
import com.petech.thomasgregchallenge.databinding.FragmentInputUserDetailsBinding;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserError;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserViewModel;
import com.petech.thomasgregchallenge.utils.AppUtils;
import com.petech.thomasgregchallenge.utils.ComponentsUtils;
import com.petech.thomasgregchallenge.utils.MaskEditUtil;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;


public class InputUserDetailsFragment extends Fragment {
    private FragmentInputUserDetailsBinding binding;
    private RegisterUserViewModel viewModel;
    private Boolean gender;

    public InputUserDetailsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInputUserDetailsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(RegisterUserViewModel.class);

        setupClicks();
        setupInputText();
        setupObservables();

        return binding.getRoot();
    }

    private void setupClicks() {
        binding.buttonNextFragmentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDate birthDate = null;
                try {
                    birthDate = LocalDate.parse(
                            AppUtils.formatDateToParse(binding.inputTextBirthDateField.getText().toString())
                    );
                } catch (DateTimeParseException exception) {
                    exception.printStackTrace();
                }

                viewModel.inputUserDetails(
                        binding.inputTextAddressField.getText().toString(),
                        birthDate,
                        gender
                );
            }
        });

        binding.radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_button_man) {
                    gender = true;
                }

                if (checkedId == R.id.radio_button_woman) {
                    gender = false;
                }
            }
        });
    }

    private void setupInputText() {
        binding.inputTextBirthDateField.addTextChangedListener(
                MaskEditUtil.mask(binding.inputTextBirthDateField, MaskEditUtil.FORMAT_DATE)
        );
        binding.inputTextBirthDateField.addTextChangedListener(
                ComponentsUtils.dismissInputErrorTextWatcher(binding.inputTextBirthDateField)
        );
        binding.inputTextAddressField.addTextChangedListener(
                ComponentsUtils.dismissInputErrorTextWatcher(binding.inputTextAddressField)
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
            case USER_DETAILS_EMPTY:
                validateInputFieldsIsEmpty();
                break;
            case USER_UNDER_AGE:
                binding.inputTextBirthDateField.setError(getString(R.string.user_under_age_error_hint));
                break;
        }
    }

    private void validateInputFieldsIsEmpty() {
        ComponentsUtils.validateIfIsEmptyTextWatcher(
                binding.inputTextAddressField,
                getString(R.string.required_field_error_string)
        );
        ComponentsUtils.validateIfIsEmptyTextWatcher(
                binding.inputTextBirthDateField,
                getString(R.string.required_field_error_string)
        );
    }
}