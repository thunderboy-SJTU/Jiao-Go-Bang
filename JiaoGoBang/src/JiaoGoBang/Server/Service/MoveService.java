package JiaoGoBang.Server.Service;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import JiaoGoBang.Common.Request;
import JiaoGoBang.Common.Response;
import JiaoGoBang.Common.XStreamUtil;
import JiaoGoBang.Common.Model.Chess;
import JiaoGoBang.Common.Model.Player;
import JiaoGoBang.Common.Model.Room;
import JiaoGoBang.Server.Server;

public class MoveService implements Service{

	@Override
	public void execute(Request request, Response response, Socket socket) {
		response.setReturnType("MoveAction");
		String mode = (String) request.getParameter("mode");
		if (mode.equals("PVP")) {
            String xml;
            PrintStream ps;
            response.setStatus(1);
			String userId = (String) request.getParameter("id");
			Chess chess =(Chess)request.getParameter("chess");
			Player player = (Player) Server.getUser(userId);
			if (player == null)
				return;
			Room room = player.getRoom();
			if (room == null) {
				return;
			} else {
				room.getChessTable().addChess(chess);
			}
			boolean isWin = room.getChessTable().isWin(chess);
			System.out.println(isWin);
			if(isWin == true)
			{   
				room.reset();
				response.setStatus(2);
				xml = XStreamUtil.toXML(response);
				try {
					ps = new PrintStream(socket.getOutputStream());
					ps.println(xml);
				} catch (IOException e) {
					e.printStackTrace();
				}				
				response.setStatus(3);
			}			
			response.setData("chess", chess);			
			xml = XStreamUtil.toXML(response);
			try {
				ps = new PrintStream(room.getAnotherPlayer(player).getSocket().getOutputStream());
				ps.println(xml);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
