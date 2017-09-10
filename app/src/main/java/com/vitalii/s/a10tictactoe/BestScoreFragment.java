package com.vitalii.s.a10tictactoe;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

/**
 * Created by user on 10.09.2017.
 */
public class BestScoreFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.MyAlertDialogStyle);
        builder.setTitle("Best Score")
                .setMessage(getResources().getString(R.string.bestScoreText,
                        ((MainActivity)getActivity()).gameView.countMove))
                .setIcon(null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                       dialog.cancel();
                    }
                });



        return builder.create();
    }
}
