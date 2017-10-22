package JiaoGoBang.Common.Model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ChessTable {
	private Chess[] chessList;
	private int chessCount;
	private int rows;
	private int cols;

	public ChessTable(int rows, int cols) {
		chessList = new Chess[(rows + 1) * (cols + 1) + 1];
		this.rows = rows+1;
		this.cols = cols+1;
		chessCount = 0;
	}

	public Chess[] getChessList() {
		return chessList;
	}

	public int getChessCount() {
		return chessCount;
	}

	public boolean findChess(Chess chess) {
		for (int i = 0; i < chessCount; i++) {
			if (chess.getX() == chessList[i].getX() && chess.getY() == chessList[i].getY())
				return true;
		}
		return false;
	}

	public boolean findChess(int x, int y, List<Chess> chesses) {
		if (y < 0 || x < 0 || y > cols-1 || x > rows-1)
			return false;
		for (int i = 0; i < chesses.size(); i++) {
			if (chesses.get(i).getX() == x && chesses.get(i).getY() == y)
				return true;
		}
		return false;
	}

	public void addChess(Chess chess) {
		chessList[chessCount] = chess;
		chessCount++;
		return;
	}
	
	public void removeChess()
	{
		chessList[chessCount--] = null;
	}

	public void withdraw(Color color) {
		if (chessCount == 0)
			return;
		if (chessCount == 1) {
			chessCount--;
			chessList[0] = null;
			return;
		}
		if (chessList[chessCount - 1].getColor().equals(color)) {
			chessList[chessCount--] = null;
			return;
		} else {			
			if (chessList[chessCount - 2].getColor().equals(chessList[chessCount - 1].getColor()))
				chessList[chessCount--] = null;
			else
			{
				chessList[chessCount--] = null;
				chessList[chessCount--] = null;
			}
		}

	}
    
	public boolean isWin(Chess chess) {
		List<Chess> horizontal = new ArrayList<Chess>();
		List<Chess> vertical = new ArrayList<Chess>();
		List<Chess> leftSlant = new ArrayList<Chess>();
		List<Chess> rightSlant = new ArrayList<Chess>();
		int count = 1;
		int tmpCount = count;
		for (int i = 0; i < chessCount; i++) {
			Chess anoChess = chessList[i];
			if (!chess.isEqual(anoChess)) {
				if (chess.isHorizontal(anoChess)) {
					horizontal.add(anoChess);
					continue;
				}
				if (chess.isVertical(anoChess)) {
					vertical.add(anoChess);
					continue;
				}
				if (chess.isLeftSlant(anoChess)) {
					leftSlant.add(anoChess);
					continue;
				}
				if (chess.isRightSlant(anoChess))
					rightSlant.add(anoChess);
			}
		}

		int x = chess.getX();
		int y = chess.getY();
		// isHorizontal
		for (int i = 1; i < 5; i++) {
			boolean bool = findChess(x + i, y, horizontal);
			if (bool == false)
				break;
			count++;
			if (count == 5)
				return true;
		}
		tmpCount = count;
		for (int i = 1; i < 6 - tmpCount; i++) {
			boolean bool = findChess(x - i, y, horizontal);

			if (bool == false)
				break;
			count++;
			if (count == 5)
				return true;
		}
		count = 1;
		tmpCount = count;
		// isVertical
		for (int i = 1; i < 5; i++) {
			boolean bool = findChess(x, y + i, vertical);

			if (bool == false)
				break;
			count++;
			if (count == 5)
				return true;
		}
		tmpCount = count;
		for (int i = 1; i < 6 - tmpCount; i++) {
			boolean bool = findChess(x, y - i, vertical);

			if (bool == false)
				break;
			count++;
			if (count == 5)
				return true;
		}
		count = 1;
		tmpCount = count;
		// isLeftSlant
		for (int i = 1; i < 5; i++) {
			boolean bool = findChess(x - i, y + i, leftSlant);

			if (bool == false)
				break;
			count++;
			if (count == 5)
				return true;
		}
		tmpCount = count;
		for (int i = 1; i < 6 - tmpCount; i++) {
			boolean bool = findChess(x + i, y - i, leftSlant);

			if (bool == false)
				break;
			count++;
			if (count == 5)
				return true;
		}
		count = 1;
		tmpCount = count;
		// isRightSlant
		for (int i = 1; i < 5; i++) {
			boolean bool = findChess(x + i, y + i, rightSlant);

			if (bool == false)
				break;
			count++;
			if (count == 5)
				return true;
		}
		tmpCount = count;
		for (int i = 1; i < 6 - tmpCount; i++) {
			boolean bool = findChess(x - i, y - i, rightSlant);

			if (bool == false)
				break;
			count++;
			if (count == 5)
				return true;
		}

		return false;
	}
	
	public Chess[][] toArrayTable()
	{
		Chess[][] chesses = new Chess[rows][cols];
		for(int i = 0;i< rows;i++)
			for(int j=0;j< cols;j++)
				chesses[i][j] = new Chess(i,j,null);
		for(int i=0;i<chessCount;i++)
		{
			Chess chess = chessList[i];
			chesses[chess.getX()][chess.getY()] = chess;
		}
		return chesses;
	}
}
