package mgr.wfengine.service;

import java.util.Map;

import mgr.wfengine.repository.FlowRepository;
import mgr.wfengine.repository.hibernate.FlowRepositoryImpl;
import mgr.wfengine.repository.hibernate.HibernateUtil;
import mgr.wfengine.util.Utility;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class FlowService {
	
	private static final Logger log = Logger.getLogger(FlowService.class);

	private FlowRepository flowR;
	
	public FlowService () {
		flowR = new FlowRepositoryImpl(Utility.WFENGINE_DB);
	}
	
	public String getFlows(Map<String, Object> conditions, String company, Integer pageList, Integer pageNum) {
		Transaction trans = null;
		Map<String, Object> flows = null;
		String results = "";
		try {
			trans = HibernateUtil.getSessionFactory(Utility.WFENGINE_DB).getCurrentSession().beginTransaction();
			flows = flowR.listByCondition(conditions, company, pageList, pageNum);
			results = JSON.toJSONStringWithDateFormat(flows, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteNullStringAsEmpty);
			trans.commit();
			log.trace("流程定义取得成功！");
		} catch (Exception e) {
			if (trans != null && trans.isActive()) trans.rollback();
			log.error("流程定义取得失败！", e);
		}
		
		return results;
	}
}
