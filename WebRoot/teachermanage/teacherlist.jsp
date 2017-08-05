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
		var url;

		//隐藏密码这一列
		$(function(){
			$("#dg").datagrid('hideColumn', 'teacher_userPass');
		});
		
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
	    		$.messager.alert("系统提示","请选择要删除的数据!");
	    		return;
	    	}			
			var strIds=[];
		    for(var i=0;i<selectedRows.length;i++){
		    	strIds.push(selectedRows[i].teacher_id);
		    }
		    var ids = strIds.join(",");
		    $.messager.confirm("系统提示","您确定要删掉这<font color='red'>"+selectedRows.length+"</font>条数据么?",function(r){
		    	if(r){
		    		$.post("teacherManageAction!delete.action",{delIds:ids},function(result){
		    			if(result.success){
		    				$.messager.alert("系统提示",'您已成功删除<font color=red>'+result.delNums+"</font>条数据!");
		    				$("#dg").datagrid("reload");
		    			}else{
		    				$.messager.alert("系统提示",result.errorMsg);
		    			}
		    		},"json"); 
		    	}
		    });
		}
	
		//添加
		function newUser(){
			$("#dlg").dialog("open").dialog("setTitle","添加楼宇管理员");
			$("#fm").form("clear");
			
			// 设置单选按钮默认选中
			$('[name="vo.teacher_sex"]:radio').each(function() {   
                if (this.value == '男'){   
                   this.checked = true;   
                }       
            }); 
			
			url="teacherManageAction!save.action";
		}
	
		//编辑
		function editUser(){
			var selectedRows=$("#dg").datagrid("getSelections");
			if(selectedRows.length!=1){
	    		$.messager.alert("系统提示","请选择一条要修改的数据");
	    		return;
	    	}
	    	var row=selectedRows[0];
	    	
	    	$("#dlg").dialog("open").dialog("setTitle","编辑楼宇管理员");
	    	$("#teacher_userName").val(row.teacher_userName);
	    	/* $("#teacher_userPass").val(row.teacher_userPass); */
	    	$("#teacher_name").val(row.teacher_name);
	    	
	    	// 赋值给界面radio控件
	    	if(row.teacher_sex == "男"){
	    		$('input:radio[name="vo.teacher_sex"][value="男"]').prop('checked', true);
	    	}else if(row.teacher_sex == "女"){
	    		$('input:radio[name="vo.teacher_sex"][value="女"]').prop('checked', true);
	    	}
	    	
	    	$("#teacher_tel").val(row.teacher_tel);
	    	url="teacherManageAction!save.action?teacher_id="+row.teacher_id;
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
						$.messager.alert("系统提示",result.errorMsg);
						return;
					}else{
						$.messager.alert("系统提示","您已经保存成功！");
						$("#dlg").dialog("close");
						$("#dg").datagrid("reload");
					}
				}
			});
		}

		//用模版导出用户
		 function exportUser(){
			$('#condition').submit();
		}
	</script>
  </head>
  
<body style="margin:5px">
    <table id="dg" title="楼宇管理员基本信息" class="easyui-datagrid" style="width:900px;height:400px"
            url="teacherManageAction!execute.action" 
            toolbar="#toolbar" pagination="true" pageList=[5,10,15]
            rownumbers="true" fitColumns="true"  fit="true" >
        <thead>
            <tr>
            	<th field="teacher_id" checkbox="true">id</th>
            	<th field="teacher_userName" width="50">用户名</th>
            	<th field="teacher_userPass" width="50">密码</th>
                <th field="teacher_name" width="50">姓名</th>
                <th field="teacher_sex" width="50">性别</th>
                <th field="teacher_tel" width="50">电话</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
      <div  style="float: left">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">添加用户</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑用户</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteUser()">删除用户</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="exportUser()">导出用户</a>
      </div>
      <div>
  		<form id="condition" name="condition" action="teacherManageAction!export.action" method="post"> 
      		&nbsp;&nbsp;<input type="text" id="t_name" name="t_name" placeholder="请输入要查询的姓名">
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
        			<td><input name="vo.teacher_userName" id="teacher_userName" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<!-- <tr>
        			<td>密码：</td>
        			<td><input name="vo.teacher_userPass" id="teacher_userPass" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr> -->
        		<tr>
        			<td>姓名：</td>
        			<td><input name="vo.teacher_name" id="teacher_name" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>性别：</td>
        			<td>
        				男：<input name="vo.teacher_sex" id="teacher_sex" type="radio" class="easyui-validatebox" checked="checked" required="true" value="男">
        				女：<input name="vo.teacher_sex" id="teacher_sex" type="radio" class="easyui-validatebox" required="true" value="女">
        			</td>
        		</tr>
        		
        		<tr>
        			<td>电话：</td>
        			<td><input name="vo.teacher_tel" id="teacher_tel" class="easyui-validatebox" required="true" style="width: 200px;"></td>
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
