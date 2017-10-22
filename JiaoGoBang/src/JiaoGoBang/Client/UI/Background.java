package JiaoGoBang.Client.UI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class Background extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Image background;
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 625;

	public Background() {
		background = Toolkit.getDefaultToolkit().getImage("src/background.jpg");
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
	}

	public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, WIDTH,HEIGHT, null);
		MediaTracker t = new MediaTracker(this);
		t.addImage(background, 0);
		try {
			t.waitForAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}
