package ru.vsu.savina.tablegame.game.impl.field;

import java.util.Map;

import ru.vsu.savina.tablegame.game.engine.field.Place;
import ru.vsu.savina.tablegame.game.engine.field.Transition;
import ru.vsu.savina.tablegame.game.engine.field.TransitionType;

public class RedEnemyPlace extends Place {
    public RedEnemyPlace() {
        setMustStop(true);
    }

    public RedEnemyPlace(Map<Transition, TransitionType> transitionMap) {
        super(transitionMap);
    }
}
