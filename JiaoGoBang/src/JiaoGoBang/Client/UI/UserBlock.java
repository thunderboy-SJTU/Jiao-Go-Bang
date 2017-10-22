package JiaoGoBang.Client.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;

import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

public class UserBlock extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 110;
	private String user;
	private Color color;

	public UserBlock(String user) {
		this.user = user;
		this.color = null;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if(color!= null){
		if (color.equals(Color.BLACK)) {
			RadialGradientPaint paint = new RadialGradientPaint(85 - 15 + 25, 84 - 15 + 10, 20, new float[] { 0f, 1f },
					new Color[] { Color.WHITE, Color.BLACK });
			g2.setPaint(paint);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);

		} else if (color.equals(Color.WHITE)) {
			RadialGradientPaint paint = new RadialGradientPaint(85 - 15 + 25, 84 - 15 + 10, 70, new float[] { 0f, 1f },
					new Color[] { Color.WHITE, Color.BLACK });
			g2.setPaint(paint);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
		}
		Ellipse2D e = new Ellipse2D.Float(85 - 15, 84 - 15, 35, 35);
		g2.fill(e);
		}
		if(user != null)
		{
		Font f = new Font("Arial",Font.BOLD,24);
		g2.setFont(f);
		g2.setPaint(Color.DARK_GRAY);
		g2.drawString(user, 120, 100);
		}
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getUser() {
		return user;
	}

	public Color getColor() {
		return color;
	}
}
