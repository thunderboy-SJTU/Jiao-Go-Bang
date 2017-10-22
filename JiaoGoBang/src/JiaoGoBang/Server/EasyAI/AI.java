package JiaoGoBang.Server.EasyAI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import JiaoGoBang.Common.Model.Chess;
import JiaoGoBang.Common.Model.ChessTable;

public class AI {
	private Color color;
	private Chess[][] horizontal;
	private Chess[][] vertical;
	private Chess[][] leftSlant;
	private Chess[][] rightSlant;
	private int rows;

	public AI(Color color, int rows) {
		this.color = color;
		this.rows = rows;
		horizontal = new Chess[rows][rows];
		vertical = new Chess[rows][rows];
		leftSlant = new Chess[rows * 2 - 1][rows];
		rightSlant = new Chess[rows * 2 - 1][rows];
	}

	public Chess setChess(ChessTable board, int deep) {
		Chess chess = minmax(board, deep);
		return chess;
	}
	
	public Chess setChessRandom(ChessTable board,int deep)
	{
		Chess chess = minmaxRandom(board, deep);
		return chess;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void flat(Chess[][] chessArray) {

		// horizontal
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++)
				horizontal[i][j] = chessArray[i][j];
		}

		// vertical
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++)
				vertical[i][j] = chessArray[j][i];
		}

		// leftSlant
		for (int i = 0; i < rows * 2 - 1; i++) {
			int k = 0;
			for (int j = 0; j <= i && j < rows; j++) {
				if (i - j < rows)
					leftSlant[i][k++] = chessArray[i - j][j];
			}
		}

		// rightSlant
		for (int i = -rows + 1; i < rows; i++) {
			int k = 0;
			for (int j = 0; j < rows; j++) {
				if (j + i >= 0 && j + i < rows) {
					rightSlant[i + rows - 1][k] = chessArray[j + i][j];
					k++;
				}
			}
		}
	}

	public Chess minmax(ChessTable board, int deep) {
		if (color == null)
			return null;
		if (board.getChessCount() == 0) {
			/*
			 * Random r = new Random(); int x = r.nextInt(5)+(rows-5)/2; int y =
			 * r.nextInt(5)+(rows-5)/2;
			 */
			Chess chess = new Chess(7, 7, color);
			return chess;
		}
		int best = -10000000;

		List<Chess> bestChesses = new ArrayList<Chess>();
		Chess[][] chessArray = board.toArrayTable();
		flat(chessArray);
		List<Chess> generator = gen(chessArray, deep, color);
		System.out.println(board.getChessCount() + "s");
		if (board.getChessCount() == rows * rows - 1) {
			Chess bestChess = generator.get(0);
			bestChess.setColor(color);
			return bestChess;
		}

		for (int i = 0; i < generator.size(); i++) {
			int alpha = 10000000;
			int beta = -10000000;
			Chess chess = generator.get(i);
			board.addChess(chess);
			chessArray[chess.getX()][chess.getY()].setColor(color);
			if (board.isWin(chess)) {
				board.removeChess();
				chessArray[chess.getX()][chess.getY()].setColor(null);
				chess.setColor(color);
				return chess;
			}
			beta = best > beta ? best : beta;
			int value = min(chessArray, board, deep - 1, alpha, beta);
			if (value == best) {
				bestChesses.add(chess);
			} else if (value > best) {
				best = value;
				bestChesses = new ArrayList<Chess>();
				bestChesses.add(chess);
			}
			board.removeChess();
			chessArray[chess.getX()][chess.getY()].setColor(null);

		}
		Random r = new Random();
		int i = r.nextInt(bestChesses.size());
		Chess bestChess = bestChesses.get(i);

		bestChess.setColor(color);
		return bestChess;
	}

	public Chess minmaxRandom(ChessTable board, int deep) {
		if (color == null)
			return null;
		if (board.getChessCount() == 0) {
			/*
			 * Random r = new Random(); int x = r.nextInt(5)+(rows-5)/2; int y =
			 * r.nextInt(5)+(rows-5)/2;
			 */
			Chess chess = new Chess(7, 7, color);
			return chess;
		}
		int best = -10000000;
		int min = 10000000;

		List<Chess> bestChesses = new ArrayList<Chess>();
		List<Chess> goodChesses = new ArrayList<Chess>();
		List<Integer> scores = new ArrayList<Integer>();
		Chess[][] chessArray = board.toArrayTable();
		flat(chessArray);
		List<Chess> generator = gen(chessArray, deep, color);
		System.out.println(board.getChessCount() + "s");
		if (board.getChessCount() == rows * rows - 1) {
			Chess bestChess = generator.get(0);
			bestChess.setColor(color);
			return bestChess;
		}

		for (int i = 0; i < generator.size(); i++) {
			int alpha = 10000000;
			int beta = -10000000;
			Chess chess = generator.get(i);
			board.addChess(chess);
			chessArray[chess.getX()][chess.getY()].setColor(color);
			if (board.isWin(chess)) {
				board.removeChess();
				chessArray[chess.getX()][chess.getY()].setColor(null);
				chess.setColor(color);
				return chess;
			}
			beta = best > beta ? best : beta;
			int value = min(chessArray, board, deep - 1, alpha, beta);
			if (value == best) {
				bestChesses.add(chess);
			} else if (value > best) {

				min = value < min ? value : min;
				for (int j = 0; j < bestChesses.size(); j++) {
					goodChesses.add(bestChesses.get(j));
					scores.add(best);
				}
				best = value;
				bestChesses = new ArrayList<Chess>();
				bestChesses.add(chess);
			} else if (value < best) {
				if (goodChesses.size() < 3) {
					goodChesses.add(chess);
					scores.add(value);
					min = value < min ? value : min;
				} else if (value > min) {
					for (int j = 0; j < scores.size(); j++) {
						if (scores.get(j).equals(min)) {
							goodChesses.set(j, chess);
							scores.set(j, value);
						}
					}
					min = scores.get(0);
					for (int j = 1; j < scores.size(); j++) {
						min = scores.get(j) < min ? scores.get(j) : min;
					}
				}
			}
			board.removeChess();
			chessArray[chess.getX()][chess.getY()].setColor(null);

		}
		for(int i = 0;i<goodChesses.size();i++)
		{
			bestChesses.add(goodChesses.get(i));
		}
		Random r = new Random();
		int i = r.nextInt(bestChesses.size());
		Chess bestChess = bestChesses.get(i);
		bestChess.setColor(color);
		return bestChess;
	}

	public int min(Chess[][] chessArray, ChessTable board, int deep, int alpha, int beta) {

		int score = evaluate();
		if (deep <= 0 || board.isWin(board.getChessList()[board.getChessCount() - 1])
				|| board.getChessCount() >= rows * rows) {
			return score;
		}
		int best = 10000000;
		Color anotherColor = null;
		if (color.equals(Color.BLACK))
			anotherColor = Color.WHITE;
		else
			anotherColor = Color.BLACK;
		List<Chess> generator = gen(chessArray, deep, anotherColor);

		for (int i = 0; i < generator.size(); i++) {
			Chess chess = generator.get(i);
			board.addChess(chess);
			if (color.equals(Color.BLACK))
				chessArray[chess.getX()][chess.getY()].setColor(Color.WHITE);
			else
				chessArray[chess.getX()][chess.getY()].setColor(Color.BLACK);
			alpha = best < alpha ? best : alpha;
			score = max(chessArray, board, deep - 1, alpha, beta);
			board.removeChess();
			chessArray[chess.getX()][chess.getY()].setColor(null);
			if (score < best) {
				best = score;
			}
			if (score < beta) {
				break;
			}
		}
		return best;
	}

	public int max(Chess[][] chessArray, ChessTable board, int deep, int alpha, int beta) {
		int score = evaluate();
		if (deep <= 0 || board.isWin(board.getChessList()[board.getChessCount() - 1])
				|| board.getChessCount() >= rows * rows) {
			return score;
		}
		int best = -10000000;
		List<Chess> generator = gen(chessArray, deep, color);
		for (int i = 0; i < generator.size(); i++) {
			Chess chess = generator.get(i);
			board.addChess(chess);
			chessArray[chess.getX()][chess.getY()].setColor(color);
			beta = best > beta ? best : beta;
			score = min(chessArray, board, deep - 1, alpha, beta);
			board.removeChess();
			chessArray[chess.getX()][chess.getY()].setColor(null);
			if (score > best) {
				best = score;
			}
			if (score > alpha) {
				break;
			}
		}
		return best;
	}

	public int evaluate() {
		int aiScore = evaluateRows(color);
		int playerScore;
		if (color.equals(Color.BLACK))
			playerScore = evaluateRows(Color.WHITE);
		else
			playerScore = evaluateRows(Color.BLACK);
		return aiScore - playerScore;
	}

	public int evaluateRows(Color chessColor) {
		int score = 0;
		for (int i = 0; i < rows; i++)
			score += evaluateRow(horizontal[i], rows, chessColor);
		for (int i = 0; i < rows; i++)
			score += evaluateRow(vertical[i], rows, chessColor);
		for (int i = 0; i < 2 * rows - 1; i++) {
			if (i < rows)
				score += evaluateRow(leftSlant[i], i + 1, chessColor);
			else
				score += evaluateRow(leftSlant[i], 2 * rows - 1 - i, chessColor);
		}
		for (int i = 0; i < 2 * rows - 1; i++) {
			if (i < rows)
				score += evaluateRow(rightSlant[i], i + 1, chessColor);
			else
				score += evaluateRow(rightSlant[i], 2 * rows - 1 - i, chessColor);
		}
		return score;
	}

	public int evaluateRow(Chess[] row, int len, Color chessColor) {
		int count = 0;
		int block = 0;
		int empty = 0;
		int score = 0;
		Color anotherColor;
		if (chessColor.equals(Color.BLACK))
			anotherColor = Color.WHITE;
		else
			anotherColor = Color.BLACK;

		for (int i = 0; i < len; i++) {
			if (chessColor.equals(row[i].getColor())) {
				count = 1;
				block = 0;
				empty = 0;

				if (i == 0)
					block = 1;
				else if (row[i - 1].getColor() != null)
					block = 1;

				for (i = i + 1; i < len; i++) {
					if (chessColor.equals(row[i].getColor())) {
						count++;
					} else if (empty == 0 && i < len - 1 && row[i].getColor() == null
							&& chessColor.equals(row[i + 1].getColor()))
						empty = count;
					else if (empty == 0 && count == 3 && i < len && row[i].getColor() == null
							&& (i == len - 1 || anotherColor.equals(row[i + 1].getColor())) && i >= 3
							&& row[i - 3].getColor() == null
							&& (i - 3 == 0 || anotherColor.equals(row[i - 4].getColor()))) {
						System.out.println("alarm");
						block += 1;
						break;
					} else
						break;
				}

				if (i == len || row[i].getColor() != null)
					block++;
				score += score(count, block, empty);
			}
		}
		return score;
	}

	public int score(int count, int block, int empty) {
		if (empty == 0) {
			if (count >= 5)
				return Score.FIVE;
			if (block == 0) {
				switch (count) {
				case 1:
					return Score.ONE;
				case 2:
					return Score.TWO;
				case 3:
					return Score.THREE;
				case 4:
					return Score.FOUR;
				}
			}
			if (block == 1) {
				switch (count) {
				case 1:
					return Score.BLOCKED_ONE;
				case 2:
					return Score.BLOCKED_TWO;
				case 3:
					return Score.BLOCKED_THREE;
				case 4:
					return Score.BLOCKED_FOUR;
				}
			}

		} else if (empty == 1 || empty == count - 1) {

			if (count >= 6) {
				return Score.FIVE;
			}
			if (block == 0) {
				switch (count) {
				case 2:
					return Score.TWO / 2;
				case 3:
				case 4:
					return Score.THREE;
				case 5:
					return Score.FOUR;
				}
			}
			if (block == 1) {
				switch (count) {
				case 2:
					return Score.BLOCKED_TWO;
				case 3:
					return Score.BLOCKED_THREE;
				case 4:
				case 5:
					return Score.BLOCKED_FOUR;
				}
			}
		} else if (empty == 2 || empty == count - 2) {

			if (count >= 7) {
				return Score.FIVE;
			}
			if (block == 0) {
				switch (count) {
				case 3:
				case 4:
				case 5:
					return Score.THREE;
				case 6:
					return Score.FOUR;
				}
			}
			if (block == 1) {
				switch (count) {
				case 3:
					return Score.BLOCKED_THREE;
				case 4:
					return Score.BLOCKED_FOUR;
				case 5:
					return Score.BLOCKED_FOUR;
				case 6:
					return Score.FOUR;
				}
			}
			if (block == 2) {
				switch (count) {
				case 4:
				case 5:
				case 6:
					return Score.BLOCKED_FOUR;
				}
			}
		} else if (empty == 3 || empty == count - 3) {
			if (count >= 8) {
				return Score.FIVE;
			}
			if (block == 0) {
				switch (count) {
				case 4:
				case 5:
					return Score.THREE;
				case 6:
					return Score.THREE * 2;
				case 7:
					return Score.FOUR;
				}
			}
			if (block == 1) {
				switch (count) {
				case 4:
				case 5:
				case 6:
					return Score.BLOCKED_FOUR;
				case 7:
					return Score.FOUR;
				}
			}
			if (block == 2) {
				switch (count) {
				case 4:
				case 5:
				case 6:
				case 7:
					return Score.BLOCKED_FOUR;
				}
			}
		} else if (empty == 4 || empty == count - 4) {
			if (count >= 9) {
				return Score.FIVE;
			}
			if (block == 0) {
				switch (count) {
				case 5:
				case 6:
				case 7:
				case 8:
					return Score.FOUR;
				}
			}
			if (block == 1) {
				switch (count) {
				case 4:
				case 5:
				case 6:
				case 7:
					return Score.BLOCKED_FOUR;
				case 8:
					return Score.FOUR;
				}
			}
			if (block == 2) {
				switch (count) {
				case 5:
				case 6:
				case 7:
				case 8:
					return Score.BLOCKED_FOUR;
				}
			}
		} else if (empty == 5 || empty == count - 5) {
			return Score.FIVE;
		}
		return 0;
	}

	public int chessScore(Chess[][] chessArray, Chess chess) {
		int result = 0;
		int count = 1;
		int block = 0;
		int secondCount = 0;
		int empty = 0;

		for (int i = chess.getY() + 1; true; i++) {
			if (i > rows - 1) {
				block++;
				break;
			}
			Chess newChess = chessArray[chess.getX()][i];
			if (newChess.getColor() == null) {
				if (empty == 0 && i < rows - 1 && chess.getColor().equals(chessArray[chess.getX()][i + 1])) {
					empty = count;
					continue;
				} else {
					break;
				}
			}
			if (newChess.getColor().equals(chess.getColor())) {
				count++;
				continue;
			} else {
				block++;
				break;
			}
		}

		for (int i = chess.getY() - 1; true; i--) {
			if (i < 0) {
				block++;
				break;
			}
			Chess newChess = chessArray[chess.getX()][i];
			if (newChess.getColor() == null) {
				if (empty == 0 && i > 0 && chess.getColor().equals(chessArray[chess.getX()][i - 1])) {
					empty = 0;
					continue;
				} else {
					break;
				}
			}
			if (newChess.getColor().equals(chess.getColor())) {
				secondCount++;
				if (empty != 0)
					empty++;
				continue;
			} else {
				block++;
				break;
			}
		}
		count += secondCount;
		result += score(count, block, empty);

		count = 1;
		block = 0;
		empty = 0;
		secondCount = 0;

		for (int i = chess.getX() + 1; true; i++) {
			if (i > rows - 1) {
				block++;
				break;
			}
			Chess newChess = chessArray[i][chess.getY()];
			if (newChess.getColor() == null) {
				if (empty == 0 && i < rows - 1 && chess.getColor().equals(chessArray[i + 1][chess.getY()].getColor())) {
					empty = count;
					continue;
				} else {
					break;
				}
			}
			if (newChess.getColor().equals(chess.getColor())) {
				count++;
				continue;
			} else {
				block++;
				break;
			}
		}

		for (int i = chess.getX() - 1; true; i--) {
			if (i < 0) {
				block++;
				break;
			}
			Chess newChess = chessArray[i][chess.getY()];
			if (newChess.getColor() == null) {
				if (empty == 0 && i > 0 && chess.getColor().equals(chessArray[i - 1][chess.getY()].getColor())) {
					empty = 0;
					continue;
				} else {
					break;
				}
			}
			if (newChess.getColor().equals(chess.getColor())) {
				secondCount++;
				if (empty != 0)
					empty++;
				continue;
			} else {
				block++;
				break;
			}
		}

		count += secondCount;
		result += score(count, block, empty);

		count = 1;
		block = 0;
		empty = 0;
		secondCount = 0;

		for (int i = 1; true; i++) {
			int x = chess.getX() + i, y = chess.getY() + i;
			if (x > rows - 1 || y > rows - 1) {
				block++;
				break;
			}
			Chess newChess = chessArray[x][y];
			if (newChess.getColor() == null) {
				if (empty == 0 && (x < rows - 1 && y < rows - 1)
						&& chess.getColor().equals(chessArray[x + 1][y + 1].getColor())) {
					empty = count;
					continue;
				} else {
					break;
				}
			}
			if (newChess.getColor().equals(chess.getColor())) {
				count++;
				continue;
			} else {
				block++;
				break;
			}
		}

		for (int i = 1; true; i++) {
			int x = chess.getX() - i, y = chess.getY() - i;
			if (x < 0 || y < 0) {
				block++;
				break;
			}
			Chess newChess = chessArray[x][y];
			if (newChess.getColor() == null) {
				if (empty == 0 && (x > 0 && y > 0) && chess.getColor().equals(chessArray[x - 1][y - 1].getColor())) {
					empty = 0;
					continue;
				} else {
					break;
				}
			}
			if (newChess.getColor().equals(chess.getColor())) {
				secondCount++;
				if (empty != 0)
					empty++;
				continue;
			} else {
				block++;
				break;
			}
		}

		count += secondCount;
		result += score(count, block, empty);

		count = 1;
		block = 0;
		empty = 0;
		secondCount = 0;

		for (int i = 1; true; i++) {
			int x = chess.getX() + i, y = chess.getY() - i;
			if (x < 0 || y < 0 || x > rows - 1 || y > rows - 1) {
				block++;
				break;
			}
			Chess newChess = chessArray[x][y];
			if (newChess.getColor() == null) {
				if (empty == 0 && (x < rows - 1 && y > 0)
						&& chess.getColor().equals(chessArray[x + 1][y - 1].getColor())) {
					empty = count;
					continue;
				} else {
					break;
				}
			}
			if (newChess.getColor().equals(chess.getColor())) {
				count++;
				continue;
			} else {
				block++;
				break;
			}
		}

		for (int i = 1; true; i++) {
			int x = chess.getX() - i, y = chess.getY() + i;
			if (x < 0 || y < 0 || x > rows - 1 || y > rows - 1) {
				block++;
				break;
			}
			Chess newChess = chessArray[x][y];
			if (newChess.getColor() == null) {
				if (empty == 0 && (x > 0 && y < rows - 1)
						&& chess.getColor().equals(chessArray[x - 1][y + 1].getColor())) {
					empty = 0;
					continue;
				} else {
					break;
				}
			}
			if (newChess.getColor().equals(chess.getColor())) {
				secondCount++;
				if (empty != 0)
					empty++;
				continue;
			} else {
				block++;
				break;
			}
		}

		count += secondCount;
		result += score(count, block, empty);
		/*
		 * if (result < Score.FOUR && result >= Score.BLOCKED_FOUR) { if (result
		 * >= Score.BLOCKED_FOUR && result < (Score.BLOCKED_FOUR + Score.THREE))
		 * { return Score.THREE; } else if (result >= Score.BLOCKED_FOUR +
		 * Score.THREE && result < Score.BLOCKED_FOUR * 2) { return Score.FOUR;
		 * } else { return Score.FOUR * 2; } }
		 */
		// System.out.println(result);
		return result;
	}

	public List<Chess> gen(Chess[][] chessArray, int deep) {
		List<Chess> neighbors = new ArrayList<Chess>();
		List<Chess> neighbors2 = new ArrayList<Chess>();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				if (chessArray[i][j].getColor() == null) {
					Chess chess = chessArray[i][j];
					if (hasNeighbor(chessArray, chess, 1, 1)) {
						neighbors.add(chess);
					} else if (deep >= 2 && hasNeighbor(chessArray, chess, 2, 2)) {
						neighbors2.add(chess);
					}
				}
			}
		}
		for (int i = 0; i < neighbors2.size(); i++)
			neighbors.add(neighbors2.get(i));
		return neighbors;
	}

	public List<Chess> gen(Chess[][] chessArray, int deep, Color chessColor) {

		List<Chess> fives = new ArrayList<Chess>();
		List<Chess> anotherFours = new ArrayList<Chess>();
		List<Chess> fours = new ArrayList<Chess>();
		List<Chess> anotherTwoThrees = new ArrayList<Chess>();
		List<Chess> twoThrees = new ArrayList<Chess>();
		List<Chess> anotherThrees = new ArrayList<Chess>();
		List<Chess> threes = new ArrayList<Chess>();
		List<Chess> anotherTwos = new ArrayList<Chess>();
		List<Chess> twos = new ArrayList<Chess>();

		List<Chess> neighbors = new ArrayList<Chess>();
		List<Chess> neighbors2 = new ArrayList<Chess>();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				if (chessArray[i][j].getColor() == null) {
					Chess chess = chessArray[i][j];
					Color anotherColor = null;
					if (color.equals(Color.BLACK))
						anotherColor = Color.WHITE;
					else
						anotherColor = Color.BLACK;
					if (hasNeighbor(chessArray, chess, 1, 1)) {
						System.out.println("A" + chess.getX());
						System.out.println("B" + chess.getY());
						chess.setColor(chessColor);
						int score = chessScore(chessArray, chess);
						System.out.println("c" + score);
						chess.setColor(null);
						chess.setColor(anotherColor);
						int score2 = chessScore(chessArray, chess);
						System.out.println("D" + score2);
						chess.setColor(null);
						if (score >= Score.FIVE) {
							List<Chess> list = new ArrayList<Chess>();
							list.add(chess);
							return list;
						} else if (score2 >= Score.FIVE) {
							fives.add(chess);
						} else if (score >= Score.FOUR) {
							fours.add(chess);
						} else if (score2 >= Score.FOUR) {
							anotherFours.add(chess);
						} else if (score >= 2 * Score.THREE) {
							twoThrees.add(chess);
						} else if (score2 >= 2 * Score.THREE) {
							anotherTwoThrees.add(chess);
						} else if (score >= Score.THREE) {
							threes.add(chess);
						} else if (score2 >= Score.THREE) {
							anotherThrees.add(chess);
						} else if (score >= Score.TWO) {
							twos.add(chess);
						} else if (score2 >= Score.TWO) {
							anotherTwos.add(chess);
						} else {
							neighbors.add(chess);
						}
					} else if (deep >= 2 && hasNeighbor(chessArray, chess, 2, 2)) {
						neighbors2.add(chess);
					}

				}

			}
		}
		if (!fives.isEmpty()) {
			List<Chess> list = new ArrayList<Chess>();
			list.add(fives.get(0));
			return list;
		}

		if (!fours.isEmpty() || !anotherFours.isEmpty()) {
			for (int i = 0; i < anotherFours.size(); i++)
				fours.add(anotherFours.get(i));
			return fours;
		}
		if (!twoThrees.isEmpty() || !anotherTwoThrees.isEmpty()) {
			for (int i = 0; i < anotherTwoThrees.size(); i++)
				twoThrees.add(anotherTwoThrees.get(i));
			return twoThrees;
		}
		for (int i = 0; i < anotherThrees.size(); i++)
			threes.add(anotherThrees.get(i));
		for (int i = 0; i < twos.size(); i++)
			threes.add(twos.get(i));
		for (int i = 0; i < anotherTwos.size(); i++)
			threes.add(anotherTwos.get(i));
		for (int i = 0; i < neighbors.size(); i++)
			threes.add(neighbors.get(i));
		for (int i = 0; i < neighbors2.size(); i++)
			threes.add(neighbors2.get(i));
		return threes;

	}

	public boolean hasNeighbor(Chess[][] chessArray, Chess chess, int distance, int count) {
		int startX = chess.getX() - distance;
		int endX = chess.getX() + distance;
		int startY = chess.getY() - distance;
		int endY = chess.getY() + distance;
		for (int i = startX; i <= endX; i++) {
			if (i < 0 || i >= rows)
				continue;
			for (int j = startY; j <= endY; j++) {
				if (j < 0 || j >= rows)
					continue;
				if (i == chess.getX() && j == chess.getY())
					continue;
				if (chessArray[i][j].getColor() != null) {
					count--;
					if (count <= 0)
						return true;
				}
			}
		}
		return false;
	}
}
