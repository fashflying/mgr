package mgr.wfengine.dto;

import java.util.Date;
import java.util.List;

public class FlowInsDto {
	private String id;
	private String flowId;
	private String title;
	private String startUser;
	private List<ActivityInsDto> activityInstancesJSONObj;
	private List<FlowDataDto> datasJSONObj;
	private int flowVersion;
	private Date createTime;
	private Date finishTime;
	private String state;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStartUser() {
		return startUser;
	}
	public void setStartUser(String startUser) {
		this.startUser = startUser;
	}
	public List<ActivityInsDto> getActivityInstancesJSONObj() {
		return activityInstancesJSONObj;
	}
	public void setActivityInstancesJSONObj(
			List<ActivityInsDto> activityInstancesJSONObj) {
		this.activityInstancesJSONObj = activityInstancesJSONObj;
	}
	public List<FlowDataDto> getDatasJSONObj() {
		return datasJSONObj;
	}
	public void setDatasJSONObj(List<FlowDataDto> datasJSONObj) {
		this.datasJSONObj = datasJSONObj;
	}
	public int getFlowVersion() {
		return flowVersion;
	}
	public void setFlowVersion(int flowVersion) {
		this.flowVersion = flowVersion;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
