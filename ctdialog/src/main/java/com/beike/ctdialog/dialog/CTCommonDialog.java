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
import com.beike.ctdialog.iterface.IDialogCommonListener;

/**
 * Created by liupeng on 2017/6/19.
 */

public class CTCommonDialog extends AlertDialog{

    private TextView tvTitle;
    private TextView tvMessage;
    private TextView tvCancel;
    private TextView tvConfirm;

    private String title;
    private String message;
    private String cancel;
    private String confirm;

    private Context context;

    private IDialogCommonListener commonListener;

    public CTCommonDialog(@NonNull Context context, String message, @Nullable IDialogCommonListener commonListener) {
        this(context, "", message, "", "", true, true, commonListener);
    }

    public CTCommonDialog(@NonNull Context context, String message, boolean cancelable, boolean outsideCancelable, @Nullable IDialogCommonListener commonListener) {
        this(context, "", message, "", "", cancelable, outsideCancelable, commonListener);
    }

    public CTCommonDialog(@NonNull Context context, String title, String message, @Nullable IDialogCommonListener commonListener) {
        this(context, title, message, "", "", true, true, commonListener);
    }

    public CTCommonDialog(@NonNull Context context, String title, String message, boolean cancelable, boolean outsideCancelable, @Nullable IDialogCommonListener commonListener) {
        this(context, title, message, "", "", cancelable, outsideCancelable, commonListener);
    }

    public CTCommonDialog(@NonNull Context context, String title, String message, String cancel, String confirm, boolean cancelable, boolean outsideCancelable, @Nullable IDialogCommonListener commonListener) {
        super(context);
//        this.context = context;
        this.title = title;
        this.message = message;
        this.cancel = cancel;
        this.confirm = confirm;
        this.commonListener = commonListener;
        setCancelable(cancelable);
        setCanceledOnTouchOutside(outsideCancelable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common_layout);

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
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);

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

        if (cancel == null) {
            tvCancel.setVisibility(View.GONE);
            findViewById(R.id.line1).setVisibility(View.GONE);
            tvConfirm.setBackgroundResource(R.drawable.selector_shape_dialog_bottom_half);
        } else if (!"".equals(cancel)){
            tvCancel.setText(cancel);
        }

        if (!TextUtils.isEmpty(confirm)) {
            tvConfirm.setText(confirm);
        }

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (commonListener != null) {
                    commonListener.onCancel();
                }
            }
        });

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (commonListener != null) {
                    commonListener.onConfirm();
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
