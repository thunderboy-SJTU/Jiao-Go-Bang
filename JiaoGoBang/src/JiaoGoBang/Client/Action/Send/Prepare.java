package JiaoGoBang.Client.Action.Send;



import JiaoGoBang.Client.UI.UI;
import JiaoGoBang.Common.Request;
import JiaoGoBang.Common.XStreamUtil;
import JiaoGoBang.Common.Model.User;

public class Prepare implements Send {
	private String mode;

	
	public Prepare(String mode)
	{
		this.mode = mode;
	}
	public void execute() {
		User user = UI.getUser();
		String id = user.getId();
		Request req = new Request("PrepareService");
		req.setParameter("id", id);
		req.setParameter("mode", mode);
		String xml = XStreamUtil.toXML(req);
		user.getPrintStream().println(xml);
		/*ClientThread thread = new ClientThread();
		thread.start();*/
	}
}
