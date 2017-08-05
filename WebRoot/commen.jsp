<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'commen.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<script type="text/javascript" >
		function myBrowser(){
		    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
		    var isOpera = userAgent.indexOf("Opera") > -1;
		    if (isOpera) {
		        return "Opera"
		    }; //判断是否Opera浏览器
		    if (userAgent.indexOf("Firefox") > -1) {
		        return "FF";
		    } //判断是否Firefox浏览器
		    if (userAgent.indexOf("Chrome") > -1){
			  return "Chrome";
			 }
		    if (userAgent.indexOf("Safari") > -1) {
		        return "Safari";
		    } //判断是否Safari浏览器
		    if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
		        return "IE";
		    }; //判断是否IE浏览器
		}
		//以下是调用上面的函数
		var mb = myBrowser();
		if ("IE" == mb) {
		    alert("我是 IE");
		}
		if ("FF" == mb) {
		    alert("我是 Firefox");
		}
		if ("Chrome" == mb) {
		    alert("我是 Chrome");
		}
		if ("Opera" == mb) {
		    alert("我是 Opera");
		}
		if ("Safari" == mb) {
		    alert("我是 Safari");
		}
</script>

  </head>
  
  <script type="text/javascript" src="js/ifFirstOpen.js" charset="utf-8"></script>


<script type="text/javascript" src="Cookie.js"></script>
<script type="text/javascript" language="javascript">
 window.onload=function(){
  var ck=new Cookie("HasLoaded");  //每个页面的new Cookie名HasLoaded不能相同
  if(ck.Read()==null){//未加载过，Cookie内容为空
      alert("首次打开页面");
    
     //设置保存时间
     var dd = new Date();
     dd = new Date(dd.getYear() + 1900, dd.getMonth(), dd.getDate());
     dd.setDate(dd.getDate() + 365);
     ck.setExpiresTime(dd);

      ck.Write("true");  //设置Cookie。只要IE不关闭，Cookie就一直存在
  }
  else{//Cookie存在，表示页面是被刷新的
   alert("页面刷新");
  }
 };
</script>
  
</html>
