package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import game.FileManager;
import game.User;
;
public class LeaderboardGUI extends JPanel{

	
	private Font font;
	
	private JLabel titleLabel;
	private JTable topScoresTable;
	private JScrollPane jsp;
	Vector<String> topScores = new Vector<String>();
	private FileManager fileManager;
	private JButton backButton;
	private User u;
	//private ArrayList<String> topUsers = new ArrayList<String>();
	//private ArrayList<Integer> topScores = new ArrayList<Integer>();
	
	LeaderboardGUI(User u){
		this.u = u;
		try {
		      InputStream in = new BufferedInputStream(new FileInputStream("fonts/alpha_echo.ttf"));
		      Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, in);
		      font = ttfBase.deriveFont(Font.BOLD, 32);
		
		} catch (FontFormatException e) {
		        e.printStackTrace();
		} catch (IOException e) {
		        e.printStackTrace();
		}
		
		initializeVariables();
		createGUI();
		addActionListeners();
	}
	
	public void initializeVariables(){
		fileManager = new FileManager();
		titleLabel = new JLabel("\n\nHigh Scores\n\n");
		titleLabel.setFont(font);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		topScores = fileManager.highScores();
		System.out.println(topScores);
		backButton = new JButton("Back");
		backButton.setFont(font);
	}
	
	public void createGUI(){
		setLayout(new BorderLayout());
		setSize(Constants.welcomeGUIheight, Constants.welcomeGUIwidth);
		
		String[] columnNames = {"#", "Username", "Score"};
		String[][] data = new String[5][3];
		for (int r = 0; r < data.length; r++) {
				for (int c = 0; c < data[r].length; c++) {
					String toAdd = "";
					String[] args = topScores.get(r).split(" ");
					if (c == 0) { toAdd = " " + Integer.toString(r+1); }
					else if (c == 1) { toAdd = args[0]; }
					else { toAdd = args[1]; }
					data[r][c] = toAdd;
				}
		}
		topScoresTable = new JTable(data, columnNames);
		topScoresTable.setFont(font);
		topScoresTable.setPreferredSize(new Dimension(550,300));
		topScoresTable.getColumnModel().getColumn(0).setPreferredWidth(75);
		topScoresTable.getColumnModel().getColumn(1).setPreferredWidth(375);
		topScoresTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		topScoresTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		topScoresTable.setRowHeight(50);
		jsp = new JScrollPane(topScoresTable);
		jsp.setPreferredSize(new Dimension(560,310));
		JPanel jp = new JPanel(new FlowLayout());
		jp.add(jsp);
		JPanel backButtonPanel = new JPanel();
		backButtonPanel.add(backButton);
		add(titleLabel, BorderLayout.NORTH);
		add(jp, BorderLayout.CENTER);
		add(backButtonPanel, BorderLayout.SOUTH);
	}
	
	public void addActionListeners(){
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MainMenuGUI mmgui = new MainMenuGUI(u);
				DDR.cardPanel.add(mmgui);
				mmgui.setVisible(true);
				setVisible(false);
			}
			
		});
	}
}
