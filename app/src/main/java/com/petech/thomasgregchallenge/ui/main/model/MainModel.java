package com.petech.thomasgregchallenge.ui.main.model;

import com.petech.thomasgregchallenge.data.entities.User;

import java.util.List;

public interface MainModel {
    List<User> getAllUsers();
    int deleteUserById(int userId);
}
