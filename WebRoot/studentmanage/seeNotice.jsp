<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>查看消息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/demo/demo.css">
	<!-- <link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.3/themes/bootstrap/easyui.css"> -->
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery-1.8.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	
	<script type="text/javascript">
		var url;
		
		$(function(){
			// 隐藏此列
			$("#dg").datagrid('hideColumn', 'guid');
			$("#dg").datagrid('hideColumn', 'content');
		});
	
		//查询
		function searchUser(){
	    	$("#dg").datagrid('load',{
	    		see_state:$("#see_state").combobox('getValue'),
	    		send_person:$("#send_person").val()
	    	});
	    }
		
		//-------------------------------调换寝室-----------------------------------
		/*查询出List，若List不为空，则可以进行寝室调换操作，否则弹出  ：该学生还没有入住，不能进行寝室调换操作*/
		function toChangeDorm(row){
	    	$.post("StudentManageAction!qryStudentChangeDorm.action?student_userName="+row.send_userName+"&guid="+row.guid,null,function(data){
	    		var resultData = eval('('+data+')');
	    		$("#student_id_changeDorm").val(resultData[0].student_id);
	    		$("#dorm_id_changeDorm").val(resultData[0].dorm_id);
	    		$("#student_userName_changeDorm").val(resultData[0].student_userName);
	    		$("#student_name_changeDorm").val(resultData[0].student_name);
	    		$("#student_sex_changeDorm").val(resultData[0].student_sex);
	    		$("#building_name_changeDorm").val(resultData[0].building_name);
	    		$("#dorm_name_changeDorm").val(resultData[0].dorm_name);
	    	});
	    	
	    	// 查询出全部的building列表
			$.post("<%=basePath%>StudentManageAction!queryAllBuilding_changeDorm.action?index="+Math.random(),null,function(data){
				$("#buildingData").html(data);
				//$("#buildingData").html(data).find('select').combobox();
			});
	    	
	    	$("#dlg_changeDorm").dialog("open").dialog("setTitle","消息处理");
		}
		
		//根据buildingID查询出对应的Dorm列表
		function changeBuilding_changeDorm(id){
			var building_id=$("#building_id").val();
			if(building_id!=""){
				$.post("<%=basePath%>StudentManageAction!queryDormByBuilding_changeDorm.action",
						"building_id="+building_id+"&index="+Math.random(),callbackByDorm_changeDorm);
			}
		}
		function callbackByDorm_changeDorm(data){
			$("#dormData").html(data).find('select').combobox();
		}
		
		// 保存 调换后的 寝室
		function saveChange(){
			var student_name = $("#student_name_changeDorm").val();
			$("#fm_changeDorm").form("submit",{
				url:"StudentManageAction!change.action?student_name="+student_name,
				onSubmit:function(){
					return $(this).form('validate');
				},
				success:function(result){
					var result=eval("("+result+")");
					if(result.errorMsg){
						$.messager.alert("系统提示",result.errorMsg,"warning");
						return;
					}else{
						$.messager.alert("系统提示","操作成功，该生寝室已调换！","info");
						$("#dlg_changeDorm").dialog("close");
						$("#dg").datagrid("reload");
						/* window.close();
						parent.window.close();
						parent.window.location.reload(); */
					}
				}
			});
		}
		
		//------------------------------------迁出宿舍-------------------------------------
		function toOutDorm(row){
	    	$.post("StudentManageAction!qryStudentChangeDorm.action?student_userName="+row.send_userName+"&guid="+row.guid,null,function(data){
	    		var resultData = eval('('+data+')');
	    		$("#student_id_outDorm").val(resultData[0].student_id);
	    		$("#dorm_id_outDorm").val(resultData[0].dorm_id);
	    		$("#student_userName_outDorm").val(resultData[0].student_userName);
	    		$("#student_name_outDorm").val(resultData[0].student_name);
	    		$("#student_sex_outDorm").val(resultData[0].student_sex);
	    		$("#building_name_outDorm").val(resultData[0].building_name);
	    		$("#dorm_name_outDorm").val(resultData[0].dorm_name);
	    	});
	    	$("#dlg_outDorm").dialog("open").dialog("setTitle","消息处理");
		}
		
		// 迁出宿舍保存
		function saveOut(){
			var student_id = $("#student_id_outDorm").val();
			var out_date = $("#out_date").datebox('getValue');
			var student_remark = $("#student_remark_outDorm").val();
			
			$("#fm_outDorm").form("submit",{
				url:"StudentManageAction!outDorm.action?student_id="+student_id+"&out_date="+out_date+"&student_remark="+student_remark,
				onSubmit:function(){
					return $(this).form('validate');
				},
				success:function(result){
					var result=eval("("+result+")");
					if(result.errorMsg){
						$.messager.alert("系统提示",result.errorMsg);
						return;
					}else{
						$.messager.alert("系统提示","操作成功，该学生已经迁出寝室！");
						$("#dlg_outDorm").dialog("close");
						$("#dg").datagrid("reload");
					}
				}
			});
		}
		
		// 获取当前时间
		function myformatter(date){  
		     var y = date.getFullYear();  
	           var m = date.getMonth()+1;  
	           var d = date.getDate();  
	           var time = y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	           return time; 
		}  
		          
		function myparser(s){  
		    if (!s) return new Date();
		    if(s != null && s.length > 0 && s != undefined){
		    	var ss = (s.split('-')); 
			    var y = parseInt(ss[0],10);  
			    var m = parseInt(ss[1],10);  
			    var d = parseInt(ss[2],10); 
	            if (!isNaN(y) && !isNaN(m) && !isNaN(d)){  
	                return new Date(y,m-1,d);  
	            } else {  
	                return new Date();  
	            }  
		    }  
		}
		
		//========================================================================
		//点击消息处理按钮 判断  消息的类型是  迁出宿舍 还是 调换宿舍
		function ChangeOrOutDorm(){
			var selectedRows=$("#dg").datagrid("getSelections");
			if(selectedRows.length!=1){
	    		$.messager.alert("系统提示","请选择一条要处理的消息！","warning");
	    		return;
	    	}
	    	var row=selectedRows[0];
	    	var see_state = row.see_state;
	    	if(see_state == "已确认"){
	    		$.messager.alert("系统提示","该消息已确认，禁止处理！","warning");
	    		return;
	    	}
	    	
	    	var msg_type = row.msg_type;
	    	// 根据 类型 调用不同的 方法
	    	if(msg_type == "宿舍调换"){
	    		toChangeDorm(row);
	    	}
	    	if(msg_type == "迁出申请"){
	    		toOutDorm(row);
	    	}
		}
		
	</script>
  </head>
  
  <input type="hidden" name="loginFlag" id="loginFlag" value="${sessionScope.loginFlag }"/>
  <table class=easyui-datagrid id="dg" title="消息基本信息"
       toolbar="#toolbar" pagination="true" pageList=[5,10,15] 
       	fit="true" rownumbers="true" fitColumns="true" 
    	url="StudentManageAction!qryNotSeeNoticeListByTeacher.action">
    <thead>
      <tr>
        <th field="id" checkbox="true" align="center">编号</th>
        <th field="title" width="70" align="center">标题</th>
        <th field="msg_type" width="50" align="center">消息类型</th>
        <th field="send_userName" width="60" align="center">发送人用户名</th>
        <th field="send_person" width="50" align="center">发送人</th>
        <th field="send_role" width="60" align="center">发送人角色</th>
        <th field="send_time" width="80" align="center">发送时间</th>
        <th field="see_state" width="50" align="center">消息操作</th>
        
        <th field="content" width="70" align="center">内容</th>
        <th field="guid" width="70" align="center">guid</th>
  </table>
  <div id=toolbar>
	  <div id="addUptDel" style="float: left">
	    <a href="javascript:ChangeOrOutDorm();" class=easyui-linkbutton iconCls="icon-handleMessage" plain="true">处理消息</a>
	  </div>
	  <div>
	      &nbsp;&nbsp;
	          查询：
		<select id="see_state" name="see_state" class="easyui-combobox" style="width:80px;">
			<option value="否">未处理</option>
			<option value="已确认">已确认</option>
		</select>
	     <input type="text" id="send_person" name="send_person" placeholder="请输入要查询的发送人">
	     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="btn" plain="true" onclick="searchUser()">搜索</a>
  	  </div>
     </div>
     
     
     <!-- ===========================调换寝室 div ================= -->
     <!-- 点击 消息 处理后    验证是 调换宿舍 后  才弹出此窗口 -->
    <div id="dlg_changeDorm" class="easyui-dialog" closed="true" style="width:430px;height:390px;padding:5px 20px"  buttons="#dlg-buttons_changeDorm">
        <form id="fm_changeDorm" method="post">
        	<input type="hidden" name="fixVo.student_id" id="student_id_changeDorm" />
        	<input type="hidden" name="dorm_id" id="dorm_id_changeDorm" />
        	<table cellspacing="10px;">
        		<tr>
        			<td>学生学号：</td>		
        			<td><input disabled="disabled" id="student_userName_changeDorm" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>学生姓名：</td>
        			<td><input disabled="disabled" id="student_name_changeDorm" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>学生性别：</td>
        			<td><input disabled="disabled" id="student_sex_changeDorm" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>目前楼宇：</td>
        			<td>
        				<input disabled="disabled"  id="building_name_changeDorm" class="easyui-validatebox" required="true" style="width: 200px;">
        			</td>
        		</tr>
        		<tr>
        			<td>目前寝室：</td>
        			<td>
        				<input disabled="disabled" id="dorm_name_changeDorm"  class="easyui-validatebox" required="true" style="width: 200px;">
        			</td>
        		</tr>
        		<tr>
        			<td><font color="red" style="font-weight: bolder">*</font>调换到楼宇：</td>
        			<td>
						<span id="buildingData">
						</span>
        			</td>
        		</tr>
        		<tr>
        			<td><font color="red" style="font-weight: bolder">*</font>调换到寝室：</td>
        			<td>
						<span id="dormData">
							<select id="dorm_id" name="dorm_id" class="easyui-combobox" style="width:200px;" >
								<option value="">====全部====</option>
							</select>
						</span>
						
        			</td>
        		</tr>
        		<tr>
        			<td><font color="red" style="font-weight: bolder">*</font>填写备注：</td>
        			<td>
        				<input name="fixVo.student_remark" id="student_remark_changeDorm" class="easyui-validatebox" required="true" style="width: 200px;">
        			</td>
        		</tr>
        	</table>
        </form>
    </div>
	<div id="dlg-buttons_changeDorm">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveChange()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_changeDorm').dialog('close')">关闭</a>
	</div>
   
    <!-- ===========================迁出寝室 div ================= -->
    <!-- 点击 消息 处理后    验证是 迁出宿舍 后  才弹出此窗口 -->
    <div id="dlg_outDorm" class="easyui-dialog" closed="true" style="width:430px;height:390px;padding:5px 20px"  buttons="#dlg-buttons_outDorm">
        <form id="fm_outDorm" method="post">
        	<input type="hidden" name="vo.student_id" id="student_id_outDorm"/>
        	<table cellspacing="10px;">
        		<tr>
        			<td>学生学号：</td>		
        			<td><input disabled="disabled" id="student_userName_outDorm" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>学生姓名：</td>
        			<td><input disabled="disabled" id="student_name_outDorm" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>学生性别：</td>
        			<td><input disabled="disabled" id="student_sex_outDorm" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>目前楼宇：</td>
        			<td>
        				<input disabled="disabled" id="building_name_outDorm" class="easyui-validatebox" required="true" style="width: 200px;">
        			</td>
        		</tr>
        		<tr>
        			<td>目前寝室：</td>
        			<td>
        				<input type="hidden" name="dorm_id" id="dorm_id_outDorm">
        				<input disabled="disabled" id="dorm_name_outDorm" class="easyui-validatebox" required="true" style="width: 200px;">
        			</td>
        		</tr>
        		<tr>
        			<td>迁出日期</td>
        			<td>
        				<input type="text" id="out_date" name="vo.out_date" data-options="formatter:myformatter,parser:myparser" class="easyui-datebox" /><br/>
        			</td>
        		</tr>
        		<tr>
        			<td><font color="red" style="font-weight: bolder">*</font>迁出备注：</td>
        			<td>
        				<input name="vo.student_remark" id="student_remark_outDorm" class="easyui-validatebox" required="true" style="width: 200px;">
        			</td>
        		</tr>
        	</table>
        	<font color="red" style="font-size: 13px">注：此功能只适用于毕业的学生迁出，迁出后将不可恢复！</font>
        </form>
    </div>
    <div id="dlg-buttons_outDorm">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveOut()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_outDorm').dialog('close')">关闭</a>
	</div>
    
  </body>
</html>
