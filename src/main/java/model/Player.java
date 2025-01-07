package model;

import utils.Constantes;

public class Player {
	private int id;
	private String name;
	private int idUser;
	private int gold;
	private int score;

	public Player(int id, String name) {
		this.id = id;
		this.name = name;
		this.gold = 0;
		this.score = 0;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	// Methode
	public void addGold(int g) {
		this.gold += g;
	}

	public void retireGold(int g) {
		this.gold -= g;
	}

	public void cityWin() {
		this.score += Constantes.SCORE_VALUE_CITY_WIN;
	}

	public void soldierWin() {
		this.score += Constantes.SCORE_VALUE_COMBAT_WIN;
	}

}