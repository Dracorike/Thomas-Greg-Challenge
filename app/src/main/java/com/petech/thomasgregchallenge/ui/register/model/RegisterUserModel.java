package com.petech.thomasgregchallenge.ui.register.model;

import com.petech.thomasgregchallenge.data.entities.enums.UserType;

import java.time.LocalDate;

public interface RegisterUserModel {
    void inputUserData(String photo, String name, String userName, String email);

    void inputUserDetails(String address, LocalDate birthDate, boolean gender);

    void inputUserDocuments(String document, UserType userType);

    void inputUserPassword(String password);

    boolean finishRegistration();

    void closeDatabase();

    boolean isUserNameExists(String userName);
}
