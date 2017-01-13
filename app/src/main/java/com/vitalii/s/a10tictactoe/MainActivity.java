package com.vitalii.s.a10tictactoe;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import playground.SimplePlayGround;

public class MainActivity extends Activity {

    private GameViewStatic gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameViewStatic gameView = new GameViewStatic(this);
        gameView.setId(R.id.viewStatic);
        setContentView(gameView); //- если только gameView - без кнопок и др элементов
//        setContentView(R.layout.activity_main);
//        gameView = (GameViewStatic)findViewById(R.id.viewStatic);

    }



//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//    }
//
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//    }


}

