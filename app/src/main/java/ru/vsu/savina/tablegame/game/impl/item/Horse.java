package ru.vsu.savina.tablegame.game.impl.item;

import ru.vsu.savina.tablegame.game.engine.item.Item;

public class Horse extends Item {
    public Horse(int cost) {
        super(cost);
    }

    @Override
    public String optionInfo() {
        return "Лошадь";
    }
}
