package mgr.wfengine.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mgr.wfengine.util.Utility;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class ExceptionAction extends BaseAction {

	/** 
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 220066245107416010L;

	/**
	 * 异常优先级 
	 */
	private String priority;

	/**
	 * 异常发生时间FROM 
	 */
	private Date startHappendTime;

	/**
	 * 异常发生时间TO
	 */
	private Date endHappendTime;
	
	/**
	 * 异常ID
	 */
	private String exceptionInfoId;
	
	/**
	 * 处理者
	 */
	private String handler;
	
	/**
	 * 处理方法
	 */
	private String handleMethod;

	/**
	 * 异常处理时间FROM 
	 */
	private Date startHandleTime;

	/**
	 * 异常处理时间TO
	 */
	private Date endHandleTime;
	
	
	private static final Logger log = Logger.getLogger(ExceptionAction.class);
			
	public void getHandlingException() {
		if (null == getPage()) {
			setPage(1);
		}
		if (null == getRows()) {
			setRows(10);
		}
		Map<String, String> params = new HashMap<String, String>();
		JSONObject json = new JSONObject();
		json.put("handleType", "query");
		json.put("priority", this.priority);
		json.put("startHappendTime", this.startHappendTime);
		json.put("endHappendTime", this.endHappendTime);
		json.put("status", "0");
		json.put("pageNum", getPage());
		json.put("pageList", getRows());
		
		params.put("msg_type", "exception");
		params.put("msg_body", json.toJSONString());
		
		String response = "";
		try {
			log.info("访问地址：" + Utility.getWFENGINE_URL());
			response = Utility.postUrl(Utility.getWFENGINE_URL(), params);
			JSONObject j = JSON.parseObject(response);
			if ("Success".equals(j.get("result"))) {
				responseJson(j.getString("data"));
			} else {
				// TODO
				log.error(j.get("info"));
			}
		} catch (ClientProtocolException e) {
			log.error("服务器访问失败！",e);
		} catch (IOException e) {
			log.error("服务器访问失败！",e);
		}
	}
	
	public void getHandledException() {
		if (null == getPage()) {
			setPage(1);
		}
		if (null == getRows()) {
			setRows(10);
		}
		Map<String, String> params = new HashMap<String, String>();
		JSONObject json = new JSONObject();
		json.put("handleType", "query");
		json.put("handler", this.handler);
		json.put("startHandleTime", this.startHandleTime);
		json.put("endHandleTime", this.endHandleTime);
		json.put("status", "1");
		json.put("pageNum", getPage());
		json.put("pageList", getRows());

		params.put("msg_type", "exception");
		params.put("msg_body", json.toJSONString());

		String response = "";
		try {
			response = Utility.postUrl(Utility.getWFENGINE_URL(), params);
			JSONObject j = JSON.parseObject(response);
			if ("Success".equals(j.get("result"))) {
				responseJson(j.getString("data"));
			} else {
				// TODO
				log.error(j.get("info"));
			}
		} catch (ClientProtocolException e) {
			log.error("服务器访问失败！", e);
		} catch (IOException e) {
			log.error("服务器访问失败！", e);
		}
	}

	public void updateHandleException() {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, Object> datas = new HashMap<String, Object>();
		datas.put("exceptionInfoId", this.exceptionInfoId);
		datas.put("status", "1");
		datas.put("handleTime", new Date());
		datas.put("handleMethod", this.handleMethod);
		datas.put("handler", this.handler);
		JSONObject json = new JSONObject();
		json.put("handleType", "update");
		json.put("datas", datas);
		
		params.put("msg_type", "exception");
		params.put("msg_body", json.toJSONString());
		
		String response = "";
		try {
			response = Utility.postUrl(Utility.getWFENGINE_URL(), params);
			responseJson(response);
		} catch (ClientProtocolException e) {
			log.error("服务器访问失败！",e);
		} catch (IOException e) {
			log.error("服务器访问失败！",e);
		}
	}
	
	public void deleteException() {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, Object> datas = new HashMap<String, Object>();
		datas.put("exceptionInfoId", this.exceptionInfoId);
		JSONObject json = new JSONObject();
		json.put("handleType", "delete");
		json.put("datas", datas);
		
		params.put("msg_type", "exception");
		params.put("msg_body", json.toJSONString());
		String response = "";
		try {
			response = Utility.postUrl(Utility.getWFENGINE_URL(), params);
			responseJson(response);
		} catch (ClientProtocolException e) {
			log.error("服务器访问失败！",e);
		} catch (IOException e) {
			log.error("服务器访问失败！",e);
		}
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Date getStartHappendTime() {
		return startHappendTime;
	}

	public void setStartHappendTime(Date startHappendTime) {
		this.startHappendTime = startHappendTime;
	}

	public Date getEndHappendTime() {
		return endHappendTime;
	}

	public void setEndHappendTime(Date endHappendTime) {
		this.endHappendTime = endHappendTime;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getHandleMethod() {
		return handleMethod;
	}

	public void setHandleMethod(String handleMethod) {
		this.handleMethod = handleMethod;
	}

	public String getExceptionInfoId() {
		return exceptionInfoId;
	}

	public void setExceptionInfoId(String exceptionInfoId) {
		this.exceptionInfoId = exceptionInfoId;
	}

	public Date getStartHandleTime() {
		return startHandleTime;
	}

	public void setStartHandleTime(Date startHandleTime) {
		this.startHandleTime = startHandleTime;
	}

	public Date getEndHandleTime() {
		return endHandleTime;
	}

	public void setEndHandleTime(Date endHandleTime) {
		this.endHandleTime = endHandleTime;
	}
}
