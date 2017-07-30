package com.vitalii.s.a10tictactoe;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import playground.Seed;

/**
 * Created by user on 18.07.2017.
 */
public class NewGameFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),4);
        builder.setTitle("New game")
                .setMessage("Start new game?")
                .setIcon(null)
                .setPositiveButton("CROSS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        ((MainActivity)getActivity()).changePlayerSeed(Seed.CROSS);
                        ((MainActivity)getActivity()).startNewGame();
                    }
                })
                .setNegativeButton("NOUGHT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((MainActivity)getActivity()).changePlayerSeed(Seed.NOUGHT);
                        ((MainActivity)getActivity()).startNewGame();
                        //dialogInterface.cancel();
                    }
                });


        return builder.create();
    }
}
