package com.petech.thomasgregchallenge.ui.register.view;

import android.os.Bundle;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.petech.thomasgregchallenge.R;
import com.petech.thomasgregchallenge.databinding.ActivityRegisterUserBinding;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserError;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserSteps;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserViewModel;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserViewModelFactory;
import com.petech.thomasgregchallenge.utils.AppUtils;

public class RegisterUserActivity extends AppCompatActivity {
    private static final String TAG = "RegisterUserActivity: ";
    private static final String WARNING_TAG = TAG + "warning-box";

    private ActivityRegisterUserBinding binding;
    private RegisterUserViewModel viewModel;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupViewModel();
        setupNavController();
        setupObservables();
        setupClicks();
    }

    private void setupViewModel() {
        RegisterUserViewModelFactory factory = AppUtils.getRegisterUserViewModel(getApplicationContext());
        viewModel = new ViewModelProvider(this, factory).get(RegisterUserViewModel.class);
    }

    private void setupNavController() {
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_host);
        navController = navHostFragment.getNavController();
    }

    private void setupObservables() {
        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                progressBarToggle(isLoading);
            }
        });

        viewModel.getRegisterError().observe(this, new Observer<RegisterUserError>() {
            @Override
            public void onChanged(RegisterUserError registerUserError) {
                handleViewModelErrors(registerUserError);
            }
        });

        viewModel.getRegisterUserStep().observe(this, new Observer<RegisterUserSteps>() {
            @Override
            public void onChanged(RegisterUserSteps registerUserSteps) {
                handleRegisterSteps(registerUserSteps);
            }
        });
    }

    private void setupClicks() {
        binding.toolbarRegisterUser.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.backNavStack();
            }
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                viewModel.backNavStack();
            }
        });
    }

    private void progressBarToggle(boolean isLoading) {
        int visibility = isLoading ? View.VISIBLE : View.GONE;
        binding.progressBarRegisterUserActivity.setVisibility(visibility);
    }

    private void handleViewModelErrors(RegisterUserError registerUserError) {
        switch (registerUserError) {
            case USER_DETAILS_EMPTY:
            case USER_DOCUMENT_EMPTY:
            case USER_PASSWORD_EMPTY:
            case DATA_FIELD_EMPTY:
                showError(getString(R.string.data_field_error_message));
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
                showError(getString(R.string.user_under_age_error_message));
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
            case PASSWORD_DONT_MATCHES:
                showError(getString(R.string.password_dont_matches_error_message));
                break;
            case REGISTRATION_ERROR:
                showError(getString(R.string.registration_error_message));
                break;
            case UNKNOWN_ERROR:
                showError(getString(R.string.unknown_error_message));
                break;
        }
    }

    private void showError(String msg) {
        AppUtils.showError(this, msg, WARNING_TAG, getSupportFragmentManager());
    }

    private void handleRegisterSteps(RegisterUserSteps userStep) {
        switch (userStep) {
            case REGISTER_USER_DATA:
                navController.navigate(R.id.inputUserDataFragment);
                break;
            case REGISTER_USER_DETAILS:
                navController.navigate(R.id.inputUserDetailsFragment);
                break;
            case REGISTER_USER_DOCUMENTS:
                navController.navigate(R.id.inputUserDocumentFragment);
                break;
            case REGISTER_USER_PASSWORD:
                navController.navigate(R.id.inputUserPasswordFragment);
                break;
            case REGISTER_USER_SUCCESS:
                navController.navigate(R.id.registrationUserSuccessFragment);
                break;
            case EXIT:
                finish();
                break;
        }
    }
}