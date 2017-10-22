package JiaoGoBang.Common.Model;

import java.awt.Color;

public class Chess {
	private int x;
	private int y;
	private Color color;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Color getColor() {
		return color;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public boolean isEqual(Chess chess)
	{
		if(chess.getX() == x && chess.getY() == y)
			return true;
		return false;
	}
	
	public boolean isHorizontal(Chess chess)
	{
		if(chess.getY() == y && chess.color.equals(color))
			return true;
		return false;
	}
	
	public boolean isVertical(Chess chess)
	{
		if(chess.getX() == x&& chess.color.equals(color))
			return true;
		return false;
	}
	
	public boolean isRightSlant(Chess chess)
	{
		if(((chess.getY()-chess.getX())==(y-x))&& chess.color.equals(color))
			return true;
		return false;
	}
	
	public boolean isLeftSlant(Chess chess)
	{
		if(((chess.getY()+chess.getX())==(y+x))&& chess.color.equals(color))
			return true;
		return false;
	}
}
