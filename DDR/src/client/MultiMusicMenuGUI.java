package client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import game.SinglePlayerGUI;
import game.User;

public class MultiMusicMenuGUI extends JPanel {
	private Font font, fontSmall;
	private JLabel selectSongLabel;
	private JList songList;
	private String[] songNames;
	volatile static String onlineUserNames = null;
	private JButton playButton;
	private User user;
	String selectedSong;
	volatile static boolean received = false;
	//Image backgroundImage = Toolkit.getDefaultToolkit().createImage("images/dancebg.jpg");
	public MultiMusicMenuGUI(User u) {
		user = u;
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
		
		String[] s = {"Hotline Bling", "No Scrubs", "Trap Queen", "All I Want For Christmas Is You", "Watch Me (Whip)", "Set Fire To The Rain", "Uptown Funk", "It's Tricky", "Party In The USA"};
		songNames = s;
		received = false;
		
	}
	
	public void createGUI() {
		setLayout(new BorderLayout());
		songList = new JList(songNames);
		songList.setFont(font);
		JScrollPane scrollPane = new JScrollPane(songList);
		selectSongLabel = new JLabel("Select A Song", SwingConstants.CENTER);
		selectSongLabel.setFont(font);
		playButton = new JButton("Next");
		playButton.setFont(font);
		add(selectSongLabel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(playButton, BorderLayout.SOUTH);
		
	}
	
	public static void received(boolean b) {
		received = b;
		System.out.println("Users got: " + received);
		System.out.println(onlineUserNames);
	}
	
	public void addActionListeners() {
		songList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				int sel = songList.getSelectedIndex();
                if( sel != -1 ){
                	selectedSong = songNames[sel];
                }
			}
			
		});
		
		playButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
				
				Client.csc.sendMessage("MULTI_USERS/" + user.getName() + "/");
				System.out.println("Checkpoint mmmgui2");
				while(!received);
				System.out.println("Checkpoint mmmgui1");
				if(received) {
					System.out.println("Checkpoint mmmgui");
					setVisible(false);
					MultiUserMenuGUI mumgui = new MultiUserMenuGUI(user, onlineUserNames, selectedSong);
					DDR.cardPanel.add(mumgui);
					mumgui.setVisible(true);
					
				}
			}
			
		});
	}
	
}
