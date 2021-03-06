package com.vitalii.s.a10tictactoe.Activities;


import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.vitalii.s.a10tictactoe.Fragments.BestScoreFragment;
import com.vitalii.s.a10tictactoe.Fragments.ChangeBoardSizeFragment;
import com.vitalii.s.a10tictactoe.Fragments.ChangeDifficultyFragment;
import com.vitalii.s.a10tictactoe.Fragments.ChangePlayerFragment;
import com.vitalii.s.a10tictactoe.Fragments.ChangeSoundFragment;
import com.vitalii.s.a10tictactoe.MyApplication;
import com.vitalii.s.a10tictactoe.Fragments.NewGameFragment;
import com.vitalii.s.a10tictactoe.R;
import com.vitalii.s.a10tictactoe.Data.Sound;

import com.vitalii.s.a10tictactoe.Models.playground.Seed;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public GameViewStatic gameView;
    public TextView barText;
    public Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(gameView); //- если только gameView - без кнопок и др элементов
        setContentView(R.layout.activity_main);
        if (MyApplication.preferences.getInt(GameViewStatic.SAVED_BOARD_SIZE,
                GameViewStatic.BOARD_SIZE_3) == GameViewStatic.BOARD_SIZE_10) {
            findViewById(R.id.toolbarTextView).setVisibility(View.VISIBLE);
        }
        gameView = (GameViewStatic) findViewById(R.id.viewStatic);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        showBestScore();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        Sound.init(this);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_new_game) {
            FragmentManager manager = getSupportFragmentManager();
            NewGameFragment newGameFragment = new NewGameFragment();
            newGameFragment.show(manager, "new game dialog");


        } else if (id == R.id.nav_board_size) {
            FragmentManager manager = getSupportFragmentManager();
            ChangeBoardSizeFragment changeBoardSizeFragment = new ChangeBoardSizeFragment();
            changeBoardSizeFragment.show(manager, "change board size dialog");


        } else if (id == R.id.nav_player) {
            FragmentManager manager = getSupportFragmentManager();
            ChangePlayerFragment changePlayerFragment = new ChangePlayerFragment();
            changePlayerFragment.show(manager, "change player dialog");

        } else if (id == R.id.nav_difficulty) {
            FragmentManager manager = getSupportFragmentManager();
            ChangeDifficultyFragment changeDifficultyFragment = new ChangeDifficultyFragment();
            changeDifficultyFragment.show(manager, "change difficulty fragment");

        } else if (id == R.id.nav_sound) {
            FragmentManager manager = getSupportFragmentManager();
            ChangeSoundFragment changeSoundFragment = new ChangeSoundFragment();
            changeSoundFragment.show(manager, "change sound fragment");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void startNewGame() {
        gameView.startNewGameThread();
    }

    public void changeBoardSize(int i) {
        gameView.changeBoardSize(i);
    }

    public void changePlayerSeed(Seed seed) {
        gameView.changePlayerSeed(seed);
        gameView.readBestScore();
        showBestScore();
    }

    public void changeDifficulty(int diff) {
        gameView.changeDifficulty(diff);
        gameView.readBestScore();
        showBestScore();
    }

    public void changeIsSound(boolean isSound) {
        gameView.changeIsSound(isSound);
    }

    public void changeToolBarText(final String bestScore, final String playerMove) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String text;
                if (!bestScore.equals("0")) {
                    text = getResources().getString(R.string.toolBarText, bestScore, playerMove, " ");
                } else {
                    text = getResources().getString(R.string.toolBarText, "  ", playerMove, " ");
                }
                barText.setText(text);
            }
        });

    }

    public void showBestScoreFragment() {
        FragmentManager manager = getSupportFragmentManager();
        BestScoreFragment bestScoreFragment = new BestScoreFragment();
        bestScoreFragment.show(manager, "change player dialog");

    }

    public void showBestScore() {
        barText = (TextView) findViewById(R.id.toolbarTextView);
        String bestScore = Integer.toString(gameView.bestScore);
        String playerMove = Integer.toString(gameView.countMove);
        String text;
        if (!bestScore.equals("0")) {
            text = getResources().getString(R.string.toolBarText, bestScore, playerMove, " ");
        } else {
            text = getResources().getString(R.string.toolBarText, "  ", playerMove, " ");
        }
        barText.setText(text);
    }


}

