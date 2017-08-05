package com.business.action;

import java.sql.ResultSet;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.business.bean.TeacherVo;
import com.business.services.I_TeacherManageServices;
import com.business.util.ExcelUtil;
import com.business.util.JsonUtil;
import com.business.util.PageBean;
import com.business.util.ResponseUtil;
import com.business.util.Tools;
import com.opensymphony.xwork2.ActionSupport;

@Controller("teacherAction")
@Scope("prototype")
public class TeacherManageAction extends ActionSupport implements ServletRequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource I_TeacherManageServices teacherService=null;
	HttpServletRequest request=null;

	private TeacherVo vo=new TeacherVo();
	private String teacher_id;
	private String page;
	private String rows;
	
	private String delIds;
	private String t_name;
	
	
	/**
	 * 	默认方法，用来查询List
	 * */
	public String execute() throws Exception{
		try{
			PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
			JSONObject result=new JSONObject();
			System.out.println("t_name=="+t_name);
			vo.setTeacher_name(t_name);
			JSONArray jsonArray=JsonUtil.formatRsToJsonArray(teacherService.teacherList(vo, pageBean));
			int total=teacherService.TeacherCount();
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
			if(!Tools.isEmpty(teacher_id)){
				vo.setTeacher_id(Long.parseLong(teacher_id));
				System.out.println("id不为空！！！");
			}
			if(!Tools.isEmpty(teacher_id)){
				flag=teacherService.updateTeacherVo(vo);
			}else{
				flag=teacherService.addTeacher(vo);
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
				result.put("errorMsg", "该管理员名称已存在，请核实后重新录入！");
			}
		} finally{
			ResponseUtil.write(ServletActionContext.getResponse(), result);
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
			System.out.println("delIds============="+delIds);
			int delNums = 0;
			if(str != null && str.length <= 1){
				delNums = str.length;
				if(str.length>1){
					for(int i=0;i<delNums;i++){
						flag=teacherService.deleteTeacher(Long.parseLong(str[i]));
					}
				}else{
					flag=teacherService.deleteTeacher(Long.parseLong(delIds));
				}
			}
			
			if(flag){
				result.put("success", "true");
				result.put("delNums", delNums);
			}else{
				result.put("errorMsg", "Sorry！删除失败");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 	用模版导出查询后的数据
	 * */
	public String export() throws Exception{
		try{
			Workbook wb=new HSSFWorkbook();
			if(null==vo){
				vo=new TeacherVo();
			}
			vo.setTeacher_name(t_name);
			ResultSet rs = teacherService.teacherList(vo, null);
			wb=ExcelUtil.fillExcelDataWithTemplate(rs, "teacherExporTemplate.xls");
			ResponseUtil.export(ServletActionContext.getResponse(), wb, "利用模版导出excel.xls");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 	查询出所有的teacher列表
	 * */
	public String queryAllTeacher(){
		try{
			List<TeacherVo> buildingList = teacherService.queryAllTeacher();
			String html="<select id=\"sendManage\" name=\"notice.rec_userName\" style=\"width:100\" class=\"easyui-combobox\">";
			html+="<option value=\"\" selected=\"selected\">==全部==</option>";
			TeacherVo bVo=null;
			if(buildingList!=null&buildingList.size()>0){
				for(int i=0;i<buildingList.size();i++){
					bVo=buildingList.get(i);
					html+="<option value='"+bVo.getTeacher_userName()+"'>"+bVo.getTeacher_userName()+"</option>";
				}
			}
			html+="</select>";
			ResponseUtil.write(ServletActionContext.getResponse(),html);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
	
	//get(),set()方法

	public HttpServletRequest getRequest() {
		return request;
	}
	public I_TeacherManageServices getTeacherService() {
		return teacherService;
	}
	public void setTeacherService(I_TeacherManageServices teacherService) {
		this.teacherService = teacherService;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public TeacherVo getVo() {
		return vo;
	}
	public void setVo(TeacherVo vo) {
		this.vo = vo;
	}
	public String getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(String teacherId) {
		teacher_id = teacherId;
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
	public String getDelIds() {
		return delIds;
	}
	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}
	public String getT_name() {
		return t_name;
	}
	public void setT_name(String tName) {
		t_name = tName;
	}

}
