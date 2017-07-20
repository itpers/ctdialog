package com.beike.ctdialog.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by liupeng on 2017/6/15.
 */

public class PopuController {
    private int layoutRId;
    public Context context;
    private PopupWindow popupWindow;
    public View popuView;
    private View view;
    private Window window;

    public PopuController(Context context, PopupWindow popupWindow) {
        this.context = context;
        this.popupWindow = popupWindow;
    }

    /**
     * 设置布局
     * @param layoutRId
     */
    public void setView(int layoutRId) {
        view = null;
        this.layoutRId = layoutRId;
        installContent();
    }

    /**
     * 设置布局
     * @param view
     */
    public void setView(View view) {
        this.view = view;
        this.layoutRId = 0;
        installContent();
    }

    /**
     * 装载布局
     */
    public void installContent() {
        if (layoutRId != 0) {
            popuView = LayoutInflater.from(context).inflate(layoutRId, null);
        } else if (view != null) {
            popuView = view;
        }

        popupWindow.setContentView(popuView);
    }

    /**
     * 设置宽高
     * @param width
     * @param height
     */
    public void setSize(int width, int height) {
        if (width == 0 || height == 0) {
            popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            popupWindow.setWidth(width);
            popupWindow.setHeight(height);
        }
    }

    /**
     * 设置背景灰色程度
     * @param level
     */
    public void setBackgroundLevel(float level) {
        window = ((Activity)context).getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = window.getAttributes();
        params.alpha = level;
        window.setAttributes(params);
    }

    /**
     * 设置动画
     * @param animationStyle
     */
    public void setAnimationStyle(int animationStyle) {
        popupWindow.setAnimationStyle(animationStyle);
    }

    /**
     * outside是否可点击
     * @param touchable
     */
    private void setOutsideTouchable(boolean touchable) {
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(touchable);
        popupWindow.setFocusable(touchable);
    }

    static class PopupParams {
        public int layoutRId;
        public Context context;
        public int width, height;
        public boolean isShowBg, isShowAnim;
        public float bgLevel;
        public int animationStyle;
        public View view;
        public boolean isTouchable = true;

        public PopupParams(Context context) {
            this.context = context;
        }

        public void apply(PopuController controller) {
            if (view != null) {
                controller.setView(view);
            } else if (layoutRId != 0) {
                controller.setView(layoutRId);
            } else {
                throw new IllegalArgumentException("PopuView's contentView is null");
            }

            controller.setSize(width, height);
            controller.setOutsideTouchable(isTouchable);

            if (isShowBg) {
                controller.setBackgroundLevel(bgLevel);
            }

            if (isShowAnim) {
                controller.setAnimationStyle(animationStyle);
            }
        }
    }
}
