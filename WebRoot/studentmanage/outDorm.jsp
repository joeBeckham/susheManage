<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'update.jsp' starting page</title>
    
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
		$(function(){
			$("#dlg").dialog("open").dialog("setTitle","学生迁出登记");
		});

		function saveOut(){
			$("#fm").form("submit",{
				url:"StudentManageAction!outDorm.action",
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
						$("#dlg").dialog("close");
					}
				}
			});
		}
	</script>
  </head>
  
  <body>
    <!-- 点击迁出按钮后弹出此窗口 -->
   <div id="dlg" class="easyui-dialog" style="width:430px;height:365px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post">
        	<input type="hidden" name="vo.student_id" value="${fixVo.student_id }"/>
        	<input type="hidden" name="vo.out_date" value="${fixVo.out_date }"/>
        	<font color="red">注：此功能只适用于毕业的学生迁出，迁出后将不可恢复！</font>
        	<table cellspacing="10px;">
        	  <s:if test="fixVo!=null">
        		<tr>
        			<td>学生学号：</td>		
        			<td><input value="${fixVo.student_userName }" disabled="disabled" id="student_userName" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>学生姓名：</td>
        			<td><input value="${fixVo.student_name }" disabled="disabled" id="student_name" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>学生性别：</td>
        			<td><input value="${fixVo.student_sex }" disabled="disabled" id="student_sex" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>目前楼宇：</td>
        			<td>
        				<input disabled="disabled" value="${fixVo.building_name }" id="building_name" class="easyui-validatebox" required="true" style="width: 200px;">
        			</td>
        		</tr>
        		<tr>
        			<td>目前寝室：</td>
        			<td>
        				<input type="hidden" name="dorm_id" value="${fixVo.dorm_id }">
        				<input disabled="disabled" id="dorm_name" value="${fixVo.dorm_name }" class="easyui-validatebox" required="true" style="width: 200px;">
        			</td>
        		</tr>
        		<tr>
        			<td><font color="red" style="font-weight: bolder">*</font>迁出备注：</td>
        			<td>
        				<input name="vo.student_remark" id="student_remark" class="easyui-validatebox" required="true" style="width: 200px;">
        			</td>
        		</tr>
        	 </s:if>
        	</table>
        </form>
    </div>
    
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveOut()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">关闭</a>
	</div>
  </body>
</html>
