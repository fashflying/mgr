package mgr.wfengine.define;

import org.jdom2.Element;

public class ActivityActionDefine {
	/**
	 * Action的类型，类的全称
	 */
	private String type;
	/**
	 * Action的构造函数参数
	 */
	private String args;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getArgs() {
		return args;
	}
	public void setArgs(String args) {
		this.args = args;
	}
	public static ActivityActionDefine parse(Element element) {
		ActivityActionDefine def = new ActivityActionDefine();
		
		def.setType(element.getChildText("type"));
		def.setArgs(element.getChildText("args"));
		
		return def;
	}
}
