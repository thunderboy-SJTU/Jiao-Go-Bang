package JiaoGoBang.Client.Action.Receive;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import javax.swing.JOptionPane;

import JiaoGoBang.Client.UI.ModeChoose;
import JiaoGoBang.Client.UI.UI;
import JiaoGoBang.Common.Response;

public class LoginAction implements Action {

	@Override
	public void execute(Response response) {
		int status = response.getStatus();
		if (status == 0) {
			UI.setUser(null);
			Icon icon = new ImageIcon("src/sign-error-icon.png");
			JOptionPane.showMessageDialog(null, "登录过程中遇到错误！", "ERROR", JOptionPane.PLAIN_MESSAGE, icon);
			return;
		}
		ModeChoose mode = new ModeChoose((UI.getBegin().getUser()));
		UI.setModeChoose(mode);
		mode.setVisible(true);
		UI.getBegin().setEnabled(true);
		UI.getBegin().setVisible(false);
		//UI.getLoginBoard().setVisible(false);
		//UI.getLoginBoard().setEnabled(true);
		return;
	}

}
