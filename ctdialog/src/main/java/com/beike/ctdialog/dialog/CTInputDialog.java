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
import android.widget.EditText;
import android.widget.TextView;

import com.beike.ctdialog.R;
import com.beike.ctdialog.iterface.IDialogInputListener;

/**
 * Created by liupeng on 2017/6/19.
 */

public class CTInputDialog extends AlertDialog{

    private TextView tvTitle;
    private EditText tvMessage;
    private TextView tvCancel;
    private TextView tvConfirm;

    private String title;
    private String inputHint;
    private String cancel;
    private String confirm;

//    private Context context;

    private IDialogInputListener inputListener;

    public CTInputDialog(@NonNull Context context, String inputHint, @Nullable IDialogInputListener inputListener) {
        this(context, "", inputHint, "", "", true, true, inputListener);
    }

    public CTInputDialog(@NonNull Context context, String inputHint, boolean cancelable, boolean outsideCancelable, @Nullable IDialogInputListener inputListener) {
        this(context, "", inputHint, "", "", cancelable, outsideCancelable, inputListener);
    }

    public CTInputDialog(@NonNull Context context, String title, String inputHint, @Nullable IDialogInputListener inputListener) {
        this(context, title, inputHint, "", "", true, true, inputListener);
    }

    public CTInputDialog(@NonNull Context context, String title, String inputHint, boolean cancelable, boolean outsideCancelable, @Nullable IDialogInputListener inputListener) {
        this(context, title, inputHint, "", "", cancelable, outsideCancelable, inputListener);
    }

    public CTInputDialog(@NonNull Context context, String title, String inputHint, String cancel, String confirm, boolean cancelable, boolean outsideCancelable, @Nullable IDialogInputListener inputListener) {
        super(context);
        this.title = title;
        this.inputHint = inputHint;
        this.cancel = cancel;
        this.confirm = confirm;
        this.inputListener = inputListener;
        setCancelable(cancelable);
        setCanceledOnTouchOutside(outsideCancelable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input_layout);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels > metrics.heightPixels ? metrics.heightPixels : metrics.widthPixels;
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.dimAmount = 0.4f;
        p.width = (int) (screenWidth * 0.8);
        getWindow().setAttributes(p);
        getWindow().setBackgroundDrawableResource(R.color.transparent);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvMessage = (EditText) findViewById(R.id.tv_msg);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);

        if (title == null) {
            tvTitle.setVisibility(View.GONE);
        } else if (!"".equals(title)){
            tvTitle.setText(title);
        }

        if (!"".equals(inputHint)){
            tvMessage.setHint(inputHint);
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
                if (inputListener != null) {
                    inputListener.onCancel();
                }
            }
        });

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (inputListener != null) {
                    inputListener.onConfirm(getInput());
                }
            }
        });

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void setTitle(CharSequence title) {
        if (TextUtils.isEmpty(title)) return;
        tvTitle.setText(title);
    }

    public String getInput() {
        return tvMessage.getText().toString();
    }

    public void setHint(CharSequence hint) {
        if (TextUtils.isEmpty(hint)) return;
        tvMessage.setHint(hint);
    }
}
