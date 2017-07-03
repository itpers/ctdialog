package com.beike.ctdialog.iterface;

/**
 * Created by liupeng on 2017/6/19.
 */

public interface IDialogInputListener {

    /**
     * 确认
     */
    public void onConfirm(String input);

    /**
     * 对话框取消
     */
    public void onCancel();
}
