package mgr.wfengine.repository;

import mgr.wfengine.domain.ActivityTimeout;

public interface ActivityTimeoutRepository extends Repository<ActivityTimeout, String> {

	ActivityTimeout getByActivityInstance(String activityInstanceId);
	void delByActInsIds(String... actInsIds);
}
