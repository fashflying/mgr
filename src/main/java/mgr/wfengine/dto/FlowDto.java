package mgr.wfengine.dto;

import java.util.List;

public class FlowDto {
	private String id;
	/**
	 * 流程标识名称
	 */
	private String name;
	/**
	 * 显示名称
	 */
	private String title;
	/**
	 * 最新版本号
	 */
	private int lastVersion;

	private List<FlowInsDto> flowInstancesJSONObj;

	private List<FlowVersionDto> versionsObj;

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

	public int getLastVersion() {
		return lastVersion;
	}

	public void setLastVersion(int lastVersion) {
		this.lastVersion = lastVersion;
	}

	public List<FlowInsDto> getFlowInstancesJSONObj() {
		return flowInstancesJSONObj;
	}

	public void setFlowInstancesJSONObj(List<FlowInsDto> flowInstancesJSONObj) {
		this.flowInstancesJSONObj = flowInstancesJSONObj;
	}

	public List<FlowVersionDto> getVersionsObj() {
		return versionsObj;
	}

	public void setVersionsObj(List<FlowVersionDto> versionsObj) {
		this.versionsObj = versionsObj;
	}
}
