package ru.vsu.savina.tablegame.game.impl.gameevent;

public class RollDiceEvent implements IGameEvent{
    private final int diceNum;

    public RollDiceEvent(int diceNum) {
        this.diceNum = diceNum;
    }

    public int getDiceNum() {
        return diceNum;
    }
}
