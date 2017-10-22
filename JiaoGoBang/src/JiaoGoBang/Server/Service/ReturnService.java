package JiaoGoBang.Server.Service;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import JiaoGoBang.Common.Request;
import JiaoGoBang.Common.Response;
import JiaoGoBang.Common.XStreamUtil;
import JiaoGoBang.Common.Model.Player;
import JiaoGoBang.Common.Model.Room;
import JiaoGoBang.Common.Model.User;
import JiaoGoBang.Server.Server;

public class ReturnService implements Service {
	
	@Override
	public void execute(Request request, Response response, Socket socket) {
		response.setReturnType("ReturnAction");
		String userId = (String)request.getParameter("id");
		Player player = (Player)Server.getUser(userId);
		Room room = player.getRoom();
		if(room.getCount() == 1)
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
