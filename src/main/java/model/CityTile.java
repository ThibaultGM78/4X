/**
 * @file CityTile.java
 * @brief Class representing a city tile on the game map.
 *
 * This class extends the Tile class and represents a specific type of tile: a city.
 */
package model;

public class CityTile extends Tile {

    /**
     * @brief Constructor for the CityTile class.
     *
     * Initializes the city tile with a specific type and image path.
     */
    public CityTile() {
        super(1, "images/icons/Small/city.png");
    }
}
