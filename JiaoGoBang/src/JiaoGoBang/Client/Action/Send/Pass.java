package JiaoGoBang.Client.Action.Send;

import JiaoGoBang.Client.UI.UI;
import JiaoGoBang.Common.Request;
import JiaoGoBang.Common.XStreamUtil;
import JiaoGoBang.Common.Model.User;

public class Pass implements Send {

	private String mode;

	public Pass(String mode) {
		this.mode = mode;
	}

	public void execute() {
		User user = UI.getUser();
		String id = user.getId();
		Request req = new Request("PassService");
		req.setParameter("id", id);
		req.setParameter("mode", mode);
		String xml = XStreamUtil.toXML(req);
		user.getPrintStream().println(xml);
	}
}
