package game;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class SongPlayer extends Thread {
	Song song;
	
	public SongPlayer(Song s) {
		song = s;
	}
	
	public void play() {
		start();
	}
	
	public void run() {
		try{
			File file = new File("resources/" + song.getName() + ".mp3");
			System.out.println("Name:" + song.getName() + ".");
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			Player player = new Player(bis);
			player.play();
		}
		catch(Exception ex)
		{
			System.out.println("Doesn't work");
		}
	}
	
	
}
