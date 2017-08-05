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
	
		//查询
		function searchUser(){
	    	$("#dg").datagrid('load',{
	    		t_name:$("#t_name").val(),
	    		building_id:$("#dorm_building1").combobox('getValue')
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
		    	strIds.push(selectedRows[i].dorm_id);
		    }
		    var ids = strIds.join(",");
		    $.messager.confirm("系统提示","您确定要删掉这<font color='red'>"+selectedRows.length+"</font>条数据么?",function(r){
		    	if(r){
		    		$.post("dormManageAction!delete.action",{delIds:ids},function(result){
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
			$("#firtTr").show();
			$("#dlg").dialog("open").dialog("setTitle","添加宿舍");
			$("#fm").form("clear");
			
			// 查询出全部的building列表
			$.post("<%=basePath%>StudentManageAction!queryAllBuilding1.action?index="+Math.random(),null,callbackByBuilding);
			function callbackByBuilding(data){
				$("#buildingData").html(data).find('select').combobox();
			}
			
			url="dormManageAction!save.action";
		}
	
		//编辑
		function editUser(){
			$("#firtTr").hide();
			var selectedRows=$("#dg").datagrid("getSelections");
			if(selectedRows.length!=1){
	    		$.messager.alert("系统提示","请选择一条要修改的数据","warning");
	    		return;
	    	}
	    	var row=selectedRows[0];
	    	
	    	$("#dlg").dialog("open").dialog("setTitle","编辑宿舍");
	    	$("#dorm_name").val(row.dorm_name);
	    	$("#dorm_tel").val(row.dorm_tel);
	    	$("#dorm_type").val(row.dorm_type);
	    	url="dormManageAction!save.action?dorm_id="+row.dorm_id;
		}
		
		//保存
		function saveUser(){
			var dorm_building=$("#dorm_building").combobox('getValue');
			var building_name=$("#dorm_building").combobox('getText');
			if(url=="dormManageAction!save.action"){					//增加保存
				$("#fm").form("submit",{
					url:url+"?building_id="+dorm_building +"&building_name="+building_name,
					onSubmit:function(){
						return $(this).form('validate');
					},
					success:function(result){
						var result=eval("("+result+")");
						if(result.errorMsg){
							$.messager.alert("系统提示",result.errorMsg,"warning");
							return;
						}else{
							$.messager.alert("系统提示","您已经增加成功！");
							$("#dlg").dialog("close");
							$("#dg").datagrid("reload");
						}
					}
				});	
			}else{												//修改保存
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
							$.messager.alert("系统提示","您已经修改成功！");
							$("#dlg").dialog("close");
							$("#dg").datagrid("reload");
						}
					}
				});
			}
		}

		//用模版导出用户
		 function exportUser(){
			$('#condition').submit();
		}
		
		$(function() {
			// 查询出全部的building列表
			$.post("<%=basePath%>StudentManageAction!queryAllBuilding2.action?index="+Math.random(),null,callbackByBuilding1);
			function callbackByBuilding1(data){
				$("#buildingData1").html(data).find('select').combobox();
			}
			
			var loginFlag=$("#loginFlag").val();
			if("teacher"==loginFlag){
				$("#addUptDel").show();
			} else {
				$("#addUptDel").hide();
			}
			
		});

	</script>
  </head>
  
<body style="margin:5px">
	<input type="hidden" name="loginFlag" id="loginFlag" value="${sessionScope.loginFlag }"/>
  <table class=easyui-datagrid id="dg" title="宿舍基本信息"
       toolbar="#toolbar" pagination="true" pageList=[5,10,15] 
       	fit="true" rownumbers="true" fitColumns="true" 
    	url="dormManageAction!execute.action">
    <thead>
      <tr>
        <th field="dorm_id" checkbox="true" align="center">编号</th>
        <th field="dorm_building" width="50" align="center">楼宇</th>
        <th field="dorm_name" width="50" align="center">宿舍号</th>
        <th field="dorm_people_num" width="50" align="center">人数</th>
        <th field="dorm_tel" width="70" align="center">电话</th>
        <th field="dorm_type" width="50" align="center">类型</th>
  </table>
  <div id=toolbar>
	  <div id="addUptDel" style="float: left">
	    <a href="javascript:newUser()" class=easyui-linkbutton iconCls="icon-add" plain="true">添加宿舍</a>
	    <a href="javascript:editUser()" class=easyui-linkbutton iconCls="icon-edit" plain="true">修改宿舍</a>
	    <a href="javascript:deleteUser()" class=easyui-linkbutton iconCls="icon-remove" plain="true">删除宿舍</a>
	  </div>
	  <div>
	      &nbsp;&nbsp;
	          查询：
		 <span id="buildingData1">
			<select id="dorm_building1" name="dorm_building1" class="easyui-combobox" style="width:80px;">
				<option value="" selected="selected">==楼宇==</option>
			</select>
		 </span>&nbsp;&nbsp;
		 
	     <input type="text" id="t_name" name="t_name" placeholder="请输入要查询的宿舍号">
	     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="btn" plain="true" onclick="searchUser()">搜索</a>
  	  </div>
     </div>
     
  <div id="dlg" class="easyui-dialog" style="width:370px;height:260px;padding:10px 40px"
    closed="true" buttons="#dlg-buttons">
     <form id=fm method="post">
       <table>
       	<tr id="firtTr">
       		<td>选择楼宇：</td>
       		<td>
				<span id="buildingData">
					<select id="dorm_building" name="vo.dorm_building" required="true">
						<option value="" selected="selected">==全部==</option>
					</select>
				</span>
       		</td>
       	</tr>
        <tr>
            <td>宿舍号：</td>
            <td>
            	<input type="text" name="vo.dorm_name"  id="dorm_name" class=easyui-validatebox required="true">
          	</td>
        </tr>
        <tr>
          	<td>电话号：</td>
           	<td>
           		<input type="text" name="vo.dorm_tel"  id="dorm_tel" class=easyui-validatebox required="true">
          	</td>
        </tr>
        <tr>
        	<td>宿舍类型：</td>
           	<td>
           		<input type="text" name="vo.dorm_type"  id="dorm_type" class=easyui-validatebox required="true">
          	</td>
        </tr>
         <tr>
        	<td colspan=2>&nbsp;</td>
        </tr>
        <tr>
        	<td colspan=2>&nbsp;<font color="red">注：宿舍类型格式为：X人间(X为汉字)</font></td>
        </tr>
       </table>
     </form>
  </div>
  <div id="dlg-buttons">
		<a href="javascript:saveUser()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onclick="javascript:$('#dlg').dialog('close')">关闭</a>
	</div>
</body>
</html>
