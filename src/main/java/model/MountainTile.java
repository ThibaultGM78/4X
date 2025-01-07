/**
 * @file MountainTile.java
 * @brief Class representing a mountain tile on the game map.
 *
 * This class extends the Tile class and represents a specific type of tile: a mountain.
 */
package model;

public class MountainTile extends Tile {

    /**
     * @brief Constructor for the MountainTile class.
     *
     * Initializes the mountain tile with a specific type and image path.
     */
    public MountainTile() {
        super(3, "images/icons/Small/mountain.png");
    }
}
