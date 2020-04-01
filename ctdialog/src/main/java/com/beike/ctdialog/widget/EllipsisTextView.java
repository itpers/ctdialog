package com.beike.ctdialog.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

public class EllipsisTextView extends AppCompatTextView {
    private int suffixSize;

    public EllipsisTextView(Context context) {
        super(context);
    }

    public EllipsisTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EllipsisTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getLineCount() == 0) {
            return;
        }
        if (getLayout() == null) {
            return;

        }
        int ellipsisCount = getLayout().getEllipsisCount(getLineCount() - 1);
        if (ellipsisCount == 0) {
            return;
        }

        String content = getText().toString();
        Log.i("itper", "onMeasure: 1 content = " + content);
        String lastChar = content.substring(content.length() - 1 - suffixSize);
        content = content.substring(0, content.length() - ellipsisCount - suffixSize - 1);
        Log.i("itper", "onMeasure: 2 content = " + content);
        setText(content + "..." + lastChar);
    }

    public void setTexts(String text, String ext) {
        if (ext != null) {
            suffixSize = ext.length() + 1;
            setText(text + "." + ext);
        } else {
            suffixSize = 0;
            setText(text);
        }

    }
}
