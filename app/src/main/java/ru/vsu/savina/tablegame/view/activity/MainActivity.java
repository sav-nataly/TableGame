package ru.vsu.savina.tablegame.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.vsu.savina.tablegame.R;
import ru.vsu.savina.tablegame.view.fragment.PlayerInputDialogFragment;
import ru.vsu.savina.tablegame.view.listadapter.PlayerListAdapter;

public class MainActivity extends AppCompatActivity implements PlayerInputDialogFragment.PlayerInputDialogListener {
    private final PlayerListAdapter adapter = new PlayerListAdapter(new ArrayList<>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonStart = findViewById(R.id.button_start);
        buttonStart.setOnClickListener(v -> startGameButtonClick());

        Button buttonAdd = findViewById(R.id.button_add);
        buttonAdd.setOnClickListener(v -> addPlayerButtonClick());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_player);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void addPlayerButtonClick() {
        PlayerInputDialogFragment fragment = new PlayerInputDialogFragment();
        fragment.show(getSupportFragmentManager(), "player_input");
    }

    private void startGameButtonClick() {
        Intent gameActivityIntent = new Intent(
                getApplicationContext(), GameActivity.class
        );

        gameActivityIntent.putExtra("playerNameList", adapter.getPlayerNameList());
        startActivity(gameActivityIntent);
    }

    @Override
    public void onDialogSaveClick(String name) {
        adapter.addPlayer(name);
    }

    @Override
    public void onDialogCancelClick() {
    }
}