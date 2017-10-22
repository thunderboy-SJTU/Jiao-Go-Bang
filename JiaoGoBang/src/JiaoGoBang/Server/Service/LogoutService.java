package JiaoGoBang.Server.Service;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import JiaoGoBang.Common.Request;
import JiaoGoBang.Common.Response;
import JiaoGoBang.Common.XStreamUtil;
import JiaoGoBang.Common.Model.User;
import JiaoGoBang.Server.Server;

public class LogoutService implements Service {

	@Override
	public void execute(Request request, Response response, Socket socket) {
		response.setReturnType("LogoutAction");
		String userId = (String)request.getParameter("id");
		User user = Server.getUser(userId);
		Server.removeUser(user);
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
