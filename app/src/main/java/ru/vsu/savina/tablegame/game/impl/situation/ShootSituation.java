package ru.vsu.savina.tablegame.game.impl.situation;

import java.util.List;

import ru.vsu.savina.tablegame.game.Game;
import ru.vsu.savina.tablegame.game.engine.item.Item;
import ru.vsu.savina.tablegame.game.impl.action.Chooseable;
import ru.vsu.savina.tablegame.game.impl.action.IChoiceAction;
import ru.vsu.savina.tablegame.game.impl.gameevent.PlayerStateChangeEvent;
import ru.vsu.savina.tablegame.game.impl.gameevent.RollDiceEvent;
import ru.vsu.savina.tablegame.game.impl.gamestate.ChoosePlayerAction;
import ru.vsu.savina.tablegame.game.impl.item.SmokeBomb;
import ru.vsu.savina.tablegame.game.impl.item.Weapon;
import ru.vsu.savina.tablegame.game.impl.player.PlayerState;

public class ShootSituation implements Situation, IChoiceAction {
    private int shootNumber;
    private boolean canUseSmokeBomb;
    private Game game;

    public ShootSituation(int shootNumber, boolean canUseSmokeBomb) {
        this.shootNumber = shootNumber;
        this.canUseSmokeBomb = canUseSmokeBomb;
    }

    @Override
    public void performAction(Game game) {
        this.game = game;
        if (game.getActivePlayer().hasItemOfType(Weapon.class)) {
            List<Item> itemList = game.getActivePlayer().getItemsOfType(Weapon.class);

            if (canUseSmokeBomb && game.getActivePlayer().hasItemOfType(SmokeBomb.class)) {
                itemList.addAll(game.getActivePlayer().getItemsOfType(SmokeBomb.class));

                game.chooseItem(itemList, this);
            } else {
                if (itemList.size() > 1) {
                    game.chooseItem(itemList, this);
                } else {
                    shoot((Weapon) itemList.get(0));
                }
            }
        } else if (canUseSmokeBomb && game.getActivePlayer().hasItemOfType(SmokeBomb.class)) {
            game.getActivePlayer().deleteItem(game.getActivePlayer().getItemsOfType(SmokeBomb.class).get(0), 1);
            game.setCurrAction(new ChoosePlayerAction());
        } else {
            game.getPlayerStateMap().replace(game.getActivePlayer(), PlayerState.GAME_OVER);
            game.notifyOnGameEvent(new PlayerStateChangeEvent(PlayerState.GAME_OVER));
            game.setCurrAction(new ChoosePlayerAction());
        }

    }

    @Override
    public void onChoiceMade(Chooseable option) {
        if (option instanceof Weapon) {
            shoot((Weapon) option);
        } else {
            game.getActivePlayer().deleteItem(game.getActivePlayer().getItemsOfType(SmokeBomb.class).get(0), 1);
        }
        game.setCurrAction(new ChoosePlayerAction());
    }

    private void shoot(Weapon weapon) {
        for (int i = 0; i < shootNumber; i++) {
            int diceNum = game.getDice().rollDice();

            if (!weapon.getLuckyNumbers().contains(diceNum)) {
                game.getPlayerStateMap().replace(game.getActivePlayer(), PlayerState.GAME_OVER);
                game.notifyOnGameEvent(new PlayerStateChangeEvent(PlayerState.GAME_OVER));
                game.setCurrAction(new ChoosePlayerAction());
                return;
            }
        }

        game.setCurrAction(new ChoosePlayerAction());
    }

    public int getShootNumber() {
        return shootNumber;
    }

    public void setShootNumber(int shootNumber) {
        this.shootNumber = shootNumber;
    }

    public boolean isCanUseSmokeBomb() {
        return canUseSmokeBomb;
    }

    public void setCanUseSmokeBomb(boolean canUseSmokeBomb) {
        this.canUseSmokeBomb = canUseSmokeBomb;
    }
}
