package com.beike.ctdialog.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.beike.ctdialog.utils.DensityUtil;

/**
 * Created by liupeng on 2017/7/18.
 */

public class LoadingView extends View {

    private int width, height;
    private Paint paint;
    private int startPosition = 0;
    private RectF rectF;
    private int rectWidth, rectHeight;

    private final int RADIAN = 3;

    private String[] color = {"#A0A0A0", "#AAAAAA", "#B0B0B0", "#BBBBBB", "#C0C0C0", "#DFDFDF"};

    public LoadingView(Context context) {
        this(context, null, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        width = Math.min(width, height);

        rectWidth = width / 12;   //菊花矩形的宽
        rectHeight = 3 * rectWidth;  //菊花矩形的高
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (rectF == null) {
            rectF = new RectF((width - rectWidth) / 2, 0, (width + rectWidth) / 2, rectHeight);
        }

        for (int i = 0; i < 12; i++) {
            if (i - startPosition >= 7) {
                paint.setColor(Color.parseColor(color[5]));
            } else if (i - startPosition >= 0 && i - startPosition < 5) {
                paint.setColor(Color.parseColor(color[i - startPosition]));
            } else if (i - startPosition >= -5 && i - startPosition < 0) {
                paint.setColor(Color.parseColor(color[5]));
            } else if (i - startPosition >= -11 && i - startPosition < -7) {
                paint.setColor(Color.parseColor(color[12 + i - startPosition]));
            }

            canvas.drawRoundRect(rectF, RADIAN, RADIAN, paint);  //绘制
            canvas.rotate(30, width / 2, width / 2);    //旋转
        }

        startPosition++;
        if (startPosition > 11) {
            startPosition = 0;
        }

        postInvalidateDelayed(100);  //一个周期用时1200
    }
}
