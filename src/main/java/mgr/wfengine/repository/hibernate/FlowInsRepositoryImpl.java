package mgr.wfengine.repository.hibernate;

import java.util.List;
import java.util.Map;

import mgr.wfengine.domain.FlowInstance;
import mgr.wfengine.repository.FlowInsRepository;
import mgr.wfengine.util.Utility;

import org.apache.log4j.Logger;
import org.hibernate.Query;

public class FlowInsRepositoryImpl extends HibernateRepository<FlowInstance, String>
		implements FlowInsRepository {

	private static final Logger log = Logger.getLogger(FlowInsRepositoryImpl.class);
	
	public FlowInsRepositoryImpl(String database) {
		super(database);
	}

	@Override
	public List<FlowInstance> listByCondition(Map<String, Object> conditions,
			String company, Integer pageList, Integer pageNum) {
			//Hibernate implicit inner join
			StringBuffer hql=new StringBuffer("from FlowInstance FI where FI.flow.company = :companyID ");//"from FlowInstance FI where FI.title like :title and FI.createTime between :createTimeFrom and :createTimeTo and FI.flow.company = :companyID order by FI.id";
			String title=conditions.get("title").toString();
			if(title!=null&&!"".equals(title)){
				hql.append(" and FI.title like :title");
			}
			Object dateF = conditions.get("createTimeFrom");
			String dateFrom=null;
			if(dateF!=null){
				dateFrom=dateF.toString();
				hql.append(" and FI.createTime >= :createTimeFrom");
			}
			Object dateT = conditions.get("createTimeTo");
			String dateTo=null;
			if(dateT!=null){
				dateTo=dateT.toString();
				hql.append(" and FI.createTime <= :createTimeTo");
			}
			hql.append(" order by FI.id");
			Query query=getCurrentSession(Utility.WFENGINE_DB).createQuery(hql.toString()).setInteger("companyID", Integer.parseInt(company));
			if(title!=null&&!"".equals(title)){
				query=query.setString("title", "%"+title+"%");
			}
			if(dateFrom!=null){
				query=query.setString("createTimeFrom", dateF.toString());
			}
			if(dateTo!=null){
				query=query.setString("createTimeTo", dateT.toString());
			}
			query.setFirstResult((pageNum - 1) * pageList);
			query.setMaxResults(pageList);
			List<FlowInstance> flowInses=(List<FlowInstance>)query.list();
		return flowInses;
	}

	@Override
	public Long cntByCondition(Map<String, Object> conditions, String company) {
		StringBuffer hql=new StringBuffer("select count(*) from FlowInstance FI where FI.flow.company = :companyID ");//"from FlowInstance FI where FI.title like :title and FI.createTime between :createTimeFrom and :createTimeTo and FI.flow.company = :companyID order by FI.id";
		String title=conditions.get("title").toString();
		if(title!=null&&!"".equals(title)){
			hql.append(" and FI.title like :title");
		}
		Object dateF = conditions.get("createTimeFrom");
		String dateFrom=null;
		if(dateF!=null){
			dateFrom=dateF.toString();
			hql.append(" and FI.createTime >= :createTimeFrom");
		}
		Object dateT = conditions.get("createTimeTo");
		String dateTo=null;
		if(dateT!=null){
			dateTo=dateT.toString();
			hql.append(" and FI.createTime <= :createTimeTo");
		}
		hql.append(" order by FI.id");
		Query query=getCurrentSession(Utility.WFENGINE_DB).createQuery(hql.toString()).setInteger("companyID", Integer.parseInt(company));
		if(title!=null&&!"".equals(title)){
			query=query.setString("title", "%"+title+"%");
		}
		if(dateFrom!=null){
			query=query.setString("createTimeFrom", dateF.toString());
		}
		if(dateTo!=null){
			query=query.setString("createTimeTo", dateT.toString());
		}
		
		return ((Long)query.iterate().next()).longValue();
	}
}
