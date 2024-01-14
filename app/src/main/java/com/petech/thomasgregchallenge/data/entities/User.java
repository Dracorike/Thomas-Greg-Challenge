package com.petech.thomasgregchallenge.data.entities;

import com.petech.thomasgregchallenge.data.entities.enums.UserType;

import java.time.LocalDate;

public class User {
    public static final String NAME_TAG = "name";
    public static final String USER_NAME_TAG = "nick_name";
    public static final String PASSWORD_TAG = "password";
    public static final String USER_IMAGE_TAG = "user_image";
    public static final String ADDRESS_TAG = "address";
    public static final String EMAIL_TAG = "email";
    public static final String BORN_DATE_TAG = "born_date";
    public static final String GENDER_TAG = "gender";
    public static final String CPF_CNJP_TAG = "cpfcnpj";

    private int _id;
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

    public User(Builder builder) {
        this._id = builder._id;
        this.name = builder.name;
        this.userName = builder.userName;
        this.password = builder.password;
        this.userPhoto = builder.userPhoto;
        this.address = builder.address;
        this.email = builder.email;
        this.birthDate = builder.birthDate;
        this.gender = builder.gender;
        this.userType = builder.userType;
        this.documentNumber = builder.documentNumber;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public static class Builder {
        private int _id;
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

        public Builder setId(int _id) {
            this._id = _id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setUserPhoto(String userPhoto) {
            this.userPhoto = userPhoto;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder setGender(boolean gender) {
            this.gender = gender;
            return this;
        }

        public Builder setUserType(UserType userType) {
            this.userType = userType;
            return this;
        }

        public Builder setDocumentNumber(String documentNumber) {
            this.documentNumber = documentNumber;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}