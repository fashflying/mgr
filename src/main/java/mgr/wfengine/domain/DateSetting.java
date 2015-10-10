package mgr.wfengine.domain;

import java.util.Date;
import java.sql.Time;

public class DateSetting {
	private String id;
	private Date extraDate;
	private Time extraTime;
	private String property;
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getExtraDate() {
		return extraDate;
	}
	public void setExtraDate(Date extraDate) {
		this.extraDate = extraDate;
	}
	public Time getExtraTime() {
		return extraTime;
	}
	public void setExtraTime(Time extraTime) {
		this.extraTime = extraTime;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
