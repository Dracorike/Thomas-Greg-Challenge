package com.petech.thomasgregchallenge.ui.register.model.implementation;

import com.petech.thomasgregchallenge.data.datasource.UserRepository;
import com.petech.thomasgregchallenge.data.entities.User;
import com.petech.thomasgregchallenge.data.entities.enums.UserType;
import com.petech.thomasgregchallenge.ui.register.model.RegisterUserModel;

import java.time.LocalDate;
import java.util.List;

public class RegisterUserModelImpl implements RegisterUserModel {
    private final User.Builder newRegistration = new User.Builder();
    private final UserRepository userRepository;

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
        long newId = userRepository.createUser(newRegistration.build());
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
}
