package com.petech.thomasgregchallenge.ui.update.view.utils;

import com.petech.thomasgregchallenge.data.entities.User;
import com.petech.thomasgregchallenge.data.entities.enums.UserType;
import com.petech.thomasgregchallenge.databinding.FragmentUserDetailsBinding;
import com.petech.thomasgregchallenge.utils.AppUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ViewUtil {

    public static User getUserByView(FragmentUserDetailsBinding binding, String imagePath) {
        LocalDate birthDate = null;
        Boolean isGender = null;
        UserType userType = null;

        try {
            birthDate = LocalDate.parse(
                    AppUtils.formatDateToParse(binding.inputTextBirthDateUpdate.getText().toString())
            );
        } catch (DateTimeParseException exception) {
            exception.printStackTrace();
        }

        if (binding.radioOptionManUpdate.isChecked()) {
            isGender = true;
        }

        if (binding.radioOptionWomanUpdate.isChecked()) {
            isGender = false;
        }

        if (binding.radioOptionCpfUpdate.isChecked()) {
            userType = UserType.CPF;
        }

        if (binding.radioOptionCnpjUpdate.isChecked()) {
            userType = UserType.CNPJ;
        }

        return new User.Builder()
                .setUserPhoto(imagePath)
                .setName(binding.inputTextFullNameUpdate.getText().toString())
                .setUserName(binding.inputTextNicknameUpdate.getText().toString())
                .setEmail(binding.inputTextEmailUpdate.getText().toString())
                .setAddress(binding.inputTextAddressUpdate.getText().toString())
                .setBirthDate(birthDate)
                .setGender(isGender)
                .setUserType(userType)
                .setDocumentNumber(binding.inputTextCpfCnpjUpdate.getText().toString())
                .build();
    }
}
