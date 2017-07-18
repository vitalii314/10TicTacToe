package com.vitalii.s.a10tictactoe;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

/**
 * Created by user on 18.07.2017.
 */
public class NewGameFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("New game")
                .setMessage("Start new game?")
                .setIcon(null)
                .setPositiveButton("CROSS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        ((MainActivity)getActivity()).startNewGame();
                    }
                })
                .setNegativeButton("NOUGHT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        Toast.makeText(getActivity(),"You don't want to play",Toast.LENGTH_SHORT).show();
                    }
                });


        return builder.create();
    }
}
