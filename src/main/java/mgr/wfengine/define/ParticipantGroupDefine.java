package mgr.wfengine.define;

import java.util.ArrayList;

public class ParticipantGroupDefine extends ArrayList<ParticipantTypeDefine> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6797601838032314687L;
	private int group = 0;
	
	public int getGroup() {
		return group;
	}
	
	public void setGroup(int group) {
		this.group = group;
	}
}
