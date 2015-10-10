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

	<table id="sactsTBL" cellpadding="0" cellspacing="0" style="width: 100%;height: 50%">
		<tbody>
			<tr>
				<th>活动状态</th>
				<td colspan="3"><select class="easyui-combobox" data-options="valueField:'id',textField:'text',data:[{id:'000001',text:'活动'},{id:'000002',text:'完成'}],value:'000001',editable:false,width:55,height:24,panelHeight:80"></select></td>
			</tr>
			<tr>
				<td colspan="4"><img class="buttons" src="images/button/botton_bc.png" alt="保存"/></td>
			</tr>
		</tbody>
	</table>


</body> 
</html>