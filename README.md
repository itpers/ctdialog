## ctdialog

实现 Android各种对话框 示例见:app/src/main/java/com/beike/ctwidget/MainActivity.java

### 普通对话框 CTCommonDialog

```java
        new CTCommonDialog.Builder(context)
                .setTitle("测试测试测试")
                .setDialogListener(new IDialogCommonListener() {
                    @Override
                    public void onConfirm() {

                    }

                    @Override
                    public void onCancel() {

                    }
                }).create();
```

CTCommonDialog 采用链式结构:

```java
/*
    setTitle: 可选, 标题, 默认显示 '提示'字样;
    setMessage: 可选, 无值默认隐藏;
    setCancel: 可选, 取消按钮文字, 默认显示 '取消' 字样, 传null 则不显示取消按钮 ;
    setConfirm: 可选, 确定按钮, 默认显示 '确定';
    setShowCancel: 可选, 是否显示取消按钮, 默认显示;
    setIsCancelable: 可选, 是否可以取消弹框, 默认true
    setOutsideTouchable: 可选, 点击外部是否可以取消弹框, 默认true
    setDialogListener: 点击回调
    */
```

### 输入对话框 CTInputDialog

```java
        new CTInputDialog.Builder(context)
                .setTitle("重命名")
                .setInputDefault("原名字")
                .setInputHint("请输入新名称")
                .setInputDialogListener(new IDialogInputListener() {
                    @Override
                    public void onConfirm(String input) {
                        Toast.makeText(context, input+"", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {

                    }
                }).create();
```

CTInputDialog 构造函数可接收多个参数 , 默认重载了5个构造函数, 以适应不同的情况, 参数解释如下:

```java
    /*
    setTitle: 可选, 标题, 默认显示 '提示'字样;
    setInputHint: 可选, 输入框hint;
    setInputDefault: 可选, 输入框默认文字 ;
    setConfirm: 可选, 确定按钮, 默认显示 '确定';
    setShowCancel: 可选, 是否显示取消按钮, 默认显示;
    setOutsideTouchable: 可选, 点击外部是否可以取消弹框, 默认true
    setInputDialogListener: 点击回调
    */
```
![普通弹窗](https://github.com/itpers/ctdialog/blob/master/img/ct_common_dialog.png)
![带选中弹窗](https://github.com/itpers/ctdialog/blob/master/img/ct_check_dialog.png)
![输入弹窗](https://github.com/itpers/ctdialog/blob/master/img/ct_input_dialog.png)
![垂直弹窗](https://github.com/itpers/ctdialog/blob/master/img/ct_vertical_dialog.png)

### 加载对话框 分两种 一种iOS小菊花风格CTIOSLoadingDialog, 另一种圈圈风格CTLoadingDialog

```java
CTIOSLoadingDialog loadingDialog = new CTIOSLoadingDialog(context, msg/*msg可选,不传默认显示'加载中...'*/);
loadingDialog.show();
loadingDialog.setCancelDialogListener(new ICancelDialogListener() { //监听加载框消失
    @Override
    public void cancel() {
    }
});
//CTLoadingDialog 用法同上
```

![iOS风格loading](https://github.com/itpers/ctdialog/blob/master/img/ct_ios_loading.png)
![普通loading](https://github.com/itpers/ctdialog/blob/master/img/ct_common_loading.png)

### iOS风格选择框, 可无限add..

```java
new CTPickPopup.Builder(context)
                .setTitle("标题") //可选, 默认不显示标题
                .addAction("照片")
                .addAction("相机")
                .addConfirmAction("退出") //addConfirmAction为红色字体, 起到提醒确认作用
                .setActionClickListener(new IActionClickListener() {
                    @Override
                    public void onClick(int buttonIndex) {
                        switch (buttonIndex) { //0表示取消操作, 按addAction的顺序, 一次从1开始
                            case 0:
                                //点击了取消
                                break;
                            case 1:
                                //点击了照片
                                break;
                            case 2:
                                //点击了相机
                                break;
                            case 3:
                                //点击了退出
                                break;
                        }
                    }
                })
                .create()
                .showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
```

![选择](https://github.com/itpers/ctdialog/blob/master/img/ct_selected_dialog.png)

### iOS风格, 分享,选择等多功能操作框

```java
new CTSharePopup.Builder(context) // addShare表示上半部分, addControl表示下半部分, 均可无限add..
                .addShare(context.getResources().getDrawable(R.mipmap.ic_share_to_wx_friend), "微信好友") //参数分别对应 要显示的图标和文字, 文字可不传, 只显示图片
                .addShare(context.getResources().getDrawable(R.mipmap.ic_share_to_wx_timeline), "朋友圈")
                .addShare(context.getResources().getDrawable(R.mipmap.ic_share_to_dd), "钉钉")
                .addControl(context.getResources().getDrawable(R.mipmap.ic_web_control_refresh), "刷新")
                .setTitle(R.string.ct_dialog_title)
                .setTitle("分享到:") //不设置title 则不显示
                .setActionClickListener(new IActionClickListener() {
                    @Override
                    public void onClick(int buttonIndex) {
                        switch (buttonIndex) { //index 规则同CTPickPopup
                            case 0:
                                //取消
                                break;
                            case 1:
                                //微信好友
                                break;
                            case 2:
                                //朋友圈
                                break;
                            case 3:
                                //钉钉
                                break;
                            case 4:
                                //刷新

                        }
                    }
                })
                .create()
                .showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
```

![分享弹窗](https://github.com/itpers/ctdialog/blob/master/img/ct_share_popup.png)
![分享弹窗](https://github.com/itpers/ctdialog/blob/master/img/ct_share_popup1.png)

### 进度条
![进度条](https://github.com/itpers/ctdialog/blob/master/img/ct_progress_dialog.png)
