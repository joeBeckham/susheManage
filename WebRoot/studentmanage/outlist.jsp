<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/demo/demo.css">
	<!-- <link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.3/themes/bootstrap/easyui.css"> -->
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery-1.8.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>

	<script type="text/javascript">
		//查询
		function searchUser(){
	    	$("#dg").datagrid('load',{
	    		s_name:$("#s_name").val()
	    	});
	    }
	</script>
  </head>
  
<body style="margin:5px">
    <table id="dg" title="学生迁出记录" class="easyui-datagrid" style="width:900px;"
            url="StudentManageAction!list.action?s_state =3" 
            toolbar="#toolbar" pagination="true" pageList=[5,10,15]
            rownumbers="true" fitColumns="true" fit="true" >
        <thead>
            <tr>
            	<th field="out_date" width="80" align="center">日期</th>
            	<th field="student_userName" align="center" width="100">学号</th>
                <th field="student_name" align="center" width="50">姓名</th>
                <th field="student_sex" align="center" width="50">性别</th>
                <th field="student_class" align="center" width="80">班级</th>
                <th field="student_remark" align="center" width="200">备注</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
      <div>
      	   &nbsp;&nbsp;
      	   <input type="text" id="s_name" name="s_name" placeholder="请输入要查询的姓名">
           <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="btn" plain="true" onclick="searchUser()">搜索</a>
  	  </div>
    </div>
    
  </body>
</html>
