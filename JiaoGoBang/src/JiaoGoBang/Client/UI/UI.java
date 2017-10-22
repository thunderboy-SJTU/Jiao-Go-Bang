package JiaoGoBang.Client.UI;

import JiaoGoBang.Common.Model.User;

public class UI {
   private static Begin begin;
   private static LoginBoard loginBoard;
   private static ModeChoose modeChoose;
   private static GameBoard gameBoard;
   private static User user;
   
   public static void setBegin(Begin begin)
   {
	   UI.begin = begin;
   }
   
   public static void setLoginBoard(LoginBoard loginBoard)
   {
	   UI.loginBoard = loginBoard;
   }
   
   public static void setModeChoose(ModeChoose modeChoose)
   {
	  UI.modeChoose = modeChoose;
   }
   
   public static void setGameBoard(GameBoard gameBoard)
   {
	   UI.gameBoard = gameBoard;
   }
   
   public static void setUser(User user)
   {
	   UI.user = user;
   }
   
   public static Begin getBegin()
   {
	   return begin;
   }
   
   public static LoginBoard getLoginBoard()
   {
	   return loginBoard;
   }
   
   public static ModeChoose getModeChoose()
   {
	   return modeChoose;
   }
   
   public static GameBoard getGameBoard()
   {
	   return gameBoard;
   }
   
   public static User getUser()
   {
	   return user;
   }
}
