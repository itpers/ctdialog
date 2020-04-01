package com.beike.ctdialog.menuPopup;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.beike.ctdialog.R;
import com.beike.ctdialog.iterface.IItemClickListener;
import com.beike.ctdialog.popMenu.PopMenuItem;
import com.beike.ctdialog.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupeng on 2017/6/15.
 */

public class MenuPopupController {
    private Context context;
    private PopupWindow popupWindow;
    private Window window;
    private IItemClickListener clickListener;
    public LinearLayout popupView;
    private boolean isDarkModel;

    private static final int DEFAULT_ITEM_HEIGHT = 44;
    private static int DEFAULT_ITEM_PADDING_SIZE = 30; // dp
    private static int DEFAULT_LAYOUT_PADDING = 10; // dp

    public MenuPopupController(Context context, PopupWindow popupWindow) {
        this.context = context;
        this.popupWindow = popupWindow;
    }

    /**
     * 装载布局
     */
    public void installContent(boolean isDarkModel) {
        this.isDarkModel = isDarkModel;
        popupView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.ct_popup_menu_layout, null);

//        linearLayout = popuView.findViewById(R.id.linear_add_area);
        if (isDarkModel) {
            popupView.setBackgroundResource(R.drawable.shape_popup_menu_dark);
        }

        popupWindow.setContentView(popupView);
    }

    public void addItems(List<PopMenuItem> items, final boolean autoDismiss) {
        String maxText = "";
        if (items == null) {
            return;
        }
        int size = items.size();

        for (int i = 0; i < size; i++) {
            final PopMenuItem item = items.get(i);
            item.setDarkModel(isDarkModel);
            final int position = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (autoDismiss && popupWindow != null) {
                        popupWindow.dismiss();
                    }
                    if (clickListener != null) {
                        if (!item.getEnable()) return;
                        clickListener.onClick(position);
                    }
                }
            });
            String itemText = item.getTitle().getText().toString();
            if (!TextUtils.isEmpty(itemText) && itemText.length() > maxText.length()) {
                maxText = itemText;
            }
            popupView.addView(item);
            if (i < items.size() - 1) {
                addLineView();
            }
        }

        int popWidth = getPopWidth(maxText);
        setSize(popWidth, DensityUtil.dip2px(context, DEFAULT_ITEM_HEIGHT) * size + size - 1 + 40);
        return;
    }

    public int getPopWidth(String maxText) {
        int textPxSize = DensityUtil.sp2px(context, 14);
        int otherPxWidth = DensityUtil.dip2px(context, DEFAULT_ITEM_PADDING_SIZE * 2 + DEFAULT_LAYOUT_PADDING * 2);

        Paint paint = new Paint();
        paint.setTextSize(textPxSize);
        int titleWidth = (int) paint.measureText(maxText);

        return otherPxWidth + titleWidth;
    }

    /**
     * 添加分割线
     */
    public void addLineView() {
        View lineView = new View(context);
        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, context.getResources().getDimensionPixelSize(R.dimen.ct_line_dimen_1));
        lineView.setBackgroundColor(context.getResources().getColor(isDarkModel ? R.color.ct_dark_line_color_1AF : R.color.ct_line_color_e5));
        popupView.addView(lineView, lineParams);
    }

    /**
     * 设置宽高
     */
    public void setSize(int w, int h) {
        popupWindow.setWidth(w);
        popupWindow.setHeight(h);
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

    public void setItemClickListener(IItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public static class PopupParams {
        public Context context;
        public float bgLevel = 0.6f;
        public boolean isTouchable = true, autoDismiss = true, isDarkModel = false;
        public String title;
        public List<PopMenuItem> items = new ArrayList<>();
        public IItemClickListener clickListener;

        public PopupParams(Context context) {
            this.context = context;
        }

        public void apply(MenuPopupController controller) {

            controller.installContent(isDarkModel);
            controller.setItemClickListener(clickListener);
            controller.addItems(items, autoDismiss);
            controller.setOutsideTouchable(isTouchable);

//            controller.setBackgroundLevel(bgLevel);
//            controller.setAnimationStyle();
        }
    }
}
