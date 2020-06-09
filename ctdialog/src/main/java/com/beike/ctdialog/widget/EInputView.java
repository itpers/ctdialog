package com.beike.ctdialog.widget;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.util.AttributeSet;

public class EInputView extends AppCompatEditText {
    public EInputView(Context context) {
        super(context);
    }

    public EInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
}
