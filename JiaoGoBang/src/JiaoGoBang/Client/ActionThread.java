package JiaoGoBang.Client;



import JiaoGoBang.Client.Action.Receive.Action;
import JiaoGoBang.Common.Response;


public class ActionThread extends Thread {
		private Response response;

		public ActionThread(Response response) {
			this.response = response;
		}

		public void run() {
			Action action = getAction(response.getReturnType());
			System.out.println(response);
			action.execute(response);
		}
		
		private Action getAction(String returnType) {
			try {

				Action action = (Action) Class.forName("JiaoGoBang.Client.Action.Receive." + returnType).newInstance();
				return action;

			} catch (Exception e) {
				return null;
			}
		}

}
