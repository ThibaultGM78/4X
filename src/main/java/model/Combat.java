package model;

public class Combat {
	
	private int defPt;
	private String atqPlayer;
	private String defPlayer;
	private int dice1;
	private int dice2;
	private int initDefLife;
	private int type;
	
	public Combat(int defPt, String pAtq, String pDef, int type) {
		this.defPt = defPt;
		this.atqPlayer= pAtq;
		this.defPlayer= pDef;
		this.type = type;
		this.initDefLife = defPt;

		this.dice1 = (int) (Math.random() * 6) + 1;
	    this.dice2 = (int) (Math.random() * 6) + 1;
	}

	public String getAtqPlayer() {
		return atqPlayer;
	}

	public void setAtqPlayer(String atqPlayer) {
		this.atqPlayer = atqPlayer;
	}

	public String getDefPlayer() {
		return defPlayer;
	}

	public void setDefPlayer(String defPlayer) {
		this.defPlayer = defPlayer;
	}

	public int getDice1() {
		return dice1;
	}

	public void setDice1(int dice1) {
		this.dice1 = dice1;
	}

	public int getDice2() {
		return dice2;
	}

	public void setDice2(int dice2) {
		this.dice2 = dice2;
	}

	public int getInitDefLife() {
		return initDefLife;
	}

	public void setInitDefLife(int initDefLife) {
		this.initDefLife = initDefLife;
	}

	public int getDefPt() {
		return defPt;
	}

	public void setDefPt(int defPt) {
		this.defPt = defPt;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	//Methode
	public boolean isVictory() {
		return this.getDefLifeAfterCombat() == 0;
	}
	
	public int getDefLifeAfterCombat() {
		int d = this.initDefLife - this.dice1 - this.dice2;
		return d <= 0 ? 0 : d;
	}
	
	
}