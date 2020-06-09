package com.beike.ctdialog.sharepopup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.beike.ctdialog.R;
import com.beike.ctdialog.iterface.IActionClickListener;
import com.beike.ctdialog.utils.DensityUtil;
import com.beike.ctdialog.widget.ImageText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupeng on 2017/6/15.
 */
public class CTSharePopup extends PopupWindow {

    public View popupView;
    private Context context;
    private Window window;
    private IActionClickListener clickListener;

    private CTSharePopup(Context context) {
        this.context = context;
    }

    /**
     * 装载布局
     */
    public void installContent() {
        popupView = LayoutInflater.from(context).inflate(R.layout.popup_share_layout, null);

        popupView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (clickListener != null) {
                    clickListener.onClick(-1);
                }
            }
        });

        setContentView(popupView);

    }

    public void addAction(final List<ShareItem> shareItems, int spanCount, int paddingSize, int bgColor, final int imageSize, final int textSize, final int textColor, String title, boolean isControl) {
        RecyclerView recyclerView;
        boolean isShowTitle = !TextUtils.isEmpty(title);

        if (isControl) {
            recyclerView = popupView.findViewById(R.id.rv_controls);
        } else {
            recyclerView = popupView.findViewById(R.id.rv_actions);
        }

        recyclerView.setBackgroundColor(context.getResources().getColor(bgColor));
        if (paddingSize > 0) {
            paddingSize = DensityUtil.dip2px(context, paddingSize);
            recyclerView.setPadding(paddingSize, 0, paddingSize, 0);
        }
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new GridLayoutManager(context, spanCount));

        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                ImageText imageText = new ImageText(context);
                imageText.setTextColor(textColor);
                imageText.setTextSize(textSize);
                return new RecyclerView.ViewHolder(imageText) {
                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
                ShareItem shareItem = shareItems.get(i);
                if (!TextUtils.isEmpty(shareItem.name)) {
                    ImageText imageText = (ImageText) viewHolder.itemView;
                    imageText.setText(shareItem.name);
                    imageText.setImageView(shareItem.resId, imageSize);
                    imageText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dismiss();
                            if (clickListener != null) {
                                clickListener.onClick(i);

                            }
                        }
                    });
                }
            }

            @Override
            public int getItemCount() {
                return shareItems.size();
            }
        });

        TextView tvTitle = popupView.findViewById(R.id.share_title);
        if (isShowTitle) {
            tvTitle.setText(title);
            tvTitle.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置宽高
     */
    public void setSize() {
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置背景灰色程度
     *
     * @param level
     */
    public void setBackgroundLevel(float level) {
        setBackgroundDrawable(new ColorDrawable(0x00000000));
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
        setAnimationStyle(R.style.ActionSheetStyle);
    }

    public void setActionClickListener(IActionClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        setBackgroundLevel(1.0f);
    }

    public static class Builder {
        public Context context;
        public float bgLevel = 0.6f;
        public boolean isTouchable = true;
        public String title;
        public List<ShareItem> actions = new ArrayList<>();
        public List<ShareItem> controls = new ArrayList<>();
        public IActionClickListener clickListener;
        public int imageSize = 50, textSize = 12, textColor, spanCount = 4, paddingSize = 0;
        public int bgColor;

        public Builder(Context context) {
            this.context = context;
            textColor = context.getResources().getColor(R.color.ct_black);
            bgColor = R.color.white;
        }

        public Builder setTitle(String title) {
            if (TextUtils.isEmpty(title)) return this;
            this.title = title;
            return this;
        }

        public Builder setTitle(@StringRes int titleRid) {
            this.title = context.getText(titleRid).toString();
            return this;
        }

        public Builder addShare(ShareItem shareItem) {
            this.actions.add(shareItem);
            return this;
        }

        public Builder addControl(ShareItem shareItem) {
            this.controls.add(shareItem);
            return this;
        }

        public Builder setPaddingSize(int paddingSize) {
            this.paddingSize = paddingSize;
            return this;
        }

        public Builder setBgColor(int bgColor) {
            this.bgColor = bgColor;
            return this;
        }

        public Builder setSpanCount(int spanCount) {
            this.spanCount = spanCount;
            return this;
        }

        public Builder setImageSize(int imageSize) {
            this.imageSize = imageSize;
            return this;
        }

        public Builder setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public Builder setTextColor(int textColor) {
            this.textColor = textColor;
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

        public Builder setActionClickListener(IActionClickListener clickListener) {
            this.clickListener = clickListener;
            return this;
        }

        public CTSharePopup create(View targetView) {
            final CTSharePopup popupWindow = new CTSharePopup(context);

            popupWindow.installContent();
            popupWindow.setActionClickListener(clickListener);
            popupWindow.addAction(actions, spanCount, paddingSize, bgColor, imageSize, textSize, textColor, title, false);
            if (controls.size() > 0) {
                popupWindow.addAction(controls, spanCount, paddingSize, bgColor, imageSize, textSize, textColor, title, true);
            }

            popupWindow.setSize();
            popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
            popupWindow.setOutsideTouchable(isTouchable);
            popupWindow.setFocusable(true);
            popupWindow.setAnimationStyle();

//            int measure = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//            popupWindow.popupView.measure(measure, measure);

            if (targetView != null) {
                popupWindow.showAtLocation(targetView, Gravity.BOTTOM, 0, 0);
                popupWindow.setBackgroundLevel(bgLevel);
            }
            return popupWindow;
        }
    }
}
