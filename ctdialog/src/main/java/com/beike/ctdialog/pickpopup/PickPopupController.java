package com.beike.ctdialog.pickpopup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.beike.ctdialog.R;
import com.beike.ctdialog.iterface.IActionClickListener;
import com.beike.ctdialog.utils.DensityUtil;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by liupeng on 2017/6/15.
 */

public class PickPopupController {
    private Context context;
    private PopupWindow popupWindow;
    public View popuView;
    private Window window;
    private IActionClickListener clickListener;
    private LinearLayout linearLayout;
    private ScrollView scrollView;

    public PickPopupController(Context context, PopupWindow popupWindow) {
        this.context = context;
        this.popupWindow = popupWindow;
    }

    /**
     * 装载布局
     */
    public void installContent() {
        popuView = LayoutInflater.from(context).inflate(R.layout.popup_pick_layout, null);

        popuView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (clickListener != null) {
                    clickListener.onClick(0);
                }
            }
        });

        scrollView = popuView.findViewById(R.id.scroll_view);

        popupWindow.setContentView(popuView);
    }

    public void addAction(LinkedHashMap<String, Boolean> actionMap, String title) {
        int textViewHeight = context.getResources().getDimensionPixelSize(R.dimen.ct_selector_item_dimen);
        if (linearLayout == null) {
            linearLayout = popuView.findViewById(R.id.linear_add_area);
        }

        if (actionMap.size() > 6) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) scrollView.getLayoutParams();
            params.height = textViewHeight * 8;
            scrollView.setLayoutParams(params);
        }

        TextView tvTitle = popuView.findViewById(R.id.tv_title);
        View lineView = popuView.findViewById(R.id.line_title);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
            tvTitle.setVisibility(View.VISIBLE);

//            if (actionMap.size() == 0) {
//                tvTitle.setBackgroundResource(R.drawable.shape_dialog_bg);
//                lineView.setVisibility(View.INVISIBLE);
//                return;
//            }
            lineView.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.GONE);
            lineView.setVisibility(View.GONE);
        }

        Iterator<Map.Entry<String, Boolean>> iterator = actionMap.entrySet().iterator();
        int i = 0;
        int textViewPadding = DensityUtil.dip2px(context, 10);
        while (iterator.hasNext()) {
            i++;
            Map.Entry<String, Boolean> entry = iterator.next();

            TextView textView = new TextView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, textViewHeight);
            textView.setText(entry.getKey());
            textView.setTextColor(context.getResources().getColor(entry.getValue() ? R.color.ct_red : R.color.ct_black));
            textView.setMaxLines(1);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setTextSize(16);
            textView.setPadding(textViewPadding, 0, textViewPadding, 0);
            textView.setGravity(Gravity.CENTER);

            final int index = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    if (clickListener != null) {
                        clickListener.onClick(index);
                    }
                }
            });
            textView.setBackgroundColor(context.getResources().getColor(R.color.white));
            linearLayout.addView(textView, params);

            if (actionMap.size() > 1 && i < actionMap.size()) {
                addLineView();
            }
        }
    }

    /**
     * 添加分割线
     */
    public void addLineView() {
        View lineView = new View(context);
        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, context.getResources().getDimensionPixelSize(R.dimen.ct_line_dimen));
        lineView.setBackgroundColor(context.getResources().getColor(R.color.ct_line_color_1));
        linearLayout.addView(lineView, lineParams);
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
        public LinkedHashMap<String, Boolean> actionMap = new LinkedHashMap<>();
        public IActionClickListener clickListener;

        public PopupParams(Context context) {
            this.context = context;
        }

        public void apply(PickPopupController controller) {

            controller.installContent();
            controller.setActionClickListener(clickListener);
            controller.addAction(actionMap, title);

            controller.setSize();
            controller.setOutsideTouchable(isTouchable);

            controller.setBackgroundLevel(bgLevel);
            controller.setAnimationStyle();
        }
    }
}
