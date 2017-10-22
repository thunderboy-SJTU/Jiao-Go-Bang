package JiaoGoBang.Server.Service;

import java.awt.Color;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import JiaoGoBang.Common.Request;
import JiaoGoBang.Common.Response;
import JiaoGoBang.Common.XStreamUtil;
import JiaoGoBang.Common.Model.Player;
import JiaoGoBang.Common.Model.Room;
import JiaoGoBang.Server.Server;

public class WithdrawService implements Service {

	@Override
	public void execute(Request request, Response response, Socket socket) {
		String mode = (String) request.getParameter("mode");
		if (mode.equals("PVP")) {
			String method = (String) request.getParameter("method");
			String xml;
			PrintStream ps;
			response.setStatus(1);
			if (method.equals("require")) {
				response.setReturnType("WithdrawRequireAction");
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
			} else if (method.equals("confirm")) {
				int confirm = (int) request.getParameter("confirm");
				if (confirm == 0) {
					response.setReturnType("WithdrawAction");
					String userId = (String) request.getParameter("id");
					Player player = (Player) Server.getUser(userId);
					Room room = player.getRoom();
					Color color = room.getAnotherPlayer(player).getColor();
					room.getChessTable().withdraw(color);
					xml = XStreamUtil.toXML(response);
					try {
						ps = new PrintStream(room.getAnotherPlayer(player).getSocket().getOutputStream());
						ps.println(xml);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			response.setReturnType("WithdrawAction");
			String userId = (String) request.getParameter("id");
			Player player = (Player) Server.getUser(userId);
			int whoPlay = (int) request.getParameter("whoPlay");
			Room room = player.getRoom();
			if (whoPlay == 1) {
				Color color = player.getColor();
				room.getChessTable().withdraw(color);
				response.setStatus(1);
				String xml = XStreamUtil.toXML(response);
				try {
					PrintStream ps = new PrintStream(socket.getOutputStream());
					ps.println(xml);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				player.setStatus(5);
			}
		}
	}

}
