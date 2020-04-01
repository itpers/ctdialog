package com.beike.ctdialog.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beike.ctdialog.R;
import com.beike.ctdialog.iterface.IDialogCommonListener;

import jp.wasabeef.blurry.Blurry;

/**
 * Created by liupeng on 2017/6/19.
 */

public class CTIconDialog extends AlertDialog {

    private Context context;
    private TextView tvTitle;
    private TextView tvMessage;
    private TextView tvCancel;
    private TextView tvConfirm;
    private ImageView ivDialogType;
    private ViewGroup rootView;

    private String title;
    private String message;
    private String cancel;
    private String confirm;
    private boolean isShowCancel, cancelable, outsideCancelable;
    private int dialogType;

    private IDialogCommonListener commonListener;

    public CTIconDialog(@NonNull Context context, String title, String message, String cancel, String confirm, boolean isShowCancel, boolean cancelable, boolean outsideCancelable, int dialogType, ViewGroup rootView, @Nullable IDialogCommonListener commonListener) {
        super(context);
        this.context = context;
        this.title = title;
        this.message = message;
        this.cancel = cancel;
        this.confirm = confirm;
        this.commonListener = commonListener;
        this.isShowCancel = isShowCancel;
        this.dialogType = dialogType;
        this.cancelable = cancelable;
        this.rootView = rootView;
        this.outsideCancelable = outsideCancelable;
        setCancelable(cancelable);
        setCanceledOnTouchOutside(outsideCancelable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_icon_layout);

        Window window = getWindow();
        if (window != null) {
            DisplayMetrics metrics = new DisplayMetrics();
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            window.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            WindowManager.LayoutParams p = window.getAttributes();
            p.dimAmount = 0.0f;
            p.width = (int) (Math.min(metrics.widthPixels, metrics.heightPixels) * 0.8);
//            p.height = metrics.heightPixels;
            window.setAttributes(p);
            window.setBackgroundDrawableResource(R.color.transparent);

//            RelativeLayout rlDialog = findViewById(R.id.rl_dialog_layout);
//            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rlDialog.getLayoutParams();
//            layoutParams.width = (int) (Math.min(metrics.widthPixels, metrics.heightPixels) * 0.8);
//            rlDialog.setLayoutParams(layoutParams);
        }

        tvTitle = findViewById(R.id.tv_title);
        tvMessage = findViewById(R.id.tv_msg);
        tvCancel = findViewById(R.id.tv_cancel);
        tvConfirm = findViewById(R.id.tv_confirm);
        ivDialogType = findViewById(R.id.ic_dialog_type);

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

        if (!isShowCancel) {
            tvCancel.setVisibility(View.GONE);
            findViewById(R.id.line1).setVisibility(View.GONE);
            tvConfirm.setBackgroundResource(R.drawable.shape_dialog_bottom_half_dark_normal);
        } else if (!TextUtils.isEmpty(cancel)) {
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
                    commonListener.onCancel(true);
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

        this.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (commonListener != null) {
                    commonListener.onCancel(false);
                }
            }
        });

        switch (dialogType) {
            case Builder.DIALOG_TYPE_SUCCESS:
                ivDialogType.setImageResource(R.drawable.ic_dialog_success);
                break;
            case Builder.DIALOG_TYPE_ERROR:
                ivDialogType.setImageResource(R.drawable.ic_dialog_error);
                break;
            case Builder.DIALOG_TYPE_WARNING:
                ivDialogType.setImageResource(R.drawable.ic_dialog_warning);
                break;
        }
        if (rootView != null && ViewCompat.isAttachedToWindow(rootView)) {
            Blurry.with(context).sampling(3).animate().onto(rootView);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (rootView != null && ViewCompat.isAttachedToWindow(rootView)) {
            Blurry.delete(rootView);
        }
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
        private String title, message;
        private String confirm, cancel;
        private int dialogType;
        private boolean isTouchable = false, showCancel = true, cancelable = true;
        private ViewGroup rootView;
        private IDialogCommonListener clickListener;

        public static final int DIALOG_TYPE_SUCCESS = 0;
        public static final int DIALOG_TYPE_ERROR = 1;
        public static final int DIALOG_TYPE_WARNING = 2;

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

        public Builder setShowCancel(boolean showCancel) {
            this.showCancel = showCancel;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setOutsideTouchable(boolean touchable) {
            this.isTouchable = touchable;
            return this;
        }

        public Builder setDialogType(int dialogType) {
            this.dialogType = dialogType;
            return this;
        }

        public Builder setDialogListener(IDialogCommonListener clickListener) {
            this.clickListener = clickListener;
            return this;
        }

        public Builder setRootView(ViewGroup rootView) {
            this.rootView = rootView;
            return this;
        }

        public CTIconDialog create() {
            final CTIconDialog dialog = new CTIconDialog(context, title, message, cancel, confirm, showCancel, cancelable, isTouchable, dialogType, rootView, clickListener);
            dialog.show();
            return dialog;
        }
    }
}
