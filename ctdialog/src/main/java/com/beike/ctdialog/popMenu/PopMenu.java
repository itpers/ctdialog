package com.beike.ctdialog.popMenu;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.beike.ctdialog.R;
import com.beike.ctdialog.iterface.IItemClickListener;
import com.beike.ctdialog.utils.DensityUtil;
import com.xujiaji.happybubble.BubbleDialog;
import com.xujiaji.happybubble.BubbleLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupeng on 2017/6/15.
 */

public class PopMenu {

    private static int DEFAULT_ITEM_ICON_SIZE = 26; // dp
    private static int DEFAULT_ITEM_TEXT_SIZE = 18; // sp
    private static int DEFAULT_ITEM_PADDING_SIZE = 10; // dp
    private static int DEFAULT_ITEM_ICON_TEXT_MARGIN_SIZE = 5; //dp
    private static int DEFAULT_PADDING = 20; // dp

    private Context context;
    private BubbleDialog bubbleDialog;
    private BubbleLayout bubbleLayout;
    private ViewGroup popuView;
    private List<PopMenuItem> items;
    private IItemClickListener clickListener;
    private boolean isShowLine;
    private int itemIconSize = DEFAULT_ITEM_ICON_SIZE;
    private int itemTitleSize = DEFAULT_ITEM_TEXT_SIZE;
    private int itemPaddingSize = DEFAULT_ITEM_PADDING_SIZE;
    private int popPading = DEFAULT_PADDING;


    public PopMenu(Context context) {
        this.context = context;

        bubbleLayout = new BubbleLayout(context);

        popPading += bubbleLayout.getShadowRadius() * 2;

        this.bubbleDialog = new BubbleDialog(context)
                        .calBar(true)
                        .setRelativeOffset(-8)
                        .setPosition(BubbleDialog.Position.BOTTOM, BubbleDialog.Position.TOP);

        popuView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.popup_pop_menu_layout, null);

        items = new ArrayList<>();
    }

    public PopMenu setTargetView(View view) {
        bubbleDialog.setClickedView(view);
        return this;
    }

    public PopMenu setBubbleColor(int color) {
        bubbleLayout.setBubbleColor(color);
        bubbleDialog.setBubbleLayout(bubbleLayout);
        return this;
    }

    public PopMenu setRelativeOffset(int offset) {
        bubbleDialog.setRelativeOffset(offset);
        return this;
    }

    public PopMenu setTransParentBackground() {
        bubbleDialog.setTransParentBackground();
        return this;
    }

    public PopMenu setShowLine(boolean isShowLine) {
        this.isShowLine = isShowLine;
        return this;
    }

    public PopMenu addItem(PopMenuItem item) {
        if (item == null) return this;
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        items.add(item);
        return this;
    }

    public PopMenu addItems(List<PopMenuItem> items) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.addAll(items);
        return this;

    }

    /**
     * 设置 item 图标打下
     * @param iconSize 默认单位 dp
     * @return
     */
    public PopMenu setItemIconSize(int iconSize) {
        itemIconSize = iconSize;
        return this;
    }

    /**
     * 设置 item 文字大小
     * @param textSize 默认单位 sp
     * @return
     */
    public PopMenu setItemTextSize(int textSize) {
        itemTitleSize = textSize;
        return this;
    }

    /**
     * 设置 item 内边距大小
     * @param paddingSize 默认单位 dp
     * @return
     */
    public PopMenu setItemPaddingSize(int paddingSize) {
        itemPaddingSize = paddingSize;
        return this;
    }

    public PopMenu installContent() {
        String maxText = "";
        if (items == null) {
            bubbleDialog.addContentView(popuView);
            return this;
        }

        for (int i = 0; i < items.size(); i++) {
            final PopMenuItem item = items.get(i);
            final int position = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        clickListener.onClick(position);
                    }
                }
            });
            String itemText = item.getTitle().getText().toString();
            if (!TextUtils.isEmpty(itemText) && itemText.length() > maxText.length()) {
                maxText = itemText;
            }
            popuView.addView(item);
            if (isShowLine && i < items.size() - 1) {
                addLineView();
            }
        }
        bubbleDialog.addContentView(popuView);

        int popWidth = getPopWidth(maxText);
        bubbleDialog.setLayout(popWidth, ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        return this;
    }

    public int getPopWidth(String maxText) {
        int textPxSize = DensityUtil.sp2px(context, itemTitleSize);
        int otherPxWidth = DensityUtil.dip2px(context, itemIconSize + itemPaddingSize * 2 + DEFAULT_ITEM_ICON_TEXT_MARGIN_SIZE + popPading);

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
        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, context.getResources().getDimensionPixelSize(R.dimen.ct_line_dimen));
        lineView.setBackgroundColor(context.getResources().getColor(R.color.ct_line_color_1));
        popuView.addView(lineView, lineParams);
    }

    public PopMenu setItemClickListener(IItemClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }

    public PopMenu show() {
        bubbleDialog.show();
        return this;
    }
}
