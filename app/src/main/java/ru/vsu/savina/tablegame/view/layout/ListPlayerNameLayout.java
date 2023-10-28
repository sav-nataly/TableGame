package ru.vsu.savina.tablegame.view.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import ru.vsu.savina.tablegame.R;

public class ListPlayerNameLayout extends LinearLayout {
    public ListPlayerNameLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_list_player_name, this);
    }
}
