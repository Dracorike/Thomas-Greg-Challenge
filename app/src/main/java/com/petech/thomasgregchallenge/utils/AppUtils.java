package com.petech.thomasgregchallenge.utils;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.petech.thomasgregchallenge.R;
import com.petech.thomasgregchallenge.ui.components.warningbox.WarningBox;
import com.petech.thomasgregchallenge.ui.components.warningbox.WarningBoxAttributes;

import java.util.regex.Pattern;

public class AppUtils {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_CPF_REGEX =
            Pattern.compile("[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}");

    public static final Pattern VALID_CNPJ_REGEX =
            Pattern.compile("[0-9]{2}\\.?[0-9]{3}\\.?[0-9]{3}\\/?[0-9]{4}\\-?[0-9]{2}");

    public static final Pattern VALID_PASSWORD =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

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

}
