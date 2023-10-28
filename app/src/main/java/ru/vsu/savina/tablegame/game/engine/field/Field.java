package ru.vsu.savina.tablegame.game.engine.field;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private Place startPlace;

    private List<Place> placeList;
    private List<Transition> transitionList;

    public Field() {
        placeList = new ArrayList<>();
        transitionList = new ArrayList<>();
    }

    public void addPlace(Place place) {
        if (!placeList.contains(place)) {
            placeList.add(place);
        }
    }

    public void addTransition(Transition transition) {
        if (!transitionList.contains(transition)) {
            transitionList.add(transition);

            transition.getSource().addOutgoingTransition(transition);
            transition.getTarget().addIncomingTransition(transition);
        }
    }

    public List<Place> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(List<Place> placeList) {
        this.placeList = placeList;
    }

    public List<Transition> getTransitionList() {
        return transitionList;
    }

    public void setTransitionList(List<Transition> transitionList) {
        this.transitionList = transitionList;
    }

    public Place getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(Place startPlace) {
        this.startPlace = startPlace;
    }
}
