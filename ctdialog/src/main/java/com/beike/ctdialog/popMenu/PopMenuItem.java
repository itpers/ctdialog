package com.beike.ctdialog.popMenu;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beike.ctdialog.R;

public class PopMenuItem extends LinearLayout {
    private TextView tvTitle;
//    private ImageView ivIcon;

    public PopMenuItem(Context context) {
        this(context, null);
    }

    public PopMenuItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopMenuItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        View view = inflate(context, R.layout.ct_popup_menu_item, null);
        tvTitle = view.findViewById(R.id.tv_text);
//        ivIcon = view.findViewById(R.id.iv_icon);
//        LayoutParams params = new LayoutParams()
        addView(view);
    }

    public PopMenuItem setTitle(int strId) {
        if (tvTitle != null && strId > 0) {
            tvTitle.setText(strId);
        }
        return this;
    }

    public PopMenuItem setTitle(String title) {
        if (tvTitle != null && title != null) {
            tvTitle.setText(title);
        }
        return this;
    }

    public TextView getTitle() {
        return tvTitle;
    }

//    public PopMenuItem setIcon(int resId) {
//        if (ivIcon != null && resId > 0) {
//            ivIcon.setImageResource(resId);
//        }
//        return this;
//    }

//    public PopMenuItem setIconVisible(int visible) {
//        if (ivIcon != null) {
//            ivIcon.setVisibility(visible);
//        }
//        return this;
//    }

    public PopMenuItem setEnable(boolean enabled) {
        if (tvTitle != null) {
            tvTitle.setEnabled(enabled);
        }
        this.setEnabled(enabled);
//        if (ivIcon != null) {
//            ivIcon.setEnabled(enabled);
//        }
        return this;
    }

    public boolean getEnable() {
        if (tvTitle != null) {
            return tvTitle.isEnabled();
        }
        return true;
    }

    public void setDarkModel(boolean isDarkModel) {
        if (isDarkModel && tvTitle != null) {
            tvTitle.setTextColor(getResources().getColorStateList(R.color.dark_menu_popup_item_text_color));
        }
    }

//    public ImageView getIcon() {
//        return ivIcon;
//    }


}
