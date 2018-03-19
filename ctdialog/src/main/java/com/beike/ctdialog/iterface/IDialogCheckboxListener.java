package com.beike.ctdialog.iterface;

/**
 * Created by liupeng on 2017/6/19.
 */

public interface IDialogCheckboxListener {

    /**
     * 确认
     */
    public void onConfirm(boolean isChecked);

    /**
     * 对话框取消
     */
    public void onCancel(boolean isChecked);
}
