package mgr.wfengine.repository;

import java.util.Map;

import mgr.wfengine.domain.Flow;

public interface FlowRepository extends Repository<Flow, String> {
	Map<String,Object> listByCondition(Map<String, Object> conditions, String company, Integer pageList, Integer pageNum);
}
