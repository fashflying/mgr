package mgr.wfengine.dto;

public class FlowDataDto {
	private String flowInsId;
	private String id;
	private String dataName;
	private String dataValue;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getFlowInsId() {
		return flowInsId;
	}
	public void setFlowInsId(String flowInsId) {
		this.flowInsId = flowInsId;
	}
}
