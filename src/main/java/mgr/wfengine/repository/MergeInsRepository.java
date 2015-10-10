package mgr.wfengine.repository;

import mgr.wfengine.domain.MergeInstance;

public interface MergeInsRepository extends Repository<MergeInstance, String> {
	void delByFlowInsId(String flowInsId);
}
