package com.petech.thomasgregchallenge.data.datasource.implementation;

import android.util.Log;

import com.petech.thomasgregchallenge.data.database.dao.UserDAO;
import com.petech.thomasgregchallenge.data.datasource.UserRepository;
import com.petech.thomasgregchallenge.data.entities.User;
import com.petech.thomasgregchallenge.data.entities.enums.UserType;
import com.petech.thomasgregchallenge.utils.AppUtils;

import java.util.List;
import java.util.function.Consumer;

public class UserRepositoryImpl implements UserRepository {
    private final UserDAO userDAO;

    public UserRepositoryImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userDAO.getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            User currentUser = users.get(i);

            if (AppUtils.VALID_CPF_REGEX.matcher(currentUser.getDocumentNumber()).matches()) {
                currentUser.setUserType(UserType.CPF);
            }

            if (AppUtils.VALID_CNPJ_REGEX.matcher(currentUser.getDocumentNumber()).matches()) {
                currentUser.setUserType(UserType.CNPJ);
            }

            users.set(i, currentUser);
        }
        return users;
    }

    @Override
    public long createUser(User user) {
        long newId = userDAO.createUser(user);
        return newId;
    }

    @Override
    public int updateUser(User user) {
        return userDAO.updateUser(user);
    }

    @Override
    public int deleteUser(int userId) {
        int deletedId = userDAO.deleteUser(userId);
        return deletedId;
    }

    @Override
    public List<User> findUserBy(String tagColumn, String value) {
        List<User> users = userDAO.findUserBy(tagColumn, value);
        for (int i = 0; i < users.size(); i++) {
            User currentUser = users.get(i);

            if (AppUtils.VALID_CPF_REGEX.matcher(currentUser.getDocumentNumber()).matches()) {
                currentUser.setUserType(UserType.CPF);
            }

            if (AppUtils.VALID_CNPJ_REGEX.matcher(currentUser.getDocumentNumber()).matches()) {
                currentUser.setUserType(UserType.CNPJ);
            }

            users.set(i, currentUser);
        }
        return users;
    }

    @Override
    public void closeDatabase() {
        userDAO.closeDatabase();
    }
}
