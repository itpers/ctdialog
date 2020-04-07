package com.beike.ctdialog.sharepopup;

import android.graphics.drawable.Drawable;

public class ShareItem {
    public int resId;
    public String name;
    public boolean isEnable = true;

    public ShareItem() {
    }

    public ShareItem(String name) {
        this.name = name;
    }

    public ShareItem(int resId, String name) {
        this.resId = resId;
        this.name = name;
    }

    public ShareItem(String name, boolean isEnable) {
        this.name = name;
        this.isEnable = isEnable;
    }

    public ShareItem(int resId, String name, boolean isEnable) {
        this.resId = resId;
        this.name = name;
        this.isEnable = isEnable;
    }
}
