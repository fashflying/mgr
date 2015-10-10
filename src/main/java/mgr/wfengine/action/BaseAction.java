package mgr.wfengine.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4657882804421374876L;
	
	private static final Logger log = Logger.getLogger(BaseAction.class);
	
	protected static Map<String, String> dateMap = new HashMap<String, String>();
	
	private Integer rows=10;//每页显示的记录数   
	
	private Integer page=1;//当前第几页  
	
	private String company;
	
	/**
	 * 返回json数据
	 * @param json
	 */
	public void responseJson(String json) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			log.error("Response写入异常", e);
		}
	}
	
	/**
	 * 设定session
	 * @param strKey
	 * @param o
	 */
	public void setSession(String strKey, Object o) {
		ServletActionContext.getContext().getSession().put(strKey, o);
	}

	/**
	 * 取得session
	 * @param strKey
	 * @return
	 */
	public Object getSession(String strKey) {
		return ServletActionContext.getContext().getSession().get(strKey);
	}
	
	/**
	 * 删除session
	 * @param strKey
	 */
	public void removeSession(String strKey) {
		ServletActionContext.getContext().getSession().remove(strKey);
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		if (rows == null) {
			rows = 10;
		}
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		if (page == null) {
			page = 1;
		}
		this.page = page;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		if (company != null) {
			setSession("company", company);
		}
		this.company = company;
	}
}
