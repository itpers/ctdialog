package com.beike.ctdialog.menuPopup;

import android.content.Context;
import android.text.TextUtils;
import android.widget.PopupWindow;

import com.beike.ctdialog.iterface.IItemClickListener;
import com.beike.ctdialog.popMenu.PopMenuItem;

/**
 * Created by liupeng on 2017/6/15.
 */
public class CTMenuPopup extends PopupWindow {

    private MenuPopupController controller;

    @Override
    public int getWidth() {
        return controller.popupView.getMeasuredWidth();
    }

    @Override
    public int getHeight() {
        return controller.popupView.getMeasuredHeight();
    }

    private CTMenuPopup(Context context) {
        controller = new MenuPopupController(context, this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        controller.setBackgroundLevel(1.0f);
    }

    public static class Builder {
        private MenuPopupController.PopupParams params;

        public Builder(Context context) {
            params = new MenuPopupController.PopupParams(context);
        }

        public Builder setTitle(String title) {
            if (TextUtils.isEmpty(title)) return this;
            params.title = title;
            return this;
        }

        public Builder addItem(PopMenuItem item) {
            params.items.add(item);
            return this;
        }

        public Builder setDarkModel(boolean isDarkModel) {
            params.isDarkModel = isDarkModel;
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

        public Builder setAutoDismiss(boolean autoDismiss) {
            params.autoDismiss = autoDismiss;
            return this;
        }

        public Builder setItemClickListener(IItemClickListener clickListener) {
            params.clickListener = clickListener;
            return this;
        }

        public CTMenuPopup create() {
            final CTMenuPopup popuWindow = new CTMenuPopup(params.context);
            params.apply(popuWindow.controller);

//            int measure = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//            popuWindow.controller.popupView.measure(measure, measure);
            return popuWindow;
        }
    }
}
