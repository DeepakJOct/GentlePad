package com.example.gentlepad.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.gentlepad.listeners.DeleteItemListener;

public class YesOrNoDialogFragment extends DialogFragment {
    private DeleteItemListener deleteItemListener;
    public YesOrNoDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    public static YesOrNoDialogFragment newInstance(String title, String message, DeleteItemListener deleteItemListener) {
        YesOrNoDialogFragment frag = new YesOrNoDialogFragment();
        frag.deleteItemListener = deleteItemListener;
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        String message = getArguments().getString("message");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success
                deleteItemListener.onSuccess(true);

            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }

        });

        return alertDialogBuilder.create();
    }
}

