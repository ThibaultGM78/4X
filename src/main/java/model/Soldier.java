/**
 * @file Soldier.java
 * @brief Class representing a soldier in the game.
 *
 * This class manages the soldier's attributes such as defense points, owner ID,
 * image path, and last action turn. It also includes methods to modify these
 * attributes and handle combat-related events like receiving damage or healing.
 */
package model;

import utils.Constantes;

public class Soldier {
    private final String imagePath = "images/icons/Small/soldier.png";
    private int idPlayerOwner;
    private int defensePoint;
    private int lastActionTurn;
    private int maxDefensePoint;

    /**
     * @brief Constructor for the Soldier class.
     * @param idPlayerOwner The ID of the player who owns the soldier.
     */
    public Soldier(int idPlayerOwner) {
        this.idPlayerOwner = idPlayerOwner;
        this.defensePoint = Constantes.SOLDIER_DEFENSE;
        this.maxDefensePoint = Constantes.SOLDIER_DEFENSE;
        this.lastActionTurn = -1;
    }

    /**
     * @brief Gets the soldier's defense points.
     * @return The soldier's defense points.
     */
    public int getDefensePoint() {
        return defensePoint;
    }

    /**
     * @brief Sets the soldier's defense points.
     * @param defensePoint The soldier's defense points.
     */
    public void setDefensePoint(int defensePoint) {
        this.defensePoint = defensePoint;
    }

    /**
     * @brief Gets the ID of the player who owns the soldier.
     * @return The ID of the player who owns the soldier.
     */
    public int getIdPlayerOwner() {
        return idPlayerOwner;
    }

    /**
     * @brief Sets the ID of the player who owns the soldier.
     * @param idPlayerOwner The ID of the player who owns the soldier.
     */
    public void setIdPlayerOwner(int idPlayerOwner) {
        this.idPlayerOwner = idPlayerOwner;
    }

    /**
     * @brief Gets the image path of the soldier.
     * @return The image path of the soldier.
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @brief Gets the last action turn of the soldier.
     * @return The last action turn of the soldier.
     */
    public int getLastActionTurn() {
        return lastActionTurn;
    }

    /**
     * @brief Sets the last action turn of the soldier.
     * @param lastActionTurn The last action turn of the soldier.
     */
    public void setLastActionTurn(int lastActionTurn) {
        this.lastActionTurn = lastActionTurn;
    }

    /**
     * @brief Gets the maximum defense points of the soldier.
     * @return The maximum defense points of the soldier.
     */
    public int getMaxDefensePoint() {
        return maxDefensePoint;
    }

    /**
     * @brief Sets the maximum defense points of the soldier.
     * @param maxDefensePoint The maximum defense points of the soldier.
     */
    public void setMaxDefensePoint(int maxDefensePoint) {
        this.maxDefensePoint = maxDefensePoint;
    }

    /**
     * @brief Decreases the soldier's defense points by a specified amount of damage.
     * @param d The amount of damage to receive.
     */
    public void receiveDamage(int d) {
        this.defensePoint -= d;
        if (this.defensePoint < 0) {
            this.defensePoint = 0;
        }
    }

    /**
     * @brief Checks if the soldier is knocked out (defense points are zero or less).
     * @return True if the soldier is knocked out, false otherwise.
     */
    public boolean isKo() {
        return this.defensePoint <= 0;
    }

    /**
     * @brief Checks if the soldier is blessed (defense points are less than maximum defense points).
     * @return True if the soldier is blessed, false otherwise.
     */
    public boolean isBlessed() {
        return this.getDefensePoint() < this.getMaxDefensePoint();
    }

    /**
     * @brief Increases the soldier's defense points by a specified amount of healing.
     * @param h The amount of healing to receive.
     */
    public void receiveHeal(int h) {
        int h2 = this.getDefensePoint() + h;
        this.setDefensePoint(h2 > this.getMaxDefensePoint() ? this.getDefensePoint() : h2);
    }
}
