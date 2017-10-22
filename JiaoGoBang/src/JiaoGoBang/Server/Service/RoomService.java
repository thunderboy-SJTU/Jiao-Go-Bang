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

public class RoomService implements Service {

	@Override
	public void execute(Request request, Response response, Socket socket) {
		response.setReturnType("EnterRoomAction");
		String mode = (String) request.getParameter("mode");
		if (mode.equals("PVP")) {
			String userId = (String) request.getParameter("id");
			Player player = (Player) Server.getUser(userId);
			if (player == null)
				return;
			Room room = Server.findRoom();
			if (room == null) {
				room = new Room(player, null);
				Server.addPVPRoom(room);
			} else {
				room.addPlayer(player);
				room.addCount();
			}
			player.setRoom(room);
			player.setStatus(1);
			while (room.getCount() != 2) {
				Thread.yield();
			}
			String rivalname = room.getAnotherPlayer(player).getUsername();
			response.setData("username", player.getUsername());
			response.setData("rivalname", rivalname);
			response.setStatus(1);
			String xml = XStreamUtil.toXML(response);
			
			try {
				PrintStream ps = new PrintStream(socket.getOutputStream());
				ps.println(xml);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(mode.equals("PVE"))
		{
			String userId = (String) request.getParameter("id");
			Player player = (Player) Server.getUser(userId);
			if (player == null)
				return;
			Room room = new Room(player, null);
			player.setRoom(room);
			response.setData("username", player.getUsername());
			response.setData("rivalname", "Computer");
			response.setStatus(1);
			String xml = XStreamUtil.toXML(response);
			try {
				PrintStream ps = new PrintStream(socket.getOutputStream());
				ps.println(xml);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

}
