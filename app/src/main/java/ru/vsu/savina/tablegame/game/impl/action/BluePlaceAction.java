package ru.vsu.savina.tablegame.game.impl.action;

import java.util.ArrayList;
import java.util.List;

import ru.vsu.savina.tablegame.game.Game;
import ru.vsu.savina.tablegame.game.engine.IGame;
import ru.vsu.savina.tablegame.game.engine.action.IPlaceAction;
import ru.vsu.savina.tablegame.game.engine.item.Item;
import ru.vsu.savina.tablegame.game.impl.gameevent.ItemLossEvent;
import ru.vsu.savina.tablegame.game.impl.gameevent.PlayerStateChangeEvent;
import ru.vsu.savina.tablegame.game.impl.gameevent.RollDiceEvent;
import ru.vsu.savina.tablegame.game.impl.gamestate.ChoosePlayerAction;
import ru.vsu.savina.tablegame.game.impl.item.Boat;
import ru.vsu.savina.tablegame.game.impl.player.PlayerState;

public class BluePlaceAction implements IPlaceAction, IChoiceAction {
    private Game game;

    @Override
    public void execute(IGame game) {
        this.game = (Game) game;

        List<Item> options = new ArrayList<>(game.getActivePlayer().getItemsOfType(Boat.class));

        if (options.size() > 0) {
            this.game.chooseItem(options, this);
        } else {
            int diceNum = this.game.getDice().rollDice();
            this.game.notifyOnGameEvent(new RollDiceEvent(diceNum));

            if (diceNum > 2) {
                this.game.getPlayerStateMap().replace(this.game.getActivePlayer(), PlayerState.GAME_OVER);
                this.game.notifyOnGameEvent(new PlayerStateChangeEvent(PlayerState.GAME_OVER));
            }
            else {
            }
            this.game.setCurrAction(new ChoosePlayerAction());
        }

    }

    @Override
    public void onChoiceMade(Chooseable option) {
        int diceNum = game.getDice().rollDice();
        this.game.notifyOnGameEvent(new RollDiceEvent(diceNum));

        Boat b = (Boat) option;

        if (!b.getLuckyNumbers().contains(diceNum)) {
            game.getActivePlayer().deleteItem(b, 1);
            this.game.notifyOnGameEvent(new ItemLossEvent(b));
        }
        else {
            this.game.notifyOnGameEvent(new RollDiceEvent(diceNum));
        }
        game.setCurrAction(new ChoosePlayerAction());
    }
}
