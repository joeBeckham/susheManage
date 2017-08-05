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
	
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/demo/demo.css">
	<!-- <link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/themes/bootstrap/easyui.css"> -->
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery-1.8.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>

	<link rel="stylesheet" href="css/web.css">
	<style type="text/css">
		#mainWrapper {width:400px;padding-top:100px;}
	</style>
	
  </head>
  
<body>
	<center> <!-- 加上这个center ，目的是 保证在 IE浏览器中 居中 -->
	<div id="mainWrapper" >
		<img src="image/logo.png" /> 
		<font style = "font-size: 30px;padding-bottom:20px;">&nbsp;&nbsp;&nbsp;&nbsp;
			<b>校园宿舍管理系统</b>
		</font>
		<div class="easyui-panel" title="请登录" icon="icon-forward" collapsible="false" align="center" style="padding:15px 50px;height:180px; ">
	        <form id="form" method="post" action="systemManageAction!denglu.action">
	            <table>
	            	<tr>
	            		<td>&nbsp;请选择：</td>
	            		<td>
	            			<select name="flag" id="flag" class="easyui-combobox"  style="width:169px;">
	            				<option value="">---全部---</option>
	            				<option value="system">系统管理员</option>
	            				<option value="teacher">楼宇管理员</option>
	            				<option value="student">学生</option>
	            			</select>
	            		</td>
	            	</tr>
	            	<tr>
	            		<td>用户名称：</td>
	            		<td>
	            			<input type="text" id="userName" name="userName"  class="easyui-validatebox" required="true" />
	            		</td>
	            	</tr>
	            	<tr>
	            		<td>用户密码：</td>
	            		<td>
	            			<input type="password" id="userPass" name="userPass"  class="easyui-validatebox" required="true" />
	            		</td>
	            	</tr>
	            	<tr>
	            		<td colspan="2" align="center">
	            			<input type="submit" value="登录"/> &nbsp;&nbsp;
	            			<input type="reset" value="重置"/>
	            		</td>
	            	</tr>
	            	<tr>
	            		<td colspan="2" align="center">
	            			<s:fielderror fieldName="loginFlag" cssStyle="color:red"></s:fielderror>
	            		</td>
	            	</tr>
	            </table>
	        </form>
	    </div>	    
	</div>
	</center>
</body>
</html>
