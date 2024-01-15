package com.petech.thomasgregchallenge.ui.register.model.implementation;

import android.util.Log;

import com.petech.thomasgregchallenge.data.datasource.UserRepository;
import com.petech.thomasgregchallenge.data.entities.User;
import com.petech.thomasgregchallenge.data.entities.enums.UserType;
import com.petech.thomasgregchallenge.ui.register.model.RegisterUserModel;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import retrofit2.Response;

public class RegisterUserModelImpl implements RegisterUserModel {
    private final User.Builder newRegistration = new User.Builder();
    private final UserRepository userRepository;
    private String imageBase64 = "";

    public RegisterUserModelImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void inputUserData(String photo, String name, String userName, String email) {
        newRegistration.setUserPhoto(photo)
                .setName(name)
                .setUserName(userName)
                .setEmail(email);
    }

    @Override
    public void inputUserProfileBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    @Override
    public void inputUserDetails(String address, LocalDate birthDate, boolean gender) {
        newRegistration.setAddress(address)
                .setBirthDate(birthDate)
                .setGender(gender);
    }

    @Override
    public void inputUserDocuments(String document, UserType userType) {
        newRegistration.setDocumentNumber(document)
                .setUserType(userType);
    }

    @Override
    public void inputUserPassword(String password) {
        newRegistration.setPassword(password);
    }

    @Override
    public boolean finishRegistration() {
        long newId;
        try {
            Response<Void> response = sendRegistrationToApi();
            Log.i("TAG", "response: code=" + response.code() + ", message= " + response.message());
            if (response.code() >= 400) {
                return false;
            }

            newId = userRepository.createUser(newRegistration.build());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return newId != -1;
    }

    @Override
    public void closeDatabase() {
        userRepository.closeDatabase();
    }

    @Override
    public boolean isUserNameExists(String userName) {
        List<User> usersList = userRepository.findUserBy(User.USER_NAME_TAG, userName);
        return usersList.size() > 0;
    }

    @Override
    public Response<Void> sendRegistrationToApi() throws IOException {
        return userRepository.sendUserToApi(newRegistration.build(), imageBase64).execute();
    }
}
