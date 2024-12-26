package model;

public class Soldier {
	
	private final String imagePath = "images/icons/Small/soldier.png";
	private int idPlayerOwner;
	private int defensePoint;
	
	public Soldier(int idPlayerOwner) {
		this.idPlayerOwner = idPlayerOwner;
		this.defensePoint = 6;
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
}