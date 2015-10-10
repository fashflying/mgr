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
<script type="text/javascript" src="js/sort.js"></script>
<script type="text/javascript" src="js/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="json/json2.js"></script>
<title>管理维护平台</title>
</head>
<body style="width: 100%;height: 100%">
	<div class="easyui-layout" data-options="fit:true">
		<!-- top -->
		<div data-options="region:'north',height:43" style="border-bottom-width: 0">
			<form id="flowDefDF">
				<table id="flowDefDFT" >
					<tbody>
						<tr>
						<td><label>流程标题</label>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><input id="flowTitleD" name="title" type="text" style="width:150px;height: 20px;border: 1px solid #ccc;"/></td>
						<td>&nbsp;&nbsp;<img class="buttons" src="images/button/botton_cx.png" alt="查询" onclick="findFlow()"/>
						</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- center -->
		<div data-options="region:'center',title:'流程定义'">
			<table id="flowDefDT" class="easyui-datagrid"></table>
		</div>
		<!-- bottom -->
		<div data-options="region:'south',height:260,title:'版本信息',collapsible:false,split:true"  >
			<table id="flowVersionDT"  class="easyui-datagrid"></table>
		</div>
	</div>
	<div id="flowVersionDig">
		<table id="flowVersionTBL" cellpadding="0" cellspacing="0" style="width: 636px;height: 314px">
		<tbody>
			<tr>
				<th style="height: 35px;width: 100px">流程版本ID</th>
				<td id="flowId" colspan="2"></td>
				<th style="width: 100px">流程版本</th>
				<td id="flowVersion" width="80px"></td>
			</tr>
			<tr>
				<th style="height: 35px">创建时间</th>
				<td id="flowCreateTime" width="210px"></td>
				<th width="100px">编辑时间</th>
				<td colspan="2" id="flowEditTime"></td>
			</tr>
			<tr>
				<th>流程详细</th>
				<td colspan="4" style="padding: 0"><div id="flowDefine" style="height: 228px;width:526px;margin: 0;overflow: auto;padding: 5px;"></div></td>
			</tr>
		</tbody>
		</table>
	</div>
<script type="text/javascript">
	var versionDig;
	function findFlow() {
		var title = $('#flowTitleD').val();
		$('#flowDefDT').datagrid('unselectAll');
		$('#flowDefDT').datagrid({
			pageNumber:1,
			url:'flow/get_Flows',
			queryParams:{
				title:title
			}
		});
		
		//分钟
		pagefunction("flowDefDT");
	}

	function pagefunction(id){
		 
		var p = $('#'+id).datagrid('getPager');
			$(p).pagination({

		        beforePageText: '第',//页数文本框前显示的汉字   

		        afterPageText: '页    共 {pages} 页',   

		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'

			});
	}

 	function lookflowversion() {
 		var row = $("#flowDefDT").datagrid('getSelected');
 		var versionData = eval(JSON.stringify(row.versions));
 		$('#flowVersionDT').datagrid('unselectAll');
		$('#flowVersionDT').datagrid({
			data:versionData
		});
 	}
 	
	$(function() {
		$('#flowTitleD').focus();
		$('#flowTitleD').keydown(function(event) {
			var myEvent = event || window.event;
			var keyCode = myEvent.keyCode;//获得键值
			if (keyCode == 13) {
				return false;
			}
		});
		
		versionDig = $('#flowVersionDig').dialog({    
		    title: '流程数据',    
		    width: 650,    
		    height: 350,    
		    closed: true,    
		    cache: false,    
		    modal: true   
		}).dialog('close');
		
		$("#flowDefDT").datagrid({
			fit:true,
			border:false,
			striped:true,
			loadMsg:'数据处理中，请稍等 ...',
			singleSelect:true,
			idField:'id',
			pagination:true,
			nowrap:true,
			columns:[[
			          {field:'id',title:'流程ID',width:250,align:'center'},    
			          {field:'name',title:'流程名称',width:200,align:'center'},
			          {field:'title',title:'流程标题',width:208,align:'center'},
			          {field:'version',title:'版本',width:110,align:'center',
			        	  formatter:function(value,row) {
			        	  return "<div class='versionDiv' style='display:none'><a style='text-decoration:underline' href='javascript:void(lookflowversion())'>查看版本</a></div>"
			          }}
			      ]],
			onLoadSuccess:function (data) {
				$('tr').hover(function(){
					if ($(this).find('td:last .versionDiv')[0]) {
						$(this).find('td:last .versionDiv')[0].style.display="block";
					}
				},
				function() {
					if ($(this).find('td:last .versionDiv')[0]) {
						$(this).find('td:last .versionDiv')[0].style.display="none";
					}
				});
			}
		});

		//分钟
		pagefunction("flowDefDT");
		
		$("#flowVersionDT").datagrid({
			fit:true,
			border:false,
			striped:true,
			loadMsg:'数据处理中，请稍等 ...',
			singleSelect:true,
			idField:'id',
			remoteSort:false,
			nowrap:true,
			columns:[[
			          {field:'id',title:'流程版本ID',width:230,align:'center',sortable:true,sorter:otherSort},    
			          {field:'createTime',title:'创建时间',width:140,align:'center',sortable:true,sorter:dateTimeSort},
			          {field:'editTime',title:'最后编辑时间',width:140,align:'center',sortable:true,sorter:dateTimeSort},
			          {field:'version',title:'版本号',width:70,align:'center',sortable:true,sorter:otherSort},
			          {field:'define',title:'详细',width:187,align:'center',
			        	  formatter:function(value) {
			        		  if (value == '') return value;
			        		  return value.replace(/</g,"&lt").replace(/>/g, "&gt");
			        	  }}
			      ]],
			onLoadSuccess:function (data) {
				var i = $("#flowDefDT").datagrid('getRows').length+1;
				$("td[field='id']:gt("+i+")").attr("id","data");
				$("td[id='data'][field='id']").hover(function(){
					$(this).css({background:"#0081cc",cursor:"pointer",color:"white"});
				},
				function() {
					$(this).css({background:"",color:""});
				});
				$("td[id='data'][field='id']").bind("click", function() {
					var index = $(this).parent().attr('datagrid-row-index');
					$("#flowVersionDT").datagrid('selectRow', index);
					var row = $("#flowVersionDT").datagrid('getSelected');
					$("#flowId").text(row.id);
					$("#flowVersion").text(row.version);
					$("#flowCreateTime").text(row.createTime);
					$("#flowEditTime").text(row.editTime);
					$("#flowDefine").text(row.define);
					versionDig.dialog('open');
				});
			}
		});
	})
</script>
</body> 
</html>