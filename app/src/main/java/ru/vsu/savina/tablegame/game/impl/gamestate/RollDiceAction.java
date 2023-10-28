package ru.vsu.savina.tablegame.game.impl.gamestate;

import ru.vsu.savina.tablegame.game.Game;

public class RollDiceAction implements IGameAction {
    @Override
    public void execute(Game game) {
        int diceNum = game.getDice().rollDice();

        game.setCurrAction(new MovePlayerAction(diceNum));
    }
}
