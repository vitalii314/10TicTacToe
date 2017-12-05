package com.vitalii.s.a10tictactoe.Fragments;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.vitalii.s.a10tictactoe.Activities.MainActivity;
import com.vitalii.s.a10tictactoe.R;

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

                        } else {
                            ((MainActivity) getContext()).changeIsSound(false);

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
