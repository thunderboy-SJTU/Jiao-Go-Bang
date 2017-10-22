package JiaoGoBang.Common.Model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Room {
	private Player player1;
	private Player player2;
	private ChessTable chessTable;
	private int count;
	private int prepareCount;
	private Lock lock = new ReentrantLock();

	public Room(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		this.chessTable = new ChessTable(14, 14);
		count = 1;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}
	
	public Player getAnotherPlayer(Player player)
	{
		if(player.getId().equals(player1.getId())&& (!player.getId().equals(player2.getId())))
			return player2;
		if(player.getId().equals(player2.getId())&& (!player.getId().equals(player1.getId())))
			return player1;
		return null;			
	}

	public int getCount() {
		return count;
	}
	
	public int getPrepareCount()
	{
		
		return prepareCount;
	}

	public ChessTable getChessTable() {
		return chessTable;
	}
    
	
	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}
    
	public void addPlayer(Player player)
	{
		if(player1 == null)
			player1 = player;
		else if(player2 == null)
			player2 = player;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public void setPrepareCount(int prepareCount)
	{
		lock.lock();
		this.prepareCount = prepareCount;
		lock.unlock();
	}
	
	public void addCount() {
		count++;
	}
	
	public void addPrepareCount()
	{
		lock.lock();
		prepareCount++;
		lock.unlock();
	}
	
	public void removePrepareCount()
	{
		lock.lock();
		prepareCount--;
		lock.unlock();
	}
	
	public void reset()
	{
		chessTable = new ChessTable(14,14);
		setPrepareCount(0);
		player1.setStatus(1);
		player2.setStatus(1);
		player1.setColor(null);
		player2.setColor(null);
	}
	
	public int getPlayerNumber(Player player)
	{
		if(player.getId().equals(player1.getId())&& (!player.getId().equals(player2.getId())))
			return 1;
		if(player.getId().equals(player2.getId())&& (!player.getId().equals(player1.getId())))
			return 2;
		return 0;	
	}
}
