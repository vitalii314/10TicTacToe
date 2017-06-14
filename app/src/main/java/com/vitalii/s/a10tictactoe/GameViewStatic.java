package com.vitalii.s.a10tictactoe;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.ParcelableCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.widget.Checkable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import playground.Board;
import playground.Bot5.Bot5;
import playground.Cell;
import playground.Seed;
import playground.SimplePlayGround;
import playground.State;


/**
 * Created by user on 30.10.2016.
 */
public class GameViewStatic extends View implements SoundPool.OnLoadCompleteListener {


    public class GameThread extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < rects.length; i++) {
                for (int j = 0; j < rects[i].length; j++) {
                    if (rects[i][j].contains((int) touchX, (int) touchY)
                            && !simplePlayGround.isFinished()
                            && simplePlayGround.getCurrentPlayer() == Seed.CROSS
                            && simplePlayGround.getBoard().cells[i][j].content == Seed.EMPTY) {
                        simplePlayGround.doStep(i, j);
                        postInvalidate();
                        if (simplePlayGround.getBoard().hasWon(Seed.CROSS)) {
                            playerWin++;
                            streamId = mSoundPool.play(soundID, 1, 1, 0, 0, 1);
                            showWinner();
                            mSoundPool.stop(streamId);

                        }

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                        }
                        if (!simplePlayGround.isFinished()) {
                            makeBotMove();
                            postInvalidate();
                            if (simplePlayGround.getBoard().hasWon(Seed.NOUGHT)) {
                                compWin++;
                                streamId = mSoundPool.play(soundID, 1, 1, 0, 0, 1);
                                showWinner();
                                mSoundPool.stop(streamId);
                            }

                        }

                    }
                    if (rects[i][j].contains((int) touchX, (int) touchY)) flag = true;
                }
            }
            if (simplePlayGround.isFinished() && !flag) {
                simplePlayGround.start();
                //bot.start();
                postInvalidate();

            }
        }

        public void showWinner() {


            winningFields = simplePlayGround.getBoard().winningFields;
            int x1 = winningFields[0][0];
            int y1 = winningFields[0][1];
            int x2 = winningFields[1][0];
            int y2 = winningFields[1][1];
            int x3 = winningFields[2][0];
            int y3 = winningFields[2][1];
            Seed tempContent1 = simplePlayGround.getBoard().cells[x1][y1].content;
            Seed tempContent2 = simplePlayGround.getBoard().cells[x2][y2].content;
            Seed tempContent3 = simplePlayGround.getBoard().cells[x3][y3].content;


            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }

                simplePlayGround.getBoard().cells[x1][y1].content = Seed.EMPTY;
                simplePlayGround.getBoard().cells[x2][y2].content = Seed.EMPTY;
                simplePlayGround.getBoard().cells[x3][y3].content = Seed.EMPTY;
                postInvalidate();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }

                simplePlayGround.getBoard().cells[x1][y1].content = tempContent1;
                simplePlayGround.getBoard().cells[x2][y2].content = tempContent2;
                simplePlayGround.getBoard().cells[x3][y3].content = tempContent3;
                postInvalidate();
            }


        }
    }
    private Context context;
    private Rect[][] rects = new Rect[3][3];
    private static final int size = 3;
    private Paint mPaint;
    private Paint mCirclePaint;
    private Paint mCrossPaint;
    public static final int WIDTH = 960;
    public static final int HEIGHT = 1280;
    float touchX = 0;
    float touchY = 0;
    private Bitmap bitmap;
    public SimplePlayGround simplePlayGround;
    //private Bot bot;
    private boolean flag;
    private Thread gameThread;
    private boolean firstThread=false;
    private int[][] winningFields;
    private SoundPool mSoundPool;
    private int soundID;
    private int streamId;
    final int MAX_STREAMS = 5;
    final List<Target> targets = new ArrayList<>();
    public static int playerWin;
    public static int compWin;
    public TextView textView;



    public GameViewStatic(Context context) {
        super(context);
        this.context=context;
        mSoundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        mSoundPool.setOnLoadCompleteListener(this);
        soundID = mSoundPool.load(context, R.raw.win, 1);
        init();

    }

    // эти два конструктора нужны , если будем использовать вместе с кнопками
    public GameViewStatic(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        mSoundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        mSoundPool.setOnLoadCompleteListener(this);
        soundID = mSoundPool.load(context, R.raw.win, 1);
        init();
    }

    // то есть если будем добавлять наш GameViewStatic как customView чере разметку
    public GameViewStatic(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
        mSoundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        mSoundPool.setOnLoadCompleteListener(this);
        soundID = mSoundPool.load(context, R.raw.win, 1);
        init();
    }

    public void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(10);
        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.RED);
        mCirclePaint.setStrokeWidth(10);
        mCrossPaint = new Paint();
        mCrossPaint.setColor(Color.BLUE);
        mCrossPaint.setStrokeWidth(10);
        mCrossPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        simplePlayGround = new SimplePlayGround(3,3,3);
        simplePlayGround.start();
       // bot = new Bot();
        if (!firstThread) {
            playerWin = 0;
            compWin = 0;
        }
        //bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kletka3);
        //bitmap = StartingActivity.getBitmapFromCache("1");
        firstThread = true;
        Target mTarget = new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                GameViewStatic.this.bitmap = bitmap;
                invalidate();
                targets.remove(this);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                targets.remove(this);

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }


        };

        targets.add(mTarget);
        Picasso.with(getContext()).load(R.drawable.kletka3).
                memoryPolicy(MemoryPolicy.NO_STORE,MemoryPolicy.NO_CACHE).into(targets.get(0));

    }


    @Override
    public void onLoadComplete(SoundPool soundPool, int i, int i1) {

    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (bitmap != null) {
            textView = (TextView) ((Activity)context).findViewById(R.id.scoreText);
            textView.setText("Srore:"+playerWin+":"+compWin);
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

            float fieldLength = getWidth() / 2;

            float startPosX1 = (getWidth() - fieldLength) / 2 + fieldLength / size;
            float startPosY1 = (getHeight() - fieldLength) / 2;
            float startPosX2 = startPosX1;
            float startPosY2 = startPosY1 + fieldLength;
            canvas.drawLine(startPosX1, startPosY1, startPosX2, startPosY2, mPaint);

            float startPosX3 = startPosX1 + fieldLength / size;
            float startPosY3 = startPosY1;
            float startPosX4 = startPosX3;
            float startPosY4 = startPosY1 + fieldLength;
            canvas.drawLine(startPosX3, startPosY3, startPosX4, startPosY4, mPaint);

            float startPosX5 = (getWidth() - fieldLength) / 2;
            float startPosY5 = (getHeight() / 2) - fieldLength / size / 2;
            float startPosX6 = startPosX5 + fieldLength;
            float startPosY6 = startPosY5;
            canvas.drawLine(startPosX5, startPosY5, startPosX6, startPosY6, mPaint);

            float startPosX7 = startPosX5;
            float startPosY7 = (getHeight() / 2) + fieldLength / size / 2;
            float startPosX8 = startPosX7 + fieldLength;
            float startPosY8 = startPosY7;
            canvas.drawLine(startPosX7, startPosY7, startPosX8, startPosY8, mPaint);

            if (rects[0][0] == null) {
                rects[0][0] = new Rect((int) (startPosX1 - fieldLength / size), (int) (startPosY5 - fieldLength / size),
                        (int) startPosX1, (int) startPosY5);
                rects[0][1] = new Rect((int) startPosX1, (int) (startPosY5 - fieldLength / size),
                        (int) (startPosX1 + fieldLength / size), (int) startPosY5);
                rects[0][2] = new Rect((int) (startPosX1 + fieldLength / size), (int) (startPosY5 - fieldLength / size),
                        (int) (startPosX1 + 2 * fieldLength / size), (int) startPosY5);
                rects[1][0] = new Rect((int) (startPosX1 - fieldLength / size), (int) startPosY5,
                        (int) startPosX1, (int) (startPosY5 + fieldLength / size));
                rects[1][1] = new Rect((int) startPosX1, (int) startPosY5,
                        (int) (startPosX1 + fieldLength / size), (int) (startPosY5 + fieldLength / size));
                rects[1][2] = new Rect((int) (startPosX1 + fieldLength / size), (int) startPosY5,
                        (int) (startPosX1 + 2 * fieldLength / size), (int) (startPosY5 + fieldLength / size));
                rects[2][0] = new Rect((int) (startPosX1 - fieldLength / size), (int) (startPosY5 + fieldLength / size),
                        (int) startPosX1, (int) (startPosY5 + fieldLength / size * 2));
                rects[2][1] = new Rect((int) startPosX1, (int) (startPosY5 + fieldLength / size),
                        (int) (startPosX1 + fieldLength / size), (int) (startPosY5 + fieldLength / size * 2));
                rects[2][2] = new Rect((int) (startPosX1 + fieldLength / size), (int) (startPosY5 + fieldLength / size),
                        (int) (startPosX1 + 2 * fieldLength / size), (int) (startPosY5 + fieldLength / size * 2));
            }


            for (int i = 0; i < rects.length; i++) {
                for (int j = 0; j < rects[i].length; j++) {

                    if (simplePlayGround.getBoard().cells[i][j].content == Seed.CROSS) {
                        drawCross(canvas, rects[i][j].centerX(), rects[i][j].centerY());
                    } else {
                        if (simplePlayGround.getBoard().cells[i][j].content == Seed.NOUGHT) {
                            drawCirlce(canvas, rects[i][j].centerX(), rects[i][j].centerY());
                        }

                    }

                }
            }


        }
    }


    public boolean onTouchEvent(MotionEvent event) {
        if (firstThread || !gameThread.isAlive()) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                flag = false;
                touchX = event.getX();
                touchY = event.getY();
                gameThread = new GameThread();
                gameThread.setDaemon(true);
                gameThread.start();
                firstThread = false;
            }

        }

        return true;
    }


    public void drawCirlce(Canvas canvas, int x, int y) {
        Path circlePath = new Path();
        circlePath.addCircle(x, y, (getWidth() / 2 / size - 30) / 2, Path.Direction.CW);
        canvas.drawPath(circlePath, mCirclePaint);

    }

    public void drawCross(Canvas canvas, int x, int y) {
        float x1 = (x - (getWidth() / 2 / size / 2 - 15));
        float y1 = (y - (getWidth() / 2 / size / 2 - 15));
        float x2 = (x + (getWidth() / 2 / size / 2 - 15));
        float y2 = (y + (getWidth() / 2 / size / 2 - 15));
        canvas.drawLine(x1, y1, x2, y2, mCrossPaint);

        float x3 = (x + (getWidth() / 2 / size / 2 - 15));
        float y3 = (y - (getWidth() / 2 / size / 2 - 15));
        float x4 = (x - (getWidth() / 2 / size / 2 - 15));
        float y4 = (y + (getWidth() / 2 / size / 2 - 15));

        canvas.drawLine(x3, y3, x4, y4, mCrossPaint);
        canvas.drawLine(x3, y3, x4, y4, mCrossPaint);
    }

    public int[][] findWinningRects() {
        int[][] winningRects = new int[3][2];
        if (this.simplePlayGround.getBoard().cells[0][0].content == Seed.CROSS
                && this.simplePlayGround.getBoard().cells[0][1].content == Seed.CROSS
                && this.simplePlayGround.getBoard().cells[0][2].content == Seed.CROSS
                || this.simplePlayGround.getBoard().cells[1][0].content == Seed.NOUGHT
                && this.simplePlayGround.getBoard().cells[1][1].content == Seed.NOUGHT
                && this.simplePlayGround.getBoard().cells[1][2].content == Seed.NOUGHT) {
            winningRects[0][0] = 0;
            winningRects[0][1] = 0;
            winningRects[1][0] = 0;
            winningRects[1][1] = 1;
            winningRects[2][0] = 0;
            winningRects[2][1] = 2;


        }
        return winningRects;
    }



    public void makeBotMove() {
        if (simplePlayGround.getCurrentPlayer() == Seed.NOUGHT && !simplePlayGround.isFinished()) {
            int[] compMove = Bot5.makeBotMove(Seed.NOUGHT,simplePlayGround,8);
            int a = compMove[0];
            int b = compMove[1];
            State state = simplePlayGround.doStep(a, b);

        }

    }


    static class SavedState extends BaseSavedState {
        String savedPlayground;
        String savedBot;
        int playerWin;
        int compWin;


        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            savedPlayground = in.readString();
            savedBot = in.readString();
            playerWin = in.readInt();
            compWin = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(savedPlayground);
            out.writeString(savedBot);
            out.writeInt(playerWin);
            out.writeInt(compWin);
        }

        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.savedPlayground = new Gson().toJson(simplePlayGround);
        //ss.savedBot = new Gson().toJson(bot);
        ss.playerWin = this.playerWin;
        ss.compWin = this.compWin;
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        simplePlayGround = new Gson().fromJson(ss.savedPlayground, SimplePlayGround.class);
        //bot = new Gson().fromJson(ss.savedBot, Bot.class);
        this.playerWin = ss.playerWin;
        this.compWin = ss.compWin;


    }


}
