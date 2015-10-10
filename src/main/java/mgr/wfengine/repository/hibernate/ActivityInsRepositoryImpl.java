package mgr.wfengine.repository.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import mgr.wfengine.domain.ActivityInstance;
import mgr.wfengine.repository.ActivityInsRepository;

public class ActivityInsRepositoryImpl extends HibernateRepository<ActivityInstance, String>
		implements ActivityInsRepository {

	public ActivityInsRepositoryImpl(String database) {
		super(database);
	}

	@Override
	public void delByFlowInsId(String flowInsId) {

		getCurrentSession(super.database)
		.createQuery("delete ActivityInstance where flowInstanceId = :flowInsId")
		.setString("flowInsId", flowInsId)
		.executeUpdate();
	}

	@Override
	public List<ActivityInstance> listByCondition(
			Map<String, Object> conditions, String company, Integer pageList,
			Integer pageNum) {
		StringBuffer hql=new StringBuffer("from ActivityInstance AI where AI.flowInstance.flow.company = :companyID ");//"from ActivityInstance AI where AI.createTime between :createTimeFrom and :createTimeTo and AI.flowInstance.flow.company = :companyID order by AI.id";
		// 流程id条件追加
		Object flowInsID = conditions.get("flowInstanceId");
		String strflowInsID=null;
		if (flowInsID!=null && !"".equals(flowInsID)) {
			strflowInsID = flowInsID.toString();
			hql.append(" and AI.flowInstanceId = :flowInstanceId");
		}
		Object dateF = conditions.get("createTimeFrom");
		String dateFrom=null;
		if(dateF!=null){
			dateFrom=dateF.toString();
			hql.append(" and AI.createTime >= :createTimeFrom");
		}
		Object dateT = conditions.get("createTimeTo");
		String dateTo=null;
		if(dateT!=null){
			dateTo=dateT.toString();
			hql.append(" and AI.createTime <= :createTimeTo");
		}
		hql.append(" order by AI.id");
		
		Query query=getCurrentSession(super.database).createQuery(hql.toString()).setInteger("companyID", Integer.parseInt(company));
		if (strflowInsID!=null && !"".equals(strflowInsID)) {
			query=query.setString("flowInstanceId", strflowInsID);
		}
		if(dateFrom!=null){
			query=query.setString("createTimeFrom", dateFrom);
		}
		if(dateTo!=null){
			query=query.setString("createTimeTo", dateTo);
		}
		query.setFirstResult((pageNum - 1) * pageList);
		query.setMaxResults(pageList);
		@SuppressWarnings("unchecked")
		List<ActivityInstance> activityInses=(List<ActivityInstance>)query.list();
		return activityInses;
	}

	@Override
	public Long cntByCondition(Map<String, Object> conditions, String company) {
		StringBuffer hql=new StringBuffer("select count(*) from ActivityInstance AI where AI.flowInstance.flow.company = :companyID ");//"from ActivityInstance AI where AI.createTime between :createTimeFrom and :createTimeTo and AI.flowInstance.flow.company = :companyID order by AI.id";
		// 流程id条件追加
		Object flowInsID = conditions.get("flowInstanceId");
		String strflowInsID=null;
		if (flowInsID!=null && !"".equals(flowInsID)) {
			strflowInsID = flowInsID.toString();
			hql.append(" and AI.flowInstanceId = :flowInstanceId");
		}
		Object dateF = conditions.get("createTimeFrom");
		String dateFrom=null;
		if(dateF!=null){
			dateFrom=dateF.toString();
			hql.append(" and AI.createTime >= :createTimeFrom");
		}
		Object dateT = conditions.get("createTimeTo");
		String dateTo=null;
		if(dateT!=null){
			dateTo=dateT.toString();
			hql.append(" and AI.createTime <= :createTimeTo");
		}
		hql.append(" order by AI.id");
		
		Query query=getCurrentSession(super.database).createQuery(hql.toString()).setInteger("companyID", Integer.parseInt(company));
		if (strflowInsID!=null && !"".equals(strflowInsID)) {
			query=query.setString("flowInstanceId", strflowInsID);
		}
		if(dateFrom!=null){
			query=query.setString("createTimeFrom", dateFrom);
		}
		if(dateTo!=null){
			query=query.setString("createTimeTo", dateTo);
		}
		return ((Long)query.iterate().next()).longValue();
	}
}