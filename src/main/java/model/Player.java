package model;

public class Player{
	private int id;
	private String name;
	private int idUser;
	private int gold;
	
	public Player(int id, String name) {
		this.id = id;
		this.name = name;
		this.gold = 0;
	}
	
	public Player() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}
	
	//Methode
	public void addGold(int g) {
		this.gold += g;
	}
	
	public void retireGold(int g) {
		this.gold -= g;
	}
}