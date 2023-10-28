package ru.vsu.savina.tablegame.game.impl.gamestate;

import ru.vsu.savina.tablegame.game.Game;
import ru.vsu.savina.tablegame.game.impl.player.PlayerState;
import ru.vsu.savina.tablegame.game.impl.player.TableGamePlayer;

public class ChoosePlayerAction implements IGameAction {
    @Override
    public void execute(Game game) {
        TableGamePlayer player;
        if (game.getActivePlayer() == null)
            player = game.getPlayerList().get(0);
        else {
            int next = (game.getPlayerList().indexOf(game.getActivePlayer()) + 1) % game.getPlayerList().size();
            player = game.getPlayerList().get(next);

            while (game.getPlayerStateMap().get(player) != PlayerState.IN_GAME) {
                if (game.getPlayerStateMap().get(player) == PlayerState.PASS_TURN)
                    game.getPlayerStateMap().replace(player, PlayerState.IN_GAME);

                next = (next + 1) % game.getPlayerList().size();
                player = game.getPlayerList().get(next);
            }
        }
        game.setActivePlayer(player);
        game.setCurrAction(new RollDiceAction());
    }
}
