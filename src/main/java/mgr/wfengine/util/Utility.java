package mgr.wfengine.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import mgr.wfengine.service.DateSettingService;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

public class Utility {

	private static DateSettingService dateService = new DateSettingService();
	private static final Logger log = Logger.getLogger(Utility.class);
	/*
	private static Properties confProperties = new Properties();
	private static final String CONF_PATH = Thread.currentThread().getContextClassLoader().getResource("conf.properties").getFile();

	static {
		try {
			log.info("系统路径：" +Thread.currentThread().getContextClassLoader().getResource(""));
			log.info("文件路径："+Thread.currentThread().getContextClassLoader().getResource("conf.properties"));
			log.info("conf文件路径："+CONF_PATH);
			confProperties.load(new FileInputStream(CONF_PATH));
			log.info("confProperty:"+confProperties);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String getConfigProperty(String key) {
		return confProperties.getProperty(key);
	}
	*/

	private static SimpleDateFormat fmtDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd");
	
	public static final String PARAM_NAME_WFENGINEURL = "WFENGINE_URL";
	private static String WFENGINE_URL;
	public static final String PARAM_NAME_USERWSURL = "USER_WSURL";
	private static String USER_WSURL;
	public static final String PARAM_NAME_FORWARDINTERFACEURL = "FORWARD_INTERFACE_URL";
	private static String FORWARD_INTERFACE_URL;
	public static final String WFENGINE_DB = "wfengine";
	public static final String WFPORTALLET_DB = "wfportallet";
	
	public static String postUrl(String url, Map<String, String> parameters) throws ClientProtocolException, IOException {
		return postUrl(url, parameters, Charset.forName("utf-8"));
	}
	
	public static String postUrl(String url, Map<String, String> parameters, Charset charset) throws ClientProtocolException, IOException {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		StringBuffer result = new StringBuffer();
		BufferedReader reader = null;
		try {
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			Iterator<Map.Entry<String, String>> param = parameters.entrySet().iterator();
			while (param.hasNext()) {
				Map.Entry<String, String> entry = param.next();
			    formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			    log.info("参数["+entry.getKey()+"]:" + entry.getValue());
			}
		    UrlEncodedFormEntity params = new UrlEncodedFormEntity(formparams, charset);
//			String entityValue = URLEncodedUtils.format(formparams, charset);
//			Iterator<Map.Entry<String, String>> param = parameters.entrySet().iterator();
//			String[] args = new String[2];
//			StringBuffer format = new StringBuffer();
//			int cnt = 0;
//			while (param.hasNext()) {
//				Map.Entry<String, String> entry = param.next();
//				format.append(entry.getKey()).append("=%s&");
//				args[cnt++] = URLEncoder.encode(entry.getValue(), "UTF-8");
//			}
//			String entityValue = String.format(format.toString().substring(0, format.length() - 1), args);
//			StringEntity params = new StringEntity(entityValue);
		    
//		    BufferedReader isr = new BufferedReader(new InputStreamReader(params.getContent()));
//		    String l;
//		    StringWriter sw = new StringWriter();
//		    while ((l = isr.readLine()) != null) {
//		    	sw.write(l);
//		    }
//		    sw.flush();
//		    sw.close();
//			log.info("参数：" +  sw.toString());
			HttpPost request = new HttpPost(url);
			request.addHeader("content-type", "application/x-www-form-urlencoded");
			request.setEntity(params);
			CloseableHttpResponse httpResponse = httpClient.execute(request);
			reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), charset));
			String line = "";
			
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			
		} finally {
			if (reader != null) {
				reader.close();
			}
			httpClient.close();
		}
		return result.toString();
	}
	
	/**
	 * 活动超时时间点计算
	 * @param startTime
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public static Date timeoutDate(Date startTime, long timeout, TimeUnit unit) {
		Date startTimeZero = toDateTimeParse(toDateFormat(startTime) + " 00:00:00");
		long relativeTime = startTime.getTime() - startTimeZero.getTime() + unit.toMillis(timeout);
		long dayNum = relativeTime / TimeUnit.HOURS.toMillis(24);
		long extraTime = relativeTime % TimeUnit.HOURS.toMillis(24);
		int addDay = 0;
		Date timeoutDate = startTimeZero;
		while (dayNum >= 0) {
			timeoutDate = addDate(startTimeZero, addDay, TimeUnit.DAYS);
			if (!dateService.RestDayCheck(timeoutDate)) {
				dayNum--;
			}
			addDay++;
		}
		return new Date(timeoutDate.getTime() + extraTime);
	}

	public static Date getNow(){
		return new Date();
	}
	
	public static Date addDate(Date currentDate, int addTime, TimeUnit timeUnit) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		switch (timeUnit) {
		case DAYS:
			calendar.add(Calendar.DAY_OF_YEAR, addTime);
			break;
		case HOURS:
			calendar.add(Calendar.HOUR, addTime);
			break;
		case MINUTES:
			calendar.add(Calendar.MINUTE, addTime);
			break;
		case SECONDS:
			calendar.add(Calendar.SECOND, addTime);
			break;
		default:
			calendar.add(Calendar.MILLISECOND, (int)timeUnit.toMillis(addTime));
			break;
		}
		return calendar.getTime();
	}

	public static Date toDateParse(String date) {
		try {
			return fmtDate.parse(date);
		} catch (ParseException e) {
			log.error("日期转换错误！", e);
			return getNow();
		}
	}
	
	public static String toDateFormat(Date date) {
			try {
				return fmtDate.format(date);
			} catch (Exception e) {
				log.error("日期转换错误！", e);
				return "";
			}
	}
	
	public static boolean isWeekDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		if (weekDay == Calendar.SATURDAY || weekDay ==  Calendar.SUNDAY) {
			return true;
		}
		return false;
	}
	
	public static Date toDateTimeParse(String date) {
		try {
			return fmtDateTime.parse(date);
		} catch (Exception e) {
			log.error("日期转换错误！", e);
			return getNow();
		}
	}
	
	public static String toDateTimeFormat(Date date) {
		try {
			return fmtDateTime.format(date);
		} catch (Exception e) {
			log.error("日期转换错误！", e);
			return "";
		}
		
	}


	public static String getUSER_WSURL() {
		return USER_WSURL;
	}

	public static void setUSER_WSURL(String uSER_WSURL) {
		USER_WSURL = uSER_WSURL;
	}

	public static String getWFENGINE_URL() {
		return WFENGINE_URL;
	}

	public static void setWFENGINE_URL(String wFENGINE_URL) {
		WFENGINE_URL = wFENGINE_URL;
	}

	public static String getFORWARD_INTERFACE_URL() {
		return FORWARD_INTERFACE_URL;
	}

	public static void setFORWARD_INTERFACE_URL(String fORWARD_INTERFACE_URL) {
		FORWARD_INTERFACE_URL = fORWARD_INTERFACE_URL;
	}
}
