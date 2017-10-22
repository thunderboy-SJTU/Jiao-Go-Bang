package JiaoGoBang.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import JiaoGoBang.Common.Model.Room;
import JiaoGoBang.Common.Model.User;
import JiaoGoBang.Server.NewAI.NewAI;

public class Server {
    private ServerSocket serverSocket;
    private static List<User> onlineUsers = new ArrayList<User>();
    private static List<Room> PVProoms = new ArrayList<Room>();
   
    public Server() throws IOException
    {
        serverSocket = new ServerSocket(10000);
        NewAI newAI = new NewAI(null);
        newAI.importFile();
        while(true)
        {
        	Socket socket = serverSocket.accept();
        	new ServerThread(socket).start();       	
        }
    }
    
    public static void addUser(User user)
    {
    	onlineUsers.add(user);
    }
    
    public static void removeUser(User user)
    {
    	if(user != null)
    	{
    		for(int i=0;i<onlineUsers.size();i++)
        		if(onlineUsers.get(i).getId().equals(user.getId()))
        			onlineUsers.remove(i);       				       			
    	}
    }
    
    public static void addPVPRoom(Room room)
    {
    	PVProoms.add(room);
    }
    

    public static void removePVPRoom(Room room)
    {
    	for(int i=0;i<PVProoms.size();i++)
    		if(PVProoms.get(i) == room)
    			PVProoms.remove(i);       	
    }
    
    public static Room findRoom()
    {
    	for(int i = 0;i<PVProoms.size();i++)
    	{
    		if(PVProoms.get(i).getCount() == 1)
    			return PVProoms.get(i);
    	}
    	return null;
    }
    
    public static List<User> getUserList()
    {
    	return onlineUsers;
    }
    
    public static User getUser(String userId)
    {
    	for(int i=0;i<onlineUsers.size();i++)
    		if(onlineUsers.get(i).getId().equals(userId))
    			return onlineUsers.get(i);
    	return null;
    }
    

    public static void main(String[] args) throws IOException{  
	      new Server();
	  }  
}
