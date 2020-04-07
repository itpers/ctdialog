package com.beike.ctdialog.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

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
        String lastChar = content.substring(content.length() - 1 - suffixSize);
        float lastCharWidth = getTextWidth(lastChar);
        int ellipsisIndex = content.length() - 1 - ellipsisCount;
        int replaceIndex = ellipsisIndex - lastChar.length();
        String replaceChar = content.substring(replaceIndex, ellipsisIndex);
        float replaceWidth = getTextWidth(replaceChar);
        if (lastCharWidth > replaceWidth && replaceIndex > 0) {
            replaceIndex--;
            replaceChar = content.substring(replaceIndex, ellipsisIndex);
            while (lastCharWidth > getTextWidth(replaceChar) && replaceIndex > 0) {
                replaceChar = content.substring(replaceIndex, ellipsisIndex);
                replaceIndex--;
            }
            if (replaceIndex < content.length()) {
                replaceIndex++;
            }
        } else if (lastCharWidth < replaceWidth && replaceIndex < ellipsisIndex) {
            replaceIndex++;
            replaceChar = content.substring(replaceIndex, ellipsisIndex);
            while (lastCharWidth < getTextWidth(replaceChar) && replaceIndex < ellipsisIndex) {
                replaceChar = content.substring(replaceIndex, ellipsisIndex);
                replaceIndex++;
            }
            if (replaceIndex > 0) {
                replaceIndex--;
            }
        }
        content = content.substring(0, replaceIndex);
        setText(content + "..." + lastChar);
    }

    public void setTexts(String text, String ext) {
        if (ext != null && ext.length() > 0) {
            suffixSize = ext.length() + 1;
            setText(text + "." + ext);
        } else {
            suffixSize = 0;
            setText(text);
        }

    }

    private float getTextWidth(String text) {
        return getPaint().measureText(text);
    }

}
