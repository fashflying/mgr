package mgr.wfengine.repository;

import mgr.wfengine.domain.UserOndo;

public interface UserOndoRepository extends Repository<UserOndo, String> {

	void delByActInsIds(String... actInsIds);
}
