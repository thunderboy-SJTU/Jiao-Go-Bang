package JiaoGoBang.Server.Service;

import java.net.Socket;

import JiaoGoBang.Common.Request;
import JiaoGoBang.Common.Response;

public interface Service {
     public void execute(Request request,Response response,Socket socket);
}
