package ru.vsu.savina.tablegame.view.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.vsu.savina.tablegame.R;
import ru.vsu.savina.tablegame.application.GameApplication;
import ru.vsu.savina.tablegame.game.impl.action.Chooseable;
import ru.vsu.savina.tablegame.game.impl.action.IChoiceAction;
import ru.vsu.savina.tablegame.view.fragment.ItemShopDialog;
import ru.vsu.savina.tablegame.view.fragment.MessageDialog;
import ru.vsu.savina.tablegame.view.fragment.OptionChooseDialog;
import ru.vsu.savina.tablegame.view.view.FieldView;

public class GameActivity extends AppCompatActivity implements OptionChooseDialog.OptionChooseDialogListener, ItemShopDialog.ItemShopListener, MessageDialog.MessageDialogListener {
    private GameApplication application;

    private CoordinatorLayout coordinatorLayout;
    private FieldView fieldView;
    private Button continueButton;

    private ItemShopDialog shopDialog;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_message);
        List<String> playerNameList = (ArrayList<String>) getIntent().getSerializableExtra("playerNameList");
        application = new GameApplication(this);
        application.prepareGame(playerNameList);

        fieldView = findViewById(R.id.field_view);
        fieldView.setWrapper(application.getWrapper());
        fieldView.setPlayerPlaceMap(application.getGame().getPlayerPlaceMap());
        fieldView.invalidate();

        continueButton = (Button) findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContinueButtonClick();
            }
        });

        showItemsStartDialog();
    }

    public void showChoseDialog(List<? extends Chooseable> options, IChoiceAction action) {
        continueButton.setClickable(false);
        OptionChooseDialog dialog = new OptionChooseDialog(options, action);
        dialog.show(getSupportFragmentManager(), "player_choice");
    }

    public void showItemsStartDialog() {
        shopDialog = new ItemShopDialog(application.getGame().getPlayerList(), application.getGame().getItemMap());
        shopDialog.show(getSupportFragmentManager(), "start_shop_dialog");
    }

    public void showMessageDialog(String message) {
        continueButton.setClickable(false);
        MessageDialog dialog = new MessageDialog(message);
        dialog.show(getSupportFragmentManager(), "message_dialog");
    }

    public void onContinueButtonClick() {
        application.gameStep();
    }

    @Override
    public void onOptionClick(Chooseable option, IChoiceAction action) {
        continueButton.setClickable(true);
        action.onChoiceMade(option);
    }

    @Override
    public void onDialogReadyClick() {
        shopDialog.getDialog().dismiss();

        TextView text = findViewById(R.id.turn_player_view);
        text.setText(application.getGame().getActivePlayer().getName());
    }

    public void updateView() {
        fieldView.invalidate();
    }

    @Override
    public void onCloseClick() {
       continueButton.setClickable(true);
    }
}