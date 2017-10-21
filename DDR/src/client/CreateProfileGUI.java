package client;

import java.awt.Color;
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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import game.User;

public class CreateProfileGUI extends JPanel {
	JLabel usernameLabel, passwordLabel, titleLabel;
	JTextField usernameField;
	JPasswordField passwordField;
	JButton nextButton, backButton;
	JPanel buttonPanel;
	JPanel infoPanel;
	JPanel leftPanel,rightPanel, titlePanel;
	Image backgroundImage = Toolkit.getDefaultToolkit().createImage("images/dancebg.jpg");
	Font font, fontSmall;
	volatile static boolean success = false;
	volatile static boolean check = false;
	
	public CreateProfileGUI() {
		initializeVariables();
		createGUI();
		addActionListeners();
	}
	
	public void initializeVariables() {
		try {
		      InputStream in = new BufferedInputStream(new FileInputStream("fonts/Arvo-Bold.ttf"));
		      Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, in);
		      font = ttfBase.deriveFont(Font.BOLD, 32);
		      fontSmall = ttfBase.deriveFont(Font.BOLD, 18);
		
		} catch (FontFormatException e) {
		        e.printStackTrace();
		} catch (IOException e) {
		        e.printStackTrace();
		}
		
		titleLabel = new JLabel("Dance Dance Revolution");
		titleLabel.setFont(font);
		titleLabel.setForeground(Color.WHITE);
		
		usernameLabel = new JLabel("Username: ");
		usernameLabel.setFont(font);
		usernameLabel.setForeground(Color.WHITE);
		
		passwordLabel = new JLabel("Password: ");
		passwordLabel.setFont(font);
		passwordLabel.setForeground(Color.WHITE);
		
		usernameField = new JTextField();
		usernameField.setBackground(Color.BLACK);
		usernameField.setForeground(Color.WHITE);
		usernameField.setFont(fontSmall);
		usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBackground(Color.BLACK);
		passwordField.setForeground(Color.WHITE);
		passwordField.setFont(fontSmall);
		passwordField.setEchoChar('*');
		
		nextButton = new JButton("Next");
		nextButton.setFont(font);
		
		backButton = new JButton("Back");
		backButton.setFont(font);
		
		JPanel titlePanel = new JPanel();
		titlePanel.add(titleLabel);
		titlePanel.setOpaque(false);
		
		JPanel usernamePanel = new JPanel();
		//usernamePanel.setBorder(BorderFactory.createEmptyBorder(-30, 0, 0, 0));
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameField);
		usernamePanel.setOpaque(false);
		
		JPanel passwordPanel = new JPanel();
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		passwordPanel.setOpaque(false);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(nextButton);
		buttonPanel.add(backButton);
		buttonPanel.setOpaque(false);
		
		add(titlePanel);
		add(usernamePanel);
		add(passwordPanel);
		add(buttonPanel);
	}
	
	public void createGUI() {
		setSize(Constants.welcomeGUIheight, Constants.welcomeGUIwidth);
		
		setLayout(new GridLayout(4, 1));
	}
	
	public void addActionListeners() {
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String password = new String(passwordField.getPassword());
				String username = usernameField.getText();
				//check if all fields have text & user/pwd does not already exist
				if (username != null && password != null) {
					//TODO AUTHENTICATION WITH SERVER
					Client.csc.sendMessage("NEW_USER/"+username+"/"+password);
					
					while(!check);
					if(success){
						User user = new User(usernameField.getText(), password);
						setVisible(false);
						SelectCharacterGUI sgui = new SelectCharacterGUI(user);
						DDR.cardPanel.add(sgui);
						sgui.setVisible(true);
					}
					else{
						//Pop up
						usernameField.setText("");
						passwordField.setText("");
					}
				}
			}
		});
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DDR.cardPanel.remove(backButton.getParent().getParent());
			}
		});
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}

