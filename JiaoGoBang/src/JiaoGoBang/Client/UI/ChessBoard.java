package JiaoGoBang.Client.UI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import JiaoGoBang.Client.Action.Send.Move;
import JiaoGoBang.Common.Model.Chess;
import JiaoGoBang.Common.Model.ChessTable;

public class ChessBoard extends JPanel implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int MARGIN = 30;
	public static final int GRID_SPAN = 35;
	public static final int ROWS = 14;
	public static final int COLS = 14;
	private ChessTable chessTable;
	private Image board;
	private int status;
	private JFrame jFrame;
	private int isReplayed;

	public ChessBoard(JFrame jFrame) {
		chessTable = new ChessTable(ROWS, COLS);
		status = 0;
		isReplayed = 0;
		this.jFrame = jFrame;
		board = Toolkit.getDefaultToolkit().getImage("img/board.jpg");
		addMouseListener(this);
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent event) {
				int x1 = (event.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
				int y1 = (event.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
				if (x1 < 0 || x1 > ROWS || y1 < 0 || y1 > COLS)
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				else
					setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseDragged(MouseEvent event) {

			}
		});
		setPreferredSize(new Dimension(ROWS * GRID_SPAN + 2 * MARGIN, ROWS * GRID_SPAN + 2 * MARGIN));
	}

	public ChessBoard(JFrame jFrame, int isReplayed) {
		chessTable = new ChessTable(ROWS, COLS);
		status = 0;
		this.isReplayed = isReplayed;
		this.jFrame = jFrame;
		board = Toolkit.getDefaultToolkit().getImage("img/board.jpg");
		addMouseListener(this);
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent event) {
				int x1 = (event.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
				int y1 = (event.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
				if (x1 < 0 || x1 > ROWS || y1 < 0 || y1 > COLS)
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				else if(isReplayed == 0)
					setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseDragged(MouseEvent event) {

			}
		});
		setPreferredSize(new Dimension(ROWS * GRID_SPAN + 2 * MARGIN, ROWS * GRID_SPAN + 2 * MARGIN));
	}
	
	public void setChessTable(ChessTable chessTable)
	{
		this.chessTable = chessTable;
	}

	public void paintComponent(Graphics g) {
		g.drawImage(board, 0, 0, ROWS * GRID_SPAN + 2 * MARGIN, ROWS * GRID_SPAN + 2 * MARGIN, null);
		MediaTracker t = new MediaTracker(this);
		t.addImage(board, 0);
		try {
			t.waitForAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i <= ROWS; i++) {
			g.drawLine(MARGIN, MARGIN + i * GRID_SPAN, MARGIN + COLS * GRID_SPAN, MARGIN + i * GRID_SPAN);
		}
		for (int i = 0; i <= COLS; i++) {
			g.drawLine(MARGIN + i * GRID_SPAN, MARGIN, MARGIN + i * GRID_SPAN, MARGIN + ROWS * GRID_SPAN);
		}
		Ellipse2D point0 = new Ellipse2D.Float(MARGIN + 7 * GRID_SPAN - 6, MARGIN + 7 * GRID_SPAN - 6, 12, 12);
		Ellipse2D point1 = new Ellipse2D.Float(MARGIN + 3 * GRID_SPAN - 6, MARGIN + 3 * GRID_SPAN - 6, 12, 12);
		Ellipse2D point2 = new Ellipse2D.Float(MARGIN + 11 * GRID_SPAN - 6, MARGIN + 3 * GRID_SPAN - 6, 12, 12);
		Ellipse2D point3 = new Ellipse2D.Float(MARGIN + 3 * GRID_SPAN - 6, MARGIN + 11 * GRID_SPAN - 6, 12, 12);
		Ellipse2D point4 = new Ellipse2D.Float(MARGIN + 11 * GRID_SPAN - 6, MARGIN + 11 * GRID_SPAN - 6, 12, 12);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
		((Graphics2D) g).setPaint(Color.BLACK);
		((Graphics2D) g).fill(point0);
		((Graphics2D) g).fill(point1);
		((Graphics2D) g).fill(point2);
		((Graphics2D) g).fill(point3);
		((Graphics2D) g).fill(point4);
		((Graphics2D) g).draw(point0);
		((Graphics2D) g).draw(point1);
		((Graphics2D) g).draw(point2);
		((Graphics2D) g).draw(point3);
		((Graphics2D) g).draw(point4);
		for (int i = 0; i < chessTable.getChessCount(); i++) {
			Chess chess = chessTable.getChessList()[i];
			int x = chess.getX() * GRID_SPAN + MARGIN;
			int y = chess.getY() * GRID_SPAN + MARGIN;
			Color color = chess.getColor();
			if (color.equals(Color.BLACK)) {
				RadialGradientPaint paint = new RadialGradientPaint(x - 15 + 25, y - 15 + 10, 20,
						new float[] { 0f, 1f }, new Color[] { Color.WHITE, Color.BLACK });
				((Graphics2D) g).setPaint(paint);
				((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
						RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);

			} else if (color.equals(Color.WHITE)) {
				RadialGradientPaint paint = new RadialGradientPaint(x - 15 + 25, y - 15 + 10, 70,
						new float[] { 0f, 1f }, new Color[] { Color.WHITE, Color.BLACK });
				((Graphics2D) g).setPaint(paint);
				((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
						RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
			}
			Ellipse2D e = new Ellipse2D.Float(x - 15, y - 15, 35, 35);
			((Graphics2D) g).fill(e);
			if (i == chessTable.getChessCount() - 1) {
				g.setColor(Color.RED);
				g.drawRect(x - 15, y - 15, 35, 35);
			}
		}

	}

	public void mousePressed(MouseEvent event) {
		if (isReplayed == 0) {
			if (status == 1) {
				int x = (event.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
				int y = (event.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
				if (x < 0 || x > ROWS || y < 0 || y > COLS)
					return;
				Chess chess = new Chess();
				chess.setX(x);
				chess.setY(y);
				chess.setColor(((GameBoard) jFrame).getColor());
				if (chessTable.findChess(chess)) {
					return;
				}
				chessTable.addChess(chess);
				UI.getGameBoard().setStatus(0);
				Move move = new Move(chess, ((GameBoard) jFrame).getMode());
				move.execute();
				repaint();
			}
		}
	}

	public void mouseClicked(MouseEvent event) {

	}

	public void mouseReleased(MouseEvent event) {

	}

	public void mouseEntered(MouseEvent event) {

	}

	public void mouseExited(MouseEvent event) {

	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ChessTable getChessTable() {
		return chessTable;
	}

	public void reset() {
		chessTable = new ChessTable(14, 14);
		status = 0;
	}

}
