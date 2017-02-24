package pck;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Label;

public class League implements Serializable {
	private static final long serialVersionUID = 51531863218645L;

	private String tag;
	private ArrayList<String> teams;
	private ArrayList<String> betSites;

	// the historicdata contains each match (twoway, correctmapscore) for each
	// respective league
	private HistoricData historicData;

	public League(String tag) {
		historicData = new HistoricData();
		betSites = new ArrayList<>();
		betSites.add("Pinnaccle");
		betSites.add("bet365");
		betSites.add("BetWay");
		betSites.add("10Bet");
		betSites.add("Unikrn");
		// create leagues
		switch (tag) {
		case "EU":
			this.tag = tag;
			 this.teams = new ArrayList<>();
			 teams.add("Giants Gaming");
			 teams.add("Unicorns of Love");
			 teams.add("G2 Esports");
			 teams.add("H2K");
			 teams.add("Splyce");
			 teams.add("Misfits");
			 teams.add("Team Vitality");
			 teams.add("Fnatic");
			 teams.add("ROCCAT");
			 teams.add("Origen");
			break;
		case "LCK":
			this.tag = tag;
			 this.teams = new ArrayList<>();
			 teams.add("BBQ Olivers");
			 teams.add("Longzhu");
			 teams.add("Jin Air Green Wings");
			 teams.add("MVP");
			 teams.add("Kongdoo Monster");
			 teams.add("Afreeca Freecs");
			 teams.add("SK Telecom T1");
			 teams.add("Samsung Galaxy");
			 teams.add("ROX Tigers");
			 teams.add("KT Rolster");
			break;
		case "NA":
			this.tag = tag;
			 this.teams = new ArrayList<>();
			 teams.add("Immortals");
			 teams.add("Cloud9");
			 teams.add("Team Liquid");
			 teams.add("CLG");
			 teams.add("Echo Fox");
			 teams.add("Phoenix1");
			 teams.add("FlyQuest");
			 teams.add("Team SoloMid");
			 teams.add("EnVyUs");
			 teams.add("Team Dignitas");
			break;
		}
	}
	public void clearMatches(){
		historicData.clearMatches();
	}

	public void addTwoWayWinnerMatch(String description, String name1, String name2, Double odds1,
			Double odds2, String bettingSite) {
		TwoWayWinnerMatch match = new TwoWayWinnerMatch(name1, name2, odds1, odds2, bettingSite);
		historicData.addTwoWayWinnerMatch(description, match);
	}

	public void addCorrectMatchScoreMatch(String description, String name1, String name2,
			Double odds2_0, Double odds2_1, Double odds0_2, Double odds1_2, String bettingSite) {
		CorrectMapScoreMatch match = new CorrectMapScoreMatch(name1, name2, odds2_0, odds2_1, odds1_2,
				odds0_2, bettingSite);
		historicData.addCorrectMapScoreMatch(description, match);
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void printMatches(String fileName) throws IOException {
		historicData.printMatches(fileName);
	}


	public void writeToFile() throws FileNotFoundException, IOException {
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream("d:\\Eclipse\\Workspace\\ArbitrageFinder\\" + this.tag + ".ser"));
		oos.writeObject(this);
		oos.close();
		System.out.println("League serialized");
	}

	public void searchArbitrage(double betAmount, StyledText console) {
		historicData.searchArbitrage(betAmount, console);
	}

	public ArrayList<String> getTeams() {
		return teams;
	}

	public void setTeams(ArrayList<String> teams) {
		this.teams = teams;
	}

	public HistoricData getHistoricData() {
		return historicData;
	}

	public void setHistoricData(HistoricData historicData) {
		this.historicData = historicData;
	}

	public ArrayList<String> getBetSites() {
		return betSites;
	}

	public void setBetSites(ArrayList<String> betSites) {
		this.betSites = betSites;
	}
	public void writeMatchesToConsole(StyledText console) {
		historicData.writeMatchesToConsole(console);
		
		
	}
	
}
