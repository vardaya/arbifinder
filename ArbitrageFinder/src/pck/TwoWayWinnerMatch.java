package pck;

import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;

public class TwoWayWinnerMatch implements Serializable {
	//the site which provided the betting odds
	private String bettingSite;
	private String team1;
	private String team2;
	private double oddsOn1_0;
	private double oddsOn0_1;
	

	public TwoWayWinnerMatch(String team1, String team2, double oddsOnTeamOneWinner,
			double oddsOnTeamTwoWinner, String bettingSite) {
		super();
		this.team1 = team1;
		this.team2 = team2;
		this.oddsOn1_0 = oddsOnTeamOneWinner;
		this.oddsOn0_1 = oddsOnTeamTwoWinner;
		this.bettingSite = bettingSite;
	}

	public boolean isCorrect() {
		if (team1 != null && !team1.equals("") && team2 != null
				&& !team2.equals("") && oddsOn0_1 >= 1 && oddsOn1_0 >= 1) {
			return true;
		} else {
			return false;
		}
	}



	public String getTeam1() {
		return team1;
	}

	public void setTeam1(String team1) {
		this.team1 = team1;
	}

	public String getTeam2() {
		return team2;
	}

	public void setTeam2(String team2) {
		this.team2 = team2;
	}

	public double getOddsOn1_0() {
		return oddsOn1_0;
	}

	public void setOddsOn1_0(double oddsOn1_0) {
		this.oddsOn1_0 = oddsOn1_0;
	}

	public double getOddsOn0_1() {
		return oddsOn0_1;
	}

	public void setOddsOn0_1(double oddsOn0_1) {
		this.oddsOn0_1 = oddsOn0_1;
	}

	public String getBettingSite() {
		return bettingSite;
	}

	public void setBettingSite(String bettingSite) {
		this.bettingSite = bettingSite;
	}
	
}
