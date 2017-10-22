package JiaoGoBang.Client.Action.Receive;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import JiaoGoBang.Client.UI.UI;
import JiaoGoBang.Common.Response;

public class LogoutAction implements Action {

	@Override
	public void execute(Response response) {
		int status = response.getStatus();
		if (status == 0) {
			Icon icon = new ImageIcon("src/sign-error-icon.png");
			JOptionPane.showMessageDialog(null, "登出过程中遇到错误！", "ERROR", JOptionPane.PLAIN_MESSAGE, icon);
			return;
		}
		UI.getModeChoose().setVisible(false);
		UI.getBegin().setVisible(true);
		UI.setUser(null);     
		return;		
	}
}
