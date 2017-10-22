package JiaoGoBang.Server.NewAI;

import JiaoGoBang.Common.Model.Chess;

public class ChessRatio {
    private Chess chess;
    private Ratio ratio;
    
    
    public ChessRatio(Chess chess, Ratio ratio)
    {
    	this.chess = chess;
    	this.ratio = ratio;
    }
    
    public Chess getChess()
    {
    	return chess;
    }
    
    public Ratio getRatio()
    {
    	return ratio;
    }
    
    public void setChess(Chess chess)
    {
    	this.chess = chess;
    }
    
    public void setRatio(Ratio ratio)
    {
    	this.ratio = ratio;
    }
    
    
}
