package com.business.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.business.bean.DormVo;
import com.business.services.I_DormManageService;
import com.business.util.JsonUtil;
import com.business.util.PageBean;
import com.business.util.ResponseUtil;
import com.business.util.Tools;
import com.opensymphony.xwork2.ActionSupport;

@Controller("dormAction")
@Scope("prototype")
public class DormManageAction extends ActionSupport implements ServletRequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource I_DormManageService dormService=null;
	HttpServletRequest request=null;

	private DormVo vo=new DormVo();
	private String dorm_id;
	private String page;
	private String rows;
	
	private String delIds;
	private String t_name;
	private String building_id;
	private String building_name;
	
	
	/**
	 * 	默认方法，用来查询List
	 * */
	public String execute() throws Exception{
		try{
			System.out.println("building_id================="+building_id);
			PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
			JSONObject result=new JSONObject();
			System.out.println("t_name=="+t_name);
			vo.setDorm_name(t_name);
			JSONArray jsonArray=JsonUtil.formatRsToJsonArray(dormService.DormList(vo,building_id, pageBean));
			int total=dormService.DormCount();
			System.out.println("total==="+total);
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
			if(!Tools.isEmpty(dorm_id)){
				vo.setDorm_id(Long.parseLong(dorm_id));
			}
			
			if(!Tools.isEmpty(dorm_id)){
				flag=dormService.updateDorm(vo);
			}else{
				// 下面这一句是转码，不加这一句  前台传值  会乱码
				//building_name = new String(request.getParameter("building_name").getBytes("ISO8859-1"), "UTF-8");
				vo.setDorm_building(building_name);
				flag=dormService.addDormAndDormBuilding(vo, Long.parseLong(building_id));
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
				result.put("errorMsg", "该宿舍已存在，请核实后正确输入！");
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
			int delNums=str.length;
			if(str.length>1){
				for(int i=0;i<delNums;i++){
					flag=dormService.deleteDormAndDormBuilding(Long.parseLong(str[i]));
				}
			}else{
				flag=dormService.deleteDormAndDormBuilding(Long.parseLong(delIds));
			}
			if(flag){
				result.put("success", "true");
				result.put("delNums", delNums);
			}else{
				result.put("errorMsg", "Sorry！删除失败！");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	//实现接口
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
	
	//get(),set()方法
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
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
	public I_DormManageService getDormService() {
		return dormService;
	}
	public void setDormService(I_DormManageService dormService) {
		this.dormService = dormService;
	}
	public DormVo getVo() {
		return vo;
	}
	public void setVo(DormVo vo) {
		this.vo = vo;
	}
	public String getDorm_id() {
		return dorm_id;
	}
	public void setDorm_id(String dormId) {
		dorm_id = dormId;
	}
	public String getBuilding_id() {
		return building_id;
	}
	public void setBuilding_id(String buildingId) {
		building_id = buildingId;
	}

	public String getBuilding_name() {
		return building_name;
	}

	public void setBuilding_name(String building_name) {
		this.building_name = building_name;
	}

}
