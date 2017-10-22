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
import JiaoGoBang.Server.EasyAI.AI;

public class PassService implements Service {

	@Override
	public void execute(Request request, Response response, Socket socket) {
		String mode = (String) request.getParameter("mode");
		if (mode.equals("PVP")) {
			response.setReturnType("MoveAction");
			String xml;
			PrintStream ps;
			response.setStatus(4);
			String userId = (String) request.getParameter("id");
			Player player = (Player) Server.getUser(userId);
			Room room = player.getRoom();
			xml = XStreamUtil.toXML(response);
			try {
				ps = new PrintStream(room.getAnotherPlayer(player).getSocket().getOutputStream());
				ps.println(xml);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			response.setReturnType("MoveAction");
			String xml;
			PrintStream ps;
			response.setStatus(4);
			String userId = (String) request.getParameter("id");
			Player player = (Player) Server.getUser(userId);
			Room room = player.getRoom();
			Color color = player.getColor();
			AI ai;
			if (color.equals(Color.BLACK))
				ai = new AI(Color.WHITE,15);
			else {
				ai = new AI(Color.BLACK,15);
			}
			Chess newChess = ai.setChess(room.getChessTable(),3);
			room.getChessTable().addChess(newChess);
			boolean isAIWin = room.getChessTable().isWin(newChess);
			response.setStatus(1);
			if (isAIWin == true) {
				room.reset();
				response.setStatus(3);
			}
			else if(room.getChessTable().getChessCount() >= 225)
			{
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
