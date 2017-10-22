package JiaoGoBang.Client.Action.Receive;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import JiaoGoBang.Client.UI.UI;
import JiaoGoBang.Common.Response;

public class ReturnAction implements Action {

	@Override
	public void execute(Response response) {
		int status = response.getStatus();
		if (status == 0) {
			Icon icon = new ImageIcon("src/sign-error-icon.png");
			JOptionPane.showMessageDialog(null, "返回过程中遇到错误！", "ERROR", JOptionPane.PLAIN_MESSAGE, icon);
			return;
		}
		if(status == 1)
		{	
			UI.getGameBoard().setEnabled(true);
			UI.getGameBoard().setVisible(false);
			UI.setGameBoard(null);
		    UI.getModeChoose().setVisible(true);
		    return;
		}
		if(status == 2)
		{
			UI.getGameBoard().setRivalname(null);
			UI.getGameBoard().repaint();
		}
		return;		
	}
		
	}

