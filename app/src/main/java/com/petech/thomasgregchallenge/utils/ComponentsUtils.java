package com.petech.thomasgregchallenge.utils;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputEditText;

public class ComponentsUtils {

    public static void dismissInputErrorTextWatcher(TextInputEditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editText.setError(null);
            }
        });
    }

    public static void validateIfIsEmptyTextWatcher(TextInputEditText editText, String errorMsg) {
        if (editText.getText().toString().isEmpty()) {
            editText.setError(errorMsg);
        }
    }
}
