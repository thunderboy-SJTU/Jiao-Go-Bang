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

public class PassService implements Service {

	@Override
	public void execute(Request request, Response response, Socket socket) {
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
		}
		
	}

