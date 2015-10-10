/**流程数据模块： 实体类
 *@author baibo
 *@createTime 2014年11月4日 下午3:17:09
 */
package mgr.wfengine.domain;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class FlowData {
	private String id;
	@JSONField(serialize=false)
	private FlowInstance flowInstance;
	@JSONField(serialize=false)
	private Flow flow;
	private String dataName;
	private String dataValue;
	private Date lastUpdate;
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
	public Flow getFlow() {
		return flow;
	}
	public void setFlow(Flow flow) {
		this.flow = flow;
	}
	public String getDataName() {
		return dataName;
	}
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	public String getDataValue() {
		return dataValue;
	}
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
