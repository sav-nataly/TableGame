package ru.vsu.savina.tablegame.fieldgeneration;

import java.util.concurrent.ThreadLocalRandom;

import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.polygon.Polygon;
import ru.vsu.savina.tablegame.game.engine.field.Place;
import ru.vsu.savina.tablegame.game.impl.field.BluePlace;
import ru.vsu.savina.tablegame.game.impl.field.GreenPlace;
import ru.vsu.savina.tablegame.game.impl.field.GreyPlace;
import ru.vsu.savina.tablegame.game.impl.field.RedEnemyPlace;
import ru.vsu.savina.tablegame.game.impl.field.RedMonsterPlace;
import ru.vsu.savina.tablegame.game.impl.field.RedNativesPlace;
import ru.vsu.savina.tablegame.game.impl.field.YellowPlace;
import ru.vsu.savina.tablegame.game.impl.field.monster.Crocodile;
import ru.vsu.savina.tablegame.game.impl.field.monster.JangaMonster;
import ru.vsu.savina.tablegame.game.impl.field.monster.Snake;
import ru.vsu.savina.tablegame.view.wrapper.PlaceWrapper;

public class GraphGeneration {
    public static Place createPlace(PlaceWrapper u, Polygon p, boolean dPolygon) {
        switch (p.getType()) {
            case RIVER:
                return createRiverPlace(u);
            case SWAMP:
                return createSwampPlace(u);
            case JANGA:
                return createJangaPlace(u, dPolygon);
            default:
                return createPlainsPlace(u);
        }
    }

    private static Place createRiverPlace(PlaceWrapper u) {
        if (!u.getPlace().getClass().equals(GreyPlace.class))
            return new GreyPlace();

        return new BluePlace();
    }

    private static Place createPlainsPlace(PlaceWrapper u) {
        if (!u.getPlace().getClass().equals(GreyPlace.class))
            return new GreyPlace();

        double dNum = ThreadLocalRandom.current().nextDouble();

        if (dNum < 0.2) {
            return new RedEnemyPlace();
        }
        if (dNum > 0.2 && dNum < 0.4) {
            return new RedNativesPlace();
        }
        return new YellowPlace();
    }

    private static Place createSwampPlace(PlaceWrapper u) {
        if (!u.getPlace().getClass().equals(GreyPlace.class))
            return new GreyPlace();

        double dNum = ThreadLocalRandom.current().nextDouble();

        if (dNum < 0.2) {
            return new YellowPlace();
        }

        return new GreenPlace();
    }

    private static Place createJangaPlace(PlaceWrapper u, boolean dPolygon) {
        if (!u.getPlace().getClass().equals(GreyPlace.class))
            return new GreyPlace();

        double dNum = ThreadLocalRandom.current().nextDouble();

        if (dNum < 0.2) {
            return new YellowPlace();
        }

        RedMonsterPlace place = new RedMonsterPlace();

        if (dPolygon) {
            place.setMonster(new JangaMonster(5, 5, 4, 50000));
        } else {
            double mNum = ThreadLocalRandom.current().nextDouble();

            if (mNum < 0.3) {
                place.setMonster(new Crocodile(2, 2, 1, 15000));
            } else {
                place.setMonster(new Snake(3, 3, 2, 25000));
            }
        }
        return place;
    }
}
