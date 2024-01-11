package com.petech.thomasgregchallenge.data.entities;

import com.petech.thomasgregchallenge.data.entities.enums.UserType;

import java.time.LocalDate;

public class User {
    private String name;
    private String userName;
    private String password;
    private String userPhoto;
    private String address;
    private String email;
    private LocalDate birthDate;
    private boolean gender;
    private UserType userType;
    private String documentNumber;
}