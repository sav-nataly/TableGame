package ru.vsu.savina.tablegame.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.List;

import ru.vsu.savina.tablegame.R;
import ru.vsu.savina.tablegame.game.impl.action.Chooseable;
import ru.vsu.savina.tablegame.game.impl.action.IChoiceAction;

public class OptionChooseDialog extends DialogFragment {
    public interface OptionChooseDialogListener {
        void onOptionClick(Chooseable option, IChoiceAction action);
    }

    OptionChooseDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (OptionChooseDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException("PlayerInputDialogIListener not implemented");
        }
    }

    private List<? extends Chooseable> optionList;
    private IChoiceAction action;

    public OptionChooseDialog(List<? extends Chooseable> optionList, IChoiceAction action) {
        this.optionList = optionList;
        this.action = action;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] options = new String[optionList.size()];
        for (int i = 0; i < optionList.size(); i++) {
            options[i] = optionList.get(i).optionInfo();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.lbl_option_dialog_question).setCancelable(false)
                .setItems(options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onOptionClick(optionList.get(which), action);
                    }
                });
        return builder.create();
    }
}
