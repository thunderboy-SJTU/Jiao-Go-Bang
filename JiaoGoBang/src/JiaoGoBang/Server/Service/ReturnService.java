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

public class ReturnService implements Service {

	@Override
	public void execute(Request request, Response response, Socket socket) {
		response.setReturnType("ReturnAction");
		String userId = (String) request.getParameter("id");
		Player player = (Player) Server.getUser(userId);
		Room room = player.getRoom();
		Player rival = null;
		if (room.getCount() == 2) {
			rival = room.getAnotherPlayer(player);
			response.setStatus(2);
			String xml = XStreamUtil.toXML(response);
			PrintStream ps;
			try {
				ps = new PrintStream(rival.getSocket().getOutputStream());
				ps.println(xml);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		if (player.getStatus() == 2) {
			room.removePrepareCount();
		}
		player.setStatus(0);
		if (room.getPlayer1() == player)
			room.setPlayer1(null);
		else
			room.setPlayer2(null);
		room.removeCount();
		response.setStatus(1);
		String xml = XStreamUtil.toXML(response);
		try {
			PrintStream ps = new PrintStream(socket.getOutputStream());
			ps.println(xml);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (room.getCount() == 0)
			Server.removePVPRoom(room);
		else if(rival != null) {
			while (room.getCount() != 2) {
				Thread.yield();
			}
			response.setReturnType("EnterRoomAction");
			String rivalname = room.getAnotherPlayer(rival).getUsername();
			response.setData("username", rival.getUsername());
			response.setData("rivalname", rivalname);
			response.setStatus(1);
			xml = XStreamUtil.toXML(response);

			try {
				PrintStream ps = new PrintStream(rival.getSocket().getOutputStream());
				ps.println(xml);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
