package com.petech.thomasgregchallenge.ui.update.model;

import com.petech.thomasgregchallenge.data.entities.User;

public interface UserDetailsModel {
    void findUserById(int userId);
    User getUser();
}
