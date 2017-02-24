package pck;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.plaf.ActionMapUIResource;

import org.eclipse.swt.custom.StyledText;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class HistoricData implements Serializable {
	// contains keys and the list of matches with odds
	// example: key: BBQ Olivers vs Longzhu - LOL - LCK Spring Split Book Closes
	// 8 Feb 08:00
	// values: the matches with dates and odds
	private ListMultimap<String, TwoWayWinnerMatch> twoWayWinnerMatches;
	private ListMultimap<String, CorrectMapScoreMatch> correctMapScoreMatches;

	public HistoricData() {
		twoWayWinnerMatches = ArrayListMultimap.create();
		correctMapScoreMatches = ArrayListMultimap.create();
	}

	public void addTwoWayWinnerMatch(String key, TwoWayWinnerMatch match) {
	
		if (key != null && !key.isEmpty() && match != null && match.isCorrect()) {
			twoWayWinnerMatches.put(key, match);
		} else {
			System.err.println("Wanted to add incorrect match data");
			return;
		}
	}

	public void addCorrectMapScoreMatch(String key, CorrectMapScoreMatch match) {
		if (key != null && !key.isEmpty() && match != null && match.isCorrect()) {
			correctMapScoreMatches.put(key, match);	
		} else {
			System.err.println("Wanted to add incorrect match data");
			return;
		}
	}

	

	// we write out the matches to the given .txt
	public void printMatches(String fileName) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
		out.write("Two way matches of the historic data");
		out.newLine();
		out.newLine();
		Set<String> keySet = twoWayWinnerMatches.keySet();
		Iterator<String> keyIterator = keySet.iterator();
		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			out.newLine();
			out.write("\nDescription: " + key);
			out.newLine();
			List<TwoWayWinnerMatch> values = twoWayWinnerMatches.get(key);
			for (TwoWayWinnerMatch act : values) {
				out.write("Team1: " + act.getTeam1() + "  Odds: " + act.getOddsOn1_0() + "  Team 2: " + act.getTeam2()
						+ "  Odds: " + act.getOddsOn0_1());
				out.newLine();
			}
		}
		out.newLine();
		out.newLine();
		out.write("Correctmatchscore of the historic data");
		out.newLine();
		out.newLine();
		Set<String> keySet2 = correctMapScoreMatches.keySet();
		Iterator<String> keyIterator2 = keySet2.iterator();
		while (keyIterator2.hasNext()) {
			String key = (String) keyIterator2.next();
			out.newLine();
			out.write("Description: " + key);
			out.newLine();
			List<CorrectMapScoreMatch> values = correctMapScoreMatches.get(key);
			for (CorrectMapScoreMatch act : values) {
				out.write("Team1: " + act.getTeam1() + "  Team 2: " + act.getTeam2() + "  Odds 2-0: "
						+ act.getOddsOn2_0() + "  Odds 2-1: " + act.getOddsOn2_1() + "  Odds 0-2: " + act.getOddsOn0_2()
						+ "  Odds 1-2: " + act.getOddsOn1_2());
				out.newLine();
			}
		}
		if (out != null)
			out.close();
	}
	
	public void clearMatches(){
		twoWayWinnerMatches.clear();
		correctMapScoreMatches.clear();
	}

	public void searchArbitrage(double betAmount, StyledText console) {
		console.setText("");
		Set<String> keySet = twoWayWinnerMatches.keySet();
		Iterator<String> keyIterator = keySet.iterator();
		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			List<TwoWayWinnerMatch> matchesOfDifferentSites = twoWayWinnerMatches.get(key);
			
			if (Arbitrage.countArbitrageTwoWay(matchesOfDifferentSites, betAmount) != null) {
				// if there is arbitrage
				
				console.append("Arbitrage found @ TwoWayMatch! \n ");
				ArbitrageBetTwoWay[] bets = Arbitrage.countArbitrageTwoWay(matchesOfDifferentSites, betAmount);
				console.append(key+"\n");

				console.append(
						
						bets[0].getMatch().getBettingSite() + "	odds 1_0: " + 
						bets[0].getMatch().getOddsOn1_0()	+ "	" + bets[0].getBetAmount() + "\n"+
						bets[1].getMatch().getBettingSite() + "	odds 0_1: " +
						bets[1].getMatch().getOddsOn0_1()	+ " "+	bets[1].getBetAmount() + "\n");
			}
			else{
				console.append("Arbitrage not found @ TwoWayMatch! \n ");
			}
		}

		Set<String> keySet2 = correctMapScoreMatches.keySet();
		Iterator<String> keyIterator2 = keySet2.iterator();
		while (keyIterator2.hasNext()) {
			String key = (String) keyIterator2.next();
			List<CorrectMapScoreMatch> matchesOfDifferentSites = correctMapScoreMatches.get(key);
				// TODO if more sites are scraped, compare them
				if (Arbitrage.countArbitrageCorrectMapScore(matchesOfDifferentSites, betAmount) != null) {
					// if there is arbitrage
					console.append(
							"Arbitrage found @ CorrectMatchScore!\n");
					ArbitrageBetCorrectMapScore[] bets = Arbitrage
							.countArbitrageCorrectMapScore(matchesOfDifferentSites, betAmount);
					console.append(key+"\n");
					console.append(
							bets[0].getMatch().getBettingSite() + "  odds 2_0: " +bets[0].getMatch().getOddsOn2_0() +  "	" + bets[0].getBetAmount() +"\n" + 
							bets[1].getMatch().getBettingSite() + "  odds 2_1: " +bets[1].getMatch().getOddsOn2_1() +  "	" + bets[1].getBetAmount() + "\n" +
							bets[2].getMatch().getBettingSite() + "  odds 1_2: " +bets[2].getMatch().getOddsOn1_2() +  "	" + bets[2].getBetAmount() + "\n" +
							bets[3].getMatch().getBettingSite() + "  odds 0_2: " +bets[3].getMatch().getOddsOn0_2() +  "	" + bets[3].getBetAmount() + "\n");

				}
				else{

						console.append("Arbitrage not found @ CorrectMatchScoreMatch! \n ");
				}
			}
		}

	public void writeMatchesToConsole(StyledText console) {
		if(!twoWayWinnerMatches.isEmpty()){
		console.append("Two way matches of the historic data:");
		Set<String> keySet = twoWayWinnerMatches.keySet();
		Iterator<String> keyIterator = keySet.iterator();
		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			console.append("\n");
			List<TwoWayWinnerMatch> values = twoWayWinnerMatches.get(key);
			for (TwoWayWinnerMatch act : values) {
				console.append(act.getBettingSite()+"		" + act.getTeam1() + "		" + act.getOddsOn1_0() + "	vs	" + act.getTeam2()
						+ "		" + act.getOddsOn0_1());
				console.append("\n");
			}
		}
		}
		else{
			console.append("\n");
			console.append("Twowaymatches historic data is empty");
		}
		if(!correctMapScoreMatches.isEmpty()){
		console.append("\n");
		console.append("Correctmatchscore of the historic data");
		console.append("\n");
		Set<String> keySet2 = correctMapScoreMatches.keySet();
		Iterator<String> keyIterator2 = keySet2.iterator();
		while (keyIterator2.hasNext()) {
			String key = (String) keyIterator2.next();
			console.append("\n");
			List<CorrectMapScoreMatch> values = correctMapScoreMatches.get(key);
			for (CorrectMapScoreMatch act : values) {
				console.append(act.getBettingSite()+"		" + act.getTeam1() + "	vs	" + act.getTeam2() + "  Odds 2-0: "
						+ act.getOddsOn2_0() + "  Odds 2-1: " + act.getOddsOn2_1() + "  Odds 0-2: " + act.getOddsOn0_2()
						+ "  Odds 1-2: " + act.getOddsOn1_2());
				console.append("\n");
			}
		}
	}
		else {
			console.append("\n");
			console.append("Correctmatchscore historic data is empty");
		}
	}
}
