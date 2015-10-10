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
	<div style="border:1px solid #ccc;width:300px;height:148px;float:left;position: relative;top:10px;left:15px " >
		<table id="actvT" class="easyui-datagrid"></table>
	</div>
	<table id="actvmTBL" cellpadding="0" cellspacing="0" style="width: 400px;height: 148px;position: relative;top:10px;left:25px">
		<tbody>
			<tr>
				<td colspan="2"><img class="buttons" src="images/button/botton_tjhdbl.png" alt="添加活动变量"/></td>
			</tr>
			<tr>
				<th>活动变量名称</th>
				<td><input id="tbActInsID" class="easyui-textbox" data-options="height:24"/></td>
			</tr>
			<tr>
				<th>活动变量值</th>
				<td><input id="tbActInsID" class="easyui-textbox" data-options="height:24"/></td>
			</tr>
			<tr>
				<td colspan="2"><img class="buttons" src="images/button/botton_bc.png" alt="保存"/></td>
			</tr>
		</tbody>
	</table>
	
<script type="text/javascript">
$(function() {
	$("#actvT").datagrid({
		url:'',
		fit:true,
		border:false,
		striped:true,
		loadMsg:'数据处理中，请稍等 ...',
		singleSelect:true,
		columns:[[
		          {field:'1',title:'变量名称',width:150,align:'center'},    
		          {field:'2',title:'变量值',width:150,align:'center'}
		      ]]
	});
})
</script>
</body> 
</html>