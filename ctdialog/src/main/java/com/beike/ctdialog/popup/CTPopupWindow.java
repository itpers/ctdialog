package com.beike.ctdialog.popup;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by liupeng on 2017/6/15.
 */

public class CTPopupWindow extends PopupWindow {

    private PopuController controller;

    @Override
    public int getWidth() {
        return controller.popuView.getMeasuredWidth();
    }

    @Override
    public int getHeight() {
        return controller.popuView.getMeasuredHeight();
    }

    private CTPopupWindow(Context context) {
        controller = new PopuController(context, this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        controller.setBackgroundLevel(1.0f);
    }

    public static class Builder {
        private PopuController.PopupParams params;
        private ViewInterface listener;

        public Builder(Context context) {
            params = new PopuController.PopupParams(context);
        }

        public Builder setView(int layoutRId) {
            params.view = null;
            params.layoutRId = layoutRId;
            return this;
        }

        public Builder setView(View view) {
            params.view = view;
            params.layoutRId = 0;
            return this;
        }

        public Builder setViewOnClickListener(ViewInterface listener) {
            this.listener = listener;
            return this;
        }

        public Builder setSize(int width, int height) {
            params.width = width;
            params.height = height;
            return this;
        }

        public Builder setBackgroundLevel(float level) {
            params.isShowBg = true;
            params.bgLevel = level;
            return this;
        }

        public Builder setOutsideTouchable(boolean touchable) {
            params.isTouchable = touchable;
            return this;
        }

        public Builder setAnimation(int animation) {
            params.isShowAnim = true;
            params.animationStyle = animation;
            return this;
        }

        public CTPopupWindow create() {
            final CTPopupWindow popuWindow = new CTPopupWindow(params.context);
            params.apply(popuWindow.controller);
            if (listener != null && params.layoutRId != 0) {
                listener.getChildView(popuWindow.controller.popuView, params.layoutRId);
            }
            int measure = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            popuWindow.controller.popuView.measure(measure, measure);
            return popuWindow;
        }
    }

    public interface ViewInterface {
        void getChildView(View view, int layoutRId);
    }
}
