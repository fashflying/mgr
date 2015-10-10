<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />
<title>管理维护平台</title>
</head>
<body>

	<table id="exceptionDetailTBL" cellpadding="0" cellspacing="0" style="width: 738px;height: 205px">
		<tbody>
			<tr>
				<th height="32px">异常ID</th>
				<td id="exceptionid"></td>
			</tr>
			<tr>
				<th height="32px">异常信息</th>
				<td id="exceptionMsg"></td>
			</tr>
			<tr>
				<th height="116px">堆栈信息</th>
				<td style="padding: 0"><div id="stackTrace" style="height: 126px;width:625px;margin: 0;overflow:auto;padding: 5px;"></div></td>
			</tr>
		</tbody>
	</table>


</body> 
</html>