package client;

import java.io.*;
import java.net.Socket;

import javax.swing.JOptionPane;

import game.MultiPlayerGUI;
import game.User;

public class ClientServerCommunicator extends Thread{
	
	private Socket mSocket;
	private ObjectInputStream ois;
	private BufferedReader br;
	private PrintWriter pw;
	private String receiveMessage = null;
	private User cUser = null;
	public MultiPlayerGUI cgui = null;
	
	public ClientServerCommunicator(Socket s)
	{
		mSocket = s;
		boolean socketReady = initializeVariables();
		if (socketReady) {
			start();
		}
	}
	
	private boolean initializeVariables(){
		try {
			ois = new ObjectInputStream(mSocket.getInputStream());
			pw = new PrintWriter(mSocket.getOutputStream());
			br = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
		} catch (IOException ioe) {
			return false;
		} return true;
	}
	
	public void sendMessage(String msg) {
		System.out.println("Sending msg: " +msg);
		pw.println(msg);
		pw.flush();
	}
	
	public void updateScore(String u, int score) {
		if (cUser.getHighScore() < score)
		{
			sendMessage("UPDATE_SCORE/" + u + "/" + score + "/");
			cUser.updateScore(score);
		}
	}
	
	public void addGame(String u) {
		cUser.addGame();
		sendMessage("ADD_GAME/" + u + "/" + cUser.getNumber() + "/");
		cUser.addGame();
	}
	
	public void updateLast(String s) {
		sendMessage("UPDATE_LAST_SONG/" + cUser.getName() + "/" + s + "/");
		cUser.setLastSong(s);
	}
	
	public void run() {
		while(true) {
			try {
				System.out.println("running");
				String line = br.readLine();
				System.out.println("received: " + line);
				if (line != null) {
					String[] args = line.split("/");
					for (int i = 0; i < args.length; i++) {
						System.out.println(args[i]);
					}
					if (line.contains("LOGINSUCCESS")) {
						System.out.println("loginsuccess");
						String user = br.readLine();
						String[] words = user.split("/");
						cUser = new User(words[0], words[1], Integer.parseInt(words[2]), Integer.parseInt(words[3]), words[4], words[5]);
						LoginGUI.u = cUser;
						LoginGUI.login(true);
					}
					else if (line.contains("LOGINFAIL")) {
						System.out.println("loginfail");
						LoginGUI.login(false);
					}
					else if (line.contains("USER_MADE")) {
						System.out.println("newusermade");
						CreateProfileGUI.success = true;
						CreateProfileGUI.check = true;
					}
					else if (line.contains("USER_EXISTS")) {
						System.out.println("usernotmade");
						CreateProfileGUI.success = false;
						CreateProfileGUI.check = true;
					}
					else if (line.contains("MULTI_USERS")) {
						String users = line.substring(12);
						MultiMusicMenuGUI.onlineUserNames = users;
						MultiMusicMenuGUI.received = true;
					}
					else if(line.contains("MULTI_REQUEST")) {
						//Popup asking if the user would like to join
						int reply = JOptionPane.showConfirmDialog(DDR.cardPanel, args[1] + " wants to play " + args[2] + " with you!", "Game Request", JOptionPane.YES_NO_OPTION);;
						if (reply == JOptionPane.YES_OPTION) {
							//Accept game, go into game gui
							Client.csc.sendMessage("GAME_ACCEPTED/" + args[1] + "/" + cUser.getName() + "/" + args[2]);
						}
						else {
							//nothing idk
						}
						//multirequest, username, song
					}
					else if (line.contains("START_MULTI_GAME")) {
						//DDR.cardPanel.getTopLevelAncestor().setVisible(false);
						DDR.cardPanel.removeAll();
						cgui = new MultiPlayerGUI(cUser, args[1], args[3]);
						cgui.requestFocusInWindow();
						System.out.println("ARGS[1] " + args[3]);
						DDR.cardPanel.add(cgui);
						cgui.setVisible(true);
						DDR.cardPanel.revalidate();
						DDR.cardPanel.repaint();
					}
					else if (line.contains("SEND_SCORE_2_PLAYER")) { 
						cgui.updateOScore(Integer.parseInt(args[1]));
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void quit() throws IOException {
		mSocket.close();
		ois.close();
		br.close();
		pw.close();
		
	}

	public String receiveMessage() {
		return receiveMessage;
	}
}
