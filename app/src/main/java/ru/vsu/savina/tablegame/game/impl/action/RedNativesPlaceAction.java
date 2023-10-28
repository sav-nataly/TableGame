package ru.vsu.savina.tablegame.game.impl.action;

import java.util.ArrayList;
import java.util.List;

import ru.vsu.savina.tablegame.game.Game;
import ru.vsu.savina.tablegame.game.engine.IGame;
import ru.vsu.savina.tablegame.game.engine.action.IPlaceAction;
import ru.vsu.savina.tablegame.game.engine.item.Item;
import ru.vsu.savina.tablegame.game.impl.gameevent.PlayerStateChangeEvent;
import ru.vsu.savina.tablegame.game.impl.gameevent.RollDiceEvent;
import ru.vsu.savina.tablegame.game.impl.gamestate.ChoosePlayerAction;
import ru.vsu.savina.tablegame.game.impl.item.Gifts;
import ru.vsu.savina.tablegame.game.impl.item.SmokeBomb;
import ru.vsu.savina.tablegame.game.impl.player.PlayerState;

public class RedNativesPlaceAction implements IPlaceAction, IChoiceAction {
    Game g;

    @Override
    public void execute(IGame game) {
        g = (Game) game;

        List<Item> options = new ArrayList<>();

        options.addAll(game.getActivePlayer().getItemsOfType(Gifts.class));
        options.addAll(game.getActivePlayer().getItemsOfType(SmokeBomb.class));

        if (options.size() > 0) {
            g.chooseItem(options, this);
        } else {
            g.getPlayerStateMap().replace(g.getActivePlayer(), PlayerState.GAME_OVER);
            g.notifyOnGameEvent(new PlayerStateChangeEvent(PlayerState.GAME_OVER));
            g.setCurrAction(new ChoosePlayerAction());
        }
    }

    @Override
    public void onChoiceMade(Chooseable option) {
        g.getActivePlayer().deleteItem((Item) option, 1);
        g.setCurrAction(new ChoosePlayerAction());
    }
}
