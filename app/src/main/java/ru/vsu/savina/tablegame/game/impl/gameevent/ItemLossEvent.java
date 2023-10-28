package ru.vsu.savina.tablegame.game.impl.gameevent;

import ru.vsu.savina.tablegame.game.engine.item.Item;

public class ItemLossEvent implements IGameEvent{
    private final Item item;

    public ItemLossEvent(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
