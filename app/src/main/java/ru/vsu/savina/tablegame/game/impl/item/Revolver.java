package ru.vsu.savina.tablegame.game.impl.item;

import java.util.List;

public class Revolver extends Weapon{
    public Revolver(int cost, List<Integer> luckyNumbers, int shotNumber) {
        super(cost, luckyNumbers, shotNumber);
    }

    @Override
    public String optionInfo() {
        return "Револьвер";
    }
}
