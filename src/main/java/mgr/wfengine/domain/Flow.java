package mgr.wfengine.domain;

import java.util.Set;

import com.alibaba.fastjson.annotation.JSONField;

public class Flow {
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
	/**
	 * 流程所属企业
	 */
	private int company;
	@JSONField(serialize=false)
	private Set<FlowInstance> flowInstances;
	private Set<FlowVersion> versions;
	
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
	public Set<FlowInstance> getFlowInstances() {
		return flowInstances;
	}
	public void setFlowInstances(Set<FlowInstance> flowInstances) {
		this.flowInstances = flowInstances;
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
	public Set<FlowVersion> getVersions() {
		return versions;
	}
	public void setVersions(Set<FlowVersion> versions) {
		this.versions = versions;
	}
	public int getCompany() {
		return company;
	}
	public void setCompany(int company) {
		this.company = company;
	}
}
