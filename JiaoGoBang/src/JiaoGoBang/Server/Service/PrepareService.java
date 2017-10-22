package JiaoGoBang.Server.Service;

import java.awt.Color;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Random;

import JiaoGoBang.Common.Request;
import JiaoGoBang.Common.Response;
import JiaoGoBang.Common.XStreamUtil;
import JiaoGoBang.Common.Model.Chess;
import JiaoGoBang.Common.Model.Player;
import JiaoGoBang.Common.Model.Room;
import JiaoGoBang.Server.Server;
import JiaoGoBang.Server.NewAI.NewAI;

public class PrepareService implements Service {

	@Override
	public void execute(Request request, Response response, Socket socket) {
		response.setReturnType("PrepareAction");
		String mode = (String) request.getParameter("mode");
		if (mode.equals("PVP")) {
			String userId = (String) request.getParameter("id");
			Player player = (Player) Server.getUser(userId);
			if (player == null)
				return;
			Room room = player.getRoom();
			room.addPrepareCount();
			player.setStatus(2);
			while (room.getCount() != 2) {
				Thread.yield();
			}
			if (room.getPlayerNumber(player) == 1)
				while (room.getPrepareCount() != 2) {
					if (player.getStatus() == 1)
						return;
					Thread.yield();
				}
			else if (room.getPlayerNumber(player) == 2) {
				while (room.getPrepareCount() != 2) {
					if (player.getStatus() == 1)
						return;
					Thread.yield();
				}
				player.setStatus(3);
				Random r = new Random();
				int number = r.nextInt(2);
				if (number == 0) {
					player.setColor(Color.BLACK);
					room.getAnotherPlayer(player).setColor(Color.WHITE);
					response.setData("color", "black");
				} else {
					player.setColor(Color.WHITE);
					room.getAnotherPlayer(player).setColor(Color.BLACK);
					response.setData("color", "white");
				}
				try {
					response.setStatus(1);
					String xml = XStreamUtil.toXML(response);
					PrintStream ps = new PrintStream(socket.getOutputStream());
					ps.println(xml);

					response = new Response("PrepareAction");
					response.setStatus(1);
					if (number == 0)
						response.setData("color", "white");
					else
						response.setData("color", "black");
					xml = XStreamUtil.toXML(response);
					ps = new PrintStream(room.getAnotherPlayer(player).getSocket().getOutputStream());
					ps.println(xml);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			String userId = (String) request.getParameter("id");
			Player player = (Player) Server.getUser(userId);
			if (player == null)
				return;
			Room room = player.getRoom();
			room.addPrepareCount();
			player.setStatus(3);
			Random r = new Random();
			int number = r.nextInt(2);
			if (number == 0) {
				player.setColor(Color.BLACK);
				response.setData("color", "black");
			} else {
				player.setColor(Color.WHITE);
				response.setData("color", "white");
			}
			response.setStatus(1);
			String xml = XStreamUtil.toXML(response);
			PrintStream ps;
			try {
				ps = new PrintStream(socket.getOutputStream());
				ps.println(xml);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (number == 1) {
				/*
				 * AI ai = new AI(Color.BLACK,15); Chess chess =
				 * ai.setChess(room.getChessTable(),3);
				 */
				NewAI ai = new NewAI(Color.BLACK);
				Chess chess = ai.setChess(room.getChessTable(),3);
				room.getChessTable().addChess(chess);
				response = new Response("MoveAction");
				response.setStatus(1);
				response.setData("chess", chess);
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
