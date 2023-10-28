package ru.vsu.savina.tablegame.game.impl.gameevent;

import ru.vsu.savina.tablegame.game.impl.player.PlayerState;

public class PlayerStateChangeEvent implements IGameEvent{
    private final PlayerState state;

    public PlayerStateChangeEvent(PlayerState state) {
        this.state = state;
    }

    public PlayerState getState() {
        return state;
    }
}
