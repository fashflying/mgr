package mgr.wfengine.action;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import mgr.wfengine.dto.FlowVersionDto;
import mgr.wfengine.service.FlowService;

import org.apache.log4j.Logger;

public class FlowAction extends BaseAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6599745216105374994L;
	
	private static final Logger log = Logger.getLogger(FlowAction.class);
	
	private FlowService flowService = new FlowService();
	
	
	/**
	 * 流程标题
	 */
	private String title;
	
	/**
	 * 流程ID
	 */
	private String flowID;
	
	public void getFlows() {
		if (null == getPage()) {
			setPage(1);
		}
		if (null == getRows()) {
			setRows(10);
		}
		Map<String, Object> conds = new HashMap<String, Object>();
		conds.put("title", this.title);
		responseJson(flowService.getFlows(conds, getSession("company").toString(), getRows(), getPage()));
	}

	/*
	@SuppressWarnings("unchecked")
	public void getFlowVersions() {
		if (null == getPage()) {
			setPage(1);
		}
		if (null == getRows()) {
			setRows(10);
		}
		
		List<FlowDto> flowList = (List<FlowDto>)getSession("dataFDList");
		
		if (flowList == null) {
			JSONObject data = new JSONObject();
			data.put("total", 0);
			data.put("rows", new ArrayList<FlowVersionDto>());
			responseJson(data.toJSONString());
		} else {
			int start = (getPage() - 1) * getRows();
			int end = start + getRows();
			List<FlowVersionDto> flowVersions = null;
			for (FlowDto flow : flowList) {
				if (this.flowID.equals(flow.getId())) {
					flowVersions = flow.getVersionsObj();
					break;
				}
			}
			if (flowVersions == null) {
				JSONObject data = new JSONObject();
				data.put("total", 0);
				data.put("rows", new ArrayList<FlowVersionDto>());
				responseJson(data.toJSONString());
			} else {
				int total = flowVersions.size();
				end = end > total?total: end;
				Collections.sort(flowVersions, new SortByInt());
				
				List<FlowVersionDto> retList = new ArrayList<FlowVersionDto>();
				for (int i = start; i < end; i++) {
					retList.add(flowVersions.get(i));
				};
				JSONObject data = new JSONObject();
				data.put("total", total);
				data.put("rows", retList);
				responseJson(JSON.toJSONStringWithDateFormat(data, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteNullStringAsEmpty));
			}
		}
	}
	*/

	public String getFlowID() {
		return flowID;
	}

	public void setFlowID(String flowID) {
		this.flowID = flowID;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	class SortByInt implements Comparator<FlowVersionDto> {

		@Override
		public int compare(FlowVersionDto o1, FlowVersionDto o2) {
			return o2.getVersion() - o1.getVersion();
		}
		
	}
}
