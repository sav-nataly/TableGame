package ru.vsu.savina.tablegame.game.impl.item;

import java.util.List;

public class Rifle extends Weapon{
    public Rifle(int cost, List<Integer> luckyNumbers, int shotNumber) {
        super(cost, luckyNumbers, shotNumber);
    }

    @Override
    public String optionInfo() {
        return "Ружье";
    }
}
