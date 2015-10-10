package mgr.wfengine.action;


public class ManagerAction extends BaseAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2806563033243492181L;
	
	/**
	 * companyID
	 */
	private String CID;
	
	/**
	 * userid
	 */
	private String userid;

	public void setSessionCID() {
		setSession("company", this.CID);
		setSession("userID", this.userid);
	}
	
	public String getCID() {
		return CID;
	}

	public void setCID(String cID) {
		CID = cID;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
}
