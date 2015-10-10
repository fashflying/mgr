package mgr.wfengine.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mgr.wfengine.domain.Enums.FlowInstanceState;

import com.alibaba.fastjson.annotation.JSONField;

public class FlowInstance {
	private String id;
	private String flowId;
	private String title;
	private String startUser;
	@JSONField(serialize=false)
	private Set<ActivityInstance> activityInstances;
	@JSONField(serialize=false)
	private Set<MergeInstance> mergeInstances;
	private Set<FlowData> datas;
	@JSONField(serialize=false)
	private Flow flow;
	private String parentActInstId;
	private int flowVersion;
	private Date createTime;
	private Date finishTime;
	private FlowInstanceState state;
	
	public void markDeleted(){
		this.state = FlowInstanceState.Deleted;
		
		for(ActivityInstance actInst : this.getActivityInstances()){
			actInst.markDeleted();
		}	
		
		for(MergeInstance merge : this.getMergeInstances()){
			merge.cancel();
		}
	}
	
	public void canceled() {
		this.setState(FlowInstanceState.Canceled);
		
		for(ActivityInstance actInst : this.getActivityInstances()){
			actInst.flow_cancel();
		}
		
		for(MergeInstance merge : this.getMergeInstances()){
			merge.cancel();
		}
	}
	
	public List<ActivityData> updateData(Map<String, Object> datas, boolean remove_not_exists){
		// 获取变化的数据
		Date lastUpdate = new Date();
		Set<FlowData> flowDatas =this.getDatas();
		if(flowDatas == null){
			flowDatas = new HashSet<FlowData>();
			this.setDatas(flowDatas);
		}
		List<ActivityData> actInstDatas = new ArrayList<ActivityData>();
		List<FlowData> toDels = new ArrayList<FlowData>();
		Map<String, FlowData> fdMap = new HashMap<String,FlowData>();
		if(flowDatas.size() > 0){
			for(FlowData fd : flowDatas){
				fdMap.put(fd.getDataName(), fd);
				if(datas.containsKey(fd.getDataName())){
					String data = datas.get(fd.getDataName()).toString();
					if(!data.equals(fd.getDataValue())){
						//数据不同
						ActivityData ad = new ActivityData();
						ad.setName(fd.getDataName());
						ad.setOldvalue(fd.getDataValue());
						ad.setOldvaluetime(fd.getLastUpdate());
						ad.setValue(data);
						ad.setValuetime(lastUpdate);
						actInstDatas.add(ad);
						
						fd.setDataValue(data);
						fd.setLastUpdate(lastUpdate);
					}
				} else {
					toDels.add(fd);
				}
			}
		}
		
		//看看有没有当前流程数据中没有的数据
		for(String key : datas.keySet()){
			if(!fdMap.containsKey(key)){
				//没有数据，新增 
				FlowData fd = new FlowData();
				fd.setDataName(key);
				fd.setDataValue(datas.get(key).toString());
				fd.setLastUpdate(lastUpdate);
				fd.setFlowInstance(this);
				fd.setFlow(this.getFlow());
				
				this.getDatas().add(fd);

				ActivityData ad = new ActivityData();
				ad.setName(key);
				ad.setOldvalue("");
				ad.setOldvaluetime(lastUpdate);
				ad.setValue(datas.get(key).toString());
				ad.setValuetime(lastUpdate);
				actInstDatas.add(ad);
			}
		}
		
		if(remove_not_exists){
			for(FlowData fd : toDels){
				this.getDatas().remove(fd);
			}
		}
		return actInstDatas;
	}
	
	public List<ActivityData> updateData(Map<String, Object> datas){
		return updateData(datas, false);
	}
	
	public void restoreData(List<ActivityData> datas){
		if(datas != null && datas.size() > 0){
			for(ActivityData ad : datas){
				for(FlowData fd : this.getDatas()){
					//找到名称相同，并且流程数据的最后更新时间与活动数据的时间一致的
					if(fd.getDataName().equals(ad.getName())
							&& fd.getLastUpdate().equals(ad.getValuetime())){
						//这个数据需要恢复	
						fd.setDataValue(ad.getOldvalue());
						fd.setLastUpdate(ad.getOldvaluetime());
						
						break;
					}
				}
			}
		}
	}

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

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<FlowData> getDatas() {
		return datas;
	}

	public void setDatas(Set<FlowData> datas) {
		this.datas = datas;
	}

	public Set<ActivityInstance> getActivityInstances() {
		return activityInstances;
	}

	public void setActivityInstances(Set<ActivityInstance> activityInstances) {
		this.activityInstances = activityInstances;
	}

	public String getStartUser() {
		return startUser;
	}

	public void setStartUser(String startUser) {
		this.startUser = startUser;
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

	public void finish() throws Exception {
		this.setState(FlowInstanceState.Finished);		
		this.setFinishTime(new Date());
		
		//检查是不是有父流程实例
		if(this.getParentActInstId() != null && !this.getParentActInstId().equals("")){
			//完成子流活动实例
			// TODO 流程结束时要看操作父流程，维护平台怎么处理，涉及到待办已办怎么办
//			EngineContext.getInstance().getRunningService().finishActivityInstance(this.getParentActInstId(), null, null, null);
		}
	}

	public FlowInstanceState getState() {
		return state;
	}

	public void setState(FlowInstanceState state) {
		this.state = state;
	}

	public int getFlowVersion() {
		return flowVersion;
	}

	public void setFlowVersion(int flowVersion) {
		this.flowVersion = flowVersion;
	}

	public Set<MergeInstance> getMergeInstances() {
		return mergeInstances;
	}

	public void setMergeInstances(Set<MergeInstance> mergeInstances) {
		this.mergeInstances = mergeInstances;
	}

	public String getParentActInstId() {
		return parentActInstId;
	}

	public void setParentActInstId(String parentActInstId) {
		this.parentActInstId = parentActInstId;
	}
}
