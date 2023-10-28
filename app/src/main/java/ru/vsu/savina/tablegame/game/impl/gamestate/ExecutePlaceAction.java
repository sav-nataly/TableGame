package ru.vsu.savina.tablegame.game.impl.gamestate;

import ru.vsu.savina.tablegame.game.Game;
import ru.vsu.savina.tablegame.game.engine.action.IPlaceAction;
import ru.vsu.savina.tablegame.game.engine.field.Place;

public class ExecutePlaceAction implements IGameAction {
    @Override
    public void execute(Game game) {
        Place place = game.getPlayerPlaceMap().get(game.getActivePlayer());
        IPlaceAction action = game.getPlaceActionMap().get(place.getClass());

        action.execute(game);
    }
}
