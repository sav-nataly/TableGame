package ru.vsu.savina.tablegame.view.wrapper;

import java.util.HashMap;
import java.util.Map;

import ru.vsu.savina.tablegame.game.engine.field.PlaceType;
import ru.vsu.savina.tablegame.game.engine.field.Transition;
import ru.vsu.savina.tablegame.game.engine.field.TransitionType;

public class TransitionWrapper {
    private Transition transition;
    private Map<PlaceWrapper, PlaceType> placeMap;

    public TransitionWrapper() {
        placeMap = new HashMap<>();
    }

    public TransitionWrapper(PlaceWrapper source, PlaceWrapper target) {
        placeMap = new HashMap<>();
        placeMap.put(source, PlaceType.SOURCE);
        placeMap.put(target, PlaceType.TARGET);
    }

    public Transition getTransition() {
        return transition;
    }

    public void setTransition(Transition transition) {
        this.transition = transition;
    }

    public Map<PlaceWrapper, PlaceType> getPlaceMap() {
        return placeMap;
    }

    public void setPlaceMap(Map<PlaceWrapper, PlaceType> placeMap) {
        this.placeMap = placeMap;
    }

    public void setTarget(PlaceWrapper wrapper) {
        placeMap.put(wrapper, PlaceType.TARGET);

        wrapper.getTransitionMap().put(this, TransitionType.INCOME);
    }

    public void setSource(PlaceWrapper wrapper) {
        placeMap.put(wrapper, PlaceType.SOURCE);

        wrapper.getTransitionMap().put(this, TransitionType.OUTGO);
    }

    public PlaceWrapper getSource() {
        return placeMap.keySet().stream().
                filter(k -> placeMap.get(k) == PlaceType.SOURCE).findFirst().orElse(null);
    }

    public PlaceWrapper getTarget() {
        return placeMap.keySet().stream().
                filter(k -> placeMap.get(k) == PlaceType.TARGET).findFirst().orElse(null);
    }
}
