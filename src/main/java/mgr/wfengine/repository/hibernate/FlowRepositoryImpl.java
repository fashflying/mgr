package mgr.wfengine.repository.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mgr.wfengine.domain.Flow;
import mgr.wfengine.repository.FlowRepository;
import mgr.wfengine.util.Utility;

import org.apache.log4j.Logger;
import org.hibernate.Query;

public class FlowRepositoryImpl extends HibernateRepository<Flow, String>
		implements FlowRepository {

	private static final Logger log = Logger.getLogger(FlowRepositoryImpl.class);
	
	public FlowRepositoryImpl(String database) {
		super(database);
	}

	@Override
	public Map<String,Object> listByCondition(Map<String,Object> conditions, String company,
			Integer pageList, Integer pageNum) {
		 Map<String,Object> result=new HashMap<String,Object>();
			try{
				StringBuffer hql=new StringBuffer("from Flow where company = :companyID");
				String title = conditions.get("title").toString();
				if (title != null && !"".equals(title)) {
					hql.append(" and title like :flowTitle");
				}
				hql.append(" order by id");
				Query query = getCurrentSession(Utility.WFENGINE_DB).createQuery(hql.toString()).setInteger("companyID", Integer.parseInt(company));
				if (title != null && !"".equals(title)) {
					query = query.setString("flowTitle", "%"+title+"%");
				}
				@SuppressWarnings("unchecked")
				List<Flow> flows=query.list();
				List<Flow> flowsPage = new ArrayList<Flow>();
				int totalCount=flows.size();
				int firstResultNo=(pageNum-1)*pageList;
				int lastResultNo=(firstResultNo+pageList-1)<totalCount?(firstResultNo+pageList-1):(totalCount-1);
				for(int i=firstResultNo;i<=lastResultNo;i++){
					flowsPage.add(flows.get(i));
				}
				
				result.put("total", String.valueOf(totalCount));
				result.put("rows", flowsPage);
								
			}catch(Exception e){
				log.error("流程定义取得失败", e);
			}
			return result;
	}

}
