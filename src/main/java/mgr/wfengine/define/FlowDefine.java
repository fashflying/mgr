package mgr.wfengine.define;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class FlowDefine {
	private String id;
	private String name;
	private String title;
	private int version;
	private String type;
	private List<ActivityDefine> activities;
	private List<VariableDefine> variables;
	
	public FlowDefine(){
		this.activities = new ArrayList<ActivityDefine>();
		this.variables = new ArrayList<VariableDefine>();
	}
	
	public static FlowDefine parse(String content) throws Exception {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader(content));
		Element rootElement = document.getRootElement();
		
		FlowDefine define = new FlowDefine();
		//解析流程定义字段
		define.setId(rootElement.getChildText("id"));
		define.setVersion(Integer.parseInt(rootElement.getChildText("version")));
		define.setName(rootElement.getChildText("title"));
		define.setTitle(rootElement.getChildText("title"));
		define.setType(rootElement.getChildText("type"));
		
		List<Element> activities = rootElement.getChild("activities").getChildren();
		for(Element ele : activities){
			define.getActivities().add(ActivityDefine.parse(ele));
		}
		
		//解析流程变量
		if(rootElement.getChild("variables") != null){
			List<Element> vars = rootElement.getChild("variables").getChildren();
			for(Element ele : vars){
				define.getVariables().add(VariableDefine.parse(ele));
			}
		}
		return define;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public List<ActivityDefine> getActivities() {
		return activities;
	}
	public void setActivities(List<ActivityDefine> activities) {
		this.activities = activities;
	}


	public List<VariableDefine> getVariables() {
		return variables;
	}

	public void setVariables(List<VariableDefine> variables) {
		this.variables = variables;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
