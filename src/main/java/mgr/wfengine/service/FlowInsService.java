package mgr.wfengine.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mgr.wfengine.domain.ActivityInstance;
import mgr.wfengine.domain.FlowData;
import mgr.wfengine.domain.FlowInstance;
import mgr.wfengine.domain.MergeInstance;
import mgr.wfengine.repository.ActivityInsRepository;
import mgr.wfengine.repository.ActivityTimeoutRepository;
import mgr.wfengine.repository.FlowInsRepository;
import mgr.wfengine.repository.MergeInsRepository;
import mgr.wfengine.repository.UserDoneRepository;
import mgr.wfengine.repository.UserOndoRepository;
import mgr.wfengine.repository.hibernate.ActivityInsRepositoryImpl;
import mgr.wfengine.repository.hibernate.ActivityTimeoutRepositoryImpl;
import mgr.wfengine.repository.hibernate.FlowInsRepositoryImpl;
import mgr.wfengine.repository.hibernate.HibernateUtil;
import mgr.wfengine.repository.hibernate.MergeInsRepositoryImpl;
import mgr.wfengine.repository.hibernate.UserDoneRepositoryImpl;
import mgr.wfengine.repository.hibernate.UserOndoRepositoryImpl;
import mgr.wfengine.util.Utility;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class FlowInsService {
	
	private static final Logger log = Logger.getLogger(FlowInsService.class);

	private FlowInsRepository flowInsR;
	
	private ActivityTimeoutRepository actTimeoutR;
	
	private MergeInsRepository mergeInsR;
	
	private ActivityInsRepository actInsR;
	
	private UserOndoRepository userOndoR;
	
	private UserDoneRepository userDoneR;
	
	public FlowInsService () {
		flowInsR = new FlowInsRepositoryImpl(Utility.WFENGINE_DB);
		actTimeoutR = new ActivityTimeoutRepositoryImpl(Utility.WFENGINE_DB);
		mergeInsR = new MergeInsRepositoryImpl(Utility.WFENGINE_DB);
		actInsR = new ActivityInsRepositoryImpl(Utility.WFENGINE_DB);
		userOndoR = new UserOndoRepositoryImpl(Utility.WFPORTALLET_DB);
		userDoneR = new UserDoneRepositoryImpl(Utility.WFPORTALLET_DB);
	}
	
	/**
	 * 取得流程实例
	 * @param conditions
	 * @param company
	 * @param pageList
	 * @param pageNum
	 * @return
	 */
	public String getFlowIns(Map<String, Object> conditions, String company, Integer pageList, Integer pageNum) {
		Transaction trans = null;
		Map<String, Object> flowIns = new HashMap<String, Object>();
		String result = "";
		try {
			trans = HibernateUtil.getSessionFactory(Utility.WFENGINE_DB).getCurrentSession().beginTransaction();
			List<FlowInstance> listFlowins = flowInsR.listByCondition(conditions, company, pageList, pageNum);
			Long cntAll = flowInsR.cntByCondition(conditions, company);
			flowIns.put("rows", listFlowins);
			flowIns.put("total", cntAll);
			result = JSON.toJSONStringWithDateFormat(flowIns, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteNullStringAsEmpty);
			trans.commit();
			log.trace("流程实例取得成功！");
		} catch (Exception e) {
			if (trans != null && trans.isActive()) trans.rollback();
			flowIns.put("rows", new ArrayList<FlowInstance>());
			flowIns.put("total", 0);
			result = JSON.toJSONString(flowIns);
			log.error("流程实例取得失败！", e);
		}
		
		return result;
	}
	
	/**
	 * 取消流程
	 * @param flowInsId
	 * @return
	 */
	public String cancelFlowIns(String flowInsId) {
		Transaction trans_WFE = null;
		Transaction trans_WFP = null;
		JSONObject result = new JSONObject();
		try {
			trans_WFE = HibernateUtil.getSessionFactory(Utility.WFENGINE_DB).getCurrentSession().beginTransaction();
			trans_WFP = HibernateUtil.getSessionFactory(Utility.WFPORTALLET_DB).getCurrentSession().beginTransaction();
			FlowInstance flowIns = flowInsR.get(flowInsId);
			// 流程取消
			flowIns.canceled();
			
			// 超时提醒删除
			Set<ActivityInstance> listActIns = flowIns.getActivityInstances();
			String[] ids = new String[listActIns.size()];
			int i = 0;
			for (ActivityInstance act : listActIns) {
				ids[i] = act.getId();
				i++;
			}
			this.actTimeoutR.delByActInsIds(ids);
			
			// 已办待办取消
			this.userOndoR.delByActInsIds(ids);
			this.userDoneR.delByActInsIds(ids);
			
			trans_WFE.commit();
			trans_WFP.commit();
			
			result.put("result", "Success");
			log.trace("流程实例取消成功！");
		} catch (Exception e) {
			if (trans_WFE != null && trans_WFE.isActive()) trans_WFE.rollback();
			if (trans_WFP != null && trans_WFP.isActive()) trans_WFP.rollback();
			result.put("result", "Error");
			
			log.error("流程实例取消失败！", e);
		}
		
		return result.toJSONString();
	}
	
	/**
	 * 删除流程
	 * @param flowInsId
	 * @return
	 */
	public String delFlowIns(String flowInsId) {
		Transaction trans_WFE = null;
		Transaction trans_WFP = null;
		JSONObject result = new JSONObject();
		try {
			trans_WFE = HibernateUtil.getSessionFactory(Utility.WFENGINE_DB).getCurrentSession().beginTransaction();
			trans_WFP = HibernateUtil.getSessionFactory(Utility.WFPORTALLET_DB).getCurrentSession().beginTransaction();
			FlowInstance flowIns = flowInsR.get(flowInsId);

			Set<ActivityInstance> listActIns = flowIns.getActivityInstances();
			String[] ids = new String[listActIns.size()];
			int i = 0;
			for (ActivityInstance act : listActIns) {
				ids[i] = act.getId();
				i++;
			}
			// 删除合并实例
			for (ActivityInstance actins : flowIns.getActivityInstances()) {
				actins.getMergeInstances().clear();
			}
//			for (MergeInstance merge : flowIns.getMergeInstances()) {
//				merge.setFlowInstance(null);
//			}
			this.mergeInsR.delByFlowInsId(flowInsId);
			
			// 删除活动实例
			this.actInsR.delByFlowInsId(flowInsId);
//			flowIns.getActivityInstances().clear();
			
			// 删除流程实例
			this.flowInsR.delete(flowIns);
			
			// 超时提醒删除
			this.actTimeoutR.delByActInsIds(ids);
			
			// 已办待办删除
			this.userOndoR.delByActInsIds(ids);
			this.userDoneR.delByActInsIds(ids);
			
			trans_WFE.commit();
			trans_WFP.commit();
			
			result.put("result", "Success");
			log.trace("流程实例删除成功！");
		} catch (Exception e) {
			if (trans_WFE != null && trans_WFE.isActive()) trans_WFE.rollback();
			if (trans_WFP != null && trans_WFP.isActive()) trans_WFP.rollback();
			result.put("result", "Error");
			
			log.error("流程实例删除失败！", e);
		}
		return result.toJSONString();
	}
	
	/**
	 * 更新流程数据
	 * @param flowInsId
	 * @param datas
	 * @return
	 */
	public String saveFlowData(String flowInsId, Map<String, Object> datas) {
		Transaction trans_WFE = null;
		JSONObject result = new JSONObject();
		try {
			trans_WFE = HibernateUtil.getSessionFactory(Utility.WFENGINE_DB).getCurrentSession().beginTransaction();
			FlowInstance flowIns = flowInsR.get(flowInsId);

			// 更新流程数据
			flowIns.updateData(datas, true);
			
			trans_WFE.commit();
			
			result.put("result", "Success");
			log.trace("流程数据保存成功！");
		} catch (Exception e) {
			if (trans_WFE != null && trans_WFE.isActive()) trans_WFE.rollback();
			result.put("result", "Error");
			
			log.error("流程数据保存失败！", e);
		}
		return result.toJSONString();
	}
	
	/**
	 * @param flowInsId
	 * @param zs
	 * @param cs
	 * @return
	 */
	public String addZSCS(String flowInsId, String zs, String cs) {
		Transaction trans_WFE = null;
		JSONObject result = new JSONObject();
		try {
			trans_WFE = HibernateUtil.getSessionFactory(Utility.WFENGINE_DB).getCurrentSession().beginTransaction();
			FlowInstance flowIns = flowInsR.get(flowInsId);
			JSONObject data = new JSONObject();
			data.put("flowInstanceId", flowInsId);
			data.put("flowId", flowIns.getFlowId());
			Set<FlowData> flowDatas = flowIns.getDatas();
			JSONObject flowData = new JSONObject();
			for (FlowData fd : flowDatas) {
				if ("zs888".equals(fd.getDataName())) {
					flowData.put("zs888", zs);
				} else if ("cs888".equals(fd.getDataName())) {
					flowData.put("cs888", cs);
				} else {
					flowData.put(fd.getDataName(), fd.getDataValue());
				}
			}
			data.put("flowData", flowData);
			trans_WFE.commit();
			
			// 调用主送抄送接口
			Map<String, String> params = new HashMap<String, String>();
			params.put("flowdata", JSON.toJSONString(data, SerializerFeature.WriteMapNullValue));
			String response = Utility.postUrl(Utility.getFORWARD_INTERFACE_URL(), params);
			if (!response.equals("success")) {
				log.error("接口调用失败！");
				log.error("错误信息：" + response);
				throw new Exception();
			}
			
			result.put("result", "Success");
			log.trace("主送抄送增加成功！");
		} catch (ClientProtocolException e) {
			log.error("客户端提交给服务器的请求，不符合HTTP协议!", e);
		} catch (IOException e) {
			log.error("接口服务器连接失败！", e);
		} catch (Exception e) {
			if (trans_WFE != null && trans_WFE.isActive()) trans_WFE.rollback();
			result.put("result", "Error");
			
			log.error("主送抄送增加失败！", e);
		}
		return result.toJSONString();
	}
}
