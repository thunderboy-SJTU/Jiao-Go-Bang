package JiaoGoBang.Client.Action.Send;


import JiaoGoBang.Client.UI.UI;
import JiaoGoBang.Common.Request;
import JiaoGoBang.Common.XStreamUtil;
import JiaoGoBang.Common.Model.Chess;
import JiaoGoBang.Common.Model.User;

public class Move implements Send {
   private Chess chess;
   private String mode;
   
   public Move(Chess chess,String mode)
	{
		this.mode = mode;
		this.chess = chess;
	}
	public void execute() {
		User user = UI.getUser();
		String id = user.getId();
		Request req = new Request("MoveService");
		req.setParameter("id", id);
		req.setParameter("mode", mode);
		req.setParameter("chess", chess);
		String xml = XStreamUtil.toXML(req);
		user.getPrintStream().println(xml);
		/*ClientThread thread = new ClientThread();
		thread.start();*/
	}
}
