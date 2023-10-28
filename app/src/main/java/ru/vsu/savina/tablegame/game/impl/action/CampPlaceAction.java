package ru.vsu.savina.tablegame.game.impl.action;

import java.util.List;
import java.util.stream.Collectors;

import ru.vsu.savina.tablegame.game.Game;
import ru.vsu.savina.tablegame.game.engine.IGame;
import ru.vsu.savina.tablegame.game.engine.action.IPlaceAction;
import ru.vsu.savina.tablegame.game.engine.item.Item;
import ru.vsu.savina.tablegame.game.impl.field.CampPlace;
import ru.vsu.savina.tablegame.game.impl.gamestate.ChoosePlayerAction;

public class CampPlaceAction implements IPlaceAction, IChoiceAction {
    private Game game;

    @Override
    public void execute(IGame game) {
        this.game = (Game) game;

        CampPlace place = (CampPlace) this.game.getPlayerPlaceMap().get(this.game.getActivePlayer());

        if (!place.isVisited()) {
            List<Item> options = this.game.getItemMap().keySet().stream().filter(item -> this.game.getItemMap().get(item) > 0).collect(Collectors.toList());

            if (options.size() > 0) {
                this.game.chooseItem(options, this);
            }
        }
    }

    @Override
    public void onChoiceMade(Chooseable option) {
        Item item = (Item) option;
        game.getActivePlayer().addItem(item, 1);
        CampPlace place = (CampPlace) this.game.getPlayerPlaceMap().get(this.game.getActivePlayer());
        place.setVisited(true);

        game.setCurrAction(new ChoosePlayerAction());
    }
}
