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

public class CancelPrepareService implements Service {

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
			if(room.getPrepareCount() == 1)			
				 room.removePrepareCount();
				 player.setStatus(1);
				 response.setStatus(3);
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
