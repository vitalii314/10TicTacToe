package com.vitalii.s.a10tictactoe;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

/**
 * Created by user on 30.10.2016.
 */
public class GameManager extends Thread {

    private static final int FIELD_WIDTH = 300;
    private static final int FIELD_HEIGHT = 250;
    private SurfaceHolder mSurfaceHolder;
    private boolean mRunning;
    private Paint mPaint;
    private Rect mField;
    private GameViewDynamicExample gameViewDynamicExample;


    public GameManager(SurfaceHolder surfaceHolder, GameViewDynamicExample gameViewDynamicExample) {
        this.mSurfaceHolder = surfaceHolder;
        this.gameViewDynamicExample = gameViewDynamicExample;
        mRunning = false;
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);

        int left = 10;
        int top = 50;
        mField = new Rect(left, top, left + FIELD_WIDTH, top + FIELD_HEIGHT);


    }

    @Override
    public void run () {

        while (mRunning) {
            Canvas canvas = null;
            try {
                canvas = mSurfaceHolder.lockCanvas();
                synchronized (mSurfaceHolder) {
                canvas.drawRect(mField,mPaint);
                    canvas.drawBitmap(gameViewDynamicExample.myBitmap,100,100,null);
                }
            } catch (Exception e) {

            }
            finally {
                if (canvas!=null) {
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public void setRunning(boolean running) {
        mRunning = running;
    }


}
