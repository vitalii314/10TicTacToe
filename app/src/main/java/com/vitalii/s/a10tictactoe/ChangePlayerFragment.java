package com.vitalii.s.a10tictactoe;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.widget.Toast;

import playground.Seed;

/**
 * Created by user on 07.08.2017.
 */
public class ChangePlayerFragment extends DialogFragment {

    final String[] items = {"Cross","Nought"};
    int selection;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        selection=((MainActivity)getContext()).gameView.playerSeed==Seed.CROSS?0:1;


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        builder.setTitle("Choose side");

        //list of items

        builder.setSingleChoiceItems(items,selection,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selection = which;
                    }
                });

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        if (selection == 0) {
                            ((MainActivity) getContext()).changePlayerSeed(Seed.CROSS);
                            ((MainActivity) getContext()).startNewGame();

                        } else {
                            ((MainActivity) getContext()).changePlayerSeed(Seed.NOUGHT);
                            ((MainActivity) getContext()).startNewGame();
                        }
                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel(); // negative button logic
                    }
                });


        return builder.create();

    }
}

