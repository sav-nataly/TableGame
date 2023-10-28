package ru.vsu.savina.tablegame.game.impl.situation;

import ru.vsu.savina.tablegame.game.Game;
import ru.vsu.savina.tablegame.game.engine.item.Item;
import ru.vsu.savina.tablegame.game.impl.action.Chooseable;
import ru.vsu.savina.tablegame.game.impl.action.IChoiceAction;
import ru.vsu.savina.tablegame.game.impl.gamestate.ChoosePlayerAction;
import ru.vsu.savina.tablegame.game.impl.player.TableGamePlayer;

public class ItemTakeSituation implements Situation, IChoiceAction {
    private Item item;
    private Game game;

    public ItemTakeSituation(Item item) {
        this.item = item;
    }

    @Override
    public void performAction(Game game) {
        this.game = game;
        game.chooseItem(game.getPlayerList(), this);

    }

    @Override
    public void onChoiceMade(Chooseable option) {
        TableGamePlayer player = (TableGamePlayer) option;
        if (player.hasItemOfType(item.getClass())) {
            Item takeItem = player.getItemsOfType(item.getClass()).get(0);
            player.deleteItem(takeItem, 1);
            game.getActivePlayer().addItem(takeItem, 1);
        }
        this.game.setCurrAction(new ChoosePlayerAction());
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
