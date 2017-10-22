package JiaoGoBang.Server;


import java.net.Socket;

import JiaoGoBang.Common.Request;
import JiaoGoBang.Common.Response;
import JiaoGoBang.Server.Service.Service;

public class ServiceThread extends Thread {
	private Request request;
	private Response response;
	private Socket socket;
	private Service service;

	public ServiceThread(Request request, Response response, Service service, Socket socket) {
		this.socket = socket;
		this.request = request;
		this.response = response;
		this.service = service;
	}

	public void run() {
		service.execute(request, response, this.socket);

	}
}
