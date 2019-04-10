package com.beike.ctdialog.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.beike.ctdialog.R;
import com.beike.ctdialog.iterface.IDialogInputListener;

/**
 * Created by liupeng on 2017/6/15.
 */
public class CTInputDialog extends AlertDialog {
    public Context context;
    public String title, inputHint, inputDefault;
    public String confirm, cancel;
    public boolean isShowCancel;
    public IDialogInputListener inputListener;

    private TextView tvTitle;
    private EditText etContent;
    private TextView tvCancel;
    private TextView tvConfirm;

    public CTInputDialog(@NonNull Context context, String title, String inputHint, String inputDefault, String cancel, String confirm, boolean isShowCancel, boolean outsideCancelable, @Nullable IDialogInputListener inputListener) {
        super(context);
        this.title = title;
        this.inputHint = inputHint;
        this.cancel = cancel;
        this.confirm = confirm;
        this.inputListener = inputListener;
        this.inputDefault = inputDefault;
        this.isShowCancel = isShowCancel;
//        setCancelable(isShowCancel);
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
        etContent = (EditText) findViewById(R.id.tv_msg);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);

        if (title == null) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }

        if (inputHint != null) {
            etContent.setHint(inputHint);
        }

        if (!TextUtils.isEmpty(inputDefault)) {
            etContent.setText(inputDefault);
            etContent.setSelection(inputDefault.length());
            tvConfirm.setEnabled(true);
        }

        if (!isShowCancel) {
            tvCancel.setVisibility(View.GONE);
            findViewById(R.id.line1).setVisibility(View.GONE);
            tvConfirm.setBackgroundResource(R.drawable.selector_shape_dialog_bottom_half);
        } else if (!TextUtils.isEmpty(cancel)) {
            tvCancel.setText(cancel);
        }

        if (!TextUtils.isEmpty(confirm)) {
            tvConfirm.setText(confirm);
        }

        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tvConfirm.setEnabled(getInput().trim().length() > 0);
            }
        });

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

    public String getInput() {
        return etContent.getText().toString();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public static class Builder {
        public Context context;
        public String title, inputHint, inputDefault;
        public String confirm, cancel;
        public boolean isTouchable = false, showCancel = true;
        public IDialogInputListener clickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            if (TextUtils.isEmpty(title)) return this;
            this.title = title;
            return this;
        }

        public Builder setInputHint(String inputHint) {
            if (TextUtils.isEmpty(inputHint)) return this;
            this.inputHint = inputHint;
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

        public Builder setIsShowCancel(boolean showCancel) {
            this.showCancel = showCancel;
            return this;
        }

        public Builder setOutsideTouchable(boolean touchable) {
            this.isTouchable = touchable;
            return this;
        }

        public Builder setInputDialogListener(IDialogInputListener clickListener) {
            this.clickListener = clickListener;
            return this;
        }

        public CTInputDialog create() {
            final CTInputDialog dialog = new CTInputDialog(context, title, inputHint, inputDefault, cancel, confirm, showCancel, isTouchable, clickListener);
            dialog.show();
            return dialog;
        }
    }
}
