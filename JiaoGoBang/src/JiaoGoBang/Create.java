package JiaoGoBang;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import JiaoGoBang.Common.Model.Chess;
import JiaoGoBang.Common.Model.ChessTable;
import JiaoGoBang.Server.EasyAI.AI;

public class Create {

	public void execute(int begin, int end) {
		for (int j = begin; j < end; j++) {

			ChessTable board = new ChessTable(14, 14);
			/*
			 * Color color = Color.BLACK; Random r = new Random();
			 * 
			 * for (int i = 0; i < 2; i++) { int x = r.nextInt(5) + 5; int y =
			 * r.nextInt(5) + 5; Chess chess = new Chess(x,y,color);
			 * if(!board.findChess(chess)) board.addChess(chess); else { i--;
			 * continue; } if(color.equals(Color.BLACK)) color = Color.WHITE;
			 * else color = Color.BLACK; }
			 */
			AI ai1 = new AI(Color.BLACK, 15);
			AI ai2 = new AI(Color.WHITE, 15);
			Date a = new Date();
			while (true) {
				Date b = new Date();
				if ((b.getTime() - a.getTime()) / 1000 > 90) {
					j--;
					break;
				}
				if (board.getChessCount() >= 225) {
					writeToFile(j, board, 2);
					break;
				}
				Random random = new Random();
				int flag = random.nextInt(2);
				if (flag == 0) {
					Chess blackChess = ai1.setChessRandom(board, 3);
					board.addChess(blackChess);
					if (board.isWin(blackChess)) {
						writeToFile(j, board, 0);
						break;
					}
					if (board.getChessCount() >= 225) {
						writeToFile(j, board, 2);
						break;
					}
					Chess whiteChess = ai2.setChess(board, 3);
					board.addChess(whiteChess);
					if (board.isWin(whiteChess)) {
						writeToFile(j, board, 1);
						break;
					}
				} else {
					Chess blackChess = ai1.setChess(board, 3);
					board.addChess(blackChess);
					if (board.isWin(blackChess)) {
						writeToFile(j, board, 0);
						break;
					}
					if (board.getChessCount() >= 225) {
						writeToFile(j, board, 2);
						break;
					}
					Chess whiteChess = ai2.setChessRandom(board, 3);
					board.addChess(whiteChess);
					if (board.isWin(whiteChess)) {
						writeToFile(j, board, 1);
						break;
					}
				}
			}

		}
	}

	private  void writeToFile(int number, ChessTable board, int whoWin) {
		try {
			String data = whoWin + "\n";
			for (int i = 0; i < board.getChessCount(); i++) {
				Chess chess = board.getChessList()[i];
				if (chess.getColor() == Color.BLACK)
					data += "0 ";
				else
					data += "1 ";
				data += chess.getX() + " " + chess.getY() + "\n";
			}
			File file = new File("src/JiaoGoBang/file/" + number + ".txt");
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(data);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Create create = new Create();
		create.execute(28762, 30000);
	}
}
