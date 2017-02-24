package pck;

import java.io.Serializable;
import java.util.Date;

public class CorrectMapScoreMatch implements Serializable {
	
	private String bettingSite;
	public String getBettingSite() {
		return bettingSite;
	}

	public void setBettingSite(String bettingSite) {
		this.bettingSite = bettingSite;
	}

	private String team1;
	private String team2;
	private double oddsOn2_0;
	private double oddsOn2_1;
	private double oddsOn1_2;
	private double oddsOn0_2;

	public CorrectMapScoreMatch(String team1, String team2, double oddsOn2_0, double oddsOn2_1,
			double oddsOn1_2, double oddsOn0_2, String bettingSite) {
		super();
		
		this.team1 = team1;
		this.team2 = team2;
		this.oddsOn2_0 = oddsOn2_0;
		this.oddsOn2_1 = oddsOn2_1;
		this.oddsOn1_2 = oddsOn1_2;
		this.oddsOn0_2 = oddsOn0_2;
		this.bettingSite = bettingSite;
	}

	public boolean isCorrect() {
		if (team1 != null && !team1.equals("") && team2 != null
				&& !team2.equals("") && oddsOn2_0 >= 1 && oddsOn2_1 >= 1 && oddsOn1_2 >= 1 && oddsOn0_2 >= 1) {
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

	public double getOddsOn2_0() {
		return oddsOn2_0;
	}

	public void setOddsOn2_0(double oddsOn2_0) {
		this.oddsOn2_0 = oddsOn2_0;
	}

	public double getOddsOn2_1() {
		return oddsOn2_1;
	}

	public void setOddsOn2_1(double oddsOn2_1) {
		this.oddsOn2_1 = oddsOn2_1;
	}

	public double getOddsOn1_2() {
		return oddsOn1_2;
	}

	public void setOddsOn1_2(double oddsOn1_2) {
		this.oddsOn1_2 = oddsOn1_2;
	}

	public double getOddsOn0_2() {
		return oddsOn0_2;
	}

	public void setOddsOn0_2(double oddsOn0_2) {
		this.oddsOn0_2 = oddsOn0_2;
	}

}
