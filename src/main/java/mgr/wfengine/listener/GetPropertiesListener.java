package mgr.wfengine.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import mgr.wfengine.util.Utility;

public class GetPropertiesListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Utility.setWFENGINE_URL(arg0.getServletContext().getInitParameter(Utility.PARAM_NAME_WFENGINEURL));
		Utility.setUSER_WSURL(arg0.getServletContext().getInitParameter(Utility.PARAM_NAME_USERWSURL));
		Utility.setFORWARD_INTERFACE_URL(arg0.getServletContext().getInitParameter(Utility.PARAM_NAME_FORWARDINTERFACEURL));
	}

}
