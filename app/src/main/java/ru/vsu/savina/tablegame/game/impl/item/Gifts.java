package ru.vsu.savina.tablegame.game.impl.item;

import ru.vsu.savina.tablegame.game.engine.item.Item;

public class Gifts extends Item {
    public Gifts(int cost) {
        super(cost);
    }

    @Override
    public String optionInfo() {
        return "Подарки";
    }
}
