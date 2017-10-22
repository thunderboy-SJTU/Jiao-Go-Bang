package JiaoGoBang.Server.Service;

import java.awt.Color;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import JiaoGoBang.Common.Request;
import JiaoGoBang.Common.Response;
import JiaoGoBang.Common.XStreamUtil;
import JiaoGoBang.Common.Model.Chess;
import JiaoGoBang.Common.Model.Player;
import JiaoGoBang.Common.Model.Room;
import JiaoGoBang.Server.Server;
import JiaoGoBang.Server.NewAI.NewAI;

public class MoveService implements Service {

	@Override
	public void execute(Request request, Response response, Socket socket) {
		response.setReturnType("MoveAction");
		String mode = (String) request.getParameter("mode");
		if (mode.equals("PVP")) {
			String xml;
			PrintStream ps;
			response.setStatus(1);
			String userId = (String) request.getParameter("id");
			Chess chess = (Chess) request.getParameter("chess");
			Player player = (Player) Server.getUser(userId);
			if (player == null)
				return;
			Room room = player.getRoom();
			if (room == null) {
				return;
			} else {
				room.getChessTable().addChess(chess);
			}
			boolean isWin = room.getChessTable().isWin(chess);
			if (isWin == true) {
				room.reset();
				response.setStatus(2);
				xml = XStreamUtil.toXML(response);
				try {
					ps = new PrintStream(socket.getOutputStream());
					ps.println(xml);
				} catch (IOException e) {
					e.printStackTrace();
				}
				response.setStatus(3);
			} else if (room.getChessTable().getChessCount() >= 225) {
				room.reset();
				response.setStatus(5);
				xml = XStreamUtil.toXML(response);
				try {
					ps = new PrintStream(socket.getOutputStream());
					ps.println(xml);
				} catch (IOException e) {
					e.printStackTrace();
				}
				response.setStatus(6);
			}
			response.setData("chess", chess);
			xml = XStreamUtil.toXML(response);
			try {
				ps = new PrintStream(room.getAnotherPlayer(player).getSocket().getOutputStream());
				ps.println(xml);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			String xml;
			PrintStream ps;
			response.setStatus(1);
			String userId = (String) request.getParameter("id");
			Chess chess = (Chess) request.getParameter("chess");
			Player player = (Player) Server.getUser(userId);
			if (player == null)
				return;
			Room room = player.getRoom();
			if (room == null) {
				return;
			} else {
				room.getChessTable().addChess(chess);
			}
			boolean isWin = room.getChessTable().isWin(chess);
			if (isWin == true) {
				room.reset();
				response.setStatus(2);
				xml = XStreamUtil.toXML(response);
				try {
					ps = new PrintStream(socket.getOutputStream());
					ps.println(xml);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				Color color = chess.getColor();
				if (room.getChessTable().getChessCount() >= 225) {
					room.reset();
					response.setStatus(5);
					xml = XStreamUtil.toXML(response);
					try {
						ps = new PrintStream(socket.getOutputStream());
						ps.println(xml);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}

				/*
				 * AI ai; if (color.equals(Color.BLACK)) ai = new
				 * AI(Color.WHITE, 15); else { ai = new AI(Color.BLACK, 15); }
				 * Chess newChess = ai.setChess(room.getChessTable(), 3);
				 */

				NewAI ai;
				if (color.equals(Color.BLACK))
					ai = new NewAI(Color.WHITE);
				else {
					ai = new NewAI(Color.BLACK);
				}
				Chess newChess = ai.setChess(room.getChessTable(),3);

				room.getChessTable().addChess(newChess);
				if (player.getStatus() == 5) {
					player.setStatus(4);
					room.getChessTable().withdraw(color);
					response.setReturnType("WithdrawAction");
					response.setStatus(1);
					xml = XStreamUtil.toXML(response);
					try {
						ps = new PrintStream(socket.getOutputStream());
						ps.println(xml);
						return;
					} catch (IOException e) {
						e.printStackTrace();
						return;
					}
				} else if (player.getStatus() == 6) {
					player.setStatus(4);
					response.setReturnType("LoseAction");
					response.setStatus(1);
					room.reset();
					xml = XStreamUtil.toXML(response);
					try {
						ps = new PrintStream(socket.getOutputStream());
						ps.println(xml);
						return;
					} catch (IOException e) {
						e.printStackTrace();
						return;
					}
				}
				boolean isAIWin = room.getChessTable().isWin(newChess);
				response.setStatus(1);
				System.out.println(room.getChessTable().getChessCount());
				if (isAIWin == true) {
					room.reset();
					response.setStatus(3);
				} else if (room.getChessTable().getChessCount() >= 225) {
					room.reset();
					response.setStatus(6);
				}
				response.setData("chess", newChess);
				xml = XStreamUtil.toXML(response);
				try {
					ps = new PrintStream(socket.getOutputStream());
					ps.println(xml);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
