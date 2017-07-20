package com.beike.ctwidget;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.beike.ctdialog.dialog.CTCommonDialog;
import com.beike.ctdialog.loading.CTIOSLoadingDialog;
import com.beike.ctdialog.dialog.CTInputDialog;
import com.beike.ctdialog.loading.CTLoadingDialog;
import com.beike.ctdialog.progress.CTProgressDialog;
import com.beike.ctdialog.iterface.IDialogInputListener;
import com.beike.ctdialog.pickpopup.CTPickPopup;
import com.beike.ctdialog.iterface.IActionClickListener;
import com.beike.ctdialog.iterface.ICancelDialogListener;
import com.beike.ctdialog.iterface.IDialogCommonListener;
import com.beike.ctdialog.sharepopup.CTSharePopup;

public class MainActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        findViewById(R.id.bt_common_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommonDilaog();
            }
        });

        findViewById(R.id.bt_input_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });

        findViewById(R.id.bt_pick_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickPopup();
            }
        });

        findViewById(R.id.bt_share_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });

        findViewById(R.id.bt_loding_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDilalog();
            }
        });

        findViewById(R.id.bt_progress_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
            }
        });
    }


    public void showCommonDilaog() {
        CTCommonDialog commonDialog = new CTCommonDialog(context, "测试测试测试测", new IDialogCommonListener() {
            @Override
            public void onConfirm() {
            }

            @Override
            public void onCancel() {

            }
        });
        commonDialog.show();
    }

    public void showInputDialog() {
        CTInputDialog inputDialog = new CTInputDialog(context,"新建文档", "请输入名字", new IDialogInputListener() {
            @Override
            public void onConfirm(String input) {
                Toast.makeText(context, input+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }
        });
        inputDialog.show();
    }

    public void showLoadingDilalog() {
        CTIOSLoadingDialog loadingDialog = new CTIOSLoadingDialog(context);
        loadingDialog.show();
        loadingDialog.setCancelDialogListener(new ICancelDialogListener() {
            @Override
            public void cancel() {
                showLoadingDilalog1();
            }
        });
    }

    public void showLoadingDilalog1() {
        CTLoadingDialog processDialog = new CTLoadingDialog(context);
        processDialog.show();
        processDialog.setCancelDialogListener(new ICancelDialogListener() {
            @Override
            public void cancel() {
            }
        });
    }

    public void showProgressDialog() {
        CTProgressDialog progressDialog = new CTProgressDialog(context);
        progressDialog.show();
        progressDialog.setProgress(20);
        progressDialog.setCancelDialogListener(new ICancelDialogListener() {
            @Override
            public void cancel() {
            }
        });
    }

    public void showPickPopup() {

        new CTPickPopup.Builder(context)
                .setTitle("标题")
                .addAction("照片")
                .addAction("相机")
                .setActionClickListener(new IActionClickListener() {
                    @Override
                    public void onClick(int buttonIndex) {
                        switch (buttonIndex) {
                            case 0:
                                Toast.makeText(context, "点击了取消", Toast.LENGTH_SHORT).show();
                                showSelectPopup1();
                                break;
                            case 1:
                                Toast.makeText(context, "点击了照片", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Toast.makeText(context, "点击了相机", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                })
                .create()
                .showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    public void showSelectPopup1() {

        new CTPickPopup.Builder(context)
                .setTitle("标题")
                .addAction("照片")
                .addAction("相机")
                .addConfirmAction("退出")
                .setActionClickListener(new IActionClickListener() {
                    @Override
                    public void onClick(int buttonIndex) {
                        switch (buttonIndex) {
                            case 0:
                                Toast.makeText(context, "点击了取消", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(context, "点击了照片", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Toast.makeText(context, "点击了相机", Toast.LENGTH_SHORT).show();
                                break;
                            case 3:
                                Toast.makeText(context, "点击了退出", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                })
                .create()
                .showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    public void showShare() {

        new CTSharePopup.Builder(context)
                .addShare(context.getResources().getDrawable(R.mipmap.ic_share_to_wx_friend), "微信好友")
                .addShare(context.getResources().getDrawable(R.mipmap.ic_share_to_wx_timeline), "朋友圈")
                .addShare(context.getResources().getDrawable(R.mipmap.ic_share_to_dd), "钉钉")
                .addControl(context.getResources().getDrawable(R.mipmap.ic_web_control_refresh), "刷新")
                .setTitle(R.string.ct_dialog_title)
                .setActionClickListener(new IActionClickListener() {
                    @Override
                    public void onClick(int buttonIndex) {
                        switch (buttonIndex) {
                            case 0:
                                Toast.makeText(context, "取消", Toast.LENGTH_SHORT).show();
                                showShare1();
                                break;
                            case 1:
                                Toast.makeText(context, "微信好友", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Toast.makeText(context, "朋友圈", Toast.LENGTH_SHORT).show();
                                break;
                            case 3:
                                Toast.makeText(context, "钉钉", Toast.LENGTH_SHORT).show();
                                break;
                            case 4:
                                Toast.makeText(context, "刷新", Toast.LENGTH_SHORT).show();

                        }
                    }
                })
                .create()
                .showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    public void showShare1() {

        new CTSharePopup.Builder(context)
                .addShare(context.getResources().getDrawable(R.mipmap.ic_share_to_wx_friend), "微信好友")
                .addShare(context.getResources().getDrawable(R.mipmap.ic_share_to_wx_timeline), "朋友圈")
                .addShare(context.getResources().getDrawable(R.mipmap.ic_share_to_dd), "钉钉")
                .setActionClickListener(new IActionClickListener() {
                    @Override
                    public void onClick(int buttonIndex) {
                        switch (buttonIndex) {
                            case 0:
                                Toast.makeText(context, "取消", Toast.LENGTH_SHORT).show();
                                showShare2();
                                break;
                            case 1:
                                Toast.makeText(context, "微信好友", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Toast.makeText(context, "朋友圈", Toast.LENGTH_SHORT).show();
                                break;
                            case 3:
                                Toast.makeText(context, "钉钉", Toast.LENGTH_SHORT).show();
                                break;
                            case 4:
                                Toast.makeText(context, "刷新", Toast.LENGTH_SHORT).show();

                        }
                    }
                })
                .create()
                .showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    public void showShare2() {

        new CTSharePopup.Builder(context)
                .addShare(context.getResources().getDrawable(R.mipmap.ic_share_to_wx_friend), "微信好友")
                .addShare(context.getResources().getDrawable(R.mipmap.ic_share_to_wx_timeline), "朋友圈")
                .addShare(context.getResources().getDrawable(R.mipmap.ic_share_to_dd), "钉钉")
                .setTitle("分享到:")
                .setActionClickListener(new IActionClickListener() {
                    @Override
                    public void onClick(int buttonIndex) {
                        switch (buttonIndex) {
                            case 0:
                                Toast.makeText(context, "取消", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(context, "微信好友", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Toast.makeText(context, "朋友圈", Toast.LENGTH_SHORT).show();
                                break;
                            case 3:
                                Toast.makeText(context, "钉钉", Toast.LENGTH_SHORT).show();
                                break;
                            case 4:
                                Toast.makeText(context, "刷新", Toast.LENGTH_SHORT).show();

                        }
                    }
                })
                .create()
                .showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }
}
