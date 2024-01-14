package com.petech.thomasgregchallenge.data.datasource.implementation;

import com.petech.thomasgregchallenge.data.database.dao.UserDAO;
import com.petech.thomasgregchallenge.data.datasource.UserRepository;
import com.petech.thomasgregchallenge.data.entities.User;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private final UserDAO userDAO;

    public UserRepositoryImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public long createUser(User user) {
        return userDAO.createUser(user);
    }

    @Override
    public int updateUser(User user) {
        return userDAO.updateUser(user);
    }

    @Override
    public int deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }

    @Override
    public List<User> findUserBy(String tagColumn, String value) {
        return userDAO.findUserBy(tagColumn, value);
    }

    @Override
    public void closeDatabase() {
        userDAO.closeDatabase();
    }
}
