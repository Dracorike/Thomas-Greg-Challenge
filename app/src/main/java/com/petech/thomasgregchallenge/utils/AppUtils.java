package com.petech.thomasgregchallenge.utils;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.petech.thomasgregchallenge.R;
import com.petech.thomasgregchallenge.data.database.dao.UserDAO;
import com.petech.thomasgregchallenge.data.database.dao.implementation.UserDAOImpl;
import com.petech.thomasgregchallenge.data.datasource.UserRepository;
import com.petech.thomasgregchallenge.data.datasource.implementation.UserRepositoryImpl;
import com.petech.thomasgregchallenge.ui.components.warningbox.WarningBox;
import com.petech.thomasgregchallenge.ui.components.warningbox.WarningBoxAttributes;
import com.petech.thomasgregchallenge.ui.register.model.RegisterUserModel;
import com.petech.thomasgregchallenge.ui.register.model.implementation.RegisterUserModelImpl;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserViewModelFactory;

import java.util.regex.Pattern;

public class AppUtils {
    private static final int DEFAULT_DATE_LENGTH = 10;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_CPF_REGEX =
            Pattern.compile("[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}");

    public static final Pattern VALID_CNPJ_REGEX =
            Pattern.compile("[0-9]{2}\\.?[0-9]{3}\\.?[0-9]{3}\\/?[0-9]{4}\\-?[0-9]{2}");

    public static final Pattern VALID_PASSWORD =
            Pattern.compile("^(?=.*[A-Z])(?=.*\\d).{8,}$");

    public static RegisterUserViewModelFactory getRegisterUserViewModel(Context context) {
        UserDAO userDAO = new UserDAOImpl(context);
        UserRepository userRepository = new UserRepositoryImpl(userDAO);
        RegisterUserModel userModel = new RegisterUserModelImpl(userRepository);
        return new RegisterUserViewModelFactory(userModel);
    }

    public static void showError(Context context, String msg, String tag, FragmentManager fragmentManager) {
        WarningBoxAttributes attributes = new WarningBoxAttributes(
                context.getString(R.string.warning_box_title_default),
                msg,
                context.getString(R.string.okay_string),
                null
        );
        WarningBox warningBox = WarningBox.newInstance(attributes);
        warningBox.show(fragmentManager, tag);
    }

    public static String formatDateToParse(String date) {
        if (date.length() < DEFAULT_DATE_LENGTH) {
            return "";
        }

        String[] dateSplit = date.split("/");
        return dateSplit[2] + "-" + dateSplit[1] + "-" + dateSplit[0];
    }
}
