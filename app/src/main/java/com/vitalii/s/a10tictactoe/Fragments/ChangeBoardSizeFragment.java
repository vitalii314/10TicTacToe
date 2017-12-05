package com.vitalii.s.a10tictactoe.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;

import com.vitalii.s.a10tictactoe.Activities.GameViewStatic;
import com.vitalii.s.a10tictactoe.Activities.MainActivity;
import com.vitalii.s.a10tictactoe.R;


/**
 * Created by user on 31.07.2017.
 */
public class ChangeBoardSizeFragment extends DialogFragment {

    final String[] items = {"3 X 3", "10 X 10"};
    int selection;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        selection=((MainActivity)getContext()).gameView.simplePlayGround.getBoard().cells.length== GameViewStatic.BOARD_SIZE_3?0:1;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);

        builder.setTitle("Select board size");

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

    @Override
    public void onResume() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int heigth = getDialog().getWindow().getAttributes().height;
        if (width/displayMetrics.density>=335) width = (int)(300*displayMetrics.density);
        getDialog().getWindow().setLayout(width,heigth );
        super.onResume();
    }
}

