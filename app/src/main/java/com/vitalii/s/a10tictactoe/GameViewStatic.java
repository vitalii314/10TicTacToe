package com.vitalii.s.a10tictactoe;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.SoundPool;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.vitalii.s.a10tictactoe.Data.HoteContract;
import com.vitalii.s.a10tictactoe.Data.HotelDbHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import playground.Bot5.Bot5;
import playground.Seed;
import playground.SimplePlayGround;
import playground.State;


/**
 * Created by user on 30.10.2016.
 */
public class GameViewStatic extends View {



    public class GameThread extends Thread {


        @Override
        public void run() {

            if (rects[0][0] == null) {
                try {
                    String value = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }



            for (int i = 0; i < rects.length; i++) {
                for (int j = 0; j < rects[i].length; j++) {
                    if (rects[i][j].contains((int) touchX, (int) touchY)
                            && !simplePlayGround.isFinished()
                            && simplePlayGround.getCurrentPlayer() == playerSeed
                            && simplePlayGround.getBoard().cells[i][j].content == Seed.EMPTY) {
                        simplePlayGround.doStep(i, j);
                        if (isSound) Sound.playMoveSound();
                        countMove++;
                        ((MainActivity) getContext()).changeToolBarText(Integer.toString(bestScore),
                                Integer.toString(countMove));
                        postInvalidate();

                        if (simplePlayGround.getBoard().hasWon(playerSeed)) {
                            playerWin++;
                            if (isSound) Sound.playWinSound();
                            showWinner();
                            if (bestScore == 0 && boardSize == BOARD_SIZE_10) {
                                bestScore = countMove;
                                saveBestScore(bestScore);
                                ((MainActivity) getContext()).showBestScoreFragment();
                                Sound.stopSound();
                                if (isSound) Sound.playBestScoreSound();
                            } else if (countMove < bestScore && boardSize == BOARD_SIZE_3) {
                                bestScore = countMove;
                                saveBestScore(bestScore);
                                ((MainActivity) getContext()).showBestScoreFragment();
                                Sound.stopSound();
                                if (isSound) Sound.playBestScoreSound();

                            }


                            ((MainActivity) getContext()).changeToolBarText(Integer.toString(bestScore),
                                    Integer.toString(countMove));

                        } else {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    if (rects[i][j].contains((int) touchX, (int) touchY)) flag = true;
                }
            }


            if (!simplePlayGround.isFinished() &&
                    simplePlayGround.getCurrentPlayer() == (playerSeed == Seed.NOUGHT ? Seed.CROSS : Seed.NOUGHT)) {
                long start = System.nanoTime();

                makeBotMove();

                if (Thread.currentThread().isInterrupted()) {
                    return;
                }

                long finish = System.nanoTime();
                if ((finish - start) / 1000000 < 600) {
                    long diff = 600 - (finish - start) / 1000000;
                    try {
                        Thread.sleep(diff);
                    } catch (InterruptedException e) {
                    }
                }
                postInvalidate();
                if (isSound) Sound.playMoveSound();
                if (simplePlayGround.getBoard().hasWon(playerSeed == Seed.NOUGHT ? Seed.CROSS : Seed.NOUGHT)) {

                    Sound.playWinSound();
                    showWinner();
                }

            }


            if (simplePlayGround.isFinished() && !flag) {
                simplePlayGround.start();
                countMove = 0;
                ((MainActivity) getContext()).changeToolBarText(Integer.toString(bestScore),
                        Integer.toString(countMove));
                postInvalidate();

            }

            if (queue.isEmpty()) queue.add("go");

        }

        public void showWinner() {


            winningFields = simplePlayGround.getBoard().winningFields;
            Seed[] tempSeed = new Seed[winningFields.size()];
            for (int i = 0; i < winningFields.size(); i++) {
                int[][] tempArr = (int[][]) winningFields.get(i);
                tempSeed[i] = simplePlayGround.getBoard().
                        cells[tempArr[0][0]][tempArr[0][1]].content;
            }
//

            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }

                for (int k = 0; k < winningFields.size(); k++) {
                    int[][] tempArr = (int[][]) winningFields.get(k);
                    simplePlayGround.getBoard().
                            cells[tempArr[0][0]][tempArr[0][1]].content = Seed.EMPTY;

                }

                if (Thread.currentThread().isInterrupted()) return;

                postInvalidate();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }

                for (int k = 0; k < tempSeed.length; k++) {
                    int[][] tempArr = (int[][]) winningFields.get(k);
                    simplePlayGround.getBoard().
                            cells[tempArr[0][0]][tempArr[0][1]].content = tempSeed[k];

                }

                postInvalidate();
            }


        }


    }


    private final static int LINE_WIDTH_NORMAL = 10;
    public final static int BOARD_SIZE_3 = 3;
    public final static int BOARD_SIZE_10 = 10;
    public final static int DIFFICULTY_EASY = 0;
    public final static int DIFFICULTY_HARD = 1;
    private final static int NORMAL_FIELDLENGTH_3_IN_DP = 160;
    private final static int NORMAL_FIELDLENGTH_10_IN_DP = 285;

    public final static String SAVED_BOARD_SIZE = "saved board size";
    private final static String SAVED_DIFFICULTY = "saved difficulty";
    private final static String SAVED_PLAYER_SEED = "saved player seed";
    private final static String SAVED_IS_SOUND = "saved is sound";


    final BlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
    // private int[] boardSize = {10, 10, 5, 3}; //playground rows, cols, number to win,depth
    int depth;
    private Context context;
    public Rect[][] rects;
    private Paint mPaint;
    private Paint mCirclePaint;
    private Paint mCrossPaint;
    public static final int WIDTH = 960;
    public static final int HEIGHT = 1280;
    float touchX = 0;
    float touchY = 0;
    private Bitmap bitmap;
    SimplePlayGround simplePlayGround;
    private Bot5 bot;
    private boolean flag;
    GameThread gameThread = new GameThread();
    private boolean firstThread = false;
    private ArrayList winningFields = new ArrayList<>();
    final List<Target> targets = new ArrayList<>();
    public static int playerWin;
    public static int compWin;
    public float fieldLength;
    Seed playerSeed;
    int difficulty;
    boolean isSound;
    int boardSize;
    int countMove;
    int bestScore;
    private HotelDbHelper mDbHelper;


    public GameViewStatic(Context context) {
        super(context);
        this.context = context;
        init();

    }

    // эти два конструктора нужны , если будем использовать вместе с кнопками
    public GameViewStatic(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    // то есть если будем добавлять наш GameViewStatic как customView чере разметку
    public GameViewStatic(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public void init() {


        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(LINE_WIDTH_NORMAL);
        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.RED);
        mCirclePaint.setStrokeWidth(LINE_WIDTH_NORMAL);
        mCrossPaint = new Paint();
        mCrossPaint.setColor(Color.BLUE);
        mCrossPaint.setStrokeWidth(LINE_WIDTH_NORMAL);
        mCrossPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStyle(Paint.Style.STROKE);

        bot = new Bot5();
        if (!firstThread) {
            playerWin = 0;
            compWin = 0;
        }

        if (MyApplication.isNormalRun) {
            boardSize = MyApplication.preferences.getInt(SAVED_BOARD_SIZE, BOARD_SIZE_3);
            String crossJson = new Gson().toJson(Seed.CROSS);
            String s = MyApplication.preferences.getString(SAVED_PLAYER_SEED, crossJson);
            playerSeed = new Gson().fromJson(s, Seed.class);
            difficulty = MyApplication.preferences.getInt(SAVED_DIFFICULTY, DIFFICULTY_EASY);
            isSound = MyApplication.preferences.getBoolean(SAVED_IS_SOUND, true);
        } else {
            boardSize = BOARD_SIZE_3;
            playerSeed = Seed.CROSS;
            difficulty = DIFFICULTY_EASY;
            isSound = true;

        }
        simplePlayGround = new SimplePlayGround(boardSize, boardSize, boardSize == BOARD_SIZE_10 ? 5 : 3);
        simplePlayGround.start();
        rects = new Rect[simplePlayGround.getBoard().cells.length][simplePlayGround.getBoard().cells.length];
        if (boardSize == BOARD_SIZE_10) {
            depth = 3;
        } else {
            depth = (difficulty == DIFFICULTY_HARD ? 8 : 5);
        }

        countMove = 0;
        mDbHelper = new HotelDbHelper(getContext());
        if (boardSize == BOARD_SIZE_10) readBestScore();



        firstThread = true;
        gameThread = new GameThread();
        gameThread.start();

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
        Picasso.with(getContext()).load(R.drawable.kletka).
                memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE).into(targets.get(0));

    }





    @Override
    protected void onDraw(Canvas canvas) {


        if (bitmap != null) {

            final float scaleFactorX;
            final float scaleFactorY;
            int size = simplePlayGround.getBoard().cells.length;



            if (getWidth() > WIDTH || getHeight() > HEIGHT) {
                scaleFactorX = (float) getWidth() / WIDTH;
                scaleFactorY = (float) getHeight() / HEIGHT;
            } else {
                scaleFactorX = 1;
                scaleFactorY = 1;
            }
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);

            //canvas.drawBitmap(bitmap, 0, 0, null);
            canvas.restoreToCount(savedState);


            int orient = getContext().getResources().getConfiguration().orientation;
            if (orient == 1) { //vertikal orientation
                fieldLength = (simplePlayGround.getBoard().cells.length == 10 ? (getWidth() - dpToPx(35)) : (getWidth() / 2));
                float k = boardSize == 3 ? pxToDp(fieldLength) / NORMAL_FIELDLENGTH_3_IN_DP : pxToDp(fieldLength) / NORMAL_FIELDLENGTH_10_IN_DP;
                float a = boardSize == 3 ? LINE_WIDTH_NORMAL * k : (LINE_WIDTH_NORMAL / 2) * k;
                mPaint.setStrokeWidth(Math.round(a));
                mCirclePaint.setStrokeWidth(Math.round(a));
                mCrossPaint.setStrokeWidth(Math.round(a));


            } else {
                fieldLength = (simplePlayGround.getBoard().cells.length == 10 ? getHeight() - dpToPx(35) - getToolBarHeight() : getHeight() / 2);

                float k = boardSize == 3 ? pxToDp(fieldLength) / NORMAL_FIELDLENGTH_3_IN_DP : pxToDp(fieldLength) / NORMAL_FIELDLENGTH_10_IN_DP;
                float a = boardSize == 3 ? LINE_WIDTH_NORMAL * k : (LINE_WIDTH_NORMAL / 2) * k;
                mPaint.setStrokeWidth(Math.round(a));
                mCirclePaint.setStrokeWidth(Math.round(a));
                mCrossPaint.setStrokeWidth(Math.round(a));


            }





            // drawing vertikal lines

            for (int i = 0; i < simplePlayGround.getBoard().cells.length - 1; i++) {
                float startPosX1;
                float startPosY1;

                startPosY1 = (getHeight() - fieldLength - getToolBarHeight()) / 2 + getToolBarHeight();


                float startPosY2 = startPosY1 + fieldLength;

                startPosX1 = (getWidth() - fieldLength) / 2 + (i + 1) * fieldLength / size;

                float startPosX2 = startPosX1;
                canvas.drawLine(startPosX1, startPosY1, startPosX2, startPosY2, mPaint);

            }

            // drawing horizontal linesа
            for (int i = 0; i < simplePlayGround.getBoard().cells.length - 1; i++) {

                float startPosX1 = (getWidth() - fieldLength) / 2;
                float startPosX2 = startPosX1 + fieldLength;
                float startPosY1;

                startPosY1 = (getHeight() - fieldLength - getToolBarHeight()) / 2 + getToolBarHeight() + (i + 1) * fieldLength / size;

                float startPosY2 = startPosY1;
                canvas.drawLine(startPosX1, startPosY1, startPosX2, startPosY2, mPaint);


            }
            // filling rects
            if (rects[0][0] == null) {
                for (int i = 0; i < size; i++) {
                    float startPosY1 = (getHeight() - fieldLength - getToolBarHeight()) / 2 + getToolBarHeight();
                    for (int j = 0; j < size; j++) {

                        float startPosX1 = (getWidth() - fieldLength) / 2 + (j + 1) * fieldLength / size;

                        rects[i][j] = new Rect(Math.round(startPosX1 - fieldLength / size),
                                Math.round(startPosY1 + i * (fieldLength / size)),
                                Math.round(startPosX1),
                                Math.round(startPosY1 + (i + 1) * (fieldLength / size)));

                    }

                }

            }



            for (int i = 0; i < rects.length; i++) {
                for (int j = 0; j < rects[i].length; j++) {

                    if (simplePlayGround.getBoard().cells[i][j].content == Seed.CROSS) {
                        drawCross(canvas, rects[i][j].centerX(), rects[i][j].centerY());
                        System.out.println("PLAYGROUND " + i + ", " + j + " " + simplePlayGround.getBoard().cells[i][j].content);
                    } else {
                        if (simplePlayGround.getBoard().cells[i][j].content == Seed.NOUGHT) {
                            drawCirlce(canvas, rects[i][j].centerX(), rects[i][j].centerY());
                            System.out.println("PLAYGROUND " + i + ", " + j + " " + simplePlayGround.getBoard().cells[i][j].content);
                        }

                    }

                }
            }
            if (queue.isEmpty()) queue.add("go");
        }
    }


    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN &&
                !gameThread.isAlive()) {

            flag = false;
            touchX = event.getX();
            touchY = event.getY();
            gameThread = new GameThread();
            gameThread.setDaemon(true);
            gameThread.start();
            firstThread = false;
        }


        return true;
    }


    public void drawCirlce(Canvas canvas, int x, int y) {
        int size = simplePlayGround.getBoard().cells.length;
        Path circlePath = new Path();
        float offset = fieldLength / size / 3.9f;
        circlePath.addCircle(x, y, (fieldLength / size - offset) / 2, Path.Direction.CW);
        canvas.drawPath(circlePath, mCirclePaint);

    }

    public void drawCross(Canvas canvas, int x, int y) {
        int size = simplePlayGround.getBoard().cells.length;
        //float offset = (float) getWidth() / 2 / size / 3.9f / 2;
        float offset = fieldLength / size / 3.9f / 2;


        float x1 = (x - (fieldLength / size / 2 - offset));
        float y1 = (y - (fieldLength / size / 2 - offset));
        float x2 = (x + (fieldLength / size / 2 - offset));
        float y2 = (y + (fieldLength / size / 2 - offset));
        canvas.drawLine(x1, y1, x2, y2, mCrossPaint);

        float x3 = (x + (fieldLength / size / 2 - offset));
        float y3 = (y - (fieldLength / size / 2 - offset));
        float x4 = (x - (fieldLength / size / 2 - offset));
        float y4 = (y + (fieldLength / size / 2 - offset));

        canvas.drawLine(x3, y3, x4, y4, mCrossPaint);
        canvas.drawLine(x3, y3, x4, y4, mCrossPaint);
    }


    public void makeBotMove() {
        String s = new Gson().toJson(this.simplePlayGround);
        int[] compMove = bot.makeBotMove(playerSeed == Seed.NOUGHT ? Seed.CROSS : Seed.NOUGHT, s, depth, difficulty);
        int a = compMove[0];
        int b = compMove[1];
        if (!Thread.currentThread().isInterrupted()) {
            System.out.println(" BOT MADE MOVE");
            State state = simplePlayGround.doStep(a, b);
        }




    }


    static class SavedState extends BaseSavedState {
        String savedPlayground;
        String savedBot;
        String savedPlayerSeed;
        int playerWin;
        int compWin;
        int savedBoardSize;
        int savedDifficulty;
        int savedCountMove;


        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            savedPlayground = in.readString();
            savedBot = in.readString();
            savedPlayerSeed = in.readString();
            playerWin = in.readInt();
            compWin = in.readInt();
            savedBoardSize = in.readInt();
            savedDifficulty = in.readInt();
            savedCountMove = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(savedPlayground);
            out.writeString(savedBot);
            out.writeString(savedPlayerSeed);
            out.writeInt(playerWin);
            out.writeInt(compWin);
            out.writeInt(savedBoardSize);
            out.writeInt(savedDifficulty);
            out.writeInt(savedCountMove);
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
        if (!simplePlayGround.isFinished()) {
            ss.savedPlayground = new Gson().toJson(simplePlayGround);
        }

        ss.savedPlayerSeed = new Gson().toJson(playerSeed);
        ss.playerWin = playerWin;
        ss.compWin = compWin;
        ss.savedDifficulty = this.difficulty;
        ss.savedBoardSize = this.boardSize;
        if (!simplePlayGround.isFinished()) {
            ss.savedCountMove = this.countMove;
        }

        return ss;
    }

    @Override
    protected void onDetachedFromWindow() {  //activity destroyed
        super.onDetachedFromWindow();
        if (gameThread.isAlive()) gameThread.interrupt();

    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        if (ss.savedPlayground != null) {
            simplePlayGround = new Gson().fromJson(ss.savedPlayground, SimplePlayGround.class);
        }
        boardSize = simplePlayGround.getBoard().cells.length;
        playerWin = ss.playerWin;
        compWin = ss.compWin;
        this.playerSeed = new Gson().fromJson(ss.savedPlayerSeed, Seed.class);
        this.rects = new Rect[simplePlayGround.getBoard().cells.length][simplePlayGround.getBoard().cells.length];
        this.boardSize = ss.savedBoardSize;
        this.difficulty = ss.savedDifficulty;
        this.countMove = ss.savedCountMove;
        ((MainActivity) getContext()).changeToolBarText(Integer.toString(bestScore),
                Integer.toString(countMove));
    }

    public void startNewGameThread() {
        touchX = 0.0f;
        touchY = 0.0f;
        if (gameThread.isAlive()) gameThread.interrupt();
        countMove = 0;
        ((MainActivity) getContext()).changeToolBarText(Integer.toString(bestScore),
                Integer.toString(countMove));
        simplePlayGround.start();
        invalidate();
        gameThread = new GameThread();
        gameThread.start();

    }

    public void changeBoardSize(int i) {
        if (gameThread.isAlive()) gameThread.interrupt();
        if (i == BOARD_SIZE_3) {
            simplePlayGround = new SimplePlayGround(3, 3, 3);
            boardSize = 3;
            rects = new Rect[3][3];
            depth = difficulty == DIFFICULTY_HARD ? 8 : 5;
            countMove = 0;
            ((MainActivity) getContext()).findViewById(R.id.toolbarTextView).setVisibility(GONE);
        } else {
            simplePlayGround = new SimplePlayGround(10, 10, 5);
            boardSize = 10;
            rects = new Rect[10][10];
            depth = 3;
            countMove = 0;
            ((MainActivity) getContext()).findViewById(R.id.toolbarTextView).setVisibility(VISIBLE);
        }


        MyApplication.preferences.edit().putInt(SAVED_BOARD_SIZE, boardSize).commit();
        try {
            String s = queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startNewGameThread();


    }

    public void changePlayerSeed(Seed seed) {
        playerSeed = seed;
        MyApplication.preferences.edit().putString(SAVED_PLAYER_SEED,
                new Gson().toJson(playerSeed)).commit();

    }

    public void changeDifficulty(int diff) {
        difficulty = diff;
        MyApplication.preferences.edit().putInt(SAVED_DIFFICULTY, difficulty).commit();

    }

    public void changeIsSound(boolean sound) {
        isSound = sound;
        MyApplication.preferences.edit().putBoolean(SAVED_IS_SOUND, isSound).commit();
    }

    private void insertBestScore(int score, String tableName) {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Создаем объект ContentValues, где имена столбцов ключи,
        // а информация о госте является значениями ключей
        ContentValues values = new ContentValues();
        values.put(HoteContract.BestScore.COLUMN_SCORE, score);

        long newRowId = db.update(tableName,
                values, "_id = ?", new String[]{Integer.toString(1)});
    }

    public int displayDataBaseInfo(String tableName) {
        // Создадим и откроем для чтения базу данных
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Зададим условие для выборки - список столбцов
        String[] projection = {
                HoteContract.BestScore._ID,
                // HoteContract.BestScore.COLUMN_NAME,
                HoteContract.BestScore.COLUMN_SCORE};

        String selection = HoteContract.BestScore._ID + ">?";
        String[] selectionArgs = {"0"};


        // Делаем запрос
        Cursor cursor = db.query(
                tableName,   // таблица
                projection,            // столбцы
                selection,                  // столбцы для условия WHERE
                selectionArgs,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                HoteContract.BestScore.COLUMN_SCORE + " DESC");// порядок сортировки


        try {

            // Узнаем индекс каждого столбца
            int idColumnIndex = cursor.getColumnIndex(HoteContract.BestScore._ID);
            // int nameColumnIndex = cursor.getColumnIndex(HoteContract.BestScore.COLUMN_NAME);
            int scoreColumnIndex = cursor.getColumnIndex(HoteContract.BestScore.COLUMN_SCORE);

//            // Проходим через все ряды
            while (cursor.moveToNext()) {
                // Используем индекс для получения строки или числа
                int currentID = cursor.getInt(idColumnIndex);
                //String currentName = cursor.getString(nameColumnIndex);
                String currentScore = cursor.getString(scoreColumnIndex);
                System.out.println("ID=" + currentID + " score=" + currentScore);

                return Integer.parseInt(currentScore);
            }
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
        return 0;
    }

    public void saveBestScore(int bestScore) {
        if (playerSeed == Seed.CROSS) {
            String tableName = difficulty == DIFFICULTY_EASY ? HoteContract.BestScore.TABLE_NAME_DIFF_EASY_CROSS :
                    HoteContract.BestScore.TABLE_NAME_DIFF_HARD_CROSS;
            insertBestScore(bestScore, tableName);
        } else if (playerSeed == Seed.NOUGHT) {
            String tableName = difficulty == DIFFICULTY_EASY ? HoteContract.BestScore.TABLE_NAME_DIFF_EASY_NOUGHT :
                    HoteContract.BestScore.TABLE_NAME_DIFF_HARD_NOUGHT;
            insertBestScore(bestScore, tableName);
        }

    }

    public void readBestScore() {
        if (playerSeed == Seed.CROSS) {
            bestScore = (difficulty == DIFFICULTY_EASY ? displayDataBaseInfo(HoteContract.BestScore.TABLE_NAME_DIFF_EASY_CROSS) :
                    displayDataBaseInfo(HoteContract.BestScore.TABLE_NAME_DIFF_HARD_CROSS));
        } else if (playerSeed == Seed.NOUGHT) {
            bestScore = (difficulty == DIFFICULTY_EASY ? displayDataBaseInfo(HoteContract.BestScore.TABLE_NAME_DIFF_EASY_NOUGHT) :
                    displayDataBaseInfo(HoteContract.BestScore.TABLE_NAME_DIFF_HARD_NOUGHT));
        }

    }


    public int getToolBarHeight() {
        return ((MainActivity) getContext()).toolbar.getHeight();
    }

    public float pxToDp(float px) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        float dp = px / displayMetrics.density;
        return dp;
    }

    public int dpToPx(float dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * displayMetrics.density);
        return px;
    }


}
