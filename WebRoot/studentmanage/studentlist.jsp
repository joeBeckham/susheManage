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
	
	<script type="text/javascript" src="js/date.js"></script>
	<script type="text/javascript">
	
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
	
	$(function() {

		// 日期处理
		/* $("input.easyui-datebox").datebox( {
			required : true,
			formatter : dateFormatter,
			parser : dateParser
		}); */
		
		// 设置日期框中的默认值
		var curr_time = new Date(); 
		$("#out_date").datebox("setValue",myformatter(curr_time));  
		
		// 注册所有下拉控件
		$("select").combobox( {
			width : 155,
			listWidth : 100,
			editable : true
		});

		// 查询出全部的building列表
		$.post("<%=basePath%>StudentManageAction!queryAllBuilding.action?index="+Math.random(),null,callbackByBuilding);
		function callbackByBuilding(data){
			$("#buildingData").html(data);
			//$("#buildingData").html(data).find('select').combobox();
		}
		
		// TIP: 配合body解决页面跳动和闪烁问题
		$("body").css({visibility:"visible"});
		
	});

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
		var rows = $("#ROWS").val();
		var page = $("#PAGE").val();
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
					$.post("StudentManageAction!list.action?rows="+rows+"&page="+page,function(msg){
						$.messager.alert("系统提示","入住操作成功！","info");
					});
				}
			}
		});
	}

	/*查询出List，若List不为空，则可以进行寝室调换操作，否则弹出  ：该学生还没有入住，不能进行寝室调换操作*/
	function toChangeDorm(){
		var student_userName2=$("#student_userName2").val();
		$("#form2").form("submit",{
			url:"StudentManageAction!queryChangeVo.action?student_userName="+student_userName2,
			onSubmit:function(){
				return $(this).form('validate');
			},
			success:function(result){
				var result=eval("("+result+")");
				if(result.errorMsg){
					$.messager.alert("系统提示",result.errorMsg,"warning");
					return;
				}else{
					var iWidth=440;                          //弹出窗口的宽度; 
			        var iHeight=395;                         //弹出窗口的高度; 
			        //获得窗口的垂直位置 
			        var iTop = (window.screen.availHeight - 30 - iHeight) / 2; 
			        //获得窗口的水平位置 
			        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; 
			        var arguemnts = new Object();
           		    arguemnts.window = window;
			        window.open("StudentManageAction!queryChangeVo1.action?student_userName="+student_userName2, "", 'height=' + iHeight + ',,innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no'); 
					//window.open("StudentManageAction!queryChangeVo1.action?student_userName="+student_userName2," 'height=100, width=100, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no')");
				}
			}
		});
	}

	/*查询出List，若List不为空，则可以进行迁出操作，否则弹出  ：该学生还没有入住，不能进行迁出操作*/
	function toOutDorm(){
		var student_userName3=$("#student_userName3").val();
		var out_data=$('#out_date').datebox('getValue');
		$("#form3").form("submit",{
			url:"StudentManageAction!queryOutVo.action?student_userName="+student_userName3+"&out_data="+out_data,
			onSubmit:function(){
				return $(this).form('validate');
			},
			success:function(result){
				var result=eval("("+result+")");
				if(result.errorMsg){
					$.messager.alert("系统提示",result.errorMsg,"warning");
					return;
				}else{
					window.location="StudentManageAction!queryOutVo1.action?student_userName="+student_userName3+"&out_data="+out_data;
				}
			}
		});
	}

	</script>
	
	<link rel="stylesheet" type="text/css" href="css/web.css">
	<style type="text/css">
		.panel-header{border-width:0 0 1px 1px;}
		.panel-body{border-width:0 0 0 1px;}
		.table-edit td{padding-left:10px;}
		.operator .combobox{border:0;margin:0 0 0 3px;}
		.operator .combobox-text{color:#55f;height:15px;margin:0;background-color:window;cursor:pointer;}
		.operator .combobox-arrow{display:none;}
	</style>
  </head>
  
 <body class="easyui-layout" style="visibility:hidden;">
 	<input type="hidden" name="PAGE" id="PAGE" value="${sessionScope.PAGE }"/>
 	<input type="hidden" name="ROWS" id="ROWS" value="${sessionScope.ROWS }"/>
 	
    <div region="east" title="<font style='font-size:15px'>基本操作</font>" icon="icon-forward" collapsed=true style="width:190px;overflow:auto;" split="false" border="true">
		<div class="datagrid-toolbar" style="padding:7px">	
			<b><font color="green">学生入住登记</font></b>
		</div>
		
		<script type="text/javascript">
		</script>
		
		<form id="form1" method="post">
			<table class="table-edit" width="100%" >				
				<tr>
					<td><b>楼宇：</b>
						<span id="buildingData">
						</span>
					</td>
				</tr>
				<tr>
					<td><b>宿舍：</b>
						<span id="dormData">
							<select id="dorm_id" name="dorm_id" >
								<option value="">==全部==</option>
							</select>
						</span>
					</td>
				</tr>
				<tr>
					<td>
						<b>学生学号：</b>
						<input type="text" id="student_userName" name="student_userName"/>
					</td>
				</tr>
				<tr>
					<td>
						<a href="javascript:void(0)" onclick="inDorm()" class="easyui-linkbutton" plain="true" icon="icon-search">确定入住</a>
					</td>
				</tr>
			</table>
		</form>
		
		<div class="datagrid-toolbar"  style="padding:7px">	
			<b><font color="green">学生宿舍调换</font></b>
		</div>
		<form id="form2" method="post" >
			<table class="table-edit" width="100%" >
				<tr>
					<td>
						<b>请输入要调换宿舍学生的学号：</b>
						<input type="text" id="student_userName2" name="student_userName2"/>
					</td>
				</tr>
				<tr>
					<td>
						<a href="javascript:void(0)" onclick="toChangeDorm()" class="easyui-linkbutton" plain="true" icon="icon-search">确定调换</a>
					</td>
				</tr>
			</table>
		</form>
		
		<div class="datagrid-toolbar" style="padding:7px">	
			<b><font color="green">学生迁出登记</font></b>
		</div>	
		<form id="form3" method="post" >
			<table class="table-edit" width="100%" >
				<tr>
					<td>
						<b>日期：</b>
						<input type="text" id="out_date" name="out_date" data-options="formatter:myformatter,parser:myparser" class="easyui-datebox" /><br/>
					</td>
				</tr>
				<tr>
					<td>
						<b>学生学号：</b>
						<input type="text" id="student_userName3" name="student_userName3"/>
					</td>
				</tr>
				<tr>
					<td>
						<a id="ajax" href="javascript:void(0)" onclick="toOutDorm()" class="easyui-linkbutton" plain="true" icon="icon-search">确定迁出</a>
					</td>
				</tr>
			</table>
		</form>
    </div>
    
    <div region="center" style="overflow:hidden;" border="false">
		<iframe id="list" src="studentmanage/list.jsp" scrolling="no" style="width:100%;height:100%;border:0;"></iframe>
    </div>
    
 </body>
</html>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    