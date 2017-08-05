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
			$("#dlg").dialog("open").dialog("setTitle","学生寝室调换");

			// 注册所有下拉控件
			$("select").combobox( {
				width : 155,
				listWidth : 100,
				editable : true
			});

			//根据buildingID查询出对应的Dorm列表
			$("#building_id").combobox({
				 onChange:function(){                  //注意：下拉框的onchange事件
				 	var building_id=$("#building_id").combobox('getValue');
					if(building_id!=""){
						$.post("<%=basePath%>StudentManageAction!queryDormByBuilding.action",
								"building_id="+building_id+"&index="+Math.random(),callbackByDorm);
					}
	         	 }  
			});

			//回调函数
			function callbackByDorm(data){
				$("#dormData").html(data).find('select').combobox();
			}
		});

		// 保存 调换后的 寝室
		function saveChange(){
			$("#fm").form("submit",{
				url:"StudentManageAction!change.action",
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
						//$("#dlg").dialog("close");
						window.close();
						parent.window.close();
						parent.window.location.reload();
					}
				}
			});
		}
	</script>
  </head>
  
  <body>
    <!-- 点击寝室调换后弹出此窗口 -->
   <div id="dlg" class="easyui-dialog" style="width:430px;height:390px;padding:5px 20px"  buttons="#dlg-buttons">
        <form id="fm" method="post">
        	<input type="hidden" name="fixVo.student_id" value="${fixVo.student_id }"/>
        	<input type="hidden" name="dorm_id" value="${fixVo.dorm_id }"/>
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
        				<input disabled="disabled" id="dorm_name" value="${fixVo.dorm_name }" class="easyui-validatebox" required="true" style="width: 200px;">
        			</td>
        		</tr>
        		<tr>
        			<td><font color="red" style="font-weight: bolder">*</font>调换到楼宇：</td>
        			<td>
        				<select id="building_id" name="fixVo.building_id">
							<option value="">==请选择或手动填写==</option>
							<c:if test="${sessionScope.bList!=null}">
								<c:forEach items="${sessionScope.bList}" var="obj">
									<option value="${obj.building_id}">${obj.building_name}</option>
								</c:forEach>
							</c:if>
						</select>
        			</td>
        		</tr>
        		<tr>
        			<td><font color="red" style="font-weight: bolder">*</font>调换到寝室：</td>
        			<td>
        				<span id="dormData">
	        				<select id="dorm_id" name="fixVo.dorm_id" >
								<option value="">==请选择或手动填写==</option>
							</select>
						</span>
        			</td>
        		</tr>
        		<tr>
        			<td><font color="red" style="font-weight: bolder">*</font>填写备注：</td>
        			<td>
        				<input name="fixVo.student_remark" id="student_remark" class="easyui-validatebox" required="true" style="width: 200px;">
        			</td>
        		</tr>
        	 </s:if>
        	</table>
        </form>
    </div>
    
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveChange()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">关闭</a>
	</div>
  </body>
</html>
