/**
 *@author baibo
 *@createTime 2014年11月4日 下午3:22:11
 */
package mgr.wfengine.domain;

import java.util.Date;

public class ActivityData {
	private String name;
	private String oldvalue;
	private String value;
	private Date oldvaluetime;
	private Date valuetime;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOldvalue() {
		return oldvalue;
	}
	public void setOldvalue(String oldvalue) {
		this.oldvalue = oldvalue;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Date getOldvaluetime() {
		return oldvaluetime;
	}
	public void setOldvaluetime(Date oldvaluetime) {
		this.oldvaluetime = oldvaluetime;
	}
	public Date getValuetime() {
		return valuetime;
	}
	public void setValuetime(Date valuetime) {
		this.valuetime = valuetime;
	}
}
