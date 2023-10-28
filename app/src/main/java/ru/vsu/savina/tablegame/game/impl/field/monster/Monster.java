package ru.vsu.savina.tablegame.game.impl.field.monster;

public abstract class Monster {
    private int shotNumber;
    private int attackNumber;
    private int rarityCoefficient;
    private int skinCost;

    private boolean isDead;

    public Monster(int shotNumber, int attackNumber, int rarityCoefficient, int skinCost) {
        this.shotNumber = shotNumber;
        this.attackNumber = attackNumber;
        this.rarityCoefficient = rarityCoefficient;
        this.skinCost = skinCost;
        isDead = false;
    }

    public int getShotNumber() {
        return shotNumber;
    }

    public void setShotNumber(int shotNumber) {
        this.shotNumber = shotNumber;
    }

    public int getAttackNumber() {
        return attackNumber;
    }

    public void setAttackNumber(int attackNumber) {
        this.attackNumber = attackNumber;
    }

    public int getRarityCoefficient() {
        return rarityCoefficient;
    }

    public void setRarityCoefficient(int rarityCoefficient) {
        this.rarityCoefficient = rarityCoefficient;
    }

    public int getSkinCost() {
        return skinCost;
    }

    public void setSkinCost(int skinCost) {
        this.skinCost = skinCost;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
