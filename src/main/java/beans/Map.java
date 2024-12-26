package beans;

import model.CityTile;
import utils.Constantes;
import model.DefaultTile;
import model.Soldier;
import model.Tile;

public class Map {
	//Var
	private Tile[][] grid;
	  
	//Constructor
	public Map() {
		this.grid = new Tile[Constantes.MAP_SIZE][Constantes.MAP_SIZE];
		
		for(int i = 0; i <Constantes.MAP_SIZE; i++) {
			for(int j = 0; j < Constantes.MAP_SIZE; j++) {
				this.grid[i][j] = new DefaultTile();
			}
		}
		
		this.grid[2][2] = new CityTile();
		this.grid[2][2].setSoldier(new Soldier(0));
		this.grid[2][2].setState(Constantes.TILE_STATE_OWN_BY_PLAYER_1);
	}
	
	//GetSet
	public Tile[][] getGrid() {
		return grid;
	}
	
	public void setGrid(Tile[][] grid) {
		this.grid = grid;
	}


}
