package client;

import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Client {
	public static ClientServerCommunicator csc;
	
	public Client() throws IOException 
	{
		String ip = JOptionPane.showInputDialog("Enter Server IP Address");
		DDR wGUI = new DDR(ip);
		Socket s = wGUI.getSocket();
		csc = new ClientServerCommunicator(s);
	}
	
	public static void main(String[] args) throws IOException {
		Client clientDDR = new Client();
	}
}
