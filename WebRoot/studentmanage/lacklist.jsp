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
    
    <title>缺寝记录</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">  
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta charset = "utf-8"/>  

	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/demo/demo.css">
	<!-- <link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.3/themes/bootstrap/easyui.css"> -->
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery-1.8.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>

	<script type="text/javascript" src="js/amq_jquery_adapter.js"></script>
	<script type="text/javascript" src="js/amq.js"></script>
	
	<!-- 调用判断 书否第一个 打开页面的 js -->
	<script type="text/javascript" src="js/ifFirstOpen.js" charset="utf-8"></script>

	<script type="text/javascript" charset = "utf-8" >
		var url;

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
		
		//隐藏密码和缺寝标识这两列
		/*$(function(){
			$("#dg").datagrid('hideColumn', 'student_userPass');
			$("#dg").datagrid('hideColumn', 'student_lackFlag');
		}); */
			
		//查询
		function searchUser(){
	    	$("#dg").datagrid('load',{
	    		s_name:$("#s_name").val()
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
			// 设置时间  
			var curr_time = new Date();     
			$("#dlg").dialog("open").dialog("setTitle","缺寝登记");
			$("#fm").form("clear");
			
			// 设置日期框中的默认值
			$("#txtBeginTime").datebox("setValue",myformatterStart(curr_time));  
			$("#txtEndTime").datebox("setValue",myformatterEnd(curr_time)); 
			
			// 设置单选按钮默认选中
			$('[name="vo.student_sex"]:radio').each(function() {   
                if (this.value == '男'){   
                   this.checked = true;   
                }       
            });  
            
			 //获取时间  
			 /* var beginTime=$("#txtBeginTime").datebox("getValue");  
			var endTime=$("#txtEndTime").datebox("getValue");   */
			
			// 提交路径
			url="StudentManageAction!save.action";
		}
	
		//用模版导出用户
		 function exportUser(){
			$('#condition').submit();
		}
		
		/* var messages;
	
		$(function(){
			var loginFlag = $("#loginFlag").val();
		    var realName = $("#realName").val();
		    var GUID = $("#GUID").val();
		    
			var amq = org.activemq.Amq;
			amq.init({
				uri : 'amq',
				logging : true,
				timeout : 1,
				clientId:(new Date()).getTime().toString()  /*  clientId是为了在一个浏览器的多个页面中使用ActiveMQ Ajax. */
			//}); 
			
			
			/* var myHandler = function(message) {
			    //java代码发送的消息
		    	alert("java!");
		    	if(loginFlag != null && loginFlag == "student"){
		    		if(realName != null && realName != ""){
		    			messages = unescape(message.nodeValue);
				        //writeCookie(GUID, messages, 2);
			    		$.messager.show({
							title:"缺寝通知：",
							msg:unescape(message.nodeValue),
							timeout:5000
						});
						// unescape 为解码函数。在后台 对 汉字 进行了编码
			    	}
		    	}
			};
			if(loginFlag != null && loginFlag == "student"){
	    		if(realName != null && realName != ""){ */
					 
					 // 判断是首次打开页面 还是 刷新页面  	    
					/*  window.onload=function(){
						  var ck=new Cookie(GUID);  //每个页面的new Cookie名HasLoaded不能相同
						  if(ck.Read()==null){//未加载过，Cookie内容为空
						      //alert("首次打开页面");
						      
						      amq.addListener(GUID, "quene://"+$("#realName").val(), myHandler);
						      
						      //设置保存时间
						      var dd = new Date();
						      dd = new Date(dd.getYear() + 1900, dd.getMonth(), dd.getDate());
						      dd.setDate(dd.getDate() + 365);
						      ck.setExpiresTime(dd);
						
						      ck.Write("true");  //设置Cookie。只要IE不关闭，Cookie就一直存在
						  } */
						  //else{//Cookie存在，表示页面是被刷新的
						     //amq.removeListener(GUID,"quene://"+$("#realName").val());
						     //amq.addListener(GUID, "quene://"+$("#realName").val(), myHandler);
						   	 //alert("页面刷新" + GUID);
						   	 //sleep(2000);
						   	 //amq.addListener(GUID, "quene://"+$("#realName").val(), myHandler);
			/* 			  }
					 };
	    		}
		    } */
		    
		    
		    // 从cookie中 取出值
			/* function readCookie(name) {
				var cookieValue = "";
				var search = escape(name) ;
				if (document.cookie.length > 0) {
					offset = document.cookie.indexOf(search);
					if (offset != -1) {
						offset += search.length;
						end = document.cookie.indexOf(";", offset);
						if (end == -1) {
							end = document.cookie.length;
						}
						cookieValue = unescape(document.cookie.substring(offset, end));
						//alert("cookieValue======="+cookieValue);
					}
				}
				return cookieValue;
			} */
			
			// 将值 保存到 cookie中 
			/* function writeCookie(name, value, hours) {
				var expire = "";
				if (hours != null) {
					expire = new Date((new Date()).getTime() + hours * 3600000);
					expire = "; expires=" + expire.toGMTString();
				}
				document.cookie = name + "=" + escape(value) + expire;
			} */
			
			
			// 截取 字符串
			/* function splitStr(str) {
				if(str == null || str == ""){
					return;
				}
				var message = str.split("=");
				var msg = message[2].split("'");
				return msg[0];
			} */
			
			//writeCookie("myCookie", "我是徐邦启", 24);
			//alert("1111==="+splitStr(readCookie("徐邦启"))); 
			

			//messages = splitStr(readCookie(realName));
			
			// 首先加载，得到 缺寝登记中传来的 studentName，若不为空， 将值 作为 条件 传到 查询缺寝 记录中
			/*  if(messages != null && messages !="" && (messages != undefined || messages != "undefined")){
				alert("111111111111111111111111111==="+messages);
				$("#dg").datagrid('load',{
					s_name:messages
				});
			} */

		//});


		// 休眠 几秒
		function sleep(numberMillis) {
			var now = new Date();
			var exitTime = now.getTime() + numberMillis;
			while (true) {
				now = new Date();
				if (now.getTime() > exitTime)
					return;
			}
		}
		
		// 根据角色隐藏显示按钮
		$(function(){
			var loginFlag = $("#loginFlag").val();
			if(loginFlag == "student" || loginFlag== "vipStudent"){
				$("#daochu").hide();
				$("#ifOk").show();
			}else {
				$("#daochu").show();
				$("#ifOk").hide();
			}
		});
		
		// 点击 缺寝确认按钮 触发
		function checkOk(){
			var selectedRows=$("#dg").datagrid("getSelections");
			if(selectedRows.length!=1){
	    		$.messager.alert("系统提示","请选择一条要确认的数据！","warning");
	    		return;
	    	}
	    	var row=selectedRows[0];
	    	var student_name = row.student_name;
	    	if(student_name != "${sessionScope.realName}"){
	    		$.messager.alert("系统提示","非本人不可确认!","warning");
	    		return;
	    	}
	    	
	    	var student_ifOk = row.student_ifOk;
	    	if(student_ifOk == "是"){
	    		$.messager.alert("系统提示","请选择未确认记录确认！","warning");
	    		return;
	    	}
	    	
	    	var studentId = row.student_id;
	    	$.post("StudentManageAction!qryIfOfFlag.action?student_id="+studentId,null,function(result){
	    		if(result == "是"){
					$.messager.alert("系统提示","该缺寝信息已确认!","warning");
					return;
				}
				// 调用函数
				callBackBycheckOk(studentId);
	    	});
		}
		
		function callBackBycheckOk(studentId){
			$.post("StudentManageAction!uptStudentLack.action?student_id="+studentId,null,function(result){
				if(result.errorMsg){
					$.messager.alert("系统提示",result.errorMsg,"warning");
					return;
				}else{
					$.messager.alert("系统提示","您确认缺寝信息成功！");
					$("#dg").datagrid("reload");
				}
	    	});
		}
		
	</script>
  </head>
  
<body style="margin:5px">
	<input type="hidden" name="loginFlag" id="loginFlag" value="${sessionScope.loginFlag }"/>
	<input type="hidden" name="realName" id="realName" value="${sessionScope.realName}"/>
	<input type="hidden" name="GUID" id="GUID" value="${sessionScope.GUID}"/>
	<input type="hidden" name="tempValue" id="tempValue" value=""/>
    <table id="dg" title="缺寝记录" class="easyui-datagrid" style="width:900px;"
            url="StudentManageAction!lackList.action" 
            toolbar="#toolbar" pagination="true" pageList=[5,10,15]
            rownumbers="true" fitColumns="true" fit="true" >
        <thead>
            <tr>
            	<th field="student_id" checkbox="true" align="center">id</th>
            	<!-- <th field="student_userPass" width="20" align="center">密码</th>
            	<th field="student_lackFlag" width="5" align="center">是否缺寝标识（0 未缺寝）</th> -->
                <th field="student_name" width="50" align="center">姓名</th>
                <th field="student_sex" width="30" align="center">性别</th>
                <th field="student_institution" width="60" align="center">学院</th>
                <th field="student_major" width="90" align="center">专业</th>
                <th field="student_class" width="50" align="center">班级</th>
                <th field="student_building" width="40" align="center">楼宇</th>
                <th field="student_dorm" width="40" align="center">宿舍</th>
                <th field="student_lackBeginTime" width="120" align="center">缺寝开始时间</th>
                <th field="student_lackEndTime" width="120" align="center">缺寝结束时间</th>
                <th field="student_remark" width="200" align="center">原因</th>
                <th field="student_ifOk" width="50" align="center">是否确认</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
      <div style="float: left" id ="daochu">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="exportUser()">导出用户</a>
      </div>
      <div style="float: left" id ="ifOk">
      	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="checkOk()">缺寝确认</a>
      </div>
      <div>
  		<form id="condition" name="condition" action="StudentManageAction!export" method="post"> 
      		&nbsp;&nbsp;
      		查询：
      		<input type="text" id="s_name" name="s_name" placeholder="请输入要查询的姓名">
        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="btn" plain="true" onclick="searchUser()">搜索</a>
        </form>
  	  </div>
    </div>
  </body>
</html>
