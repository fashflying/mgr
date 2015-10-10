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
			<form id="handlingExceptionDF">
				<table id="handlingExceptionDFT" >
					<tbody>
						<tr>
						<td><label>异常级别</label>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><select id="priorityDN" name="priority" style="height: 20px;border: 1px solid #ccc;width: 60px;line-height: 20px">
								<option value=""></option>
								<option value="Low">普通</option>
								<option value="Medium">中等</option>
								<option value="Top">紧急</option>
						    </select>
						</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<label>异常发生时间</label>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><input id="excetpionDHTF" name="startHappendTime" class="easyui-datetimebox" style="width: 140px"/>&nbsp;～&nbsp;<input id="excetpionDHTT" name="endHappendTime" class="easyui-datetimebox" style="width: 140px" /></td>
						<td>&nbsp;&nbsp;<img class="buttons" src="images/button/botton_cx.png" alt="查询" onclick="findException0()"/>
						</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- center -->
		<div data-options="region:'center',title:'未处理异常'">
			<table id="handlingExceptionDT" style="padding-top: 80px" ></table>
		</div>
		<!-- bottom -->
		<div data-options="region:'south',height:256"  >
			<div id="exceptionDTABS" class="easyui-tabs" data-options="fit:true,border:false">
				<div id="DexceptionD" title="异常详情" href="${pageContext.request.contextPath}/maintain/exception_exceptionDetail" style="padding: 10px 15px"></div>
				<div id="DhandleE" title="处理异常" href="${pageContext.request.contextPath}/maintain/exception_handleException" style="padding: 10px 15px"></div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	function findException0() {
		var priority = $('#priorityDN').val();
		var fromtime =  $('#excetpionDHTF').datetimebox('getValue');
		var totime = $('#excetpionDHTT').datetimebox('getValue');
		if (fromtime && totime && totime<fromtime) {
			$.messager.alert('提示','异常发生时间From不能大于To！','warning');
			return false;
		}
		$('#handlingExceptionDT').datagrid('unselectAll');
		$('#handlingExceptionDT').datagrid({
			pageNumber:1,
			url:'exception/get_HandlingException',
			queryParams:{
				priority:priority,
				startHappendTime:fromtime,
				endHappendTime:totime
			}
		
		});
		
		// 分页
		pagefunction("handlingExceptionDT");
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
		var row = $("#handlingExceptionDT").datagrid('getSelected');
		
		// handle
		var exceptionid = $('#hexceptionid').text();
		var handler = $('#handler').val();
		var handleMethod = $('#handleMethod').val();
		if (exceptionid != row.id) {
			if (exceptionid != '' && (handler != '' || handleMethod != '')) {
				$.messager.confirm('确认','有正在处理的异常，确定要放弃吗！',
						function(r) {
							  if (r) {
								  reset();
								  lookexception1(row);
							  }
				});
			} else {
				lookexception1(row);
			}
		} else {
			$("#exceptionDTABS").tabs('select',0);
		}
	}
	
	function lookexception1(row) {
		// handle
		$('#hexceptionid').text(row.id);
		// look
		var stackTrace = row.stackTrace.replace(/\n/g, '<br/>&nbsp;&nbsp;');
		$('#exceptionid').text(row.id);
		$('#exceptionMsg').text(row.message);
		$('#stackTrace').html(stackTrace);
		$("#exceptionDTABS").tabs('select',0);
	}
	
	function handleexception() {
		var row = $("#handlingExceptionDT").datagrid('getSelected');
		// check
		var exceptionid = $('#hexceptionid').text();
		var handler = $('#handler').val();
		var handleMethod = $('#handleMethod').val();
		if (exceptionid != row.id) {
			if (exceptionid != '' && (handler != '' || handleMethod != '')) {
				$.messager.confirm('确认','有正在处理的异常，确定要放弃吗！',
						function(r) {
							  if (r) {
								  handleexcepiton1(row);
							  }
				});
			} else {
				handleexcepiton1(row);
			}
		} else {
			$("#exceptionDTABS").tabs('select',1);
		}
		
	}
	
	function handleexcepiton1(row) {
		// look
		var stackTrace = row.stackTrace.replace(/\n/g, '<br/>&nbsp;&nbsp;');
		$('#exceptionid').text(row.id);
		$('#exceptionMsg').text(row.message);
		$('#stackTrace').html(stackTrace);
		// handle
		reset();
		$('#hexceptionid').text(row.id);
		$("#exceptionDTABS").tabs('select',1);
		$('#handleMethod').focus();
	}
	
	function updateHandleException() {
		var exceptionid = $('#hexceptionid').text();
		var handler = $('#handler').val();
		var handleMethod = $('#handleMethod').val();
		if (exceptionid == '') {
			$.messager.alert('提示','请选择要处理的异常！','error');
			return false;
		}
		if (handleMethod == '') {
			$.messager.alert('提示','处理方法不能为空！','error');
			return false;
		}
		if (handler == '') {
			$.messager.alert('提示','处理者不能为空！','error');
			return false;
		}
		$.post('exception/update_HandleException',
				{
					"exceptionInfoId":exceptionid,
					"handler":handler,
					"handleMethod":handleMethod
				},
				function(data) {
					if (data.result == "Success") {
						$.messager.alert('提示','异常处理完成！','info');
						reset();
						$('#handlingExceptionDT').datagrid('reload');
					} else {
						$.messager.alert('提示','异常处理失败，请重试！','error');
					}
				},
				"json"); 
	}
	
	function reset() {
		$('#hexceptionid').text('');
		$('#handleMethod').val('');
		$('#handler').val('');
	}
	
	function deleteException(row) {
		$.post('exception/delete_Exception',
				{
					'exceptionInfoId':row.id
				},
				function(data) {
					if (data.result == "Success") {
						$.messager.alert('提示','删除成功！','info');
						$('#handlingExceptionDT').datagrid('reload');
					} else {
						$.messager.alert('提示','删除失败，请重试！','error');
					}
				},'json');
	}
	
	$(function() {
		var tabs = $("#exceptionDTABS").tabs('tabs');
		for (var i=tabs.length -1 ;i>=0;i--) {
			$("#exceptionDTABS").tabs('select',i);
		}
		$('#priorityDN').focus();
		$("#handlingExceptionDT").datagrid({
			url:'',
			fit:true,
			border:false,
			striped:true,
			loadMsg:'数据处理中，请稍等 ...',
			pagination:true,
			singleSelect:true,
			nowrap:true,
			idField:'id',
			columns:[[
			          {field:'id',title:'异常ID',width:240,align:'center'},    
			          {field:'priority',title:'异常级别',width:88,align:'center',
			        	  formatter:function(value, row) {
			        		  if (value == 'Low') {
			        			  return '普通';
			        		  } else if (value == 'Medium') {
			        			  return '中等';
			        		  } else if (value == 'Top') {
			        			  return '紧急';
			        		  } else {
			        			  return '';
			        		  }
			        	  }},
			          {field:'happendTime',title:'发生时间',width:140,align:'center'},
			          {field:'message',title:'异常信息',width:200,align:'center'},
			          {field:'handle',title:'操作',width:100,align:'center',
			        	  formatter:function(value,row,i) {
				        	  return "<div class='handleDiv' style='display:none'><a style='text-decoration:underline' href='javascript:void(lookexception())'>查看</a>"+
				        	  "&nbsp;&nbsp;&nbsp;&nbsp;<a style='text-decoration:underline' href='javascript:void(handleexception())'>处理</a></div>"
				          }}
			      ]],
			toolbar: [{
			  		text:'删除',
			  		iconCls: 'icon-remove',
			  		handler: function(){
			  			var row = $("#handlingExceptionDT").datagrid('getSelected');
			  			if (row == null) {
			  				$.messager.alert('提示','请选择要删除的数据！','warning');
			  			} else {
			  				$.messager.confirm('确认','确定要删除该条数据吗？',function(data) {
			  					if (data) {
			  						deleteException(row);
			  					}
			  				});
			  			}
			  		}
			  	}],
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
		pagefunction("handlingExceptionDT");
	})
</script>
</body> 
</html>