package com.petech.thomasgregchallenge.ui.register.viewmodel;

import com.petech.thomasgregchallenge.data.entities.enums.UserType;
import com.petech.thomasgregchallenge.utils.AppUtils;

import java.time.LocalDate;

public class RegisterUserViewModelUtils {
    public static boolean userDataIsInvalid(String photo, String name, String userName, String email) {
        return photo.isEmpty() || name.isEmpty() || userName.isEmpty() || email.isEmpty();
    }

    public static boolean validateUserAge(LocalDate birthDate) {
        LocalDate birthEighteen = LocalDate.now().minusYears(18);
        return birthEighteen.isAfter(birthDate);
    }

    public static boolean validateCPForCNPJ(String document, UserType userType) {
        if (userType == UserType.CPF) {
            return AppUtils.VALID_CPF_REGEX.matcher(document).matches();
        }

        if (userType == UserType.CNPJ) {
            return AppUtils.VALID_CNPJ_REGEX.matcher(document).matches();
        }

        return false;
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
            case REGISTER_USER_SUCCESS:
            default:
                return RegisterUserSteps.REGISTER_USER_SUCCESS;

        }
    }

    public static RegisterUserSteps previousStep(RegisterUserSteps registerUserSteps) {
        switch (registerUserSteps) {
            case REGISTER_USER_DATA:
            case REGISTER_USER_DETAILS:
            default:
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
