package beans;

import model.CityTile;
import utils.Constantes;
import model.DefaultTile;
import model.Player;
import model.Soldier;
import model.Tile;

public class Map {
	//Var
	private Tile[][] grid;
	private Player[] players;
	private int idPlayerTurn;
	  
	//Constructor
	public Map() {
		this.grid = new Tile[Constantes.MAP_SIZE][Constantes.MAP_SIZE];
		this.players = new Player[Constantes.MAP_N_PLAYER];
		
		for(int i = 0; i <Constantes.MAP_SIZE; i++) {
			for(int j = 0; j < Constantes.MAP_SIZE; j++) {
				this.grid[i][j] = new DefaultTile();
			}
		}
		
		this.grid[2][2] = new CityTile();
		this.grid[2][2].setSoldier(new Soldier(0));
		this.grid[2][2].setState(Constantes.TILE_STATE_OWN_BY_PLAYER_1);
		
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

	
	//Methode
	public int getIdNewPlayer() {
		
		for(int i = 0; i < Constantes.MAP_N_PLAYER; i++) {
			if(this.players[i] == null) {
				this.players[i] = new Player();
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


}
