package pck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Arbitrage {
	
	
	// we receive x instance (depends on betting site) of the same match, and
	// the needed betAmount
	// return null: if there is no arbitrage. Return the list of
	// ArbitrageBetTwoways
	public static ArbitrageBetTwoWay[] countArbitrageTwoWay(List<TwoWayWinnerMatch> matchesOfDifferentSites,
			double betAmount) {

		ArbitrageBetTwoWay[] bestMatches = null;

		double maxOddsOn1_0 = 0;
		double maxOddsOn0_1 = 0;

		//search for biggest odds
		for (TwoWayWinnerMatch act : matchesOfDifferentSites) {
			if (act.getOddsOn1_0() > maxOddsOn1_0) 	maxOddsOn1_0 = act.getOddsOn1_0();
			if (act.getOddsOn0_1() > maxOddsOn0_1) 	maxOddsOn0_1 = act.getOddsOn0_1();
		}
		
		
		if ((((1 / maxOddsOn0_1) * 100) + ((1 / maxOddsOn1_0) * 100)) > 100) {
			// there is no arbitrage
			return null;
		} else {// there is arbitrage, we count the respective percentages,
				// betAmounts
			ArbitrageBetTwoWay biggestOdds1_0 = new ArbitrageBetTwoWay();
			ArbitrageBetTwoWay biggestOdds0_1 = new ArbitrageBetTwoWay();

			//TODO we cant decide which site we use if the odds is the same
			for (TwoWayWinnerMatch act : matchesOfDifferentSites) {
				if (act.getOddsOn1_0() == maxOddsOn1_0) {
					biggestOdds1_0.setMatch(act);
					biggestOdds1_0.setOddsType(0);
					biggestOdds1_0.setPercentage((1 / maxOddsOn1_0) * 100);
				}
				if (act.getOddsOn0_1() == maxOddsOn0_1) {
					biggestOdds0_1.setMatch(act);
					biggestOdds0_1.setOddsType(1);
					biggestOdds0_1.setPercentage((1 / maxOddsOn0_1) * 100);
				}
			}
			// we set the needed bet amounts
			double percentageSum = biggestOdds1_0.getPercentage() + biggestOdds0_1.getPercentage() ;
			
			biggestOdds1_0.setBetAmount((biggestOdds1_0.getPercentage() / percentageSum) * betAmount);
			biggestOdds0_1.setBetAmount((biggestOdds0_1.getPercentage() / percentageSum) * betAmount);
			
			
			bestMatches = new ArbitrageBetTwoWay[2];
			bestMatches[0] = biggestOdds1_0;
			bestMatches[1] = biggestOdds0_1;
		}
		return bestMatches;
	}
	
	public static ArbitrageBetCorrectMapScore[] countArbitrageCorrectMapScore(List<CorrectMapScoreMatch> matchesOfDifferentSites,
			double betAmount) {
		ArbitrageBetCorrectMapScore[] bestMatches = null;
	
		double maxOddsOn2_0 = 0;
		double maxOddsOn2_1 = 0;
		double maxOddsOn0_2 = 0;
		double maxOddsOn1_2 = 0;

		// search for biggest odds
		for (CorrectMapScoreMatch act : matchesOfDifferentSites) {
			if (act.getOddsOn2_0() > maxOddsOn2_0)	maxOddsOn2_0 = act.getOddsOn2_0();
			if (act.getOddsOn2_1() > maxOddsOn2_1)	maxOddsOn2_1 = act.getOddsOn2_1();
			if (act.getOddsOn1_2() > maxOddsOn1_2)	maxOddsOn1_2 = act.getOddsOn1_2();
			if (act.getOddsOn0_2() > maxOddsOn0_2)	maxOddsOn0_2 = act.getOddsOn0_2();
		}
		
		if(((((double)1.0/maxOddsOn2_0)+((double)1/maxOddsOn2_1)+((double)1/maxOddsOn1_2)+((double)1/maxOddsOn0_2))*100)>100){
			//there is no arbitrage
			return null;
		}
		else{//there is arbitrage
			ArbitrageBetCorrectMapScore biggestOddsOn2_0 = new ArbitrageBetCorrectMapScore();
			ArbitrageBetCorrectMapScore biggestOddsOn2_1 = new ArbitrageBetCorrectMapScore();
			ArbitrageBetCorrectMapScore biggestOddsOn1_2 = new ArbitrageBetCorrectMapScore();
			ArbitrageBetCorrectMapScore biggestOddsOn0_2 = new ArbitrageBetCorrectMapScore();
			
			for(CorrectMapScoreMatch act: matchesOfDifferentSites){
				if(act.getOddsOn2_0()==maxOddsOn2_0){
					biggestOddsOn2_0.setMatch(act);
					biggestOddsOn2_0.setOddsType(1);
					biggestOddsOn2_0.setPercentage((1.0f/maxOddsOn2_0)*100);
				}
				if (act.getOddsOn2_1() == maxOddsOn2_1) {
					biggestOddsOn2_1.setMatch(act);
					biggestOddsOn2_1.setOddsType(2);
					biggestOddsOn2_1.setPercentage((1/maxOddsOn2_1)*100);
				}
				if (act.getOddsOn1_2() == maxOddsOn1_2) {
					biggestOddsOn1_2.setMatch(act);
					biggestOddsOn1_2.setOddsType(3);
					biggestOddsOn1_2.setPercentage((1/maxOddsOn1_2)*100);
				}
				if (act.getOddsOn0_2() == maxOddsOn0_2) {
					biggestOddsOn0_2.setMatch(act);
					biggestOddsOn0_2.setOddsType(4);
					biggestOddsOn0_2.setPercentage((1/maxOddsOn0_2)*100);
				}
			}
			//we set the respective betamounts
			
			double percentageSum = 0;
			percentageSum = biggestOddsOn2_0.getPercentage()+biggestOddsOn2_1.getPercentage()+biggestOddsOn1_2.getPercentage()+biggestOddsOn0_2.getPercentage();
			
			biggestOddsOn2_0.setBetAmount((biggestOddsOn2_0.getPercentage() / percentageSum) * betAmount);
			biggestOddsOn2_1.setBetAmount((biggestOddsOn2_1.getPercentage() / percentageSum) * betAmount);
			biggestOddsOn1_2.setBetAmount((biggestOddsOn1_2.getPercentage() / percentageSum) * betAmount);
			biggestOddsOn0_2.setBetAmount((biggestOddsOn0_2.getPercentage() / percentageSum) * betAmount);
			
			bestMatches = new ArbitrageBetCorrectMapScore[4];
			bestMatches[0] = biggestOddsOn2_0;
			bestMatches[1] = biggestOddsOn2_1;
			bestMatches[2] = biggestOddsOn1_2;
			bestMatches[3] = biggestOddsOn0_2;
		}
		return bestMatches;
	}
}
