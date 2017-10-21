package client;

import java.util.ArrayList;

import javax.swing.JPanel;

public class leaderboardPanel extends JPanel{
	
	private ArrayList <scorePair> highscores;
	
	public leaderboardPanel(){
		
	}
	
	class scorePair{
		private String name;
		private int score;
		
		public scorePair(String name, int score){
			this.name = name;
			this.score = score;
		}
		
		public int getScore(){
			return score;
		}
		//end scorepair
	}
	
///// end leaderboard
}
