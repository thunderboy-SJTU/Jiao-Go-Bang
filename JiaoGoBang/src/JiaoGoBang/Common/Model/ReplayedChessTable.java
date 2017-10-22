package JiaoGoBang.Common.Model;


public class ReplayedChessTable extends ChessTable {

	
	private int whoWin;
	
	public ReplayedChessTable(int rows, int cols) {
		super(rows, cols);
	}
	
	public void setWhoWin(int whoWin)
	{
		this.whoWin = whoWin;
	}
	
	public int getWhoWin()
	{
		return whoWin;
	}
	

}
