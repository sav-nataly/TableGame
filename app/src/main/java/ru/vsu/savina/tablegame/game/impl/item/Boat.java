package ru.vsu.savina.tablegame.game.impl.item;
import java.util.List;

import ru.vsu.savina.tablegame.game.engine.item.Item;

public class Boat extends Item{
    private int capacity;
    private List<Integer> luckyNumbers;
    public Boat(int cost, int capacity, List<Integer> luckyNumbers) {
        super(cost);
        this.capacity = capacity;
        this.luckyNumbers = luckyNumbers;
    }
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Integer> getLuckyNumbers() {
        return luckyNumbers;
    }

    public void setLuckyNumbers(List<Integer> luckyNumbers) {
        this.luckyNumbers = luckyNumbers;
    }

    @Override
    public String optionInfo() {
        return "Лодка";
    }
}
