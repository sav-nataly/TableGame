package ru.vsu.savina.tablegame.game.impl.field;

import java.util.Map;

import ru.vsu.savina.tablegame.game.engine.field.Place;
import ru.vsu.savina.tablegame.game.engine.field.Transition;
import ru.vsu.savina.tablegame.game.engine.field.TransitionType;

public class GreyPlace extends Place {
    public GreyPlace() {
    }

    public GreyPlace(Map<Transition, TransitionType> transitionMap) {
        super(transitionMap);
    }
}
