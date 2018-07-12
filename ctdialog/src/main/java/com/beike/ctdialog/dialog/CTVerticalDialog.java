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

public class CTVerticalDialog extends AlertDialog{

    private TextView tvTitle;
    private TextView tvMessage;
    private TextView tvTop, tvMiddle, tvBottom;

    private String title;
    private String message;
    private String top, middle, bottom;

    private Context context;

    private IDialogVerticalListener verticalListener;

    public CTVerticalDialog(@NonNull Context context, String message, @Nullable IDialogVerticalListener verticalListener) {
        this(context, "", message, "", "", "", true, true, verticalListener);
    }

    public CTVerticalDialog(@NonNull Context context, String title, String message, String top, String middle, String bottom, @Nullable IDialogVerticalListener verticalListener) {
        this(context, title, message, top, middle, bottom, true, true, verticalListener);
    }

    public CTVerticalDialog(@NonNull Context context, String message, boolean cancelable, boolean outsideCancelable, @Nullable IDialogVerticalListener verticalListener) {
        this(context, "", message, "", "", "", cancelable, outsideCancelable, verticalListener);
    }

    public CTVerticalDialog(@NonNull Context context, String title, String message, @Nullable IDialogVerticalListener verticalListener) {
        this(context, title, message, "", "", "", true, true, verticalListener);
    }

    public CTVerticalDialog(@NonNull Context context, String title, String message, boolean cancelable, boolean outsideCancelable, @Nullable IDialogVerticalListener verticalListener) {
        this(context, title, message, "", "", "", cancelable, outsideCancelable, verticalListener);
    }

    public CTVerticalDialog(@NonNull Context context, String title, String message, String top, String middle, String bottom, boolean cancelable, boolean outsideCancelable, @Nullable IDialogVerticalListener verticalListener) {
        super(context);
//        this.context = context;
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

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvMessage = (TextView) findViewById(R.id.tv_msg);
        tvTop = (TextView) findViewById(R.id.tv_top);
        tvMiddle = (TextView) findViewById(R.id.tv_middle);
        tvBottom = (TextView) findViewById(R.id.tv_bottom);

        if (title == null) {
            tvTitle.setVisibility(View.GONE);
        } else if (!"".equals(title)){
            tvTitle.setText(title);
        }

        if (message == null) {
            tvMessage.setVisibility(View.GONE);
        } else if (!"".equals(message)){
            tvMessage.setText(message);
        }

        if (!TextUtils.isEmpty(top)){
            tvTop.setText(top);
        }

        if (middle == null) {
            tvMiddle.setVisibility(View.GONE);
            findViewById(R.id.line1).setVisibility(View.GONE);
        } else if (!"".equals(middle)){
            tvMiddle.setText(middle);
        }

        if (!TextUtils.isEmpty(bottom)){
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
}
