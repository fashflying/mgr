package mgr.wfengine.define;

import org.jdom2.Element;

public class VariableDefine {
	private String name;
	private String type;
	private String value;
	
	public static VariableDefine parse(Element element){
		VariableDefine var = new VariableDefine();
		
		var.setName(element.getChildText("name"));
		var.setType(element.getChildText("type"));
		var.setValue(element.getChildText("value"));
		
		return var;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
