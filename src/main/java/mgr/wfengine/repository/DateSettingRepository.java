package mgr.wfengine.repository;

import java.util.Date;

import mgr.wfengine.domain.DateSetting;

public interface DateSettingRepository extends Repository<DateSetting, String> {
	/**
	 * 判断特殊工作日
	 * @return
	 */
	boolean isWorkingDay(Date date); 

	/**
	 * 判断特殊休息日
	 * @return
	 */
	boolean isRestDay(Date date); 
	
}
