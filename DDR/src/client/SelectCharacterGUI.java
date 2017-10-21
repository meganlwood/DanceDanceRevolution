package client;

import java.awt.BorderLayout;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.User;

public class SelectCharacterGUI extends JPanel {
	private JLabel selectCharacterLabel;
	private JButton finishButton;
	private Font font, fontSmall;
	private String selectedCharacter;
	private JLabel drakeLabel;
	private JLabel dancinggirlLabel;
	private JLabel selectedCharacterLabel, mrbeanLabel;
	
	private Image backgroundImage = Toolkit.getDefaultToolkit().createImage("images/gameplaybackground.jpg");
	private User user;
	public SelectCharacterGUI(User user) {
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
		      fontSmall = ttfBase.deriveFont(Font.BOLD, 22);
		
		} catch (FontFormatException e) {
		        e.printStackTrace();
		} catch (IOException e) {
		        e.printStackTrace();
		}
		selectCharacterLabel = new JLabel("Select Your Character", SwingConstants.CENTER);
		selectCharacterLabel.setFont(font);
		selectCharacterLabel.setForeground(Color.WHITE);
		
		selectedCharacterLabel = new JLabel("Selected Character: ");
		selectedCharacterLabel.setFont(fontSmall);
		selectedCharacterLabel.setForeground(Color.WHITE);
		
		/*
		//radio button group to select character
		imageButtons = new ButtonGroup();
		bananaButton = new JRadioButton("Banana");
		pickleButton = new JRadioButton("Pickle");
		imageButtons.add(bananaButton);
		imageButtons.add(pickleButton);
		*/
		
		finishButton = new JButton("Finish");
		finishButton.setFont(font);
		
		drakeLabel = new JLabel();
		dancinggirlLabel = new JLabel();
		mrbeanLabel = new JLabel();
	}
	
	public void createGUI() {
		setLayout(new BorderLayout());
		
		add(selectCharacterLabel, BorderLayout.NORTH);
		
		//ImageIcon pickleGif = new ImageIcon("images/pickle.gif");
		//ImageIcon bananaGif = new ImageIcon("images/banana.gif");
		
		ImageIcon drakeIcon = new ImageIcon("images/drakehead.png");
		drakeLabel.setIcon(drakeIcon);
		ImageIcon girlIcon = new ImageIcon("images/dancinggirlicon.png");
		dancinggirlLabel.setIcon(girlIcon);
		ImageIcon mrbeanIcon = new ImageIcon("images/mrbeanhead.png");
		mrbeanLabel.setIcon(mrbeanIcon);
		
		JPanel imagePanel = new JPanel(new GridLayout(1, 3));
		//JLabel bananaLabel = new JLabel();
		//bananaLabel.setIcon(bananaGif);
		//JLabel pickleLabel = new JLabel();
		//pickleLabel.setIcon(pickleGif);
		//bananaLabel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 0));
		//pickleLabel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));
		imagePanel.add(drakeLabel);
		imagePanel.add(dancinggirlLabel);
		imagePanel.add(mrbeanLabel);
		
		imagePanel.setOpaque(false);
		
		add(imagePanel, BorderLayout.CENTER);
		add(selectedCharacterLabel, BorderLayout.SOUTH);
		
		JPanel buttonPanel = new JPanel(new GridLayout(2,1));
		//buttonPanel.add(bananaButton);
		//buttonPanel.add(pickleButton);
		buttonPanel.add(selectedCharacterLabel);
		buttonPanel.add(finishButton);
		buttonPanel.setOpaque(false);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public void addActionListeners() {
		finishButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedCharacter != null) {
					setVisible(false);
					user.setCharacter(selectedCharacter);
					
					MainMenuGUI mmgui = new MainMenuGUI(user);
					DDR.cardPanel.add(mmgui);
					mmgui.setVisible(true);
				}
			}
		});
		drakeLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println("clicked drake");
				selectedCharacter = "drake";
				selectedCharacterLabel.setText("Selected Character: Drake");
			}
		});
		dancinggirlLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				selectedCharacter = "dancinggirl";
				selectedCharacterLabel.setText("Selected Character: Weird Dancing Girl");
			}
		});
		mrbeanLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				selectedCharacter = "mrbean";
				selectedCharacterLabel.setText("Selected Character: Mr Bean");
			}
		});
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
	}

}
