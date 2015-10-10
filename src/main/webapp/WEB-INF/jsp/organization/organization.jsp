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
<div><ul id="organization"></ul></div>  
<div id="queding" style="text-align: center;margin-bottom: 10px;"><img class="buttons" src="images/button/botton_qd.png" alt="确定" onclick="getChecked()"/></div>	
<script type="text/javascript">
function onDblClick(node) {
	if (node.attributes.type == "department") {
		$('#organization').tree('toggle', node.target);
	} else if (node.attributes.type == "staff") {
		$('#addParticipant').val(node.id);
		$('#addParticipantN').val(node.text);
		orgDIG.dialog('close');
	}
}

function getChecked() {
	var nodes = $('#organization').tree('getChecked');
	var id = '';
	var name = '';
	for(var i=0; i<nodes.length; i++){
		if (id != '') id += ',';
		id += nodes[i].id;
		if (name != '') name += ',';
		name += nodes[i].text;
	}
	$('#'+retID).val(id);
	$('#'+retName).val(name);
	orgDIG.dialog('close');
}

$(function() {
	if (orgs == undefined) {
		$('#organization').tree({
			url:'activityIns/get_Organizations',
			animate:true,
			lines:true,
			method:'get',
			onDblClick:onDblClick
		})
		
		$('#queding').css('display','none');
	} else {
		$('#organization').tree({
			url:'flowIns/get_Organizations?orgs=' + orgs,
			animate:true,
			lines:true,
			method:'get',
			checkbox:true,
			cascadeCheck:false
		})
	}
});

		
</script>

</body> 
</html>