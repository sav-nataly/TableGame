package ru.vsu.savina.tablegame.game.impl.situation;

import ru.vsu.savina.tablegame.game.Game;
import ru.vsu.savina.tablegame.game.engine.item.Item;
import ru.vsu.savina.tablegame.game.impl.action.Chooseable;
import ru.vsu.savina.tablegame.game.impl.action.IChoiceAction;
import ru.vsu.savina.tablegame.game.impl.gamestate.ChoosePlayerAction;
import ru.vsu.savina.tablegame.game.impl.player.TableGamePlayer;

public class ItemGiveSituation implements Situation, IChoiceAction {
    private Item item;
    private Game game;

    public ItemGiveSituation(Item item) {
        this.item = item;
    }

    @Override
    public void performAction(Game game) {
        this.game = game;
        if (game.getActivePlayer().hasItemOfType(item.getClass())) {
            game.chooseItem(game.getPlayerList(), this);
        }
    }

    @Override
    public void onChoiceMade(Chooseable option) {
        TableGamePlayer player = (TableGamePlayer) option;
        Item giveItem = game.getActivePlayer().getItemsOfType(item.getClass()).get(0);

        player.addItem(giveItem, 1);
        game.getActivePlayer().deleteItem(giveItem, 1);
        this.game.setCurrAction(new ChoosePlayerAction());
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
