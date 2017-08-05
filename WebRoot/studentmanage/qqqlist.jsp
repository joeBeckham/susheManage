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

	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.3/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.3/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.3/demo/demo.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.3/jquery.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.3/jquery-1.8.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>

	<script type="text/javascript">
		function myformatterStart(date){  
		     var y = date.getFullYear();  
            var m = date.getMonth()+1;  
            var d = date.getDate();  
            var h = date.getHours();  
            var min = date.getMinutes();  
            var sec = date.getSeconds();
            var time = y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+'-'+' '+(h<10?('0'+h):h)+':'+(min<10?('0'+min):min)+':'+(sec<10?('0'+sec):sec);
            return time; 
		}  
		
		function myformatterEnd(date){  
		     var y = date.getFullYear();  
            var m = date.getMonth()+1;  
            var d = date.getDate();  
            var h = date.getHours();  
            var min = date.getMinutes();  
            var sec = date.getSeconds();
            var time = y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+'-'+' '+(h<10?('0'+h):h)+':'+(min<10?('0'+min):min)+':'+(sec<10?('0'+sec):sec);
            return time; 
		}  
		          
		function myparser(s){  
		    if (!s) return new Date();  
		    var ss = (s.split('-')); 
		    var y = parseInt(ss[0],10);  
		    var m = parseInt(ss[1],10);  
		    var d = parseInt(ss[2],10);  
		    var time = ss[3];
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
		  
		//页面加载  
		$(function(){  
		    //设置时间  
		var curr_time = new Date();     
		 $("#txtBeginTime").datebox("setValue",myformatterStart(curr_time));  
		 $("#txtEndTime").datebox("setValue",myformatterEnd(curr_time));  
		 //获取时间  
		 /* var beginTime=$("#txtBeginTime").datebox("getValue");  
		var endTime=$("#txtEndTime").datebox("getValue");  */ 
		});  
		
			</script>
  </head>
  
<body style="margin:5px">
	<div id="dlg" class="easyui-dialog" style="width:420px;height:320px;padding:10px 20px"
           buttons="#dlg-buttons">
        <form id="fm" method="post">
        	<table cellspacing="10px;">
        		<tr>
        			<td>姓名：</td>
        			<td><input name="vo.student_name" id="student_name" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>性别：</td>
        			<td>
        				男：<input name="vo.student_sex" id="student_sex" type="radio" class="easyui-validatebox" checked="checked" required="true" value="男">
        				女：<input name="vo.student_sex" id="student_sex" type="radio" class="easyui-validatebox"  required="true" value="女">
        			</td>
        		</tr>
        		<tr>
        			<td>时间段：</td>
        			<td>
        				<input id="txtBeginTime" name="txtBeginTime" class="easyui-datetimebox" data-options="formatter:myformatterStart,parser:myparser"></input>至  
						<input id="txtEndTime" name="txtEndTime" class="easyui-datetimebox" data-options="formatter:myformatterEnd,parser:myparser"></input>  
        			</td>
        		</tr>
        	</table>
        </form>
    </div>


  </body>
</html>
