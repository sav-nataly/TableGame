package ru.vsu.savina.tablegame.game.impl.item;

import ru.vsu.savina.tablegame.game.engine.item.Item;

public class Helmet extends Item {
    public Helmet(int cost) {
        super(cost);
    }

    @Override
    public String optionInfo() {
        return "Шлем";
    }
}
