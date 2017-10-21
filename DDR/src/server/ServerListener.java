package server;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class ServerListener extends Thread {
	
	private ServerSocket ss;
	private Vector<ServerClientCommunicator> sccVector;
	protected Map<String, String> oUsers = new HashMap<String, String>(); 
	
	public ServerListener(ServerSocket ss){
		this.ss = ss;
		sccVector = new Vector<ServerClientCommunicator>();
	}
	
	public void removeServerClientCommunicator(ServerClientCommunicator scc) {
		sccVector.remove(scc);
		//Print to ServerGUI
	}
	
	public void addUser(String s, String u)
	{
		oUsers.put(s, u);
		ServerGUI.addMessage("Users online: " + oUsers.size());
	}
	public String getUsernames(String s)
	{
		String toReturn = "";
		Iterator it = oUsers.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			if((s.equals(pair.getValue()))){}
			else{
			toReturn = toReturn + pair.getValue() + "/";
			}
		}
		return toReturn;
	}
	
	public void removeUser(String s)
	{
		System.out.println(s);
		ServerGUI.addMessage(s);
		oUsers.remove(s);
		ServerGUI.addMessage("Users online: " + oUsers.size());
	}
	
	private String getSocket(String u)
	{
		String toReturn = "";
		Iterator it = oUsers.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			if((u.equals(pair.getValue()))){
				toReturn = (String) pair.getKey();
			}
		}
		//System.out.println(toReturn);
		return toReturn;
	}
	
	
	public ServerClientCommunicator findSCC(String u)
	{
		ServerClientCommunicator toReturn = null;
		
		String socket = getSocket(u);
		System.out.println("User socket: " + socket);
		
		for (int i = 0; i < sccVector.size(); i++)
		{
			System.out.println(i);
			Socket temp = sccVector.elementAt(i).socket;
			String check = (temp.getInetAddress() + ":"  + temp.getPort());
			
			System.out.println("Want: " + socket);
			System.out.println("Have " + check);
			
			if (socket.equals(check)) {
				System.out.println("Found");
				toReturn = sccVector.elementAt(i);
				break;
			}
		}
		
		return toReturn;
	}
	
	public ServerClientCommunicator findSCC1(String s)
	{
		ServerClientCommunicator toReturn = null;
		
		String socket = s;
		System.out.println("User socket: " + socket);
		
		for (int i = 0; i < sccVector.size(); i++)
		{
			System.out.println(i);
			Socket temp = sccVector.elementAt(i).socket;
			String check = (temp.getInetAddress() + ":"  + temp.getPort());
			
			System.out.println("Want1: " + socket);
			System.out.println("Have1 " + check);
			
			if (socket.equals(check)) {
				System.out.println("Found1");
				toReturn = sccVector.elementAt(i);
				break;
			}
		}
		
		return toReturn;
	}
	
	
	public void ask(String u, String r, String s)
	{
		ServerClientCommunicator req = findSCC(u);
		System.out.println(req);
		req.reqGame(r, s);
	}
	
	public void startGame(String u1, String u2, String s){
		
		System.out.println(u1);
		System.out.println(u2);
		
		String socket1 = getSocket(u1);
		String socket2 = getSocket(u2);
		
		System.out.println(socket1);
		System.out.println(socket2);
		
		ServerClientCommunicator scc1 = findSCC(u1);
		ServerClientCommunicator scc2 = findSCC(u2);
		
		scc1.startMGame(socket2, s);
		scc2.startMGame(socket1, s);
		
		ServerGUI.addMessage("Game should be starting right now");
		
	}
	
	public void sendScore(String socket, String score) {
		System.out.println("Socket: " + socket);
		ServerClientCommunicator scc1 = findSCC1("/" + socket);
		scc1.sendScore(score);
	}
	
	public void run() {
		try {
			ServerGUI.addMessage(Constants.initialFactoryTextAreaString + ss.getLocalPort());
			while(true) {
				Socket s = ss.accept();
				ServerGUI.addMessage(Constants.startClientConnectedString + s.getInetAddress() + ":" + s.getPort() + Constants.endClientConnectedString);
				
				try {
					// this line can throw an IOException
					// if it does, we won't start the thread
					ServerClientCommunicator scc = new ServerClientCommunicator(s, this);
					scc.start();
					sccVector.add(scc);
					
				} catch (IOException ioe) {
					//Util.printExceptionToCommand(ioe);
				}
			}
		} catch(BindException be) {
			//Util.printExceptionToCommand(be);
		}
		catch (IOException ioe) {
			//Util.printExceptionToCommand(ioe); 
		} finally {
			if (ss != null) {
				try {
					ss.close();
				} catch (IOException ioe) {
					//Util.printExceptionToCommand(ioe);
				}
			}
		}
	}
}