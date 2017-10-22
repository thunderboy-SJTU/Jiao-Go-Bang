package JiaoGoBang.Server.NewAI;

public class NextStep {
	
	
   private int chessCount = 0;
   private int winCount = 0;
 //  private int winTotalCount = 0;
    
   public void setChessCount(int chessCount)
   {
	   this.chessCount = chessCount;
   }
   
   public void addChessCount()
   {
	   chessCount++;
   }
   
   public void setWinCount(int winCount)
   {
	   this.winCount = winCount;
   }
   

   
   public void addWinCount(int isWin)
   {
	   if(isWin == 1)
	   {
	       winCount++;
	       //winTotalCount++;
	   }
   }
   
   public int getChessCount()
   {
	   return chessCount;
   }
   
   public int getWinCount()
   {
	   return winCount;
   } 
   
   /*public int getWinTotalCount()
   {
	   return winTotalCount;
   }*/


}
