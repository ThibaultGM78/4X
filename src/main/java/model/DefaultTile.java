/**
 * @file DefaultTile.java
 * @brief Class representing a default tile on the game map.
 *
 * This class extends the Tile class and represents a default type of tile,
 * which typically has no special properties or images.
 */
package model;

import utils.Constantes;

public class DefaultTile extends Tile {

    /**
     * @brief Constructor for the DefaultTile class.
     *
     * Initializes the default tile with a specific type and a null image path.
     */
    public DefaultTile() {
        super(0, Constantes.IMAGE_NULL_PATH);
    }
}
