package JiaoGoBang.Common;

import com.thoughtworks.xstream.XStream;

public class XStreamUtil {
	private static XStream xstream = new XStream();

	public static Object fromXML(String xml) {
		String a = xml.replaceAll("\n", "");
		String s = a.replaceAll("\r", "");
		return xstream.fromXML(s);
	}	

	public static String toXML(Object obj) {
		String xml = xstream.toXML(obj);
		String a = xml.replaceAll("\n", "");
		String s = a.replaceAll("\r", "");
		s.concat("\n");
		return s;
	}
}
