package mgr.wfengine.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import mgr.wfengine.domain.Enums.ActivityInstanceState;
import mgr.wfengine.domain.Enums.MergeInstanceState;


public class MergeInstance {
	private String id;
	private String owner;
	/**
	 建立时，一共有几个活动实例
	 */
	private int splitCount;
	/**
	 目前完成了几个活动实例
	 */
	private int finishCount;
	/**
	 完成多少个活动实例时即可合并
	 */
	private int expectCount;
	private MergeInstanceState state;
	private Set<ActivityInstance> activityInstances;
	private String startActanceInstanceId;
	private String finishActanceInstanceId;
	private Date createTime;
	private Date finishTime;
	private String flowInstanceId;
	private FlowInstance flowInstance;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public MergeInstanceState getState() {
		return state;
	}

	public void setState(MergeInstanceState state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public void cancel(){
		this.setState(MergeInstanceState.Canceled);
	}
	
//	public void done(ActivityInstance actInst){
//        //符合合并条件，可以进行合并，然后继续向下运行了
//        this.setState(MergeInstanceState.Merged);
//        this.setFinishActanceInstanceId(actInst.getId());
//        this.setFinishTime(new Date());
//        
//        List<ActivityInstance> canceledInsts = new ArrayList<ActivityInstance>();
//        //如果合并时，还存在与此合并相关联的未完成的活动实例，需要将其全部关闭
//        for(ActivityInstance inst : this.getActivityInstances()){
//        	if(inst.getState() == ActivityInstanceState.Running){
//        		inst.cancel();
//        		canceledInsts.add(inst);
//        	}
//        }
//		
//        if(canceledInsts.size() > 0){
//    		//应该调用前端接口设置待办的状态为已经取消
//            EngineContext.getInstance().getPortalProvider().cancel(canceledInsts);
//        }
//	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Set<ActivityInstance> getActivityInstances() {
		return activityInstances;
	}

	public void setActivityInstances(Set<ActivityInstance> activityInstances) {
		this.activityInstances = activityInstances;
	}

	public String getStartActanceInstanceId() {
		return startActanceInstanceId;
	}

	public void setStartActanceInstanceId(String startActanceInstanceId) {
		this.startActanceInstanceId = startActanceInstanceId;
	}

	public String getFinishActanceInstanceId() {
		return finishActanceInstanceId;
	}

	public void setFinishActanceInstanceId(String finishActanceInstanceId) {
		this.finishActanceInstanceId = finishActanceInstanceId;
	}

	public int getSplitCount() {
		return splitCount;
	}

	public void setSplitCount(int splitCount) {
		this.splitCount = splitCount;
	}

	public int getFinishCount() {
		return finishCount;
	}

	public void setFinishCount(int finishCount) {
		this.finishCount = finishCount;
	}

	public int getExpectCount() {
		return expectCount;
	}

	public void setExpectCount(int expectCount) {
		this.expectCount = expectCount;
	}

	public String getFlowInstanceId() {
		return flowInstanceId;
	}

	public void setFlowInstanceId(String flowInstanceId) {
		this.flowInstanceId = flowInstanceId;
	}

	public FlowInstance getFlowInstance() {
		return flowInstance;
	}

	public void setFlowInstance(FlowInstance flowInstance) {
		this.flowInstance = flowInstance;
	}
}
