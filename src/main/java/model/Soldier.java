package model;

import utils.Constantes;

public class Soldier {
	
	private final String imagePath = "images/icons/Small/soldier.png";
	private int idPlayerOwner;
	private int defensePoint;
	private int lastActionTurn;
	private int maxDefensePoint;
	
	public Soldier(int idPlayerOwner) {
		this.idPlayerOwner = idPlayerOwner;
		this.defensePoint = Constantes.SOLDIER_DEFENSE;
		this.maxDefensePoint = Constantes.SOLDIER_DEFENSE;
		this.lastActionTurn = -1;
	}

	public int getDefensePoint() {
		return defensePoint;
	}

	public void setDefensePoint(int defensePoint) {
		this.defensePoint = defensePoint;
	}

	public int getIdPlayerOwner() {
		return idPlayerOwner;
	}

	public void setIdPlayerOwner(int idPlayerOwner) {
		this.idPlayerOwner = idPlayerOwner;
	}

	public String getImagePath() {
		return imagePath;
	}

	public int getLastActionTurn() {
		return lastActionTurn;
	}

	public void setLastActionTurn(int lastActionTurn) {
		this.lastActionTurn = lastActionTurn;
	}
	
	public int getMaxDefensePoint() {
		return maxDefensePoint;
	}

	public void setMaxDefensePoint(int maxDefensePoint) {
		this.maxDefensePoint = maxDefensePoint;
	}

	//methode
	public void receiveDamage(int d) {
		this.defensePoint -= d;
		if(this.defensePoint < 0) {
			this.defensePoint = 0;
		}
	}
	
	public boolean isKo() {
		return this.defensePoint <= 0;
	}
	
	public boolean isBlessed() {
		return this.getDefensePoint() < this.getMaxDefensePoint();
	}
	
	public void receiveHeal(int h) {
		int h2 = this.getDefensePoint() + h;
		this.setDefensePoint(h2 > this.getMaxDefensePoint() ? this.getDefensePoint() : h2);
	}
}