package pck;

public class ArbitrageBetTwoWay {
	private TwoWayWinnerMatch match;
	//1: 1-0, 2: 0-1
	private int oddsType;
	private double percentage;
	private double betAmount;
	
	public ArbitrageBetTwoWay() {
		super();
	}

	public ArbitrageBetTwoWay(TwoWayWinnerMatch match) {
		super();
		this.match = match;
	}

	public TwoWayWinnerMatch getMatch() {
		return match;
	}

	public void setMatch(TwoWayWinnerMatch match) {
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
