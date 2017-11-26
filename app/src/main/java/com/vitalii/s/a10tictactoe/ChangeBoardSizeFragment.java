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


/**
 * Created by user on 31.07.2017.
 */
public class ChangeBoardSizeFragment extends DialogFragment {

    final String[] items = {"3 X 3", "10 X 10"};
    int selection;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        selection=((MainActivity)getContext()).gameView.simplePlayGround.getBoard().cells.length==GameViewStatic.BOARD_SIZE_3?0:1;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        builder.setTitle("Choose board size");

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
                            ((MainActivity) getContext()).changeBoardSize(GameViewStatic.BOARD_SIZE_3);
                        } else {
                            ((MainActivity) getContext()).changeBoardSize(GameViewStatic.BOARD_SIZE_10);
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

