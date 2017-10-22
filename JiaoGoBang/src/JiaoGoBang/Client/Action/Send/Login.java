package JiaoGoBang.Client.Action.Send;

import JiaoGoBang.Common.Request;
import JiaoGoBang.Common.XStreamUtil;
import JiaoGoBang.Common.Model.User;

public class Login implements Send {
	private User user;

	public Login(User user) {
		this.user = user;

	}

	public void execute() {
		String id = user.getId();
		String username = user.getUsername();
		Request req = new Request("LoginService");
		req.setParameter("id", id);
		req.setParameter("username", username);
		String xml = XStreamUtil.toXML(req);
		user.getPrintStream().println(xml);
		/*
		 * ClientThread thread = new ClientThread(); thread.start();
		 */
	}
}
