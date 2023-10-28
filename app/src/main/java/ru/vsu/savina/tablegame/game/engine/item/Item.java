package ru.vsu.savina.tablegame.game.engine.item;

import ru.vsu.savina.tablegame.game.impl.action.Chooseable;

public abstract class Item implements Chooseable {
    private int cost;

    public Item(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
