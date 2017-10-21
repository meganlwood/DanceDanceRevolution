package client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.ScrollPane;
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

public class MultiUserMenuGUI extends JPanel {
	private Font font, fontSmall;
	private JLabel selectOpLabel;
	private JList userList;
	private String[] userNames;
	private JButton playButton;
	private User user;
	String selectedSong, selectedUser;
	volatile static boolean ready;
	//Image backgroundImage = Toolkit.getDefaultToolkit().createImage("images/dancebg.jpg");
	public MultiUserMenuGUI(User u, String users, String song) {
		user = u;
		initializeVariables(users, song);
		createGUI();
		addActionListeners();
	}
	
	public void initializeVariables(String s, String song) {
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
		
		userNames = s.split("/");
		selectedSong = song;
	}
	
	public void createGUI() {
		setLayout(new BorderLayout());
		userList = new JList(userNames);
		userList.setFont(font);
		JScrollPane scrollPane = new JScrollPane(userList);
		selectOpLabel = new JLabel("Select An Opponent", SwingConstants.CENTER);
		selectOpLabel.setFont(font);
		playButton = new JButton("Request");
		playButton.setFont(font);
		add(selectOpLabel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(playButton, BorderLayout.SOUTH);
		
	}
	
	public void addActionListeners() {
		userList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				int sel = userList.getSelectedIndex();
                if( sel != -1 ){
                	selectedUser = userNames[sel];
                }
			}
			
		});
		
		playButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Client.csc.sendMessage("MULTI_REQ_HOST/" + user.getName() + "/" + selectedSong + "/" + selectedUser + "/");
				playButton.setEnabled(false);
				}
		});	
	}
}