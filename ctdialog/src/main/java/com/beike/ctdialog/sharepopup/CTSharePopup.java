package com.beike.ctdialog.sharepopup;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.PopupWindow;

import com.beike.ctdialog.iterface.IActionClickListener;

/**
 * Created by liupeng on 2017/6/15.
 */
public class CTSharePopup extends PopupWindow {

    private SharePopupController controller;

    @Override
    public int getWidth() {
        return controller.popuView.getMeasuredWidth();
    }

    @Override
    public int getHeight() {
        return controller.popuView.getMeasuredHeight();
    }

    private CTSharePopup(Context context) {
        controller = new SharePopupController(context, this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        controller.setBackgroundLevel(1.0f);
    }

    public static class Builder {
        private SharePopupController.PopupParams params;

        public Builder(Context context) {
            params = new SharePopupController.PopupParams(context);
        }

        public Builder setTitle(String title) {
            if (TextUtils.isEmpty(title)) return this;
            params.title = title;
            return this;
        }

        public Builder setTitle(@StringRes int titleRid) {
            if (titleRid <= 0) return this;
            params.title = params.context.getText(titleRid).toString();
            return this;
        }

        public Builder addShare(Drawable drawable, String name) {
            params.actionMap.put(drawable, name);
            return this;
        }

        public Builder addControl(Drawable drawable, String name) {
            params.controlMap.put(drawable, name);
            return this;
        }

        public Builder setImageSize(int imageSize) {
            params.imageSize = imageSize;
            return this;
        }

        public Builder setTextSize(int textSize) {
            params.textSize = textSize;
            return this;
        }

        public Builder setTextColor(int textColor) {
            params.textColor = textColor;
            return this;
        }

        public Builder setBackgroundLevel(float level) {
            params.bgLevel = level;
            return this;
        }

        public Builder setOutsideTouchable(boolean touchable) {
            params.isTouchable = touchable;
            return this;
        }

        public Builder setActionClickListener(IActionClickListener clickListener) {
            params.clickListener = clickListener;
            return this;
        }

        public CTSharePopup create() {
            final CTSharePopup popuWindow = new CTSharePopup(params.context);
            params.apply(popuWindow.controller);

            int measure = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            popuWindow.controller.popuView.measure(measure, measure);
            return popuWindow;
        }
    }
}
