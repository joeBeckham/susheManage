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
    
    <title>校园宿舍管理系统</title>
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
	
	
  	<script language="JavaScript"> 

    //右上角的帮助菜单
    function showAbout(){
    	$.messager.show({
			title:"系统设计 V1.0",
			msg:"设计: 徐邦启<br/>邮箱: 734431825@qq.com",
			timeout:5000
		});
    }

    function reload(){
    	window.location.reload();
    }


    //左边的树形菜单
    $(function(){
		
		 var loginFlag=$("#loginFlag").val();
		 
		 if("system"==loginFlag){
		 	$("#showSystem").show();
			$("#showTeacher").hide();
			$("#showStudent").hide();
			$("#showVipStudent").hide();
		 }
		 
		 if("teacher"==loginFlag){
			$("#showSystem").hide();
			$("#showTeacher").show();
			$("#showStudent").hide();
			$("#showVipStudent").hide();
		} 
		 
		if("student"==loginFlag){
			$("#showSystem").hide();
			$("#showTeacher").hide();
			$("#showStudent").show();
			$("#showVipStudent").hide();
		}
		
		if("vipStudent"==loginFlag){
			$("#showSystem").hide();
			$("#showTeacher").hide();
			$("#showStudent").hide();
			$("#showVipStudent").show();
		}
		
		var treeStuDate=[{
				text:"学生基本信息",
				iconCls:"icon-basic",
				attributes:{
					url:"studentmanage/list.jsp"
				}
			},{
				text:"迁出记录",
				iconCls:"icon-out",
				attributes:{
					url:"studentmanage/outlist.jsp"
				}
			},{
				text:"缺寝记录",
				iconCls:"icon-queQin",
				attributes:{
				url:"studentmanage/lacklist.jsp"
			   }
			}	
		];
		  
		  var treeBuildDate=[{
			  text:"楼宇基本信息",
			  iconCls:"icon-homeBuilding",
			  attributes:{
				  url:"buildingrmanage/buildinglist.jsp"
			  }
		  	}
  		  ];

		  var treeTeacherDate=[{
			  text:"管理员基本信息",
			  iconCls:"icon-buildingManage",
			  attributes:{
				  url:"teachermanage/teacherlist.jsp"
			  }
		  	}
  		  ];

		  var treeDormDate=[{
			  text:"宿舍基本信息",
			  iconCls:"icon-dormBasic",
			  attributes:{
				  url:"dormmanage/dormlist.jsp"
			  }
		  	}
  		  ];

		  var treeLogDate=[{
			  text:"登录日志",
			  iconCls:"icon-log",
			  attributes:{
				  url:"loginlog/loglist.jsp"
			  }
		  	}
  		  ];

		  $("#studenttree").tree({
			 data:treeStuDate,
		     lines:false,
		     onClick:function(node){
		    	 if(node.attributes){
		    		 openTab(node.text,node.attributes.url);
		    	 }
		     }
		  });

		  $("#buildtree").tree({
				 data:treeBuildDate,
			     lines:false,
			     onClick:function(node){
			    	 if(node.attributes){
			    		 openTab(node.text,node.attributes.url);
			    	 }
			     }
		  });

		  $("#teachertree").tree({
				 data:treeTeacherDate,
			     lines:false,
			     onClick:function(node){
			    	 if(node.attributes){
			    		 openTab(node.text,node.attributes.url);
			    	 }
			     }
		  });

		  $("#dormtree").tree({
				 data:treeDormDate,
			     lines:false,
			     onClick:function(node){
			    	 if(node.attributes){
			    		 openTab(node.text,node.attributes.url);
			    	 }
			     }
		  });           

		  $("#logtree").tree({
				 data:treeLogDate,
			     lines:false,
			     onClick:function(node){
			    	 if(node.attributes){
			    		 openTab(node.text,node.attributes.url);
			    	 }
			     }
		  }); 
		  
		  // 楼宇管理员 显示的菜单
		  var treeStuDateTeacher=[{
				text:"学生基本信息",
				iconCls:"icon-basic",
				attributes:{
					url:"studentmanage/list.jsp"
				}
			},{
				text:"迁出记录",
				iconCls:"icon-out",
				attributes:{
					url:"studentmanage/outlist.jsp"
				}
			},{
				text:"缺寝记录",
				iconCls:"icon-queQin",
				attributes:{
				url:"studentmanage/lacklist.jsp"
			   }
			}
		];
		  
		  var treeDormDateTeacher=[{
			  text:"宿舍基本信息",
			  iconCls:"icon-dormBasic",
			  attributes:{
				  url:"dormmanage/dormlist.jsp"
			  }
		  	}
  		  ];
  		  
  		   var treeNoticeDateTeacher=[{
			  text:"消息处理",
			  iconCls:"icon-messageProcess",
			  attributes:{
				  url:"studentmanage/seeNotice.jsp"
			  }
		  	}
  		  ];

		  $("#studenttreeTeacher").tree({
			 data:treeStuDateTeacher,
		     lines:false,
		     onClick:function(node){
		    	 if(node.attributes){
		    		 openTab(node.text,node.attributes.url);
		    	 }
		     }
		  });


		  $("#dormtreeTeacher").tree({
				 data:treeDormDateTeacher,
			     lines:false,
			     onClick:function(node){
			    	 if(node.attributes){
			    		 openTab(node.text,node.attributes.url);
			    	 }
			     }
		  });  
		  
		  $("#noticetreeTeacher").tree({
				 data:treeNoticeDateTeacher,
			     lines:false,
			     onClick:function(node){
			    	 if(node.attributes){
			    		 openTab(node.text,node.attributes.url);
			    	 }
			     }
		  });          

		  
		  // 普通 学生 角色 显示的 菜单
		  var treeStuDateStudent=[
		    {
				text:"缺寝记录",
				iconCls:"icon-queQin",
				attributes:{
					url:"studentmanage/lacklist.jsp"
			    }
			}	
		];
		
		$("#studenttreeStudent").tree({
			 data:treeStuDateStudent,
		     lines:false,
		     onClick:function(node){
		    	 if(node.attributes){
		    		 openTab(node.text,node.attributes.url);
		    	 }
		     }
		  });
		  
		  var treeApplyDateTeacher=[{
				text:"学生申请",
				iconCls:"icon-studentApply1",
				attributes:{
					url:"studentmanage/changeApply.jsp"
				}
			}
  		  ];
		  
		  $("#applytreeStudent").tree({
			 data:treeApplyDateTeacher,
		     lines:false,
		     onClick:function(node){
		    	 if(node.attributes){
		    		 openTab(node.text,node.attributes.url);
		    	 }
		     }
		  });
		  
		  // 学生班级负责人显示的菜单
		  var treeVipStuDateStudent=[{
				text:"学生基本信息",
				iconCls:"icon-basic",
				attributes:{
					url:"studentmanage/list.jsp"
				}
			},
		    {
				text:"缺寝记录",
				iconCls:"icon-queQin",
				attributes:{
					url:"studentmanage/lacklist.jsp"
			    }
			}	
		];
		
		$("#studenttreeVIPStudent").tree({
			 data:treeVipStuDateStudent,
		     lines:false,
		     onClick:function(node){
		    	 if(node.attributes){
		    		 openTab(node.text,node.attributes.url);
		    	 }
		     }
		  });
		  
		  var treeApplyDateStudent=[{
				text:"宿舍调换申请",
				iconCls:"icon-studentApply1",
				attributes:{
					url:"studentmanage/changeApply.jsp"
				}
			}
  		  ];
		  
		  $("#applytreeVIPStudent").tree({
			 data:treeApplyDateStudent,
		     lines:false,
		     onClick:function(node){
		    	 if(node.attributes){
		    		 openTab(node.text,node.attributes.url);
		    	 }
		     }
		  });	  
		  
		  function openTab(text,url){
			  if($("#tabs").tabs("exists",text)){
				  $("#tabs").tabs("select",text);
			  }else{
				  var content="<iframe src="+url+" frameborder='0' scrolling='auto' style='width:100%;height:100%'></frame>";
				  $("#tabs").tabs("add",{
					  title:text,
					  content:content,
					  closable:true
				  });			  
			  }
		  }
	  });

	  
    $(function(){  

        //绑定tabs的右键菜单
        $("#tabs").tabs({
              onContextMenu : function (e, title) {
                  e.preventDefault();
                  $('#rcmenu').menu('show', {
                      left : e.pageX,
                      top : e.pageY
                 }).data("tabTitle", title);
              }
        });
             

     	// 刷新
    	$('#update').bind("click",function() {
    		var currTab = $('#tabs').tabs('getSelected');	//选中的选项卡对象 
    		var content = currTab.panel('options').content; //获取面板内容 
    		$('#tabs').tabs('update', {						//更新
    			tab : currTab,
    			options : {
    				content : content
    			}
    		});
    	});
    	
    	 //关闭当前标签页
        $("#closecur").bind("click",function(){
            var tab = $('#tabs').tabs('getSelected');
            var index = $('#tabs').tabs('getTabIndex',tab);
            $('#tabs').tabs('close',index);
        });
	        
	      //关闭所有标签页  
	      $("#closeall").bind("click",function(){  
	          var tablist = $('#tabs').tabs('tabs');  
	          for(var i=tablist.length-1;i>=1;i--){  
	              $('#tabs').tabs('close',i);  
	          }  
	      });  
	      //关闭其他页面（先关闭右侧，再关闭左侧）  
	      $("#closeother").bind("click",function(){  
	          var tablist = $('#tabs').tabs('tabs');  
	          var tab = $('#tabs').tabs('getSelected');  
	          var index = $('#tabs').tabs('getTabIndex',tab);  
	          for(var i=tablist.length-1;i>index;i--){  
	              $('#tabs').tabs('close',i);  
	          }  
	          var num = index-1;  
	          if(num < 1){  
	              return;  
	          }else{  
	              for(var i=num;i>=1;i--){  
	                  $('#tabs').tabs('close',i);  
	              }  
	              $("#tabs").tabs("select", 1);  
	          }  
	      }); 

	    //关闭当前标签页右侧标签页
	      $("#closeright").bind("click",function(){
	          var tablist = $('#tabs').tabs('tabs');
	          var tabs = $('#tabs').tabs('getSelected');
	          var index = $('#tabs').tabs('getTabIndex',tabs);
	          for(var i=tablist.length-1;i>index;i--){
	              $('#tabs').tabs('close',i);
	          }
	      });
	      
	      //关闭当前标签页左侧标签页
	      $("#closeleft").bind("click",function(){
	          var tabs = $('#tabs').tabs('getSelected');
	          var index = $('#tabs').tabs('getTabIndex',tabs);
	          var num = index-1;
	          for(var i=0;i<=num;i++){
	              $('#tabs').tabs('close',1);
	          }
	      });

	      /* var loginFlag=$("#loginFlag").val();
	      $.messager.show({
				title:"welcome",
				msg:"欢迎"+loginFlag+"角色，登陆成功！",
				timeout:3000
		  }); */
	  });

	  ///////////////////////////////////
	   //设置登录窗口
        function openPwd() {
            $('#w').window({
                title: '修改密码',
                width: 300,
                modal: true,
                shadow: true,
                closed: true,
                height: 190,
                resizable:false
            });
        }
        //关闭登录窗口
        function closePwd() {
            $('#w').window('close');
        }
        
        //修改密码
        function serverLogin() {
            var $newpass = $('#txtNewPass');
            var $rePass = $('#txtRePass');
            var inputOldPass = $("#inputOldPass").val();
            
            if(inputOldPass == ''){
            	$.messager.alert('系统提示', '请输入原密码！', 'warning');
                return false;
            }            
            if ($newpass.val() == '') {
            	$.messager.alert('系统提示', '请输入新密码！', 'warning');
                return false;
            }
            if ($rePass.val() == '') {
            	$.messager.alert('系统提示', '请在一次输入密码！', 'warning');
                return false;
            }

            if ($newpass.val() != $rePass.val()) {
            	$.messager.alert('系统提示', '两次密码不一至！请重新输入', 'warning');
                return false;
            }

            var loginFlag=$("#loginFlag").val();
            var loginId = $("#loginId").val();
            $.post("<%=basePath%>systemManageAction!updatePass.action?userPass="+ $newpass.val()+"&flag="+loginFlag+"&loginId="+loginId+"&inputOldPass="+inputOldPass, function(msg) {
            	if(msg == "false"){
            		$.messager.show({
	        			title:"系统提示",
	        			msg:"您的原密码输入错误！<br>请重新输入！",
	        			timeout:4000
	        		});
	        		$("#inputOldPass").val("");
	        		return;
            	}
            	$.messager.show({
        			title:"系统提示",
        			msg:"恭喜，密码修改成功！<br>您的新密码为：" + msg+"<br>5秒后跳转到登录页面！",
        			timeout:4000
        		});
                $newpass.val('');
                $rePass.val('');
                closePwd();
                setTimeout("go()",5000);
            });
        }
        

        function go(){
        	location.href = 'login.jsp';
        }

        $(function() {
        	/* setInterval(submitShow,2000);
        	
        	function submitShow(){
        		$.post("StudentManageAction!qryMessage.action",function(msg){
	        		$.messager.show({
						title:"学生申请通知：",
						msg:"1111111111111111111111",
						timeout:5000
					});
	        	});
        	} */
        	
        	$(".messager-body").window('close');
        	
            openPwd();
            $('#editpass').click(function() {
                $('#w').window('open');
            });
            $('#btnEp').click(function() {
                serverLogin();
            });
            
			$('#btnCancel').click(function(){closePwd();});

            $('#loginOut').click(function() {
                $.messager.confirm('系统提示', '您确定要退出本次登录吗？', function(r) {
                    if (r) {
                    	top.location.href = "login.jsp";
                    }
                });
            });
        });
	</script>  
	
	<!-- 角色 是 楼宇管理员的  才可以 弹出 此 消息 弹窗 -->
	<c:if test="${sessionScope.loginFlag == 'teacher'}">
		<!-- 消息弹框的js -->
		<script type="text/javascript" src='js/Popup.js'></script>
		<script type="text/javascript">
			//this.doit();
			 $(function(){
				$.post("StudentManageAction!qryNotOkNoticeListByTeacher.action?index="+Math.random()+"&rec_role=${sessionScope.loginFlag}"+"&rec_userName=${sessionScope.userName}",null,callback);
				function callback(data){
					if(data == 'true'){
						//neoneo();
						if (document.body == null) {		
							window.setTimeout(doit,500);
							return;
						}
						job();
					}
				}
			}); 
		</script>
	</c:if>
	<!-- 角色 是 学生的  才可以 弹出 此 消息 弹窗 -->
	<c:if test="${sessionScope.loginFlag == 'student'}">
		<script type="text/javascript" src='js/PopupStudent.js'></script>
		<script type="text/javascript">
			$(function(){
				// polling();
				var realName = $("#realName").val();
				$.post("StudentManageAction!qryIfOfFlag.action?index="+Math.random()+"&rec_userName="+realName,null,function(data){
					if(data == 'true'){
						if (document.body == null) {		
							window.setTimeout(doit,500);
							return;
						}
						job();
					}
				});
				
			});
			
			/* function polling(){
				synchronous();
				setInterval("synchronous()", 1000*60*3);
			}
			
			function synchronous(){
				var realName = $("#realName").val();
				$.post("StudentManageAction!qryIfOfFlag.action?index="+Math.random()+"&rec_userName="+realName,null,function(data){
					if(data == 'true'){
						if (document.body == null) {		
							window.setTimeout(doit,500);
							return;
						}
						job();
					}
				});
			} */
			/* $(function(){
				$.messager.show({
					title:"welcome",
					msg:"徐邦启 同学，你有缺寝记录， <br>请在【缺寝记录】中确认！！！",
					timeout:5000
				  });
			}); */
			//this.NeoneoStudent();
		</script>
	</c:if>
	
	<!-- 角色 是 班级学生负责人的  才可以 弹出 此 消息 弹窗 -->
	<c:if test="${sessionScope.loginFlag == 'vipStudent'}">
		<script type="text/javascript" src='js/PopupStudent.js'></script>
		<script type="text/javascript">
			$(function(){
				var realName = $("#realName").val();
				$.post("StudentManageAction!qryIfOfFlag.action?index="+Math.random()+"&rec_userName="+realName,null,function(data){
					if(data == 'true'){
						if (document.body == null) {		
							window.setTimeout(doit,500);
							return;
						}
						job();
					}
				});
			});
			//this.NeoneoStudent();
		</script>
	</c:if>
	
	<!-- 调用判断 书否第一个 打开页面的 js -->
	<%-- <script type="text/javascript" src="js/ifFirstOpen.js" charset="utf-8"></script>
	<script type="text/javascript">
		// 判断是首次打开页面 还是 刷新页面  	
		 window.onload=function(){
		 	var GUID = $("#GUID").val();
			var ck = new Cookie(GUID);  //每个页面的new Cookie名HasLoaded不能相同
			  if(ck.Read()==null){    //未加载过，Cookie内容为空
			      //alert("首次打开页面");
			      //设置保存时间
			      var dd = new Date();
			      dd = new Date(dd.getYear() + 1900, dd.getMonth(), dd.getDate());
			      dd.setDate(dd.getDate() + 365);
			      ck.setExpiresTime(dd);
			      ck.Write("true");  //设置Cookie。只要IE不关闭，Cookie就一直存在
			       $.messager.show({
					title:"welcome",
					msg:"欢迎您，登录本系统！",
					timeout:5000
				  });
			  } 
			  else{//Cookie存在，表示页面是被刷新的
			   	 //alert("页面刷新" + GUID);
			  }
		};    
	</script> --%>
	
	<style>  
		.footer {  
		    width: 100%;  
		    text-align: center;  
		    line-height: 35px;  
		}  
		  
		.top-bg {  
		    background-color: #d8e4fe;  
		    height: 80px;  
		}  
		
		.menuDiv ul{list-style-type:none;margin:0px;padding:2px;}
		.menuDiv ul li{ padding:0px;}
		.menuDiv ul li div{margin:2px 0px;padding-left:10px;padding-top:2px;}
	</style>   
 </head>  
  
 <body class="easyui-layout">  
 	<input type="hidden" name="loginFlag" id="loginFlag" value="${sessionScope.loginFlag }"/>
 	<input type="hidden" name="loginId" id="loginId" value="${sessionScope.loginId }"/>
 	<input type="hidden" name="realName" id="realName" value="${sessionScope.realName }"/>
 	<input type="hidden" name="GUID" id="GUID" value="${sessionScope.GUID}"/>
 	
 	<!--    最上面的 -->
    <div region="north" border="true" split="false" 
        style="overflow: hidden; height: 50px; background-color:#E0EDFF">  
        
        <div id="topbar" class="title">
		     <span class="footer" style="width:200px;padding-left:100px">  
		           	<font style="color:#95B8E7; font-size:27px;font-weight:bold">
		           		<img border="0" style="width:40px;height:40px;vertical-align:-15px;" src="image/dormLogo.png"/>
		           		校园宿舍管理系统
		           	</font>
		     </span> 
		     <span style="padding-left:500px">
		     	<b>欢迎您：</b>
		     		${sessionScope.realName}
		     		<c:if test="${sessionScope.loginFlag=='system'}">（系统管理员）</c:if>
		     		<c:if test="${sessionScope.loginFlag=='teacher'}">（楼宇管理员）</c:if>
		     		<c:if test="${sessionScope.loginFlag=='student'}">（学生）</c:if>
		     		<c:if test="${sessionScope.loginFlag=='vipStudent'}">（班级学生负责人）</c:if>
		     </span>
	        <span id="topbar-logon" style="width:180px;float:right;padding-top:8px">
				<a id="loginOut" icon="icon-remove" class="easyui-linkbutton" plain="true">注销</a>
				<a icon="icon-help" href="#" class="easyui-menubutton" menu="#topbar-menu" >管理</a>
				<div id="topbar-menu" style="width:150px;">
				    <div icon="" id="editpass">修改密码</div>
				    <div class="menu-sep"></div>
				    <div data-options="iconCls:'icon-reload'" onclick="reload()">刷新系统缓存</div>
				    <div class="menu-sep"></div>
				    <div data-options="iconCls:'icon-help'" onclick="showAbout()">关于</div>
				</div>
			</span>
		</div>
    </div>  
  
  	<!-- below 最下面的 -->
    <div region="south" border="true" split="false"  
        style="overflow: hidden; height: 40px;">  
        <div class="footer">  
           © 版权所有：<a href="http://www.hnist.cn/" target="_blank">湖南理工学院</a>&nbsp; 徐邦启  &nbsp; QQ:734431825 
        </div>  
    </div>  
  
  
  	<!-- left 最左边的 -->
    <div region="west" split="false" title="<center>导航菜单</center>" style="width: 200px">  
    	<!-- 系统管理员div -->
		<div id="showSystem" class="easyui-accordion"  fit="true">   
		    <div  data-options="iconCls:'icon-student'" title='学生管理' class="menuDiv"  style="overflow:auto;"> 
		    	<ul class="easyui-tree" id="studenttree"></ul>  
		    </div>   
		    <div  data-options="iconCls:'icon-building'" title='楼宇管理' class="menuDiv">   
		        <ul class="easyui-tree" id="buildtree"></ul>    
		    </div>
		    <div data-options="iconCls:'icon-building_manager'" title='楼宇管理员管理' class="menuDiv">   
		        <ul class="easyui-tree" id="teachertree"></ul>    
		    </div> 
		    <div  data-options="iconCls:'icon-dorm'" title='宿舍管理'  class="menuDiv">   
		        <ul class="easyui-tree" id="dormtree"></ul>    
		    </div> 
		    <div  data-options="iconCls:'icon-reload'" title='日志查看' class="menuDiv">   
		        <ul class="easyui-tree" id="logtree"></ul>    
		    </div>    
		</div> 
		
		<!-- 楼宇管理员 div -->
		<div id="showTeacher" class="easyui-accordion"  fit="true">   
		    <div  data-options="iconCls:'icon-student'" title='学生管理'  class="menuDiv"  style="overflow:auto;"> 
		    	<ul class="easyui-tree" id="studenttreeTeacher"></ul>  
		    </div>   
		    <div  data-options="iconCls:'icon-dorm'" title='宿舍管理'  class="menuDiv">   
		        <ul class="easyui-tree" id="dormtreeTeacher"></ul>    
		    </div> 
		    <div  data-options="iconCls:'icon-messageBig'" title='消息处理'  class="menuDiv">   
		        <ul class="easyui-tree" id="noticetreeTeacher"></ul>    
		    </div>
		</div> 
		
		<!-- 普通学生 div -->
		<div id="showStudent" class="easyui-accordion"  fit="true">   
		    <div  data-options="iconCls:'icon-student'" title='学生管理'  class="menuDiv"  style="overflow:auto;"> 
		    	<ul class="easyui-tree" id="studenttreeStudent"></ul>  
		    </div>  
		    <div  data-options="iconCls:'icon-student_apply'" title='申请登记' class="menuDiv">   
		        <ul class="easyui-tree" id="applytreeStudent"></ul>    
		    </div> 
		</div>  
		
		<!-- 学生班级负责人 div -->
		<div id="showVipStudent" class="easyui-accordion"  fit="true">   
		    <div  data-options="iconCls:'icon-student'" title='学生管理'  class="menuDiv"  style="overflow:auto;"> 
		    	<ul class="easyui-tree" id="studenttreeVIPStudent"></ul>  
		    </div>   
		    <div  data-options="iconCls:'icon-student_apply'" title='申请登记' class="menuDiv">   
		        <ul class="easyui-tree" id="applytreeVIPStudent"></ul>    
		    </div>
		</div> 
    </div>  
   
  	
  	<!--  正文部分 -->
    <div id="mainPanle" region="center" style="overflow: hidden;">  
        <div id="tabs" class="easyui-tabs"  fit="true" >  
            <div title="欢迎使用" style="padding: 5px;" > <!--  style="background-image: url(image/welcome.gif)" -->
            	<div style="border:2px #95B8E7 solid;height:525px">
            		<center style="font-size:30px;">欢迎使用校园宿舍管理系统<br></center>
	            	<table border="0" cellspacing="0" cellpadding="0" width="1104">
	            		<tr>
			                <td><img src="image/a_03.jpg" width="740" height="443"></td>
			                <td><img src="image/a_04.jpg" width="404" height="443"></td>
			              <!--   <td><img src="image/a_05.jpg" width="320" height="243"></td> -->
			            </tr>
	            	</table>
            	</div>
            </div>  
        </div>  
    </div>  
    
    <!-- tab 的点击事件 -->
	  <div id="rcmenu" class="easyui-menu">
	  	<div id="update">刷新</div>
	  	<div class="menu-sep"></div>  
	  	<div data-options="iconCls:'icon-cancel'" id="closecur">关闭当前</div>
	    <div id="closeother">关闭其他</div> 
	    <div id="closeall">关闭全部</div> 
	    <div class="menu-sep"></div>
    	<div id="closeright">关闭右侧标签页</div>
    	<div id="closeleft">关闭左侧标签页</div>
	  </div>
    
    
    <!--修改密码窗口-->
    <div id="w" class="easyui-window"  title="修改密码" collapsible="false" minimizable="false"
        maximizable="false" icon="icon-save"  style="width: 300px; height: 480px; padding: 5px;
        background: #fafafa;">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 5px; height: 450px; background: #fff; border: 1px solid #ccc;">
                <table cellpadding=3>
                    <tr>
                        <td>原密码：</td>
                        <td><input id="inputOldPass" type="Password"/></td>
                    </tr>
                    <tr>
                        <td>新密码：</td>
                        <td><input id="txtNewPass" type="Password"/></td>
                    </tr>
                    <tr>
                        <td>确认密码：</td>
                        <td><input id="txtRePass" type="Password"/></td>
                    </tr>
                </table>
            </div>
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >确定</a> 
                <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
            </div>
        </div>
    </div>
 </body>  
 </html>  
