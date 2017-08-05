<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>校园宿舍管理系统</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="css/login.css">
	
  </head>
  
  <body>
    <div id="header">
		<div class="header_title">
			<span class="title_con">校园宿舍管理系统</span>
		</div>
	</div>
	
	<div id="content">
		<center>
			<div class="con">
			<div class="con_title">
				<span class="con_title_sp">欢迎登录校园宿舍管理系统</span>
			</div>
			<div class="con_border_bottom"></div>
			<div class="con_panel">
				<form id="form" method="post" action="systemManageAction!denglu.action">
					<div class="con_input">
						<span>用户名：</span>
						<input type="text" id="userName" name="userName" placeholder="学号/工号"/>
					</div>
					<div class="con_input">
						<span>密&nbsp;&nbsp;&nbsp;&nbsp;码：</span>
						<input type="password" id="userPass" name="userPass" placeholder="密码"/>
					</div>
					<div class="con_select">
						<input type="radio" name="flag" id="flag" value="student" checked="checked"/>学生
						<input type="radio" name="flag" id="flag" value="teacher" />楼宇管理员
						<input type="radio" name="flag" id="flag" value="system" />系统管理员
					</div>
					<input type="submit" value="登    录" class="submit-btn" style="cursor:hand;"/>
					<div>
						<s:fielderror fieldName="loginFlag" cssStyle="color:red" />
					</div>
				</form>
			</div>
		</div>
		</center>
	</div>
	
	<div id="footer">
		<center>
			© 版权所有：<a href="http://www.hnist.cn/" target="_blank">湖南理工学院</a>&nbsp; 徐邦启  &nbsp; QQ:734431825
		</center>
	</div>
  </body>
</html>
