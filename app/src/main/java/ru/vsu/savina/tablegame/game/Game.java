package ru.vsu.savina.tablegame.game;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.vsu.savina.tablegame.application.GameApplication;
import ru.vsu.savina.tablegame.game.engine.IGame;
import ru.vsu.savina.tablegame.game.engine.action.IPlaceAction;
import ru.vsu.savina.tablegame.game.engine.dice.IDice;
import ru.vsu.savina.tablegame.game.engine.field.Field;
import ru.vsu.savina.tablegame.game.engine.field.Place;
import ru.vsu.savina.tablegame.game.engine.item.Item;
import ru.vsu.savina.tablegame.game.impl.action.Chooseable;
import ru.vsu.savina.tablegame.game.impl.action.IChoiceAction;
import ru.vsu.savina.tablegame.game.impl.gameevent.IGameEvent;
import ru.vsu.savina.tablegame.game.impl.gamestate.IGameAction;
import ru.vsu.savina.tablegame.game.impl.player.PlayerState;
import ru.vsu.savina.tablegame.game.impl.player.TableGamePlayer;
import ru.vsu.savina.tablegame.game.impl.situation.SituationCards;

public class Game implements IGame<TableGamePlayer> {
    private final GameApplication application;

    private IGameAction currAction;

    private Field field;
    private List<TableGamePlayer> playerList;
    private Map<Item, Integer> itemMap;
    private SituationCards situationCards;
    private IDice dice;

    private Map<TableGamePlayer, Place> playerPlaceMap;
    private Map<TableGamePlayer, PlayerState> playerStateMap;
    private Map<Type, IPlaceAction> placeActionMap;

    private TableGamePlayer activePlayer;

    public Game(GameApplication application) {
        this.application = application;
    }

    public void turn() {
        currAction.execute(this);
    }

    public void updateView() {
        application.updateView();
    }

    public void notifyOnGameEvent(IGameEvent event) {
        application.notifyOnGameEvent(event);
    }

    public boolean checkEnded() {
        return playerStateMap.values().stream().allMatch(s -> s == PlayerState.FINISHED || s == PlayerState.GAME_OVER);
    }

    public void chooseItem(List<? extends Chooseable> optionList, IChoiceAction onChoiceMade) {
        application.chooseItem(optionList, onChoiceMade);
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;

        playerPlaceMap = new HashMap<>();

        for (TableGamePlayer player : playerList) {
            playerPlaceMap.put(player, field.getStartPlace());
        }
    }

    public List<TableGamePlayer> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<TableGamePlayer> playerList) {
        this.playerList = playerList;
        playerStateMap = new HashMap<>();

        for (TableGamePlayer player : playerList) {
            playerStateMap.put(player, PlayerState.IN_GAME);
        }

        activePlayer = playerList.get(0);
    }

    public Map<Item, Integer> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<Item, Integer> itemMap) {
        this.itemMap = itemMap;
    }

    public SituationCards getSituationCards() {
        return situationCards;
    }

    public void setSituationCards(SituationCards situationCards) {
        this.situationCards = situationCards;
    }

    public IDice getDice() {
        return dice;
    }

    public void setDice(IDice dice) {
        this.dice = dice;
    }

    public Map<TableGamePlayer, Place> getPlayerPlaceMap() {
        return playerPlaceMap;
    }

    public void setPlayerPlaceMap(Map<TableGamePlayer, Place> playerPlaceMap) {
        this.playerPlaceMap = playerPlaceMap;
    }

    public Map<TableGamePlayer, PlayerState> getPlayerStateMap() {
        return playerStateMap;
    }

    public void setPlayerStateMap(Map<TableGamePlayer, PlayerState> playerStateMap) {
        this.playerStateMap = playerStateMap;
    }

    public TableGamePlayer getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(TableGamePlayer activePlayer) {
        this.activePlayer = activePlayer;
    }

    public IGameAction getCurrAction() {
        return currAction;
    }

    public void setCurrAction(IGameAction currAction) {
        application.notifyOnGameAction(this.currAction);
        this.currAction = currAction;
    }

    public Map<Type, IPlaceAction> getPlaceActionMap() {
        return placeActionMap;
    }

    public void setPlaceActionMap(Map<Type, IPlaceAction> placeActionMap) {
        this.placeActionMap = placeActionMap;
    }
}
