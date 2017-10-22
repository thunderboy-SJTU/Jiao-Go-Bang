package JiaoGoBang.Client.Action.Receive;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import javax.swing.JOptionPane;

import JiaoGoBang.Client.UI.UI;
import JiaoGoBang.Common.Response;
import JiaoGoBang.Common.Model.Chess;

public class MoveAction implements Action {

	@Override
	public void execute(Response response) {
		int status = response.getStatus();
		if (status == 0) {
			Icon icon = new ImageIcon("src/sign-error-icon.png");
			JOptionPane.showMessageDialog(null, "下棋过程中遇到错误！", "ERROR", JOptionPane.PLAIN_MESSAGE, icon);
			return;
		}
		if (status == 2) {
			JOptionPane.showMessageDialog(null, "你赢了！", "Win", JOptionPane.PLAIN_MESSAGE);
			UI.getGameBoard().reset();
			return;
		}
		if (status == 3) {
			JOptionPane.showMessageDialog(null, "你输了！", "Lose", JOptionPane.PLAIN_MESSAGE);
			UI.getGameBoard().reset();

			return;
		}
		if(status == 4)
			
		{
			System.out.println("hee3");
			UI.getGameBoard().setStatus(1);
			UI.getGameBoard().repaint();
			return;
		}
		Chess chess = (Chess) response.getData("chess");
		UI.getGameBoard().getChessBoard().getChessTable().addChess(chess);
		UI.getGameBoard().setStatus(1);
		UI.getGameBoard().repaint();
		
	}

}
