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

    private IDialogCheckboxListener checkboxListener;

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
        } else {
            tvTitle.setText(title);
        }

        if (message == null) {
            tvMessage.setVisibility(View.GONE);
        } else {
            tvMessage.setText(message);
        }

        if (!TextUtils.isEmpty(cancel)){
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

    public static class Builder {
        public Context context;
        public String title, message, inputDefault;
        public String confirm, cancel;
        public boolean isTouchable = false, isChecked = true, cancelable = true;
        public IDialogCheckboxListener clickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            if (TextUtils.isEmpty(title)) return this;
            this.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            if (TextUtils.isEmpty(message)) return this;
            this.message = message;
            return this;
        }

        public Builder setInputDefault(String inputDefault) {
            if (TextUtils.isEmpty(inputDefault)) return this;
            this.inputDefault = inputDefault;
            return this;
        }

        public Builder setConfirm(String confirm) {
            if (TextUtils.isEmpty(confirm)) return this;
            this.confirm = confirm;
            return this;
        }

        public Builder setCancel(String cancel) {
            if (TextUtils.isEmpty(cancel)) return this;
            this.cancel = cancel;
            return this;
        }

        public Builder setIsChecked(boolean isChecked) {
            this.isChecked = isChecked;
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

        public Builder setDialogListener(IDialogCheckboxListener clickListener) {
            this.clickListener = clickListener;
            return this;
        }

        public CTCheckBoxDialog create() {
            CTCheckBoxDialog dialog = new CTCheckBoxDialog(context, title, message, cancel, confirm, isChecked, cancelable, isTouchable, clickListener);
            dialog.show();
            return dialog;
        }
    }
}
