package JiaoGoBang.Server.Service;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import JiaoGoBang.Common.Request;
import JiaoGoBang.Common.Response;
import JiaoGoBang.Common.XStreamUtil;
import JiaoGoBang.Common.Model.Player;
import JiaoGoBang.Common.Model.Room;
import JiaoGoBang.Server.Server;

public class LoseService implements Service {

	@Override
	public void execute(Request request, Response response, Socket socket) {
		response.setReturnType("LoseAction");
		String mode = (String) request.getParameter("mode");
		if (mode.equals("PVP")) {
			response.setStatus(1);
			String userId = (String) request.getParameter("id");
			Player player = (Player) Server.getUser(userId);
			if (player == null)
				return;
			Room room = player.getRoom();
			if (room == null) {
				return;
			}
			room.reset();
			String xml = XStreamUtil.toXML(response);
			try {
				PrintStream ps = new PrintStream(socket.getOutputStream());
				ps.println(xml);
			} catch (IOException e) {
				e.printStackTrace();
			}
			response.setStatus(2);
			xml = XStreamUtil.toXML(response);
			try {
				PrintStream ps = new PrintStream(room.getAnotherPlayer(player).getSocket().getOutputStream());
				ps.println(xml);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
			response.setStatus(1);
			String userId = (String) request.getParameter("id");
			Player player = (Player) Server.getUser(userId);
			if (player == null)
				return;
			Room room = player.getRoom();
			if (room == null) {
				return;
			}
			int whoPlay = (int)request.getParameter("whoPlay");
			if(whoPlay == 1)
			{
				room.reset();
				String xml = XStreamUtil.toXML(response);
				try {
					PrintStream ps = new PrintStream(socket.getOutputStream());
					ps.println(xml);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else
				player.setStatus(6);
		}

	}

}
