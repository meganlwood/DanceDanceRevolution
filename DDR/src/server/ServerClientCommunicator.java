package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import game.FileManager;
import game.User;


public class ServerClientCommunicator extends Thread {

	protected Socket socket;
	private ObjectOutputStream oos;
	private BufferedReader br;
	private ServerListener serverListener;
	private PrintWriter pw;
	protected FileManager fileManager;
	private boolean running = true;
	
	public ServerClientCommunicator(Socket s, ServerListener sl) throws IOException {
		socket = s;
		serverListener = sl;
		oos = new ObjectOutputStream(socket.getOutputStream());
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		pw = new PrintWriter(socket.getOutputStream());
		fileManager = new FileManager();
	}
	
	private void sendMessage(String message) {
		pw.println(message);
		pw.flush();
		System.out.println("sending msg: " + message);
	}
	
	public void reqGame(String r, String s)
	{
		sendMessage("MULTI_REQUEST/" + r + "/" + s + "/");
	}
	
	public void startMGame(String oppSocket, String song)
	{
		sendMessage("START_MULTI_GAME/" + song + "/" + oppSocket + "/");
	}
	
	public void sendScore(String score) 
	{
		sendMessage("SEND_SCORE_2_PLAYER/" + score + "/");
	}
	
	public void run() {
		while(running) {
			try {
				String line = br.readLine();
				if (line != null){
					ServerGUI.addMessage(socket.getInetAddress() + ":" + socket.getPort() + " - " + line);
					String[] args = line.split("/");
					
					if (line.startsWith("AUTHENTICATE")) {
						if (fileManager.userLogin(args[1], args[2]) != null) { 
							String toSend = fileManager.userLoginString(args[1], args[2]);
							sendMessage("LOGINSUCCESS/");
							sendMessage(toSend);
							//Adds the online user to the list of online users
							serverListener.addUser(socket.getInetAddress()+":"+socket.getPort(), args[1]);
							ServerGUI.addMessage("UserID: " + socket.getInetAddress() + ":" + socket.getPort() + " has loggeed in as " + args[1]);
						} else { sendMessage("LOGINFAIL/"); }
					}
					
					else if (line.startsWith("NEW_USER")) {
						if (fileManager.userExists(args[1]) != true) {
							fileManager.addUser(args[1], args[2]);
							sendMessage("USER_MADE/");
						} else { sendMessage("USER_EXISTS/"); }
					}
					
					else if (line.startsWith("LOG_OUT")) {
						ServerGUI.addMessage(args[1] + " has logged out (" +socket.getInetAddress() + ":" + socket.getPort() + ")");
						serverListener.removeUser(socket.getInetAddress()+":"+socket.getPort());
					}
					
					else if(line.startsWith("MULTI_USERS")) {
						String s = serverListener.getUsernames(args[1]);
						sendMessage("MULTI_USERS/" + s);
					}
					else if(line.startsWith("MULTI_REQ_HOST")) {
						System.out.println("Sending");
						System.out.println(args[3]);
						System.out.println(args[1]);
						System.out.println(args[2]);
						serverListener.ask(args[3], args[1], args[2]);
					}
					else if(line.startsWith("GAME_ACCEPTED")) {
						serverListener.startGame(args[1], args[2], args[3]);
					}
					else if(line.startsWith("UPDATE_OPP_SCORE")) {
						serverListener.sendScore(args[1], args[2]);
					}
					else if (line.startsWith("UPDATE_SCORE")) {
						fileManager.changeHighScore(args[1], Integer.parseInt(args[2]));
					}
					else if (line.startsWith("ADD_GAME")) {
						fileManager.changeNumGames(args[1], Integer.parseInt(args[2]));
					}
					else if (line.startsWith("UPDATE_LAST_SONG")) {
						fileManager.changelastSong(args[1], args[2]);
					}
					
				}
				else{
					ServerGUI.addMessage(socket.getInetAddress() + ":" + socket.getPort() + " - " + "User has disconnected");
					serverListener.removeUser(socket.getInetAddress()+":"+socket.getPort());
					serverListener.removeServerClientCommunicator(this);
					running = false;
					try {
						socket.close();
						oos.close();
						br.close();
						
					} catch (IOException ioe1) {
						//Print
					}
				}
				}catch (IOException ioe) {
				serverListener.removeServerClientCommunicator(this);
				ServerGUI.addMessage(socket.getInetAddress() + ":" + socket.getPort() + " - " + "User has disconnected");
				
				try {
					socket.close();
				} catch (IOException ioe1) {
					//Print
				}
			}
		}
	}
}
