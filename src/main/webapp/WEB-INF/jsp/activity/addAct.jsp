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
<body>   

<div class="easyui-layout" style="width:586px;height:425px;">
	<div data-options="region:'north'" style="height:42px;border-top-width: 0;border-left-width: 0;border-right-width: 0">
		<form id="addParticipantDF">
				<table id="addParticipantDFT" >
		<tbody>
			<tr>
				<td><label>参与者</label>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td><input id="addParticipant" type="hidden"/><input id="addParticipantN" name="participantName" type="text" style="width:200px;height: 20px;border: 1px solid #ccc;" readonly="readonly" onfocus="orgDIG.dialog('open').dialog('refresh');"/>
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;<label>文号</label>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td><input id="addDocNumber" name="docNumber" type="text" style="width:200px;height: 20px;border: 1px solid #ccc;"/>
				</td>
			</tr>
		</tbody>
		</table>
		</form>
	</div>
	<div data-options="region:'center',title:'活动类型选择',border:false" style="border-bottom-width: 1px">
		<table id="selectActDef"></table>
	</div>
    <div data-options="region:'south',title:'活动数据编辑',collapsible:false,border:false" style="height:231px;border-bottom-width: 1px">
    	<table id="flowDatas"></table>
    	<div id="tbfd">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addDatas()">新增</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeDatas()">删除</a>
		</div>
    </div>
    </div>
   <div style="text-align: center;margin-top: 10px;"><img class="buttons" src="images/button/botton_bc.png" alt="保存" onclick="addActIns()"/></div>
 	
	<!-- 组织架构接口dialog -->
	<div id="orgDIG"></div>
<script type="text/javascript">
var flowDatas;
var orgDIG;
$(function() {

	orgDIG =  $('#orgDIG').dialog({    
	    title: '组织架构',
	    href:'${pageContext.request.contextPath}/maintain/organization_organization',
	    width: 240,    
	    height: 360,    
	    closed: true,    
	    cache: false,    
	    modal: true
	}).dialog('close');

	$("#selectActDef").datagrid({
		data:addActDef,
		fit:true,
		border:false,
		striped:true,
		loadMsg:'数据处理中，请稍等 ...',
		singleSelect:true,
		nowrap:true,
		remoteSort:false,
		idField:'id',
		columns:[[
		          {field:'id',title:'活动ID',width:268,align:'center'},
		          {field:'name',title:'活动名称',width:168,align:'center'},
		          {field:'type',title:'活动类型',width:148,align:'center'}
		      ]]

	});
	
	$("#flowDatas").datagrid({
		url:'activityIns/get_FlowDatas?activityInsID='+parentActInsId,
		fit:true,
		border:false,
		striped:true,
		loadMsg:'数据处理中，请稍等 ...',
		singleSelect:true,
		nowrap:true,
		remoteSort:false,
		idField:'dataName',
		onClickCell:onClickCell,
		columns:[[
		          {field:'dataName',title:'数据名称',width:200,align:'center',
		        	  editor:{
              			type:'validatebox',
            			options:{required:true}
		          },sortable:true,sorter:otherSort},
		          {field:'dataValue',title:'数据值',width:386,align:'center',editor:'text',sortable:true,sorter:otherSort}
		      ]],
		toolbar:'#tbfd'

	});
})

// 编辑单元格
	$.extend($.fn.datagrid.methods, {
			editCell: function(jq,param){
				return jq.each(function(){
					var opts = $(this).datagrid('options');
					var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
					for(var i=0; i<fields.length; i++){
						var col = $(this).datagrid('getColumnOption', fields[i]);
						col.editor1 = col.editor;
						if (fields[i] != param.field){
							col.editor = null;
						}
					}
					$(this).datagrid('beginEdit', param.index);
					for(var i=0; i<fields.length; i++){
						var col = $(this).datagrid('getColumnOption', fields[i]);
						col.editor = col.editor1;
					}
				});
			}
		});
		
		var editIndex = undefined;
		// 结束编辑
		function endEditing(){
			if (editIndex == undefined){return true}
			if ($('#flowDatas').datagrid('validateRow', editIndex)
					&& duplicateCheck()){
				$('#flowDatas').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		
		function onClickCell(index, field){
			if (endEditing()){
				$('#flowDatas').datagrid('selectRow', index)
						.datagrid('editCell', {index:index,field:field});
				editIndex = index;
			}
		}
		
		//新增活动数据
		function addDatas() {
			if (endEditing()) {
				$('#flowDatas').datagrid('appendRow',{});
				var index = $('#flowDatas').datagrid('getRows').length - 1;
				$('#flowDatas').datagrid('selectRow', index)
						.datagrid('editCell', {index:index,field:'dataName'});
				editIndex = index;
			}
		}
		
		//删除数据
		function removeDatas() {
			if (editIndex == undefined) {
				$.messager.alert('提示','请选择要删除的数据！','info');
			} else {
				$.messager.confirm('确认','确定要删除该条活动数据吗？',function(data) {
					if (data) {
						$('#flowDatas').datagrid('deleteRow', editIndex);
						editIndex = undefined;
					}
				});
			}
		}
		
		// dataName重复check
		function duplicateCheck() {
			
			if (editIndex == undefined){return true};
			$('#flowDatas').datagrid('endEdit', editIndex);
			var rows = $('#flowDatas').datagrid('getRows');
			$('#flowDatas').datagrid('selectRow', editIndex)
			.datagrid('editCell', {index:editIndex,field:'dataName'});
			for (var i = 0; i < rows.length; i++) {
				if (i != editIndex && rows[i].dataName == rows[editIndex].dataName) {
					
					$.messager.alert('提示','数据名重复，请确认！','info');
					return false;
				}
			}
			return true;
		}
		
		// 保存活动
		function addActIns() {
			var participant = $('#addParticipant').val();
			var addActDef = $('#selectActDef').datagrid('getSelected');
			if (participant == null || "" == participant) {
				$.messager.alert('提示','请选择活动的参与者！','error');
				return false;
			}
			if (addActDef == null) {
				$.messager.alert('提示','请选择要创建的活动！','error');
				return false;
			}
			if (endEditing()) {
				var docNumber = $('#addDocNumber').val();
				var datas = $('#flowDatas').datagrid('getRows');
				var datasJSON = JSON.stringify(datas);
				var actDef = JSON.stringify(addActDef);
				$.post('activityIns/add_ActivityIns',
						{
							'activityInsID':parentActInsId,
							'participant':participant,
							'docNumber':docNumber,
							'activityInsDatas':datasJSON,
							'activityDefine':actDef,
							'flowDefine':flowDef
						},
							function(data) {
								if (data.result == "Success") {
									$.messager.alert('提示','活动创建成功！','info');
									addActInsDig.dialog('close');
						  			$('#actInsDT').datagrid('reload');
								} else {
									$.messager.alert('提示','活动创建失败！','error');
								}
							},'json');
			}
		}
		
</script>

</body> 
</html>