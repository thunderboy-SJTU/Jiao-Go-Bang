package JiaoGoBang.Client.Action.Send;

import java.awt.Color;

import JiaoGoBang.Client.UI.UI;
import JiaoGoBang.Common.Request;
import JiaoGoBang.Common.XStreamUtil;
import JiaoGoBang.Common.Model.User;

public class WithdrawRequire implements Send {
	private String mode;

	public WithdrawRequire(String mode) {
		this.mode = mode;
	}

	public void execute() {
		User user = UI.getUser();
		String id = user.getId();
		Request req = new Request("WithdrawService");
		req.setParameter("id", id);
		req.setParameter("mode", mode);
		req.setParameter("method", "require");
		if (mode.equals("PVE")) {
			int count = UI.getGameBoard().getChessBoard().getChessTable().getChessCount();
			if (count == 0 || (count == 1 && UI.getGameBoard().getUserBoard().getColor().equals(Color.WHITE)))
				return;
			req.setParameter("whoPlay", UI.getGameBoard().getChessBoard().getStatus());
			UI.getGameBoard().setStatus(0);
		}
		String xml = XStreamUtil.toXML(req);
		user.getPrintStream().println(xml);
	}
}
