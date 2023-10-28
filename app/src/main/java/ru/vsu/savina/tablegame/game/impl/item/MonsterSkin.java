package ru.vsu.savina.tablegame.game.impl.item;

import ru.vsu.savina.tablegame.game.engine.item.Item;

public class MonsterSkin extends Item {
    public MonsterSkin(int cost) {
        super(cost);
    }

    @Override
    public String optionInfo() {
        return "Шкура";
    }
}
