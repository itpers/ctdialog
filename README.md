## ctdialog

实现 Android各种对话框

### 普通对话框 CTCommonDialog

```java
CTCommonDialog commonDialog = new CTCommonDialog(context, "测试测试测试测", new IDialogCommonListener() {
    @Override
    public void onConfirm() {
    }

    @Override
    public void onCancel() {
    }
});
commonDialog.show();
```

CTCommonDialog 构造函数可接收多个参数 , 默认重载了5个构造函数, 以适应不同的情况, 参数解释如下:

```java
public CTCommonDialog(@NonNull Context context, String title, String message, String cancel, String confirm, boolean cancelable, boolean outsideCancelable, @Nullable IDialogCommonListener commonListener){
    /*
    title: 可选, 标题, 默认显示 '提示'字样;
    message: 必填, 提示内容;
    cancel: 可选, 取消按钮文字, 默认显示 '取消' 字样, 传null 则不显示取消按钮 ;
    confirm: 可选, 确定按钮, 默认显示 '确定';
    cancelable: 可选, 是否可以取消弹框, 默认true
    outsideCancelable: 可选, 点击外部是否可以取消弹框, 默认true
    commonListener: 点击回调
    */
}
```

### 输入对话框 CTInputDialog

```java
CTInputDialog inputDialog = new CTInputDialog(context,"标题", "请输入名字", new IDialogInputListener() {
    @Override
    public void onConfirm(String input) {
        Toast.makeText(context, input+"", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onCancel() {
    }
});
inputDialog.show();
```

CTInputDialog 构造函数可接收多个参数 , 默认重载了5个构造函数, 以适应不同的情况, 参数解释如下:

```java
public CTInputDialog(@NonNull Context context, String title, String inputHint, String cancel, String confirm, boolean cancelable, boolean outsideCancelable, @Nullable IDialogInputListener inputListener){
    /*
    title: 可选, 标题, 默认显示 '提示'字样;
    inputHint: 可选, 输入框占位字符;
    cancel: 可选, 取消按钮文字, 默认显示 '取消' 字样, 传null 则不显示取消按钮 ;
    confirm: 可选, 确定按钮, 默认显示 '确定';
    cancelable: 可选, 是否可以取消弹框, 默认true
    outsideCancelable: 可选, 点击外部是否可以取消弹框, 默认true
    commonListener: 点击回调
    */
}
```

### 加载对话框 分两种 一种iOS小菊花风格CTIOSLoadingDialog, 另一种Android风格CTLoadingDialog

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

### iOS风格选择框

```java
new CTPickPopup.Builder(context)
                .setTitle("标题")
                .addAction("照片")
                .addAction("相机")
                .addConfirmAction("退出") //字体红色, 起到提醒确认作用
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

### iOS风格, 分享,选择等多功能操作框

```java
new CTSharePopup.Builder(context) // addShare表示上半部分, addControl表示下半部分, 均可无限添加
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