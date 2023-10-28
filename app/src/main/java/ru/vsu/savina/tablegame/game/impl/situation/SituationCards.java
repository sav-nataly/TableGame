package ru.vsu.savina.tablegame.game.impl.situation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SituationCards {
    private List<Situation> situationList;

    public SituationCards() {
        situationList = new ArrayList<>();
    }

    public SituationCards(List<Situation> situationList) {
        this.situationList = situationList;
    }

    public List<Situation> getSituationList() {
        return situationList;
    }

    public void setSituationList(List<Situation> situationList) {
        this.situationList = situationList;
    }

    public Situation getSituation() {
        int index = ThreadLocalRandom.current().nextInt(situationList.size());

        return situationList.get(index);
    }
}
