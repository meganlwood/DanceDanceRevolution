package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

import game.SinglePlayerGUI;
import game.User;

public class MainMenuGUI extends JPanel {
	private Font font, fontSmall;
	private User user;
	JLabel mainMenuLabel;
	JButton leaderBoardButton, logoutButton, singlePlayerButton, multiPlayerButton, changeCharacterButton, viewProfileButton;
	public MainMenuGUI(User user) {
		this.user = user;
		initializeVariables();
		createGUI();
		addActionListeners();
	}
	
	public void initializeVariables() {
		try {
		      InputStream in = new BufferedInputStream(new FileInputStream("fonts/alpha_echo.ttf"));
		      Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, in);
		      font = ttfBase.deriveFont(Font.BOLD, 32);
		      fontSmall = ttfBase.deriveFont(Font.BOLD, 18);
		
		} catch (FontFormatException e) {
		        e.printStackTrace();
		} catch (IOException e) {
		        e.printStackTrace();
		}
		
		mainMenuLabel = new JLabel("Main Menu", SwingConstants.CENTER);
		mainMenuLabel.setFont(font);
		
		leaderBoardButton = new JButton("LeaderBoard");
		leaderBoardButton.setFont(font);
		//leaderBoardButton.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		
		logoutButton = new JButton("Logout");
		logoutButton.setFont(font);
		
		singlePlayerButton = new JButton("Single Player");
		singlePlayerButton.setFont(font);
		
		multiPlayerButton = new JButton("Multiplayer");
		multiPlayerButton.setFont(font);
		
		changeCharacterButton = new JButton("Change Character");
		changeCharacterButton.setFont(font);
		
		viewProfileButton = new JButton("View Profile");
		viewProfileButton.setFont(font);
		
		//limit user functionality
		if (user.isGuest()) {
			System.out.println("user is guest");
			multiPlayerButton.setEnabled(false);
			changeCharacterButton.setEnabled(false);
		}
	}
	
	public void createGUI() {
		setLayout(new GridLayout(7, 1));
		setSize(Constants.welcomeGUIheight, Constants.welcomeGUIwidth);
		
		add(mainMenuLabel);
		add(viewProfileButton);
		add(singlePlayerButton);
		add(multiPlayerButton);
		add(changeCharacterButton);
		add(leaderBoardButton);
		add(logoutButton);
	}
	
	public void addActionListeners() {
		
		leaderBoardButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LeaderboardGUI lbg = new LeaderboardGUI(user);
				DDR.cardPanel.add(lbg);
				lbg.setVisible(true);
				setVisible(false);
			}
			
		});
		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
				WelcomePanel wgui = new WelcomePanel();
				DDR.cardPanel.add(wgui);
				wgui.setVisible(true);
				Client.csc.sendMessage("LOG_OUT/"+user.getName()+"/");
			}
			
		});
		
		changeCharacterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
				SelectCharacterGUI sggui = new SelectCharacterGUI(user);
				DDR.cardPanel.add(sggui);
				sggui.setVisible(true);
				
			}
			
		});
		
		viewProfileButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
					setVisible(false);
					ViewProfileGUI vpgui = new ViewProfileGUI(user);
					DDR.cardPanel.add(vpgui);
					vpgui.setVisible(true);
			}
			
		});
		
		singlePlayerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				SinglePlayerGUI s = new SinglePlayerGUI(user);
				setVisible(false);
				WelcomeGUI.cardPanel.add(s);
				s.setVisible(true);
				s.requestFocusInWindow();
				*/
				
				MusicMenuGUI mmgui = new MusicMenuGUI(user);
				setVisible(false);
				DDR.cardPanel.add(mmgui);
				mmgui.setVisible(true);
			}
			
			
		});
		
		multiPlayerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MultiMusicMenuGUI mmmgui = new MultiMusicMenuGUI(user);
				setVisible(false);
				DDR.cardPanel.add(mmmgui);
				mmmgui.setVisible(true);
			}
		});
	}
}
