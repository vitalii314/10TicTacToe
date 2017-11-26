package com.vitalii.s.a10tictactoe;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import playground.Seed;

/**
 * Created by user on 07.08.2017.
 */
public class ChangeDifficultyFragment extends DialogFragment {

    final String[] items = {"Easy","Hard"};
    int selection;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        selection=((MainActivity)getContext()).gameView.difficulty==GameViewStatic.DIFFICULTY_EASY?0:1;


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        builder.setTitle("Choose difficulty");

        //list of items

        builder.setSingleChoiceItems(items,selection,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selection = which;
                    }
                });

        String positiveText = getString(R.string.ok_text);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        if (selection == 0) {
                            ((MainActivity) getContext()).changeDifficulty(GameViewStatic.DIFFICULTY_EASY);
                            ((MainActivity) getContext()).startNewGame();


                        } else {
                            ((MainActivity) getContext()).changeDifficulty(GameViewStatic.DIFFICULTY_HARD);
                            ((MainActivity) getContext()).startNewGame();

                        }
                    }
                });

        String negativeText = getString(R.string.cancel_text);
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
