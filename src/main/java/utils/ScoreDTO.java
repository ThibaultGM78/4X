package utils;

public class ScoreDTO {
    private int id;
    private int idGame;
    private int idUser;
    private int idPlayer;
    private int nCombatWin;
    private int nCityWin;
    private int score;

    // Constructeurs
    public ScoreDTO() {
    }

    public ScoreDTO(int id, int idUser, int idPlayer, int nCombatWin, int nCityWin, int score) {
        this.id = id;
        this.idUser = idUser;
        this.idPlayer = idPlayer;
        this.nCombatWin = nCombatWin;
        this.nCityWin = nCityWin;
        this.score = score;
    }

    // Getters et Setters
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

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public int getNCombatWin() {
        return nCombatWin;
    }

    public void setNCombatWin(int nCombatWin) {
        this.nCombatWin = nCombatWin;
    }

    public int getNCityWin() {
        return nCityWin;
    }

    public void setNCityWin(int nCityWin) {
        this.nCityWin = nCityWin;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    

    public int getIdGame() {
		return idGame;
	}

	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}

	// Méthode toString
    @Override
    public String toString() {
        return "ScoreDTO{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", idPlayer=" + idPlayer +
                ", nCombatWin=" + nCombatWin +
                ", nCityWin=" + nCityWin +
                ", score=" + score +
                '}';
    }


}
