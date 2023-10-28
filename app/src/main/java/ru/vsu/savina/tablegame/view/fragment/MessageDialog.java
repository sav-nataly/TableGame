package ru.vsu.savina.tablegame.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class MessageDialog extends DialogFragment {
    public MessageDialog(String message) {
        this.message = message;
    }

    public interface MessageDialogListener {
        void onCloseClick();
    }

    MessageDialogListener listener;
    private String message;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (MessageDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException("MessageDialogIListener not implemented");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(message).setCancelable(false).setPositiveButton("Закрыть", (dialog, which) -> listener.onCloseClick());
        return builder.create();
    }
}
