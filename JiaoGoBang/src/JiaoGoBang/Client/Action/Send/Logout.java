package JiaoGoBang.Client.Action.Send;

import JiaoGoBang.Client.ClientThread;
import JiaoGoBang.Client.UI.UI;
import JiaoGoBang.Common.Request;
import JiaoGoBang.Common.XStreamUtil;
import JiaoGoBang.Common.Model.User;

public class Logout {


	public void execute() {
		User user = UI.getUser();
		String id = user.getId();
		Request req = new Request("LogoutService");
		req.setParameter("id", id);
		String xml = XStreamUtil.toXML(req);
		user.getPrintStream().println(xml);
		ClientThread thread = new ClientThread();
		thread.start();
	}
}
