package com.dabutvin.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Dan on 11/22/2016.
 */
public class FingerLine extends View {

    private final Bitmap mBitmap;
    private final Canvas mCanvas;
    private final int width = 5;
    private final int height = 5;
    private float startX;
    private float startY;
    private float stopX;
    private float stopY;
    private Paint mPaint;


    public FingerLine(Context context) {
        super(context);
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
    }

    public FingerLine(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawLine(startX, startY, stopX, stopY, mPaint);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                stopX = event.getX();
                stopY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                stopX = event.getX();
                stopY = event.getY();
                mCanvas.drawLine(startX, startY, stopX, stopY, mPaint);
                break;
        }

        invalidate();
        return true;
    }
}
