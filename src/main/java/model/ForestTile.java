/**
 * @file ForestTile.java
 * @brief Class representing a forest tile on the game map.
 *
 * This class extends the Tile class and represents a specific type of tile: a forest.
 */
package model;

public class ForestTile extends Tile {

    /**
     * @brief Constructor for the ForestTile class.
     *
     * Initializes the forest tile with a specific type and image path.
     */
    public ForestTile() {
        super(2, "images/icons/Small/forest.png");
    }
}
