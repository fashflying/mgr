package mgr.wfengine.repository;

import java.util.List;
import java.util.Map;

import mgr.wfengine.domain.ActivityInstance;

public interface ActivityInsRepository extends Repository<ActivityInstance, String> {

	void delByFlowInsId(String flowInsId);
	List<ActivityInstance> listByCondition(Map<String, Object> conditions, String company, Integer pageList, Integer pageNum);
	Long cntByCondition(Map<String, Object> conditions, String company);
}
