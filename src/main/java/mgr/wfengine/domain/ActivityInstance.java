package mgr.wfengine.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mgr.wfengine.domain.Enums.ActivityInstanceState;
import mgr.wfengine.domain.Enums.MergeInstanceState;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

public class ActivityInstance {
	private String id;
	private String activityType;
	private String name;
	private ActivityInstanceState state;
	@JSONField(serialize=false)
	private ActivityInstance parent;
	@JSONField(serialize=false)
	private Set<ActivityInstance> childActivityInstances;
	@JSONField(serialize=false)
	private Set<MergeInstance> mergeInstances;
	private String flowInstanceId;
	@JSONField(serialize=false)
	private FlowInstance flowInstance;
	private String participant;
	private String activityId;
	private String flowId;
	private Date createTime;
	private Date finishTime;
	@JSONField(serialize=false)
	private String datas;
	@JSONField(serialize=false)
	private String datasUpdated;
	
	private List<ActivityData> dataList;
	@JSONField(serialize=false)
	private Map<String, Object> datasMap = null;
	
	public ActivityInstanceState getState() {
		return state;
	}
	public void setState(ActivityInstanceState state) {
		this.state = state;
	}
	
	public String getData(String key){
		if(datasMap != null){
			return datasMap.get(key).toString();
		}
		
		return null;
	}
	
	public void finish(){
		//结束这个活动
		this.setState(ActivityInstanceState.Finished);
		this.setFinishTime(new Date());
		
		// 活动状态改变通知超时管理改变状态
//		EngineContext.getInstance().getActivityStateChangeManager().notifyChange(this);
	}
	
	public void markDeleted(){
		this.setState(ActivityInstanceState.Deleted);
		// 活动状态改变通知超时管理改变状态
//		EngineContext.getInstance().getActivityStateChangeManager().notifyChange(this);
	}
	
	/**
	 * 取消活动实例，用于完成合并时，某些不再需要的、未完成的活动实例
	 */
	public void act_cancel(){
		this.setState(ActivityInstanceState.Canceled);
		
		//是否有由本活动发起的合并，如果有，此合并也需要取消
		for(MergeInstance inst : this.getMergeInstances()){
			if(inst.getState() == MergeInstanceState.Active && inst.getStartActanceInstanceId().equals(this.getId())){
				inst.cancel();
			}
		}
	}
	
	public void flow_cancel() {
		this.setState(ActivityInstanceState.Canceled);
	}

	public String getFlowInstanceId() {
		return flowInstanceId;
	}

	public void setFlowInstanceId(String flowInstanceId) {
		this.flowInstanceId = flowInstanceId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public FlowInstance getFlowInstance() {
		return flowInstance;
	}

	public void setFlowInstance(FlowInstance flowInstance) {
		this.flowInstance = flowInstance;
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public ActivityInstance getParent() {
		return parent;
	}

	public void setParent(ActivityInstance parent) {
		this.parent = parent;
	}

	public String getDatas() {
		return datas;
	}

	public void setDatas(String datas) {
		this.datas = datas;
		
		if(this.datasMap == null){
			this.datasMap = (Map<String,Object>)JSON.parse(datas);
		}
	}

	public String getDatasUpdated() {
		return datasUpdated;
	}
	public void setDatasUpdated(String datasUpdated) {
		this.datasUpdated = datasUpdated;
	}
	/*
	public void setDatasMap(Map<String,Object> datas){
		this.datasMap = datas;
		
		String jsonString = JSON.toJSONString(datas);
		this.setDatas(jsonString);
	}
	
	public Map<String,Object> getDatasMap(){
		return this.datasMap;
	}
	*/
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
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
	public Set<ActivityInstance> getChildActivityInstances() {
		return childActivityInstances;
	}
	public void setChildActivityInstances(Set<ActivityInstance> childActivityInstances) {
		this.childActivityInstances = childActivityInstances;
	}
	public Set<MergeInstance> getMergeInstances() {
		return mergeInstances;
	}
	public void setMergeInstances(Set<MergeInstance> mergeInstances) {
		this.mergeInstances = mergeInstances;
	}
	public boolean isRunning() {
		return this.getState() == ActivityInstanceState.Running;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ActivityData> getDataList() {
		if(this.dataList == null && this.datasUpdated != null && !this.datasUpdated.equals("")){
			this.dataList = (List<ActivityData>)JSON.parse(this.datasUpdated);
		}
		return dataList;
	}
	public void setDataList(List<ActivityData> dataList) {
		this.dataList = dataList;
		
		this.datasUpdated = JSON.toJSONString(this.getDataList());
	}
}
