package JiaoGoBang.Common;

import java.util.HashMap;
import java.util.Map;

public class Request {
   private String method;
   private Map<String,Object> parameters;
   
   public Request(String method)
   {
	   this.method = method;
	   this.parameters = new HashMap<String,Object>();
   }
   
   public void setMethod(String method)
   {
	   this.method = method;
   }
   
   public void setParameter(String name,Object object)
   {
	   parameters.put(name, object);
   }
   
   public String getMethod()
   {
	   return method;
   }
   
   public Object getParameter(String name)
   {
	   return parameters.get(name);
   }
}
