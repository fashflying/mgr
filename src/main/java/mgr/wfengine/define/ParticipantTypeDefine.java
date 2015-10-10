package mgr.wfengine.define;

import java.util.ArrayList;

public class ParticipantTypeDefine extends ArrayList<ParticipantDefine>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1422772353696512406L;
	private String type = "";

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
