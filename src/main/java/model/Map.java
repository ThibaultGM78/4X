package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import utils.Constantes;

public class Map {
    // Variable pour l'instance unique du singleton
    private static Map instance;

    // Grille, joueurs, et autres variables
    private Tile[][] grid;
    private Player[] players;
    private int idPlayerTurn;
    private int idGame;
    private int turn;
    
    // Constructor privé pour éviter l'instanciation extérieure
    private Map() {
        this.grid = new Tile[Constantes.MAP_SIZE][Constantes.MAP_SIZE];
        this.players = new Player[Constantes.MAP_N_PLAYER];

        this.fillGridRandomly();
        
        this.idPlayerTurn = 1;
        this.setTurn(0);

    }

    // Méthode pour obtenir l'instance unique (singleton)
    public static Map getInstance() {
        if (instance == null) {
            instance = new Map();  // Initialisation uniquement si l'instance n'existe pas
        }
        return instance;
    }

    // Getters et Setters (inchangés)
    public Tile[][] getGrid() {
        return grid;
    }

    public void setGrid(Tile[][] grid) {
        this.grid = grid;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public int getIdPlayerTurn() {
        return idPlayerTurn;
    }

    public void setIdPlayerTurn(int idPlayerTurn) {
        this.idPlayerTurn = idPlayerTurn;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
    
    // Méthodes existantes (inchangées)
    public void fillGridRandomly() {
        ArrayList<int[]> positions = new ArrayList<>();
        for (int i = 0; i < Constantes.MAP_SIZE; i++) {
            for (int j = 0; j < Constantes.MAP_SIZE; j++) {
                positions.add(new int[] { i, j });
            }
        }
        Collections.shuffle(positions);

        // Placer les villes
        ArrayList<int[]> cityPositions = new ArrayList<>();
        int citiesPlaced = 0;
        for (int[] pos : positions) {
            if (citiesPlaced == 4) break;
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

        // Retirer les positions utilisées pour les villes
        positions.removeAll(cityPositions);

        // Placer les montagnes
        int mountainsPlaced = 0;
        for (int[] pos : positions) {
            if (mountainsPlaced == 5) break;
            this.grid[pos[0]][pos[1]] = new MountainTile();
            mountainsPlaced++;
        }

        // Retirer les positions utilisées pour les montagnes
        positions = new ArrayList<>(positions.subList(mountainsPlaced, positions.size()));

        // Placer les forêts
        int forestsPlaced = 0;
        for (int[] pos : positions) {
            if (forestsPlaced == 10) break;
            this.grid[pos[0]][pos[1]] = new ForestTile();
            forestsPlaced++;
        }

        // Remplir les cases restantes avec des tuiles par défaut
        for (int i = 0; i < Constantes.MAP_SIZE; i++) {
            for (int j = 0; j < Constantes.MAP_SIZE; j++) {
                if (this.grid[i][j] == null) {
                    this.grid[i][j] = new DefaultTile();
                }
            }
        }
    }

    public int getIdNewPlayer(int idUser, String name) {
        for (int i = 0; i < Constantes.MAP_N_PLAYER; i++) {
            if (this.players[i] == null) {
                this.addNewPlayer(i, idUser, name);
                
                return i + 1;
            }
            if(this.players[i].getIdUser() == idUser) {
            	return i + 1;
            }
        }
        return 0;
    }

    public int getCurrentNumberOfPlayer() {
        for (int i = 0; i < Constantes.MAP_N_PLAYER; i++) {
            if (this.players[i] == null) {
                return i;
            }
        }
        return 4;
    }

    public void setNextPlayerTurn() {
        int x = this.idPlayerTurn + 1;
        this.idPlayerTurn = x > this.getCurrentNumberOfPlayer() ? 1 : x;
    }

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

    public Player getPlayer(int i) {
        return this.players[i - 1];
    }

    public void nextTurn() {
        this.turn += 1;
        
        for(int i = 0; i < Constantes.MAP_SIZE; i++) {
        	for(int j = 0; j < Constantes.MAP_SIZE; j++) {
        		if(this.grid[i][j].getType() == Constantes.TILE_TYPE_CITY && this.grid[i][j].getState() == this.getIdPlayerTurn()) {
        			
        			this.players[this.getIdPlayerTurn() - 1].addGold(Constantes.REWARD_CITY_CONTROL);
        		}
        	}
        }
        this.setNextPlayerTurn();
    }

    public boolean canMove(int y, int x) {
        if (x < 0 || x >= Constantes.MAP_SIZE) return false;
        if (y < 0 || y >= Constantes.MAP_SIZE) return false;
        if (this.grid[y][x].getType() == Constantes.TILE_TYPE_MOUNTAIN) return false;
        if(this.grid[y][x].isSoldier() && this.grid[y][x].getSoldier().getIdPlayerOwner() == this.idPlayerTurn) return false;
        return true;
    }

    public void move(int x1, int y1, int x2, int y2, int idPlayer) {
        if (this.canMove(x2, y2)) {
            System.out.println("ca bouge: " + y2 + " / " + x2);
            Soldier s = this.grid[x1][y1].getSoldier();
            s.setLastActionTurn(this.turn);
            this.grid[x1][y1].clearSoldier();
            this.grid[x2][y2].setSoldier(s);
            
            if(this.grid[x2][y2].getType() == Constantes.TILE_TYPE_CITY) {
            	this.grid[x2][y2].setState(idPlayer);
            	
            }
            
        }
    }
    
    public Player getActuelPlayer() {
    	return this.getPlayer(this.idPlayerTurn);
    }
    
}
