package mgr.wfengine.repository.hibernate;

import java.util.Date;

import mgr.wfengine.domain.DateSetting;
import mgr.wfengine.repository.DateSettingRepository;

import org.hibernate.Query;

public class DateSettingRepositoryImpl extends HibernateRepository<DateSetting, String> implements DateSettingRepository {

	public DateSettingRepositoryImpl(String database) {
		super(database);
	}

	/**
	 * 特殊工作日判断
	 */
	@Override
	public boolean isWorkingDay(Date date) {
		Query query = this
				.getCurrentSession(super.database)
				.createQuery(
						"select count(*) from DateSetting where property = '1' and extraDate = :extraDate");
		long count = ((Long) query.setDate("extraDate", date).iterate()
				.next()).longValue();
		return count > 0;
	}

	/**
	 * 特殊休息日判断
	 */
	@Override
	public boolean isRestDay(Date date) {
		Query query = this
				.getCurrentSession(super.database)
				.createQuery(
						"select count(*) from DateSetting where property = '2' and extraDate = :extraDate");
		long count = ((Long) query.setDate("extraDate", date).iterate()
				.next()).longValue();
		return count > 0;
	}
}
