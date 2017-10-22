package JiaoGoBang.Client.Action.Receive;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import JiaoGoBang.Client.UI.UI;
import JiaoGoBang.Common.Request;
import JiaoGoBang.Common.Response;
import JiaoGoBang.Common.XStreamUtil;

public class WithdrawRequireAction implements Action {

	@Override
	public void execute(Response response) {
		int status = response.getStatus();
		if (status == 0) {
			Icon icon = new ImageIcon("src/sign-error-icon.png");
			JOptionPane.showMessageDialog(null, "悔棋过程中遇到错误！", "ERROR", JOptionPane.PLAIN_MESSAGE, icon);
			return;
		}
		if (status == 1) {
			int confirm = JOptionPane.showConfirmDialog(null, "对面请求悔棋，是否同意？", "Withdraw", JOptionPane.YES_NO_OPTION);
			if (confirm == 0) {
				int count = UI.getGameBoard().getChessBoard().getChessTable().getChessCount();
				if (count == 0 || (count == 1 && UI.getGameBoard().getUserBoard().getColor().equals(Color.BLACK)))
					return;
					UI.getGameBoard().setStatus(0);
					if (UI.getGameBoard().getUserBoard().getColor().equals(Color.BLACK))
						UI.getGameBoard().getChessBoard().getChessTable().withdraw(Color.WHITE);
					else if (UI.getGameBoard().getUserBoard().getColor().equals(Color.WHITE))
						UI.getGameBoard().getChessBoard().getChessTable().withdraw(Color.BLACK);
					UI.getGameBoard().repaint();
			}
			Request req = new Request("WithdrawService");
			req.setParameter("id", UI.getUser().getId());
			req.setParameter("mode", "PVP");
			req.setParameter("method", "confirm");
			req.setParameter("confirm", confirm);
			String xml = XStreamUtil.toXML(req);
			UI.getUser().getPrintStream().println(xml);
		}

	}

}
