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

	<table id="actTBL" cellpadding="0" cellspacing="0" style="width: 100%;height: 100%">
		<tbody>
			<tr>
				<th>活动ID</th>
				<td><input id="tbActId" class="easyui-textbox" data-options="height:24"/></td>
				<th>接收时间</th>
				<td><input id="tbAccepTime" class="easyui-textbox" data-options="height:24"/></td>
			</tr>
			<tr>
				<th>活动实例ID</th>
				<td><input id="tbActInsID" class="easyui-textbox" data-options="height:24"/></td>
				<th>活动状态</th>
				<td><input id="tbActStuts" class="easyui-textbox" data-options="height:24"/></td>
			</tr>
			<tr>
				<th>父活动实例ID</th>
				<td><input id="tbFActInsId" class="easyui-textbox" data-options="height:24"/></td>
				<th>开始时间</th>
				<td><input id="tbStartTime" class="easyui-textbox" data-options="height:24"/></td>
			</tr>
			<tr>
				<th>流程实例ID</th>
				<td><input id="tbFlowInsId" class="easyui-textbox" data-options="height:24"/></td>
				<th>发送者</th>
				<td><input id="tbSender" class="easyui-textbox" data-options="height:24"/></td>
			</tr>
		</tbody>
	</table>


</body> 
</html>