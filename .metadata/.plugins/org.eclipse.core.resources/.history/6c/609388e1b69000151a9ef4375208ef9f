package server;

import java.net.ServerSocket;

public class Server {
	
	private ServerSocket ss;
	private ServerGUI sgui;
	private static ServerListener sl;
	
	
	public Server() {
		ServerGUI sgui = new ServerGUI();
		ss = sgui.getServerSocket();
		listenForConnections();
	}
	
	private void listenForConnections() {
		sl = new ServerListener(ss);
		sl.start();
	}
	
	public static void main(String [] args) {
		new Server();
	}
}
