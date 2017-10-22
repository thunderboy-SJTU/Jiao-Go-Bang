package JiaoGoBang.Client.Action.Receive;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import javax.swing.JOptionPane;

import JiaoGoBang.Client.UI.UI;
import JiaoGoBang.Common.Response;

public class PrepareAction implements Action {

	@Override
	public void execute(Response response) {
		int status = response.getStatus();
		if (status == 0) {
			Icon icon = new ImageIcon("src/sign-error-icon.png");
			JOptionPane.showMessageDialog(null, "准备过程中遇到错误！", "ERROR", JOptionPane.PLAIN_MESSAGE, icon);
			return;
		}
		if (status == 3) {
			UI.getGameBoard().getPrepareButton().setText("准备");
			return;
		}
		String colorName = (String) response.getData("color");
		Color color;

		if (colorName.equals("black")) {
			color = Color.BLACK;
			UI.getGameBoard().setStatus(1);

		} else {
			color = Color.WHITE;
			if (UI.getGameBoard().getChessBoard().getChessTable().getChessCount() != 0)
				UI.getGameBoard().setStatus(0);
			/*
			 * ClientThread thread = new ClientThread(); thread.start();
			 */
		}
		UI.getGameBoard().setColor(color);
		UI.getGameBoard().getPrepareButton().setEnabled(false);
		UI.getGameBoard().getWithdrawButton().setEnabled(true);
		UI.getGameBoard().getReturnButton().setText("认输");
		UI.getGameBoard().repaint();
		return;

	}

}
