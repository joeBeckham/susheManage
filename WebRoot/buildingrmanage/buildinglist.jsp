<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
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
		    	strIds.push(selectedRows[i].building_id);
		    }
		    var ids = strIds.join(",");
		    $.messager.confirm("系统提示","您确定要删掉这<font color='red'>"+selectedRows.length+"</font>条数据么?",function(r){
		    	if(r){
		    		$.post("buildingManageAction!delete.action",{delIds:ids},function(result){
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
	
		//添加
		function newUser(){
			$("#dlg").dialog("open").dialog("setTitle","添加楼宇");
			$("#fm").form("clear");
			url="buildingManageAction!save.action";
		}
	
		//编辑
		function editUser(){
			var selectedRows=$("#dg").datagrid("getSelections");
			if(selectedRows.length!=1){
	    		$.messager.alert("系统提示","请选择一条要修改的数据！","warning");
	    		return;
	    	}
	    	var row=selectedRows[0];
	    	
	    	$("#dlg").dialog("open").dialog("setTitle","编辑楼宇");
	    	$("#building_name").val(row.building_name);
	    	$("#building_remark").val(row.building_remark);
	    	url="buildingManageAction!save.action?building_id="+row.building_id;
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

		
		
		//对应的楼宇的管理员
		 function buildingManager(){
			 var selectedRows=$("#dg").datagrid("getSelections");
		     var row=selectedRows[0];
		     $('#dg1').datagrid({   
		         url: 'buildingManageAction!queryManage.action?building_id='+row.building_id+"&index="+Math.random(),
		         onLoadSuccess:function(data){
					$.get("<%=basePath%>buildingManageAction!getRowIdOfSession.action?building_id="+row.building_id,callback,"json");
					function callback(data){
						$.each(data, function(i){
							$('#dg1').datagrid("selectRow",parseInt(data[i]));
						});
					}
		     	 }
		     });  
		}

		
		//设置楼宇管理员窗口
		function openPwd() {
			$('#w').window( {
				title : '楼宇管理员',
				width : 420,
				modal : true,
				shadow : true,
				closed : true,
				height : 300,
				resizable : false
			});
		}
		//关闭楼宇管理员窗口
		function closePwd() {
			$('#w').window('close');
		}

		$(function() {
	         openPwd();
	         $('#manager').click(function() {
	        	 var selectedRows=$("#dg").datagrid("getSelections");
				 if(selectedRows.length!=1){
			    		$.messager.show({
			    			title:"系统提示",
			    			msg:"请选择一条数据",
			    			timeout:3000
				    	});
			    		closePwd();
			    		return;
			     }

				$('#btnCancel').click(function(){closePwd();});
				 
				//增加或者减少楼宇管理员
				$("#btnEp").click(function(){
					var selectedRows=$("#dg1").datagrid("getSelections");
					if(selectedRows.length==0){
			    		$.messager.alert("系统提示","请选择要修改的楼宇管理员！","warning");
			    		return;
			    	}			
					var strIds=[];
				    for(var i=0;i<selectedRows.length;i++){
				    	strIds.push(selectedRows[i].teacher_id);
				    }
				    var ids = strIds.join(",");
					$("#fm1").form("submit",{
						url:"buildingManageAction!updateManager.action?teacherId_box="+ids,
						onSubmit:function(){
							return $(this).form('validate');
						},
						success:function(result){
							var result=eval("("+result+")");
							if(result.errorMsg){
								$.messager.alert("系统提示",result.errorMsg,"warning");
								return;
							}else{
								$.messager.alert("系统提示","修改楼宇管理员操作成功！","info");
								//$("#dg1").datagrid("reload");
								$("#w").dialog("close");
							}
						}
					});
				});
				 
	             $('#w').window('open');
	         });
		});

		
</script>
  </head>
  
<body style="margin:5px">
	
    <table id="dg" title="楼宇基本信息" class="easyui-datagrid"
            url="buildingManageAction!execute.action" 
            toolbar="#toolbar" pagination="true" pageList=[5,10,15]
            rownumbers="true" fitColumns="true"  fit="true" >
        <thead>
            <tr>
            	<th field="building_id" id="building_id" checkbox="true" width="50" align="center">id</th>
            	<th field="building_name" width="30px" align="center">楼宇名</th>
                <th field="building_remark" width="30px" align="center">备注</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
      <div  style="float: left">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">添加楼宇</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑楼宇</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteUser()">删除楼宇</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-manager" id="manager" plain="true" onclick="buildingManager()"> &nbsp;&nbsp;管理员</a>
      </div>
      <div>
      	&nbsp;&nbsp;<input type="text" id="t_name" name="t_name" placeholder="请输入要查询的楼宇名">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="btn" plain="true" onclick="searchUser()">搜索</a>
  	  </div>
    </div>
    
    <!-- 增加和修改div -->
    <div id="dlg" class="easyui-dialog" style="width:420px;height:280px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post">
        	<table cellspacing="10px;">
        		<tr>
        			<td>楼宇名：</td>
        			<td><input name="vo.building_name" id="building_name" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>备&nbsp;注：</td>
        			<td><input name="vo.building_remark" id="building_remark" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        	</table>
        </form>
    </div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">关闭</a>
	</div>
	
	
	<!-- 对应的管理员div -->
    <div id="w" class="easyui-window"  title="楼宇管理员" collapsible="false" minimizable="false"
        maximizable="false" icon="icon-save"  style="width: 300px; height: 180px; padding: 5px;
        background: #fafafa;">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 5px; height: 300px; background: #fff; border: 1px solid #ccc;">
                <form id="fm1" method="post">
		        	<table id="dg1" class="easyui-datagrid" style="height: 180px;">
				        <thead>
				            <tr>
				            	<th field="teacher_id" width="50" checkbox="true" align="center">id</th>
				            	<th field="teacher_userName" width="100" align="center">用户名</th>
				                <th field="teacher_name" width="95" align="center">姓名</th>
				                <th field="teacher_sex" width="40" align="center">性别</th>  
				                <th field="teacher_tel" width="100" align="center">电话</th>
				            </tr>
				        </thead>
				    </table>
		        </form>
		        <div><font color="red">注：多选框选中表示为当前楼宇的管理员</font></div>
            </div>
            
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >确定</a> 
                <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
            </div>
        </div>
    </div>
	
  </body>
</html>
