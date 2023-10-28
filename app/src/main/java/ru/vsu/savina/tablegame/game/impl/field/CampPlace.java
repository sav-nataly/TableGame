package ru.vsu.savina.tablegame.game.impl.field;

import java.util.Map;

import ru.vsu.savina.tablegame.game.engine.field.Place;
import ru.vsu.savina.tablegame.game.engine.field.Transition;
import ru.vsu.savina.tablegame.game.engine.field.TransitionType;

public class CampPlace extends Place {
    private boolean isVisited;

    public CampPlace() {
        isVisited = false;
    }

    public CampPlace(Map<Transition, TransitionType> transitionMap) {
        super(transitionMap);
        isVisited = false;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }
}
