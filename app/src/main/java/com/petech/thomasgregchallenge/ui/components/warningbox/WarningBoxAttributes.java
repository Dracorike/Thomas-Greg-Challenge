package com.petech.thomasgregchallenge.ui.components.warningbox;

public class WarningBoxAttributes {
    private final String titleBox;
    private final String bodyBox;
    private final String positiveButton;
    private String negativeButton;

    public WarningBoxAttributes(String titleBox, String bodyBox, String positiveButton) {
        this.titleBox = titleBox;
        this.bodyBox = bodyBox;
        this.positiveButton = positiveButton;
    }

    public WarningBoxAttributes(String titleBox, String bodyBox, String positiveButton, String negativeButton) {
        this.titleBox = titleBox;
        this.bodyBox = bodyBox;
        this.positiveButton = positiveButton;
        this.negativeButton = negativeButton;
    }

    public String getTitleBox() {
        return titleBox;
    }

    public String getBodyBox() {
        return bodyBox;
    }

    public String getPositiveButton() {
        return positiveButton;
    }

    public String getNegativeButton() {
        return negativeButton;
    }
}
