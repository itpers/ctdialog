package com.beike.ctdialog.progress;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beike.ctdialog.R;
import com.beike.ctdialog.iterface.ICancelDialogListener;
import com.beike.ctdialog.utils.DensityUtil;

/**
 * Created by liupeng on 2017/5/19.
 */

public class CTProgressDialog extends AlertDialog {

    private Context context;

    private ProgressBar progressBar;
    private TextView tvProgress;

    private String message;

    private ICancelDialogListener cancelDialogListener;

    public CTProgressDialog(@NonNull Context context) {
        this(context, null, true, false, null);
    }

    public CTProgressDialog(@NonNull Context context, String message) {
        this(context, message, true, false, null);
    }

    public CTProgressDialog(@NonNull Context context, String message, boolean cancelable, boolean outsideCancelable, ICancelDialogListener cancelDialogListener) {
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

        setContentView(R.layout.dialog_progress_layout);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.dimAmount = 0.4f;
        params.width = DensityUtil.dip2px(context, 240);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        getWindow().setAttributes(params);

        tvProgress = (TextView) findViewById(R.id.tv_progress);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        setMessage(message);
    }

    public void setMessage(String message) {
        if (tvProgress != null && message != null) {
            tvProgress.setText(message);
        } else if ("".equals(message)) {
            tvProgress.setVisibility(View.GONE);
        }
    }

    public void setProgress(int progress){
        setProgress(progress, true);
    }

    public void setProgress(int progress, boolean updateMessage){
        if (tvProgress != null && updateMessage) {
            tvProgress.setText("进度: " + progress + "%");
        }
        if (progressBar != null) {
            progressBar.setProgress(progress);
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

    @Override
    public void show() {
        super.show();
    }
}
