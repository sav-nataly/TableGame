package ru.vsu.savina.tablegame.game.engine.field;

import java.util.HashMap;
import java.util.Map;

public class Transition {
    private Map<Place, PlaceType> placeMap;

    public Transition(Map<Place, PlaceType> placeMap) {
        this.placeMap = placeMap;
    }

    public Transition(Place source, Place target) {
        placeMap = new HashMap<>();
        placeMap.put(source, PlaceType.SOURCE);
        placeMap.put(target, PlaceType.TARGET);
    }

    public Place getSource() {
        return placeMap.keySet().stream().
                filter(k -> placeMap.get(k) == PlaceType.SOURCE).findFirst().orElse(null);
    }

    public Place getTarget() {
        return placeMap.keySet().stream().
                filter(k -> placeMap.get(k) == PlaceType.TARGET).findFirst().orElse(null);
    }
    public Map<Place, PlaceType> getPlaceMap() {
        return placeMap;
    }

    public void setPlaceMap(Map<Place, PlaceType> placeMap) {
        this.placeMap = placeMap;
    }
}
