package mgr.wfengine.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import mgr.wfengine.dto.ParticipantDto;
import mgr.wfengine.webservice.userpServiceClient;

import com.alibaba.fastjson.JSONArray;
import com.qtong.service.pojo.OrgOrganization;
import com.qtong.service.pojo.UserInfo;

public class Organization {
	
	private static final Logger log = Logger.getLogger(Organization.class);

	/**
	 * 组织架构取得
	 * @param orgId
	 * @param company
	 * @return
	 */
	public String getOrganizations(Integer orgId, Integer company) {
		// 公司根目录
		List<ParticipantDto> lsParticipant = new ArrayList<ParticipantDto>();
		StringBuffer strId = new StringBuffer();
		try {
			if (orgId == null || orgId == 0) {
				List<OrgOrganization> lsOrg = new userpServiceClient().getuserpServiceHttpPort().getChildOrganizations(0,0,company).getOrgOrganization();
				ParticipantDto dto = null;
				for (OrgOrganization org : lsOrg) {
					dto = new ParticipantDto();
					dto.setId(Integer.parseInt(strId.append(org.getId().getValue()).append(org.getOrgType().getValue()).toString()));
					strId.setLength(0);
					dto.setText(org.getDisplayName().getValue());
					dto.setState("closed");
					dto.getAttributes().put("type", "department");
					lsParticipant.add(dto);
				}
			}
			// 各部门目录
			else {
				Integer id = orgId / 10;
				Integer type = orgId % 10;
				List<OrgOrganization> lsOrg = new userpServiceClient().getuserpServiceHttpPort().getChildOrganizations(id,type,company).getOrgOrganization();
				ParticipantDto dto = null;
				// 子部门查询
				for (OrgOrganization org : lsOrg) {
					dto = new ParticipantDto();
					dto.setId(Integer.parseInt(strId.append(org.getId().getValue()).append(org.getOrgType().getValue()).toString()));
					strId.setLength(0);
					dto.setText(org.getDisplayName().getValue());
					dto.setState("closed");
					dto.getAttributes().put("type", "department");
					lsParticipant.add(dto);
				}
				// 部门员工查询
				List<UserInfo> lsUser = new userpServiceClient()
						.getuserpServiceHttpPort()
						.getOrganizationUsers(id, company).getUserInfo();
				for (UserInfo user : lsUser) {
					dto = new ParticipantDto();
					dto.setId(user.getId().getValue());
					dto.setText(user.getDisplayName().getValue());
					dto.setState("open");
					dto.getAttributes().put("type", "staff");
					lsParticipant.add(dto);
				}
			}
			
		} catch (IOException e) {
			log.error("调用组织架构接口异常！", e);
		}
		
		return JSONArray.toJSONString(lsParticipant);
	}
	
	/**
	 * 取得组织机构
	 * @param orgId
	 * @param company
	 * @param checked
	 * @return
	 */
	public String getOrganizations(Integer company, String... checked) {
		// 公司根目录
		List<ParticipantDto> lsParticipant = new ArrayList<ParticipantDto>();
		StringBuffer strId = new StringBuffer();
		try {
			List<OrgOrganization> lsOrg = new userpServiceClient()
					.getuserpServiceHttpPort()
					.getChildOrganizations(0, 0, company).getOrgOrganization();
			ParticipantDto dto = null;
			for (OrgOrganization org : lsOrg) {
				dto = new ParticipantDto();
				dto.setId(Integer.parseInt(strId.append(org.getId().getValue())
						.append(org.getOrgType().getValue()).toString()));
				strId.setLength(0);
				dto.setText(org.getDisplayName().getValue());
				dto.setState("closed");
				dto.getAttributes().put("type", "department");
				dto.setChildren(this.getChildOrgs(strId, dto.getId(), company));
				lsParticipant.add(dto);
			}
			
			// checked
			for (String orgCheck : checked) {
				this.setChecked(orgCheck, lsParticipant);
			}
			
		} catch (IOException e) {
			log.error("调用组织架构接口异常！", e);
		}
		
		return JSONArray.toJSONString(lsParticipant);
	}
	
	/**
	 * 取得员工信息
	 * @param userId
	 * @param company
	 * @return
	 */
	public static UserInfo getUserInfo(Integer userId, Integer company) {
		UserInfo userInfo = null;
		try {
			userInfo = new userpServiceClient().getuserpServiceHttpPort().getUserById(userId, company);
		} catch (IOException e) {
			userInfo = new UserInfo();
			log.error("调用组织架构接口异常！", e);
		}
		
		return userInfo;
	}
	
	/**
	 * 递归查找子部门
	 * @param strId
	 * @param orgId
	 * @param company
	 * @return
	 * @throws IOException
	 */
	private List<ParticipantDto> getChildOrgs(StringBuffer strId, Integer orgId, Integer company) throws IOException {
		List<ParticipantDto> lsParticipant = new ArrayList<ParticipantDto>();
		Integer id = orgId / 10;
		Integer type = orgId % 10;
		List<OrgOrganization> lsOrg = new userpServiceClient().getuserpServiceHttpPort().getChildOrganizations(id,type,company).getOrgOrganization();
		ParticipantDto dto = null;
		// 子部门查询
		for (OrgOrganization org : lsOrg) {
			dto = new ParticipantDto();
			dto.setId(Integer.parseInt(strId.append(org.getId().getValue()).append(org.getOrgType().getValue()).toString()));
			strId.setLength(0);
			dto.setText(org.getDisplayName().getValue());
			dto.setState("closed");
			dto.getAttributes().put("type", "department");
			dto.setChildren(this.getChildOrgs(strId, dto.getId(), company));
			lsParticipant.add(dto);
		}
		
		return lsParticipant;
	}
	
	/**
	 * 已选中的再check
	 * @param orgCheck
	 * @param lsParticipant
	 */
	private void setChecked(String orgCheck, List<ParticipantDto> lsParticipant) {
		for (ParticipantDto org : lsParticipant) {
			if (orgCheck.equals(org.getId())) {
				org.setChecked(true);
				break;
			} else {
				this.setChecked(orgCheck, org.getChildren());
			}
		}
	}
}
