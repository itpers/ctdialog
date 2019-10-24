package com.beike.ctdialog.popupMenue;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.PopupWindow;

import com.beike.ctdialog.iterface.IActionClickListener;
import com.beike.ctdialog.iterface.IItemClickListener;
import com.beike.ctdialog.popMenu.PopMenuItem;

/**
 * Created by liupeng on 2017/6/15.
 */
public class CTPopupMenu extends PopupWindow {

    private PopupMenuController controller;

    @Override
    public int getWidth() {
        return controller.popuView.getMeasuredWidth();
    }

    @Override
    public int getHeight() {
        return controller.popuView.getMeasuredHeight();
    }

    private CTPopupMenu(Context context) {
        controller = new PopupMenuController(context, this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        controller.setBackgroundLevel(1.0f);
    }

    public static class Builder {
        private PopupMenuController.PopupParams params;

        public Builder(Context context) {
            params = new PopupMenuController.PopupParams(context);
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

        public CTPopupMenu create() {
            final CTPopupMenu popuWindow = new CTPopupMenu(params.context);
            params.apply(popuWindow.controller);

//            int measure = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//            popuWindow.controller.popupView.measure(measure, measure);
            return popuWindow;
        }
    }
}
