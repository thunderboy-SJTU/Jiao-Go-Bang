package JiaoGoBang.Server.Service;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import JiaoGoBang.Common.Request;
import JiaoGoBang.Common.Response;
import JiaoGoBang.Common.XStreamUtil;
import JiaoGoBang.Common.Model.Player;
import JiaoGoBang.Common.Model.User;
import JiaoGoBang.Server.Server;

public class LoginService implements Service {

	@Override
	public void execute(Request request, Response response, Socket socket) {
		response.setReturnType("LoginAction");
		response.setStatus(1);
		String userId = (String)request.getParameter("id");
		String username = (String)request.getParameter("username");
		User user = new User(userId,username,socket);
		Player player = user.convert();
		Server.addUser(player);
		String xml = XStreamUtil.toXML(response);
		try {
			PrintStream ps = new PrintStream(socket.getOutputStream());
			ps.println(xml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
