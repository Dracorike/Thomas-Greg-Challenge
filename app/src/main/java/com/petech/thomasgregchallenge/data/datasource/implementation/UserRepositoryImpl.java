package com.petech.thomasgregchallenge.data.datasource.implementation;

import android.util.Log;

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
        List<User> users = userDAO.getAllUsers();
        Log.i("TAG", "usuários: " + users);
        return users;
    }

    @Override
    public long createUser(User user) {
        long newId = userDAO.createUser(user);
        Log.i("TAG", "Novo id: " + newId);

        return newId;
    }

    @Override
    public int updateUser(User user) {
        return userDAO.updateUser(user);
    }

    @Override
    public int deleteUser(int userId) {
        int deletedId = userDAO.deleteUser(userId);
        Log.i("TAG", "Id DEletado: " + deletedId);
        return deletedId;
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
