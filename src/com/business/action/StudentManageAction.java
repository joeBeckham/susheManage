package com.business.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;

import com.business.bean.BuildingVo;
import com.business.bean.DormVo;
import com.business.bean.FixVo;
import com.business.bean.StudentVo;
import com.business.bean.Tab_Notice;
import com.business.services.I_BuildingManageService;
import com.business.services.I_DormManageService;
import com.business.services.I_NoticeManageService;
import com.business.services.I_StudentManageServices;
import com.business.util.ExcelUtil;
import com.business.util.JedisUtil;
import com.business.util.JsonUtil;
import com.business.util.PageBean;
import com.business.util.ResponseUtil;
import com.business.util.Tools;
import com.opensymphony.xwork2.ActionSupport;
/**
 * @ClassName: StudentManageAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author xbq
 * @date 2016-4-26 下午4:03:19 
 *
 */
@Controller("studentAction")
@Scope("prototype")
public class StudentManageAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	
	private static final long serialVersionUID = 1L;
	HttpServletRequest request=null;
	HttpServletResponse response=null;
	HttpSession session=null;
	@Resource I_StudentManageServices studentService=null;
	@Resource I_BuildingManageService buildingService=null;
	@Resource I_DormManageService 	  dormService=null;
	
	private StudentVo vo=new StudentVo();
	private String student_id;
	private String page;
	private String rows;
	
	private String delIds;
	private String s_name;
	private String s_state;
	private String s_institution;
	private String s_class;
	private Integer student_lackFlag;
	private Integer lackFlag;   // 标识，用于查询缺寝记录的
	
	List<?> allList=null;
	private String building_id;
	private String dorm_id;
	private String student_userName;
	private String out_data;
	
	private String student_building;
	private String student_dorm;
	
	private FixVo fixVo=new FixVo();
	
	@Resource
	protected JmsTemplate jmsTemplateNotice;//用于推送消息
	private String realName;
	
	private String sessionid;
	private String msg;
	private String type;
	
	private String send_userName;
	private String send_person;
	private String rec_userName;
	private String rec_role;
	private String see_state;
	private String guid;
	
	private String out_date;
	private String student_remark;
	
	@Resource
	private I_NoticeManageService noticeService;
	private Tab_Notice notice = new Tab_Notice();
	
	JedisUtil jedis = new JedisUtil();
	
	private String student_name;
	
	
	/**
	 * 	用来查询List
	 * */
	public String list() throws Exception{
		try{
			PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
			
			JSONObject result=new JSONObject();
			vo.setStudent_name(s_name);
			if(s_state != null && Integer.parseInt(s_state) == 1){
				vo.setStudent_state("入住");
			}else if(s_state != null && Integer.parseInt(s_state) == 2){
				vo.setStudent_state("未入住");
			}else if(s_state != null && Integer.parseInt(s_state) == 3){
				vo.setStudent_state("迁出");
			}else {
				vo.setStudent_state(null);
			}
			vo.setStudent_institution(s_institution);
			vo.setStudent_class(s_class);
			
			vo.setStudent_lackFlag(student_lackFlag == null ? 0 : student_lackFlag);
			// 缺寝记录 查询的 应该是 入住寝室的 学生
			if(lackFlag != null && lackFlag == 1){
				vo.setStudent_state("入住");
			}
			
			JSONArray jsonArray=JsonUtil.formatRsToJsonArray(studentService.studentList(vo,pageBean));
			int total=studentService.StudentCount();
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 	根据得到的id，判断其是否为空。若为空，则执行增加操作，反之，执行修改
	 * */
	public String save() throws Exception{
		boolean flag=false;
		JSONObject result=new JSONObject();
		try{
			if(!Tools.isEmpty(student_id)){
				vo.setStudent_id(Long.parseLong(student_id));
			}
			if(!Tools.isEmpty(student_id)){
				flag=studentService.updateStudentVo(vo);
			}else{
				flag=studentService.addStudent(vo);
			}
			if(flag){
				result.put("success","true");
			}else{
				result.put("success","true");
				result.put("errorMsg","保存失败！！");
			}
		}catch(Exception ex){
			String msg = ex.getCause().getMessage() ;
			if(msg != null && msg.contains("org.hibernate.exception.ConstraintViolationException")){
				result.put("success", "true");
				result.put("errorMsg", "该学生学号已存在，请核实后重新录入！");
			}
		} finally{
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}
		return null;
	}
	
	/**
	 * 查询    缺寝  记录 
	 * @return
	 * @throws Exception
	 */
	public String lackList() throws Exception{
		try{
			PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
			
			JSONObject result=new JSONObject();
			vo.setStudent_name(s_name);
			JSONArray jsonArray=JsonUtil.formatRsToJsonArray(studentService.studentLackList(vo,pageBean));
			int total=studentService.StudentLackCount();
			result.put("rows", jsonArray);
			result.put("total", total);
			
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 学生基本情况   中用到   的  缺寝录入  操作
	 * @return
	 * @throws Exception
	 */
	public String saveLack() throws Exception{
		boolean flag=false;
		//String json = null;
		try{
			// 如果缺寝标识 =1
			if(lackFlag != null && lackFlag.intValue() == 1){
				vo.setStudent_lackFlag(lackFlag);
			}
			flag=studentService.addStudentLack(vo);
			
			JSONObject result=new JSONObject();
			if(flag){
				result.put("success","true");
				/*MqUtil mq = new MqUtil();
				realName = vo.getStudent_name();
				
				json = realName + "同学，你有<a href='studentmanage/lacklist.jsp?studentName="+ realName + "'>缺寝记录</a>，请查看！";
				
				// 对字符串中 进行编码。调用 编码 函数。 不这样子的话 穿到  前台 汉字会变为 问号
				json = EscapeUnescape.escape(json);
				
				Cookie cookie = new Cookie(EscapeUnescape.escape(realName),json); 
				cookie.setMaxAge(30*24*3600); 
				cookie.setPath("/"); 
				response.addCookie(cookie);
				
				mq.sendTopicMsg(realName == null ? "" : realName , json , jmsTemplateNotice);*/
			}else{
				result.put("success","true");
				result.put("errorMsg","保存失败！！");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	* @Title: uptStudentLack 
	* @Description: TODO(回写缺寝表中的是否确认标识)
	* @author xbq 
	* @date 2016-5-6 下午11:06:03
	* @param @return
	* @return String
	* @throws
	 */
	public String uptStudentLack(){
		boolean flag=false;
		JSONObject result=new JSONObject();
		try{
			if(student_id != null){
				flag = studentService.uptStudentLackOkFlag(student_id);
			}
			if(flag){
				result.put("success","true");
			}else{
				result.put("success","true");
				result.put("errorMsg","确认缺寝信息失败！！");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception ex){
			ex.printStackTrace();
		} 
		return null;
	}
	
	// 查询student_lack 表中的  是否确认 标识
	public String qryIfOfFlag(){
		List<String> list = null;
		String trueFlag = null;
		try {
			if(rec_userName != null){
				list = studentService.qryIfOfFlag(rec_userName);
				if(list == null){
					list = new ArrayList<String>();
				}
				for(String str : list){
					if(str != null && !str.equals("是")){
						trueFlag = "true";
					}
				}
				ResponseUtil.write(ServletActionContext.getResponse(), trueFlag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 	删除一条或者多条记录
	 * */
	public String delete() throws Exception{
		boolean flag=false;
		try{
			JSONObject result=new JSONObject();
			String str[]=delIds.split(",");
			int delNums = 0;
			if(str != null){
				delNums=str.length;
				if(str.length>1){
					for(int i=0;i<delNums;i++){
						flag=studentService.deleteStudentAndStudentDorm(Long.parseLong(str[i]));
					}
				}else{
					flag=studentService.deleteStudentAndStudentDorm(Long.parseLong(delIds));
				}
			}
			if(flag){
				result.put("success", "true");
				result.put("delNums", delNums);
			}else{
				result.put("errorMsg", "Sorry！删除失败 ！");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	* @Title: export 
	* @Description: TODO(用模版导出查询后的数据)
	* @author xbq 
	* @date 2016-5-18 下午8:06:48
	* @param @return
	* @param @throws Exception
	* @return String
	* @throws
	 */
	public String export() throws Exception{
		try{
			Workbook wb=new HSSFWorkbook();
			if(null==vo){
				vo=new StudentVo();
			}
			vo.setStudent_name(s_name);
			if(s_state != null && Integer.parseInt(s_state) == 1){
				vo.setStudent_state("入住");
			}else if(s_state != null && Integer.parseInt(s_state) == 2){
				vo.setStudent_state("未入住");
			}else if(s_state != null && Integer.parseInt(s_state) == 3){
				vo.setStudent_state("迁出");
			}else {
				vo.setStudent_state(null);
			}
			vo.setStudent_institution(s_institution);
			vo.setStudent_class(s_class);
			
			ResultSet rs = studentService.studentList(vo, null);
			wb=ExcelUtil.fillExcelDataWithTemplate(rs, "studentExporTemplate.xls");
			ResponseUtil.export(ServletActionContext.getResponse(), wb, "利用模版导出excel.xls");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	* @Title: saveMessgae 
	* @Description: TODO(发送消息，保存到消息表中)
	* @author xbq 
	* @param @return
	* @return String
	* @throws
	 */
	public String saveMessgae(){
		boolean flag = false;
		JSONObject result=new JSONObject();
		try {
			if(notice.getMsg_type() != null){
				if(notice.getMsg_type().equals("1")){
					notice.setMsg_type("宿舍调换");
				}else if(notice.getMsg_type().equals("2")){
					notice.setMsg_type("迁出申请");
				}
			}
			flag = noticeService.addNotice(notice);
			if(flag){
				result.put("success","true");
			}else{
				result.put("success","true");
				result.put("errorMsg","发送失败！！");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	* @Title: qryMessage 
	* @Description: TODO(学生 角色 登录 ---- 查询 本人 发送的消息)
	* @author xbq 
	* @param @return
	* @return String
	* @throws
	 */
	public String qryMessage(){
		try {
			PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
			JSONObject result = new JSONObject();
			if(send_userName != null && !send_userName.equals("")){
				notice.setSend_userName(send_userName);
			}
			if(send_person != null && !send_person.equals("")){
				notice.setSend_person(send_person);
			}
			JSONArray jsonArray = JsonUtil.formatRsToJsonArray(noticeService.qryNotNotice(notice, pageBean));
			int total = noticeService.NoticeCount();
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	* @Title: qryNotSeeNoticeListByTeacher 
	* @Description: TODO(查询 没有处理的 消息---楼宇管理员角色  消息处理 页面 用到)
	* @author xbq 
	* @date 2016-5-5 下午2:12:38
	* @param @return
	* @return String
	* @throws
	 */
	public String qryNotSeeNoticeListByTeacher(){
		try {
			PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
			JSONObject result = new JSONObject();
			if(rec_userName != null && !rec_userName.equals("")){
				notice.setRec_userName(rec_userName);
			}
			if(rec_role != null && !rec_role.equals("")){
				notice.setRec_role(rec_role);
			}
			if(see_state != null && !see_state.equals("")){
				notice.setSee_state(see_state);
			}else {
				notice.setSee_state("否");
			}
			if(send_person != null && !send_person.equals("")){
				notice.setSend_person(send_person);
			}
			JSONArray jsonArray = JsonUtil.formatRsToJsonArray(noticeService.qryNotNotice(notice, pageBean));
			int total = noticeService.NoticeCount();
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	* @Title: qryNotOkNoticeListByTeacher 
	* @Description: TODO(楼宇登录 到 主界面 后 会加载 回调 此方法)
	* @author xbq 
	* @param @return
	* @return String
	* @throws
	 */
	public String qryNotOkNoticeListByTeacher(){
		List<Tab_Notice> list = null;
		try {
			notice = new Tab_Notice();
			notice.setRec_role(rec_role);
			notice.setRec_userName(rec_userName);
			list = noticeService.qryNotOkNotice(notice);
			if(list != null && list.size() > 0){
				ResponseUtil.write(ServletActionContext.getResponse(),"true");
			} else {
				ResponseUtil.write(ServletActionContext.getResponse(),"false");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 	查询出所有的building列表   seenotice.jsp 中用到
	 * */
	public String queryAllBuilding_changeDorm(){
		try{
			List<BuildingVo> buildingList = buildingService.queryAllList();
			
			String html="<select id=\"building_id\" name=\"fixVo.building_id\" style=\"width:200px;font-size:12\" class=\"easyui-combobox\" onchange=\"changeBuilding_changeDorm(this.options[this.selectedIndex].value)\">";
			html+="<option value=\"\" selected=\"selected\" class=\"easyui-combobox\" style=\"width:169px;\">====全部====</option>";
			BuildingVo bVo = null;
			if(buildingList!=null&buildingList.size()>0){
				for(int i=0;i<buildingList.size();i++){
					bVo=buildingList.get(i);
					html+="<option value='"+bVo.getBuilding_id()+"'>"+bVo.getBuilding_name()+"</option>";
				}
			}
			html+="</select>";
			ResponseUtil.write(ServletActionContext.getResponse(),html);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 	根据buildingId查询出对应的dorm列表来
	 * */
	public String queryDormByBuilding_changeDorm(){
		try{
			System.out.println("building_id======"+building_id);
			List<DormVo> list=dormService.queryDormByBuilding(Long.parseLong(building_id));
			String html="<select id=\"dorm_id\" name=\"fixVo.dorm_id\" class=\"easyui-combobox\" style=\"width:200\"";
			html+="<option value=\"\">====全部====</option>";
			DormVo dVo=null;
			if(list != null && list.size()>0){
				for(int i=0;i<list.size();i++){
					dVo = list.get(i);
					html+="<option value=\""+dVo.getDorm_id()+"\">"+dVo.getDorm_name()+"</option>";
				}
			}
			html+="</select>";
			ResponseUtil.write(ServletActionContext.getResponse(),html);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 	查询出所有的building列表
	 * */
	public String queryAllBuilding(){
		try{
			List<BuildingVo> buildingList=buildingService.queryAllList();
			
			String html="<select id=\"building_id\" name=\"building_id\" style=\"width:170\" class=\"easyui-combobox\" onchange=\"changeBuilding(this.options[this.selectedIndex].value)\">";
			html+="<option value=\"\" selected=\"selected\">==全部==</option>";
			BuildingVo bVo=null;
			if(buildingList!=null&buildingList.size()>0){
				for(int i=0;i<buildingList.size();i++){
					bVo=buildingList.get(i);
					html+="<option value='"+bVo.getBuilding_id()+"'>"+bVo.getBuilding_name()+"</option>";
				}
			}
			html+="</select>";
			ResponseUtil.write(ServletActionContext.getResponse(),html);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "queryAllBuilding";
	}
	
	/**
	 * 	查询出所有的building列表  （此方法在dormlist中用到）
	 * */
	public String queryAllBuilding1(){
		try{
			List<BuildingVo> buildingList=buildingService.queryAllList();
			
			String html="<select id=\"dorm_building\" name=\"vo.dorm_building\" style=\"width:150\" class=\"easyui-combobox\" required=\"true\">";
			html+="<option value=\"\" selected=\"selected\">==全部楼宇==</option>";
			BuildingVo bVo=null;
			if(buildingList!=null&buildingList.size()>0){
				for(int i=0;i<buildingList.size();i++){
					bVo=buildingList.get(i);
					html+="<option value='"+bVo.getBuilding_id()+"'>"+bVo.getBuilding_name()+"</option>";
				}
			}
			html+="</select>";
			ResponseUtil.write(ServletActionContext.getResponse(),html);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "queryAllBuilding";
	}
	
	/**
	 * 	查询出所有的building列表  （此方法在dormlist中用到）
	 * */
	public String queryAllBuilding2(){
		try{
			List<BuildingVo> buildingList=buildingService.queryAllList();
			
			String html="<select id=\"dorm_building1\" name=\"dorm_building1\" style=\"width:80\" class=\"easyui-combobox\">";
			html+="<option value=\"\" selected=\"selected\">==楼宇==</option>";
			BuildingVo bVo=null;
			if(buildingList!=null&buildingList.size()>0){
				for(int i=0;i<buildingList.size();i++){
					bVo=buildingList.get(i);
					html+="<option value='"+bVo.getBuilding_id()+"'>"+bVo.getBuilding_name()+"</option>";
				}
			}
			html+="</select>";
			ResponseUtil.write(ServletActionContext.getResponse(),html);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "queryAllBuilding";
	}
	
	/**
	 * 	根据buildingId查询出对应的dorm列表来
	 * */
	public String queryDormByBuilding(){
		try{
			System.out.println("building_id======"+building_id);
			List<DormVo> list=dormService.queryDormByBuilding(Long.parseLong(building_id));
			String html="<select id=\"dorm_id\" name=\"fixVo.dorm_id\" class=\"easyui-combobox\" style=\"width:170\"";
			html+="<option value=\"\">==全部==</option>";
			DormVo dVo=null;
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					dVo=list.get(i);
					html+="<option value=\""+dVo.getDorm_id()+"\">"+dVo.getDorm_name()+"</option>";
				}
			}
			html+="</select>";
			ResponseUtil.write(ServletActionContext.getResponse(),html);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "queryDormByBuilding";
	}
	
	/**
	 * 	先查询学生的List，然后进行入住登记
	 * */
	public String inDorm() throws Exception{
		boolean flag=false;
		String student_id="";
		try{
			JSONObject result=new JSONObject();
			
			//如果页面不输入东西，用户直接点击确定入住，就会进入if
			if(Tools.isEmpty(building_id)){
				result.put("errorMsg","请先选择楼宇！");
				ResponseUtil.write(ServletActionContext.getResponse(), result);
				return null;
			}
			if(Tools.isEmpty(dorm_id)){
				result.put("errorMsg","请先选择或手动填写宿舍！");
				ResponseUtil.write(ServletActionContext.getResponse(), result);
				return null;
			}
			if(Tools.isEmpty(student_userName)){
				result.put("errorMsg","请先填写学生学号！");
				ResponseUtil.write(ServletActionContext.getResponse(), result);
				return null;
			}
			
			StudentVo vo1=new StudentVo();
			vo1.setStudent_userName(student_userName);
			/*根据学号查询出全部的List，并看看是否入住*/
			List<?> stuList=studentService.StudentList(vo1);
			
			/*根据学号查询出其对应的id，用于入住操作*/
			if(!Tools.isEmpty(stuList)&&stuList.size()>0){		//说明已经入住
				result.put("errorMsg","该学生已入住！不能重复入住！");
			}else{
				List<?> student_Idlist=studentService.queryStudent_Id(student_userName);
				if(!(student_Idlist.size()>0)){
					result.put("errorMsg","学生学号不存在，请重新输入！");
				}else{
					for(int i=0;i<student_Idlist.size();i++){
						student_id=student_Idlist.get(i).toString();
					}
					
					if(!"".equals(student_id)){
						//得到带有state的vo
						StudentVo state_vo=studentService.getState(Long.parseLong(student_id));
						if("迁出".equals(state_vo.getStudent_state())){
							result.put("errorMsg","该学生处于已迁出状态，不能入住！");
						}else{
							flag=studentService.ifDorm(Long.parseLong(dorm_id));
							if(flag){
								flag=studentService.inDorm(Long.parseLong(student_id),Long.parseLong(dorm_id),Long.parseLong(building_id),student_building,student_dorm);
								if(flag){
									result.put("success","true");
								}else{
									result.put("errorMsg","学生入住失败！");
								}
							}else{
								result.put("errorMsg","该宿舍人数已满，请选择其他宿舍入住！");
							}
						}
					}
				}
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据学号查询出符合条件的IDList列表，然后得到对应的状态：迁出，未入住，如果不是这两个状态则返回success，
	 * 在页面再进入queryChangeVo()方法
	 * */
	public String queryChangeVo() throws Exception {
		try{
			JSONObject result=new JSONObject();
			if(Tools.isEmpty(student_userName)){
				result.put("errorMsg","请先填写学生学号！");
				ResponseUtil.write(ServletActionContext.getResponse(), result);
				return null;
			}
			List<?> student_Idlist=studentService.queryStudent_Id(student_userName);
			if(!(student_Idlist.size()>0)){
				result.put("errorMsg","学生学号不存在，请重新输入！");
			}else{
				StudentVo state_vo=studentService.getState(Long.parseLong(String.valueOf(student_Idlist.get(0))));
				String state=state_vo.getStudent_state();
				if("迁出".equals(state)){
					result.put("errorMsg","该生处于已迁出状态，不能进行寝室调换！");
				}else if("未入住".equals(state)) {
					result.put("errorMsg","该生处于未入住状态，不能进行寝室调换！");
				}else{
					result.put("success","true");
				}
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	* @Title: qryStudentChangeDorm 
	* @Description: TODO(点击 处理消息 按钮 会 调用 这个方法)
	* @author xbq 
	* @date 2016-5-5 下午4:18:15
	* @param @return
	* @return String
	* @throws
	 */
	public String qryStudentChangeDorm(){
		try{
			
			session.setAttribute("uniqueGUID", guid);
			
			StudentVo vo1 = new StudentVo();
			vo1.setStudent_userName(student_userName);
			/* 根据学号查询出全部的List，并看看是否入住 */
			allList = studentService.StudentList(vo1);

			StudentVo s = null;
			DormVo d = null;
			BuildingVo b = null;

			for (int i = 0; i < 1; i++) {
				s = (StudentVo) allList.get(i);
				System.out.println(s);
			}
			fixVo.setStudent_id(s.getStudent_id());
			fixVo.setStudent_userName(s.getStudent_userName());
			fixVo.setStudent_name(s.getStudent_name());
			fixVo.setStudent_sex(s.getStudent_sex());

			for (int i = 1; i < 2; i++) {
				d = (DormVo) allList.get(i);
			}
			fixVo.setDorm_id(d.getDorm_id());
			fixVo.setDorm_name(d.getDorm_name());

			for (int i = 2; i < 3; i++) {
				b = (BuildingVo) allList.get(i);
			}
			fixVo.setBuilding_id(b.getBuilding_id());
			fixVo.setBuilding_name(b.getBuilding_name());

			JSONArray array = JSONArray.fromObject(fixVo);
			ResponseUtil.write(ServletActionContext.getResponse(), array);
			/*session.setAttribute("fixVo", fixVo);
			List<BuildingVo> bList = buildingService.queryAllList();
			session.setAttribute("bList",bList);
			ResponseUtil.write(ServletActionContext.getResponse(), "true");*/
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	
	public String queryChangeVo1() throws Exception {
		try{
			StudentVo vo1 = new StudentVo();
			vo1.setStudent_userName(student_userName);
			/* 根据学号查询出全部的List，并看看是否入住 */
			allList = studentService.StudentList(vo1);

			StudentVo s = null;
			DormVo d = null;
			BuildingVo b = null;

			for (int i = 0; i < 1; i++) {
				s = (StudentVo) allList.get(i);
				System.out.println(s);
			}
			fixVo.setStudent_id(s.getStudent_id());
			fixVo.setStudent_userName(s.getStudent_userName());
			fixVo.setStudent_name(s.getStudent_name());
			fixVo.setStudent_sex(s.getStudent_sex());

			for (int i = 1; i < 2; i++) {
				d = (DormVo) allList.get(i);
			}
			fixVo.setDorm_id(d.getDorm_id());
			fixVo.setDorm_name(d.getDorm_name());

			for (int i = 2; i < 3; i++) {
				b = (BuildingVo) allList.get(i);
			}
			fixVo.setBuilding_id(b.getBuilding_id());
			fixVo.setBuilding_name(b.getBuilding_name());
			
			List<BuildingVo> bList = buildingService.queryAllList();
			session.setAttribute("bList",bList);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "queryChangeVo";
	}
	
	/**
	 *  调换寝室
	 * @throws Exception 
	 * */
	public String change() throws Exception{
		try{
			JSONObject result=new JSONObject();
			Long student_id=fixVo.getStudent_id();
			Long dorm_id=fixVo.getDorm_id();
			Long building_id=fixVo.getBuilding_id();
			
			if(Tools.isEmpty(building_id)){
				result.put("errorMsg","请先选择或手动填写楼宇！");
				ResponseUtil.write(ServletActionContext.getResponse(), result);
				return null;
			}
			if(Tools.isEmpty(dorm_id)){
				result.put("errorMsg","请先选择或手动填写宿舍！");
				ResponseUtil.write(ServletActionContext.getResponse(), result);
				return null;
			}
			
			boolean delNumFlag=studentService.aftetOutDorm(student_id);
			if(delNumFlag){
				boolean flag=studentService.exchangeDorm(student_id, dorm_id, building_id,fixVo);
				if(flag){
					result.put("success", "true");
					boolean addNumFlag=studentService.ifDorm(dorm_id);
					if(!addNumFlag){
						result.put("errorMsg","该宿舍人数已满，请选择其他宿舍入住！");
					}else {
						notice = new Tab_Notice();
						notice.setSee_state("已确认");
						guid = (String) session.getAttribute("uniqueGUID");
						notice.setGuid(guid);
						boolean uptState = noticeService.uptNotice(notice);
						if(!uptState){
							result.put("errorMsg","修改消息查看状态失败！");
						}else {
							//int NotNoticeCount =  (Integer) session.getAttribute("NotNoticeCount");
							int NotNoticeCount =  Integer.parseInt(jedis.hgetValue("tab_sushe", "NotNoticeCount"));
							if(NotNoticeCount > 0){  // 大于0 说明redis中 还是有数据
								//session.setAttribute("NotNoticeCount", NotNoticeCount-1);
								jedis.hsetValue("tab_sushe", "NotNoticeCount", NotNoticeCount-1 +"");
								
								String names = jedis.hgetValue("tab_sushe", "person");
								if(names != null && names.contains(",")){
									// 将 确认 后的 学生 姓名 移除 redis
									names = names.replaceFirst(student_name, "");
									if(names != null && names.contains(",,")){
										names = names.replace(",,", ",");
									}
									if(names != null && names.startsWith(",")){
										names = names.substring(1);
									}
									// 将最新的 学生 姓名 保存到 redis中 
									jedis.hsetValue("tab_sushe", "person", names);
								}
							}
						}
					}
					ResponseUtil.write(ServletActionContext.getResponse(), result);
				}else{
					result.put("errorMsg", "调换寝室失败！");
					ResponseUtil.write(ServletActionContext.getResponse(), result);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据学号查询出符合条件的IDList列表，然后得到对应的状态：迁出，未入住，如果不是这两个状态则返回success，
	 * 在页面再进入queryOutVo1()方法
	 * */
	/*public String queryOutVo() throws Exception {
		try{
			JSONObject result=new JSONObject();
			student_userName = vo.getStudent_userName();
			Long student_id = vo.getStudent_id();
			String out_date = vo.getOut_date();
			String student_remark = vo.getStudent_remark();
			
			if(Tools.isEmpty(student_userName)){
				result.put("errorMsg","请先填写学生学号！");
				ResponseUtil.write(ServletActionContext.getResponse(), result);
				return null;
			}
			List<?> student_Idlist=studentService.queryStudent_Id(student_userName);
			if(!(student_Idlist.size()>0)){
				result.put("errorMsg","学生学号不存在，请重新输入！");
			}else{
				StudentVo state_vo=studentService.getState(Long.parseLong(String.valueOf(student_Idlist.get(0))));
				String state=state_vo.getStudent_state();
				if("迁出".equals(state)){
					result.put("errorMsg","该学生处于迁出状态，不可重复迁出！");
				}else if("未入住".equals(state)) {
					result.put("errorMsg","该学生处于未入住状态，不能进行迁出操作！");
				}else{
					//result.put("success","true");
					this.outDorm(student_id,out_date,student_remark);
				}
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}*/
	
	/**
	 * 	迁出寝室
	 * */
	public String outDorm() throws Exception {
		try{
			Long studentId = Long.valueOf(student_id);
			JSONObject result=new JSONObject();
			boolean flag=studentService.outDorm(studentId,out_date, student_remark);
			if(flag){
				result.put("success", "true");
				notice = new Tab_Notice();
				notice.setSee_state("已确认");
				guid = (String) session.getAttribute("uniqueGUID");
				notice.setGuid(guid);
				boolean uptState = noticeService.uptNotice(notice);
				if(!uptState){
					result.put("errorMsg","修改消息查看状态失败！");
				}else {
					/*int NotNoticeCount =  (Integer) session.getAttribute("NotNoticeCount");
					if(NotNoticeCount > 0){
						session.setAttribute("NotNoticeCount", NotNoticeCount-1);
					}*/
					int NotNoticeCount =  Integer.parseInt(jedis.hgetValue("tab_sushe", "NotNoticeCount"));
					if(NotNoticeCount > 0){  // 大于0 说明redis中 还是有数据
						//session.setAttribute("NotNoticeCount", NotNoticeCount-1);
						jedis.hsetValue("tab_sushe", "NotNoticeCount", NotNoticeCount-1 +"");
						
						String names = jedis.hgetValue("tab_sushe", "person");
						if(names != null && names.contains(",")){
							// 将 确认 后的 学生 姓名 移除 redis
							names = names.replaceFirst(student_name, "");
							if(names != null && names.contains(",,")){
								names = names.replace(",,", ",");
							}
							if(names != null && names.startsWith(",")){
								names = names.substring(1);
							}
							// 将最新的 学生 姓名 保存到 redis中 
							jedis.hsetValue("tab_sushe", "person", names);
						}
					}
				}
			}else{
				result.put("errorMsg", "迁出寝室失败！");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 由上面的方法可以知道要进入此方法，一定是已入住的：供学生迁出寝室使用
	 * */
	/*public String queryOutVo1() throws Exception {
		try{
			StudentVo vo1 = new StudentVo();
			vo1.setStudent_userName(student_userName);
			allList = studentService.StudentList(vo1);

			StudentVo s = null;
			DormVo d = null;
			BuildingVo b = null;

			for (int i = 0; i < 1; i++) {
				s = (StudentVo) allList.get(i);
			}

			fixVo.setStudent_id(s.getStudent_id());
			fixVo.setStudent_userName(s.getStudent_userName());
			fixVo.setStudent_name(s.getStudent_name());
			fixVo.setStudent_sex(s.getStudent_sex());
			fixVo.setOut_date(out_data);

			for (int i = 1; i < 2; i++) {
				d = (DormVo) allList.get(i);
			}
			fixVo.setDorm_id(d.getDorm_id());
			fixVo.setDorm_name(d.getDorm_name());

			for (int i = 2; i < 3; i++) {
				b = (BuildingVo) allList.get(i);
			}
			fixVo.setBuilding_id(b.getBuilding_id());
			fixVo.setBuilding_name(b.getBuilding_name());
		} catch (Exception ex){
			ex.printStackTrace();
		}
		return "queryOutVo";
	}*/
	
	/**
	 * 	迁出寝室
	 * */
	/*private String outDorm(Long student_id,String out_date,String student_remark) throws Exception {
		try{
			JSONObject result=new JSONObject();
			boolean flag=studentService.outDorm(student_id,out_date, student_remark);
			if(flag){
				result.put("success", "true");
			}else{
				result.put("errorMsg", "迁出寝室失败！");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}*/
	
	
	//实现接口
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		this.session=request.getSession();		
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response=response;
	}

	//set(),get()方法
	public StudentVo getVo() {
		return vo;
	}
	public void setVo(StudentVo vo) {
		this.vo = vo;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}

	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	
	public String getStudent_id() {
		return student_id;
	}
	public void setStudent_id(String studentId) {
		student_id = studentId;
	}
	public String getDelIds() {
		return delIds;
	}
	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}
	public String getS_name() {
		return s_name;
	}
	public void setS_name(String sName) {
		s_name = sName;
	}
	public String getS_state() {
		return s_state;
	}
	public void setS_state(String sState) {
		s_state = sState;
	}
	public String getBuilding_id() {
		return building_id;
	}
	public void setBuilding_id(String buildingId) {
		building_id = buildingId;
	}
	public String getDorm_id() {
		return dorm_id;
	}
	public void setDorm_id(String dormId) {
		dorm_id = dormId;
	}
	public String getStudent_userName() {
		return student_userName;
	}
	public void setStudent_userName(String studentUserName) {
		student_userName = studentUserName;
	}
	public List<?> getAllList() {
		return allList;
	}
	public void setAllList(List<?> allList) {
		this.allList = allList;
	}
	public FixVo getFixVo() {
		return fixVo;
	}
	public void setFixVo(FixVo fixVo) {
		this.fixVo = fixVo;
	}
	public String getOut_data() {
		return out_data;
	}
	public void setOut_data(String outData) {
		out_data = outData;
	}
	public String getStudent_building() {
		return student_building;
	}
	public void setStudent_building(String student_building) {
		this.student_building = student_building;
	}
	public String getStudent_dorm() {
		return student_dorm;
	}
	public void setStudent_dorm(String student_dorm) {
		this.student_dorm = student_dorm;
	}
	public Integer getStudent_lackFlag() {
		return student_lackFlag;
	}
	public void setStudent_lackFlag(Integer student_lackFlag) {
		this.student_lackFlag = student_lackFlag;
	}
	public Integer getLackFlag() {
		return lackFlag;
	}
	public void setLackFlag(Integer lackFlag) {
		this.lackFlag = lackFlag;
	}
	public JmsTemplate getJmsTemplateNotice() {
		return jmsTemplateNotice;
	}
	public void setJmsTemplateNotice(JmsTemplate jmsTemplateNotice) {
		this.jmsTemplateNotice = jmsTemplateNotice;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Tab_Notice getNotice() {
		return notice;
	}
	public void setNotice(Tab_Notice notice) {
		this.notice = notice;
	}
	public String getSend_userName() {
		return send_userName;
	}
	public void setSend_userName(String send_userName) {
		this.send_userName = send_userName;
	}
	public String getSend_person() {
		return send_person;
	}
	public void setSend_person(String send_person) {
		this.send_person = send_person;
	}
	public String getRec_userName() {
		return rec_userName;
	}
	public void setRec_userName(String rec_userName) {
		this.rec_userName = rec_userName;
	}
	public String getRec_role() {
		return rec_role;
	}
	public void setRec_role(String rec_role) {
		this.rec_role = rec_role;
	}
	public String getSee_state() {
		return see_state;
	}
	public void setSee_state(String see_state) {
		this.see_state = see_state;
	}

	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getOut_date() {
		return out_date;
	}
	public void setOut_date(String out_date) {
		this.out_date = out_date;
	}
	public String getStudent_remark() {
		return student_remark;
	}
	public void setStudent_remark(String student_remark) {
		this.student_remark = student_remark;
	}
	public String getStudent_name() {
		return student_name;
	}
	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
	public String getS_institution() {
		return s_institution;
	}
	public void setS_institution(String s_institution) {
		this.s_institution = s_institution;
	}
	public String getS_class() {
		return s_class;
	}
	public void setS_class(String s_class) {
		this.s_class = s_class;
	}
	
}
