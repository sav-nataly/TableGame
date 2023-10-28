package ru.vsu.savina.tablegame.game.impl.action;

import ru.vsu.savina.tablegame.game.Game;
import ru.vsu.savina.tablegame.game.engine.IGame;
import ru.vsu.savina.tablegame.game.engine.action.IPlaceAction;
import ru.vsu.savina.tablegame.game.impl.gamestate.ChoosePlayerAction;

public class GreyPlaceAction implements IPlaceAction {
    @Override
    public void execute(IGame game) {
        Game g = (Game) game;
        g.setCurrAction(new ChoosePlayerAction());
    }
}
