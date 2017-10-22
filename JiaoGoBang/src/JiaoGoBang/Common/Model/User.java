package JiaoGoBang.Common.Model;

import java.io.PrintStream;
import java.net.Socket;
import java.util.UUID;

public class User {
	private String id;
	private String username;
	private Socket socket;

	public User(String username, Socket socket) {
		this.id = UUID.randomUUID().toString();
		this.username = username;
		this.socket = socket;
	}
	
	public User(String id,String username,Socket socket)
	{
		this.id = id;
		this.username = username;
		this.socket = socket;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public Socket getSocket() {
		return socket;
	}

	public PrintStream getPrintStream() {
		try {
			PrintStream ps = new PrintStream(this.socket.getOutputStream());
			return ps;
		} catch (Exception e) {
			return null;
		}
	}
	
	public Player convert()
	{
		Player player = new Player(id,username,socket);
		player.setStatus(0);
		player.setColor(null);
		return player;
	}
}
