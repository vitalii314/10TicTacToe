package com.vitalii.s.a10tictactoe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by user on 29.10.2016.
 */
public class GameViewDynamicExample extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mSurfaceHolder;
    private GameManager mThread;
    Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kletka3);

    public GameViewDynamicExample(Context context) {
        super(context);

        //подписываеся на события
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

        mThread = new GameManager(mSurfaceHolder,this);

    }

    @Override
    protected void onDraw (Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kletka3);
        canvas.drawBitmap(bitmap, 10, 10, null);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mThread.setRunning(true);
        mThread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    boolean retry = true;
        while (retry) {
            try {
                mThread.setRunning(false);
                mThread.join();
            }

            catch (InterruptedException e ) { e.printStackTrace();}
            retry = false;
        }
    }
}
