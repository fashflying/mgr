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
	<table id="addZSCSTBL" cellpadding="0" cellspacing="0" style="width: 436px;height: 95px">
		<tbody>
			<tr>
				<th>主送</th>
				<td><input id="ZS" type="hidden"/><input id="ZSN" name="ZSName" type="text" style="width:310px;height: 20px;border: 1px solid #ccc;" readonly="readonly" onfocus="openZS()"/></td>
			</tr>
			<tr>
				<th>抄送</th>
				<td><input id="CS" type="hidden"/><input id="CSN" name="CSName" type="text" style="width:310px;height: 20px;border: 1px solid #ccc;" readonly="readonly" onfocus="openCS()"/></td>
			</tr>
		</tbody>
		</table>
    </div>
   <div style="text-align: center;margin-top: 10px;"><img class="buttons" src="images/button/botton_qd.png" alt="确定" onclick="addZSCS()"/></div>
 	
	<!-- 组织架构接口dialog -->
	<div id="orgDIG"></div>
<script type="text/javascript">
var orgDIG;
$(function() {
    var orgs = '';
	orgDIG =  $('#orgDIG').dialog({    
	    title: '组织架构',
	    href:'${pageContext.request.contextPath}/maintain/organization_organization',
	    width: 240,    
	    height: 360,    
	    closed: true,    
	    cache: false,    
	    modal: true
	}).dialog('close');
    
	function openZS() {
		orgs = $('#ZS').val();
		var retID = 'ZS';
		var retName = 'ZSN';
		orgDIG.dialog('open').dialog('refresh');
	}
	
	function openCS() {
		orgs = $('#CS').val();
		var retID = 'CS';
		var retName = 'CSN';
		orgDIG.dialog('open').dialog('refresh');
	}
	
    function addZSCS() {
    	var zs = $('#ZS').val() + ";" + $('#ZSN').val();
    	var cs = $('#CS').val() + ";" + $('#CSN').val();
    	$.post('flowIns/add_ZSCS',
				{
					'flowInsID':flowInsId,
					'ZS':zs,
					'CS':cs
				},
					function(data) {
						if (data.result == "Success") {
							$.messager.alert('提示','主送抄送增加成功！','info');
							ZSCSDig.dialog('close');
				  			$('#flowInsDT').datagrid('reload');
						} else {
							$.messager.alert('提示','送抄送增加失败！','error');
						}
					},'json');
	}

});
</script>

</body> 
</html>