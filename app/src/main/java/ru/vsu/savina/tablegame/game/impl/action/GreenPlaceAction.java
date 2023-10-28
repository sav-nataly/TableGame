package ru.vsu.savina.tablegame.game.impl.action;

import ru.vsu.savina.tablegame.game.Game;
import ru.vsu.savina.tablegame.game.engine.IGame;
import ru.vsu.savina.tablegame.game.engine.action.IPlaceAction;
import ru.vsu.savina.tablegame.game.impl.gameevent.ItemLossEvent;
import ru.vsu.savina.tablegame.game.impl.gameevent.PlayerStateChangeEvent;
import ru.vsu.savina.tablegame.game.impl.gameevent.RollDiceEvent;
import ru.vsu.savina.tablegame.game.impl.gamestate.ChoosePlayerAction;
import ru.vsu.savina.tablegame.game.impl.item.FirstAidKit;
import ru.vsu.savina.tablegame.game.impl.item.Horse;
import ru.vsu.savina.tablegame.game.impl.item.Weapon;
import ru.vsu.savina.tablegame.game.impl.player.PlayerState;

public class GreenPlaceAction implements IPlaceAction {
    @Override
    public void execute(IGame game) {
        Game g = (Game) game;

        int diceNum = game.getDice().rollDice();
        g.notifyOnGameEvent(new RollDiceEvent(diceNum));

        if (diceNum < 4) {
            if (!game.getActivePlayer().hasItemOfType(FirstAidKit.class)) {
                g.getPlayerStateMap().replace(g.getActivePlayer(), PlayerState.PASS_TURN);
                g.notifyOnGameEvent(new PlayerStateChangeEvent(PlayerState.PASS_TURN));
            }
        } else {
            if (game.getActivePlayer().hasItemOfType(Horse.class)) {
                game.getActivePlayer().deleteItemsOfType(Horse.class);
                g.notifyOnGameEvent(new ItemLossEvent(new Horse(0)));
            }
            else {
                game.getActivePlayer().deleteAllItemsExceptType(Weapon.class);
            }
        }
        g.setCurrAction(new ChoosePlayerAction());
    }
}