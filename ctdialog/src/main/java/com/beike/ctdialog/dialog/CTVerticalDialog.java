package com.beike.ctdialog.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.beike.ctdialog.R;
import com.beike.ctdialog.iterface.IDialogVerticalListener;

/**
 * Created by liupeng on 2017/6/19.
 */

public class CTVerticalDialog extends AlertDialog {

    private TextView tvTitle;
    private TextView tvMessage;

    private String title;
    private CharSequence message;
    private String top, middle, bottom;

    private IDialogVerticalListener verticalListener;

    public CTVerticalDialog(@NonNull Context context, String title, CharSequence message, String top, String middle, String bottom, boolean cancelable, boolean outsideCancelable, @Nullable IDialogVerticalListener verticalListener) {
        super(context);
        this.title = title;
        this.message = message;
        this.top = top;
        this.middle = middle;
        this.bottom = bottom;
        this.verticalListener = verticalListener;
        setCancelable(cancelable);
        setCanceledOnTouchOutside(outsideCancelable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_vertical_layout);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels > metrics.heightPixels ? metrics.heightPixels : metrics.widthPixels;
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.dimAmount = 0.4f;
        p.width = (int) (screenWidth * 0.8);
        getWindow().setAttributes(p);
        getWindow().setBackgroundDrawableResource(R.color.transparent);

        tvTitle = findViewById(R.id.tv_title);
        tvMessage = findViewById(R.id.tv_msg);
        TextView tvTop = findViewById(R.id.tv_top);
        TextView tvMiddle = findViewById(R.id.tv_middle);
        TextView tvBottom = findViewById(R.id.tv_bottom);

        if (title == null) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }

        if (message == null) {
            tvMessage.setVisibility(View.GONE);
        } else {
            tvMessage.setText(message);
        }

        if (top == null) {
            tvTop.setVisibility(View.GONE);
            findViewById(R.id.line1).setVisibility(View.GONE);
        } else {
            tvTop.setText(top);
        }

        if (middle == null) {
            tvMiddle.setVisibility(View.GONE);
            findViewById(R.id.line2).setVisibility(View.GONE);
        } else {
            tvMiddle.setText(middle);
        }

        if (bottom != null) {
            tvBottom.setText(bottom);
        }

        tvTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (verticalListener != null) {
                    verticalListener.onTop();
                }
            }
        });

        tvMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (verticalListener != null) {
                    verticalListener.onMiddle();
                }
            }
        });

        tvBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (verticalListener != null) {
                    verticalListener.onBottom();
                }
            }
        });
    }

    @Override
    public void setTitle(CharSequence title) {
        if (TextUtils.isEmpty(title)) return;
        tvTitle.setText(title);
    }

    @Override
    public void setMessage(CharSequence message) {
        if (TextUtils.isEmpty(message)) return;
        tvMessage.setText(message);
    }

    public static class Builder {
        private Context context;
        private String title;
        private CharSequence message;
        private String top, middle, bottom;
        private boolean isTouchable = false, cancelable = true;
        private IDialogVerticalListener clickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            if (TextUtils.isEmpty(title)) return this;
            this.title = title;
            return this;
        }

        public Builder setMessage(CharSequence message) {
            if (TextUtils.isEmpty(message)) return this;
            this.message = message;
            return this;
        }

        public Builder setTop(String top) {
            this.top = top;
            return this;
        }

        public Builder setMiddle(String middle) {
            this.middle = middle;
            return this;
        }

        public Builder setBottom(String bottom) {
            this.bottom = bottom;
            return this;
        }

        public Builder setIsCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setOutsideTouchable(boolean touchable) {
            this.isTouchable = touchable;
            return this;
        }

        public Builder setDialogListener(IDialogVerticalListener clickListener) {
            this.clickListener = clickListener;
            return this;
        }

        public CTVerticalDialog create() {
            final CTVerticalDialog dialog = new CTVerticalDialog(context, title, message, top, middle, bottom, cancelable, isTouchable, clickListener);
            dialog.show();
            return dialog;
        }
    }
}
