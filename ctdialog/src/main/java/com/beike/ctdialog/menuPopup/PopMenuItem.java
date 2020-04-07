package com.beike.ctdialog.menuPopup;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beike.ctdialog.R;

public class PopMenuItem extends LinearLayout {
    private View rootView;
    private TextView tvTitle;
    private ImageView ivIcon;
    private View lineView;
    private RelativeLayout.LayoutParams layoutParams;

    private Context context;

    public PopMenuItem(Context context) {
        this(context, null);
    }

    public PopMenuItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopMenuItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context);
    }

    public void init(Context context) {
        rootView = inflate(context, R.layout.ct_popup_menu_item, null);
        tvTitle = rootView.findViewById(R.id.tv_text);
        ivIcon = rootView.findViewById(R.id.iv_icon);
        lineView = rootView.findViewById(R.id.line_view);
        addView(rootView);
    }

    public PopMenuItem setDate(PopupItemDate itemDate) {
        if (itemDate == null) return this;
        setTitle(itemDate.getTitle());
        setIcon(itemDate.getResId());
        setEnable(itemDate.isEnable());
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

    public PopMenuItem setIcon(int resId) {
        if (ivIcon != null && resId > 0) {
            ivIcon.setVisibility(VISIBLE);
            ivIcon.setImageResource(resId);
            if (layoutParams == null) {
                layoutParams = (RelativeLayout.LayoutParams) tvTitle.getLayoutParams();
            }
            layoutParams.removeRule(RelativeLayout.CENTER_HORIZONTAL);
        }
        return this;
    }

    public PopMenuItem setIconVisible(int visible) {
        if (ivIcon != null) {
            ivIcon.setVisibility(visible);
        }
        return this;
    }

    public PopMenuItem setLineVisible(boolean visible) {
        if (lineView != null) {
            lineView.setVisibility(visible ? VISIBLE : INVISIBLE);
        }
        return this;
    }

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
        if (isDarkModel) {
            if (tvTitle != null) {
                tvTitle.setTextColor(getResources().getColorStateList(R.color.dark_menu_popup_item_text_color));
            }
            if (lineView != null) {
                lineView.setBackgroundColor(context.getResources().getColor(R.color.ct_dark_line_color_1AF));
            }
        }
    }

//    public ImageView getIcon() {
//        return ivIcon;
//    }


}
