package server;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ServerGUI extends JFrame {
	
	public static final long serialVersionUID = 1;
	private static JTextArea textArea;
	private JScrollPane textAreaScrollPane;
	private Lock portLock;
	private Condition portCondition;
	private ServerSocket ss;
	
	public ServerGUI() {
		super("DDR Server");
		portLock = new ReentrantLock();
		portCondition = portLock.newCondition();
		
		int portNumber = -1;
		try {
			portNumber = 3456;
		} catch (Exception e) {
			System.out.println("Did not Connect");
			return;
		}
		if (portNumber > 0 && portNumber < 65535) {
			try {
				ServerSocket tempss = new ServerSocket(portNumber);
				portLock.lock();
				ss = tempss;
				portCondition.signal();
				portLock.unlock();
				System.out.println("Connected to " + portNumber);
				
			} catch (IOException ioe) {
				// this will get thrown if I can't bind to portNumber
				System.out.println("Port already in use");
			}
		}
		else {
			System.out.println("Did not Connect");
			return;
		}
		
		initializeVariables();
		createGUI();
		setVisible(true);
	}

	private void createGUI() {
		// TODO Auto-generated method stub
		setSize(600,600);
		add(textAreaScrollPane, BorderLayout.CENTER);
	}

	private void initializeVariables() {
		// TODO Auto-generated method stub
		textArea = new JTextArea();
		textArea.setEditable(false);
		textAreaScrollPane = new JScrollPane(textArea);
	}
	
	public static void addMessage(String msg) {
		if(textArea.getText() != null && textArea.getText().trim().length() > 0){
			textArea.append("\n" + msg);
		}
		else{
			textArea.setText(msg);
		}
	}
	
	public ServerSocket getServerSocket() {
		while (ss == null) {
			portLock.lock();
			try {
				portCondition.await();
			} catch (InterruptedException ie) {
				//Util.printExceptionToCommand(ie);
			}
			portLock.unlock();
		}
		return ss;
	}
}
