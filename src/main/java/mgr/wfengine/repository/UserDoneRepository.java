package mgr.wfengine.repository;

import mgr.wfengine.domain.UserDone;

public interface UserDoneRepository extends Repository<UserDone, String> {

	void delByActInsIds(String... actInsIds);
}
