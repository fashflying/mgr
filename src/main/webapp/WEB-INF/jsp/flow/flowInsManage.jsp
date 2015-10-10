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
			<form id="flowInsDF">
				<table id="flowInsDFT" >
					<tbody>
						<tr>
						<td><label>流程标题</label>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><input id="flowInsDTitel" name="title" type="text" style="width:150px;height: 20px;border: 1px solid #ccc;"/>
						</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<label>流程创建时间</label>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><input id="flowInsDCTF" name="createTimeFrom" class="easyui-datetimebox" style="width: 140px"/>&nbsp;～&nbsp;<input id="flowInsDCTT" name="createTimeTo" class="easyui-datetimebox" style="width: 140px"/></td>
						<td>&nbsp;&nbsp;<img class="buttons" src="images/button/botton_cx.png" alt="查询" onclick="findFlowIns()"/>
						</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- center -->
		<div data-options="region:'center',title:'流程实例'">
			<table id="flowInsDT" style="padding-top: 80px" ></table>
		</div>
		<!-- bottom -->
		<div data-options="region:'south',height:256,title:'流程数据',collapsible:false,split:true"  >
			<table id="flowDataDT" class="easyui-datagrid"></table>
			<div id="tb">
				<a id="addD" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" disabled="true" onclick="addDataB()">新增</a>
				<a id="editD" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" disabled="true" onclick="editDataB()">修改</a>
				<a id="removeD" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" disabled="true" onclick="removeDataB()">删除</a>
				<a id="saveD" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" disabled="true" onclick="saveDataB()">保存</a>
			</div>
		</div>
	</div>
	<!-- 新增修改dialog -->
	<div id="FlowDataDig">
		<table id="flowDataTBL" cellpadding="0" cellspacing="0" style="width: 436px;height: 95px">
		<tbody>
			<tr>
				<th>流程数据名</th>
				<td><input id="flowDataN" name="flowDataName" type="text" style="width:310px;height: 20px;border: 1px solid #ccc;"/></td>
			</tr>
			<tr>
				<th>流程数据值</th>
				<td><input id="flowDataV" name="flowDataValue" type="text" style="width:310px;height: 20px;border: 1px solid #ccc;"/></td>
			</tr>
		</tbody>
		</table>
		<div style="text-align: center;margin-top: 15px"><img class="buttons" src="images/button/botton_bc.png" alt="保存" onclick="saveFlowData()"/></div>
	</div>
	<!-- 查看dialog -->
	<div id="FlowDataLookDig">
		<table id="flowDataLTBL" cellpadding="0" cellspacing="0" style="width: 436px;height: 95px">
		<tbody>
			<tr>
				<th height="40px">流程数据名</th>
				<td id="flowDataNL"></td>
			</tr>
			<tr>
				<th>流程数据值</th>
				<td style="padding: 0"><div id="flowDataVL" style="height: 91px;width:333px;margin: 0;overflow: auto;padding: 5px"></div></td>
			</tr>
		</tbody>
		</table>
	</div>
	<!-- 增加主送抄送dialog -->
	<div id="addZSCSDig"></div>
	<script type="text/javascript">
	var editFLG = '0';
	var dataDig;
	var ZSCSDig;
	var dataLDig;
	var dataDigFLG = '0';
	var flowInsId='';
	var editRow;
	function findFlowIns() {
		var title = $('#flowInsDTitel').val();
		var fromtime =  $('#flowInsDCTF').datetimebox('getValue');
		var totime = $('#flowInsDCTT').datetimebox('getValue');
		if (fromtime && totime && totime<fromtime) {
			$.messager.alert('提示','创建时间From不能大于To！','warning');
			return false;
		}
		$('#flowInsDT').datagrid('unselectAll');
		$('#flowInsDT').datagrid({
			pageNumber:1,
			url:'flowIns/get_FlowInses',
			queryParams:{
				title:title,
				createTimeFrom:fromtime,
				createTimeTo:totime
			}
		});
		
		//分页
		pagefunction("flowInsDT");
	}

	function pagefunction(id){
		 
		var p = $('#'+id).datagrid('getPager');
			$(p).pagination({

		        beforePageText: '第',//页数文本框前显示的汉字   

		        afterPageText: '页    共 {pages} 页',   

		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'

			});
	}
	
	function lookflowdata0() {
		if (editFLG == '1') {
			$.messager.confirm('确认','有未保存的流程数据，确定要放弃编辑吗？',function(data) {
				if (data) {
				lookflowdata1();
				editFLG = '0';
				}
			});
		} else {
			lookflowdata1();
		}
	}
	
	function lookflowdata1() {
		var row = $("#flowInsDT").datagrid('getSelected');
		flowInsId = row.id;
		var flowInsState = row.state;
		var datasArray = row.datas;
		for (var i=0; i<datasArray.length; i++) {
			datasArray[i].flowInsId = flowInsId;
		}
		
		buttonState(editFLG, datasArray.length, flowInsState);
		
 		var flowInsdata = eval(JSON.stringify(datasArray));
 		$('#flowDataDT').datagrid('unselectAll');
		$('#flowDataDT').datagrid({
			data:flowInsdata
		});
	}
	
	function buttonState(editFLG, dataCnt, flowInsState) {
		if (flowInsState == 'Finished' || flowInsState == 'Deleted') {
			$('#addD').linkbutton({disabled:true});
			$('#editD').linkbutton({disabled:true});
			$('#removeD').linkbutton({disabled:true});
			$('#saveD').linkbutton({disabled:true});
			return;
		}
		if (editFLG == '0') {
			if (dataCnt > 0) {
				$('#addD').linkbutton({disabled:false});
				$('#editD').linkbutton({disabled:false});
				$('#removeD').linkbutton({disabled:false});
				$('#saveD').linkbutton({disabled:true});
			} else {
				$('#addD').linkbutton({disabled:false});
				$('#editD').linkbutton({disabled:true});
				$('#removeD').linkbutton({disabled:true});
				$('#saveD').linkbutton({disabled:true});
			}
		} else {
			if (dataCnt > 0) {
				$('#addD').linkbutton({disabled:false});
				$('#editD').linkbutton({disabled:false});
				$('#removeD').linkbutton({disabled:false});
				$('#saveD').linkbutton({disabled:false});
			} else {
				$('#addD').linkbutton({disabled:false});
				$('#editD').linkbutton({disabled:true});
				$('#removeD').linkbutton({disabled:true});
				$('#saveD').linkbutton({disabled:false});
			}
		}
	}

	function onLoad() {
		$("td[field='flowInsId']:gt(0)").hover(function(){
			$(this).css({background:"#0081cc",cursor:"pointer",color:"white"});
		},
		function() {
			$(this).css({background:"",color:""});
		});
		$("td[field='flowInsId']:gt(0)").bind("click", function() {
			var index = $(this).parent().attr('datagrid-row-index');
			$("#flowDataDT").datagrid('selectRow', index);
			var row = $("#flowDataDT").datagrid('getSelected');
			$("#flowDataNL").text(row.dataName);
			$("#flowDataVL").text(row.dataValue);
			dataLDig.dialog('open');
		});
	}
	
	function addDataB() {
		dataDigFLG = '0';
		dataDig.dialog('open');
		$('#flowDataN').val('');
		$('#flowDataV').val('');
		$('#flowDataN').removeAttr('readonly');
		$('#flowDataN').focus();
	}
	
	function editDataB() {
		var row = $("#flowDataDT").datagrid('getSelected');
			if (row == null) {
				$.messager.alert('提示','请选择要修改的流程数据！','warning');
			} else {
  			dataDigFLG = '1';
  			dataDig.dialog('open');
  			editRow = row;
  			$('#flowDataN').val(row.dataName);
  			$('#flowDataV').val(row.dataValue);
  			$('#flowDataN').attr('readonly','readonly');
  			$('#flowDataV').focus();
			}
	}
	
	function removeDataB() {
		var row = $("#flowDataDT").datagrid('getSelected');
		if (row == null) {
			$.messager.alert('提示','请选择要修改的流程数据！','warning');
		} else {
			$.messager.confirm('确认','确定要删除该条流程数据吗？',function(data) {
				if (data) {
					//deleteFlowData(row);
					$("#flowDataDT").datagrid('deleteRow',$("#flowDataDT").datagrid('getRowIndex', row));
					editFLG = '1';
					var rows = $("#flowDataDT").datagrid('getRows');
					buttonState(editFLG, rows.length, '');
				}
			});
		}
	}
	
	function saveDataB() {
		$.messager.confirm('确认','确定要保存对流程数据的修改吗？',function(data) {
				if (data) {
				if (editFLG == '0') {
					$.messager.alert('提示','没有数据被修改，不能保存！','info');
				} else {
					var rows = $("#flowDataDT").datagrid('getRows');
					saveFlowDatas(rows);
				}
				}
			});
	}
	
	function saveFlowData() {
		var flowDataName = $('#flowDataN').val();
		var flowDataValue = $('#flowDataV').val();
		if (!flowDataName) {
			$.messager.alert('提示','流程数据名不能为空！','error',function(r) {
				$('#flowDataN').focus();
			});
			return false;
		}
		if (!flowDataValue) {
			$.messager.alert('提示','流程数据值不能为空！','error',function(r) {
				$('#flowDataV').focus();
			});
			return false;
		}
		var rows = $('#flowDataDT').datagrid('getRows');
		if (dataDigFLG == '1') {
			var index = $('#flowDataDT').datagrid('getRowIndex', editRow);
			editRow.dataValue = flowDataValue;
			$('#flowDataDT').datagrid('updateRow', {
					index:index,
					row:editRow
				});
			
			onLoad();
			/*
			$.post('flowIns/update_FlowData',
					{
						'flowDataName':flowDataName,
						'flowDataValue':flowDataValue
					},
					function(data) {
						if (data.result == "Success") {
							$.messager.alert('提示','流程数据更新成功！','info');
							dataDig.dialog('close');
							$('#flowDataDT').datagrid('reload');
				  			editFLG = '1';
						} else if (data.result == "NotExist") {
							$.messager.alert('提示','流程数据不存在！','error');
						} else {
							$.messager.alert('提示','流程实例已删除或结束，请确认！','error');
						}
					},'json');
			*/
		} else {
			for (var i=0;i<rows.length;i++) {
				if (rows[i].dataName == flowDataName) {
					$.messager.alert('提示','流程数据已存在，请确认！','error');
					return false;
				}
			}
			$('#flowDataDT').datagrid('appendRow', {
				flowInsId:flowInsId,
				dataName:flowDataName,
				dataValue:flowDataValue
			});
			
			onLoad();
			/*
			$.post('flowIns/add_FlowData',
					{
						'flowDataName':flowDataName,
						'flowDataValue':flowDataValue
					},
					function(data) {
						if (data.result == "Success") {
							$.messager.alert('提示','流程数据新增成功！','info');
							dataDig.dialog('close');
							$('#flowDataDT').datagrid('reload');
				  			editFLG = '1';
						} else if (data.result == "Duplication") {
							$.messager.alert('提示','流程数据名重复！','error');
						} else {
							$.messager.alert('提示','流程实例已删除或结束，请确认！','error');
						}
					},'json');
			*/
		}
		editFLG = '1';
		buttonState(editFLG, rows.length, '');
		dataDig.dialog('close');
		
	}
	
	/*
	function deleteFlowData(row) {
		var flowDataName = row.dataName;
		$.post('flowIns/delete_FlowData',
					{
						'flowDataName':flowDataName,
					},
					function(data) {
						if (data.result == "Success") {
							$.messager.alert('提示','流程数据删除成功！','info');
							$('#flowDataDT').datagrid('reload');
				  			editFLG = '1';
						} else if (data.result == "NotExist") {
							$.messager.alert('提示','流程数据不存在！','error');
						} else {
							$.messager.alert('提示','流程实例已删除或结束，请确认！','error');
						}
					},'json');
	}
	*/

	function saveFlowDatas(rows) {
		var rowsJSON = JSON.stringify(rows);
		$.post('flowIns/save_FLowDatas',
				{
					'flowInsID':flowInsId,
					'flowDatas':rowsJSON
				},
				function(data) {
					if (data.result == "Success") {
						$.messager.alert('提示','流程数据保存成功！','info');
				  		editFLG = '0';
				  		var rows = $('#flowDataDT').datagrid('getRows');
				  		buttonState(editFLG, rows.length, '');
				  		$('#flowInsDT').datagrid('reload');
					} else {
						$.messager.alert('提示',data.info,'error');
					}
						
				},'json');
	}
	
	function cancelFlowIns(row) {
		$.post('flowIns/cancel_FlowIns',
				{
					'flowInsID':row.id
				},
				function(data) {
					if (data.result == "Success") {
						$.messager.alert('提示','流程取消成功！','info');
						$('#flowInsDT').datagrid('reload');
					} else {
						$.messager.alert('提示','流程取消失败！','error');
					}
				},'json');
	}
	
	
	function deleteFlowIns(row) {
		$.post('flowIns/delete_FlowIns',
				{
					'flowInsID':row.id
				},
				function(data) {
					if (data.result == "Success") {
						$.messager.alert('提示','流程删除成功！','info');
						$('#flowInsDT').datagrid('reload');
					} else {
						$.messager.alert('提示','流程删除失败！','error');
					}
				},'json');
	}
	
	$(function() {
		$('#flowInsDTitel').focus();
		dataDig = $('#FlowDataDig').dialog({    
		    title: '流程数据',    
		    width: 450,    
		    height: 180,    
		    closed: true,    
		    cache: false,    
		    modal: true   
		}).dialog('close');
		
		dataLDig = $('#FlowDataLookDig').dialog({    
		    title: '流程数据',    
		    width: 450,    
		    height: 180,    
		    closed: true,    
		    cache: false,    
		    modal: true   
		}).dialog('close');
		
		ZSCSDig = $('#addZSCSDig').dialog({    
		    title: '增加主送抄送',    
		    width: 500,    
		    height: 180,    
		    closed: true,    
		    cache: false,    
		    modal: true   
		}).dialog('close');
		
		$("#flowInsDT").datagrid({
			fit:true,
			border:false,
			striped:true,
			loadMsg:'数据处理中，请稍等 ...',
			pagination:true,
			singleSelect:true,
			nowrap:true,
			idField:'id',
			columns:[[
			          {field:'id',title:'流程实例ID',width:240,align:'center'},    
			          {field:'title',title:'流程标题',width:200,align:'center'},
			          {field:'createTime',title:'创建时间',width:130,align:'center'},
			          {field:'state',title:'状态',width:98,align:'center'},
			          {field:'data',title:'流程数据',width:100,align:'center',
			        	  formatter:function(value,row) {
				        	  return "<div class='dataDiv' style='display:none'><a style='text-decoration:underline' href='javascript:void(lookflowdata0())'>流程数据管理</a></div>"
				          }}
			      ]],
			toolbar: [{
					text:'取消',
			  		iconCls: 'icon-cancel',
			  		handler: function(){
			  			var row = $("#flowInsDT").datagrid('getSelected');
			  			if (row == null) {
			  				$.messager.alert('提示','请选择要取消的流程！','warning');
			  			} else {
			  				$.messager.confirm('确认','确定要取消该流程吗？',function(data) {
			  					if (data) {
					  				if (row.state != 'Running') {
					  					$.messager.alert('提示','请选择未完成的流程！','error');
					  					return;
					  				}
			  						cancelFlowIns(row);
			  					}
			  				});
			  			}
			  		}
			  	},{
			  		text:'删除',
			  		iconCls: 'icon-remove',
			  		handler: function(){
			  			var row = $("#flowInsDT").datagrid('getSelected');
			  			if (row == null) {
			  				$.messager.alert('提示','请选择要删除的流程！','warning');
			  			} else {
			  				$.messager.confirm('确认','确定要删除该流程吗？',function(data) {
			  					if (data) {
			  						deleteFlowIns(row);
			  					}
			  				});
			  			}
			  		}
			  	},{
			  		text:'增加主送抄送',
			  		iconCls:'icon-add',
			  		handler:function() {
			  			var row = $("#flowInsDT").datagrid('getSelected');
			  			if (row == null) {
			  				$.messager.alert('提示','请选择要增加主送抄送的流程！','warning');
			  			} else {
			  				flowInsId = row.id;
			  				ZSCSDig.dialog('open');
			  			}
			  		}
			  	}],
			onLoadSuccess:function (data) {
					$('tr').hover(function(){
						if ($(this).find('td:last .dataDiv')[0]) {
							$(this).find('td:last .dataDiv')[0].style.display="block";
						}
					},
					function() {
						if ($(this).find('td:last .dataDiv')[0]) {
							$(this).find('td:last .dataDiv')[0].style.display="none";
						}
					});
				}

		});
		
		//分页
		pagefunction("flowInsDT");
		
		$("#flowDataDT").datagrid({
			fit:true,
			border:false,
			striped:true,
			loadMsg:'数据处理中，请稍等 ...',
			singleSelect:true,
			idField:'id',
			nowrap:true,
			remoteSort:false,
			columns:[[
			          {field:'flowInsId',title:'流程ID',width:238,align:'center'},
			          {field:'id',title:'流程数据ID',width:298,align:'center',hidden:true},    
			          {field:'dataName',title:'数据名称',width:220,align:'center',sortable:true,sorter:otherSort},
			          {field:'dataValue',title:'数据值',width:310,align:'center',sortable:true,sorter:otherSort}
			      ]],
			toolbar: '#tb',
			onLoadSuccess:onLoad
		});
	})
</script>
</body> 
</html>