package com.business.action;

import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.business.bean.LoginLogVo;
import com.business.services.I_SystemManageService;
import com.business.util.ExcelUtil;
import com.business.util.JsonUtil;
import com.business.util.PageBean;
import com.business.util.ResponseUtil;
import com.opensymphony.xwork2.ActionSupport;

@Controller("logAction")
@Scope("prototype")
public class LoginLogManageAction extends ActionSupport implements ServletRequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HttpServletRequest request=null;
	HttpSession session=null;
	@Resource I_SystemManageService systemService=null;
	
	LoginLogVo vo=new LoginLogVo();
	private String page;
	private String rows;
	
	private String delIds;
	private String t_name;
	
	private String id;
	
	
	/**
	 * 	默认方法，用来查询List
	 * */
	public String execute() throws Exception{
		try{
			PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
			JSONObject result=new JSONObject();
			vo.setUserName(t_name);
			JSONArray jsonArray=JsonUtil.formatRsToJsonArray(systemService.logList(vo, pageBean));
			int total=systemService.LoginLogCount();
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 	根据得到的id，执行修改
	 * */
	public String save() throws Exception{
		boolean flag=false;
		try{
			vo.setId(Long.parseLong(id));
			JSONObject result=new JSONObject();
			flag=systemService.updateLoginLog(vo);
			if(flag){
				result.put("success","true");
			}else{
				result.put("success","true");
				result.put("errorMsg","修改失败！！");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception ex){
			ex.printStackTrace();
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
			int delNums=str.length;
			if(str.length>1){
				for(int i=0;i<delNums;i++){
					flag=systemService.deleteLoginLog(Long.parseLong(str[i]));
				}
			}else{
				flag=systemService.deleteLoginLog(Long.parseLong(delIds));
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
				vo=new LoginLogVo();
			}
			vo.setUserName(t_name);
			ResultSet rs=systemService.logList(vo, null);
			
			wb=ExcelUtil.fillExcelDataWithTemplate(rs,"logExporTemplate.xls");
			ResponseUtil.export(ServletActionContext.getResponse(), wb, "利用模版导出excel.xls");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	//实现接口
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		this.session=request.getSession();
	}

	//get set方法
	public LoginLogVo getVo() {
		return vo;
	}
	public void setVo(LoginLogVo vo) {
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
