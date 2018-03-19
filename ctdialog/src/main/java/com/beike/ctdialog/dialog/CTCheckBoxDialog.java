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
import android.widget.CheckBox;
import android.widget.TextView;

import com.beike.ctdialog.R;
import com.beike.ctdialog.iterface.IDialogCheckboxListener;
import com.beike.ctdialog.iterface.IDialogCommonListener;

/**
 * Created by liupeng on 2017/6/19.
 */

public class CTCheckBoxDialog extends AlertDialog{

    private TextView tvTitle;
    private TextView tvMessage;
    private TextView tvCancel;
    private TextView tvConfirm;
    private CheckBox cbSwitch;

    private String title;
    private String message;
    private String cancel;
    private String confirm;
    private boolean isChecked;

    private Context context;

    private IDialogCheckboxListener checkboxListener;

    public CTCheckBoxDialog(@NonNull Context context, String message, boolean isChecked, @Nullable IDialogCheckboxListener checkboxListener) {
        this(context, "", message, "", "", isChecked, true, true, checkboxListener);
    }

    public CTCheckBoxDialog(@NonNull Context context, String title, String message, boolean isChecked, @Nullable IDialogCheckboxListener checkboxListener) {
        this(context, title, message, "", "", isChecked, true, true, checkboxListener);
    }

    public CTCheckBoxDialog(@NonNull Context context, String message, boolean isChecked, boolean cancelable, boolean outsideCancelable, @Nullable IDialogCheckboxListener checkboxListener) {
        this(context, "", message, "", "", isChecked, cancelable, outsideCancelable, checkboxListener);
    }

    public CTCheckBoxDialog(@NonNull Context context, String title, String message, boolean isChecked, boolean cancelable, boolean outsideCancelable, @Nullable IDialogCheckboxListener checkboxListener) {
        this(context, title, message, "", "", isChecked, cancelable, outsideCancelable, checkboxListener);
    }

    public CTCheckBoxDialog(@NonNull Context context, String title, String message, String cancel, String confirm, boolean isChecked, boolean cancelable, boolean outsideCancelable, @Nullable IDialogCheckboxListener checkboxListener) {
        super(context);
//        this.context = context;
        this.title = title;
        this.message = message;
        this.cancel = cancel;
        this.confirm = confirm;
        this.isChecked = isChecked;
        this.checkboxListener = checkboxListener;
        setCancelable(cancelable);
        setCanceledOnTouchOutside(outsideCancelable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_checkbox_layout);

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
        cbSwitch = (CheckBox) findViewById(R.id.cb_switch);

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

        cbSwitch.setChecked(isChecked);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (checkboxListener != null) {
                    checkboxListener.onCancel(cbSwitch.isChecked());
                }
            }
        });

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (checkboxListener != null) {
                    checkboxListener.onConfirm(cbSwitch.isChecked());
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
