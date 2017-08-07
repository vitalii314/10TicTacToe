package com.vitalii.s.a10tictactoe;


import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;


import playground.Seed;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public GameViewStatic gameView;
    public TextView scoreText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //gameView.setId(R.id.viewStatic);
        // setContentView(gameView); //- если только gameView - без кнопок и др элементов
        setContentView(R.layout.activity_main);
        gameView = (GameViewStatic) findViewById(R.id.viewStatic);
        scoreText = (TextView) findViewById(R.id.scoreText);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
            newGameFragment.show(manager,"new game dialog");



        } else if (id == R.id.nav_board_size) {
            FragmentManager manager = getSupportFragmentManager();
            ChangeBoardSizeFragment changeBoardSizeFragment = new ChangeBoardSizeFragment();
            changeBoardSizeFragment.show(manager,"change board size dialog");


        }  else if (id==R.id.nav_player) {
            FragmentManager manager = getSupportFragmentManager();
            ChangePlayerFragment changePlayerFragment = new ChangePlayerFragment();
            changePlayerFragment.show(manager,"change player dialog");

        }
        else if (id == R.id.nav_difficulty) {
            FragmentManager manager = getSupportFragmentManager();
            ChangeDifficultyFragment changeDifficultyFragment = new ChangeDifficultyFragment();
            changeDifficultyFragment.show(manager,"change difficulty fragment");

        } else if (id == R.id.nav_sound) {
            FragmentManager manager = getSupportFragmentManager();
            ChangeSoundFragment changeSoundFragment = new ChangeSoundFragment();
            changeSoundFragment.show(manager,"change sound fragment");

        }

//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void startNewGame() {
        gameView.startNewGameThread();
        gameView.simplePlayGround.start();
        gameView.invalidate();
    }

    public void changeBoardSize (int i) {
       gameView.changeBoardSize(i);
    }

    public void changePlayerSeed(Seed seed) {
        gameView.playerSeed=seed;
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

