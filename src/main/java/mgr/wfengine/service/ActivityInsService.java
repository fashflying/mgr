package mgr.wfengine.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import mgr.wfengine.define.ActivityDefine;
import mgr.wfengine.define.FlowDefine;
import mgr.wfengine.define.RouteDefine;
import mgr.wfengine.domain.ActivityData;
import mgr.wfengine.domain.ActivityInstance;
import mgr.wfengine.domain.ActivityTimeout;
import mgr.wfengine.domain.Enums.ActivityInstanceState;
import mgr.wfengine.domain.Enums.MergeInstanceState;
import mgr.wfengine.domain.FlowData;
import mgr.wfengine.domain.FlowInstance;
import mgr.wfengine.domain.FlowVersion;
import mgr.wfengine.domain.MergeInstance;
import mgr.wfengine.domain.UserOndo;
import mgr.wfengine.dto.ParticipantDto;
import mgr.wfengine.repository.ActivityInsRepository;
import mgr.wfengine.repository.ActivityTimeoutRepository;
import mgr.wfengine.repository.MergeInsRepository;
import mgr.wfengine.repository.UserOndoRepository;
import mgr.wfengine.repository.hibernate.ActivityInsRepositoryImpl;
import mgr.wfengine.repository.hibernate.ActivityTimeoutRepositoryImpl;
import mgr.wfengine.repository.hibernate.HibernateUtil;
import mgr.wfengine.repository.hibernate.MergeInsRepositoryImpl;
import mgr.wfengine.repository.hibernate.UserOndoRepositoryImpl;
import mgr.wfengine.util.Utility;
import mgr.wfengine.webservice.userpServiceClient;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qtong.service.pojo.OrgOrganization;
import com.qtong.service.pojo.UserInfo;

public class ActivityInsService {

	private ActivityInsRepository activityInsR;
	
	private ActivityTimeoutRepository activityTimeoutR;
	
	private UserOndoRepository userOndoR;
	
	private MergeInsRepository mergeInsR;
	
	private static final Logger log = Logger.getLogger(ActivityInsService.class);
	
	public ActivityInsService () {
		activityInsR = new ActivityInsRepositoryImpl(Utility.WFENGINE_DB);
		activityTimeoutR = new ActivityTimeoutRepositoryImpl(Utility.WFENGINE_DB); 
		mergeInsR = new MergeInsRepositoryImpl(Utility.WFENGINE_DB);
		userOndoR = new UserOndoRepositoryImpl(Utility.WFPORTALLET_DB);
	}
	
	/**
	 * 取得活动实例
	 * @param conditions
	 * @param company
	 * @param pageList
	 * @param pageNum
	 * @return
	 */
	public String getActIns(Map<String, Object> conditions, String company, Integer pageList, Integer pageNum) {
		Transaction trans = null;
		JSONObject jsonActins = new JSONObject();
		String result = "";
		try {
			trans = HibernateUtil.getSessionFactory(Utility.WFENGINE_DB).getCurrentSession().beginTransaction();
			List<ActivityInstance> list = this.activityInsR.listByCondition(conditions, company, pageList, pageNum);
			Long cnt = this.activityInsR.cntByCondition(conditions, company);
			jsonActins.put("rows", list);
			jsonActins.put("total", cnt);
			result = JSON.toJSONStringWithDateFormat(jsonActins, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteNullStringAsEmpty);
			trans.commit();
			log.trace("活动实例取得成功！");
			
		} catch (Exception e) {
			if (trans != null && trans.isActive()) trans.rollback();
			jsonActins.put("rows", new ArrayList<ActivityInstance>());
			jsonActins.put("total", 0);
			result = JSON.toJSONString(jsonActins);
			log.trace("活动实例取得失败！");
		}
		return result;
	}
	
	/**
	 * 取消活动实例
	 * @param id
	 * @return
	 */
	public String cancelActIns(String id) {
		Transaction trans_WFE = null;
		Transaction trans_WFP = null;
		JSONObject jsonActins = new JSONObject();
		try {
			trans_WFE = HibernateUtil.getSessionFactory(Utility.WFENGINE_DB).getCurrentSession().beginTransaction();
			trans_WFP = HibernateUtil.getSessionFactory(Utility.WFPORTALLET_DB).getCurrentSession().beginTransaction();
			ActivityInstance actIns = activityInsR.get(id);
			
			// 取消活动
			actIns.act_cancel();
			
			// 超时提醒删除
			activityTimeoutR.delByActInsIds(id);
			
			// 待办取消
			userOndoR.delByActInsIds(id);
			
			trans_WFE.commit();
			trans_WFP.commit();
			jsonActins.put("result", "Success");
			
		} catch (Exception e) {
			if (trans_WFE != null && trans_WFE.isActive()) trans_WFE.rollback();
			if (trans_WFP != null && trans_WFP.isActive()) trans_WFP.rollback();
			jsonActins.put("result", "Error");
			log.error("活动实例取消失败", e);
		}
		
		return jsonActins.toJSONString();
	}
	
	/**
	 * 删除活动实例
	 * @param id
	 * @return
	 */
	public String delActIns(String id) {
		Transaction trans_WFE = null;
		Transaction trans_WFP = null;
		JSONObject jsonActins = new JSONObject();
		try {
			trans_WFE = HibernateUtil.getSessionFactory(Utility.WFENGINE_DB).getCurrentSession().beginTransaction();
			trans_WFP = HibernateUtil.getSessionFactory(Utility.WFPORTALLET_DB).getCurrentSession().beginTransaction();
			ActivityInstance actIns = activityInsR.get(id);
			
			// 合并实例删除
			Set<MergeInstance> startMerge = new HashSet<MergeInstance>();
			for (MergeInstance merge : actIns.getMergeInstances()) {
				if (merge.getStartActanceInstanceId().equals(id)) {
					startMerge.add(merge);
				}
			}
			actIns.getMergeInstances().clear();
			for (MergeInstance merge : startMerge) {
				mergeInsR.delete(merge);
			}
			
			// 活动实例删除
			activityInsR.delete(actIns);
			
			// 超时提醒删除
			activityTimeoutR.delByActInsIds(id);
			
			// 待办删除
			userOndoR.delByActInsIds(id);
			
			trans_WFE.commit();
			trans_WFP.commit();
			jsonActins.put("result", "Success");
			
		} catch (Exception e) {
			if (trans_WFE != null && trans_WFE.isActive()) trans_WFE.rollback();
			if (trans_WFP != null && trans_WFP.isActive()) trans_WFP.rollback();
			jsonActins.put("result", "Error");
			log.error("活动实例取消失败", e);
		}
		
		return jsonActins.toJSONString();
	}
	
	/**
	 * 取得要增加的活动
	 * @param id
	 * @return
	 */
	public String getAddActivityById(String id) {
		Transaction trans = null;
		ActivityInstance parentAct = null;
		JSONObject jsonActins = new JSONObject();
		
		try {
			trans = HibernateUtil.getSessionFactory(Utility.WFENGINE_DB).getCurrentSession().beginTransaction();
			parentAct = activityInsR.get(id);
			
			// 流程实例对应版本的流程定义文件取得
			FlowInstance flowIns = parentAct.getFlowInstance();
			Iterator<FlowVersion> flowVersions = flowIns.getFlow().getVersions().iterator();
			String flowContent = "";
			while (flowVersions.hasNext()) {
				FlowVersion flowVersion = flowVersions.next();
				if (flowIns.getFlowVersion() == flowVersion.getVersion()) {
					flowContent = flowVersion.getDefine();
					break;
				}
			}
			
			// 解析XML文件并找到父活动的下一步所有可能的活动id
			FlowDefine flowDefine = FlowDefine.parse(flowContent);
			List<ActivityDefine> activityDefines = flowDefine.getActivities();
			List<RouteDefine> routes = new ArrayList<RouteDefine>();
			for (ActivityDefine actDef : activityDefines) {
				if (parentAct.getActivityId().equals(actDef.getId())) {
					routes = actDef.getRoutes();
					break;
				}
			}
			
			// 取得所有下一步活动定义并返回
			List<ActivityDefine> toActs = new ArrayList<ActivityDefine>();
			for (RouteDefine route : routes) {
				for (ActivityDefine actDef : activityDefines) {
					if (actDef.getId().equals(route.getTo())) {
						toActs.add(actDef);
					}
				}
			}
			
			jsonActins.put("result", "Success");
			jsonActins.put("addAct", toActs);
			jsonActins.put("flowDef", JSON.toJSONString(flowDefine));
			
			/*
			// 如果所选择的是分支活动的话，要将活动的所有分支列出供使用者选择要增加的分支
			if ("branch".equals(instance.getActivityType())) {
				// 分支活动将已经创建的分支取出用于可否新增的判断
				for (ActivityInstance childIns : instance.getChildActivityInstances()) {
					jsonCreatedBranch = new JSONObject();
					jsonCreatedBranch.put("activityID", childIns.getActivityId());
					jsonCreatedBranch.put("actInsId", childIns.getId());
					jsonCreatedBranchs.add(jsonCreatedBranch);
					if (ActivityInstanceState.Running.equals(childIns.getState())) {
						finishedall = false;
					}
				}
				// 分支没有全部结束的情况下取得所有分支
				if (!finishedall) {
					jsonActins.put("result", "Success");
					jsonActins.put("type", "branch");
					jsonActins.put("createdBranchs", jsonCreatedBranchs);
					jsonActins.put("allBranchs", "");
				}
			} else {
				for (ActivityInstance childIns : instance.getChildActivityInstances()) {
					if ("multi".equals(childIns.getActivityType())) {
						if (ActivityInstanceState.Running.equals(childIns.getState())) {
							jsonActins.put("result", "Success");
							jsonActins.put("type", "multi");
							jsonActins.put("actInsId", childIns.getId());
							finishedall = false;
							break;
						}
					} else {
						jsonActins.put("result", "TypeError");
						finishedall = false;
						break;
					}
				}
			}
			
			// 所要增加的活动已经全部结束，不能增加
			if (finishedall) {
				jsonActins.put("result", "Error");
				jsonActins.put("info", "所要增加的活动已经结束，如需增加请先撤回后再增加！");
				log.info("要增加的活动已经结束！");
			}
			*/
			
			trans.commit();
			log.info("要增加的活动取得成功！");
		} catch (Exception e) {
			if (trans != null && trans.isActive()) trans.rollback();
			jsonActins.put("result", "Error");
			jsonActins.put("info", "异常导致要增加的活动取得失败！");
			log.error("异常导致要增加的活动取得失败！", e);
		}
		return jsonActins.toJSONString();
	}
	
	/**
	 * 创建活动
	 * @param participant
	 * @param docNumber
	 * @param company
	 * @param manager
	 * @param actInsId
	 * @param datas
	 * @param activityDefine
	 * @param flowDefine
	 * @return
	 */
	public String addActivityIns(String participant, String docNumber, String company, String manager, String actInsId, String datas, String activityDefine, String flowDefine) {
		Transaction trans_WFE = null;
		Transaction trans_WFP = null;
		ActivityInstance instance = null;
		JSONObject ret = new JSONObject();
		
		try {
			trans_WFE = HibernateUtil.getSessionFactory(Utility.WFENGINE_DB).getCurrentSession().beginTransaction();
			trans_WFP = HibernateUtil.getSessionFactory(Utility.WFPORTALLET_DB).getCurrentSession().beginTransaction();
			instance = activityInsR.get(actInsId);
			
			// updateDatas编辑
			FlowInstance flowIns = instance.getFlowInstance();
//			Set<FlowData> flowDatasSet = flowIns.getDatas();
//			Iterator<FlowData> flowDatas = flowDatasSet.iterator();
			List<JSONObject> jsonDatasArray = JSONArray.parseArray(datas, JSONObject.class);
			ActivityDefine actDef = JSONObject.parseObject(activityDefine, ActivityDefine.class);
			FlowDefine flowDef = JSONObject.parseObject(flowDefine, FlowDefine.class);
//			JSONArray jsonUpdateDatasArray = new JSONArray();
			ActivityInstance addInstance = new ActivityInstance();
//			JSONObject jsonDatas = new JSONObject();
//			ActivityData jsonUpdateDatas = new ActivityData();
//			JSONObject jsonData = null;
//			FlowData flowData = null;
			Date updateTime = new Date(); 
//			boolean addDataFLG = true;
			
			Map<String, Object> mapFD = new HashMap<String, Object>();
			for (JSONObject jsonFD : jsonDatasArray) {
				mapFD.put(jsonFD.getString("dataName"), jsonFD.getString("dataValue"));
			}
			
			List<ActivityData> listActData = flowIns.updateData(mapFD, true);
//			for (int i=0;i < jsonDatasArray.size(); i++) {
//				jsonData = jsonDatasArray.get(i);
//				addDataFLG = true;
//				while (flowDatas.hasNext()) {
//					flowData = flowDatas.next();
//					// 存在的且值被改变的
//					if (jsonData.getString("dataName").equals(flowData.getDataName())) {
//						addDataFLG = false;
//						if (!jsonData.getString("dataValue").equals(flowData.getDataValue())) {
//							// 编辑更新履历
//							jsonUpdateDatas.setName(flowData.getDataName());
//							jsonUpdateDatas.setOldvalue(flowData.getDataValue());
//							jsonUpdateDatas.setOldvaluetime(flowData.getLastUpdate());
//							jsonUpdateDatas.setValue(jsonData.getString("dataValue"));
//							jsonUpdateDatas.setValuetime(updateTime);
//							jsonUpdateDatasArray.add(jsonUpdateDatas);
//							// 更新流程数据
//							flowData.setDataValue(jsonData.getString("dataValue"));
//							flowData.setLastUpdate(updateTime);
//							break;
//						}
//					}
//				}
//				
//				// 新增数据值
//				if (addDataFLG) {
//					// 编辑更新履历
//					jsonUpdateDatas.setName(jsonData.getString("dataName"));
//					jsonUpdateDatas.setOldvalue("");
//					jsonUpdateDatas.setValue(jsonData.getString("dataValue"));
//					jsonUpdateDatas.setValuetime(updateTime);
//					jsonUpdateDatasArray.add(jsonUpdateDatas);
//					// 更新流程数据
//					flowData = new FlowData();
//					flowData.setDataName(jsonData.getString("dataName"));
//					flowData.setDataValue(jsonData.getString("dataValue"));
//					flowData.setLastUpdate(updateTime);
//					flowData.setFlow(instance.getFlowInstance().getFlow());
//					flowData.setFlowInstance(instance.getFlowInstance());
//					flowDatasSet.add(flowData);
//				}
//				
//				// 活动实例的数据编辑
//				jsonDatas.put(jsonData.getString("dataName"), jsonData.getString("dataValue"));
//			}
			
			// 创建多实例活动
			addInstance.setActivityId(actDef.getId());
			addInstance.setActivityType(actDef.getType());
//			addInstance.setChildActivityInstances(instance.getChildActivityInstances());
			addInstance.setName(actDef.getName());
			addInstance.setFlowId(instance.getFlowId());
			addInstance.setParticipant(participant);
			addInstance.setDatas(JSON.toJSONString(mapFD));
			addInstance.setDatasUpdated(JSON.toJSONString(listActData));
			addInstance.setCreateTime(updateTime);
			addInstance.setState(ActivityInstanceState.Running);
			addInstance.setParent(instance);
			addInstance.setFlowInstanceId(instance.getFlowInstanceId());
			addInstance.setFlowInstance(instance.getFlowInstance());
			addInstance.setMergeInstances(new HashSet<MergeInstance>());
			//将父活动实例关联的未结束的合并实例传递到子活动实例
			if(instance.getMergeInstances() != null && instance.getMergeInstances().size() > 0){
				for(MergeInstance inst : instance.getMergeInstances()){
					if(inst.getState() == MergeInstanceState.Active){
						addInstance.getMergeInstances().add(inst);
					}
				}
			}
//			if (instance.getMergeInstances() != null) {
//				Iterator<MergeInstance> mergeIns = instance.getMergeInstances().iterator();
//				while (mergeIns.hasNext()) {
//					mergeIns.next().getActivityInstances().add(addInstance);
//				}
//			}
			
			// 创建
			this.activityInsR.save(addInstance);
			
			// 超时提醒追加
			if (actDef.getTimeout() > 0) {
				ActivityTimeout activityTimeout = new ActivityTimeout();
				activityTimeout.setActivityInstanceId(addInstance.getActivityId());
				activityTimeout.setState(addInstance.getState());
				activityTimeout.setTimeoutTime(Utility.timeoutDate(
						addInstance.getCreateTime(), actDef.getTimeout(), TimeUnit.MINUTES));
				activityTimeoutR.save(activityTimeout);
			}
			
			// 待办追加
			UserOndo userOndo = new UserOndo();
			userOndo.setId(addInstance.getId());
			userOndo.setReceiveTime(updateTime);
			userOndo.setDocNumber(docNumber);
			userOndo.setFlowTopic(flowIns.getTitle());
			userOndo.setFlowType(flowIns.getFlow().getTitle());
			userOndo.setActName(actDef.getName());
			userOndo.setFlowInstanceId(instance.getFlowInstanceId());
			userOndo.setFlowId(instance.getFlowId());
			userOndo.setActId(actDef.getId());
			userOndo.setReceiveUser(Organization.getUserInfo(Integer.parseInt(participant), Integer.parseInt(company)).getDisplayName().getValue());
			userOndo.setReceiveUserId(participant);
			UserInfo managerInfo = Organization.getUserInfo(Integer.parseInt(manager), Integer.parseInt(company));
			userOndo.setSendUser(managerInfo.getDisplayName().getValue());
			userOndo.setSendUserId(manager);
			userOndo.setSendComp(company);
			userOndo.setDocType(flowDef.getType());
			userOndo.setSendDepart(managerInfo.getOrgid().getValue());
			userOndo.setActState(1);
			userOndo.setFlowVer(String.valueOf(flowIns.getFlowVersion()));
			this.userOndoR.save(userOndo);
			
			trans_WFE.commit();
			trans_WFP.commit();
			
			// 返回成功
			log.info("活动实例创建成功！");
			ret.put("result", "Success");
			
		} catch (Exception e) {
			if (trans_WFE != null && trans_WFE.isActive()) trans_WFE.rollback(); 
			if (trans_WFP != null && trans_WFP.isActive()) trans_WFP.rollback(); 
			ret.put("result", "Error");
			log.error("异常导致活动实例创建失败！",e);
		}
		
		return ret.toJSONString();
	}
	
	/**
	 * 取得流程数据用于创建活动数据
	 * @param actInsId
	 * @return
	 */
	public String getFlowDatas(String actInsId) {
		Transaction trans = null;
		Set<FlowData> flowDatas = new HashSet<FlowData>();
		String result = "";
		try {
			trans = HibernateUtil.getSessionFactory(Utility.WFENGINE_DB).getCurrentSession().beginTransaction();
			flowDatas = activityInsR.get(actInsId).getFlowInstance().getDatas();
			result = JSONArray.toJSONString(flowDatas);
			trans.commit();
			log.info("流程数据取得成功！");
		} catch (Exception e) {
			if (trans != null && trans.isActive()) trans.rollback(); 
			log.error("异常导致流程数据取得失败！",e);
		}
		return result;
	}
}
