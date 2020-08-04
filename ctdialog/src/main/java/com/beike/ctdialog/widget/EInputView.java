package com.beike.ctdialog.widget;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.beike.ctdialog.R;

public class EInputView extends AppCompatEditText {

    private static final int LEFT = 0;
    private static final int TOP = 1;
    private static final int RIGHT = 2;
    private static final int BOTTOM = 3;

    private Drawable mClearDrawable;

    public EInputView(Context context) {
        super(context);
        init();
    }

    public EInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mClearDrawable = getResources().getDrawable(R.drawable.ic_edit_clean);
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        if (id == android.R.id.paste) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return super.onTextContextMenuItem(android.R.id.pasteAsPlainText);
            } else {
                ClipboardManager clip = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                if (clip != null) {
                    ClipData primaryClip = clip.getPrimaryClip();
                    if (primaryClip != null && primaryClip.getItemCount() > 0) {
                        CharSequence pastText = primaryClip.getItemAt(0).getText();
                        if (pastText != null && pastText.length() > 0) {
                            Editable text = getText();
                            if (text != null && text.length() > 0) {
                                int start = Math.max(0, getSelectionStart());
                                int end = Math.max(start, getSelectionEnd());
                                text.replace(start, end, pastText);
                                setText(text);
                                setSelection(Math.max(0, Math.min(start + pastText.length(), getText().length())));
                            } else {
                                setText(pastText);
                                setSelection(pastText.length());
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return super.onTextContextMenuItem(id);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setClearIconVisible(hasFocus() && length() > 0);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setClearIconVisible(focused && length() > 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            Drawable drawable = getCompoundDrawables()[RIGHT];
            if (drawable != null && event.getX() <= (getWidth() - getPaddingRight()) && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {
                setText("");
            }
        }

        return super.onTouchEvent(event);
    }

    public void setClearIconVisible(boolean visible) {
        setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[LEFT],
                getCompoundDrawables()[TOP],
                visible ? mClearDrawable : null,
                getCompoundDrawables()[BOTTOM]
        );
    }
}
