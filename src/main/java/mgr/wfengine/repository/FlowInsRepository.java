package mgr.wfengine.repository;

import java.util.List;
import java.util.Map;

import mgr.wfengine.domain.FlowInstance;

public interface FlowInsRepository extends Repository<FlowInstance, String> {
	List<FlowInstance> listByCondition(Map<String, Object> conditions, String company, Integer pageList, Integer pageNum);
	Long cntByCondition(Map<String, Object> conditions, String company);
}
