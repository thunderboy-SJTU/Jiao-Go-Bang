package JiaoGoBang.Client.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import JiaoGoBang.Common.Model.Chess;
import JiaoGoBang.Common.Model.ChessTable;
import JiaoGoBang.Common.Model.ReplayedChessTable;

public class ReplayBoard extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 560;
	public static final int HEIGHT = 690;
	private ChessBoard chessBoard;
	private ChessTable chessTable;
	private Button before;
	private Button next;
	private int count;

	public ReplayBoard() {
		count = 0;
		setTitle("JiaoGoBang");
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setLayout(new BorderLayout());
		chessBoard = new ChessBoard(this, 1);
		Color color = new Color(245, 222, 179);
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(400, 625));
		panel.setBackground(color);
		panel.add(chessBoard, BorderLayout.NORTH);
		JPanel buttons1 = new JPanel();
		JPanel buttons2 = new JPanel();
		buttons1.setPreferredSize(new Dimension(400, 40));
		buttons2.setPreferredSize(new Dimension(400, 40));
		Button load = new Button("导入", 1);
		LoadAction loadAction = new LoadAction();
		load.addActionListener(loadAction);
		before = new Button("上一步", 1);
		BeforeAction beforeAction = new BeforeAction();
		before.addActionListener(beforeAction);
		next = new Button("下一步", 1);
		NextAction nextAction = new NextAction();
		next.addActionListener(nextAction);
		Button returnBack = new Button("返回", 1);
		ReturnAction returnAction = new ReturnAction();
		returnBack.addActionListener(returnAction);
		buttons2.add(load);
		buttons2.add(returnBack);
		buttons1.add(before);
		buttons1.add(next);
		buttons1.setBackground(null);
		buttons1.setOpaque(false);
		buttons2.setBackground(null);
		buttons2.setOpaque(false);
		panel.add(buttons1, BorderLayout.CENTER);
		panel.add(buttons2, BorderLayout.SOUTH);
		add(panel, BorderLayout.CENTER);
		before.setEnabled(false);
		next.setEnabled(false);
	}

	public void readFile(String filename) {

		try {
			chessTable = new ReplayedChessTable(14, 14);
			File file = new File(filename);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file));
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				lineTxt = bufferedReader.readLine();
				((ReplayedChessTable) chessTable).setWhoWin(Integer.parseInt(lineTxt));
				while ((lineTxt = bufferedReader.readLine()) != null) {
					String[] txtChess = null;
					txtChess = lineTxt.split(" ");
					Chess chess = null;
					Color color = null;
					int chessColor = Integer.parseInt(txtChess[0]);
					if (chessColor == 0)
						color = Color.BLACK;
					else
						color = Color.WHITE;
					int x = Integer.parseInt(txtChess[1]);
					int y = Integer.parseInt(txtChess[2]);
					chess = new Chess(x, y, color);
					chessTable.addChess(chess);
				}
				read.close();
			}
		} catch (Exception e) {
			Icon icon = new ImageIcon("img/sign-error-icon.png");
			JOptionPane.showMessageDialog(null, "读取棋谱内容出错!", "ERROR", JOptionPane.PLAIN_MESSAGE, icon);
		}
	}

	private class LoadAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			File filepath = new File("file");
			jfc.setCurrentDirectory(filepath);
			jfc.showDialog(new JLabel(), "选择");
			File file = jfc.getSelectedFile();
			if (file.isFile()) {
				if(chessBoard.getChessTable().getChessCount()!= 0)
				{
					ChessTable newChessTable = new ChessTable(14,14);
					chessBoard.setChessTable(newChessTable);
					count = 0;
				}
				readFile(file.getAbsolutePath());
				if (chessTable != null && chessTable.getChessCount() != 0)
				{
					next.setEnabled(true);
					before.setEnabled(false);
				}
				repaint();
				return;
			}
		}
	}

	private class BeforeAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (count > 0) {
				count--;
				if(count == 0)
					before.setEnabled(false);
				chessBoard.getChessTable().removeChess();
				next.setEnabled(true);
				repaint();
			}
		}
	}

	private class NextAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (count < chessTable.getChessCount()) {
				count++;
				if(count == chessTable.getChessCount())
					next.setEnabled(false);
				Chess chess = chessTable.getChessList()[count - 1];
				chessBoard.getChessTable().addChess(chess);
				before.setEnabled(true);
				repaint();
			}
		}
	}


	private class ReturnAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(UI.getUser()!= null)
			{
				UI.getModeChoose().setVisible(true);
				UI.getReplayBoard().setVisible(false);
			}
			else
			{
				UI.getBegin().setVisible(true);
				UI.getReplayBoard().setVisible(false);
			}
		}
	}
	public static void main(String[] args) {
		ReplayBoard game = new ReplayBoard();
		game.setVisible(true);
	}
}
