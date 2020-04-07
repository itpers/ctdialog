package com.beike.ctdialog.menuPopup;

public class PopupItemDate {
    private int resId;
    private String title;
    private boolean isEnable = true;

    public PopupItemDate(String title) {
        this.title = title;
    }

    public static PopupItemDate get(String title) {
        return new PopupItemDate(title);
    }

    public PopupItemDate withIcon(int resId) {
        this.resId = resId;
        return this;
    }

    public PopupItemDate withEnable(boolean isEnable) {
        this.isEnable = isEnable;
        return this;
    }

    public int getResId() {
        return resId;
    }

    public String getTitle() {
        return title;
    }

    public boolean isEnable() {
        return isEnable;
    }
}
