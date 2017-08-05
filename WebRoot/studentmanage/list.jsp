<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>学生基本信息</title>
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
			// TIP: 配合body解决页面跳动和闪烁问题
			$("body").css({visibility:"visible"});
			
			// 隐藏student_userPass列
			$("#dg").datagrid('hideColumn', 'student_userPass');
			
			var loginFlag=$("#loginFlag").val();
			if("teacher"==loginFlag){
				$("#teacherShow").show();
				$("#systemShow").hide();
				$("#vipStudentShow").hide();
			}
			if("system"==loginFlag){
				$("#systemShow").show();
				$("#teacherShow").hide();
				$("#vipStudentShow").hide();
			}
			if("vipStudent"==loginFlag){
				$("#systemShow").hide();
				$("#teacherShow").hide();
				$("#vipStudentShow").show();
			}
		});
			
		//查询
		function searchUser(){
	    	$("#dg").datagrid('load',{
	    		s_name:$("#s_name").val(),
	    		s_state:$("#s_state").combobox('getValue'),
	    		s_institution:$("#s_institution").combobox('getValue'),
	    		s_class:$("#s_class").val()
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
		    	strIds.push(selectedRows[i].student_id);
		    }
		    var ids = strIds.join(",");
		    if(ids.length == 1 || ids.length == 2){
		    	if(selectedRows[0].student_building != null && selectedRows[0].student_dorm != null 
		    			&& selectedRows[0].student_dorm != "" && selectedRows[0].student_building != ""){
		    		$.messager.alert("系统提示","该学生已入住寝室，禁止删除！","warning");
			    	return;
		    	}
		    }else {
		    	for(var i=0;i<selectedRows.length;i++){
			    	if(selectedRows[i].student_building != null && selectedRows[i].student_building != "" 
			    			&& selectedRows[i].student_dorm != null && selectedRows[i].student_dorm !=""){
		    			$.messager.alert("系统提示","选中记录存在已入住学生，禁止删除！","warning");
		    			return;
			    	}
			    }
		    }
		    $.messager.confirm("系统提示","您确定要删掉这<font color='red'>"+selectedRows.length+"</font>条数据么?",function(r){
		    	if(r){
		    		$.post("StudentManageAction!delete.action",{delIds:ids},function(result){
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
			$("#dlg").dialog("open").dialog("setTitle","添加学生");
			$("#fm").form("clear");
			
			// 设置单选按钮默认选中
			$('[name="vo.student_sex"]:radio').each(function() {   
                if (this.value == '男'){   
                   this.checked = true;   
                }       
            }); 
			url="StudentManageAction!save.action";
		}
	
		//编辑
		function editUser(){
			var selectedRows=$("#dg").datagrid("getSelections");
			if(selectedRows.length!=1){
	    		$.messager.alert("系统提示","请选择一条要修改的数据！","warning");
	    		return;
	    	}
	    	var row=selectedRows[0];
	    	
	    	$("#dlg").dialog("open").dialog("setTitle","编辑学生");
	    	$("#student_userName").val(row.student_userName);
	    	/* $("#student_userPass").val(row.student_userPass); */
	    	$("#student_name").val(row.student_name);
	    	// 赋值给界面radio控件
	    	if(row.student_sex == "男"){
	    		$('input:radio[name="vo.student_sex"][value="男"]').prop('checked', true);
	    	}else if(row.student_sex == "女"){
	    		$('input:radio[name="vo.student_sex"][value="女"]').prop('checked', true);
	    	}
	    	//赋值给界面的checkBox
	    	if(row.student_headFlag == "是"){
	    		$("[name='vo.student_headFlag']").attr("checked","checked");
	    	}else{
	    		$("[name='vo.student_headFlag']").removeAttr("checked"); //从每一个匹配的元素中删除一个属性
	    	}
	    	
	    	$("#student_institution").val(row.student_institution);
	    	$("#student_major").val(row.student_major);
	    	$("#student_class").val(row.student_class);
	    	$("#student_phone").val(row.student_phone);
	    	$("#student_remark").val(row.student_remark);

	    	url="StudentManageAction!save.action?student_id="+row.student_id;
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

		//用模版导出用户
		 function exportUser(){
			$('#condition').submit();
		}
		
		
		// 获取当前时间
		function myformatterStart(date){  
		     var y = date.getFullYear();  
            var m = date.getMonth()+1;  
            var d = date.getDate();  
            var h = date.getHours();  
            var min = date.getMinutes();  
            var sec = date.getSeconds();
            var time = y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+' '+(h<10?('0'+h):h)+':'+(min<10?('0'+min):min)+':'+(sec<10?('0'+sec):sec);
            return time; 
		}  
		
		function myformatterEnd(date){  
		    var y = date.getFullYear();  
            var m = date.getMonth()+1;  
            var d = date.getDate();  
            var h = date.getHours();  
            var min = date.getMinutes();  
            var sec = date.getSeconds();
            var time = y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+' '+(h<10?('0'+h):h)+':'+(min<10?('0'+min):min)+':'+(sec<10?('0'+sec):sec);
            return time; 
		}  
		          
		function myparser(s){  
		    if (!s) return new Date();
		    if(s != null && s.length > 0 && s != undefined){
		    	var ss = (s.split('-')); 
			    var y = parseInt(ss[0],10);  
			    var m = parseInt(ss[1],10);  
			    var d = parseInt(ss[2],10); 
			    var tt = ss[2];
			    tt = (tt.split(' '));
			    var time = tt[1];
			    ss = (time.split(':')); 
			     var h = parseInt(ss[0],10);  
			     var min = parseInt(ss[1],10); 
			     var sec = parseInt(ss[2],10); 
	            if (!isNaN(y) && !isNaN(m) && !isNaN(d) && !isNaN(h) && !isNaN(min) && !isNaN(sec)){  
	                return new Date(y,m-1,d,h,min,sec);  
	            } else {  
	                return new Date();  
	            }  
		    }  
		}  
		
		
		//缺寝登记
		function lackUser(){
			var selectedRows=$("#dg").datagrid("getSelections");
			if(selectedRows.length!=1){
	    		$.messager.alert("系统提示","请先选择缺寝的学生！","warning");
	    		return;
	    	}
	    	var row=selectedRows[0];
	    	if(row.student_building == null || row.student_building == ""
	    		 || row.student_dorm == null || row.student_dorm == ""){
	    		$.messager.alert("系统提示","该学生还没有入住寝室，请确认后登记！","warning");
	    		return;
	    	}
	    	
	    	$("#dlgLack").dialog("open").dialog("setTitle","学生缺寝登记");
	    	$("#fmLack").form("clear");
	    	$("#student_userNameLack").val(row.student_userName);
	    	$("#student_id").val(row.student_id);
	    	$("#student_nameLack").val(row.student_name);
	    	
	    	// 赋值给界面radio控件
	    	if(row.student_sex == "男"){
	    		$('input:radio[name="vo.student_sex"][value="男"]').prop('checked', true);
	    	}else if(row.student_sex == "女"){
	    		$('input:radio[name="vo.student_sex"][value="女"]').prop('checked', true);
	    	}
	    	
	    	$("#student_institutionLack").val(row.student_institution);
	    	$("#student_majorLack").val(row.student_major);
	    	$("#student_classLack").val(row.student_class);
	    	$("#student_buildingLack").val(row.student_building);
	    	$("#student_dormLack").val(row.student_dorm);
	    	$("#student_remarkLack").val();
	    	
	    	// 设置时间  
			/* var curr_time = new Date();   
	    	// 设置日期框中的默认值
			$("#txtBeginTime").datebox("setValue",myformatterStart(curr_time));  
			$("#txtEndTime").datebox("setValue",myformatterEnd(curr_time));  */

	    	url="StudentManageAction!saveLack.action?lackFlag=" + 1;
		}
		
		//缺寝记录 保存
		function saveUserLack(){
			var realName = $("#realName").val();
			
			$("#fmLack").form("submit",{
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
						$.messager.alert("系统提示","缺寝录入成功，已通知该学生！","info");
						$("#dlgLack").dialog("close");
						$("#dg").datagrid("reload");
					}
				}
			});
		}
		
	</script>
  </head>
  
<body style="margin:5px">
	<input type="hidden" name="loginFlag" id="loginFlag" value="${sessionScope.loginFlag }"/>
    <table id="dg" title="学生基本信息" class="easyui-datagrid" style="width:900px;"
            url="StudentManageAction!list.action" 
            toolbar="#toolbar" pagination="true" pageList=[5,10,15]
            rownumbers="true" fitColumns="true" fit="true" >
        <thead>
            <tr>
            	<th field="student_id" checkbox="true" align="center">id</th>
            	<th field="student_userName" width="100" align="center">学号</th>
            	<th field="student_userPass" width="80" align="center">密码</th>
                <th field="student_name" width="50" align="center">姓名</th>
                <th field="student_sex" width="40" align="center">性别</th>
                <th field="student_institution" width="70" align="center">学院</th>
                <th field="student_major" width="100" align="center">专业</th>
                <th field="student_class" width="40" align="center">班级</th>
                <th field="student_headFlag" width="50" align="center">负责人</th>
                <th field="student_phone" width="100" align="center">电话</th>
                <th field="student_building" width="50" align="center">楼宇</th>
                <th field="student_dorm" width="50" align="center">宿舍</th>
                <th field="student_state" width="50" align="center">状态</th>
                <th field="student_remark" width="200" align="center">备注</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
      <div id="systemShow" style="float: left">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">添加学生</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑学生</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteUser()">删除学生</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="exportUser()">导出学生</a>
      </div>
      <div id="teacherShow" style="float: left">
       	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-inDorm" plain="true" onclick="studentIndorm()">入住登记</a>
       	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-fenseRegister" plain="true" onclick="lackUser()">缺寝登记</a>
       	<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-lanseRegister" plain="true" onclick="studentOutDorm()">迁出登记</a> -->
      </div>
      <div id="vipStudentShow" style="float: left">
       	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-fenseRegister" plain="true" onclick="lackUser()">缺寝登记</a>
      </div>
      <div>
  		<form id="condition" name="condition" action="StudentManageAction!export.action" method="post"> 
      		&nbsp;&nbsp;
      		查询：
	      	<select id="s_state" class="easyui-combobox" name="s_state" style="width:100px;">   
			    <option value="0">==全部状态==</option>   
			    <option value="1">入住</option>   
			    <option value="2">未入住</option> 
			    <option value="3">迁出</option>     
			</select>
			<select class="easyui-combobox" style="width:130px;" id="s_institution" name="s_institution">
				<option value="">==全部学院==</option>   
				<option value="政治与法学学院">政治与法学学院</option>
				<option value="体育学院">体育学院</option>
				<option value="中国语言文学学院">中国语言文学学院</option>
				<option value="外国语言文学学院">外国语言文学学院</option>
				<option value="新闻传播学院">新闻传播学院</option>
				<option value="音乐学院">音乐学院</option>
				<option value="美术学院">美术学院</option>
				<option value="数学学院">数学学院</option>
				<option value="物理与电子学院">物理与电子学院</option>
				<option value="化学化工学院">化学化工学院</option>
				<option value="机械工程学院">机械工程学院</option>
				<option value="信息与通信工程学院">信息与通信工程学院</option>
				<option value="计算机学院">计算机学院</option>
				<option value="土木建筑工程学院">土木建筑工程学院</option>
				<option value="经济与管理学院">经济与管理学院</option>
			</select>
			<input class="easyui-validatebox" style="width:40px;height:22;" id="s_class" name="s_class">班
      		<input class="easyui-validatebox" style="width:100px;height:22;" id="s_name" name="s_name" placeholder="请输入姓名">
        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="btn" plain="true" onclick="searchUser()">搜索</a>
        </form>
  	  </div>
    </div>
    
    <!-- 增加 和 修改 -->
    <div id="dlg" class="easyui-dialog" style="width:420px;height:425px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post">
        	<table cellspacing="10px;">
        		<tr>
        			<td>学号：</td>
        			<td><input name="vo.student_userName" id="student_userName" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>姓名：</td>
        			<td><input name="vo.student_name" id="student_name" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>性别：</td>
        			<td>
        				男：<input name="vo.student_sex" id="student_sex" type="radio" class="easyui-validatebox" checked="checked" required="true" value="男">
        				女：<input name="vo.student_sex" id="student_sex" type="radio" class="easyui-validatebox" required="true" value="女">
        			</td>
        		</tr>
        		<tr>
        			<td>学院：</td>
        			<td><input name="vo.student_institution" id="student_institution" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>专业：</td>
        			<td><input name="vo.student_major" id="student_major" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>班级：</td>
        			<td><input name="vo.student_class" id="student_class" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>联系电话：</td>
        			<td><input name="vo.student_phone" id="student_phone" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>备注：</td>
        			<td><input name="vo.student_remark" id="student_remark" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td colspan="2"><font color="red">是否为班级负责人：
        				<input type="checkbox" name="vo.student_headFlag" id="student_headFlag" value="是">
        				(选中表示是)</font>
        			</td>
        		</tr>
        	</table>
        </form>
    </div>
    
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">关闭</a>
	</div>
	
	<!-- 入住的 js -->
	<script type="text/javascript">
		/* 入住登记 */
		function studentIndorm(){
			var selectedRows=$("#dg").datagrid("getSelections");
			if(selectedRows.length!=1){
	    		$.messager.alert("系统提示","请选择一条要入住的学生记录！","warning");
	    		return;
	    	}
	    	var row=selectedRows[0];
	    	var student_state = row.student_state;
	    	if(student_state == "入住"){
	    		$.messager.alert("系统提示","该生处于入住状态，请选择未入住学生操作！","warning");
	    		return;
	    	}
	    	if(student_state == "迁出"){
	    		$.messager.alert("系统提示","该生处于迁出状态，禁止入住！","warning");
	    		return;
	    	}
	    	var student_userName = row.student_userName;
	    	var student_name = row.student_name;
	    	$("#student_userName_indorm").val(student_userName);
	    	$("#student_userName_indorm1").val(student_userName);
	    	$("#student_name_indorm").val(student_name);
	    	
	    	// 查询出全部的building列表
			$.post("<%=basePath%>StudentManageAction!queryAllBuilding.action?index="+Math.random(),null,callbackByBuilding);
			function callbackByBuilding(data){
				$("#buildingData").html(data);
			}
	    	
			$("#dlginDrom").dialog("open").dialog("setTitle","学生入住登记");
			
		}
	
		
		//根据buildingID查询出对应的Dorm列表
		function changeBuilding(id){
			var building_id=$("#building_id").val();
			if(building_id!=""){
				$.post("<%=basePath%>StudentManageAction!queryDormByBuilding.action",
						"building_id="+building_id+"&index="+Math.random(),callbackByDorm);
			}
		}
		function callbackByDorm(data){
			$("#dormData").html(data).find('select').combobox();
		}
		
		/*入住操作*/
		function inDorm(){
			var dorm = $("#dorm_id").combobox('getValue'); 
			$("#form1").form("submit",{
				url:"StudentManageAction!inDorm.action?dorm_id=" + dorm,
				onSubmit:function(){
					return $(this).form('validate');
				},
				success:function(result){
					var result=eval("("+result+")");
					if(result.errorMsg){
						$.messager.alert("系统提示",result.errorMsg,"warning");
						return;
					}else{
						$.messager.alert("系统提示","入住操作成功！","info");
						$("#dlginDrom").dialog("close");
						$("#dg").datagrid("reload");
					}
				}
			});
		}
	</script>
	
	
	<!-- 入住登记 -->
	<div id="dlginDrom" class="easyui-dialog" style="width:380px;height:295px;padding:15px 29px"
            closed="true" buttons="#dlg-buttonsinDrom">
    	<form id="form1" method="post">
			<table class="table-edit" width="100%" cellspacing="10px;">				
				<tr>
					<td><b>楼&nbsp;&nbsp;宇：</b>
						<span id="buildingData">
						</span>
					</td>
				</tr>
				<tr>
					<td><b>宿&nbsp;&nbsp;舍：</b>
						<span id="dormData">
							<select id="dorm_id" name="dorm_id" style="width : 170">
								<option value="">==全部==</option>
							</select>
						</span>
					</td>
				</tr>
				<tr>
					<td>
						<b>学生学号：</b>
						<input type="text" id="student_userName_indorm" name="student_userName1" class="easyui-validatebox" disabled="disabled"/>
						<input type="hidden" id="student_userName_indorm1" name="student_userName"/>
					</td>
				</tr>
				<tr>
					<td>
						<b>学生姓名：</b>
						<input type="text" id="student_name_indorm" name="student_name" class="easyui-validatebox" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="left">
						<font color="red" style="font-size: 12">注：请选择楼宇和宿舍后确定入住！</font>
					</td>
				</tr>
			</table>
		</form>
    </div>
    <div id="dlg-buttonsinDrom">
    	<a href="javascript:void(0)" onclick="inDorm()" class="easyui-linkbutton" plain="true" icon="icon-search">确定入住</a>
    </div>
	
	
	 <!-- 缺寝登记 -->
    <div id="dlgLack" class="easyui-dialog" style="width:580px;height:360px;padding:20px 20px"
            closed="true" buttons="#dlg-buttonsLack">
        <form id="fmLack" method="post">
        	<table cellspacing="3px;" border = 1>
        		<tr>
        			<td style="width: 70px;">
        				姓名：<input type="hidden" name = "vo.student_id" id= "student_id">
        			</td>
        			<td>
        				<input name="vo.student_name" id="student_nameLack" readonly="readonly" class="easyui-validatebox" style="width: 180px;">
        			</td>
        		
        			<td style="width: 70px;">性别：</td>
        			<td>
        				男：<input name="vo.student_sex" id="student_sex" readonly="readonly" type="radio" class="easyui-validatebox"  value="男">
        				女：<input name="vo.student_sex" id="student_sex" readonly="readonly" type="radio" class="easyui-validatebox"  value="女">
        			</td>
        		</tr>
        		<tr>
        			<td>学院：</td>
        			<td><input name="vo.student_institution" id="student_institutionLack" readonly="readonly"  class="easyui-validatebox" style="width: 180px;"></td>
        		
        			<td>专业：</td>
        			<td><input name="vo.student_major" id="student_majorLack" readonly="readonly"  class="easyui-validatebox" style="width: 180px;"></td>
        		</tr>
        		<tr>
        			<td>班级：</td>
        			<td><input name="vo.student_class" id="student_classLack" readonly="readonly"  class="easyui-validatebox"  style="width: 180px;"></td>
        		
        			<td>楼宇：</td>
        			<td><input name="vo.student_building" id="student_buildingLack" readonly="readonly" class="easyui-validatebox"  style="width: 180px;"></td>
        		</tr>
        		<tr>
        			<td>宿舍：</td>
        			<td><input name="vo.student_dorm" id="student_dormLack" readonly="readonly" class="easyui-validatebox"  style="width: 180px;"></td>
        		</tr>
        		<tr>
        			<td>时间段：</td>
        			<td  colspan = 3>
        				<input id="txtBeginTime" name="vo.student_lackBeginTime" class="easyui-datetimebox" required="true" data-options="formatter:myformatterStart,parser:myparser"></input>
        					&nbsp;至  &nbsp;
						<input id="txtEndTime" name="vo.student_lackEndTime" class="easyui-datetimebox" required="true" data-options="formatter:myformatterEnd,parser:myparser"></input>  
        			</td>
        		</tr>
        		<tr>
        			<td>原因：</td>
        			<td>
        				<input name="vo.student_remark" id="student_remarkLack" class="easyui-validatebox" required="true" style="width: 200px;">
        			</td>
        		</tr>
        	</table>
        	<br>
        	<font color="red">注：请填写学生缺寝时间段和原因后保存！</font>
        </form>
    </div>
    <div id="dlg-buttonsLack">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUserLack()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgLack').dialog('close')">关闭</a>
	</div>
	
	
	<!-- 迁出登记的js -->
	<script type="text/javascript">
		// 获取当前日期
		function myformatterOut(date){  
		     var y = date.getFullYear();  
	           var m = date.getMonth()+1;  
	           var d = date.getDate();  
	           var time = y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	           return time; 
		}  
		          
		function myparserOut(s){  
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
		
		/* 点击迁出登记 按钮触发 */
		function studentOutDorm(){
			var selectedRows=$("#dg").datagrid("getSelections");
			if(selectedRows.length!=1){
	    		$.messager.alert("系统提示","请选择一条要迁出的学生记录！","warning");
	    		return;
	    	}
	    	var row=selectedRows[0];
	    	$("#student_id_outDorm").val(row.student_id);
	    	$("#student_userName_outDorm").val(row.student_userName);
	    	$("#student_userName_outDorm1").val(row.student_userName);
	    	$("#student_name_outDorm").val(row.student_name);
	    	$("#student_sex_outDorm").val(row.student_sex);
	    	$("#building_name_outDorm").val(row.student_building);
	    	$("#dorm_name_outDorm").val(row.student_dorm);
	    	var curr_time = new Date(); 
			$("#out_date_outDorm").datebox("setValue",myformatterOut(curr_time));  
	    	
			$("#dlg_outDorm").dialog("open").dialog("setTitle","学生迁出登记");
		}

		function saveOut(){
			$("#fm_outDorm").form("submit",{
				url:"StudentManageAction!queryOutVo.action",
				onSubmit:function(){
					return $(this).form('validate');
				},
				success:function(result){
					var result=eval("("+result+")");
					if(result.errorMsg){
						$.messager.alert("系统提示",result.errorMsg,"warning");
						return;
					}else{
						$.messager.alert("系统提示","操作成功，该学生已经迁出寝室！","info");
						$("#dlg_outDorm").dialog("close");
					}
				}
			});
		}
	</script>
	
	<!-- 迁出登记 -->
	<div id="dlg_outDorm" class="easyui-dialog" style="width:420px;height:360px;padding:8px 35px"
            closed="true" buttons="#dlg-buttons_outDorm">
         <form id="fm_outDorm" method="post">
        	<input type="hidden" name="vo.student_id" id="student_id_outDorm"/>
        	<table cellspacing="7px;">
        		<tr>
        			<td>学生学号：</td>		
        			<td>
        				<input disabled="disabled" id="student_userName_outDorm" class="easyui-validatebox" required="true" style="width: 200px;">
        				<input type="hidden" id="student_userName_outDorm1" name="vo.student_userName">
        			</td>
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
        				<%-- <input type="hidden" name="dorm_id" value="${fixVo.dorm_id }"> --%>
        				<input disabled="disabled" id="dorm_name_outDorm" class="easyui-validatebox" required="true" style="width: 200px;">
        			</td>
        		</tr>
        		<tr>
					<td>迁出日期：</td>
					<td>	
						<input type="text" id="out_date_outDorm" name="vo.out_date" data-options="formatter:myformatterOut,parser:myparserOut" class="easyui-datebox" style="width:200px"/>
					</td>
				</tr>
        		<tr>
        			<td><font color="red" style="font-weight: bolder">*</font>迁出备注：</td>
        			<td>
        				<input name="vo.student_remark" id="student_remark" class="easyui-validatebox" required="true" style="width: 200px;">
        			</td>
        		</tr>
        		<tr>
        			<td colspan="2">
        				<font color="red" style="font-size: 13px">注：此功能只适用于毕业生,迁出后将不可恢复！</font>
        			</td>
        		</tr>
        	</table>
        </form>
    </div>
    <div id="dlg-buttons_outDorm">
    	<a id="ajax" href="javascript:void(0)" onclick="saveOut()" class="easyui-linkbutton" plain="true" icon="icon-search">确定迁出</a>
    </div>
  </body>
</html>
