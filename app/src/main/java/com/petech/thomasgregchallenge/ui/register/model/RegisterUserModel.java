package com.petech.thomasgregchallenge.ui.register.model;

import com.petech.thomasgregchallenge.data.entities.User;
import com.petech.thomasgregchallenge.data.entities.enums.UserType;

import java.io.IOException;
import java.time.LocalDate;

import retrofit2.Response;

public interface RegisterUserModel {
    void inputUserData(String photo, String name, String userName, String email);
    void inputUserProfileBase64(String imageBase64);
    void inputUserDetails(String address, LocalDate birthDate, boolean gender);
    void inputUserDocuments(String document, UserType userType);
    void inputUserPassword(String password);
    boolean finishRegistration();
    void closeDatabase();
    boolean isUserNameExists(String userName);
    Response<Void> sendRegistrationToApi() throws IOException;
}
