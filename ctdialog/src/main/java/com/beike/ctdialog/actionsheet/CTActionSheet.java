package com.beike.ctdialog.actionsheet;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.PopupWindow;

import com.beike.ctdialog.iterface.IActionClickListener;

/**
 * Created by liupeng on 2017/6/15.
 */
public class CTActionSheet extends PopupWindow {

    private ActionSheetController controller;

    @Override
    public int getWidth() {
        return controller.popuView.getMeasuredWidth();
    }

    @Override
    public int getHeight() {
        return controller.popuView.getMeasuredHeight();
    }

    private CTActionSheet(Context context) {
        controller = new ActionSheetController(context, this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        controller.setBackgroundLevel(1.0f);
    }

    public static class Builder {
        private ActionSheetController.PopupParams params;

        public Builder(Context context) {
            params = new ActionSheetController.PopupParams(context);
        }

        public Builder setTitle(String title) {
            if (TextUtils.isEmpty(title)) return this;
            params.title = title;
            return this;
        }

        public Builder addNegativeAction(String action) {
            params.actionMap.put(action, true);
            return this;
        }

        public Builder addAction(String action) {
            params.actionMap.put(action, false);
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

        public CTActionSheet create() {
            final CTActionSheet popuWindow = new CTActionSheet(params.context);
            params.apply(popuWindow.controller);

            int measure = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            popuWindow.controller.popuView.measure(measure, measure);
            return popuWindow;
        }
    }
}
