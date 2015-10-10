package mgr.wfengine.define;

import org.jdom2.Element;

public class ParticipantDefine {
	private String id;
	private String name;
	private String type;
	/**
	 * 参与者的范围，按组织架构筛选
	 */
	private int scope;
	/**
	 * 参与者分组
	 */
	private int group;
	
	public static ParticipantDefine parse(Element element){
		ParticipantDefine par = new ParticipantDefine();
		
		par.setName(element.getChildText("name"));
		par.setType(element.getChildText("type"));
		par.setId(element.getChildText("id"));
		par.setScope(Integer.parseInt(element.getChildText("scope")));
		par.setGroup(Integer.parseInt(element.getChildText("group")));
		
		return par;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public int getScope() {
		return scope;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}
}
