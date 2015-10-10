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
<link href="images/favicon.ico" rel="icon" type="image/x-icon" mce_href="images/favicon.ico" />
<script type="text/javascript" src="js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/locale/easyui-lang-zh_CN.js"></script>
<title>管理维护平台</title>
</head>
<body style="background:#F0F0F0;">

	<!-- top -->
	<div id="top">
		<img src="images/head_bg/head.png" alt="管理维护平台" />
	</div>
	
	<!-- center -->
	<div id="center">
		<!-- left -->
		<div id ="left">
			<div id="menu" class="easyui-panel" data-options="fit:true" style="background-color:#F0F0F0 ">
				<div class="ch_panel_title1"><span><img src="images/icons/flow_intervene.png" alt=" " style="margin: 8px 8px 0 20px;float:left"/></span>维护平台</div>
				<div class="ch_panel">
					<div class="ch_panel_title2" ><span><img src="images/icons/user_upcomming.png" alt=" " style="margin: 8px 2px 0 20px;float:left"/></span>已待办管理<em class="ch_panel_arrow_down"></em></div>
					<div class="ch_panel_body">
						<ul>
				  			<li><a id="upcomming" href="javascript:void(0)" onclick="setHref('upcomming','${pageContext.request.contextPath}/maintain/user_upcomming','维护平台>>已待办管理>>待办管理')"><span><img src="images/icons/icon_blueSJ.png" alt="" /></span>待办管理</a>
				  			</li>
				  			<li><a id="hastodo" href="javascript:void(0)" onclick="setHref('hastodo','${pageContext.request.contextPath}/maintain/user_hasToDo','维护平台>>已待办管理>>已办管理')"><span><img src="images/icons/icon_blueSJ.png" alt="" /></span>已办管理</a></li>
				  		</ul>
				  	</div>
				</div>
				<div class="ch_panel">
					<div class="ch_panel_title2"><span><img src="images/icons/flow_manage.png" alt=" " style="margin: 8px 2px 0 20px;float:left"/></span>流程管理<em class="ch_panel_arrow_up"></em></div>
					<div class="ch_panel_body" style="display: none">
						<ul>
							<li><a id="flowdefm" href="javascript:void(0)" onclick="setHref('flowdefm','${pageContext.request.contextPath}/maintain/flow_flowDefManage','维护平台>>流程管理>>流程定义管理')"><span><img src="images/icons/icon_blueSJ.png" alt="" /></span>流程定义管理</a></li>
				  			<li><a id="flowinsm" href="javascript:void(0)" onclick="setHref('flowinsm','${pageContext.request.contextPath}/maintain/flow_flowInsManage','维护平台>>流程管理>>流程实例管理')"><span><img src="images/icons/icon_blueSJ.png" alt="" /></span>流程实例管理</a></li>
				  		</ul>
				  	</div>
				</div>
				<div class="ch_panel">
					<div class="ch_panel_title2"><span><img src="images/icons/flow_manage.png" alt=" " style="margin: 8px 2px 0 20px;float:left"/></span>活动管理<em class="ch_panel_arrow_up"></em></div>
					<div class="ch_panel_body" style="display: none">
						<ul>
				  			<li><a id="activityinsm" href="javascript:void(0)" onclick="setHref('activityinsm','${pageContext.request.contextPath}/maintain/activity_activityInsManage','维护平台>>活动管理>>活动实例管理')"><span><img src="images/icons/icon_blueSJ.png" alt="" /></span>活动实例管理</a></li>
				  			
				  		</ul>
				  	</div>
				</div>
				<div class="ch_panel">
					<div class="ch_panel_title2"><span><img src="images/icons/exception_manage.png" alt=" " style="margin: 8px 2px 0 20px;float:left"/></span>异常管理<em class="ch_panel_arrow_up"></em></div>
					<div class="ch_panel_body" style="display: none">
						<ul>
				  			<li><a id="acte" href="javascript:void(0)" onclick="setHref('acte','${pageContext.request.contextPath}/maintain/exception_handlingException','维护平台>>异常管理>>未处理异常管理')"><span><img src="images/icons/icon_blueSJ.png" alt="" /></span>未处理异常管理</a></li>
				  			<li><a id="flowe" href="javascript:void(0)" onclick="setHref('flowe','${pageContext.request.contextPath}/maintain/exception_handledException','维护平台>>异常管理>>已处理异常管理')"><span><img src="images/icons/icon_blueSJ.png" alt="" /></span>已处理异常管理</a></li>
				  			<!-- 
				  			<li><a id="turne" href="javascript:void(0)" onclick="setHref('turne','${pageContext.request.contextPath}/maintain/exception_turnException')"><span><img src="images/icons/icon_blueSJ.png" alt="" /></span>转文异常管理</a></li>
				  			-->
				  		</ul>
				 	</div>
				</div>
					<!-- 
				<div class="ch_panel">
					<div class="ch_panel_title2"><span><img src="images/icons/other.png" alt=" " style="margin: 8px 2px 0 20px;float:left"/></span>其他<em class="ch_panel_arrow_up"></em></div>
					<div class="ch_panel_body">
						<ul>
				  			<li><a id="acttypem" href="javascript:void(0)" onclick="setHref('acttypem','${pageContext.request.contextPath}/maintain/other_actTypeM')"><span><img src="images/icons/icon_blueSJ.png" alt="" /></span>活动类型修改</a></li>
				  			<li><a id="monitorm" href="javascript:void(0)" onclick="setHref('monitorm','${pageContext.request.contextPath}/maintain/other_monitorM')"><span><img src="images/icons/icon_blueSJ.png" alt="" /></span>监控管理</a></li>
				  		</ul>
				  	</div>
				</div>
		
			<div id="menu" class="easyui-accordion" data-options="bordor:false">
				<div title="用户已待办管理">
				  <ul>
				  	<li><a id="upcomming" href="javascript:void(0)" onclick="setHref('upcomming','${pageContext.request.contextPath}/maintain/user_upcomming')"><span><img src="images/icons/icon_blueSJ.png" alt="" /></span>用户待办管理</a>
				  	</li>
				  	<li><a id="hastodo" href="javascript:void(0)" onclick="setHref('hastodo','${pageContext.request.contextPath}/maintain/user_hasToDo')"><span><img src="images/icons/icon_blueSJ.png" alt="" /></span>用户已办管理</a></li>
				  </ul>
				</div>
				<div title="流程管理"><ul>
				  	<li><a id="flowinsm" href="javascript:void(0)" onclick="setHref('flowinsm','${pageContext.request.contextPath}/maintain/flow_flowInsManage')"><span><img src="images/icons/icon_blueSJ.png" alt="" /></span>流程实例管理</a></li>
				  	<li><a id="flowdefm" href="javascript:void(0)" onclick="setHref('flowdefm','${pageContext.request.contextPath}/maintain/flow_flowDefManage')"><span><img src="images/icons/icon_blueSJ.png" alt="" /></span>流程定义管理</a></li>
				  </ul>
				</div>
				<div title="异常管理">
				  <ul>
				  	<li><a id="acte" href="javascript:void(0)" onclick="setHref('acte','${pageContext.request.contextPath}/maintain/exception_actException')"><span><img src="images/icons/icon_blueSJ.png" alt="" /></span>活动异常管理</a></li>
				  	<li><a id="flowe" href="javascript:void(0)" onclick="setHref('flowe','${pageContext.request.contextPath}/maintain/exception_flowException')"><span><img src="images/icons/icon_blueSJ.png" alt="" /></span>流程异常管理</a></li>
				  	<li><a id="turne" href="javascript:void(0)" onclick="setHref('turne','${pageContext.request.contextPath}/maintain/exception_turnException')"><span><img src="images/icons/icon_blueSJ.png" alt="" /></span>转文异常管理</a></li>
				  </ul>
				</div>
				<div title="其他">
				  <ul>
				  	<li><a id="acttypem" href="javascript:void(0)" onclick="setHref('acttypem','${pageContext.request.contextPath}/maintain/other_actTypeM')"><span><img src="images/icons/icon_blueSJ.png" alt="" /></span>活动类型修改</a></li>
				  	<li><a id="monitorm" href="javascript:void(0)" onclick="setHref('monitorm','${pageContext.request.contextPath}/maintain/other_monitorM')"><span><img src="images/icons/icon_blueSJ.png" alt="" /></span>监控管理</a></li>
				  </ul>
				</div>
			</div>
			 -->
			</div>
		</div>
		<!-- main -->
		<div id="main">
		<!--  
			 <div id ="mainPanel" class="easyui-panel" data-options="fit:true,border:true,"><div style="padding: 5px 0;text-align: center;">欢迎使用管理维护平台</div></div>
		  -->
		  <div id="main_title">
		 	 <div class="nowLink">
		  		<span class="nowLinkSpan"> 您当前所在的位置： </span>
				<span id="toppaths" class="nowLinkSpan">维护平台>>已待办管理>>待办管理</span>
			 </div>
		  </div>
		  <div id="main_content"><iframe id="mainPanel" scrolling="no" frameborder="0" src="${pageContext.request.contextPath}/maintain/user_upcomming'" style="background-color: #f0f0f0"></iframe></div>
		 </div>
	
	</div>
	<!-- bottom -->
	<div id="bottom">
		<div style="margin-top: 5px;text-align: center;font:12px '宋体';color: #FFF" >中移全通系统集成有限公司</div>
	</div>

<script type="text/javascript">
function setHref(selectid,toUrl,nowPath) {
	if ($('#current')) {
		$('#current img').attr('src','images/icons/icon_blueSJ.png');
		$('#current').removeAttr('id');
	}
	$('#' + selectid).parent().attr('id', 'current');
	$('#' + selectid+' img').attr('src','images/icons/baisanjiao.png');
	$('#toppaths').text(nowPath);
	$('#mainPanel').attr('src',toUrl);
}

	$(function(){
		$('#upcomming').parent().attr('id', 'current');
		$('#upcomming img').attr('src','images/icons/baisanjiao.png');
		
		
		var tabs_i=0;
		$('.ch_panel_title2').click(function() {
			var _self = $(this);
			var j = $('.ch_panel_title2').index(_self);
			if (tabs_i==j) return false;
			tabs_i=j;
			$('.ch_panel_title2 em').each(function(e){
				if(e==tabs_i){
					$('em',_self).removeClass('ch_panel_arrow_up').addClass('ch_panel_arrow_down');
				}else{
					$(this).removeClass('ch_panel_arrow_down').addClass('ch_panel_arrow_up');
				}
			});
			$('.ch_panel_body').slideUp().eq(tabs_i).slideDown();
		});
		
	})

</script>


</body> 
</html>