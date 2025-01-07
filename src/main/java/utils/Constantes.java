package utils;

public class Constantes {

	public static final int MAP_SIZE = 10;
	public static final int MAP_N_PLAYER = 4;

	public static final String IMAGE_NULL_PATH = "";

	public static final int TILE_TYPE_DEFAULT = 0;
	public static final int TILE_TYPE_CITY = 1;
	public static final int TILE_TYPE_FOREST = 2;
	public static final int TILE_TYPE_MOUNTAIN = 3;

	public static final String PLAYER_1_COLOR = "#FF474D";
	public static final String PLAYER_2_COLOR = "#ADDFFF";
	public static final String PLAYER_3_COLOR = "#96F97A";
	public static final String PLAYER_4_COLOR = "#FCA746";
	public static final String DEFAULT_COLOR = "#D6DBD4";

	public static final int TILE_STATE_DEFAULT = 0;
	public static final int TILE_STATE_OWN_BY_PLAYER_1 = 1;
	public static final int TILE_STATE_OWN_BY_PLAYER_2 = 2;
	public static final int TILE_STATE_OWN_BY_PLAYER_3 = 3;
	public static final int TILE_STATE_OWN_BY_PLAYER_4 = 4;

	public static final int NON_VALABLE = -1;

	public static final int REWARD_CITY_CONTROL = 4;
	public static final int REWARD_FOREST_FORAGE = 3;

	public static final int COST_SOLDIER = 12;

	public static final int COMBAT_TYPE_SOLDIER = 0;
	public static final int COMBAT_TYPE_CITY = 0;

	public static final int CITY_DEFENSE = 8;
	public static final int SOLDIER_DEFENSE = 12;
	public static final int SOLDIER_HEAL = 4;

	public static final int SCORE_VALUE_COMBAT_WIN = 1;
	public static final int SCORE_VALUE_CITY_WIN = 4;

	public static final int SCORE_WIN = 4;// 16;//Number of city * SCORE_VALUE_CITY_WIN
	
	public static final int RELAOD_TIME_MS = 5000;
}