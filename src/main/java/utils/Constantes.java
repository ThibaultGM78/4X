/**
 * @file Constantes.java
 * @brief Class containing constant values used throughout the game.
 *
 * This class defines various constants such as map size, player colors, tile types,
 * rewards, costs, combat types, defense points, score values, and other game-related constants.
 */
package utils;

public class Constantes {

    /**
     * @brief The size of the game map.
     */
    public static final int MAP_SIZE = 10;

    /**
     * @brief The number of players in the game.
     */
    public static final int MAP_N_PLAYER = 4;

    /**
     * @brief The default image path for null images.
     */
    public static final String IMAGE_NULL_PATH = "";

    /**
     * @brief The type identifier for default tiles.
     */
    public static final int TILE_TYPE_DEFAULT = 0;

    /**
     * @brief The type identifier for city tiles.
     */
    public static final int TILE_TYPE_CITY = 1;

    /**
     * @brief The type identifier for forest tiles.
     */
    public static final int TILE_TYPE_FOREST = 2;

    /**
     * @brief The type identifier for mountain tiles.
     */
    public static final int TILE_TYPE_MOUNTAIN = 3;

    /**
     * @brief The color associated with Player 1.
     */
    public static final String PLAYER_1_COLOR = "#FF474D";

    /**
     * @brief The color associated with Player 2.
     */
    public static final String PLAYER_2_COLOR = "#ADDFFF";

    /**
     * @brief The color associated with Player 3.
     */
    public static final String PLAYER_3_COLOR = "#96F97A";

    /**
     * @brief The color associated with Player 4.
     */
    public static final String PLAYER_4_COLOR = "#FCA746";

    /**
     * @brief The default color for tiles.
     */
    public static final String DEFAULT_COLOR = "#D6DBD4";

    /**
     * @brief The default state identifier for tiles.
     */
    public static final int TILE_STATE_DEFAULT = 0;

    /**
     * @brief The state identifier for tiles owned by Player 1.
     */
    public static final int TILE_STATE_OWN_BY_PLAYER_1 = 1;

    /**
     * @brief The state identifier for tiles owned by Player 2.
     */
    public static final int TILE_STATE_OWN_BY_PLAYER_2 = 2;

    /**
     * @brief The state identifier for tiles owned by Player 3.
     */
    public static final int TILE_STATE_OWN_BY_PLAYER_3 = 3;

    /**
     * @brief The state identifier for tiles owned by Player 4.
     */
    public static final int TILE_STATE_OWN_BY_PLAYER_4 = 4;

    /**
     * @brief A constant representing a non-valid value.
     */
    public static final int NON_VALABLE = -1;

    /**
     * @brief The reward for controlling a city.
     */
    public static final int REWARD_CITY_CONTROL = 4;

    /**
     * @brief The reward for foraging in a forest.
     */
    public static final int REWARD_FOREST_FORAGE = 3;

    /**
     * @brief The cost of a soldier.
     */
    public static final int COST_SOLDIER = 12;

    /**
     * @brief The type identifier for combat involving soldiers.
     */
    public static final int COMBAT_TYPE_SOLDIER = 0;

    /**
     * @brief The type identifier for combat involving cities.
     */
    public static final int COMBAT_TYPE_CITY = 0;

    /**
     * @brief The defense points of a city.
     */
    public static final int CITY_DEFENSE = 8;

    /**
     * @brief The defense points of a soldier.
     */
    public static final int SOLDIER_DEFENSE = 12;

    /**
     * @brief The amount of healing a soldier receives.
     */
    public static final int SOLDIER_HEAL = 4;

    /**
     * @brief The score value for winning a combat.
     */
    public static final int SCORE_VALUE_COMBAT_WIN = 1;

    /**
     * @brief The score value for winning a city.
     */
    public static final int SCORE_VALUE_CITY_WIN = 4;

    /**
     * @brief The score required to win the game.
     */
    public static final int SCORE_WIN = 4; // 16;//Number of city * SCORE_VALUE_CITY_WIN

    /**
     * @brief The reload time in milliseconds.
     */
    public static final int RELAOD_TIME_MS = 5000;
}
