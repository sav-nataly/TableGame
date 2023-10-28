package ru.vsu.savina.tablegame.game.engine;

import java.util.List;
import java.util.Map;

import ru.vsu.savina.tablegame.game.engine.dice.IDice;
import ru.vsu.savina.tablegame.game.engine.field.Field;
import ru.vsu.savina.tablegame.game.engine.field.Place;
import ru.vsu.savina.tablegame.game.engine.item.Item;
import ru.vsu.savina.tablegame.game.engine.player.Player;
import ru.vsu.savina.tablegame.game.impl.player.PlayerState;

public interface IGame<T extends Player> {
    void turn();
    boolean checkEnded();

    Field getField();

    void setField(Field field);

    List<T> getPlayerList();

    void setPlayerList(List<T> playerList);
    Map<Item, Integer> getItemMap();


    void setItemMap(Map<Item, Integer> itemMap);

    IDice getDice();

    void setDice(IDice dice);
    Map<T, Place> getPlayerPlaceMap();

    void setPlayerPlaceMap(Map<T, Place> playerPlaceMap);

    Map<T, PlayerState> getPlayerStateMap();

    void setPlayerStateMap(Map<T, PlayerState> playerStateMap);

    T getActivePlayer();
    void setActivePlayer(T activePlayer);

}
