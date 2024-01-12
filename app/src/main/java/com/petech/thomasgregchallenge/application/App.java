package com.petech.thomasgregchallenge.application;

import android.app.Application;

import com.petech.thomasgregchallenge.data.database.dao.UserDAO;
import com.petech.thomasgregchallenge.data.database.dao.implementation.UserDAOImpl;
import com.petech.thomasgregchallenge.data.datasource.UserRepository;
import com.petech.thomasgregchallenge.data.datasource.implementation.UserRepositoryImpl;
import com.petech.thomasgregchallenge.ui.register.model.RegisterUserModel;
import com.petech.thomasgregchallenge.ui.register.model.implementation.RegisterUserModelImpl;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserViewModelFactory;

public class App extends Application {
    public RegisterUserViewModelFactory getRegisterUserViewModel() {
        UserDAO userDAO = new UserDAOImpl(getApplicationContext());
        UserRepository userRepository = new UserRepositoryImpl(userDAO);
        RegisterUserModel userModel = new RegisterUserModelImpl(userRepository);
        return new RegisterUserViewModelFactory(userModel);
    }

}
