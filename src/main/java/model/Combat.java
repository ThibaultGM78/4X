/**
 * @file Combat.java
 * @brief Class representing a combat scenario between two players.
 *
 * This class manages the combat mechanics, including the attacker, defender,
 * dice rolls, and the outcome of the combat.
 */
package model;

public class Combat {
    private int defPt;
    private String atqPlayer;
    private String defPlayer;
    private int dice1;
    private int dice2;
    private int initDefLife;
    private int type;

    /**
     * @brief Constructor for the Combat class.
     * @param defPt The initial defense points of the defender.
     * @param pAtq The name of the attacking player.
     * @param pDef The name of the defending player.
     * @param type The type of combat.
     */
    public Combat(int defPt, String pAtq, String pDef, int type) {
        this.defPt = defPt;
        this.atqPlayer = pAtq;
        this.defPlayer = pDef;
        this.type = type;
        this.initDefLife = defPt;

        this.dice1 = (int) (Math.random() * 6) + 1;
        this.dice2 = (int) (Math.random() * 6) + 1;
    }

    /**
     * @brief Gets the name of the attacking player.
     * @return The name of the attacking player.
     */
    public String getAtqPlayer() {
        return atqPlayer;
    }

    /**
     * @brief Sets the name of the attacking player.
     * @param atqPlayer The name of the attacking player.
     */
    public void setAtqPlayer(String atqPlayer) {
        this.atqPlayer = atqPlayer;
    }

    /**
     * @brief Gets the name of the defending player.
     * @return The name of the defending player.
     */
    public String getDefPlayer() {
        return defPlayer;
    }

    /**
     * @brief Sets the name of the defending player.
     * @param defPlayer The name of the defending player.
     */
    public void setDefPlayer(String defPlayer) {
        this.defPlayer = defPlayer;
    }

    /**
     * @brief Gets the value of the first dice roll.
     * @return The value of the first dice roll.
     */
    public int getDice1() {
        return dice1;
    }

    /**
     * @brief Sets the value of the first dice roll.
     * @param dice1 The value of the first dice roll.
     */
    public void setDice1(int dice1) {
        this.dice1 = dice1;
    }

    /**
     * @brief Gets the value of the second dice roll.
     * @return The value of the second dice roll.
     */
    public int getDice2() {
        return dice2;
    }

    /**
     * @brief Sets the value of the second dice roll.
     * @param dice2 The value of the second dice roll.
     */
    public void setDice2(int dice2) {
        this.dice2 = dice2;
    }

    /**
     * @brief Gets the initial defense life points of the defender.
     * @return The initial defense life points of the defender.
     */
    public int getInitDefLife() {
        return initDefLife;
    }

    /**
     * @brief Sets the initial defense life points of the defender.
     * @param initDefLife The initial defense life points of the defender.
     */
    public void setInitDefLife(int initDefLife) {
        this.initDefLife = initDefLife;
    }

    /**
     * @brief Gets the current defense points of the defender.
     * @return The current defense points of the defender.
     */
    public int getDefPt() {
        return defPt;
    }

    /**
     * @brief Sets the current defense points of the defender.
     * @param defPt The current defense points of the defender.
     */
    public void setDefPt(int defPt) {
        this.defPt = defPt;
    }

    /**
     * @brief Gets the type of combat.
     * @return The type of combat.
     */
    public int getType() {
        return type;
    }

    /**
     * @brief Sets the type of combat.
     * @param type The type of combat.
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @brief Checks if the combat results in a victory for the attacker.
     * @return True if the defender's life points are reduced to 0, false otherwise.
     */
    public boolean isVictory() {
        return this.getDefLifeAfterCombat() == 0;
    }

    /**
     * @brief Calculates the defender's life points after the combat.
     * @return The defender's life points after the combat.
     */
    public int getDefLifeAfterCombat() {
        int d = this.initDefLife - this.dice1 - this.dice2;
        return d <= 0 ? 0 : d;
    }
}
