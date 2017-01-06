package com.vitalii.s.a10tictactoe;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.LruCache;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;

import static android.os.Environment.isExternalStorageRemovable;

public class StartingActivity extends Activity {

    private static LruCache<String, Bitmap> mMemoryCache;
    public  Bitmap bitmap;
    public static DiskLruImageCache mDiskLruImageCache;
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
    private static final String DISK_CACHE_SUBDIR = "thumbnails";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
//
//        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
//        final int cacheSize = maxMemory / 8;
//        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
//            @Override
//            protected int sizeOf(String key, Bitmap bitmap) {
//                // The cache size will be measured in kilobytes rather than
//                // number of items.
//                return bitmap.getByteCount() / 1024;
//            }
//        };

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kletka3);

//        mDiskLruImageCache = new DiskLruImageCache(this,"myCache",DISK_CACHE_SIZE, Bitmap.CompressFormat.JPEG,70);
//        addBitmapToMemoryCache("1", bitmap);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_starting);


    }





    public void onSingleButtonClick(View view) {
        Intent intent = new Intent(StartingActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromCache(key) == null){
            //mMemoryCache.put(key, bitmap);
            mDiskLruImageCache.put(key, bitmap);
        }
    }

    public static Bitmap getBitmapFromCache(String key) {

        //Bitmap bitmap = mMemoryCache.get(key);
        Bitmap bitmap = mDiskLruImageCache.getBitmap(key);
       // if (bitmap==null) bitmap = mDiskLruImageCache.getBitmap(key);
        return  bitmap;
    }


}
