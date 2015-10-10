package mgr.wfengine.repository.hibernate;

import mgr.wfengine.domain.UserOndo;
import mgr.wfengine.repository.UserOndoRepository;

public class UserOndoRepositoryImpl extends HibernateRepository<UserOndo, String>
		implements UserOndoRepository {

	public UserOndoRepositoryImpl(String database) {
		super(database);
	}

	@Override
	public void delByActInsIds(String... actInsIds) {
		getCurrentSession(super.database)
		.createQuery("delete UserOndo where id in :ids")
		.setParameterList("ids", actInsIds).executeUpdate();
	}
}
