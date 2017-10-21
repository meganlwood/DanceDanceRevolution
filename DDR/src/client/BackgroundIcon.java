package client;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Icon;

public class BackgroundIcon implements Icon {
	Image i;
	public BackgroundIcon() {
		i = Toolkit.getDefaultToolkit().getImage("Images/grey_button00.png");
	}
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.drawImage(i, 0, 0, c.getWidth(), c.getHeight(), null);
		
	}
	@Override
	public int getIconWidth() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getIconHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
