package com.vitalii.s.a10tictactoe.Data;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by user on 05.11.2017.
 */
public class Sound {

    private static AssetManager mAssetManager;
    public static SoundPool mSoundPool;
    public static int mMoveSound;
    public static int mWinSound;
    private static int mBestScoreSound;
    public static int mStreamID;
    public static boolean mSoundLoaded = false;


    public static void init(Context context) {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                mSoundLoaded = true;
            }
        });


        mAssetManager = context.getAssets();

        //получим идентификаторы
        mMoveSound = loadSound("moveSoundNew.wav", context);
        mBestScoreSound = loadSound("bestScoreSound.wav", context);
        mWinSound = loadSound("win.wav", context);


    }

    @SuppressWarnings("deprecation")
    private static void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static  void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    private static int loadSound(String fileName, Context context) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Can't load sound file  " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }

        return mSoundPool.load(afd, 1);
    }



    public static int playSound(int sound) {

        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }

        return mStreamID;
    }

    public static void playMoveSound() {
        if (mSoundLoaded) playSound(mMoveSound);

    }

    public  static void playWinSound() {
        if (mSoundLoaded) playSound(mWinSound);
    }

    public  static void playBestScoreSound() {
        if (mSoundLoaded) playSound(mBestScoreSound);
    }

    public static void stopSound() {
        mSoundPool.stop(mStreamID);
    }


}

