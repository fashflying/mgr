package mgr.wfengine.repository.hibernate;

import java.util.List;

import mgr.wfengine.domain.ActivityTimeout;
import mgr.wfengine.repository.ActivityTimeoutRepository;

public class ActivityTimeoutRepositoryImpl extends HibernateRepository<ActivityTimeout, String> implements ActivityTimeoutRepository {

	public ActivityTimeoutRepositoryImpl(String database) {
		super(database);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ActivityTimeout getByActivityInstance(String activityInstanceId) {
		List<ActivityTimeout> list = getCurrentSession(super.database)
				.createQuery(
						"from ActivityTimeout where activityInstanceId = :activityInstanceId")
				.setString("activityInstanceId", activityInstanceId).list();
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	@Override
	public void delByActInsIds(String... actInsIds) {
		getCurrentSession(super.database)
		.createQuery("delete ActivityTimeout where activityInstanceId in :ids")
		.setParameterList("ids", actInsIds).executeUpdate();
	}
}