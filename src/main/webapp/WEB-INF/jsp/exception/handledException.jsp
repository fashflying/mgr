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
			<form id="handledExceptionDF">
				<table id="handledExceptionDFT" >
					<tbody>
						<tr>
						<td><label>处理者</label>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><input id="handlerDN" name="handler" type="text" style="width:150px;"/>
						</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<label>处理时间</label>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><input id="handleDTF" name="startHandleTime" class="easyui-datetimebox" style="width: 140px"/>&nbsp;～&nbsp;<input id="handleDTT" name="startHandleTime" class="easyui-datetimebox" style="width: 140px"/></td>
						<td>&nbsp;&nbsp;<img class="buttons" src="images/button/botton_cx.png" alt="查询" onclick="findHandledException()"/>
						</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- center -->
		<div data-options="region:'center',title:'已处理异常'">
			<table id="handledExceptionDT" style="padding-top: 80px" ></table>
		</div>
		<!-- bottom -->
		<div data-options="region:'south',height:260"  >
			<div id="exceptionDTABS" class="easyui-tabs" data-options="fit:true,border:false">
				<div id="DexceptionD" title="异常详情" href="${pageContext.request.contextPath}/maintain/exception_exceptionDetail" style="padding: 10px 15px"></div>
				<div id="DhandleD" title="处理详情" href="${pageContext.request.contextPath}/maintain/exception_handleDetail" style="padding: 10px 15px"></div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	function findHandledException() {
		var handler = $('#handlerDN').val();
		var fromTime = $('#handleDTF').datetimebox('getValue');
		var toTime = $('#handleDTT').datetimebox('getValue');
		
		if (fromTime && toTime && toTime < fromTime) {
			$.messager.alert('提示','异常发生时间From不能大于To！','warning');
			return false;
		}
		$('#handledExceptionDT').datagrid('unselectAll');
		$('#handledExceptionDT').datagrid({
			pageNumber:1,
			url:'exception/get_HandledException',
			queryParams:{
				handler:handler,
				startHandleTime:fromTime,
				endHandleTime:toTime
			}
		
		});
		
		// 分页
		pagefunction("handledExceptionDT");
		
	}
	
	function pagefunction(id){
		 
		var p = $('#'+id).datagrid('getPager');
			$(p).pagination({

		        beforePageText: '第',//页数文本框前显示的汉字   

		        afterPageText: '页    共 {pages} 页',   

		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'

			});
	}
	
	
	function lookexception() {
		var row = $("#handledExceptionDT").datagrid('getSelected');
		
		//handle
		var handleMethod = row.handleMethod.replace(/\n/g, "<br/>")
		$('#hexceptionid').text(row.id);
		$('#handleMethod').html(handleMethod);
		$('#handler').html(row.handler);
		
		//look
		var stackTrace = row.stackTrace.replace(/\n/g, '<br/>&nbsp;&nbsp;');
		$('#exceptionid').text(row.id);
		$('#exceptionMsg').text(row.message);
		$('#stackTrace').html(stackTrace);
		$("#exceptionDTABS").tabs('select',0);
	}
	function lookhandle() {
		var row = $("#handledExceptionDT").datagrid('getSelected');
		
		//look
		var stackTrace = row.stackTrace.replace(/\n/g, '<br/>&nbsp;&nbsp;');
		$('#exceptionid').text(row.id);
		$('#exceptionMsg').text(row.message);
		$('#stackTrace').html(stackTrace);
		//handle
		var handleMethod = row.handleMethod.replace(/\n/g, "<br/>")
		$('#hexceptionid').text(row.id);
		$('#handleMethod').html(handleMethod);
		$('#handler').html(row.handler);
		$("#exceptionDTABS").tabs('select',1);
	}
	$(function() {
		$('#handlerDN').focus();
		var tabs = $('#exceptionDTABS').tabs('tabs');
		for (var i=tabs.length-1;i>=0;i--) {
			$('#exceptionDTABS').tabs('select',i);
		}
		$("#handledExceptionDT").datagrid({
			data:[],
			fit:true,
			border:false,
			striped:true,
			loadMsg:'数据处理中，请稍等 ...',
			pagination:true,
			singleSelect:true,
			nowrap:true,
			remoteSort:false,
			idField:'id',
			columns:[[
			          {field:'id',title:'异常ID',width:240,align:'center'},    
			          {field:'message',title:'异常信息',width:200,align:'center'},
			          {field:'handleTime',title:'处理时间',width:120,align:'center'},
			          {field:'handler',title:'处理者',width:68,align:'center'},
			          {field:'handle',title:'操作',width:140,align:'center',
			        	  formatter:function(value,row,i) {
				        	  return "<div class='handleDiv' style='display:none'><a style='text-decoration:underline' href='javascript:void(lookexception())'>异常详情</a>"+
				        	  "&nbsp;&nbsp;&nbsp;&nbsp;<a style='text-decoration:underline' href='javascript:void(lookhandle())'>处理详情</a></div>"
				          }}
			      ]],
			onLoadSuccess:function (data) {
					$('tr').hover(function(){
						if ($(this).find('td:last .handleDiv')[0]) {
							$(this).find('td:last .handleDiv')[0].style.display="block";
						}
					},
					function() {
						if ($(this).find('td:last .handleDiv')[0]) {
							$(this).find('td:last .handleDiv')[0].style.display="none";
						}
					});
				}

		});
		
		// 分页
		pagefunction("handledExceptionDT");
	})
</script>
</body> 
</html>