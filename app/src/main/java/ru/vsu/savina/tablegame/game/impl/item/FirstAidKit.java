package ru.vsu.savina.tablegame.game.impl.item;

import ru.vsu.savina.tablegame.game.engine.item.Item;

public class FirstAidKit extends Item {
    public FirstAidKit(int cost) {
        super(cost);
    }

    @Override
    public String optionInfo() {
        return "Аптечка";
    }
}
