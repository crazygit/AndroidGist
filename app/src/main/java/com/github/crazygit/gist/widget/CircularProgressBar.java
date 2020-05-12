package com.github.crazygit.gist.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.github.crazygit.gist.R;


public class CircularProgressBar extends View {
    private static final String TAG = "CircularProgressBar";
    private static final float maxProcess = 100f;
    // 开头圆点的半径倍数
    private int widthFactor;
    // 开头圆点的画笔
    private Paint beginCircle;
    // 当前进度数
    private float process;
    // 圆半径
    private float radius;
    // 背景圆宽度
    private float strokeWidth;
    // 背景圆颜色
    private int backgroundCircularColor;
    // 背景圆圈的画笔
    private Paint backgroundCircularPaint;
    // 弧度的颜色
    private int arcColor;
    // 弧度的画笔
    private Paint arcPaint;

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

        backgroundCircularPaint = new Paint();
        backgroundCircularPaint.setColor(backgroundCircularColor);
        backgroundCircularPaint.setAntiAlias(true);
        backgroundCircularPaint.setStyle(Paint.Style.STROKE);
        backgroundCircularPaint.setStrokeCap(Paint.Cap.ROUND);
        backgroundCircularPaint.setStrokeWidth(strokeWidth);

        arcPaint = new Paint();
        arcPaint.setColor(arcColor);
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);
        arcPaint.setStrokeWidth(strokeWidth);

        beginCircle = new Paint();
        beginCircle.setColor(arcColor);
        beginCircle.setAntiAlias(true);
        beginCircle.setStyle(Paint.Style.FILL);
    }

    public float getProcess() {
        return process;
    }

    public void setProcess(float process) {
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

//        // 画背景圆
//        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, radius, backgroundCircularPaint);
//
//        // 画弧度
//        float angle = 360 * (process / maxProcess);
//        Log.v(TAG, "onDraw: Angle: " + angle);
//        Log.v(TAG, "onDraw: Math.cos(angle) = " + Math.cos(Math.toRadians(angle)));
//        Log.v(TAG, "onDraw: Math.sin(angle) = " + Math.sin(Math.toRadians(angle)));
//        canvas.drawArc(strokeWidth * widthFactor / 2f, strokeWidth * widthFactor / 2f, getWidth() - strokeWidth * widthFactor / 2f, getHeight() - strokeWidth * widthFactor / 2f, 0, angle, false, arcPaint);
//
//        // 画圆点
//        float x = (float) (radius + radius * Math.cos(Math.toRadians(angle)) + strokeWidth * widthFactor / 2);
//        Log.v(TAG, "onDraw: x = " + x);
//        float y = (float) (radius + radius * Math.sin(Math.toRadians(angle)) + strokeWidth * widthFactor / 2);
//        Log.v(TAG, "onDraw: y = " + y);
//        canvas.drawCircle(x, y, strokeWidth / 2f * widthFactor, beginCircle);
        // 圆心坐标
        float cx = getWidth() / 2f;
        float cy = getHeight() / 2f;

        // 画背景圆
        canvas.drawCircle(cx, cy, radius, backgroundCircularPaint);

        // 画弧度
        float angle = 360 * (process / maxProcess);
        Log.v(TAG, "onDraw: Angle: " + angle);
        Log.v(TAG, "onDraw: Math.cos(angle) = " + Math.cos(Math.toRadians(angle)));
        Log.v(TAG, "onDraw: Math.sin(angle) = " + Math.sin(Math.toRadians(angle)));
        RectF rect = new RectF(cx - radius, cy - radius, cx + radius, cy + radius);
        canvas.drawArc(rect, 0, angle, false, arcPaint);

        // 画圆点
        float x = (float) (cx + radius * Math.cos(Math.toRadians(angle)));
        Log.v(TAG, "onDraw: x = " + x);
        float y = (float) (cy + radius * Math.sin(Math.toRadians(angle)));
        Log.v(TAG, "onDraw: y = " + y);
        canvas.drawCircle(x, y, strokeWidth / 2 * widthFactor, beginCircle);
    }
}
