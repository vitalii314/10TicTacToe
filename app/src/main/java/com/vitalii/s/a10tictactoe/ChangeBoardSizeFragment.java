package com.vitalii.s.a10tictactoe;
import android.support.v4.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;


/**
 * Created by user on 31.07.2017.
 */
public class ChangeBoardSizeFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),4);
        builder.setTitle("Board size")
                .setMessage("Change board size?")
                .setIcon(null)
                .setPositiveButton("3 X 3", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        ((MainActivity)getActivity()).changeBoardSize(3);
                    }
                })
                .setNegativeButton("10 X 10", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((MainActivity)getActivity()).changeBoardSize(10);
                        //dialogInterface.cancel();
                    }
                });


        return builder.create();
    }
}

