package com.petech.thomasgregchallenge.ui.components.warningbox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.petech.thomasgregchallenge.databinding.WarningBoxLayoutBinding;

public class WarningBox extends DialogFragment {
    public static final String TITLE_WARNING_TAG = "WarningBox: title-warning";
    public static final String BODY_WARNING_TAG = "WarningBox: body-warning";
    public static final String POSITIVE_BUTTON_WARNING_TAG = "WarningBox: positive-button";
    public static final String NEGATIVE_BUTTON_WARNING_TAG = "WarningBox: negative-button";

    private final WarningBoxClicks warningBoxClicks;
    private WarningBoxLayoutBinding binding;

    private WarningBox(WarningBoxClicks warningBoxClicks) {
        this.warningBoxClicks = warningBoxClicks;
    }

    public static WarningBox newInstance(WarningBoxClicks warningBoxClicks, WarningBoxAttributes attributes) {
        WarningBox warningBox = new WarningBox(warningBoxClicks);
        Bundle args = new Bundle();
        args.putString(TITLE_WARNING_TAG, attributes.getTitleBox());
        args.putString(BODY_WARNING_TAG, attributes.getBodyBox());
        args.putString(POSITIVE_BUTTON_WARNING_TAG, attributes.getPositiveButton());
        if (attributes.getNegativeButton() != null) {
            args.putString(NEGATIVE_BUTTON_WARNING_TAG, attributes.getNegativeButton());
        }

        warningBox.setArguments(args);
        return warningBox;
    }

    public static WarningBox newInstance(WarningBoxAttributes attributes) {
        WarningBox warningBox = new WarningBox(null);
        Bundle args = new Bundle();
        args.putString(TITLE_WARNING_TAG, attributes.getTitleBox());
        args.putString(BODY_WARNING_TAG, attributes.getBodyBox());
        args.putString(POSITIVE_BUTTON_WARNING_TAG, attributes.getPositiveButton());
        if (attributes.getNegativeButton() != null) {
            args.putString(NEGATIVE_BUTTON_WARNING_TAG, attributes.getNegativeButton());
        }

        warningBox.setArguments(args);
        return warningBox;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = WarningBoxLayoutBinding.inflate(inflater, container, false);

        getTitleAndBody();
        initButtons();

        return binding.getRoot();
    }

    private void getTitleAndBody() {
        String title = getArguments().getString(TITLE_WARNING_TAG);
        String body = getArguments().getString(BODY_WARNING_TAG);
        String positiveButton = getArguments().getString(POSITIVE_BUTTON_WARNING_TAG);
        String negativeButton = getArguments().getString(NEGATIVE_BUTTON_WARNING_TAG);

        binding.textTitleWarningBox.setText(title);
        binding.textBodyWarningBox.setText(body);

        binding.warningBoxPositiveButton.setText(positiveButton);

        if (negativeButton != null) {
            binding.warningBoxNegativeButton.setVisibility(View.VISIBLE);
            binding.warningBoxNegativeButton.setText(negativeButton);
        }
    }

    private void initButtons() {
        binding.warningBoxPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (warningBoxClicks != null) {
                    warningBoxClicks.positiveClick();
                }
                dismiss();
            }
        });

        binding.warningBoxNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (warningBoxClicks != null) {
                    warningBoxClicks.negativeClick();
                }
                dismiss();
            }
        });
    }
}
