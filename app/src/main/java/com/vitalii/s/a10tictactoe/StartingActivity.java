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
import android.widget.Toast;




public class StartingActivity extends Activity {

    public  Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kletka3);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_starting);


    }





    public void onSingleButtonClick(View view) {
        Intent intent = new Intent(StartingActivity.this, MainActivity.class);
        startActivity(intent);
    }







    public void onMultiButtonClick(View view) {
        Toast.makeText(this,"Multiplayer mode is being developed",Toast.LENGTH_SHORT).show();
    }
}
