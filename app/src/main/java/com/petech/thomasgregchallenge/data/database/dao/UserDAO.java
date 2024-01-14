package com.petech.thomasgregchallenge.data.database.dao;

import com.petech.thomasgregchallenge.data.entities.User;

import java.util.List;

public interface UserDAO {
    long createUser(User user);
    int updateUser(User user);
    int deleteUser(int userId);
    List<User> getAllUsers();
    void closeDatabase();
    List<User> findUserBy(String tagColumn, String value);
}
