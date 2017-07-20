package com.beike.ctdialog.sharepopup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.beike.ctdialog.R;
import com.beike.ctdialog.iterface.IActionClickListener;
import com.beike.ctdialog.utils.DensityUtil;
import com.beike.ctdialog.widget.ImageText;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by liupeng on 2017/6/15.
 */

public class SharePopupController {
    private Context context;
    private PopupWindow popupWindow;
    public View popuView;
    private Window window;
    private IActionClickListener clickListener;
    private int index = 0;

    public SharePopupController(Context context, PopupWindow popupWindow) {
        this.context = context;
        this.popupWindow = popupWindow;
    }

    /**
     * 装载布局
     */
    public void installContent() {
        popuView = LayoutInflater.from(context).inflate(R.layout.popup_share_layout, null);

        popuView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (clickListener != null) {
                    clickListener.onClick(0);
                }
            }
        });

        popupWindow.setContentView(popuView);

    }

    public void addAction(LinkedHashMap<Drawable, String> actionMap, int imageSize, int textSize, int textColor, String title, boolean isControl, boolean isShowControl) {
        LinearLayout linearLayout;
        HorizontalScrollView scrollView;
        boolean isShowTitle = !TextUtils.isEmpty(title);

        if (isControl) {
            scrollView = (HorizontalScrollView) popuView.findViewById(R.id.control_scrollview);
            linearLayout = (LinearLayout) popuView.findViewById(R.id.linear_contorl);
            scrollView.setVisibility(View.VISIBLE);
            popuView.findViewById(R.id.line).setVisibility(View.VISIBLE);
        } else {
            scrollView = (HorizontalScrollView) popuView.findViewById(R.id.share_scrollview);
            linearLayout = (LinearLayout) popuView.findViewById(R.id.linear_share);

            if (isShowControl) {
                if (isShowTitle) {
                    scrollView.setBackgroundResource(R.drawable.selector_shape_dialog_middle);
                } else {
                    scrollView.setBackgroundResource(R.drawable.selector_shape_dialog_top_half);
                }
            } else {
                if (isShowTitle) {
                    scrollView.setBackgroundResource(R.drawable.selector_shape_dialog_bottom_half);
                } else {
                    scrollView.setBackgroundResource(R.drawable.selector_shape_dialog_selector_cancel);
                }
            }
        }

        int imageTextMargin = DensityUtil.dip2px(context, 10);
        int i = 0;
        for (Map.Entry<Drawable, String> entry : actionMap.entrySet()) {
            index++;
            i++;
            ImageText imageText = new ImageText(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = imageTextMargin;
            if (i == actionMap.size()) {
                params.rightMargin = imageTextMargin;
            }
            imageText.setText(entry.getValue());
            imageText.setTextColor(textColor);
            imageText.setTextSize(textSize);
            imageText.setImageView(entry.getKey(), imageSize);

            final int finalIndex = index;
            imageText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    if (clickListener != null) {
                        clickListener.onClick(finalIndex);
                    }
                }
            });

            linearLayout.addView(imageText, params);
        }

        TextView tvTitle = (TextView) popuView.findViewById(R.id.share_title);
        if (isShowTitle) {
            tvTitle.setText(title);
            tvTitle.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置宽高
     */
    public void setSize() {
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置背景灰色程度
     *
     * @param level
     */
    public void setBackgroundLevel(float level) {
        window = ((Activity) context).getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = window.getAttributes();
        params.alpha = level;
        window.setAttributes(params);
    }

    /**
     * 设置动画
     */
    public void setAnimationStyle() {
        popupWindow.setAnimationStyle(R.style.ActionSheetStyle);
    }

    /**
     * outside是否可点击
     *
     * @param touchable
     */
    private void setOutsideTouchable(boolean touchable) {
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(touchable);
        popupWindow.setFocusable(touchable);
    }

    public void setActionClickListener(IActionClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public static class PopupParams {
        public Context context;
        public float bgLevel = 0.6f;
        public boolean isTouchable = true;
        public String title;
        public LinkedHashMap<Drawable, String> actionMap = new LinkedHashMap<>();
        public LinkedHashMap<Drawable, String> controlMap = new LinkedHashMap<>();
        public IActionClickListener clickListener;
        public int imageSize = 50, textSize = 12, textColor;

        public PopupParams(Context context) {
            this.context = context;
            textColor = context.getResources().getColor(R.color.ct_gray_1);
        }

        public void apply(SharePopupController controller) {

            controller.installContent();
            controller.setActionClickListener(clickListener);
            controller.addAction(actionMap, imageSize, textSize, textColor, title, false, controlMap.size() > 0);
            if (controlMap.size() > 0) {
                controller.addAction(controlMap, imageSize, textSize, textColor, title, true, true);
            }

            controller.setSize();
            controller.setOutsideTouchable(isTouchable);

            controller.setBackgroundLevel(bgLevel);
            controller.setAnimationStyle();
        }
    }
}