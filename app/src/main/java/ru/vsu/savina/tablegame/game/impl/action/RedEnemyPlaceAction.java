package ru.vsu.savina.tablegame.game.impl.action;

import java.util.List;

import ru.vsu.savina.tablegame.game.Game;
import ru.vsu.savina.tablegame.game.engine.IGame;
import ru.vsu.savina.tablegame.game.engine.action.IPlaceAction;
import ru.vsu.savina.tablegame.game.engine.item.Item;
import ru.vsu.savina.tablegame.game.impl.gameevent.PlayerStateChangeEvent;
import ru.vsu.savina.tablegame.game.impl.gameevent.RollDiceEvent;
import ru.vsu.savina.tablegame.game.impl.gamestate.ChoosePlayerAction;
import ru.vsu.savina.tablegame.game.impl.item.Weapon;
import ru.vsu.savina.tablegame.game.impl.player.PlayerState;

public class RedEnemyPlaceAction implements IPlaceAction, IChoiceAction {
    private Game g;
    @Override
    public void execute(IGame game) {
        g = (Game) game;

        if (g.getActivePlayer().hasItemOfType(Weapon.class)) {
            List<Item> weapons = g.getActivePlayer().getItemsOfType(Weapon.class);
            g.chooseItem(weapons, this);
        }
        else {
            g.getPlayerStateMap().replace(g.getActivePlayer(), PlayerState.GAME_OVER);
            g.notifyOnGameEvent(new PlayerStateChangeEvent(PlayerState.GAME_OVER));
            g.setCurrAction(new ChoosePlayerAction());
        }

    }

    @Override
    public void onChoiceMade(Chooseable option) {
        Weapon weapon = (Weapon) option;
        int diceNum = g.getDice().rollDice();
        g.notifyOnGameEvent(new RollDiceEvent(diceNum));

        if (!weapon.getLuckyNumbers().contains(diceNum)) {
            g.getPlayerStateMap().replace(g.getActivePlayer(), PlayerState.GAME_OVER);
            g.notifyOnGameEvent(new PlayerStateChangeEvent(PlayerState.GAME_OVER));
        }
        g.setCurrAction(new ChoosePlayerAction());
    }
}
