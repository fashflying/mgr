package mgr.wfengine.domain;

import java.util.Date;

import mgr.wfengine.domain.Enums.ActivityInstanceState;


public class ActivityTimeout {
	private String id;
	private ActivityInstanceState state;
	private Integer operationNum;
	private String activityInstanceId;
	private Date timeoutTime;
	
	public ActivityInstanceState getState() {
		return state;
	}
	public void setState(ActivityInstanceState state) {
		this.state = state;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public Integer getOperationNum() {
		return operationNum;
	}
	public void setOperationNum(Integer operationNum) {
		this.operationNum = operationNum;
	}
	public String getActivityInstanceId() {
		return activityInstanceId;
	}
	public void setActivityInstanceId(String activityInstanceId) {
		this.activityInstanceId = activityInstanceId;
	}
	public Date getTimeoutTime() {
		return timeoutTime;
	}
	public void setTimeoutTime(Date timeoutTime) {
		this.timeoutTime = timeoutTime;
	}
	public boolean isFinish() {
		return this.getState() != ActivityInstanceState.Running;
	}
}
