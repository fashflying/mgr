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
<link rel="stylesheet" type="text/css" href="js/themes/bootstrap/easyui.css"></link>
<link rel="stylesheet" type="text/css" href="js/themes/icon.css"></link>
<link rel="stylesheet" type="text/css" href="js/demo/demo.css"></link>
<link rel="stylesheet" type="text/css" href="css/default.css"></link>
<script type="text/javascript" src="js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/locale/easyui-lang-zh_CN.js"></script>
<title>管理维护平台</title>
</head>
<body style="width: 100%;height: 100%">
	<div class="easyui-layout" data-options="fit:true">
		<!-- top -->
		<div data-options="region:'north',height:43" style="border-bottom-width: 0">
			<form id="userDF">
				<table id="userDFT" >
					<tbody>
						<tr>
						<td><label>用户名</label>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><input id="userDN" name="fusername" type="text" style="height: 20px;border: 1px solid #ccc"/></td>
						<td>&nbsp;&nbsp;<img class="buttons" src="images/button/botton_cx.png" alt="查询"/>
						<label style="color: grey">&nbsp;|&nbsp;</label>
						<img class="buttons" src="images/button/botton_sc.png" alt="删除"/>
						</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- center -->
		<div data-options="region:'center',title:'已办列表'">
			<table id="userDT" style="padding-top: 80px" ></table>
		</div>
		<!-- bottom -->
		<div data-options="region:'south',height:200"  >
			<div id="userDTABS" class="easyui-tabs" data-options="height:190,border:false">
				<div id="Dad" title="活动详细信息" href="${pageContext.request.contextPath}/maintain/user_activitydetail" style="padding: 10px 15px"></div>
				<div id="Dnewact" title="新建活动" href="${pageContext.request.contextPath}/maintain/user_newActivity" style="padding: 10px 15px"></div>
				<div id="Dsat" title="设置活动状态" href="${pageContext.request.contextPath}/maintain/user_setActivityStatus" style="padding: 10px 15px"></div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
	$(function() {
		$("#userDT").datagrid({
			url:'',
			fit:true,
			border:false,
			striped:true,
			loadMsg:'数据处理中，请稍等 ...',
			singleSelect:true,
			pagination:true,
			columns:[[
			          {field:'1',title:'公文类型',width:110,align:'center'},    
			          {field:'2',title:'公文名',width:200,align:'center'},
			          {field:'3',title:'接收人',width:110,align:'center'},
			          {field:'4',title:'接收时间',width:110,align:'center'},
			          {field:'5',title:'发送人',width:110,align:'center'},
			          {field:'6',title:'活动名称',width:130,align:'center'}
			      ]]
		});
	})
</script>
</body> 
</html>