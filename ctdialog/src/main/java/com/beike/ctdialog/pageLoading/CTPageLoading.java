package com.beike.ctdialog.pageLoading;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.beike.ctdialog.R;
import com.beike.ctdialog.dialog.CTVerticalDialog;
import com.beike.ctdialog.iterface.ICancelDialogListener;
import com.beike.ctdialog.utils.DensityUtil;

/**
 * 自定义加载对话框
 * Created by liupeng on 2017/5/19.
 */
public class CTPageLoading extends AlertDialog {

    private TextView tvMessage = null;

    private ICancelDialogListener cancelDialogListener;


    public CTPageLoading(@Nullable Context context) {
        super(context);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.page_loading_layout);

        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.dimAmount = 0.0f;
//        p.width = DensityUtil.dip2px(context, 100);
//        p.height = DensityUtil.dip2px(context, 100);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        getWindow().setAttributes(p);

        tvMessage = findViewById(R.id.tv_msg);

//        setMessage(message);
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
        }
    }

    public void isShowMsg(boolean isShowMsg) {
        if (tvMessage == null) return;
        tvMessage.setVisibility(isShowMsg ? View.VISIBLE : View.GONE);
    }

    public void isCancelAble(boolean isCancelAble) {
        setCancelable(isCancelAble);
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

    public static class Builder {
        private Context context;
        private String msg;
        private boolean isShowMsg = true, isCancelAble = true;
        private ICancelDialogListener cancelDialogListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder isShowMessage(boolean isShowMsg) {
            this.isShowMsg = isShowMsg;
            return this;
        }

        public Builder isCancelAble(boolean isCancelAble) {
            this.isCancelAble = isCancelAble;
            return this;
        }

        public Builder setCancelDialogListener(ICancelDialogListener cancelDialogListener) {
            this.cancelDialogListener = cancelDialogListener;
            return this;
        }

        public CTPageLoading build() {
            CTPageLoading pageLoading = new CTPageLoading(context);
            pageLoading.setMessage(msg);
            pageLoading.isShowMsg(isShowMsg);
            pageLoading.setCancelable(isCancelAble);
            return pageLoading;
        }
    }
}
