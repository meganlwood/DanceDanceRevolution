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
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import game.User;

public class LoginGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	JLabel usernameLabel, passwordLabel, titleLabel;
	static JTextField usernameField;
	static JPasswordField passwordField;
	JButton loginButton, continueAsGuestButton, backButton;
	JPanel titlePanel;
	JPanel buttonPanel;
	JPanel infoPanel;
	JPanel leftPanel,rightPanel;
	Image backgroundImage = Toolkit.getDefaultToolkit().createImage("images/dancebg.jpg");
	Font font, font2;
	Font fontSmall;
	LoginGUI logingui;
	volatile static boolean login = false;
	volatile static boolean check = false;
	volatile static User u = null;
	
	public LoginGUI() {
		setName("Login");
		initializeVariables();
		createGUI();
		addActionListeners();
		setVisible(true);
		logingui = this;
	}
	
	public void initializeVariables() {
		try {
		      InputStream in = new BufferedInputStream(new FileInputStream("fonts/alpha_echo.ttf"));
		      InputStream in2 = new BufferedInputStream(new FileInputStream("fonts/Arvo-Bold.ttf"));
		      Font ttfBase2 = Font.createFont(Font.TRUETYPE_FONT, in2);
		      Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, in);
		      font = ttfBase.deriveFont(Font.BOLD, 32);
		      fontSmall = ttfBase.deriveFont(Font.BOLD, 18);
		      font2 = ttfBase2.deriveFont(Font.BOLD, 18);
		
		} catch (FontFormatException e) {
		        e.printStackTrace();
		} catch (IOException e) {
		        e.printStackTrace();
		}
		
		titleLabel = new JLabel("Dance Dance Revolution");
		titleLabel.setFont(font);
		titleLabel.setForeground(Color.WHITE);
		usernameLabel = new JLabel("Username: ");
		usernameLabel.setForeground(Color.WHITE);
		usernameLabel.setFont(font);
		passwordLabel = new JLabel("Password: ");
		passwordLabel.setForeground(Color.WHITE);
		passwordLabel.setFont(font);
		usernameField = new JTextField();
		usernameField.setFont(font2);
		usernameField.setBackground(Color.BLACK);
		usernameField.setForeground(Color.WHITE);
		usernameField.setColumns(10);
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBackground(Color.BLACK);
		passwordField.setForeground(Color.WHITE);
		passwordField.setFont(fontSmall);
		passwordField.setEchoChar('*');
		loginButton = new JButton("Login");
		loginButton.setFont(fontSmall);
		backButton = new JButton("Back");
		backButton.setFont(fontSmall);
		continueAsGuestButton = new JButton("Continue as Guest");
		continueAsGuestButton.setFont(fontSmall);
		
		JPanel titlePanel = new JPanel();
		titlePanel.add(titleLabel);
		titlePanel.setOpaque(false);
		
		JPanel usernamePanel = new JPanel();
		//usernamePanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 100, 0));
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameField);
		usernamePanel.setOpaque(false);
		
		JPanel passwordPanel = new JPanel();
		passwordPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 0));
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		passwordPanel.setOpaque(false);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(loginButton);
		buttonPanel.add(continueAsGuestButton);
		buttonPanel.add(backButton);
		buttonPanel.setOpaque(false);
		
		add(titlePanel);
		add(usernamePanel);
		add(passwordPanel);
		add(buttonPanel);
		setOpaque(true);
		
		DDR.cardPanel.add(this);
	}
	
	public void createGUI() {
		setSize(Constants.welcomeGUIheight, Constants.welcomeGUIwidth);
		
		setLayout(new GridLayout(4, 0));
	}
	
	public static void login(boolean b) {
		login = b;
		System.out.println("login" + login);
		check = true;
		
	}
	public void addActionListeners() {
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				check = false;
				u = null;
				String name = usernameField.getText();
				String password = new String(passwordField.getPassword());
				
				//send user info to server to authenticate
				Client.csc.sendMessage("AUTHENTICATE/"+name+"/"+password);
				
				while(!check);
				if (login) {
					setVisible(false);
					MainMenuGUI mmgui = new MainMenuGUI(u);
					DDR.cardPanel.add(mmgui);
					mmgui.setVisible(true);	
					check = false;
					login = false;
				}
				else{
					//Pop up indicating that credentials were wrong
					//JOptionPane.showMessageDialog(logingui, "Come on man...");
					JOptionPane.showMessageDialog(logingui, "Incorrect Login", "Incorrect Login", JOptionPane.OK_OPTION, new ImageIcon("images/kim.jpg"));
					usernameField.setText("");
					passwordField.setText("");
				}
			}
			
		});
		
		continueAsGuestButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				User u = new User();
				MainMenuGUI mmgui = new MainMenuGUI(u);
				DDR.cardPanel.add(mmgui);
				mmgui.setVisible(true);
			}
		});
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//setVisible(false);
				DDR.cardPanel.remove(backButton.getParent().getParent());
			}
		});
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}

