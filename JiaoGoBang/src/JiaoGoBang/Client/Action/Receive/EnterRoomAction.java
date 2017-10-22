package JiaoGoBang.Client.Action.Receive;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import JiaoGoBang.Client.UI.UI;
import JiaoGoBang.Common.Response;

public class EnterRoomAction implements Action {

	@Override
	public void execute(Response response) {
		int status = response.getStatus();
		if (status == 0) {
			Icon icon = new ImageIcon("src/sign-error-icon.png");
			JOptionPane.showMessageDialog(null, "进入房间过程中遇到错误！", "ERROR", JOptionPane.PLAIN_MESSAGE, icon);
			return;
		}
		String rivalname = (String) response.getData("rivalname");
		UI.getGameBoard().setRivalname(rivalname);
		UI.getGameBoard().repaint();
		return;
	}

}
