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
<script type="text/javascript" src="json/json2.js"></script>
<script type="text/javascript" src="js/sort.js"></script>
<title>管理维护平台</title>
</head>
<body style="width: 100%;height: 100%">
	<div class="easyui-layout" data-options="fit:true">
		<!-- top -->
		<div data-options="region:'north',height:43" style="border-bottom-width: 0">
			<form id="activityInsDF">
				<table id="activityInsDFT" >
					<tbody>
						<tr>
						<td><label>流程实例ID</label>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><input id="flowInsDID" name="flowInstanceId" type="text" style="width:180px;height: 20px;border: 1px solid #ccc;"/>
						</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<label>活动创建时间</label>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><input id="actInsDCTF" name="createTimeFrom" class="easyui-datetimebox" style="width: 140px"/>&nbsp;～&nbsp;<input id="actInsDCTT" name="createTimeTo" class="easyui-datetimebox" style="width: 140px"/></td>
						<td>&nbsp;&nbsp;<img class="buttons" src="images/button/botton_cx.png" alt="查询" onclick="findActivityIns()"/>
						</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- center -->
		<div data-options="region:'center',title:'活动实例'">
			<table id="actInsDT" style="padding-top: 80px" ></table>
		</div>
		<!-- bottom -->
		<!-- 
		<div data-options="region:'south',height:231,title:'活动数据',collapsible:false,split:true"  >
			<table id="actDataDT" class="easyui-datagrid"></table>
			<div id="tb">
				<a id="addD" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" disabled="true" onclick="addDataB()">新增</a>
				<a id="editD" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" disabled="true" onclick="editDataB()">修改</a>
				<a id="removeD" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" disabled="true" onclick="removeDataB()">删除</a>
				<a id="saveD" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" disabled="true" onclick="saveDataB()">保存</a>
			</div>
		</div>
		 -->
	<!-- 新增修改dialog
	<div id="ActDataDig">
		<table id="actDataTBL" cellpadding="0" cellspacing="0" style="width: 436px;height: 95px">
		<tbody>
			<tr>
				<th>活动数据名</th>
				<td><input id="actDataN" name="actDataName" type="text" style="width:310px;height: 20px;border: 1px solid #ccc;"/></td>
			</tr>
			<tr>
				<th>活动数据值</th>
				<td><input id="actDataV" name="actDataValue" type="text" style="width:310px;height: 20px;border: 1px solid #ccc;"/></td>
			</tr>
		</tbody>
		</table>
		<div style="text-align: center;margin-top: 15px"><img class="buttons" src="images/button/botton_bc.png" alt="保存" onclick="saveActData()"/></div>
	</div>
	 -->
	<!-- 查看dialog 
	<div id="ActDataLookDig">
		<table id="actDataLTBL" cellpadding="0" cellspacing="0" style="width: 436px;height: 95px">
		<tbody>
			<tr>
				<th height="40px">活动数据名</th>
				<td id="actDataNL"></td>
			</tr>
			<tr>
				<th>活动数据值</th>
				<td style="padding: 0"><div id="actDataVL" style="height: 91px;width:333px;margin: 0;overflow: auto;padding: 5px"></div></td>
			</tr>
		</tbody>
		</table>
	</div>
	-->
	<!-- 新增多实例活动dialog -->
	<div id="addActIns"></div>

	
	<script type="text/javascript">
	var editFLG = '0';
	var dataDig;
	var dataLDig;
	var addActInsDig;
	var parentActInsId;
	var addActDef;
	var flowDef;
	var dataDigFLG = '0';
	var actInsId='';
	var editRow;
	function findActivityIns() {
		var flowInsId = $('#flowInsDID').val();
		var fromtime = $('#actInsDCTF').datetimebox('getValue');
		var totime = $('#actInsDCTT').datetimebox('getValue');
		if (fromtime && totime && totime<fromtime) {
			$.messager.alert('提示','创建时间From不能大于To！','warning');
			return false;
		}
		$('#actInsDT').datagrid('unselectAll');
		$('#actInsDT').datagrid({
			pageNumber:1,
			url:'activityIns/get_ActivityInses',
			queryParams:{
				flowInstanceId:flowInsId,
				createTimeFrom:fromtime,
				createTimeTo:totime
			}
		});
		
		pagefunction('actInsDT');
	}
	
	function pagefunction(id){
		 
		var p = $('#'+id).datagrid('getPager');
			$(p).pagination({

		        beforePageText: '第',//页数文本框前显示的汉字   

		        afterPageText: '页    共 {pages} 页',   

		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'

			});
	}
	/*
	function lookactdata0() {
		if (editFLG == '1') {
			$.messager.confirm('确认','有未保存的活动数据，确定要放弃编辑吗？',function(data) {
				if (data) {
					editFLG='0';
					lookactdata1();
				}
			});
		} else {
			lookactdata1();
		}
	}
	
	function lookactdata1() {
		var row = $("#actInsDT").datagrid('getSelected');
		actInsId = row.id;
		var actInsState = row.state;
 		var actInsDatas = row.datas;
 		$('#actDataDT').datagrid('unselectAll');
		$('#actDataDT').datagrid({
			url:'activityIns/get_ActivityInsData',
			queryParams:{
				activityInsID:actInsId,
				activityInsDatas:actInsDatas
			}
		});
		
		var rows = $("#actDataDT").datagrid('getRows');
 		buttonState(editFLG, rows.length, actInsState);
	}

	function buttonState(editFLG, dataCnt, actInsState) {
		if (actInsState == 'Finished' || actInsState == 'Deleted') {
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
		$("td[field='activityInsId']:gt(0)").hover(function(){
			$(this).css({background:"#0081cc",cursor:"pointer",color:"white"});
		},
		function() {
			$(this).css({background:"",color:""});
		});
		$("td[field='activityInsId']:gt(0)").bind("click", function() {
			var index = $(this).parent().attr('datagrid-row-index');
			$("#actDataDT").datagrid('selectRow', index);
			var row = $("#actDataDT").datagrid('getSelected');
			$("#actDataNL").text(row.dataName);
			$("#actDataVL").text(row.dataValue);
			dataLDig.dialog('open');
		});
	}
	
	function addDataB() {
		dataDigFLG = '0';
		dataDig.dialog('open');
		$('#actDataN').val('');
		$('#actDataV').val('');
		$('#actDataN').removeAttr('readonly');
		$('#actDataN').focus();
	}
	
	function editDataB() {
		var row = $("#actDataDT").datagrid('getSelected');
			if (row == null) {
				$.messager.alert('提示','请选择要修改的活动数据！','warning');
			} else {
  			dataDigFLG = '1';
  			dataDig.dialog('open');
  			editRow = row;
  			$('#actDataN').val(row.dataName);
  			$('#actDataV').val(row.dataValue);
  			$('#actDataN').attr('readonly','readonly');
  			$('#actDataV').focus();
			}
	}
	
	function removeDataB() {
		
		var row = $("#actDataDT").datagrid('getSelected');
			if (row == null) {
				$.messager.alert('提示','请选择要修改的活动数据！','warning');
			} else {
				$.messager.confirm('确认','确定要删除该条活动数据吗？',function(data) {
					if (data) {
						//deleteActData(row);
						$("#actDataDT").datagrid('deleteRow',$("#actDataDT").datagrid('getRowIndex', row));
						editFLG = '1';
						var rows = $("#actDataDT").datagrid('getRows');
						buttonState(editFLG, rows.length, '');
					}
				});
			}
	}
	
	function saveDataB() {
		$.messager.confirm('确认','确定要保存对活动数据的修改吗？',function(data) {
				if (data) {
				if (editFLG == '0') {
					$.messager.alert('提示','没有数据被修改，不能保存！','info');
				} else {
					var rows = $("#actDataDT").datagrid('getRows');
					saveActDatas(rows);
				}
				}
			});
	}
	
	function saveActData() {
		var actDataName = $('#actDataN').val();
		var actDataValue = $('#actDataV').val();
		if (!actDataName) {
			$.messager.alert('提示','活动数据名不能为空！','error',function(r) {
				$('#actDataN').focus();
			});
			return false;
		}
		if (!actDataValue) {
			$.messager.alert('提示','活动数据值不能为空！','error',function(r) {
				$('#actDataV').focus();
			});
			return false;
		}

		var rows = $('#actDataDT').datagrid('getRows');
		if (dataDigFLG == '1') {
			var index = $('#actDataDT').datagrid('getRowIndex', editRow);
			editRow.dataValue = actDataValue;
			$('#actDataDT').datagrid('updateRow', {
					index:index,
					row:editRow
				});
			
		} else {
			for (var i=0;i<rows.length;i++) {
				if (rows[i].dataName == actDataName) {
					$.messager.alert('提示','活动数据已存在，请确认！','error');
					return false;
				}
			}
			$('#actDataDT').datagrid('appendRow', {
				activityInsId:actInsId,
				dataName:actDataName,
				dataValue:actDataValue
			});
		}

		onLoad();
		editFLG = '1';
		buttonState(editFLG, rows.length, '');
		dataDig.dialog('close');
	}

	function saveActDatas(rows) {
		var rowsJSON = JSON.stringify(rows);
		$.post('activityIns/save_ActivityInsDatas',
				{
					'activityInsID':actInsId,
					'activityInsDatas':rowsJSON
				},
					function(data) {
						if (data.result == "Success") {
							$.messager.alert('提示','活动数据保存成功！','info');
				  			editFLG = '0';
					  		var rows = $('#actDataDT').datagrid('getRows');
					  		buttonState(editFLG, rows.length, '');
				  			$('#actInsDT').datagrid('reload');
						} else {
							$.messager.alert('提示',data.info,'error');
						}
					},'json');
	}
	*/
	
	function cancelActIns(row) {
		$.post('activityIns/cancel_ActivityIns',
				{
					'activityInsID':row.id
				},
				function(data) {
					if (data.result == "Success") {
						$.messager.alert('提示','活动取消成功！','info');
						$('#actInsDT').datagrid('reload');
					} else {
						$.messager.alert('提示',data.info,'error');
					}
				},'json');
	}
	
	function deleteActIns(row) {
		$.post('activityIns/delete_ActivityIns',
				{
					'activityInsID':row.id
				},
				function(data) {
					if (data.result == "Success") {
						$.messager.alert('提示','活动删除成功！','info');
						$('#actInsDT').datagrid('reload');
					} else {
						$.messager.alert('提示',data.info,'error');
					}
				},'json');
	}
	
	function undoActIns(row) {
		$.post('activityIns/undo_ActivityIns',
				{
					'activityInsID':row.id
				},
				function(data) {
					if (data.result == "Success") {
						$.messager.alert('提示','活动撤回成功！','info');
						$('#actInsDT').datagrid('reload');
					} else {
						$.messager.alert('提示',data.info,'error');
					}
				},'json');
	}
	
	$(function() {
		$('#actInsDCTF').focus();
		/*
		dataDig = $('#ActDataDig').dialog({    
		    title: '活动数据',    
		    width: 450,    
		    height: 180,    
		    closed: true,    
		    cache: false,    
		    modal: true   
		}).dialog('close');
		

		dataLDig = $('#ActDataLookDig').dialog({    
		    title: '活动数据',    
		    width: 450,    
		    height: 180,    
		    closed: true,    
		    cache: false,    
		    modal: true   
		}).dialog('close');
		*/
		
		addActInsDig = $('#addActIns').dialog({    
		    title: '新增活动',
		    width: 600,    
		    height: 500,    
		    closed: true,    
		    cache: false,    
		    modal: true
		}).dialog('close');
		
		$("#actInsDT").datagrid({
			fit:true,
			border:false,
			striped:true,
			loadMsg:'数据处理中，请稍等 ...',
			pagination:true,
			singleSelect:true,
			nowrap:true,
			idField:'id',
			columns:[[
			          {field:'id',title:'活动实例ID',width:240,align:'center'},    
			          {field:'name',title:'活动名称',width:150,align:'center'},
			          {field:'activityType',title:'活动类型',width:130,align:'center'},
			          {field:'createTime',title:'创建时间',width:150,align:'center'},
			          {field:'state',title:'状态',width:98,align:'center'}
			          //{field:'data',title:'活动数据',width:130,align:'center',
			        	//  formatter:function(value,row) {
				       // 	  return "<div class='dataDiv' style='display:none'><a style='text-decoration:underline' href='javascript:void(lookactdata0())'>活动数据管理</a></div>"
				        //  }}
			      ]],
			toolbar: [{
		  		text:'新增',
		  		iconCls: 'icon-add',
		  		handler: function(){
		  			var row = $("#actInsDT").datagrid('getSelected');
		  			if (row == null) {
		  				$.messager.alert('提示','请选择要增加活动的父活动！','warning');
		  			} else {
		  				parentActInsId = row.id;
		  				$.post('activityIns/get_AddActivity',
		  						{
		  							'activityInsID':parentActInsId
		  						},
		  							function(data) {
		  								if (data.result == "Success") {
		  									addActDef = data.addAct;
		  									flowDef = data.flowDef;
		  									addActInsDig.dialog('open').dialog('refresh','${pageContext.request.contextPath}/maintain/activity_addAct');
		  								}else {
		  									$.messager.alert('提示',data.info,'error');
		  								}
		  							},'json');
		  			}
		  		}
		  	},{
			  		text:'删除',
			  		iconCls: 'icon-remove',
			  		handler: function(){
			  			var row = $("#actInsDT").datagrid('getSelected');
			  			if (row == null) {
			  				$.messager.alert('提示','请选择要删除的活动！','warning');
			  			} else {
			  				$.messager.confirm('确认','确定要删除该活动吗？',function(data) {
			  					if (data) {
					  				if (row.state != 'Running') {
					  					$.messager.alert('提示','该活动不能被删除，请确认！','error');
					  					return;
					  				}
			  						deleteActIns(row);
			  					}
			  				});
			  			}
			  		}
			  	},{
					text:'取消',
			  		iconCls: 'icon-cancel',
			  		handler: function(){
			  			var row = $("#actInsDT").datagrid('getSelected');
			  			if (row == null) {
			  				$.messager.alert('提示','请选择要取消的活动！','warning');
			  			} else {
			  				$.messager.confirm('确认','确定要取消该活动吗？',function(data) {
			  					if (data) {
			  						if (row.state != 'Running') {
					  					$.messager.alert('提示','该活动不能被取消，请确认！','error');
					  					return;
					  				}
			  						cancelActIns(row);
			  					}
			  				});
			  			}
			  		}
			  	},{
			  		text:'撤回',
			  		iconCls: 'icon-undo',
			  		handler: function(){
			  			var row = $("#actInsDT").datagrid('getSelected');
			  			if (row == null) {
			  				$.messager.alert('提示','请选择要撤回的活动！','warning');
			  			} else {
			  				$.messager.confirm('确认','确定要撤回该活动吗？',function(data) {
			  					if (data) {
			  						undoActIns(row);
			  					}
			  				});
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
		
		// 分页
		pagefunction('actInsDT');
		
		/*
		$("#actDataDT").datagrid({
			fit:true,
			border:false,
			striped:true,
			loadMsg:'数据处理中，请稍等 ...',
			singleSelect:true,
			nowrap:true,
			remoteSort:false,
			idField:'activityInsId',
			columns:[[
			          {field:'activityInsId',title:'活动实例ID',width:238,align:'center'},    
			          {field:'dataName',title:'数据名称',width:220,align:'center',sortable:true,sorter:otherSort},
			          {field:'dataValue',title:'数据值',width:310,align:'center',sortable:true,sorter:otherSort}
			      ]],
			toolbar:'#tb',
			onLoadSuccess:onLoad

		});
		*/
	})
</script>
</body> 
</html>