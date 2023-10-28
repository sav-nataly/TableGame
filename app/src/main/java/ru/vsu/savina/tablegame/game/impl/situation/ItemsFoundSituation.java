package ru.vsu.savina.tablegame.game.impl.situation;

import java.util.ArrayList;
import java.util.List;

import ru.vsu.savina.tablegame.game.Game;
import ru.vsu.savina.tablegame.game.engine.item.Item;
import ru.vsu.savina.tablegame.game.impl.gamestate.ChoosePlayerAction;

public class ItemsFoundSituation implements Situation {
    private List<Item> itemList;

    public ItemsFoundSituation(List<Item> itemList) {
        this.itemList = itemList;
    }

    public ItemsFoundSituation() {
        itemList = new ArrayList<>();
    }

    @Override
    public void performAction(Game game) {
        for (Item item : itemList) {
            game.getActivePlayer().addItem(item, 1);
        }
        game.setCurrAction(new ChoosePlayerAction());

    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
