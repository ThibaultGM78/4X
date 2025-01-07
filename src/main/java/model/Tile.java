/**
 * @file Tile.java
 * @brief Class representing a tile on the game map.
 *
 * This class manages the tile's attributes such as type, image path, soldier presence,
 * state, and defense points. It also includes methods to modify these attributes and
 * handle tile-related events like receiving damage.
 */
package model;

import utils.Constantes;

public class Tile {
    // Variables
    private int type;
    private String imagePath;
    private Soldier soldier;
    private int state;
    private boolean isSoldier;
    private int defensePoint;

    /**
     * @brief Constructor for the Tile class.
     * @param type The type of the tile.
     * @param imagePath The image path of the tile.
     */
    public Tile(int type, String imagePath) {
        this.type = type;
        this.imagePath = imagePath;
        this.state = Constantes.TILE_STATE_DEFAULT;
        this.isSoldier = false;
        this.defensePoint = Constantes.CITY_DEFENSE;
    }

    /**
     * @brief Gets the type of the tile.
     * @return The type of the tile.
     */
    public int getType() {
        return type;
    }

    /**
     * @brief Sets the type of the tile.
     * @param type The type of the tile.
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @brief Gets the image path of the tile.
     * @return The image path of the tile.
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @brief Sets the image path of the tile.
     * @param imagePath The image path of the tile.
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * @brief Gets the soldier on the tile.
     * @return The soldier on the tile.
     */
    public Soldier getSoldier() {
        return soldier;
    }

    /**
     * @brief Sets the soldier on the tile.
     * @param soldier The soldier to set on the tile.
     */
    public void setSoldier(Soldier soldier) {
        this.soldier = soldier;
        this.state = soldier.getIdPlayerOwner();
        this.isSoldier = true;
    }

    /**
     * @brief Gets the state of the tile.
     * @return The state of the tile.
     */
    public int getState() {
        return state;
    }

    /**
     * @brief Sets the state of the tile.
     * @param state The state of the tile.
     */
    public void setState(int state) {
        this.state = state;
        this.defensePoint = Constantes.CITY_DEFENSE;
    }

    /**
     * @brief Checks if there is a soldier on the tile.
     * @return True if there is a soldier on the tile, false otherwise.
     */
    public boolean isSoldier() {
        return this.isSoldier;
    }

    /**
     * @brief Clears the soldier from the tile.
     */
    public void clearSoldier() {
        this.isSoldier = false;
    }

    /**
     * @brief Gets the defense points of the tile.
     * @return The defense points of the tile.
     */
    public int getDefense() {
        return defensePoint;
    }

    /**
     * @brief Sets the defense points of the tile.
     * @param defense The defense points of the tile.
     */
    public void setDefense(int defense) {
        this.defensePoint = defense;
    }

    /**
     * @brief Sets the presence of a soldier on the tile.
     * @param isSoldier True if there is a soldier on the tile, false otherwise.
     */
    public void setSoldier(boolean isSoldier) {
        this.isSoldier = isSoldier;
    }

    /**
     * @brief Gets the overlay image path of the tile.
     * @return The overlay image path of the tile.
     */
    public String getOverlayImagePath() {
        return this.isSoldier() ? this.soldier.getImagePath() : Constantes.IMAGE_NULL_PATH;
    }

    /**
     * @brief Gets the CSS class for the tile cell.
     * @return The CSS class for the tile cell.
     */
    public String getCellCSSClass() {
        if (this.getType() == Constantes.TILE_TYPE_CITY || this.isSoldier()) {
            return "cell cell" + String.valueOf(this.getState());
        }
        return "cell cell0";
    }

    /**
     * @brief Decreases the tile's defense points by a specified amount of damage.
     * @param d The amount of damage to receive.
     */
    public void receiveDamage(int d) {
        this.defensePoint -= d;
        if (this.defensePoint < 0) {
            this.defensePoint = 0;
        }
    }

    /**
     * @brief Checks if the tile is knocked out (defense points are zero or less).
     * @return True if the tile is knocked out, false otherwise.
     */
    public boolean isKo() {
        return this.defensePoint <= 0;
    }
}
