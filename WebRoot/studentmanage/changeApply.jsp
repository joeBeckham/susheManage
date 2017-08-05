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
    
    <title>调换宿舍申请</title>
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
			$("#dg").datagrid('hideColumn', 'student_userPass');
			
			/* $("#sendTeacherRole").click(function(){
				$("#sendStudent").combobox({ disabled: true }); 
				$("#sendManage").combobox({ disabled: false });
			});
			$("#sendStudentRole").click(function(){
				$("#sendManage").combobox({ disabled: true }); 
				$("#sendStudent").combobox({ disabled: false });
			}); */
			
			// 查询出全部的building列表
			$.post("teacherManageAction!queryAllTeacher.action?index="+Math.random(),null,callbackByBuilding);
			function callbackByBuilding(data){
				$("#teacherData").html(data);
				//$("#buildingData").html(data).find('select').combobox();
			}
			
		});
			
		//查询
		function searchUser(){
	    	$("#dg").datagrid('load',{
	    		s_name:$("#s_name").val(),
	    		s_state:$("#s_state").combobox('getValue')
	    	});
	    }
		
				
		//发送
		function sendMsg(){
			var title = $("#title").val();
			var content = $("#content").val();
			if( title ==null || title == ""){
				$.messager.alert("系统提示","请先填写标题！","warning");
				return ;
			}
			// 取出字符串前后的空格
			content = $.trim(content);
			if(content == null || content == ""){
				$.messager.alert("系统提示","请先填写消息内容！","warning");
				return;
			}
			// 提交数据			
			$("#fm").form("submit",{
				url:"StudentManageAction!saveMessgae.action",
				onSubmit:function(){
					return $(this).form('validate');
				},
				success:function(result){
					var result=eval("("+result+")");
					if(result.errorMsg){
						$.messager.alert("系统提示",result.errorMsg,"warning");
						return;
					}else{
						$.messager.alert("系统提示","发送成功！","info");
						$("#queryMsg").datagrid("reload");
						$("#title").val("");
						$("#content").val("");
					}
				}
			});
		}
		
	</script>
	
	<style type="text/css">
		.td{border:solid 1px #CCCCCC;}
	</style>
  </head>
  
<body style="margin:5px">

	<!-- 对<div>标签引用'easyui-layout'类,fit="true"自动适应父窗口-->
	<div id="mainWindow"  class="easyui-layout" fit="true" >  
	    <div region="north" title="申请登记" id="topWindow" split="true" style="padding:4px 4px;height:230px;background:#F4F4F4;" >
	    	<!-- 发送消息 -->
	        <form id="fm" method="post" >
	        	<table border="1" style="border:1px #95B8E7 solid" width="83%" cellspacing="0" cellpadding="3">
	        		<tr>
	        			<td style="width:80px" align="right" class="td">发送人：</td>
	        			<td class="td">
	        				<input name="send_man" id="send_man" value="${sessionScope.realName }" disabled="disabled"  class="easyui-validatebox" style="width: 400">
	        				<input type="hidden" name="notice.send_userName" id="send_userName" value="${sessionScope.userName }" class="easyui-validatebox" style="width: 400">
	        				<input type="hidden" name="notice.send_person" id="send_person" value="${sessionScope.realName }"  class="easyui-validatebox" style="width: 400">
							<input type="hidden" name="notice.send_role" id="send_role" value="${sessionScope.loginFlag }"/>
	        			</td>
	        			<td colspan="2" class="td">
	        				<input type="radio" checked="checked" class="easyui-validatebox" name="notice.rec_role" id="sendTeacherRole" value="teacher">
	        				发送给楼宇管理员
	        				&nbsp;
	        				<!-- <select id="sendManage" class="easyui-combobox" name="notice.rec_userName" style="width:100px">
	        					<option value="10_manage">10_manage</option>
	        					<option value="16_manage">16_manage</option>
	        				</select> -->
	        				<span id="teacherData">
							</span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" class="td">消息类型：</td>
	        			<td class="td">
	        				<select id="msg_type" name="notice.msg_type" class="easyui-combobox" style="width: 400">
	        					<option value="1">宿舍调换申请</option>
	        					<option value="2">迁出申请</option>
	        				</select>
	        			</td>
	        			<td class="td" colspan="2">
	        				<input type="radio" class="easyui-validatebox" name="must" id="reply_state" vaue="1">必须回复
	        				<input type="radio" class="easyui-validatebox" name="must" id="ok_state" vaue="1" checked="checked">必须确认
	        				<input type="radio" class="easyui-validatebox" name="must" id="see_state" vaue="1">必须查看
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" class="td">标题：</td>
	        			<td class="td">
	        				<input name="notice.title" id="title" style="width:400px"  class="easyui-validatebox" >
	        			</td>
	        			<td class="td" colspan="2" align="right">
	        				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="sendMsg()">发送</a>
	        				&nbsp;
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" class="td">消息内容：</td>
	        			<td colspan="3" class="td">
	        				<textarea rows="3" cols="103" name="notice.content" id="content" class="easyui-validatebox" >
	        				</textarea>
	        			</td>
	        		</tr>
	        	</table>
	        </form>
	    </div>  
	    <!-- 下面是 查询 -->
	    <div region="center" id="buttomWindow" title="申请记录" style="padding:5px;background:#eee;">
		    <table id="queryMsg" class="easyui-datagrid" style="width:900px;"
		            url="StudentManageAction!qryMessage.action?send_userName=${sessionScope.userName }&send_person=${sessionScope.realName}" 
		            toolbar="#toolbar" pagination="true" pageList=[5,10]
		            rownumbers="true" fitColumns="true" fit="true" >
		        <thead>
		            <tr>
		            	<th field="title" width="80" align="center">标题</th>
		            	<th field="content" width="120" align="center">内容</th>
		            	<th field="msg_type" width="77" align="center">消息类型</th>
		                <th field="send_userName" width="100" align="center">发送人学号/工号</th>
		                <th field="send_person" width="40" align="center">发送人</th>
		                <th field="send_time" width="110" align="center">发送时间</th>
		                <th field="rec_userName" width="100" align="center">接收人学号/工号</th>
		                <th field="rec_person" width="40" align="center">接收人</th>
		                <th field="see_state" width="50" align="center">是否查看</th>
		               <!--  <th field="ok_state" width="50" align="center">是否确认</th> -->
		            </tr>
		        </thead>
		    </table>
		    <div id="toolbar">
		      <div>
		  		<form id="condition" name="condition" action="StudentManageAction!export.action" method="post"> 
		      		&nbsp;&nbsp;
		      		查询：
			      	<select id="s_state" class="easyui-combobox" name="s_state" style="width:100px;">   
					    <option value="0">==全部状态==</option>   
					    <option value="1">入住</option>   
					    <option value="2">未入住</option> 
					    <option value="3">迁出</option>     
					</select>&nbsp;&nbsp;
		      		<input type="text" id="s_name" name="s_name" placeholder="请输入要查询的姓名">
		        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="btn" plain="true" onclick="searchUser()">搜索</a>
		        </form>
		  	  </div>
		    </div>
	    </div>  
	</div> 

  </body>
</html>
