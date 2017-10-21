package game;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class Song {
	String songName;
	int length;
	
	public Song(String song) {
		songName = song;
		if (song.equals("Watch Me (Whip)")) {
			length = 186;
		}
		else if (song.equals("Uptown Funk")) {
			length = 186;
		}
		else if (song.equals("Trap Queen")) {
			length = 222;
		}
		else if (song.equals("Set Fire To The Rain")) {
			length = 242;
		}
		else if (song.equals("No Scrubs")) {
			length = 214;
		}
		else if (song.equals("Hotline Bling")) {
			length = 270;
		}
		else if (song.equals("All I Want For Christmas Is You")) {
			length = 241;
		}
		else if (song.equals("Party In The USA")) {
			length = 203;
		}
		else if (song.equals("It's Tricky")) {
			length = 284;
		}
	}
	
	public int getLength() {
		return length;
	}
	
	public String getName() {
		return songName;
	}
	
}
