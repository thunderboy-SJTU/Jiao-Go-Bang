package JiaoGoBang.Client.Action.Send;



import JiaoGoBang.Client.ClientThread;
import JiaoGoBang.Client.UI.GameBoard;
import JiaoGoBang.Client.UI.UI;
import JiaoGoBang.Common.Request;
import JiaoGoBang.Common.XStreamUtil;
import JiaoGoBang.Common.Model.User;

public class EnterRoom {
	private String mode;

	public EnterRoom(String mode) {
		this.mode = mode;
	}

	public void execute() {
		User user = UI.getUser();
		String id = user.getId();
		Request req = new Request("RoomService");
		req.setParameter("id", id);
		req.setParameter("mode", mode);
		String xml = XStreamUtil.toXML(req);
		user.getPrintStream().println(xml);
		GameBoard game = new GameBoard(null, mode);
		UI.setGameBoard(game);
		UI.getModeChoose().setVisible(false);
		game.setVisible(true);
		ClientThread thread = new ClientThread();
		thread.start();
	}
	
}
