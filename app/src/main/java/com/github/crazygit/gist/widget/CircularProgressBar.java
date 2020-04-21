package com.github.crazygit.gist.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.github.crazygit.gist.R;


public class CircularProgressBar extends View {
    private static final String TAG = "CircularProgressBar";
    private static final int maxProcess = 100;
    // 开头圆点的半径倍数
    private int widthFactor;
    // 当前进度数
    private int process;
    // 圆半径
    private float radius;
    // 背景圆宽度
    private float strokeWidth;
    // 背景圆颜色
    private int backgroundCircularColor;
    // 弧度的颜色
    private int arcColor;
    private Paint mPaint;

    public CircularProgressBar(Context context) {
        this(context, null);
    }

    public CircularProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CircularProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        //获取自定义属性的值
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircularProgressBar, defStyleAttr, 0);

        widthFactor = a.getInt(R.styleable.CircularProgressBar_widthFactor, 2);
        process = a.getColor(R.styleable.CircularProgressBar_process, 0);
        radius = a.getDimension(R.styleable.CircularProgressBar_radius, 100);
        strokeWidth = a.getDimension(R.styleable.CircularProgressBar_strokeWidth, 10);
        backgroundCircularColor = a.getColor(R.styleable.CircularProgressBar_backgroundCircularColor, Color.GRAY);
        arcColor = a.getColor(R.styleable.CircularProgressBar_arcColor, Color.GREEN);
        a.recycle();  //注意回收

        mPaint = new Paint();
    }

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        if (process > maxProcess) {
            process = maxProcess;
        }
        if (process < 0) {
            process = 0;
        }
        this.process = process;
        // 刷新视图
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = (int) (2 * radius + strokeWidth * widthFactor);
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = (int) (2 * radius + strokeWidth * widthFactor);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.reset();
        // 画背景圆
        mPaint.setColor(backgroundCircularColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, radius, mPaint);

        // 画弧度
        mPaint.setColor(arcColor);
        float angle = 360 * ((float) process / maxProcess);
        Log.v(TAG, "onDraw: Angle: " + angle);
        Log.v(TAG, "onDraw: Math.cos(angle) = " + Math.cos(Math.toRadians(angle)));
        Log.v(TAG, "onDraw: Math.sin(angle) = " + Math.sin(Math.toRadians(angle)));
        canvas.drawArc(strokeWidth * widthFactor / 2f, strokeWidth * widthFactor / 2f, getWidth() - strokeWidth * widthFactor / 2f, getHeight() - strokeWidth * widthFactor / 2f, 0, angle, false, mPaint);

        // 画圆点
        mPaint.reset();
        mPaint.setColor(arcColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        float x = (float) (radius + radius * Math.cos(Math.toRadians(angle)) + strokeWidth * widthFactor / 2);
        Log.v(TAG, "onDraw: x = " + x);
        float y = (float) (radius + radius * Math.sin(Math.toRadians(angle)) + strokeWidth * widthFactor / 2);
        Log.v(TAG, "onDraw: y = " + y);
        canvas.drawCircle(x, y, strokeWidth / 2f * widthFactor, mPaint);
    }
}
