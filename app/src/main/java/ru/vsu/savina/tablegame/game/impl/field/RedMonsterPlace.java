package ru.vsu.savina.tablegame.game.impl.field;

import java.util.Map;

import ru.vsu.savina.tablegame.game.engine.field.Place;
import ru.vsu.savina.tablegame.game.engine.field.Transition;
import ru.vsu.savina.tablegame.game.engine.field.TransitionType;
import ru.vsu.savina.tablegame.game.impl.field.monster.Monster;

public class RedMonsterPlace extends Place {
    private Monster monster;

    public RedMonsterPlace() {
        setMustStop(true);
    }

    public RedMonsterPlace(Monster monster) {
        this.monster = monster;
    }

    public RedMonsterPlace(Map<Transition, TransitionType> transitionMap, Monster monster) {
        super(transitionMap);
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }
}
