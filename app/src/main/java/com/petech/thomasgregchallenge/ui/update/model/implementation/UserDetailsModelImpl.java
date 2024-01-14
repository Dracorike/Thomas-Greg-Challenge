package com.petech.thomasgregchallenge.ui.update.model.implementation;

import com.petech.thomasgregchallenge.data.datasource.UserRepository;
import com.petech.thomasgregchallenge.data.entities.User;
import com.petech.thomasgregchallenge.ui.update.model.UserDetailsModel;

import java.util.List;

public class UserDetailsModelImpl implements UserDetailsModel {
    private final UserRepository userRepository;
    private User selectedUser = null;

    public UserDetailsModelImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void findUserById(int userId) {
        List<User> found = userRepository.findUserBy(User.ID_TAG, Integer.toString(userId));
        selectedUser = found.get(0);
    }

    @Override
    public User getUser() {
        return selectedUser;
    }
}
