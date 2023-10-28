package ru.vsu.savina.tablegame.view.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.polygon.Polygon;
import ru.vsu.savina.tablegame.game.engine.field.Field;
import ru.vsu.savina.tablegame.game.engine.field.Place;

public class FieldWrapper {
    private List<PlaceWrapper> placeList;
    private List<TransitionWrapper> transitionList;
    private List<Polygon> polygons;

    private double width;
    private double height;

    private Field field;

    public FieldWrapper() {
        placeList = new ArrayList<>();
        transitionList = new ArrayList<>();
        polygons = new ArrayList<>();
        field = new Field();
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public boolean containsPlace(PlaceWrapper place) {
        return placeList.contains(place);
    }

    public void addPlace(PlaceWrapper place) {
        if (!placeList.contains(place)) {
            placeList.add(place);
        }
    }

    public void addTransition(TransitionWrapper transition) {
        if (!transitionList.contains(transition)) {
            transitionList.add(transition);

            transition.getSource().addOutgoingTransition(transition);
            transition.getTarget().addIncomingTransition(transition);
        }
    }

    public PlaceWrapper getPlaceByIndex(int index) {
        return placeList.get(index);
    }

    public PlaceWrapper getWrapperByPlace(Place place) {
        return placeList.stream().filter(w -> w.getPlace().equals(place)).collect(Collectors.toList()).get(0);
    }

    public List<PlaceWrapper> getPlaceList() {
        return placeList;
    }

    public List<TransitionWrapper> getTransitionList() {
        return transitionList;
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public void setPolygons(List<Polygon> polygons) {
        this.polygons = polygons;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
