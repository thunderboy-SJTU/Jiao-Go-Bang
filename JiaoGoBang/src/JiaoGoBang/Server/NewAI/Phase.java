package JiaoGoBang.Server.NewAI;
import java.util.HashMap;
import java.util.Map;

public class Phase {
   private Map<String,NextStep> nextStepMap= new HashMap<String,NextStep>();
   
   public void setNextStepMap(Map<String,NextStep> nextStepMap)
   {
	   this.nextStepMap = nextStepMap;
   }
   
   public Map<String,NextStep> getNextStepMap()
   {
	   return nextStepMap;
   }
   
   
   public void add(Position position , int isWin)
   {
	   String str = position.toString();
	   if(nextStepMap.containsKey(str))
	   {
		   
		   NextStep nextstep = nextStepMap.get(str);
		   nextstep.addChessCount();
		   nextstep.addWinCount(isWin);
			   
	   }
	   else
	   {
		  NextStep nextstep = new NextStep();
		  nextstep.addChessCount();
		  nextstep.addWinCount(isWin);
	      nextStepMap.put(str,nextstep);
	   }
   }
   
   public PhaseRatio convert()
   {
	   int totalCount = 0;
	   PhaseRatio phaseRatio = new PhaseRatio();
	   for(String position : nextStepMap.keySet())
	   {
		   NextStep nextStep = nextStepMap.get(position);
		   totalCount += nextStep.getChessCount();	   
	   }
	   for(String position : nextStepMap.keySet())
	   {
		   NextStep nextStep = nextStepMap.get(position);
		   double chooseRatio = ((double)nextStep.getChessCount())/((double)totalCount);
		   double winRatio = ((double)nextStep.getWinCount())/((double)nextStep.getChessCount());
		   Ratio ratio = new Ratio(chooseRatio,winRatio);
		   phaseRatio.addRatio(position, ratio);   
	   }
	   return phaseRatio;	   
   }
}
