package mgr.wfengine.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParticipantDto {

	private Integer id;
	private String text;
	private String state;
	private boolean checked;
	private List<ParticipantDto> children;
	private Map<String, String> attributes = new HashMap<String, String>();
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<ParticipantDto> getChildren() {
		return children;
	}
	public void setChildren(List<ParticipantDto> children) {
		this.children = children;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
