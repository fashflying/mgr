package mgr.wfengine.define;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Element;

import com.alibaba.fastjson.annotation.JSONField;

public class ActivityDefine {
	private String id;
	private String name;
	private String type;
	
	/**
	 * 活动可能需要某种类型的额外处理，定义处理器类型
	 * 如：转流程
	 */
	@JSONField(serialize=false)
	private String processorType;
	@JSONField(serialize=false)
	private String transformFlowID;//转流程时，新流程的id
	
	
	private String form;
	private String formname;
	/**
	 * 超时时间，单位为小时
	 */
	private int timeout;
	/**
	 * 超时后的提醒方式
	 */
	private String timeoutMethod;

	@JSONField(serialize=false)
	private List<RouteDefine> routes;

	@JSONField(serialize=false)
	private List<VariableDefine> variables;

	@JSONField(serialize=false)
	private List<ParticipantGroupDefine> participants;
	
	@JSONField(serialize=false)
	private Map<String, String> args;

	@JSONField(serialize=false)
	private List<ActivityActionDefine> beforeCreateActions;
	@JSONField(serialize=false)
	private List<ActivityActionDefine> afterCreateActions;
	@JSONField(serialize=false)
	private List<ActivityActionDefine> beforeFinishActions;
	@JSONField(serialize=false)
	private List<ActivityActionDefine> afterFinishActions;

	public ActivityDefine(){
		this.setParticipants(new ArrayList<ParticipantGroupDefine>());
		this.variables = new ArrayList<VariableDefine>();
		this.routes = new ArrayList<RouteDefine>();
		this.args = new HashMap<String,String>();
		this.beforeCreateActions = new ArrayList<ActivityActionDefine>();
		this.afterCreateActions = new ArrayList<ActivityActionDefine>();
		this.beforeFinishActions = new ArrayList<ActivityActionDefine>();
		this.afterFinishActions = new ArrayList<ActivityActionDefine>();
	}

	public static ActivityDefine parse(Element element){
		ActivityDefine define = new ActivityDefine();
		
		//活动字段解析
		define.setId(element.getChildText("id"));
		define.setName(element.getChildText("name"));
		define.setType(element.getChildText("type"));
		define.setForm(element.getChildText("from"));
		define.setFormname(element.getChildText("fromname"));
		define.setTimeout(Integer.parseInt(element.getChildText("timeout")));
		define.setTimeoutMethod(element.getChildText("timeoutmethod"));
		define.setProcessorType(element.getChildText("processorType"));
		
		//参与者定义解析
		if(element.getChild("participants") != null){
			List<Element> participants = element.getChild("participants").getChildren();
			Map<Integer, ParticipantGroupDefine> groups = new HashMap<Integer, ParticipantGroupDefine>();
			Map<String, ParticipantTypeDefine> types = new HashMap<String, ParticipantTypeDefine>();
			for(Element ele : participants){
				ParticipantDefine pd = ParticipantDefine.parse(ele);
				if(!groups.containsKey(pd.getGroup())){
					ParticipantGroupDefine pgd = new ParticipantGroupDefine();
					pgd.setGroup(pd.getGroup());
					
					define.getParticipants().add(pgd);
					groups.put(pd.getGroup(), pgd);
				}
				
				String typekey = String.format("%d_%s", pd.getGroup(), pd.getType());
				if(!types.containsKey(typekey)){
					ParticipantTypeDefine ptd = new ParticipantTypeDefine();
					ptd.setType(pd.getType());

					types.put(typekey, ptd);
					groups.get(pd.getGroup()).add(ptd);
				}
				
				types.get(typekey).add(pd);
			}
		}
		
		//解析路由
		if(element.getChild("routes") != null){
			List<Element> variables = element.getChild("routes").getChildren();
			for(Element ele : variables){
				define.getRoutes().add(RouteDefine.parse(ele));
			}
		}
		
		if(element.getChild("variables") != null){
			List<Element> variables = element.getChild("variables").getChildren();
			for(Element ele : variables){
				define.getVariables().add(VariableDefine.parse(ele));
			}
		}
		
		if(element.getChild("args") != null){
			List<Element> args = element.getChild("args").getChildren();
			for(Element ele : args){
				define.getArgs().put(ele.getAttributeValue("name"), ele.getAttributeValue("value"));
			}
		}
		
		//解析活动Action
		if(element.getChild("actions") != null){
			Element actions = element.getChild("actions");
			
			if(actions.getChild("beforecreate") != null){
				List<Element> eles = element.getChild("beforecreate").getChildren();
				for(Element ele : eles){
					define.getBeforeCreateActions().add(ActivityActionDefine.parse(ele));
				}
			}
			if(actions.getChild("aftercreate") != null){
				List<Element> eles = element.getChild("aftercreate").getChildren();
				for(Element ele : eles){
					define.getAfterCreateActions().add(ActivityActionDefine.parse(ele));
				}
			}
			if(actions.getChild("beforefinish") != null){
				List<Element> eles = element.getChild("beforefinish").getChildren();
				for(Element ele : eles){
					define.getBeforeFinishActions().add(ActivityActionDefine.parse(ele));
				}
			}
			if(actions.getChild("afterfinish") != null){
				List<Element> eles = element.getChild("afterfinish").getChildren();
				for(Element ele : eles){
					define.getAfterFinishActions().add(ActivityActionDefine.parse(ele));
				}
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<VariableDefine> getVariables() {
		return variables;
	}

	public void setVariables(List<VariableDefine> variables) {
		this.variables = variables;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}


	public String getTimeoutMethod() {
		return timeoutMethod;
	}

	public void setTimeoutMethod(String timeoutMethod) {
		this.timeoutMethod = timeoutMethod;
	}

	public Map<String, String> getArgs() {
		return args;
	}

	public void setArgs(Map<String, String> args) {
		this.args = args;
	}

	public String getProcessorType() {
		return processorType;
	}

	public void setProcessorType(String processorType) {
		this.processorType = processorType;
	}

	public String getTransformFlowID() {
		return transformFlowID;
	}

	public void setTransformFlowID(String transformFlowID) {
		this.transformFlowID = transformFlowID;
	}

	public List<ParticipantGroupDefine> getParticipants() {
		return participants;
	}

	public void setParticipants(List<ParticipantGroupDefine> participants) {
		this.participants = participants;
	}
	public List<RouteDefine> getRoutes() {
		return routes;
	}

	public void setRoutes(List<RouteDefine> routes) {
		this.routes = routes;
	}

	public String getFormname() {
		return formname;
	}

	public void setFormname(String formname) {
		this.formname = formname;
	}
	
	public List<ActivityActionDefine> getBeforeCreateActions() {
		return beforeCreateActions;
	}

	public void setBeforeCreateActions(
			List<ActivityActionDefine> beforeCreateActions) {
		this.beforeCreateActions = beforeCreateActions;
	}

	public List<ActivityActionDefine> getAfterCreateActions() {
		return afterCreateActions;
	}

	public void setAfterCreateActions(List<ActivityActionDefine> afterCreateActions) {
		this.afterCreateActions = afterCreateActions;
	}

	public List<ActivityActionDefine> getBeforeFinishActions() {
		return beforeFinishActions;
	}

	public void setBeforeFinishActions(
			List<ActivityActionDefine> beforeFinishActions) {
		this.beforeFinishActions = beforeFinishActions;
	}

	public List<ActivityActionDefine> getAfterFinishActions() {
		return afterFinishActions;
	}

	public void setAfterFinishActions(List<ActivityActionDefine> afterFinishActions) {
		this.afterFinishActions = afterFinishActions;
	}
}
