package JiaoGoBang.Server.NewAI;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import JiaoGoBang.Create;
import JiaoGoBang.Common.Model.Chess;
import JiaoGoBang.Common.Model.ChessTable;
import JiaoGoBang.Common.Model.ReplayedChessTable;
import JiaoGoBang.Server.EasyAI.AI;

public class NewAI {
	private static boolean flag = false;
	private static Map<String, PhaseRatio> phaseRatios = new HashMap<String, PhaseRatio>();
	private Color color;
	private static  double winThreshold = 0.2;

	public NewAI(Color color) {
		this.color = color;
	}

	public void init() {

		Map<String, Phase> phases = new HashMap<String, Phase>();
		for (int i = 0; i < 30000; i++) {
			ChessTable board = readFile("file/" + i + ".txt");
			Chess[][] chesses = new Chess[15][15];
			if (board == null) {
				Create create = new Create();
				create.execute(i, i + 1);
				board = readFile("file/" + i + ".txt");
			}
			int whoWin = ((ReplayedChessTable) board).getWhoWin();
			for (int a = 0; a < 15; a++)
				for (int b = 0; b < 15; b++)
					chesses[a][b] = new Chess(a, b, null);
			for (int j = 0; j < board.getChessCount() - 1; j++) {
				Chess chess = board.getChessList()[j];
				int x = chess.getX();
				int y = chess.getY();
				chesses[x][y].setColor(chess.getColor());
				if (x < 3 || x > 11 || y < 3 || y > 11)
					continue;
				String key = toString(chess, chesses);
				Chess newChess = board.getChessList()[j + 1];
				Position position = new Position(newChess.getX(), newChess.getY());
				if (position.getX() - x > 3 || x - position.getX() > 3 || position.getY() - y > 3
						|| y - position.getY() > 3)
					continue;
				int isWin = -1;
				if (whoWin == 2)
					isWin = 2;
				else if ((whoWin == 0 && newChess.getColor().equals(Color.BLACK))
						|| (whoWin == 1 && newChess.getColor().equals(Color.WHITE)))
					isWin = 1;
				else
					isWin = 0;
				if (!phases.containsKey(key))
					phases.put(key, new Phase());
				position.setX(position.getX() - x + 3);
				position.setY(position.getY() - y + 3);
				phases.get(key).add(position, isWin);

			}
		}

		for (String key : phases.keySet()) {
			phaseRatios.put(key, phases.get(key).convert());
		}
		writeRecordFile(phaseRatios);
		flag = true;

	}

	public String toString(Chess chess, Chess[][] chesses) {
		int x = chess.getX();
		int y = chess.getY();
		int xBegin = -1;
		int xEnd = -1;
		int yBegin = -1;
		int yEnd = -1;
		if (x - 3 < 0) {
			xBegin = 0;
			xEnd = 6;
		} else if (x + 3 > 14) {
			xBegin = 8;
			xEnd = 14;
		} else {
			xBegin = x - 3;
			xEnd = x + 3;
		}

		if (y - 3 < 0) {
			yBegin = 0;
			yEnd = 6;
		} else if (y + 3 > 14) {
			yBegin = 8;
			yEnd = 14;
		} else {
			yBegin = y - 3;
			yEnd = y + 3;
		}
		String str = "";
		for (int a = xBegin; a <= xEnd; a++)
			for (int b = yBegin; b <= yEnd; b++) {
				Chess newChess = chesses[a][b];
				str += convertColor(newChess.getColor());
			}
		return str;
	}

	public void importFile() {
		File file = new File("file/record.txt");
		if (!file.exists())
			init();
		else if (flag == false) {
			readRecordFile();
			flag = true;
		}
	}

	public Chess setChess(ChessTable board, int deep) {
		importFile();
		if (board.getChessCount() == 0) {
			Chess chess = new Chess(7, 7, color);
			return chess;
		}
		Chess[][] chesses = board.toArrayTable();
		Chess lastChess = board.getChessList()[board.getChessCount() - 1];
		if (lastChess.getX() < 3 || lastChess.getX() > 11 || lastChess.getY() < 3 || lastChess.getY() > 11) {
			System.out.println("border");
			AI ai = new AI(color, 15);
			Chess newChess = ai.setChess(board, 2);
			return newChess;
		}
		String key = toString(lastChess, chesses);
		if (phaseRatios.containsKey(key)) {
			PhaseRatio phaseRatio = phaseRatios.get(key);
			double best = -1;
			List<Chess> bestChesses = new ArrayList<Chess>();
			System.out.println(phaseRatio.getRatioMap().size());
			List<ChessRatio> generator = generator(phaseRatio, lastChess, chesses);
			for (int i = 0; i < generator.size(); i++) {
				ChessRatio chessRatio = generator.get(i);
				Chess chess = chessRatio.getChess();
				double winRatio = chessRatio.getRatio().getWinRatio();
				board.addChess(chess);
				chesses[chess.getX()][chess.getY()].setColor(color);
				if (board.isWin(chess)) {
					board.removeChess();
					chesses[chess.getX()][chess.getY()].setColor(null);
					chess.setColor(color);
					return chess;
				}
				double value = min(chesses, board,winRatio, deep - 1);
				System.out.println(value + "e");
				if (value == best) {
					bestChesses.add(chess);
				} else if (value > best) {
					best = value;
					bestChesses = new ArrayList<Chess>();
					bestChesses.add(chess);
				}
				board.removeChess();
				chesses[chess.getX()][chess.getY()].setColor(null);
			}

			if (bestChesses.size() != 0) {
				Random r = new Random();
				int number = r.nextInt(bestChesses.size());
				Chess newChess = bestChesses.get(number);
				newChess.setColor(color);
				return newChess;
			} else {
				System.out.println("no chess");
				AI ai = new AI(color, 15);
				Chess newChess = ai.setChess(board, 2);
				return newChess;
			}
		} else {
			System.out.println("No chess");
			AI ai = new AI(color, 15);
			Chess newChess = ai.setChess(board, 2);
			return newChess;
		}
	}

	public double min(Chess[][] chesses, ChessTable board, double winRatio, int deep) {
		double score = winRatio;
		if (deep <= 0 || winRatio < winThreshold || board.isWin(board.getChessList()[board.getChessCount() - 1]) || board.getChessCount() >= 225) {
			return score;
		}

		Chess lastChess = board.getChessList()[board.getChessCount() - 1];
		String key = toString(lastChess, chesses);
		if (phaseRatios.containsKey(key)) {
			PhaseRatio phaseRatio = phaseRatios.get(key);
			double best = 100;
			List<ChessRatio> generator = generator(phaseRatio, lastChess, chesses);
			if (generator.size() == 0)
				return score;
			for (int i = 0; i < generator.size(); i++) {

				ChessRatio chessRatio = generator.get(i);
				Chess chess = chessRatio.getChess();
				board.addChess(chess);
				if (color.equals(Color.BLACK))
					chesses[chess.getX()][chess.getY()].setColor(Color.WHITE);
				else
					chesses[chess.getX()][chess.getY()].setColor(Color.BLACK);
				score = max(chesses, board, chessRatio.getRatio().getWinRatio(), deep - 1);
				System.out.println(score + "f");
				board.removeChess();
				chesses[chess.getX()][chess.getY()].setColor(null);
				if (score < best) {
					best = score;
				}
			}
			return 1-best;
		} else
			return score;
	}
	
	public double max(Chess[][] chesses, ChessTable board, double winRatio, int deep) {
		double score = winRatio;
		if (deep <= 0 || winRatio > 1- winThreshold || board.isWin(board.getChessList()[board.getChessCount() - 1]) || board.getChessCount() >= 225) {
			return score;
		}
		Chess lastChess = board.getChessList()[board.getChessCount() - 1];
		String key = toString(lastChess, chesses);
		if (phaseRatios.containsKey(key)) {
			PhaseRatio phaseRatio = phaseRatios.get(key);
			double best = -1;
			List<ChessRatio> generator = generator(phaseRatio, lastChess, chesses);
			if (generator.size() == 0)
				return score;
			for (int i = 0; i < generator.size(); i++) {

				ChessRatio chessRatio = generator.get(i);
				Chess chess = chessRatio.getChess();
				board.addChess(chess);
				chesses[chess.getX()][chess.getY()].setColor(color);
				score = min(chesses, board, chessRatio.getRatio().getWinRatio(), deep - 1);
				System.out.println(score + "g");
				board.removeChess();
				chesses[chess.getX()][chess.getY()].setColor(null);
				if (score > best) {
					best = score;
				}
			}
			return 1-best;
		} else
			return score;
	}


	List<ChessRatio> generator(PhaseRatio phaseRatio, Chess lastChess, Chess[][] chesses) {
		double threshold = 0.1;
		List<ChessRatio> list = new ArrayList<ChessRatio>();
		for (String str : phaseRatio.getRatioMap().keySet()) {
			String[] position = null;
			position = str.split(" ");
			int x = lastChess.getX() + Integer.parseInt(position[0]) - 3;
			int y = lastChess.getY() + Integer.parseInt(position[1]) - 3;
			if (chesses[x][y].getColor() != null)
				continue;
			double ratio = phaseRatio.getRatioMap().get(str).getRatio();
			if (ratio > threshold) {
				list.add(new ChessRatio(chesses[x][y], phaseRatio.getRatioMap().get(str)));
			}
		}
		return list;
	}

	public int convertColor(Color color) {
		if (color == null)
			return 0;
		if (color.equals(Color.BLACK))
			return 1;
		if (color.equals(Color.WHITE))
			return 2;
		return -1;
	}

	public ChessTable readFile(String filename) {

		try {
			System.out.println(filename);
			ChessTable chessTable = new ReplayedChessTable(14, 14);
			File file = new File(filename);
			if (file.exists()) {
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
				return chessTable;
			} else {
				System.out.println("找不到指定的文件");
				return null;
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			return null;
		}
	}

	public void writeRecordFile(Map<String, PhaseRatio> phaseRatios) {
		try {
			File file = new File("file/record.txt");
			FileWriter fileWriter = new FileWriter(file);
			for (String str : phaseRatios.keySet()) {
				String data = "";
				data += str + " ";
				PhaseRatio phaseRatio = phaseRatios.get(str);
				for (String position : phaseRatio.getRatioMap().keySet()) {
					data += position + " ";
					double ratio = phaseRatio.getRatioMap().get(position).getRatio();
					double winRatio = phaseRatio.getRatioMap().get(position).getWinRatio();
					data += ratio + " " + winRatio + " ";
				}
				data += "\n";
				fileWriter.write(data);
				fileWriter.flush();
			}
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readRecordFile() {
		try {
			File file = new File("file/record.txt");
			if (file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file));
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					String[] txtChess = null;
					txtChess = lineTxt.split(" ");
					String phase = txtChess[0];
					System.out.println(phase);
					int i = 1;
					PhaseRatio phaseRatio = new PhaseRatio();
					while (i < txtChess.length && txtChess[i] != null) {
						String x = txtChess[i];
						String y = txtChess[i + 1];
						String position = x + " " + y;
						System.out.println(position);
						double ratio = Double.parseDouble(txtChess[i + 2]);
						System.out.println(i);
						System.out.println(ratio);
						double winRatio = Double.parseDouble(txtChess[i + 3]);
						i += 4;
						Ratio chessRatio = new Ratio(ratio, winRatio);
						phaseRatio.addRatio(position, chessRatio);
					}
					phaseRatios.put(phase, phaseRatio);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

/*
 * public static void main(String[] args) { NewAI ai = new NewAI(); ai.init();
 * System.out.println("heelo");
 * 
 * }
 */
