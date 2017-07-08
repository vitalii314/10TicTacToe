package com.vitalii.s.a10tictactoe;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import playground.SimplePlayGround;

public class MainActivity extends Activity {

    private GameViewStatic gameView;
    public TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //gameView.setId(R.id.viewStatic);
       // setContentView(gameView); //- если только gameView - без кнопок и др элементов
        setContentView(R.layout.activity_main);
        gameView = (GameViewStatic)findViewById(R.id.viewStatic);
        scoreText = (TextView) findViewById(R.id.scoreText);
        scoreText.setText("Score:" + GameViewStatic.playerWin+":"+GameViewStatic.compWin);
    }


//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        Toast.makeText(this,"restored",Toast.LENGTH_LONG).show();
//
//    }




}

