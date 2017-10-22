package JiaoGoBang.Client.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class UserBoard extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 300;
	public static final int HEIGHT = 300;
	private UserBlock user1;
	private UserBlock user2;
	
	public UserBoard(String userOne,String userTwo)
	{
		user1 = new UserBlock(userOne);
		user2 = new UserBlock(userTwo);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(null);
		setOpaque(false);
		add(user1,BorderLayout.NORTH);
		add(user2,BorderLayout.CENTER);
	}
	
	public void setUser1(String username)
	{
		user1.setUser(username);
	}
	
	public void setUser2(String username)
	{
		user2.setUser(username);
	}
	
	public Color getColor()
	{
		return user1.getColor();
	}
	
	public void chooseBlack(boolean bool)
	{
		if(user1 != null && user2 != null)
		{
		  if(bool == true)
		  {
			  user1.setColor(Color.BLACK);
			  user2.setColor(Color.WHITE);
		  }
		  else
		  {
			  user1.setColor(Color.WHITE);
			  user2.setColor(Color.BLACK);
		  }
		}
			
	}
	
	public void resetColor()
	{
		user1.setColor(null);
		user2.setColor(null);
	}

}
