package client;

import java.awt.BorderLayout;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WelcomePanel extends JPanel {
	private JLabel backgroundLabel;
	private JPanel buttonPanel;
	private JButton loginButton, createButton, quitButton;
	private Image backgroundImage;
	private Image buttonImage;
	private Font buttonFont;

	public WelcomePanel(){
		initializeVariables();
		createGUI();
		addActionAdapters();
	}

	private void initializeVariables() {
		//Creates the backdrop
		backgroundLabel = new JLabel(new ImageIcon("images/welcome.jpg"));
		backgroundImage = Toolkit.getDefaultToolkit().createImage("images/ddrbg.png");
		try {
		      InputStream in = new BufferedInputStream(new FileInputStream("fonts/alpha_echo.ttf"));
		      Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, in);
		      buttonFont = ttfBase.deriveFont(Font.BOLD, 18);
		
		} catch (FontFormatException e) {
		        e.printStackTrace();
		} catch (IOException e) {
		        e.printStackTrace();
		}

		//Creates the buttons for leaving the welcome screen
		loginButton = new JButton("Login");
		loginButton.setFont(buttonFont);
		createButton = new JButton("Create Account");
		createButton.setFont(buttonFont);
		quitButton = new JButton("Quit");
		quitButton.setFont(buttonFont);

		//Creates the panel to house the buttons and adds them to the panel
		buttonPanel = new JPanel(new GridLayout(1, 3));
		buttonPanel.add(loginButton);
		buttonPanel.add(createButton);
		buttonPanel.add(quitButton);
		buttonPanel.setOpaque(false);
	}

	private void createGUI() {
		//Sets the size of the welcome window
		setSize(Constants.welcomeGUIheight, Constants.welcomeGUIwidth);

		//Sets border layout
		setLayout(new BorderLayout());

		//Adds the buttons to the backdrop
		add(buttonPanel, BorderLayout.SOUTH);

	}

	private void addActionAdapters() {
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LoginGUI lgui = new LoginGUI();
				setVisible(false);
			}
		});
		
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		createButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
				CreateProfileGUI cpgui = new CreateProfileGUI();
				DDR.cardPanel.add(cpgui);
				cpgui.setVisible(true);	
			}
			
		});
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
}
