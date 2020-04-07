package com.beike.ctdialog.menuPopup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.beike.ctdialog.R;
import com.beike.ctdialog.iterface.IItemClickListener;
import com.beike.ctdialog.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupeng on 2017/6/15.
 */
public class CTMenuPopup extends PopupWindow {
    private Context context;

    private Window window;
    public ViewGroup popupView;
    private IItemClickListener clickListener;

    private boolean isDarkModel;

    private CTMenuPopup(Context context) {
        this.context = context;
    }

    /**
     * 装载布局
     */
    public void installContent(boolean isDarkModel) {
        this.isDarkModel = isDarkModel;
        popupView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.ct_popup_menu_layout, null);

        if (isDarkModel) {
            popupView.setBackgroundResource(R.drawable.card_dark_shadow_radius_10);
        }

        setContentView(popupView);
    }

    public void addItems(final List<PopupItemDate> items, final boolean autoDismiss) {
        String maxText = "";
        if (items == null) {
            return;
        }
        setSize(DensityUtil.dip2px(context, 150), DensityUtil.dip2px(context, 45) * items.size() + 45);

        RecyclerView recyclerView = popupView.findViewById(R.id.recycler_view);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) recyclerView.getLayoutParams();
        layoutParams.height = DensityUtil.dip2px(context, 45) * items.size();
//        layoutParams.width = DensityUtil.dip2px(context, 150);
        recyclerView.setLayoutParams(layoutParams);

        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                PopMenuItem popMenuItem = new PopMenuItem(context);
                popMenuItem.setDarkModel(isDarkModel);
                return new RecyclerView.ViewHolder(popMenuItem) {
                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
                final PopupItemDate item = items.get(i);
                PopMenuItem popMenuItem = (PopMenuItem) viewHolder.itemView;
                popMenuItem.setDate(item);
                popMenuItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                        if (clickListener != null) {
                            clickListener.onClick(i);

                        }
                    }
                });
                popMenuItem.setLineVisible(i != getItemCount() - 1);
            }

            @Override
            public int getItemCount() {
                return items.size();
            }
        });
    }

    public void addLineView() {
        View lineView = new View(context);
        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, context.getResources().getDimensionPixelSize(R.dimen.ct_line_dimen_1));
        lineView.setBackgroundColor(context.getResources().getColor(isDarkModel ? R.color.ct_dark_line_color_1AF : R.color.ct_line_color_e5));
        popupView.addView(lineView, lineParams);
    }

    public void setSize(int w, int h) {
        setWidth(w);
        setHeight(h);
    }

    public void setBackgroundLevel(float level) {
        window = ((Activity) context).getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = window.getAttributes();
        params.alpha = level;
        window.setAttributes(params);
    }

    @Override
    public void dismiss() {
        super.dismiss();
//        setBackgroundLevel(1.0f);
    }

    public void setItemClickListener(IItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public static class Builder {
        public Context context;
        public float bgLevel = 0.6f;
        public boolean isTouchable = true, autoDismiss = true, isDarkModel = false;
        public String title;
        public List<PopupItemDate> items = new ArrayList<>();
        public IItemClickListener clickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            if (TextUtils.isEmpty(title)) return this;
            this.title = title;
            return this;
        }

        public Builder addItem(PopupItemDate item) {
            this.items.add(item);
            return this;
        }

        public Builder setDarkModel(boolean isDarkModel) {
            this.isDarkModel = isDarkModel;
            return this;
        }

        public Builder setBackgroundLevel(float level) {
            this.bgLevel = level;
            return this;
        }

        public Builder setOutsideTouchable(boolean touchable) {
            this.isTouchable = touchable;
            return this;
        }

        public Builder setAutoDismiss(boolean autoDismiss) {
            this.autoDismiss = autoDismiss;
            return this;
        }

        public Builder setItemClickListener(IItemClickListener clickListener) {
            this.clickListener = clickListener;
            return this;
        }

        public CTMenuPopup create(View targetView) {
            return create(targetView, 0, 0);
        }

        public CTMenuPopup create(View targetView, int xoff, int yoff) {
            CTMenuPopup popupWindow = new CTMenuPopup(context);
//            params.apply(popupWindow.controller);
            popupWindow.installContent(isDarkModel);
            popupWindow.setItemClickListener(clickListener);
            popupWindow.addItems(items, autoDismiss);
            popupWindow.setOutsideTouchable(isTouchable);

            popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
            popupWindow.setOutsideTouchable(isTouchable);
            popupWindow.setFocusable(isTouchable);
            if (targetView != null && ViewCompat.isAttachedToWindow(targetView)) {
                popupWindow.showAsDropDown(targetView, xoff, yoff);
                popupWindow.setSize(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            return popupWindow;
        }
    }
}
