package com.petech.thomasgregchallenge.data.database.dao.util;

import android.content.ContentValues;

import com.petech.thomasgregchallenge.data.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserDAOUtil {
    public static ContentValues getUserValues(User user) {
        ContentValues values = new ContentValues();
        values.put(User.NAME_TAG, user.getName());
        values.put(User.NICK_TAG, user.getUserName());
        values.put(User.PASSWORD_TAG, user.getPassword());
        values.put(User.USER_IMAGE_TAG, user.getUserPhoto());
        values.put(User.ADDRESS_TAG, user.getAddress());
        values.put(User.EMAIL_TAG, user.getEmail());
        values.put(User.BORN_DATE_TAG, UserDAOUtil.convertDateToStringIso8601(user.getBirthDate()));
        values.put(User.GENDER_TAG, user.isGender());
        values.put(User.CPF_CNJP_TAG, user.getDocumentNumber());
        return values;
    }

    public static String convertDateToStringIso8601(LocalDate date) {
        return date.toString().concat("T00:00:01.000");
    }

    public static LocalDate convertStringIso8601ToLocalDate(String iso8601) {
        return LocalDateTime.parse(iso8601).toLocalDate();
    }
}
