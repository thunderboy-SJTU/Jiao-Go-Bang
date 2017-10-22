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
			System.out.println("lalala");
			while (room.getCount() != 2) {
				Thread.yield();
			}
			System.out.println("kakaka");
			String rivalname = room.getAnotherPlayer(player).getUsername();
			response.setData("username", player.getUsername());
			response.setData("rivalname", rivalname);
			response.setStatus(1);
			String xml = XStreamUtil.toXML(response);
			
			try {
				PrintStream ps = new PrintStream(socket.getOutputStream());
				System.out.println(xml);
				ps.println(xml);
				System.out.println(xml);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
