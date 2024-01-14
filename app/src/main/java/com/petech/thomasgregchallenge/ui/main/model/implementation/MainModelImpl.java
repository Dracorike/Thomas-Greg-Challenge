package com.petech.thomasgregchallenge.ui.main.model.implementation;

import com.petech.thomasgregchallenge.data.datasource.UserRepository;
import com.petech.thomasgregchallenge.data.entities.User;
import com.petech.thomasgregchallenge.ui.main.model.MainModel;

import java.util.List;

public class MainModelImpl implements MainModel {
    private final UserRepository userRepository;

    public MainModelImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public int deleteUserById(int userId) {
        return userRepository.deleteUser(userId);
    }

    @Override
    public void closeDatabase() {
        userRepository.closeDatabase();
    }
}
