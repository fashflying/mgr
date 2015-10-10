package mgr.wfengine.service;

import java.util.Date;

import mgr.wfengine.repository.DateSettingRepository;
import mgr.wfengine.repository.hibernate.DateSettingRepositoryImpl;
import mgr.wfengine.util.Utility;

/**
 * 特殊日期设定
 * @author Chenjie
 *
 */
public class DateSettingService {

	private DateSettingRepository dateSettingRepository = new DateSettingRepositoryImpl(Utility.WFENGINE_DB);

	/**
	 * 特殊工作日判断
	 * @param dateSetting
	 * @return
	 */
	public boolean isWorkingDay(Date date) {
		return dateSettingRepository.isWorkingDay(date);
	}

	/**
	 * 特殊休息日判断
	 * @param dateSetting
	 * @return
	 */
	public boolean isRestDay(Date date) {
		return dateSettingRepository.isRestDay(date);
	}
	
	public boolean RestDayCheck(Date date) {
		
		if (Utility.isWeekDay(date)) {
			return !isWorkingDay(date);
		} else {
			return isRestDay(date);
		}
	}
}
