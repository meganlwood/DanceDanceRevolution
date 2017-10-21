package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javazoom.jl.player.Player;

public class DDR extends JFrame {
	
		public static JPanel cardPanel;
		private JLabel backgroundLabel;
		private JPanel buttonPanel;
		private JButton loginButton, createButton, quitButton;
		
		private Lock hostAndPortLock;
		private Condition hostAndPortCondition;
		private Socket socket;
		
		public DDR(String ip) throws IOException{
			super(Constants.welcomeGUITitle);
			setVisible(true);
			setSize(Constants.welcomeGUIheight, Constants.welcomeGUIwidth);

			initializeVariables();
			createGUI();
			
			setLocationRelativeTo(null);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			//playSong();
			repaint();
			revalidate();
			
			connectToServer(ip);
		}

		private void initializeVariables() {
			cardPanel = new JPanel(new CardLayout());
			WelcomePanel wp = new WelcomePanel();
			wp.setVisible(true);
			cardPanel.add(wp);
			add(cardPanel);
			socket = null;
			hostAndPortLock = new ReentrantLock();
			hostAndPortCondition = hostAndPortLock.newCondition();
			
		}
		
		private void createGUI() {
			//Sets the size of the welcome window
			setSize(Constants.welcomeGUIheight, Constants.welcomeGUIwidth);
			
			//Sets the pane to house the backdrop and sets it layout
			//setContentPane(backgroundLabel);
			setLayout(new BorderLayout());
			
			add(cardPanel);
			
		}
		
		private void playSong() {
			
			try{
				File file = new File("resources/Gemini Robot.mp3");
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

		public void connectToServer(String ip){
			int portInt = -1;
			try {
				portInt = 3456;
				System.out.println("Trying to Connect");
			} catch (Exception e) {
				return;
			}
			if (portInt > 0 && portInt < 35565) {
				// try to connect
				String hostnameStr = ip;
				try {
					socket = new Socket(hostnameStr, portInt);
					hostAndPortLock.lock();
					hostAndPortCondition.signal();
					hostAndPortLock.unlock();
					System.out.println("Connected to Server");
				} catch (IOException ioe) {
					System.out.println("Unable to Conenct");
					return;
				}
			}
			else { // port value out of range
				System.out.println("Port out of range");
				return;
			}
		}
		
		public Socket getSocket() {
			while (socket == null) {
				hostAndPortLock.lock();
				try {
					hostAndPortCondition.await();
				} catch (InterruptedException ie) {
				}
				hostAndPortLock.unlock();
			}
			return socket;
		}
}