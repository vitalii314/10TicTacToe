package com.vitalii.s.a10tictactoe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by user on 04.12.2016.
 */
public class StartingView extends View {

    public Bitmap bitmap;
    public static final int WIDTH = 960;
    public static final int HEIGHT = 1280;
    public StartingView(Context context) {
        super(context);
        init();
    }

    public StartingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StartingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kletka3);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        final float scaleFactorX;
        final float scaleFactorY;

        if (getWidth() > WIDTH || getHeight() > HEIGHT) {
            scaleFactorX = (float) getWidth() / WIDTH;
            scaleFactorY = (float) getHeight() / HEIGHT;
        } else {
            scaleFactorX = 1;
            scaleFactorY = 1;
        }
        final int savedState = canvas.save();
        canvas.scale(scaleFactorX, scaleFactorY);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.restoreToCount(savedState);
    }

}
