package com.beike.ctdialog.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.beike.ctdialog.R;

/**
 * Created by liupeng_a on 2017/2/27.
 */

public class CircleLoadingView1 extends View {

    private Paint paint;

    //圆环颜色
    private int circleColor;

    //进度条颜色
    private int circleProgressColor;

    //圆环宽度
    private float circleWidth;

    //最大进度
    private int max;

    //当前进度
    private int progress;

    //进度开始的角度数
    private int startAngle = -90;

    public CircleLoadingView1(Context context) {
        this(context, null);
    }

    public CircleLoadingView1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleLoadingView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleLoadingView);

        circleColor = typedArray.getColor(R.styleable.CircleLoadingView_circleColor, 0xff36393d);
        circleProgressColor = typedArray.getColor(R.styleable.CircleLoadingView_circleProgressColor, 0xffFF9900);

        //圆环的宽度
        circleWidth = typedArray.getDimension(R.styleable.CircleLoadingView_circleWidth, 10);
        //最大进度
        max = typedArray.getInteger(R.styleable.CircleLoadingView_max, 100);
        //当前进度
        progress = typedArray.getInt(R.styleable.CircleLoadingView_progress, 20);
        //回收资源
        typedArray.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int center = getWidth() / 2;
        int radius = (int) (center - circleWidth / 2);

        //最外层大圆环
        paint.setAntiAlias(true);
        paint.setColor(circleColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(circleWidth);
        canvas.drawCircle(center, center, radius, paint);

        //圆环进度
        paint.setStrokeWidth(circleWidth);
        paint.setColor(circleProgressColor);
        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);
        canvas.drawArc(oval, startAngle, 360 * progress / max, false, paint);
        if (startAngle == 360){
            startAngle = 0;
        }
        startAngle += 10;

        postInvalidateDelayed(30);  //一个周期用时1200

    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        if (max <= 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    public int getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
    }

    public int getCircleProgressColor() {
        return circleProgressColor;
    }

    public void setCircleProgressColor(int circleProgressColor) {
        this.circleProgressColor = circleProgressColor;
    }

    public float getCircleWidth() {
        return circleWidth;
    }

    public void setCircleWidth(float circleWidth) {
        this.circleWidth = circleWidth;
    }
}
