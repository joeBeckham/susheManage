<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
								<B>缺寝通知</B>
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
																				&nbsp;&nbsp;<b>${sessionScope.realName }</b> 同学，你有<font color="red">缺寝记录</font>，
																				<br><br>
																				&nbsp;&nbsp;请在【缺寝记录】中确认！！！
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
