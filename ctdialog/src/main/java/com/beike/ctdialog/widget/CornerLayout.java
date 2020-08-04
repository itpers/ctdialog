package com.beike.ctdialog.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.beike.ctdialog.R;

public class CornerLayout extends FrameLayout {

    private float[] radii;

    private Path path;

    public CornerLayout(@NonNull Context context) {
        super(context, null);
    }

    public CornerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CornerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        radii = new float[8];

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CornerLayout);

        int radius = a.getDimensionPixelSize(R.styleable.CornerLayout_radius, 0);

        int tl = a.getDimensionPixelSize(R.styleable.CornerLayout_radiusTL, radius);
        int tr = a.getDimensionPixelSize(R.styleable.CornerLayout_radiusTR, radius);
        int br = a.getDimensionPixelSize(R.styleable.CornerLayout_radiusBR, radius);
        int bl = a.getDimensionPixelSize(R.styleable.CornerLayout_radiusBL, radius);

        radii[0] = a.getDimensionPixelSize(R.styleable.CornerLayout_radiusTLX, tl);
        radii[1] = a.getDimensionPixelSize(R.styleable.CornerLayout_radiusTLY, tl);
        radii[2] = a.getDimensionPixelSize(R.styleable.CornerLayout_radiusTRX, tr);
        radii[3] = a.getDimensionPixelSize(R.styleable.CornerLayout_radiusTRY, tr);
        radii[4] = a.getDimensionPixelSize(R.styleable.CornerLayout_radiusBRX, br);
        radii[5] = a.getDimensionPixelSize(R.styleable.CornerLayout_radiusBRY, br);
        radii[6] = a.getDimensionPixelSize(R.styleable.CornerLayout_radiusBLX, bl);
        radii[7] = a.getDimensionPixelSize(R.styleable.CornerLayout_radiusBLY, bl);

        a.recycle();

        path = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        RectF rect = new RectF();
        // compute the path
        path.reset();
        rect.set(0, 0, w, h);
        path.addRoundRect(rect, radii, Path.Direction.CW);
        path.close();

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int save = canvas.save();
        canvas.clipPath(path);
        super.dispatchDraw(canvas);
        canvas.restoreToCount(save);
    }
}
