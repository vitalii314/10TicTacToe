package com.vitalii.s.a10tictactoe;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by user on 07.08.2017.
 */
public class ChangeSoundFragment extends DialogFragment {

    final String[] items = {"ON", "OFF"};
    int selection;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        selection = ((MainActivity) getContext()).gameView.isSound ? 0 : 1;


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        builder.setTitle("Sound");

        //list of items

        builder.setSingleChoiceItems(items, selection,
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
                            ((MainActivity) getContext()).changeIsSound(true);
                            Toast.makeText(getContext(), "Sound=" +
                                    ((MainActivity) getContext()).gameView.isSound, Toast.LENGTH_SHORT).show();

                        } else {
                            ((MainActivity) getContext()).changeIsSound(false);
                            Toast.makeText(getContext(), "Sound=" +
                                    ((MainActivity) getContext()).gameView.isSound, Toast.LENGTH_SHORT).show();
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
