package ru.vsu.savina.tablegame.application;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.vsu.savina.tablegame.R;
import ru.vsu.savina.tablegame.application.reader.FileReader;
import ru.vsu.savina.tablegame.application.reader.ItemsReader;
import ru.vsu.savina.tablegame.application.reader.PlaceActionReader;
import ru.vsu.savina.tablegame.application.reader.SituationsReader;
import ru.vsu.savina.tablegame.fieldgeneration.FieldGeneration;
import ru.vsu.savina.tablegame.game.Game;
import ru.vsu.savina.tablegame.game.engine.action.IPlaceAction;
import ru.vsu.savina.tablegame.game.engine.item.Item;
import ru.vsu.savina.tablegame.game.impl.action.Chooseable;
import ru.vsu.savina.tablegame.game.impl.action.IChoiceAction;
import ru.vsu.savina.tablegame.game.impl.dice.Dice;
import ru.vsu.savina.tablegame.game.impl.field.BluePlace;
import ru.vsu.savina.tablegame.game.impl.field.CampPlace;
import ru.vsu.savina.tablegame.game.impl.field.GreenPlace;
import ru.vsu.savina.tablegame.game.impl.field.GreyPlace;
import ru.vsu.savina.tablegame.game.impl.field.RedEnemyPlace;
import ru.vsu.savina.tablegame.game.impl.field.RedMonsterPlace;
import ru.vsu.savina.tablegame.game.impl.field.RedNativesPlace;
import ru.vsu.savina.tablegame.game.impl.field.YellowPlace;
import ru.vsu.savina.tablegame.game.impl.gameevent.IGameEvent;
import ru.vsu.savina.tablegame.game.impl.gameevent.ItemLossEvent;
import ru.vsu.savina.tablegame.game.impl.gameevent.PlayerStateChangeEvent;
import ru.vsu.savina.tablegame.game.impl.gameevent.RollDiceEvent;
import ru.vsu.savina.tablegame.game.impl.gamestate.ChoosePlayerAction;
import ru.vsu.savina.tablegame.game.impl.gamestate.ExecutePlaceAction;
import ru.vsu.savina.tablegame.game.impl.gamestate.IGameAction;
import ru.vsu.savina.tablegame.game.impl.gamestate.MovePlayerAction;
import ru.vsu.savina.tablegame.game.impl.gamestate.RollDiceAction;
import ru.vsu.savina.tablegame.game.impl.player.PlayerState;
import ru.vsu.savina.tablegame.game.impl.player.TableGamePlayer;
import ru.vsu.savina.tablegame.game.impl.situation.Situation;
import ru.vsu.savina.tablegame.game.impl.situation.SituationCards;
import ru.vsu.savina.tablegame.view.activity.GameActivity;
import ru.vsu.savina.tablegame.view.wrapper.FieldWrapper;

public class GameApplication {
    private Game game;
    private final GameActivity activity;

    private FieldWrapper wrapper;

    public GameApplication(GameActivity activity) {
        this.activity = activity;
        game = new Game(this);
    }

    public void gameStep() {
        if (game.getCurrAction() == null)
            game.setCurrAction(new ChoosePlayerAction());
        game.turn();
    }

    public void prepareGame(List<String> playerNames) {
        game.setItemMap(createItems());
        game.setSituationCards(createSituations());
        game.setDice(new Dice());

        game.setPlayerList(createPlayers(playerNames));

        createField();
        game.setField(wrapper.getField());
        game.setPlaceActionMap(createPlaceActionMap());
    }

    private List<TableGamePlayer> createPlayers(List<String> playerNames) {
        List<TableGamePlayer> playerList = new ArrayList<>();

        for (String name : playerNames) {
            TableGamePlayer player = new TableGamePlayer(name);
            player.setMoney(15000);
            playerList.add(player);
        }
        return playerList;
    }

    private SituationCards createSituations() {
        String s = FileReader.readFile(activity.getResources().getString(R.string.situations_file_name), activity);
        String className = activity.getResources().getString(R.string.items_class_name);
        List<Situation> situationList = SituationsReader.getSituationCards(s, className);
        SituationCards cards = new SituationCards(situationList);
        return cards;
    }

    private Map<Item, Integer> createItems() {
        String items = FileReader.readFile(activity.getResources().getString(R.string.items_file_name), activity);
        String className = activity.getResources().getString(R.string.items_class_name);
        Map<Item, Integer> itemMap = ItemsReader.getItemMap(items, className);
        return itemMap;
    }

    private void createField() {
        int N = activity.getResources().getInteger(R.integer.field_size);
        int L = activity.getResources().getInteger(R.integer.relaxation);

        double width = activity.getResources().getInteger(R.integer.field_width);
        double height = activity.getResources().getInteger(R.integer.field_height);

        wrapper = FieldGeneration.generateField(N, L, width, height);
    }

    private Map<Type, IPlaceAction> createPlaceActionMap() {
        String actions = FileReader.readFile(activity.getResources().getString(R.string.actions_file_name), activity);
        String placeClassName = activity.getResources().getString(R.string.place_class_name);
        String actionClassName = activity.getResources().getString(R.string.actions_class_name);
        Map<Type, IPlaceAction> placeMap = PlaceActionReader.getPlaceActionMap(actions, placeClassName, actionClassName);
        return placeMap;
    }

    public void notifyOnGameEvent(IGameEvent event) {
        if (event instanceof ItemLossEvent) {
            activity.showMessageDialog(activity.getResources().getString(R.string.lbl_item_loss) + ((ItemLossEvent) event).getItem().optionInfo() + ".");
        }
        if (event instanceof RollDiceEvent) {
            activity.showMessageDialog(activity.getResources().getString(R.string.lbl_dice_num) + ((RollDiceEvent)event).getDiceNum());
        }

        if (event instanceof PlayerStateChangeEvent) {
            if (((PlayerStateChangeEvent)event).getState() == PlayerState.PASS_TURN) {
                activity.showMessageDialog(activity.getResources().getString(R.string.lbl_pass_turn));
            }
            else {
                activity.showMessageDialog(activity.getResources().getString(R.string.lbl_game_over));
            }
        }
    }

    public void notifyOnGameAction(IGameAction action) {
        if (action instanceof MovePlayerAction) {
            activity.showMessageDialog(activity.getResources().getString(R.string.lbl_dice_num) + ((MovePlayerAction) action).getDiceNum());
        }
        if (action instanceof ChoosePlayerAction) {
            activity.showMessageDialog(activity.getResources().getString(R.string.lbl_new_player) + game.getActivePlayer().getName());
        }
        if (action instanceof ExecutePlaceAction) {
            activity.updateView();

            if (game.getPlayerPlaceMap().get(game.getActivePlayer()) instanceof BluePlace) {
                activity.showMessageDialog(activity.getResources().getString(R.string.lbl_blue_place));
            }
            if (game.getPlayerPlaceMap().get(game.getActivePlayer()) instanceof GreenPlace) {
                activity.showMessageDialog(activity.getResources().getString(R.string.lbl_green_place));
            }
            if (game.getPlayerPlaceMap().get(game.getActivePlayer()) instanceof CampPlace) {
                activity.showMessageDialog(activity.getResources().getString(R.string.lbl_camp_place));
            }
            if (game.getPlayerPlaceMap().get(game.getActivePlayer()) instanceof GreyPlace) {
                activity.showMessageDialog(activity.getResources().getString(R.string.lbl_grey_place));
            }
            if (game.getPlayerPlaceMap().get(game.getActivePlayer()) instanceof RedMonsterPlace) {
                activity.showMessageDialog(activity.getResources().getString(R.string.lbl_red_monster_place));
            }
            if (game.getPlayerPlaceMap().get(game.getActivePlayer()) instanceof RedEnemyPlace) {
                activity.showMessageDialog(activity.getResources().getString(R.string.lbl_red_enemy_place));
            }
            if (game.getPlayerPlaceMap().get(game.getActivePlayer()) instanceof RedNativesPlace) {
                activity.showMessageDialog(activity.getResources().getString(R.string.lbl_red_natives_place));
            }
            if (game.getPlayerPlaceMap().get(game.getActivePlayer()) instanceof YellowPlace) {
                activity.showMessageDialog(activity.getResources().getString(R.string.lbl_yellow_place));
            }
        }

    }

    public void chooseItem(List<? extends Chooseable> optionList, IChoiceAction onChoiceMade) {
        activity.showChoseDialog(optionList, onChoiceMade);
    }

    public void updateView() {
        activity.updateView();
    }

    public FieldWrapper getWrapper() {
        return wrapper;
    }

    public Game getGame() {
        return game;
    }

}
