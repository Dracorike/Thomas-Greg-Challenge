package com.petech.thomasgregchallenge.ui.register.viewmodel;

import android.graphics.Bitmap;
import android.util.Base64;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.petech.thomasgregchallenge.data.entities.enums.UserType;
import com.petech.thomasgregchallenge.ui.register.model.RegisterUserModel;
import com.petech.thomasgregchallenge.utils.AppUtils;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

public class RegisterUserViewModel extends ViewModel {
    private final MutableLiveData<RegisterUserError> registerError = new MutableLiveData<>();
    private final MutableLiveData<RegisterUserSteps> registerUserStep = new MutableLiveData<>(RegisterUserSteps.REGISTER_USER_DATA);
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final RegisterUserModel model;

    public RegisterUserViewModel(RegisterUserModel model) {
        this.model = model;
    }

    public void inputUserData(String photo, String name, String userName, String email) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                storeUserData(photo, name, userName, email);
            }
        }).start();
    }

    public void inputUserDetails(String address, LocalDate birthDate, Boolean gender) {
        if (RegisterUserViewModelUtils.userDetailsIsNull(address, birthDate, gender)) {
            registerError.postValue(RegisterUserError.USER_DETAILS_EMPTY);
            return;
        }
        if (!RegisterUserViewModelUtils.validateUserAge(birthDate)) {
            registerError.postValue(RegisterUserError.USER_UNDER_AGE);
            return;
        }

        model.inputUserDetails(address, birthDate, gender);
        registerUserStep.postValue(RegisterUserViewModelUtils.nextStep(registerUserStep.getValue()));
    }

    public void inputUserDocument(String document, UserType userType) {
        if (RegisterUserViewModelUtils.userDocumentIsNull(document, userType)) {
            registerError.postValue(RegisterUserError.USER_DOCUMENT_EMPTY);
            return;
        }

        if (userType == UserType.CPF && !AppUtils.VALID_CPF_REGEX.matcher(document).matches()) {
            registerError.postValue(RegisterUserError.INVALID_CPF);
            return;
        }

        if (userType == UserType.CNPJ && !AppUtils.VALID_CNPJ_REGEX.matcher(document).matches()) {
            registerError.postValue(RegisterUserError.INVALID_CNPJ);
            return;
        }

        model.inputUserDocuments(document, userType);
        registerUserStep.postValue(RegisterUserViewModelUtils.nextStep(registerUserStep.getValue()));
    }

    public void inputUserPassword(String password, String confirmPassword) {
        isLoading.postValue(true);
        if (RegisterUserViewModelUtils.userPasswordIsNull(password, confirmPassword)) {
            registerError.postValue(RegisterUserError.USER_PASSWORD_EMPTY);
            isLoading.postValue(false);
            return;
        }

        if (!password.equals(confirmPassword)) {
            registerError.postValue(RegisterUserError.PASSWORD_DONT_MATCHES);
            isLoading.postValue(false);
            return;
        }

        if (!AppUtils.VALID_PASSWORD.matcher(password).matches()) {
            registerError.postValue(RegisterUserError.INVALID_PASSWORD);
            isLoading.postValue(false);
            return;
        }

        model.inputUserPassword(password);
        saveNewUser();
    }

    public void backNavStack() {
        if (registerUserStep.getValue() != null) {
            registerUserStep.postValue(
                    RegisterUserViewModelUtils.previousStep(registerUserStep.getValue())
            );
        }
    }

    public String encodeImageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void storeUserData(String photo, String name, String userName, String email) {
        try {
            isLoading.postValue(true);
            if (!validateInputUserData(photo, name, userName, email)) {
                return;
            }

            model.inputUserData(photo, name, userName, email);
            registerUserStep.postValue(
                    RegisterUserViewModelUtils.nextStep(registerUserStep.getValue())
            );
        } catch (Exception exception) {
            exception.printStackTrace();
            registerError.postValue(RegisterUserError.UNKNOWN_ERROR);
        } finally {
            isLoading.postValue(false);
        }
    }

    private void saveNewUser() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean isNewUserCreated = model.finishRegistration();
                    if (isNewUserCreated) {
                        registerUserStep.postValue(RegisterUserViewModelUtils.nextStep(registerUserStep.getValue()));
                    } else {
                        registerError.postValue(RegisterUserError.REGISTRATION_ERROR);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                    registerError.postValue(RegisterUserError.UNKNOWN_ERROR);
                } finally {
                    isLoading.postValue(false);
                }
            }
        });

    }

    private boolean validateInputUserData(String photo, String name, String userName, String email) {
        if (RegisterUserViewModelUtils.userDataIsInvalid(photo, name, userName, email)) {
            registerError.postValue(RegisterUserError.DATA_FIELD_EMPTY);
            return false;
        }

        if (name.length() < 30) {
            registerError.postValue(RegisterUserError.NAME_MUST_BE_COMPLETE);
            return false;
        }

        if (model.isUserNameExists(userName)) {
            registerError.postValue(RegisterUserError.USER_NAME_ALREADY_EXISTS);
            return false;
        }

        if (!AppUtils.VALID_EMAIL_ADDRESS_REGEX.matcher(email).matches()) {
            registerError.postValue(RegisterUserError.EMAIL_INVALID);
            return false;
        }

        return true;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        model.closeDatabase();
    }

    public LiveData<RegisterUserError> getRegisterError() {
        return registerError;
    }

    public LiveData<RegisterUserSteps> getRegisterUserStep() {
        return registerUserStep;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}
