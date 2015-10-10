package mgr.wfengine.define;

import org.jdom2.Element;

public class RouteDefine {
	private String id;
	private String name;
	private String to;
	private String condition;
	/**
	 * 本路由使用那组参与者
	 */
	private int participantGroup;
	private int index;

	public static RouteDefine parse(Element element) {
		//解析路由定义
		RouteDefine route = new RouteDefine();
		route.setId(element.getChildText("id"));
		route.setName(element.getChildText("name"));
		route.setTo(element.getChildText("to"));
		route.setCondition(element.getChildText("condition"));
		route.setIndex(Integer.parseInt(element.getChildText("index")));
		route.setParticipantGroup(Integer.parseInt(element.getChildText("partgroup")));
		return route;
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


	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getParticipantGroup() {
		return participantGroup;
	}

	public void setParticipantGroup(int participantGroup) {
		this.participantGroup = participantGroup;
	}
}
