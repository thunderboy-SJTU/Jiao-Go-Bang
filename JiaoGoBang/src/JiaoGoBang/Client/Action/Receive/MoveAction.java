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
			JOptionPane.showMessageDialog(null, "�����������������", "ERROR", JOptionPane.PLAIN_MESSAGE, icon);
			return;
		}
		if (status == 2) {
			JOptionPane.showMessageDialog(null, "��Ӯ�ˣ�", "Win", JOptionPane.PLAIN_MESSAGE);
			UI.getGameBoard().reset();
			return;
		}
		if (status == 3) {
			Chess chess = (Chess) response.getData("chess");
			UI.getGameBoard().getChessBoard().getChessTable().addChess(chess);
			UI.getGameBoard().repaint();
			JOptionPane.showMessageDialog(null, "�����ˣ�", "Lose", JOptionPane.PLAIN_MESSAGE);
			UI.getGameBoard().reset();
			return;
		}
		if(status == 4)
			
		{
			UI.getGameBoard().setStatus(1);
			UI.getGameBoard().repaint();
			return;
		}
		if(status == 5)
		{
			JOptionPane.showMessageDialog(null, "���壡", "Draw", JOptionPane.PLAIN_MESSAGE);
			UI.getGameBoard().reset();
			return;
		}
		if(status == 6)
		{
			Chess chess = (Chess) response.getData("chess");
			UI.getGameBoard().getChessBoard().getChessTable().addChess(chess);
			UI.getGameBoard().repaint();
			JOptionPane.showMessageDialog(null, "���壡", "Draw", JOptionPane.PLAIN_MESSAGE);
			UI.getGameBoard().reset();
			return;
		}
		Chess chess = (Chess) response.getData("chess");
		UI.getGameBoard().getChessBoard().getChessTable().addChess(chess);
		UI.getGameBoard().setStatus(1);
		UI.getGameBoard().repaint();
		
	}

}
