package ru.vsu.savina.tablegame.game.impl.dice;

import java.util.concurrent.ThreadLocalRandom;

import ru.vsu.savina.tablegame.game.engine.dice.IDice;

public class Dice implements IDice {
    @Override
    public int rollDice() {
        return ThreadLocalRandom.current().nextInt(1, 7);
    }
}
