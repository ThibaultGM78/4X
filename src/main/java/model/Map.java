/**
 * @file Map.java
 * @brief Class representing the game map.
 *
 * This class manages the game map, players, turns, and soldier movements.
 * It uses the Singleton design pattern to ensure a single instance of the map.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import utils.Constantes;

public class Map {
    private static Map instance;

    private Tile[][] grid;
    private Player[] players;
    private int idPlayerTurn;
    private int idGame;
    private int turn;
    private boolean gameClose;

    /**
     * @brief Private constructor to prevent external instantiation.
     */
    private Map() {
        this.grid = new Tile[Constantes.MAP_SIZE][Constantes.MAP_SIZE];
        this.players = new Player[Constantes.MAP_N_PLAYER];

        this.fillGridRandomly();

        this.idPlayerTurn = 1;
        this.setTurn(0);
        this.gameClose = false;
    }

    /**
     * @brief Singleton method to get the instance of the map.
     * @return The single instance of the map.
     */
    public static Map getInstance() {
        if (instance == null) {
            instance = new Map();
        }
        return instance;
    }

    /**
     * @brief Resets the singleton instance.
     */
    public static void resetInstance() {
        instance = null;
    }

    /**
     * @brief Gets the grid.
     * @return The grid.
     */
    public Tile[][] getGrid() {
        return grid;
    }

    /**
     * @brief Sets the grid.
     * @param grid The new grid.
     */
    public void setGrid(Tile[][] grid) {
        this.grid = grid;
    }

    /**
     * @brief Gets the players.
     * @return The players.
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * @brief Sets the players.
     * @param players The new players.
     */
    public void setPlayers(Player[] players) {
        this.players = players;
    }

    /**
     * @brief Gets the ID of the player whose turn it is.
     * @return The ID of the player whose turn it is.
     */
    public int getIdPlayerTurn() {
        return idPlayerTurn;
    }

    /**
     * @brief Sets the ID of the player whose turn it is.
     * @param idPlayerTurn The new ID of the player whose turn it is.
     */
    public void setIdPlayerTurn(int idPlayerTurn) {
        this.idPlayerTurn = idPlayerTurn;
    }

    /**
     * @brief Gets the game ID.
     * @return The game ID.
     */
    public int getIdGame() {
        return idGame;
    }

    /**
     * @brief Sets the game ID.
     * @param idGame The new game ID.
     */
    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    /**
     * @brief Gets the current turn.
     * @return The current turn.
     */
    public int getTurn() {
        return turn;
    }


    public void setTurn(int turn) {
        this.turn = turn;
    }
    
    public boolean isGameClose() {
		return gameClose;
	}

	public void closeGame() {
		this.gameClose = true;
	}

	/**
     * @brief Fills the grid randomly with different types of tiles.
     */
    public void fillGridRandomly() {
        ArrayList<int[]> positions = new ArrayList<>();
        for (int i = 0; i < Constantes.MAP_SIZE; i++) {
            for (int j = 0; j < Constantes.MAP_SIZE; j++) {
                positions.add(new int[] { i, j });
            }
        }
        Collections.shuffle(positions);

        // Place cities
        ArrayList<int[]> cityPositions = new ArrayList<>();
        int citiesPlaced = 0;
        for (int[] pos : positions) {
            if (citiesPlaced == 4)
                break;
            boolean valid = true;
            for (int[] city : cityPositions) {
                int distance = Math.abs(city[0] - pos[0]) + Math.abs(city[1] - pos[1]);
                if (distance < 4) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                this.grid[pos[0]][pos[1]] = new CityTile();
                cityPositions.add(pos);
                citiesPlaced++;
            }
        }

        // Remove positions used for cities
        positions.removeAll(cityPositions);

        // Place mountains
        int mountainsPlaced = 0;
        for (int[] pos : positions) {
            if (mountainsPlaced == 5)
                break;
            this.grid[pos[0]][pos[1]] = new MountainTile();
            mountainsPlaced++;
        }

        // Remove positions used for mountains
        positions = new ArrayList<>(positions.subList(mountainsPlaced, positions.size()));

        // Place forests
        int forestsPlaced = 0;
        for (int[] pos : positions) {
            if (forestsPlaced == 10)
                break;
            this.grid[pos[0]][pos[1]] = new ForestTile();
            forestsPlaced++;
        }

        // Fill remaining cells with default tiles
        for (int i = 0; i < Constantes.MAP_SIZE; i++) {
            for (int j = 0; j < Constantes.MAP_SIZE; j++) {
                if (this.grid[i][j] == null) {
                    this.grid[i][j] = new DefaultTile();
                }
            }
        }
    }

    /**
     * @brief Gets the ID for a new player.
     * @param idUser The user ID.
     * @param name The player's name.
     * @return The ID of the new player, or 0 if the player already exists.
     */
    public int getIdNewPlayer(int idUser, String name) {
        for (int i = 0; i < Constantes.MAP_N_PLAYER; i++) {
            if (this.players[i] == null) {
                this.addNewPlayer(i, idUser, name);
                return i + 1;
            }
            if (this.players[i].getIdUser() == idUser) {
                return i + 1;
            }
        }
        return 0;
    }

    /**
     * @brief Gets the current number of players.
     * @return The current number of players.
     */
    public int getCurrentNumberOfPlayer() {
        for (int i = 0; i < Constantes.MAP_N_PLAYER; i++) {
            if (this.players[i] == null) {
                return i;
            }
        }
        return Constantes.MAP_N_PLAYER;
    }

    /**
     * @brief Sets the next player's turn.
     */
    public void setNextPlayerTurn() {
        int x = this.idPlayerTurn + 1;
        this.idPlayerTurn = x > this.getCurrentNumberOfPlayer() ? 1 : x;
        System.out.println("ICI: " + this.idPlayerTurn + " / nplayer" + this.getCurrentNumberOfPlayer());
    }

    /**
     * @brief Adds a new player.
     * @param idPlayer The player ID.
     * @param idUser The user ID.
     * @param name The player's name.
     */
    public void addNewPlayer(int idPlayer, int idUser, String name) {
        this.players[idPlayer] = new Player();

        this.players[idPlayer].setIdUser(idUser);
        this.players[idPlayer].setName(name);

        Random random = new Random();
        int i, j;

        do {
            i = random.nextInt(10);
            j = random.nextInt(10);
        } while (this.grid[i][j].isSoldier() || this.grid[i][j].getType() == Constantes.TILE_TYPE_MOUNTAIN);

        this.grid[i][j].setSoldier(new Soldier(idPlayer + 1));
    }

    /**
     * @brief Gets a player by their ID.
     * @param i The player ID.
     * @return The player.
     */
    public Player getPlayer(int i) {
        return this.players[i - 1];
    }

    /**
     * @brief Advances to the next turn.
     */
    public void nextTurn() {
        this.turn += 1;

        for (int i = 0; i < Constantes.MAP_SIZE; i++) {
            for (int j = 0; j < Constantes.MAP_SIZE; j++) {
                if (this.grid[i][j].getType() == Constantes.TILE_TYPE_CITY
                        && this.grid[i][j].getState() == this.getIdPlayerTurn()) {

                    this.players[this.getIdPlayerTurn() - 1].addGold(Constantes.REWARD_CITY_CONTROL);
                }
            }
        }
        this.setNextPlayerTurn();
    }

    /**
     * @brief Checks if a move is valid.
     * @param y The y-coordinate.
     * @param x The x-coordinate.
     * @return True if the move is valid, false otherwise.
     */
    public boolean canMove(int y, int x) {
        if (x < 0 || x >= Constantes.MAP_SIZE)
            return false;
        if (y < 0 || y >= Constantes.MAP_SIZE)
            return false;
        if (this.grid[y][x].getType() == Constantes.TILE_TYPE_MOUNTAIN)
            return false;
        if (this.grid[y][x].isSoldier() && this.grid[y][x].getSoldier().getIdPlayerOwner() == this.idPlayerTurn)
            return false;
        return true;
    }

    /**
     * @brief Moves a soldier from one position to another.
     * @param x1 The starting x-coordinate.
     * @param y1 The starting y-coordinate.
     * @param x2 The ending x-coordinate.
     * @param y2 The ending y-coordinate.
     * @param idPlayer The player ID.
     */
    public void move(int x1, int y1, int x2, int y2, int idPlayer) {
        if (this.canMove(x2, y2)) {
            System.out.println("Moving to: " + y2 + " / " + x2);
            Soldier s = this.grid[x1][y1].getSoldier();
            s.setLastActionTurn(this.turn);
            this.grid[x1][y1].clearSoldier();
            this.grid[x2][y2].setSoldier(s);

            if (this.grid[x2][y2].getType() == Constantes.TILE_TYPE_CITY) {
                this.grid[x2][y2].setState(idPlayer);
            }
        }
    }

    /**
     * @brief Gets the current player.
     * @return The current player.
     */
    public Player getActuelPlayer() {
        return this.getPlayer(this.idPlayerTurn);
    }

    /**
     * @brief Determines the winner of the game.
     * @return The ID of the winning player, or -1 if there is no winner.
     */
    public int determineWinner() {
        int idWinner = -1;

        System.out.println("Actual ID Player: " + this.getActuelPlayer().getId());

        if (this.getActuelPlayer().getScore() >= Constantes.SCORE_WIN)
            return this.getActuelPlayer().getId();

        if (this.getCurrentNumberOfPlayer() == Constantes.MAP_N_PLAYER) {
            idWinner = this.getActuelPlayer().getId();
            for (int i = 0; i < Constantes.MAP_SIZE; i++) {
                for (int j = 0; j < Constantes.MAP_SIZE; j++) {
                    if (this.getGrid()[i][j].isSoldier()
                            && this.getGrid()[i][j].getSoldier().getIdPlayerOwner() != this.getActuelPlayer().getId()) {
                        return -1;
                    }
                }
            }
        }

        return idWinner;
    }
}
