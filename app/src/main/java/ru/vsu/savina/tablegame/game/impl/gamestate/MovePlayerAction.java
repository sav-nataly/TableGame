package ru.vsu.savina.tablegame.game.impl.gamestate;

import ru.vsu.savina.tablegame.game.Game;
import ru.vsu.savina.tablegame.game.engine.field.Place;

public class MovePlayerAction implements IGameAction {
    private int diceNum;

    public MovePlayerAction(int diceNum) {
        this.diceNum = diceNum;
    }

    @Override
    public void execute(Game game) {

        Place place = game.getPlayerPlaceMap().get(game.getActivePlayer());

        //todo path choose event
        int counter = 0;
        while (counter < diceNum) {
            if (!place.getOutgoingTransitions().get(0).getTarget().isMustStop()) {
                place = place.getOutgoingTransitions().get(0).getTarget();
                counter++;
            } else {
                place = place.getOutgoingTransitions().get(0).getTarget();
                break;
            }
        }

        game.getPlayerPlaceMap().replace(game.getActivePlayer(), place);
        game.updateView();
        game.setCurrAction(new ExecutePlaceAction());
    }

    public int getDiceNum() {
        return diceNum;
    }
}
