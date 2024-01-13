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
import com.petech.thomasgregchallenge.data.entities.enums.UserType;
import com.petech.thomasgregchallenge.databinding.FragmentInputUserDocumentBinding;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserError;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserViewModel;
import com.petech.thomasgregchallenge.utils.ComponentsUtils;
import com.petech.thomasgregchallenge.utils.MaskEditUtil;


public class InputUserDocumentFragment extends Fragment {
    private FragmentInputUserDocumentBinding binding;
    private RegisterUserViewModel viewModel;
    private UserType userType;

    public InputUserDocumentFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInputUserDocumentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(RegisterUserViewModel.class);

        setupClicks();
        setupObservables();
        setupInputTextDocument();

        return binding.getRoot();
    }

    private void setupClicks() {
        binding.radioGroupCpfCnpj.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_button_cpf) {
                    setupInputTextToCPF();
                }

                if (checkedId == R.id.radio_button_cnpj) {
                    setupInputTextToCNPJ();
                }

                binding.inputTextCpfCnpjField.setEnabled(true);
            }
        });

        binding.buttonNextFragmentDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.inputUserDocument(
                        binding.inputTextCpfCnpjField.getText().toString(),
                        userType
                );
            }
        });
    }

    private void setupObservables() {
        viewModel.getRegisterError().observe(getViewLifecycleOwner(), new Observer<RegisterUserError>() {
            @Override
            public void onChanged(RegisterUserError registerUserError) {
                handleRegisterUserError(registerUserError);
            }
        });
    }

    private void setupInputTextDocument() {
        binding.inputTextCpfCnpjField.addTextChangedListener(
                ComponentsUtils.dismissInputErrorTextWatcher(binding.inputTextCpfCnpjField)
        );
    }

    private void setupInputTextToCPF() {
        userType = UserType.CPF;
        binding.inputTextCpfCnpjField.setHint(requireActivity().getString(R.string.cpf_string));
        binding.inputTextCpfCnpjField.addTextChangedListener(
                MaskEditUtil.mask(binding.inputTextCpfCnpjField, MaskEditUtil.FORMAT_CPF)
        );
    }

    private void setupInputTextToCNPJ() {
        userType = UserType.CNPJ;
        binding.inputTextCpfCnpjField.setHint(requireActivity().getString(R.string.cnpj_string));
        binding.inputTextCpfCnpjField.addTextChangedListener(
                MaskEditUtil.mask(binding.inputTextCpfCnpjField, MaskEditUtil.FORMAT_CNPJ)
        );
    }

    private void handleRegisterUserError(RegisterUserError registerUserError) {
        switch (registerUserError) {
            case USER_DOCUMENT_EMPTY:
                validateInputFieldsIsEmpty();
                break;
            case INVALID_CPF:
                binding.inputTextCpfCnpjField.setError(getString(R.string.invalid_cpf_error_hint));
                break;
            case INVALID_CNPJ:
                binding.inputTextCpfCnpjField.setError(getString(R.string.invalid_cnpj_error_hint));
                break;
        }
    }

    private void validateInputFieldsIsEmpty() {
        ComponentsUtils.validateIfIsEmptyTextWatcher(
                binding.inputTextCpfCnpjField,
                getString(R.string.required_field_error_string)
        );
    }
}