package com.petech.thomasgregchallenge.ui.update.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.petech.thomasgregchallenge.data.entities.User;
import com.petech.thomasgregchallenge.data.entities.enums.UserType;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserError;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserViewModelUtils;
import com.petech.thomasgregchallenge.utils.AppUtils;

public class UserDetailsViewModelUtils extends RegisterUserViewModelUtils {
    public static boolean validationsToUpdateUser(
            User user,
            boolean validateInputUserData,
            MutableLiveData<RegisterUserError> registerError
    ) {
        if (!validateInputUserData) {
            return false;
        }

        if (userDetailsIsNull(
                user.getAddress(),
                user.getBirthDate(),
                user.isGender()
        )) {
            registerError.postValue(RegisterUserError.USER_DETAILS_EMPTY);
            return false;
        }

        if (!validateUserAge(user.getBirthDate())) {
            registerError.postValue(RegisterUserError.USER_UNDER_AGE);
            return false;
        }

        if (userDocumentIsNull(
                user.getDocumentNumber(),
                user.getUserType()
        )) {
            registerError.postValue(RegisterUserError.USER_DOCUMENT_EMPTY);
            return false;
        }

        if (
                user.getUserType() == UserType.CPF
                        && !AppUtils.VALID_CPF_REGEX
                        .matcher(user.getDocumentNumber())
                        .matches()
        ) {
            registerError.postValue(RegisterUserError.INVALID_CPF);
            return false;
        }

        if (
                user.getUserType() == UserType.CNPJ
                        && !AppUtils.VALID_CNPJ_REGEX
                        .matcher(user.getDocumentNumber())
                        .matches()
        ) {
            registerError.postValue(RegisterUserError.INVALID_CNPJ);
            return false;
        }

        return true;
    }

    public static boolean userDataIsInvalid(User user) {
        String photo = null;
        String name = null;
        String userName = null;
        String email = null;

        if (user.getUserPhoto() != null) {
            photo = user.getUserPhoto().trim();
        }
        if (user.getName() != null) {
            name = user.getName().trim();
        }

        if (user.getUserName() != null) {
            userName = user.getUserName().trim();
        }

        if (user.getEmail() != null) {
            email = user.getEmail().trim();
        }

        return photo == null || name == null || userName == null || email == null
                || photo.isEmpty() || photo.equals(" ") || name.isEmpty() || name.equals(" ")
                || userName.isEmpty() || userName.equals(" ") || email.isEmpty() || email.equals(" ");
    }
}
