package JiaoGoBang.Client.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ReplayBoard extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 560;
	public static final int HEIGHT = 660;
	private ChessBoard chessBoard;

	ReplayBoard() {
		setTitle("JiaoGoBang");
		setSize(WIDTH, HEIGHT);
		setResizable(false); 
		chessBoard = new ChessBoard(this);
		Color color = new Color(245, 222, 179);
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(400, 625));
		panel.setBackground(color);
		panel.add(chessBoard, BorderLayout.NORTH);
		JPanel buttons = new JPanel();
		buttons.setPreferredSize(new Dimension(400, 325));
		Button pass = new Button("上一步", 1);
		Button withdraw = new Button("下一步", 1);
		Button giveUp = new Button("返回", 1);
		buttons.add(pass);
		buttons.add(withdraw);
		buttons.add(giveUp);
		buttons.setBackground(null);
		buttons.setOpaque(false);
		panel.add(buttons, BorderLayout.CENTER);
		add(panel, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		ReplayBoard game = new ReplayBoard();
		game.setVisible(true);
	}
}
