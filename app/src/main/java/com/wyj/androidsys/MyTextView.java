package com.wyj.androidsys;
/*
 *  description：
 *  author:        wyj
 *  date:         2019/8/20 0020  下午 6:38
 *  corporation: 湖北空越泰学
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class MyTextView extends View {
    private Paint mPaint;
    private Path mPath;


    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPath = new Path();
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        final int width = getMySize(widthMeasureSpec);
//        final int height = getMySize(heightMeasureSpec);
//        final int min = Math.min(width, height);//保证控件为方形
//        setMeasuredDimension(min, min);
//    }



    /**
     * 修改TextView需要重写onDraw()
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {

//
        mPath.lineTo(100, 0);
        mPath.arcTo(0, 0, 150, 100, -90, 180, false);


        canvas.drawPath(mPath, mPaint);
    }

    private int dip2px(Context context, float dipValue) {
        float fontScale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * fontScale + 0.5f);
    }
}
