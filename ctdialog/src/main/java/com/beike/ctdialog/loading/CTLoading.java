package com.beike.ctdialog.loading;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.beike.ctdialog.R;
import com.beike.ctdialog.iterface.ICancelDialogListener;
import com.beike.ctdialog.utils.DensityUtil;

/**
 * 自定义加载对话框
 * Created by liupeng on 2017/5/19.
 */
public class CTLoading extends AlertDialog {

    private Context context = null;

    private ImageView ivLoading = null;
    private TextView tvMessage = null;

    private String message;

    private ICancelDialogListener cancelDialogListener;


    public CTLoading(@Nullable Context context) {
        this(context, null, true, false, null);
    }


    public CTLoading(@Nullable Context context, String message) {
        this(context, message, true, false, null);
    }


    public CTLoading(@Nullable Context context, String message, boolean cancelable, boolean outsideCancelable, ICancelDialogListener cancelDialogListener) {
        super(context);
        this.context = context;
        this.message = message;
        this.cancelDialogListener = cancelDialogListener;
        setCancelable(cancelable);
        setCanceledOnTouchOutside(outsideCancelable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_process_layout);

        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.dimAmount = 0.0f;
        p.width = DensityUtil.dip2px(context, 120);
        p.height = DensityUtil.dip2px(context, 120);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        getWindow().setAttributes(p);

        ivLoading = (ImageView) findViewById(R.id.iv_loading);
        tvMessage = (TextView) findViewById(R.id.tv_msg);

        Animation rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.ct_loading);
        ivLoading.startAnimation(rotateAnimation);

        setMessage(message);
    }

    /**
     * 设置内容
     *
     * @param message 内容
     */
    public void setMessage(String message) {
        if (tvMessage == null) return;
        if (!TextUtils.isEmpty(message)) {
            tvMessage.setText(message);
            tvMessage.setVisibility(View.VISIBLE);
        } else {
            tvMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public void cancel() {
        super.cancel();
        if (cancelDialogListener != null) {
            cancelDialogListener.cancel();
        }
    }

    public void setCancelDialogListener(ICancelDialogListener cancelDialogListener) {
        this.cancelDialogListener = cancelDialogListener;
    }
}
