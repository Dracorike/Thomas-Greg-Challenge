package com.petech.thomasgregchallenge.data.datasource;

import com.petech.thomasgregchallenge.data.entities.User;

import java.util.List;

import retrofit2.Call;

public interface UserRepository {
    List<User> getAllUsers();
    long createUser(User user);
    int updateUser(User user);
    int deleteUser(int userId);
    List<User> findUserBy(String tagColumn, String value);
    void closeDatabase();
    Call<Void> sendUserToApi(User user, String imageBase64);
}
