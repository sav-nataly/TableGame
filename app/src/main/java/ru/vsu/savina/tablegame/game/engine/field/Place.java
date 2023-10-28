package ru.vsu.savina.tablegame.game.engine.field;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Place {
    private Map<Transition, TransitionType> transitionMap;

    private boolean mustStop;

    public Place() {
        transitionMap = new HashMap<>();
        mustStop = false;
    }


    public Place(Map<Transition, TransitionType> transitionMap) {
        this.transitionMap = transitionMap;
    }

    public List<Transition> getIncomingTransitions() {
        return transitionMap.keySet().stream().
                filter(k -> transitionMap.get(k) == TransitionType.INCOME).collect(Collectors.toList());
    }

    public List<Transition> getOutgoingTransitions() {
        return transitionMap.keySet().stream().
                filter(k -> transitionMap.get(k) == TransitionType.OUTGO).collect(Collectors.toList());
    }

    public void addOutgoingTransition(Transition transition) {
        transitionMap.put(transition, TransitionType.OUTGO);
    }

    public void addIncomingTransition(Transition transition) {
        transitionMap.put(transition, TransitionType.INCOME);
    }

    public Map<Transition, TransitionType> getTransitionMap() {
        return transitionMap;
    }

    public void setTransitionMap(Map<Transition, TransitionType> transitionMap) {
        this.transitionMap = transitionMap;
    }

    public boolean isMustStop() {
        return mustStop;
    }

    public void setMustStop(boolean mustStop) {
        this.mustStop = mustStop;
    }
}
