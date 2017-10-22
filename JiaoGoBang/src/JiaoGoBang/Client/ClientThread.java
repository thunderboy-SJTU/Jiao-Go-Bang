package JiaoGoBang.Client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;



import JiaoGoBang.Client.UI.UI;
import JiaoGoBang.Common.Response;
import JiaoGoBang.Common.XStreamUtil;

public class ClientThread extends Thread {
	public void run() {

		BufferedReader reader;
		InputStream is;

		try {			
			is = UI.getUser().getSocket().getInputStream();
			reader = new BufferedReader(new InputStreamReader(is));
			String content;	
			while ((content = reader.readLine()) != null) {
				System.out.println(content);
				Response response = getResponse(content);			
				new ActionThread(response).start();
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private Response getResponse(String xml) {
		return (Response) XStreamUtil.fromXML(xml);
	}
}
