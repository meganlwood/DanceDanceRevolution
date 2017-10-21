package game;

import java.awt.Image;
import java.io.Serializable;
import java.util.Hashtable;

public class User implements Serializable{
	private String name;
	private String lastSong;
	private int numGamesPlayed;
	private int highScore;
//	private Image characterImage;
	private String character;
	private String password;
	private Hashtable<String, Integer> songHistory;
	
	//new user
	public User(String name, String password){
		this.name = name;
		this.password = password;
//		songHistory = new Hashtable<String, Integer>();
		lastSong = "";
		numGamesPlayed = 0;
		highScore = 0;
		character = "";
	}
	
	//new user, via log in
	public User(String name, String password, int highScore, int numGamesPlayed, String lastSong, String character){
			this.name = name;
			this.password = password;
			this.lastSong = lastSong;
			this.numGamesPlayed = numGamesPlayed;
			this.highScore = highScore;
			this.character = character;
		}
	
	//guest user
	public User() {
		name = "Guest";
		password = "";
		lastSong = "";
		numGamesPlayed = 0;
		highScore = 0;
		character = "dancinggirl";
		
	}

	
	public String getName(){
		return name;
	}
	public String getLastSong(){
		return lastSong;
	}
//	public String getFavoriteSong(){
//		return favoriteSong;
//	}
	public void setCharacter(String s){
		character = s;
	}
	
	public int getHighScore(){
		return highScore;
	}
	
	public String getCharacter(){
		return character;
	}
	public void setLastSong(String s){
		lastSong = s;
		/*if(songHistory.containsKey(s)){
			Integer n = new Integer(songHistory.get(s) + 1);
			//songHistory.put(s,n)?
		}
		else{
			songHistory.put(s,  1);
		}*/
	}
	public boolean isGuest() {
		return (password.length() == 0);
	}
	public int getNumber(){
		return numGamesPlayed;
	}
	public void addGame() {
		numGamesPlayed++;
	}
	public void updateScore(int x){
		if (x > highScore)
			highScore = x;
	}
		
}
