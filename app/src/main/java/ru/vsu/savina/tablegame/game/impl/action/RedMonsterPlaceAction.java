package ru.vsu.savina.tablegame.game.impl.action;

import java.util.ArrayList;
import java.util.List;

import ru.vsu.savina.tablegame.game.Game;
import ru.vsu.savina.tablegame.game.engine.IGame;
import ru.vsu.savina.tablegame.game.engine.action.IPlaceAction;
import ru.vsu.savina.tablegame.game.engine.item.Item;
import ru.vsu.savina.tablegame.game.impl.field.RedMonsterPlace;
import ru.vsu.savina.tablegame.game.impl.field.monster.Monster;
import ru.vsu.savina.tablegame.game.impl.gameevent.PlayerStateChangeEvent;
import ru.vsu.savina.tablegame.game.impl.gameevent.RollDiceEvent;
import ru.vsu.savina.tablegame.game.impl.gamestate.ChoosePlayerAction;
import ru.vsu.savina.tablegame.game.impl.item.Camera;
import ru.vsu.savina.tablegame.game.impl.item.MonsterSkin;
import ru.vsu.savina.tablegame.game.impl.item.SmokeBomb;
import ru.vsu.savina.tablegame.game.impl.item.Weapon;
import ru.vsu.savina.tablegame.game.impl.player.PlayerState;

public class RedMonsterPlaceAction implements IPlaceAction, IChoiceAction {
    private Game game;

    @Override
    public void execute(IGame game) {
        this.game = (Game) game;

        int diceNum = this.game.getDice().rollDice();
        this.game.notifyOnGameEvent(new RollDiceEvent(diceNum));

        if (diceNum < 4) {
            takePhotos();
        } else {
            fight();
        }
    }

    private void takePhotos() {
        if (game.getActivePlayer().hasItemOfType(Camera.class)) {
            RedMonsterPlace place = (RedMonsterPlace) game.getPlayerPlaceMap().get(game.getActivePlayer());

            Camera camera = (Camera) game.getActivePlayer().getItemsOfType(Camera.class).get(0);
            game.getActivePlayer().setMoney(game.getActivePlayer().getMoney()
                    + camera.getShotCost() * place.getMonster().getRarityCoefficient());
        }
    }

    private void fight() {
        List<Item> options = new ArrayList<>();

        options.addAll(game.getActivePlayer().getItemsOfType(Weapon.class));
        options.addAll(game.getActivePlayer().getItemsOfType(SmokeBomb.class));

        if (options.size() > 0) {
            game.chooseItem(options, this);
        } else {
            game.getPlayerStateMap().replace(game.getActivePlayer(), PlayerState.GAME_OVER);
            game.notifyOnGameEvent(new PlayerStateChangeEvent(PlayerState.GAME_OVER));
            game.setCurrAction(new ChoosePlayerAction());
        }
    }


    @Override
    public void onChoiceMade(Chooseable option) {
        Item item = (Item) option;

        if (item.getClass().equals(SmokeBomb.class))
            game.getActivePlayer().deleteItem(item, 1);
        else {
            RedMonsterPlace place = (RedMonsterPlace) game.getPlayerPlaceMap().get(game.getActivePlayer());

            Monster monster = place.getMonster();
            Weapon weapon = (Weapon) item;

            shoot(weapon, monster);
            monsterAttack(monster);
        }
        game.setCurrAction(new ChoosePlayerAction());
    }

    private void shoot(Weapon weapon, Monster monster) {
        int shotsCounter = 0;

        for (int i = 0; i < weapon.getShotNumber(); i++) {
            int diceNum = game.getDice().rollDice();
            this.game.notifyOnGameEvent(new RollDiceEvent(diceNum));

            if (weapon.getLuckyNumbers().contains(diceNum))
                shotsCounter++;

            if (shotsCounter == monster.getShotNumber()) {
                monster.setDead(true);
                game.getActivePlayer().addItem(new MonsterSkin(monster.getSkinCost()), 1);
                return;
            }
        }
    }

    private void monsterAttack(Monster monster) {
        for (int i = 0; i < monster.getAttackNumber(); i++) {
            int diceNum = game.getDice().rollDice();
            this.game.notifyOnGameEvent(new RollDiceEvent(diceNum));

            if (diceNum > 3) {
                game.getPlayerStateMap().replace(game.getActivePlayer(), PlayerState.GAME_OVER);
                game.notifyOnGameEvent(new PlayerStateChangeEvent(PlayerState.GAME_OVER));
                return;
            }
        }
    }

}
