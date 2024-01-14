package com.petech.thomasgregchallenge.ui.register.viewmodel;

import com.petech.thomasgregchallenge.data.entities.enums.UserType;

import java.time.LocalDate;

public class RegisterUserViewModelUtils {
    public static boolean userDataIsInvalid(String photo, String name, String userName, String email) {
        if (photo != null) {
            photo = photo.trim();
        }
        if (name != null) {
            name = name.trim();
        }

        if (userName != null) {
            userName = userName.trim();
        }

        if (email != null) {
            email = email.trim();
        }

        return photo == null || name == null || userName == null || email == null
                || photo.isEmpty() || photo.equals(" ") || name.isEmpty() || name.equals(" ")
                || userName.isEmpty() || userName.equals(" ") || email.isEmpty() || email.equals(" ");
    }

    public static boolean userDetailsIsNull(String address, LocalDate birthDate, Boolean gender) {
        if (address != null) {
            address = address.trim();
        }else {
            return true;
        }
        return address.isEmpty() || birthDate == null || gender == null;
    }

    public static boolean userDocumentIsNull(String document, UserType userType) {
        return document.isEmpty() || userType == null;
    }

    public static boolean userPasswordIsNull(String password, String confirmPassword) {
        return password.isEmpty() || confirmPassword.isEmpty();
    }

    public static boolean validateUserAge(LocalDate birthDate) {
        LocalDate birthEighteen = LocalDate.now().minusYears(18);
        return birthEighteen.isAfter(birthDate);
    }

    public static RegisterUserSteps nextStep(RegisterUserSteps registerUserSteps) {
        switch (registerUserSteps) {
            case REGISTER_USER_DATA:
                return RegisterUserSteps.REGISTER_USER_DETAILS;
            case REGISTER_USER_DETAILS:
                return RegisterUserSteps.REGISTER_USER_DOCUMENTS;
            case REGISTER_USER_DOCUMENTS:
                return RegisterUserSteps.REGISTER_USER_PASSWORD;
            case REGISTER_USER_PASSWORD:
                return RegisterUserSteps.REGISTER_USER_SUCCESS;
            case REGISTER_USER_SUCCESS:
            default:
                return RegisterUserSteps.EXIT;
        }
    }

    public static RegisterUserSteps previousStep(RegisterUserSteps registerUserSteps) {
        switch (registerUserSteps) {
            case REGISTER_USER_DATA:
            default:
                return RegisterUserSteps.EXIT;
            case REGISTER_USER_DETAILS:
                return RegisterUserSteps.REGISTER_USER_DATA;
            case REGISTER_USER_DOCUMENTS:
                return RegisterUserSteps.REGISTER_USER_DETAILS;
            case REGISTER_USER_PASSWORD:
                return RegisterUserSteps.REGISTER_USER_DOCUMENTS;
            case REGISTER_USER_SUCCESS:
                return RegisterUserSteps.REGISTER_USER_SUCCESS;
        }
    }
}
