package com.petech.thomasgregchallenge.ui.register.viewmodel;

import java.time.LocalDate;

public class RegisterUserViewModelUtils {
    public static boolean userDataIsInvalid(String photo, String name, String userName, String email) {
        return photo.isEmpty() || name.isEmpty() || userName.isEmpty() || email.isEmpty();
    }

    public static boolean userDetailsIsNull(String address, LocalDate birthDate, Boolean gender) {
        return address.isEmpty() || birthDate == null || gender == null;
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
