package com.beike.ctdialog.loading;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.TextView;

import com.beike.ctdialog.R;
import com.beike.ctdialog.iterface.ICancelDialogListener;
import com.beike.ctdialog.utils.DensityUtil;

/**
 * 自定义加载对话框
 * Created by liupeng on 2017/5/19.
 */
public class CTIOSLoadingDialog extends AlertDialog {

    private Context context = null;

    private TextView tvMessage = null;

    private String message;

    private ICancelDialogListener cancelDialogListener;


    public CTIOSLoadingDialog(@Nullable Context context) {
        this(context, null, true, false, null);
    }


    public CTIOSLoadingDialog(@Nullable Context context, String message) {
        this(context, message, true, false, null);
    }


    public CTIOSLoadingDialog(@Nullable Context context, String message, boolean cancelable, boolean outsideCancelable, ICancelDialogListener cancelDialogListener) {
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

        setContentView(R.layout.dialog_ios_loading_layout);

        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.dimAmount = 0.0f;
        p.width = DensityUtil.dip2px(context, 100);
        p.height = DensityUtil.dip2px(context, 100);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        getWindow().setAttributes(p);

        tvMessage = (TextView) findViewById(R.id.tv_msg);

        setMessage(message);
    }

    /**
     * 设置内容
     * @param message 内容
     */
    public void setMessage(String message) {
        if (tvMessage != null && !TextUtils.isEmpty(message)) {
            tvMessage.setText(message);
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
