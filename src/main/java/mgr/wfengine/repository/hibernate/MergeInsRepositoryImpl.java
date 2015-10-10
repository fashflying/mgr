package mgr.wfengine.repository.hibernate;

import mgr.wfengine.domain.MergeInstance;
import mgr.wfengine.repository.MergeInsRepository;

import org.apache.log4j.Logger;

public class MergeInsRepositoryImpl extends HibernateRepository<MergeInstance, String>
		implements MergeInsRepository {

	private static final Logger log = Logger.getLogger(MergeInsRepositoryImpl.class);
	
	public MergeInsRepositoryImpl(String database) {
		super(database);
	}

	@Override
	public void delByFlowInsId(String flowInsId) {
		getCurrentSession(super.database)
		.createQuery("delete MergeInstance where flowInstanceId = :flowInsId")
		.setString("flowInsId", flowInsId).executeUpdate();
	}

}
