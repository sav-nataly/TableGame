package ru.vsu.savina.tablegame.game.impl.item;

import java.util.List;

import ru.vsu.savina.tablegame.game.engine.item.Item;

public class Weapon extends Item {
    private List<Integer> luckyNumbers;
    private int shotNumber;

    public Weapon(int cost, List<Integer> luckyNumbers, int shotNumber) {
        super(cost);
        this.luckyNumbers = luckyNumbers;
        this.shotNumber = shotNumber;
    }

    public List<Integer> getLuckyNumbers() {
        return luckyNumbers;
    }

    public void setLuckyNumbers(List<Integer> luckyNumbers) {
        this.luckyNumbers = luckyNumbers;
    }

    public int getShotNumber() {
        return shotNumber;
    }

    public void setShotNumber(int shotNumber) {
        this.shotNumber = shotNumber;
    }

    @Override
    public String optionInfo() {
        return "Weapon";
    }
}
