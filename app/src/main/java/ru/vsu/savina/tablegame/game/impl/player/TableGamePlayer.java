package ru.vsu.savina.tablegame.game.impl.player;

import ru.vsu.savina.tablegame.game.engine.player.Player;
import ru.vsu.savina.tablegame.game.impl.action.Chooseable;

public class TableGamePlayer extends Player implements Chooseable {
    public TableGamePlayer(String name) {
        super(name);
    }

    @Override
    public String optionInfo() {
        return getName();
    }
}
