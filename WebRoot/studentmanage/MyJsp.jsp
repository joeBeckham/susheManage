<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/demo/demo.css">
	<!-- <link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.3/themes/bootstrap/easyui.css"> -->
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery-1.8.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>

  </head>
  
  <!--1. 在整个页面创建布局面板-->
<body class="easyui-layout">

	<!--1.1 egion="north"，指明高度，可以自适应-->
	<div region="north" style="height:80px;"><center> <h1>管理系统</h1></center></div>
	
	<!--1.2 region="west",必须指明宽度-->
	<div region="west"  title="导航菜单" split="true" style="width:220px" >页面左侧</div>
	
	<!--1.3region="center",这里的宽度和高度都是由周边决定，不用设置-->
	<div region="center">
	
	<!--2. 对<div>标签引用'easyui-layout'类,fit="true"自动适应父窗口,这里我们指定了宽度和高度-->
	<div id="cc"  class="easyui-layout" style="width:600px;height:400px;">  
	    <div region="north" title="North Title" split="true" style="height:100px;">
	    	12212121<br>
	    	12212121<br>
	    	12212121<br>
	    </div>  
	    <div region="south" title="South Title" split="true" style="height:100px;"></div>  
	    <div region="east" iconCls="icon-reload" title="East" split="true" style="width:100px;"></div>  
	    <div region="west" split="true" title="West" style="width:100px;"></div>  
	    <div region="center" title="center title" style="padding:5px;background:#eee;"></div>  
	</div>  
	
	</div>
	
	<!--1.4 region="east",必须指明宽度-->
	<div region="east"  style="width:100px;">页面右侧</div>
	
	<!--1.5 region="south"，指明高度，可以自适应-->
	<div region="south" style="height:50px;"><center> <h3>页面底部</h3></center></div>
</body>
</html>