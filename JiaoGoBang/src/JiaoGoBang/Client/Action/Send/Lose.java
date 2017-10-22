package JiaoGoBang.Client.Action.Send;

import JiaoGoBang.Client.UI.UI;
import JiaoGoBang.Common.Request;
import JiaoGoBang.Common.XStreamUtil;
import JiaoGoBang.Common.Model.User;

public class Lose implements Send {
	private String mode;

	public Lose(String mode) {
		this.mode = mode;
	}

	public void execute() {
		User user = UI.getUser();
		String id = user.getId();
		Request req = new Request("LoseService");
		req.setParameter("id", id);
		req.setParameter("mode", mode);
		if (mode.equals("PVE")) {
			req.setParameter("whoPlay", UI.getGameBoard().getChessBoard().getStatus());
			UI.getGameBoard().setStatus(0);
		}
		String xml = XStreamUtil.toXML(req);
		user.getPrintStream().println(xml);
	}
}
