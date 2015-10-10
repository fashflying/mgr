package mgr.wfengine.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mgr.wfengine.domain.FlowData;
import mgr.wfengine.service.FlowInsService;
import mgr.wfengine.service.Organization;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

public class FlowInsAction extends BaseAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -475656101957750513L;

	private FlowInsService flowInsS = new FlowInsService();
	
	private Organization orgService = new Organization();
	
	/**
	 * 流程标题
	 */
	private String title;
	
	/**
	 * 流程ID
	 */
	private String flowInsID;
	
	/**
	 * 创建时间FROM
	 */
	private Date createTimeFrom;
	
	/**
	 * 创建时间TO
	 */
	private Date createTimeTo;
	
	/**
	 * 流程数据
	 */
	private String flowDatas;
	
	/**
	 * 主送
	 */
	private String ZS;
	
	/**
	 * 抄送
	 */
	private String CS;
	
	/**
	 * 已选择的部门
	 */
	private String orgs;

	public void getFlowInses() {
		if (null == getPage()) {
			setPage(1);
		}
		if (null == getRows()) {
			setRows(10);
		}
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("title", this.title);
		conditions.put("createTimeFrom", this.createTimeFrom);
		conditions.put("createTimeTo", this.createTimeTo);
		responseJson(this.flowInsS.getFlowIns(conditions, getSession("company").toString(), getRows(), getPage()));
		
	}
	
	public void deleteFlowIns() {
		responseJson(this.flowInsS.delFlowIns(this.flowInsID));
	}

	public void cancelFlowIns() {
		responseJson(this.flowInsS.cancelFlowIns(this.flowInsID));
	}

	public void saveFLowDatas() {
		
		Map<String, Object> datas = new HashMap<String, Object>();
		if (this.flowDatas != null && !"".equals(this.flowDatas)) {
			List<FlowData> flowDatasTemp = JSON.parseArray(this.flowDatas, FlowData.class);
			if (flowDatasTemp != null) {
				for (FlowData dataDto : flowDatasTemp) {
					datas.put(dataDto.getDataName(), dataDto.getDataValue());
				}
			}
		}
		responseJson(this.flowInsS.saveFlowData(this.flowInsID, datas));
	}
	
	/**
	 * 取得组织架构
	 */
	public void getOrganizations () {
		String[] checked = {};
		if (this.orgs != null && !"".equals(this.orgs)) {
			checked = this.orgs.split(",");
		}
		responseJson(this.orgService.getOrganizations(Integer.parseInt(getSession("company").toString()), checked));
	}
	
	/**
	 * 增加主送抄送
	 */
	public void addZSCS() {
		// 主送抄送编辑
		StringBuffer zs = new StringBuffer();
		if (this.ZS != null && !"".equals(this.ZS)) {
			String[] zsInfo = this.ZS.split(";");
			String[] zsId = zsInfo[0].split(",");
			String[] zsName = zsInfo[1].split(",");
			for (int i = 0;i < zsId.length; i++) {
				if (0 < zs.length()) {
					zs.append(";");
				}
				zs.append(StringUtils.substring(zsId[i], 0, zsId[i].length() -1))
				  .append("|").append(zsName[i]).append("|dept");
			}
		}
		
		StringBuffer cs = new StringBuffer();
		if (this.CS != null && !"".equals(this.CS)) {
			String[] csInfo = this.CS.split(";");
			String[] csId = csInfo[0].split(",");
			String[] csName = csInfo[1].split(",");
			for (int i = 0;i < csId.length; i++) {
				if (0 < cs.length()) {
					cs.append(";");
				}
				cs.append(StringUtils.substring(csId[i], 0, csId[i].length() -1))
				  .append("|").append(csName[i]).append("|dept");
			}
		}
		
		responseJson(this.flowInsS.addZSCS(this.flowInsID, zs.toString(), cs.toString()));
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFlowInsID() {
		return flowInsID;
	}

	public void setFlowInsID(String flowInsID) {
		this.flowInsID = flowInsID;
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

	public String getFlowDatas() {
		return flowDatas;
	}

	public void setFlowDatas(String flowDatas) {
		this.flowDatas = flowDatas;
	}

	public String getZS() {
		return ZS;
	}

	public void setZS(String zS) {
		ZS = zS;
	}

	public String getCS() {
		return CS;
	}

	public void setCS(String cS) {
		CS = cS;
	}

	public String getOrgs() {
		return orgs;
	}

	public void setOrgs(String orgs) {
		this.orgs = orgs;
	}
}
