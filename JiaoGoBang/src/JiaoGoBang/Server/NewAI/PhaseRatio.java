package JiaoGoBang.Server.NewAI;

import java.util.HashMap;
import java.util.Map;

public class PhaseRatio {
	 private Map<String,Ratio> ratioMap= new HashMap<String,Ratio>();
	 
	 public void addRatio(String position,Ratio ratio)
	 { 

		 if(!ratioMap.containsKey(position))
			 ratioMap.put(position, ratio);
	 }
	 
	 public void setRatioMap(Map<String,Ratio> ratioMap)
	 {
		 this.ratioMap = ratioMap;
	 }
	 
	 
	 public Map<String,Ratio> getRatioMap()
	 {
		 return ratioMap;
	 }
	 
	 
}
