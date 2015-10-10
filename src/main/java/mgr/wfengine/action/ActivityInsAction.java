package mgr.wfengine.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mgr.wfengine.dto.ActivityInsDataDto;
import mgr.wfengine.service.ActivityInsService;
import mgr.wfengine.service.Organization;
import mgr.wfengine.util.Utility;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ActivityInsAction extends BaseAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -9147926918286009879L;

	private static final Logger log = Logger.getLogger(ActivityInsAction.class);
	
	/**
	 * ActivityInsService
	 */
	private ActivityInsService service = new ActivityInsService();
	
	/**
	 * Organization
	 */
	private Organization org = new Organization();
	
	/**
	 * 活动ID
	 */
	private String activityInsID;
	
	/**
	 * 创建时间FROM
	 */
	private Date createTimeFrom;
	
	/**
	 * 创建时间TO
	 */
	private Date createTimeTo;
	
	/**
	 * 活动数据
	 */
	private String activityInsDatas;
	
	/**
	 * 流程ID
	 */
	private String flowInstanceId;
	
	/**
	 * 活动参与者
	 */
	private String participant;
	
	/**
	 * 文书号
	 */
	private String docNumber;

	/**
	 * 要增加活动定义
	 */
	private String activityDefine;
	
	/**
	 * 流程定义
	 */
	private String flowDefine;
	
	/**
	 * 组织架构的OrgID
	 */
	private Integer id;
	
	public void getActivityInses() {
		if (null == getPage()) {
			setPage(1);
		}
		if (null == getRows()) {
			setRows(10);
		}
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("flowInstanceId", this.flowInstanceId);
		conditions.put("createTimeFrom", this.createTimeFrom);
		conditions.put("createTimeTo", this.createTimeTo);
		responseJson(this.service.getActIns(conditions, getSession("company").toString(), getRows(), getPage()));
	}

	@SuppressWarnings("unchecked")
	public void getActivityInsData() {
		if (activityInsDatas == null || "".equals(activityInsDatas) || "null".equals(activityInsDatas)) {
			responseJson(JSON.toJSONString(new ArrayList<ActivityInsDataDto>()));
		} else {
			List<ActivityInsDataDto> actInsDatas = new ArrayList<ActivityInsDataDto>();
			Map<String, String> actInsDatasTemp = JSON.parseObject(activityInsDatas, Map.class);
			if (actInsDatasTemp != null) {
				ActivityInsDataDto actData;
				for (Map.Entry<String, String> actDataTemp : actInsDatasTemp.entrySet()) {
					actData = new ActivityInsDataDto();
					actData.setActivityInsId(activityInsID);
					actData.setDataName(actDataTemp.getKey());
					actData.setDataValue(actDataTemp.getValue());
					actInsDatas.add(actData);
				}
			}
			responseJson(JSON.toJSONString(actInsDatas));
		}
	}
	
	public void deleteActivityIns() {
		responseJson(this.service.delActIns(this.activityInsID));
	}

	public void cancelActivityIns() {
		responseJson(this.service.cancelActIns(this.activityInsID));
	}

	public void undoActivityIns() {
		Map<String, String> params = new HashMap<String, String>();
		JSONObject json = new JSONObject();
		json.put("managementType", "rollbackActivityIns");
		json.put("args", this.activityInsID);
		
		params.put("msg_type", "managementPlatform");
		params.put("msg_body", json.toJSONString());
		
		String response = "";
		try {
			response = Utility.postUrl(Utility.getWFENGINE_URL(), params);
			JSONObject j = JSON.parseObject(response);
			if ("Success".equals(j.get("result"))) {
				responseJson(response);
			} else {
				responseJson(response);
				log.error(j.get("info"));
			}
		} catch (ClientProtocolException e) {
			log.error("服务器访问失败！",e);
		} catch (IOException e) {
			log.error("服务器访问失败！",e);
		}
	}

	public void saveActivityInsDatas() {
		
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String> datas = new HashMap<String, String>();
		if (this.activityInsDatas != null && !"".equals(this.activityInsDatas)) {
			List<ActivityInsDataDto> actInsDatasTemp = JSON.parseArray(this.activityInsDatas, ActivityInsDataDto.class);
			if (actInsDatasTemp != null) {
				for (ActivityInsDataDto dataDto : actInsDatasTemp) {
					datas.put(dataDto.getDataName(), dataDto.getDataValue());
				}
			}
		}
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		String data = JSON.toJSONString(datas);
		json.put("managementType", "manageActivityData");
		json1.put(activityInsID, data);
		json.put("args", json1.toJSONString());
		
		params.put("msg_type", "managementPlatform");
		params.put("msg_body", json.toJSONString());
		
		String response = "";
		try {
			response = Utility.postUrl(Utility.getWFENGINE_URL(), params);
			JSONObject j = JSON.parseObject(response);
			if ("Success".equals(j.get("result"))) {
				responseJson(response);
			} else {
				responseJson(response);
				log.error(j.get("info"));
			}
		} catch (ClientProtocolException e) {
			log.error("服务器访问失败！",e);
		} catch (IOException e) {
			log.error("服务器访问失败！",e);
		}
	}

	/**
	 * 取得要增加的流程实例
	 */
	public void getAddActivity() {
		responseJson(this.service.getAddActivityById(this.activityInsID));
	}
	
	/**
	 * 创建活动实例
	 */
	public void addActivityIns() {
		responseJson(this.service.addActivityIns(this.participant, this.docNumber, getSession("company").toString(), getSession("userID").toString(), this.activityInsID, this.activityInsDatas, this.activityDefine, this.flowDefine));
	}
	
	/**
	 * 取得流程数据用于创建活动
	 */
	public void getFlowDatas() {
		responseJson(this.service.getFlowDatas(this.activityInsID));
	}
	
	/**
	 * 取得组织架构
	 */
	public void getParticipants() {
		responseJson(this.org.getOrganizations(this.id, Integer.parseInt(getSession("company").toString())));
	}

	public String getActivityInsID() {
		return activityInsID;
	}

	public void setActivityInsID(String activityInsID) {
		this.activityInsID = activityInsID;
	}

	public Date getCreateTimeFrom() {
		return createTimeFrom;
	}

	public void setCreateTimeFrom(Date createTimeFrom) {
		this.createTimeFrom = createTimeFrom;
	}

	public Date getCreateTimeTo() {
		return createTimeTo;
	}

	public void setCreateTimeTo(Date createTimeTo) {
		this.createTimeTo = createTimeTo;
	}

	public String getActivityInsDatas() {
		return activityInsDatas;
	}

	public void setActivityInsDatas(String activityInsDatas) {
		this.activityInsDatas = activityInsDatas;
	}

	public String getFlowInstanceId() {
		return flowInstanceId;
	}

	public void setFlowInstanceId(String flowInstanceId) {
		this.flowInstanceId = flowInstanceId;
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public String getActivityDefine() {
		return activityDefine;
	}

	public void setActivityDefine(String activityDefine) {
		this.activityDefine = activityDefine;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDocNumber() {
		return docNumber;
	}

	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}

	public String getFlowDefine() {
		return flowDefine;
	}

	public void setFlowDefine(String flowDefine) {
		this.flowDefine = flowDefine;
	}
}
