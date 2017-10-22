package JiaoGoBang.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;


import JiaoGoBang.Common.Request;
import JiaoGoBang.Common.Response;
import JiaoGoBang.Common.XStreamUtil;
import JiaoGoBang.Server.Service.Service;

public class ServerThread extends Thread {
	private Socket socket;
	
    
	public ServerThread(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			String content;
			while ((content = reader.readLine()) != null) {
				Request request = getRequest(content);
				Response response = new Response(request.getMethod());
				Service service = getService(request.getMethod());
				new ServiceThread(request,response,service,socket).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Request getRequest(String xml) {
		Request r = (Request) XStreamUtil.fromXML(xml);
		return r;
	}

	private Service getService(String method)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Service service = (Service) Class.forName("JiaoGoBang.Server.Service."+method).newInstance();
		return service;
	}
}
