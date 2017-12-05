package com.vitalii.s.a10tictactoe.Fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;


import com.vitalii.s.a10tictactoe.Activities.MainActivity;
import com.vitalii.s.a10tictactoe.R;

/**
 * Created by user on 18.07.2017.
 */
public class NewGameFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);


        builder.setTitle("New game")
                .setMessage("Start new game?")
                .setIcon(null)
               .setPositiveButton(R.string.ok_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        ((MainActivity)getActivity()).startNewGame();
                    }
                })
                .setNegativeButton(R.string.cancel_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
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
