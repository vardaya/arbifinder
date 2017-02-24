package pck;

public class ArbitrageBetCorrectMapScore {
	//match,  of odds on  and respective betAmounttype 
	private CorrectMapScoreMatch match;
	//1: 2-0, 2: 2-1, 3: 1-2, 4: 0-2 
	private int oddsType;
	private double percentage;
	private double betAmount;
	public ArbitrageBetCorrectMapScore() {
		super();
	}
	public CorrectMapScoreMatch getMatch() {
		return match;
	}
	public void setMatch(CorrectMapScoreMatch match) {
		this.match = match;
	}
	public int getOddsType() {
		return oddsType;
	}
	public void setOddsType(int oddsType) {
		this.oddsType = oddsType;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	public double getBetAmount() {
		return betAmount;
	}
	public void setBetAmount(double betAmount) {
		this.betAmount = betAmount;
	}
	
	
}
