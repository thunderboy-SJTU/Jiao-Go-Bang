package JiaoGoBang.Client.Action.Receive;

import javax.swing.JOptionPane;

import JiaoGoBang.Client.UI.UI;
import JiaoGoBang.Common.Response;

public class LoseAction implements Action {

	@Override
	public void execute(Response response) {
		int status = response.getStatus();
		if (status == 1) {
			JOptionPane.showMessageDialog(null, "�����ˣ�", "Lose", JOptionPane.PLAIN_MESSAGE);
			UI.getGameBoard().reset();
			return;
		}
		if (status == 2) {
			JOptionPane.showMessageDialog(null, "��Ӯ�ˣ�", "Win", JOptionPane.PLAIN_MESSAGE);
			UI.getGameBoard().reset();
			return;
		}
		
	}

}
