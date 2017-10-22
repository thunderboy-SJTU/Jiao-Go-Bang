package JiaoGoBang.Client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



import JiaoGoBang.Client.Action.Receive.Action;
import JiaoGoBang.Client.UI.UI;
import JiaoGoBang.Common.Response;
import JiaoGoBang.Common.XStreamUtil;

public class ClientThread extends Thread {
	private static Lock lock = new ReentrantLock();

	public void run() {

		BufferedReader reader;
		InputStream is;

		try {
			lock.lock();
			is = UI.getUser().getSocket().getInputStream();
			reader = new BufferedReader(new InputStreamReader(is));

			String content;

			if ((content = reader.readLine()) != null) {
				System.out.println(content);
				Response response = getResponse(content);
				lock.unlock();
				Action action = getAction(response.getReturnType());
				action.execute(response);
			} else
				lock.unlock();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Action getAction(String returnType) {
		try {

			Action action = (Action) Class.forName("JiaoGoBang.Client.Action.Receive." + returnType).newInstance();
			return action;

		} catch (Exception e) {
			return null;
		}
	}

	private Response getResponse(String xml) {
		return (Response) XStreamUtil.fromXML(xml);
	}
}
