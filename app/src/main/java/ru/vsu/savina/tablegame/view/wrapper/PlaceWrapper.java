package ru.vsu.savina.tablegame.view.wrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ru.vsu.savina.tablegame.game.engine.field.Place;
import ru.vsu.savina.tablegame.game.engine.field.TransitionType;

public class PlaceWrapper {
    private Place place;
    private double x;
    private double y;

    private Map<TransitionWrapper, TransitionType> transitionMap;

    public PlaceWrapper() {
        transitionMap = new HashMap<>();
    }

    public PlaceWrapper(double x, double y) {
        this.x = x;
        this.y = y;
        transitionMap = new HashMap<>();
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Map<TransitionWrapper, TransitionType> getTransitionMap() {
        return transitionMap;
    }

    public void setTransitionMap(Map<TransitionWrapper, TransitionType> transitionMap) {
        this.transitionMap = transitionMap;
    }

    public List<TransitionWrapper> getIncomingTransitions() {
        return transitionMap.keySet().stream().
                filter(k -> transitionMap.get(k) == TransitionType.INCOME).collect(Collectors.toList());
    }

    public List<TransitionWrapper> getOutgoingTransitions() {
        return transitionMap.keySet().stream().
                filter(k -> transitionMap.get(k) == TransitionType.OUTGO).collect(Collectors.toList());
    }

    public void addOutgoingTransition(TransitionWrapper transition) {
        transitionMap.put(transition, TransitionType.OUTGO);
    }

    public void addIncomingTransition(TransitionWrapper transition) {
        transitionMap.put(transition, TransitionType.INCOME);
    }
}
