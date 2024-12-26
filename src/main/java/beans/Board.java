package beans;

import utils.Constantes;

public class Board {
	//Var
	private int[][] grid;
	  
	//Constructor
	public Board() {
		this.grid = new int[Constantes.BOARD_SIZE][Constantes.BOARD_SIZE];
		
		for(int i = 0; i <Constantes.BOARD_SIZE; i++) {
			for(int j = 0; j < Constantes.BOARD_SIZE; j++) {
				this.grid[i][j] = Constantes.TILE_TYPE_DEFAULT;
			}
		}
	}
	
	//GetSet
	public int[][] getGrid() {
		return grid;
	}
	
	public void setGrid(int[][] grid) {
		this.grid = grid;
	}


}
