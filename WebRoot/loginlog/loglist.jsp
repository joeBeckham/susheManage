<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" content="ie=edge"/>
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
		var url;
		
		//查询
		function searchUser(){
	    	$("#dg").datagrid('load',{
	    		t_name:$("#t_name").val()
	    	});
	    }
		
		//点击删除按钮
		function deleteUser(){
			var selectedRows=$("#dg").datagrid("getSelections");
			if(selectedRows.length==0){
	    		$.messager.alert("系统提示","请选择要删除的数据!","warning");
	    		return;
	    	}			
			var strIds=[];
		    for(var i=0;i<selectedRows.length;i++){
		    	strIds.push(selectedRows[i].id);
		    }
		    var ids = strIds.join(",");
		    $.messager.confirm("系统提示","您确定要删掉这<font color='red'>"+selectedRows.length+"</font>条数据么?",function(r){
		    	if(r){
		    		$.post("logManageAction!delete.action",{delIds:ids},function(result){
		    			if(result.success){
		    				$.messager.alert("系统提示",'您已成功删除<font color=red>'+result.delNums+"</font>条数据!","info");
		    				$("#dg").datagrid("reload");
		    			}else{
		    				$.messager.alert("系统提示",result.errorMsg,"warning");
		    			}
		    		},"json"); 
		    	}
		    });
		}
	
		//编辑
		function editUser(){
			var selectedRows=$("#dg").datagrid("getSelections");
			if(selectedRows.length!=1){
	    		$.messager.alert("系统提示","请选择一条要修改的数据","warning");
	    		return;
	    	}
	    	var row=selectedRows[0];
	    	
	    	$("#dlg").dialog("open").dialog("setTitle","编辑楼宇管理员");
	    	$("#userName").val(row.username);
	    	$("#loginDate").val(row.logindate);
	    	$("#loginIP").val(row.loginip);
	    	$("#iPaddress").val(row.ipaddress);
	    	url="logManageAction!save.action?id="+row.id;
		}
		
		//保存
		function saveUser(){
			$("#fm").form("submit",{
				url:url,
				onSubmit:function(){
					return $(this).form('validate');
				},
				success:function(result){
					var result=eval("("+result+")");
					if(result.errorMsg){
						$.messager.alert("系统提示",result.errorMsg,"warning");
						return;
					}else{
						$.messager.alert("系统提示","您已经保存成功！","info");
						$("#dlg").dialog("close");
						$("#dg").datagrid("reload");
					}
				}
			});
		}

		//用模版导出报表
		 function exportUser(){
			$('#condition').submit();
		}
	</script>
  </head>
  
<body style="margin:5px">
    <table id="dg" title="登录日志信息" class="easyui-datagrid" style="width:900px;height:400px"
            url="logManageAction!execute.action" 
            toolbar="#toolbar" pagination="true" pageList=[5,10,15]
            rownumbers="true" fitColumns="true"  fit="true" >
        <thead>
            <tr>
            	<th field="id" checkbox="true" align="center">id</th>
            	<th field="userid" width="50" align="center">用户Id</th>
            	<th field="username" width="50" align="center">用户名</th>
                <th field="logindate" width="50" align="center">登录时间</th>
                <th field="loginip" width="50" align="center">IP地址</th>
                <th field="ipaddress" width="50" align="center">IP所属地</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
      <div  style="float: left">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑用户</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteUser()">删除用户</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="exportUser()">导出报表</a>
      </div>
      <div>
  		<form id="condition" name="condition" action="logManageAction!export.action" method="post"> 
      		&nbsp;&nbsp;<input type="text" id="t_name" name="t_name" placeholder="请输入要查询的用户名">
        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="btn" plain="true" onclick="searchUser()">搜索</a>
        </form>
  	  </div>
    </div>
    
    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post">
        	<table cellspacing="10px;">
        		<tr>
        			<td>用户名：</td>
        			<td><input name="vo.userName" id="userName" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>登录时间：</td>
        			<td><input name="vo.loginDate" id="loginDate" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>IP地址：</td>
        			<td><input name="vo.loginIP" id="loginIP" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>IP所属地：</td>
        			<td><input name="vo.iPaddress" id="iPaddress" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        	</table>
        </form>
    </div>
    
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">关闭</a>
	</div>
  </body>
</html>
