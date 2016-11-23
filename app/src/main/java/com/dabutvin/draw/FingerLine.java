package com.dabutvin.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Dan on 11/22/2016.
 */
public class FingerLine extends View {

    private final int paintColor = 0xFF660000;
    private Path mDrawPath;
    private Bitmap mBitMap;
    private Canvas mCanvas;
    private Paint mDrawPaint;
    private Paint mCanvasPaint;

    public FingerLine(Context context) {
        super(context);
        setup();
    }

    public FingerLine(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setup();
    }

    private void setup(){
        mDrawPath = new Path();

        mDrawPaint = new Paint();
        mDrawPaint.setColor(paintColor);
        mDrawPaint.setAntiAlias(true);
        mDrawPaint.setStrokeWidth(10);
        mDrawPaint.setStyle(Paint.Style.STROKE);
        mDrawPaint.setStrokeJoin(Paint.Join.MITER);
        mDrawPaint.setStrokeCap(Paint.Cap.ROUND);

        mCanvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitMap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitMap);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawBitmap(mBitMap, 0, 0, mCanvasPaint);
        canvas.drawPath(mDrawPath, mDrawPaint);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDrawPath.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                mDrawPath.lineTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                //mCanvas.drawPath(mDrawPath, mDrawPaint);
                //mDrawPath.reset();
                break;
        }

        invalidate();
        return true;
    }

    public void reset() {
        setup();
        invalidate();
    }
}
