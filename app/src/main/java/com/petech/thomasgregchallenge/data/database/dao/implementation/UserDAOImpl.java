package com.petech.thomasgregchallenge.data.database.dao.implementation;

import static com.petech.thomasgregchallenge.data.database.helper.AppDatabaseHelper.USER_TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.petech.thomasgregchallenge.data.database.dao.UserDAO;
import com.petech.thomasgregchallenge.data.database.dao.util.UserDAOUtil;
import com.petech.thomasgregchallenge.data.database.helper.AppDatabaseHelper;
import com.petech.thomasgregchallenge.data.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private final String[] columns = new String[]{
            "_id",
            "name",
            "nick_name",
            "password",
            "user_image",
            "address",
            "email",
            "born_date",
            "gender",
            "cpfcnpj"};

    private SQLiteDatabase database;

    public UserDAOImpl(Context context) {
        AppDatabaseHelper helper = new AppDatabaseHelper(context);
        try {
            database = helper.getWritableDatabase();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public long createUser(User user) {
        ContentValues contentUser = UserDAOUtil.getUserValues(user);

        return database.insertOrThrow(USER_TABLE_NAME, null, contentUser);
    }

    @Override
    public int updateUser(User user) {
        ContentValues contentUser = UserDAOUtil.getUserValues(user);

        return database.update(
                USER_TABLE_NAME,
                contentUser,
                "_id = ?",
                new String[]{Integer.toString(user.get_id())}
        );
    }

    @Override
    public int deleteUser(int userId) {
        return database.delete(
                USER_TABLE_NAME,
                "_id = ?",
                new String[]{Integer.toString(userId)}
        );
    }

    @Override
    public List<User> getAllUsers() {
        Cursor cursor = database.query(
                USER_TABLE_NAME,
                new String[]{"_id", "name", "nick_name", "password", "user_image", "address", "email", "born_date", "gender", "cpfcnpj"},
                null,
                null,
                null,
                null,
                null
        );
        List<User> usersList = new ArrayList<>();

        while (cursor.moveToNext()) {
            User userItem = new User.Builder()
                    .setId(cursor.getInt(0))
                    .setName(cursor.getString(1))
                    .setUserName(cursor.getString(2))
                    .setPassword(cursor.getString(3))
                    .setUserPhoto(cursor.getString(4))
                    .setAddress(cursor.getString(5))
                    .setEmail(cursor.getString(6))
                    .setBirthDate(UserDAOUtil.convertStringIso8601ToLocalDate(cursor.getString(7)))
                    .setGender(cursor.getInt(8) == 1)
                    .setDocumentNumber(cursor.getString(9))
                    .build();

            usersList.add(userItem);
        }

        cursor.close();
        return usersList;
    }

    @Override
    public void closeDatabase() {
        database.close();
    }

    @Override
    public List<User> findUserBy(String tagColumn, String value) {
        String selection = tagColumn + " = ?";
        String[] selectionArgs = {value};
        Cursor cursor = database.query(
                USER_TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        List<User> usersList = new ArrayList<>();


        while (cursor.moveToNext()) {
            User userItem = new User.Builder()
                    .setId(cursor.getInt(0))
                    .setName(cursor.getString(1))
                    .setUserName(cursor.getString(2))
                    .setPassword(cursor.getString(3))
                    .setUserPhoto(cursor.getString(4))
                    .setAddress(cursor.getString(5))
                    .setEmail(cursor.getString(6))
                    .setBirthDate(UserDAOUtil.convertStringIso8601ToLocalDate(cursor.getString(7)))
                    .setGender(cursor.getInt(8) == 1)
                    .setDocumentNumber(cursor.getString(9))
                    .build();

            usersList.add(userItem);
        }

        cursor.close();
        return usersList;
    }


}