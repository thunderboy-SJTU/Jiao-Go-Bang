package JiaoGoBang.Client.Action.Receive;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import JiaoGoBang.Client.UI.UI;
import JiaoGoBang.Common.Response;

public class WithdrawAction implements Action {

	@Override
	public void execute(Response response) {
		int status = response.getStatus();
		if (status == 0) {
			Icon icon = new ImageIcon("src/sign-error-icon.png");
			JOptionPane.showMessageDialog(null, "悔棋过程中遇到错误！", "ERROR", JOptionPane.PLAIN_MESSAGE, icon);
			return;
		}
		if (status == 1) {
			Color color = UI.getGameBoard().getUserBoard().getColor();
			UI.getGameBoard().getChessBoard().getChessTable().withdraw(color);
			UI.getGameBoard().setStatus(1);
			UI.getGameBoard().repaint();
		}
	}
}
