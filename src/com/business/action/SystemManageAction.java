package com.business.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.junit.Test;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.business.bean.LoginLogVo;
import com.business.bean.Tab_Notice;
import com.business.ip.IPSeeker;
import com.business.services.I_NoticeManageService;
import com.business.services.I_StudentManageServices;
import com.business.services.I_SystemManageService;
import com.business.util.JedisUtil;
import com.business.util.ResponseUtil;
import com.business.util.Tools;
import com.opensymphony.xwork2.ActionSupport;

@Controller("systemAction")
@Scope("prototype")
public class SystemManageAction extends ActionSupport implements ServletRequestAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HttpServletRequest request=null;
	HttpSession session=null;
	@Resource 
	private I_SystemManageService systemService;
	
	@Resource
	private I_StudentManageServices studentService;
	
	private String flag;
	private String userName;
	private String userPass;
	private String GUID;
	private String inputOldPass;
	private String loginId;
	
	@Resource
	private I_NoticeManageService noticeService;
	private Tab_Notice notice = new Tab_Notice();
	
	JedisUtil jedis = new JedisUtil();
	
//	@Resource
//	private I_TempNoticeService tempNoticeService;
	
	/*覆盖基类的方法，进入action后首先执行这个方法*/
	@Override
	public void validate(){
		String arr[]={"","","","",""};
		String guid = null;
//		TempNoticeBean bean = null;
		try{
			// 作为唯一标识
			guid = UUID.randomUUID().toString();
			if(Tools.isEmpty(flag)){
				this.addFieldError("loginFlag","请选择角色后重新登录！");
				return;
			}
			//如果用户名或者密码都不为空
			if(!Tools.isEmpty(userName)&&!Tools.isEmpty(userPass)){
				arr = systemService.login(flag, userName, userPass);
				//System.out.println("arr=================="+arr);
				if(!Tools.isEmpty(arr) && arr.length>0){
					for(int i=0;i<arr.length;i++){
						if("".equals(arr[0])){
							this.addFieldError("loginFlag","用户名或密码输入错误！");
							return;
						}
					}
				}
				if("student".equals(flag)){			//如果是学生登录
					if("迁出".equals(arr[4])){
						this.addFieldError("loginFlag","该学生处于迁出状态，不能登录本系统！");
						return;
					}else if("未入住".equals(arr[4])){
						this.addFieldError("loginFlag","该学生处于未入住状态，请联系管理员入住！");
						return;
					}else if("入住".equals(arr[4])){
						session.setAttribute("loginId",arr[1]);				//将登录id保存在session中
						String student_headFlag = studentService.qryVIPStudentByUserName(userName);
						if(student_headFlag != null && !student_headFlag.equals("") && student_headFlag.equals("是")){
							session.setAttribute("loginFlag","vipStudent");		//将角色保存在session中
						}else {
							session.setAttribute("loginFlag",flag);				//将角色保存在session中
						}
						session.setAttribute("realName",arr[3]);
						session.setAttribute("GUID", guid);
						session.setAttribute("userName", userName);
					}
				}else {							//系统管理员   --- 楼宇管理员   登录
					//System.out.println("--------系统管理员---楼宇管理员---lohinId==="+arr[1]);
					session.setAttribute("loginId",arr[1]);
					session.setAttribute("loginFlag",flag);
					session.setAttribute("realName",arr[3]);
					session.setAttribute("GUID", guid);
					session.setAttribute("userName", userName);
				}
				
				if(flag != null && flag.equals("teacher")){
					// 查询 楼宇管理员 是否 有 未读 消息
					List<Tab_Notice> list = null;
					notice = new Tab_Notice();
					notice.setRec_userName(userName);
					notice.setRec_role(flag);
					list = noticeService.qryNotOkNotice(notice);
					StringBuffer person = new StringBuffer();
					StringBuffer msgType = new StringBuffer();
					StringBuffer contents = new StringBuffer();
					/*String resultPerson = null;
					String resultMsgType = null;*/
					
					if(list != null && list.size() > 0){
						for(Tab_Notice notice : list){
							person.append(notice.getSend_person()+ ",");
							msgType.append(notice.getMsg_type() + ",");
							contents.append(notice.getContent() + ";");
						}
						if(person.length() > 0){
							person = person.deleteCharAt(person.length() - 1);
						}
						if(msgType.length() > 0){
							msgType = msgType.deleteCharAt(msgType.length() - 1);
						}
						if(contents.length() > 0){
							contents = contents.deleteCharAt(contents.length() - 1);
						}
						/*String contentOne = null;
						if(contents != null){
							if(contents.length() < 8){
								contentOne = contents.substring(0, contents.length());
							}else {
								// 大于8个字符 就截取 前8个 字符
								contentOne = contents.substring(0, 8);
							}
						}*/
						
						/*// 调用 截取 字符串的方法
						resultPerson = this.getTwoName(person.toString());
						resultMsgType = this.getTwoName(msgType.toString());*/
						
						jedis.hsetValue("tab_sushe", "person", person.toString());
						jedis.hsetValue("tab_sushe", "msgType", msgType.toString());
						jedis.hsetValue("tab_sushe", "contentOne", contents.toString());
						jedis.hsetValue("tab_sushe", "NotNoticeCount", list.size()+"");
						
						// 保存到 session中
						/*session.setAttribute("person", resultPerson);
						session.setAttribute("msgType", resultMsgType);
						session.setAttribute("contentOne", contentOne);
						session.setAttribute("NotNoticeCount", list.size());*/
						
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	/**
	* @Title: getTwoName 
	* @Description: TODO(截取 带有 逗号的 字符串)
	* @author xbq 
	* @param @param names
	* @param @return
	* @return String
	* @throws
	 */
	/*private String getTwoName(String names){
		String result = null;
		if(names.contains(",")){
			String one = names.substring(0,names.indexOf(",")+1);
			String two = names.substring(names.indexOf(",")+1);
			String second = null;
			String sum = null;
			if(two.contains(",")){
				second = two.substring(0,names.indexOf(","));
				sum = one + second;
				if(one.contains(second)){
					sum = one;
				}
			}else {
				sum = one + two;
				if(one.contains(two)){
					sum = one ;
				}
			}
			result = sum + "[...]";
		}else {
			result = names;
		}
		return result;
	}*/
	
	/**
	 * 	登录
	 * */
	public String denglu(){
		try{
			String loginID=(String) session.getAttribute("loginId");
			String loginUserName=(String) session.getAttribute("realName");
			//添加登录日志
			this.addLoginLog(loginID,loginUserName);			
			//System.out.println("-----------denglu方法-------loginID-------===-="+loginID+"----loginUserName==="+loginUserName);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "denglu";
	}
	

	/**
	 * 	修改密码
	 * */
	public String updatePass(){
		try{
			// 先验证原密码是否正确，如果正确，则 修改 新密码，否则，return 
			boolean checkPass = systemService.checkPass(loginId, inputOldPass,flag);
			if(!checkPass){
				ResponseUtil.write(ServletActionContext.getResponse(),false);
				return null;
			}
			boolean updateFlag = systemService.updatePassWord(userPass, flag ,loginId);
			if(updateFlag){
				ResponseUtil.write(ServletActionContext.getResponse(),userPass);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 添加登录日志
	 */
	public void addLoginLog(String id,String name) throws Exception{
		LoginLogVo log=new LoginLogVo();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ip=getIpAddr();
		IPSeeker seeker = IPSeeker.getInstance();
		log.setUserId(Long.parseLong(id));
		log.setUserName(name);
		log.setLoginDate(format.format(new Date()));
		log.setLoginIP(ip);
		log.setIPaddress(seeker.getCountry(ip));
		try {
			systemService.insertLoginLog(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取IP地址
	 */
	public String getIpAddr() {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	//实现接口
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		this.session=request.getSession();
	}
	
	//set get方法
	public String getUserPass() {
		return userPass;
	}
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getGUID() {
		return GUID;
	}
	public void setGUID(String gUID) {
		GUID = gUID;
	}
	public String getInputOldPass() {
		return inputOldPass;
	}
	public void setInputOldPass(String inputOldPass) {
		this.inputOldPass = inputOldPass;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	
	@Test
	public void test(){
		String ss = "徐邦启,徐邦启,张三";
		ss = ss.replaceFirst("徐邦启", "");
		System.out.println(ss);
		
		if(ss.startsWith(",")){
			System.out.println("111111111111111");
		}
		
		ss = ss.substring(1);
		System.out.println("sss========"+ss);
	}
	
}
