<%@page import="com.business.util.Tools"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.business.util.JedisUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Popup.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery-1.8.0.min.js" charset="utf-8"></script>
	<embed width="0" height="0" loop="false" autostart="true" starttime="00:01" controls="ControlPanel" src="image/system.wav">
	<style type=text/css>
	td {
		font-size: 12px;
	}

	.style1 {
		font-size: 12px;
		color: #091f84;
	}
	
	.style1 a:link {
		font-size: 12px;
		color: #091f84;
		text-decoration: underline
	}
	
	.style1 a:visited {
		font-size: 12px;
		color: #091f84;
		text-decoration: underline
	}
	
	.style1 a:hover {
		font-size: 12px;
		color: #ff0000;
		text-decoration: underline
	}
	
	.style2 {
		font-size: 12px;
		color: #ffffff
	}
	
	.style2 a:link {
		font-size: 12px;
		color: #ffffff;
		text-decoration: none
	}
	
	.style2 a:visited {
		font-size: 12px;
		color: #ffffff;
		text-decoration: none
	}
	
	.style2 a:hover {
		font-size: 12px;
		color: #cfe7fc;
		text-decoration: none
	}
	
	.style3 {
		font-size: 12px;
		color: #091f84
	}
	
	.style3 a:link {
		font-size: 12px;
		color: #091f84;
		text-decoration: none
	}
	
	.style3 a:visited {
		font-size: 12px;
		color: #091f84;
		text-decoration: none
	}
	
	.style3 a:hover {
		font-size: 12px;
		color: #ff0000;
		text-decoration: none
	}
	
	.style4 {
		font-size: 12px;
		color: #0139d8
	}
	
	.style4 a:link {
		font-size: 12px;
		color: #0139d8;
		text-decoration: underline
	}
	
	.style4 a:visited {
		font-size: 12px;
		color: #0139d8;
		text-decoration: underline
	}
	
	.style4 a:hover {
		font-size: 12px;
		color: #ff0000;
		text-decoration: underline
	}
	
	.style5 {
		font-size: 3px
	}
	</style>
	
	<script language=javascript>
		function killerrors() {
			return true;
		}
		window.onerror = killerrors;
	</script>
	
	</head>
	<body leftmargin=0 topmargin=0>
		<table height=159 cellspacing=0 cellpadding=0 width=256 border=0>
			<tr>
				<td height=20>
					<table cellspacing=0 cellpadding=0 width="100%" border=0>
						<tr>
							<td width=44><img src="image/kk1.gif" width=44 height=20
								border="0">
							</td>
							<td class=style2 style="padding-top: 4px" width=193
								background=image/kk2.gif>
								<B>您有 
								<%
									JedisUtil jedis = new JedisUtil();
									String count = jedis.hgetValue("tab_sushe", "NotNoticeCount");
									out.print(count);
								 %>
								条短消息 ！</b>
							</td>
							<td width=19>
								<a href="javascript:void(0)" id="closepopup" target="_blank">
									<img src="image/kk3.gif" alt=关闭 width="19" height=20 border=0>
								</a>
							</td>
						</tr>
					</table></td>
			</tr>
			<tr>
				<td valign=bottom>
					<table cellspacing=0 cellpadding=0 width="100%" border=0>
						<tr>
							<td background=image/kk7.gif height=107>
								<table height=107 cellspacing=0 cellpadding=0 width="100%"
									border=0>
									<tr>
										<td width=3 background=image/kk8.gif></td>
										<td valign=top width=250>
											<table height=107 cellspacing=0 cellpadding=0 width="100%"
												border=0>
												<tr>
													<td valign=center>
														<table cellspacing=0 cellpadding=0 width="100%" border=0>
															<tr>
																<td width=13></td>
																<td width=143>
																	<table cellspacing=0 cellpadding=0 width="100%" border=0>
																		<tr>
																			<td width=3></td>
																			<td>
																				<table height=18 cellspacing=0 cellpadding=0
																					width="100%" border=0>
																					<tr>
																						<td class=style4>
																							<font color=#091f84>·</font>
																							发送人名：
																							<%
																								String name = jedis.hgetValue("tab_sushe", "person");
																								name = Tools.getTwoName(name,0,null);
																								out.print(name);
																							 %>
																						</td>
																					</tr>
																				</table>
																				<table height=18 cellspacing=0 cellpadding=0
																					width="100%" border=0>
																					<tr>
																						<td class=style4>
																							<font color=#091f84>·</font>
																							消息类型：
																							<%
																								String msgType = jedis.hgetValue("tab_sushe", "msgType");
																								msgType = Tools.getTwoName(msgType,0,null);
																								out.print(msgType);
																							 %>
																						</td>
																					</tr>
																				</table>
																				<table height=18 cellspacing=0 cellpadding=0
																					width="100%" border=0>
																					<tr>
																						<td class=style4>
																							<font color=#091f84>·</font>
																							消息内容：
																							<%
																								String contentOne = jedis.hgetValue("tab_sushe", "contentOne");
																								contentOne = Tools.getTwoName(null,1,contentOne);
																								out.print(contentOne);
																							 %>
																							[...]
																						</td>
																					</tr>
																				</table>
																			</td>
																		</tr>
																	</table>
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
										<td width=3 background=image/kk9.gif></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table height=32 cellspacing=0 cellpadding=0 width="100%"
									border=0>
									<tr>
										<td width=7><img height=32 src="image/kk5.gif" width=7>
										</td>
										<td background=image/kk4.gif>
											<table cellspacing=0 cellpadding=0 width="100%" border=0>
												<tr>
													<td class=style3 width=33 align="right">
														<a href="javascipt:void(0);" onclick="lookUp()">请在<b>消息处理</b>中查看</a>
													</td>
												</tr>
											</table>
										</td>
										<td width=7><img height=32 src="image/kk6.gif" width=7>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
