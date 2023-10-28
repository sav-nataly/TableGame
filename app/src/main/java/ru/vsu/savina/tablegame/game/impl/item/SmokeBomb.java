package ru.vsu.savina.tablegame.game.impl.item;

import ru.vsu.savina.tablegame.game.engine.item.Item;

public class SmokeBomb extends Item {
    public SmokeBomb(int cost) {
        super(cost);
    }

    @Override
    public String optionInfo() {
        return "Дымовая шашка";
    }
}
