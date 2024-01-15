package com.petech.thomasgregchallenge.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import androidx.fragment.app.FragmentManager;

import com.petech.thomasgregchallenge.R;
import com.petech.thomasgregchallenge.data.database.dao.UserDAO;
import com.petech.thomasgregchallenge.data.database.dao.implementation.UserDAOImpl;
import com.petech.thomasgregchallenge.data.datasource.UserRepository;
import com.petech.thomasgregchallenge.data.datasource.implementation.UserRepositoryImpl;
import com.petech.thomasgregchallenge.network.NetworkService;
import com.petech.thomasgregchallenge.network.services.TestAvatyApiService;
import com.petech.thomasgregchallenge.ui.components.warningbox.WarningBox;
import com.petech.thomasgregchallenge.ui.components.warningbox.WarningBoxAttributes;
import com.petech.thomasgregchallenge.ui.main.model.MainModel;
import com.petech.thomasgregchallenge.ui.main.model.implementation.MainModelImpl;
import com.petech.thomasgregchallenge.ui.main.viewmodel.MainViewModelFactory;
import com.petech.thomasgregchallenge.ui.register.model.RegisterUserModel;
import com.petech.thomasgregchallenge.ui.register.model.implementation.RegisterUserModelImpl;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserViewModelFactory;
import com.petech.thomasgregchallenge.ui.update.model.UserDetailsModel;
import com.petech.thomasgregchallenge.ui.update.model.implementation.UserDetailsModelImpl;
import com.petech.thomasgregchallenge.ui.update.viewmodel.UserDetailsViewModel;
import com.petech.thomasgregchallenge.ui.update.viewmodel.UserDetailsViewModelFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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
        RegisterUserModel userModel = new RegisterUserModelImpl(getUserRepository(context));
        return new RegisterUserViewModelFactory(userModel);
    }

    public static MainViewModelFactory getMainViewModel(Context context) {
        MainModel mainModel = new MainModelImpl(getUserRepository(context));
        return new MainViewModelFactory(mainModel);
    }

    public static UserDetailsViewModelFactory getUserDetailsViewModel(Context context) {
        UserDetailsModel userDetailsModel = new UserDetailsModelImpl(getUserRepository(context));
        return new UserDetailsViewModelFactory(userDetailsModel);
    }

    public static UserRepository getUserRepository(Context context) {
        UserDAO userDAO = new UserDAOImpl(context);
        TestAvatyApiService service = NetworkService.getTestAvatyApiService();
        return new UserRepositoryImpl(userDAO, service);
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

    public static String formateLocalDateToString(LocalDate date) {
        String[] dateSplit = date.format(DateTimeFormatter.ISO_LOCAL_DATE).split("-");
        return dateSplit[2] + "/" + dateSplit[1] + "/" + dateSplit[0];
    }

    public static String encodeImageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap decodeBase64ToImage(String base64) {
        byte[] decodedImage = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
    }

    public static Uri getImageUri(String imagePath) {
        Uri userPhotoUri = null;
        try {
            File file = new File(imagePath);

            if (file.exists()) {
                userPhotoUri = Uri.fromFile(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userPhotoUri;
    }
}
