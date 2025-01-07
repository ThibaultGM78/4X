package model;

import utils.Constantes;

public class Tile {
    // Var
    private int type;
    private String imagePath;
    private Soldier soldier;
    private int state;
    private boolean isSoldier;
    private int defensePoint;

    // Constructor
    public Tile(int type, String imagePath) {
        this.type = type;
        this.imagePath = imagePath;
        this.state = Constantes.TILE_STATE_DEFAULT;
        this.isSoldier = false;
        this.defensePoint = Constantes.CITY_DEFENSE;
    }

    // GetSet
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Soldier getSoldier() {
        return soldier;
    }

    public void setSoldier(Soldier soldier) {
        this.soldier = soldier;
        this.isSoldier = (soldier != null); // Synchronise isSoldier avec soldier
        if (soldier != null) {
            this.state = soldier.getIdPlayerOwner();
        }
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        this.defensePoint = Constantes.CITY_DEFENSE;
    }

    public boolean isSoldier() {
        return this.isSoldier;
    }

    public void clearSoldier() {
        this.soldier = null;
        this.isSoldier = false; // Synchronise isSoldier après avoir supprimé le soldat
    }

    public int getDefense() {
        return defensePoint;
    }

    public void setDefense(int defense) {
        this.defensePoint = defense;
    }

    public void setSoldier(boolean isSoldier) {
        this.isSoldier = isSoldier;
    }

    // Methode
    public String getOverlayImagePath() {
        return this.isSoldier() ? this.soldier.getImagePath() : Constantes.IMAGE_NULL_PATH;
    }

    public String getCellCSSClass() {
        if (this.getType() == Constantes.TILE_TYPE_CITY || this.isSoldier()) {
            return "cell cell" + String.valueOf(this.getState());
        }
        return "cell cell0";
    }

    public void receiveDamage(int d) {
        this.defensePoint -= d;
        if (this.defensePoint < 0) {
            this.defensePoint = 0;
        }
    }

    public boolean isKo() {
        return this.defensePoint <= 0;
    }
}
