package com.beike.ctdialog.popMenu;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.beike.ctdialog.R;
import com.beike.ctdialog.iterface.IItemClickListener;
import com.xujiaji.happybubble.BubbleDialog;
import com.xujiaji.happybubble.BubbleLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupeng on 2017/6/15.
 */

public class PopMenu {
    private Context context;
    private BubbleDialog bubbleDialog;
    private BubbleLayout bubbleLayout;
    private ViewGroup popuView;
    private List<PopMenuItem> items;
    private IItemClickListener clickListener;
    private boolean isShowLine;

    public PopMenu(Context context) {
        this.context = context;

        bubbleLayout = new BubbleLayout(context);
        bubbleLayout.setPadding(0, 0, 0, 0);
        bubbleLayout.setPaddingRelative(0, 0, 0, 0);

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

    public PopMenu installContent() {
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
            popuView.addView(item);
            if (isShowLine && i < items.size() - 1) {
                item.findViewById(R.id.line).setVisibility(View.VISIBLE);
//                addLineView();
            }
        }
        bubbleDialog.addContentView(popuView);
        return this;
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
