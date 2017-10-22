package JiaoGoBang.Common;

import java.util.HashMap;
import java.util.Map;

public class Response {
	private String returnType;
	private Map<String, Object> data;
	private int status;

	public Response(String returnType) {
		this.returnType = returnType;
		this.data = new HashMap<String, Object>();
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
    
	public void setStatus(int status)
	{
		this.status = status;
	}
	public void setData(String name, Object object) {
		data.put(name, object);
	}

	public String getReturnType() {
		return returnType;
	}

	public Object getData(String name) {
		return data.get(name);
	}
	
	public int getStatus()
	{
		return status;
	}
}
