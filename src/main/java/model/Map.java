package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import model.CityTile;
import utils.Constantes;
import model.DefaultTile;
import model.ForestTile;
import model.MountainTile;
import model.Player;
import model.Soldier;
import model.Tile;

public class Map {
	//Var
	private Tile[][] grid;
	private Player[] players;
	private int idPlayerTurn;
	private int idGame;
	  
	//Constructor
	public Map() {
		this.grid = new Tile[Constantes.MAP_SIZE][Constantes.MAP_SIZE];
		this.players = new Player[Constantes.MAP_N_PLAYER];
	
		this.fillGridRandomly();
		//this.grid[1][1] = new CityTile();
		//this.grid[1][1].setState(1);
		
		//
		this.idPlayerTurn = 1;
	}
	
	//GetSet
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

	//Methode
	public void fillGridRandomly() {
	    ArrayList<int[]> positions = new ArrayList<>();
	    
	    //Générer toutes les positions possibles dans une grille 10x10
	    for (int i = 0; i < Constantes.MAP_SIZE; i++) {
	        for (int j = 0; j < Constantes.MAP_SIZE; j++) {
	            positions.add(new int[] { i, j });
	        }
	    }
	    
	    // Mélanger les positions pour garantir l'aléatoire
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

	public int getIdNewPlayer() {
		
		for(int i = 0; i < Constantes.MAP_N_PLAYER; i++) {
			if(this.players[i] == null) {
				this.addNewPlayer(i);
				return i + 1;
			}
			
		}
		return 0;
	}

	public int getCurrentNumberOfPlayer() {
		
		for(int i = 0; i < Constantes.MAP_N_PLAYER; i++) {
			if(this.players[i] == null) {
				return i;
			}
		}
		return 4;
	}
	
	public void setNextPlayerTurn() {
		
		int x = this.idPlayerTurn + 1;
		this.idPlayerTurn = x > this.getCurrentNumberOfPlayer() ? 1 : x; 
	}
	
	public void addNewPlayer(int id) {
		this.players[id] = new Player();
		
	    Random random = new Random();
	    int i, j;
	    
	   do {
	    	i =  random.nextInt(10);
	    	j = random.nextInt(10);
	    }while(this.grid[i][j].isSoldier() || this.grid[i][j].getType() == Constantes.TILE_TYPE_MOUNTAIN);
	    
	    this.grid[i][j].setSoldier(new Soldier(id + 1));
	}

}
