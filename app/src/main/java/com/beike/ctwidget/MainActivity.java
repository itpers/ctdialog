package com.beike.ctwidget;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.beike.ctdialog.dialog.CTCheckBoxDialog;
import com.beike.ctdialog.dialog.CTCommonDialog;
import com.beike.ctdialog.dialog.CTVerticalDialog;
import com.beike.ctdialog.dialog.CTInputDialog;
import com.beike.ctdialog.iterface.IDialogCheckboxListener;
import com.beike.ctdialog.iterface.IDialogVerticalListener;
import com.beike.ctdialog.iterface.IItemClickListener;
import com.beike.ctdialog.loading.CTIOSLoading;
import com.beike.ctdialog.loading.CTLoading;
import com.beike.ctdialog.pageLoading.CTPageLoading;
import com.beike.ctdialog.pickpopup.CTPickPopup;
import com.beike.ctdialog.popMenu.PopMenuItem;
import com.beike.ctdialog.popupMenue.CTPopupMenu;
import com.beike.ctdialog.progress.CTProgressDialog;
import com.beike.ctdialog.iterface.IDialogInputListener;
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

        findViewById(R.id.bt_checkbox_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCheckboxDilaog();
            }
        });

        findViewById(R.id.bt_input_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });

        findViewById(R.id.bt_vertical_bt_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVerticalDilaog();
            }
        });

        findViewById(R.id.bt_pick_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickPopup();
            }
        });

        findViewById(R.id.bt_page_loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPageLoading();
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

        findViewById(R.id.popup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup(v);
            }
        });

        findViewById(R.id.popup1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup(view);
            }
        });

        findViewById(R.id.popup2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup(view);
            }
        });
    }


    public void showCheckboxDilaog() {
        new CTCheckBoxDialog.Builder(context)
                .setTitle("测试测试测试")
                .setMessage("aaaaaaaaaa")
                .setIsChecked(true)
                .setDialogListener(new IDialogCheckboxListener() {
                    @Override
                    public void onConfirm(boolean isChecked) {

                    }

                    @Override
                    public void onCancel(boolean isChecked) {

                    }
                }).create();
    }


    public void showCommonDilaog() {
        new CTCommonDialog.Builder(context)
                .setTitle("测试测试测试")
                .setDialogListener(new IDialogCommonListener() {
                    @Override
                    public void onConfirm() {

                    }

                    @Override
                    public void onCancel(boolean isButton) {

                    }
                }).create();
    }

    public void showInputDialog() {
        new CTInputDialog.Builder(context)
                .setTitle("重命名")
                .setInputDefault("原名字")
                .setInputHint("请输入新名称")
                .setLengthLimit(5)
                .setInputDialogListener(new IDialogInputListener() {
                    @Override
                    public void onConfirm(String input) {
                        Toast.makeText(context, input+"", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {

                    }
                }).create();
    }
    public void showVerticalDilaog() {
        new CTVerticalDialog.Builder(context)
                .setTitle("您对XXX提供的服务还满意吗？")
                .setTop("不是很满意")
                .setMiddle("非常满意")
                .setBottom("非常满意，鼓励一下")
                .setDialogListener(new IDialogVerticalListener() {
                    @Override
                    public void onTop() {
                    }

                    @Override
                    public void onMiddle() {

                    }

                    @Override
                    public void onBottom() {

                    }
                }).create();
    }

    public void showPageLoading() {
        CTPageLoading pageLoading = new CTPageLoading
                .Builder(context)
                .isShowMessage(false)
                .build();
        pageLoading.show();
    }

    public void showLoadingDilalog() {
        CTIOSLoading loadingDialog = new CTIOSLoading
                .Builder(context)
                .isShowMessage(false)
                .setCancelDialogListener(new ICancelDialogListener() {
                    @Override
                    public void cancel() {
                        showLoadingDilalog1();
                    }
                })
                .build();
        loadingDialog.show();
    }

    public void showLoadingDilalog1() {
        CTLoading processDialog = new CTLoading(context);
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

    public void popup(View target) {
        new CTPopupMenu.Builder(context)
                .addItem(new PopMenuItem(context).setTitle("测试测试").setEnable(false))
                .addItem(new PopMenuItem(context).setTitle("测试测试"))
                .addItem(new PopMenuItem(context).setTitle("测试测试"))
                .addItem(new PopMenuItem(context).setTitle("测试测试"))
                .setItemClickListener(new IItemClickListener() {
                    @Override
                    public void onClick(int buttonIndex) {
                        Toast.makeText(context, "index: " + buttonIndex, Toast.LENGTH_SHORT).show();
                    }
                })
                .create()
                .showAsDropDown(target);

//        new PopMenu(context)
//                .addItem(new PopMenuItem(context).setTitle("测试测试"))
//                .addItem(new PopMenuItem(context).setTitle("测试测测"))
//                .addItem(new PopMenuItem(context).setTitle("测试测"))
//                .addItem(new PopMenuItem(context).setTitle("测试测试").setEnable(false))
//                .setTargetView(target)
////                .setBubbleColor(Color.TRANSPARENT)
////                .setTransParentBackground()
//                .addContent()
//                .setItemClickListener(new IItemClickListener() {
//                    @Override
//                    public void onClick(int buttonIndex) {
//                        Toast.makeText(context, "点击了: " + buttonIndex, Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .show(target);
    }

    public void showPickPopup() {

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
                                showSelectPopup1();
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

    public void showSelectPopup1() {

        new CTPickPopup.Builder(context)
                .setTitle("标题")
                .addAction("照片")
                .addAction("相机")
                .addAction("照片1")
                .addAction("相机1")
                .addAction("照片2")
                .addAction("相机2")
                .addAction("照片3")
                .addAction("相机3")
                .addAction("照片4")
                .addAction("相机4")
                .addAction("照片5")
                .addAction("相机5")
                .addAction("照片6")
                .addAction("相机6")
                .addAction("照片7")
                .addAction("相机7")
                .addAction("照片8")
                .addAction("相机8")
                .addAction("照片9")
                .addAction("相机9")
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
                        }
                    }
                })
                .create()
                .showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    public void showShare() {

        new CTSharePopup.Builder(context)
                .addShare(context.getResources().getDrawable(R.drawable.ic_share_to_wx_friend), "微信好友")
                .addShare(context.getResources().getDrawable(R.drawable.ic_share_to_wx_timeline), "朋友圈")
                .addShare(context.getResources().getDrawable(R.drawable.ic_share_to_dd), "钉钉")
                .addControl(context.getResources().getDrawable(R.drawable.ic_web_control_refresh), "刷新")
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
                .addShare(context.getResources().getDrawable(R.drawable.ic_share_to_wx_friend), "微信好友")
                .addShare(context.getResources().getDrawable(R.drawable.ic_share_to_wx_timeline), "朋友圈")
                .addShare(context.getResources().getDrawable(R.drawable.ic_share_to_dd), "钉钉")
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
                .addShare(context.getResources().getDrawable(R.drawable.ic_share_to_wx_friend), "微信好友")
                .addShare(context.getResources().getDrawable(R.drawable.ic_share_to_wx_timeline), "朋友圈")
                .addShare(context.getResources().getDrawable(R.drawable.ic_share_to_dd), "钉钉")
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
