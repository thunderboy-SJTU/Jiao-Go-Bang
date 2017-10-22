package JiaoGoBang.Common.Model;

import java.awt.Color;
import java.net.Socket;

public class Player extends User{
    public Player(String id, String username, Socket socket) {
		super(id, username, socket);
		color = null;
		room = null;
	}

	private int status;
    private Color color;	
    private Room room;
	
	
	public void setStatus(int status)
	{
		this.status = status;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public int getStatus()
	{
		return status;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setRoom(Room room)
	{ 
		this.room = room;
	}
	
    public Room getRoom()
    {
    	return room;
    }


}
