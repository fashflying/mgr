package mgr.wfengine.repository.hibernate;

import mgr.wfengine.domain.UserDone;
import mgr.wfengine.repository.UserDoneRepository;

public class UserDoneRepositoryImpl extends HibernateRepository<UserDone, String>
		implements UserDoneRepository {

	public UserDoneRepositoryImpl(String database) {
		super(database);
	}

	@Override
	public void delByActInsIds(String... actInsIds) {
		getCurrentSession(super.database)
		.createQuery("delete UserDone where id in :ids")
		.setParameterList("ids", actInsIds).executeUpdate();
	}

}
