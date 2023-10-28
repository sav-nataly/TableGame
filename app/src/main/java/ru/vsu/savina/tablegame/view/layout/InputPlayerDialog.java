package ru.vsu.savina.tablegame.view.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import ru.vsu.savina.tablegame.R;

public class InputPlayerDialog extends LinearLayout {
    public InputPlayerDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.dialog_input_player, this);
    }
}
