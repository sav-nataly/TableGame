package ru.vsu.savina.tablegame.game.impl.item;

import ru.vsu.savina.tablegame.game.engine.item.Item;

public class Camera extends Item {
    private int shotCost;
    public Camera(int cost, int shotCost) {
        super(cost);
    }

    public int getShotCost() {
        return shotCost;
    }

    public void setShotCost(int shotCost) {
        this.shotCost = shotCost;
    }

    @Override
    public String optionInfo() {
        return "Камера";
    }
}
