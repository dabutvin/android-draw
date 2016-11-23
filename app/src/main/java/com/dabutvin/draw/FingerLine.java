package com.dabutvin.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

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
        mCanvas.drawBitmap(mBitMap, 0, 0, mCanvasPaint);
        mCanvas.drawPath(mDrawPath, mDrawPaint);
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
        mCanvas.drawColor(Color.WHITE);
        setup();
        invalidate();
    }

    public void share() throws IOException {

        String _time = "";
        Calendar cal = Calendar.getInstance();
        int millisecond = cal.get(Calendar.MILLISECOND);
        int second = cal.get(Calendar.SECOND);
        int minute = cal.get(Calendar.MINUTE);
        int hourofday = cal.get(Calendar.HOUR_OF_DAY);
        _time = "image_" + hourofday + "" + minute + "" + second + ""
                + millisecond + ".png";
        String file_path = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/drawapp";
        File dir = new File(file_path);
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(dir, _time);
        FileOutputStream fOut = new FileOutputStream(file);
        mBitMap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        fOut.flush();
        fOut.close();
        Toast.makeText(getContext(),
                "Image has been saved in drawapp folder",
                Toast.LENGTH_LONG).show();
    }
}
