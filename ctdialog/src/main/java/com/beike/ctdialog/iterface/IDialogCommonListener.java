package com.beike.ctdialog.iterface;

/**
 * Created by liupeng on 2017/6/19.
 */

public interface IDialogCommonListener {

    /**
     * 确认
     */
    public void onConfirm();

    /**
     * 对话框取消
     * isbutton : 是否是点击取消按钮出发
     */
    public void onCancel(boolean isButton);
}
