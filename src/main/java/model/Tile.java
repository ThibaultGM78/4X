package model;

import utils.Constantes;

public class Tile{
	//Var
	private int type;
	private String imagePath;
	private Soldier soldier;
	private int state;
	private boolean isSoldier;
	
	//Constructor
	public Tile (int type, String imagePath) {
		this.type = type;
		this.imagePath = imagePath;
		this.isSoldier = false;
	}

	//GetSet
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Soldier getSoldier() {
		return soldier;
	}

	public void setSoldier(Soldier soldier) {
		this.soldier = soldier;
		this.isSoldier = true;
		this.state = soldier.getIdPlayerOwner();
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	public boolean isSoldier() {
		return isSoldier;
	}

	public void setSoldier(boolean isSoldier) {
		this.isSoldier = isSoldier;
	}
	
	//Methode
	public String getOverlayImagePath() {
		return this.soldier != null ? this.soldier.getImagePath() : Constantes.IMAGE_NULL_PATH;
	}
	
	public String getCellCSSClass() {
		
		if(this.getType() == Constantes.TILE_TYPE_CITY || this.isSoldier) {
			return "cell cell" + String.valueOf(this.getState());
		}
	
		return "cell cell0";
	}


}