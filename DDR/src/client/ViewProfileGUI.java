package client;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.User;

public class ViewProfileGUI extends JPanel{
	//user name, number of games played, favorite song 
	//(most played song), 
	//character (shows image of character), and last song played
	//blabla
	
	private String username;
	private String character;
	private int numberGames;
	private int favoriteSong;
	private String lastSong;
	
//	private CharPanel characterPanel;

	private JLabel usernameLabel;
	private JLabel characterLabel;
	private JLabel numberGamesLabel;
	private JLabel favoriteSongLabel;
	private JLabel lastSongLabel;
	
	private JPanel backPanel;
	private JButton backButton;
	
	private Image backgroundImage;
	
	
	private Font font;
	
	private User user;
	
	
	public ViewProfileGUI(User user) {
		this.user = user;
		try {
		      InputStream in = new BufferedInputStream(new FileInputStream("fonts/alpha_echo.ttf"));
		      Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, in);
		      font = ttfBase.deriveFont(Font.BOLD, 32);
		
		} catch (FontFormatException e) {
		        e.printStackTrace();
		} catch (IOException e) {
		        e.printStackTrace();
		}
		//this.username = user.getName();
		
		//use calls from user passed in to get info
		username = user.getName();
		numberGames = user.getNumber();
		favoriteSong = user.getHighScore();
		lastSong = user.getLastSong();
		character = user.getCharacter();
		
		initializeVariables();
		createGUI();
		addActionListeners();
	}
	
	private void initializeVariables(){
		usernameLabel = new JLabel("Username : " + username);
		usernameLabel.setFont(font);
		
		System.out.println("character: " + user.getCharacter());
//		characterPanel = new CharPanel(user.getCharacter());
		
		characterLabel = new JLabel("");
		if(character.equals("dancinggirl")){
			characterLabel.setIcon(new ImageIcon("images/dancinggirlicon.png"));
		}
		else{
			characterLabel.setIcon(new ImageIcon("images/" + character + "head.png"));
		}
		
		numberGamesLabel = new JLabel("Games played: " + numberGames);
		numberGamesLabel.setFont(font);
		
		
		favoriteSongLabel = new JLabel("High Score: " + favoriteSong);
		favoriteSongLabel.setFont(font);
		
		lastSongLabel = new JLabel("Last Song: " + lastSong);
		lastSongLabel.setFont(font);
		
		backButton = new JButton("Back to Menu");
		backButton.setFont(font);
		
		backPanel = new JPanel(new FlowLayout());
		
		
	}
	
	private void createGUI(){
		setLayout(new GridLayout(6,1));
		setSize(Constants.welcomeGUIheight, Constants.welcomeGUIwidth);
		
		//add(characterPanel, SwingConstants.CENTER);
		
		add(usernameLabel);
		add(characterLabel);

		add(numberGamesLabel);

		add(favoriteSongLabel);

		add(lastSongLabel);
		
		backPanel.add(new JLabel(" "));
		backPanel.add(new JLabel(" "));
		backPanel.add(backButton);
		add(backPanel);
		
		
	}
	
	private void addActionListeners(){
	
		backButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
				MainMenuGUI mmgui = new MainMenuGUI(user);
				DDR.cardPanel.add(mmgui);
				mmgui.setVisible(true);
			}
			
		});
	}
	
//	class CharPanel extends JPanel{
//		private Image backgroundImage;
//		public CharPanel(String image){
//			backgroundImage = Toolkit.getDefaultToolkit().createImage(image);
//		}
//		
//		public void paintComponent(Graphics g) {
//	        super.paintComponent(g);
//	        if(backgroundImage != null){
//	        	g.drawImage(backgroundImage, 0, 0, this);
//	        }
//		}
//	}
//	
}
