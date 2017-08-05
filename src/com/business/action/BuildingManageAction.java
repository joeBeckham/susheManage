package com.business.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.business.bean.BuildingVo;
import com.business.bean.TeacherVo;
import com.business.services.I_BuildingManageService;
import com.business.services.I_TeacherManageServices;
import com.business.util.JsonUtil;
import com.business.util.PageBean;
import com.business.util.ResponseUtil;
import com.business.util.Tools;
import com.opensymphony.xwork2.ActionSupport;
/***
 * @author xbq
 * @version 1.0
 */
@Controller("buidlingAction")
@Scope("prototype")
public class BuildingManageAction extends ActionSupport implements ServletRequestAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource I_BuildingManageService buildingService=null;
	@Resource I_TeacherManageServices teacherService=null;
	HttpServletRequest request=null;
	HttpSession session=null;

	private BuildingVo vo=new BuildingVo();
	private String building_id;
	private String page;
	private String rows;
	
	private String delIds;
	private String t_name;
	private String teacherId_box;
	
	
	/**
	 * 	默认方法，用来查询List
	 * */
	public String execute() throws Exception{
		try{
			PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
			JSONObject result=new JSONObject();
			vo.setBuilding_name(t_name);
			
			JSONArray jsonArray=JsonUtil.formatRsToJsonArray(buildingService.buildingList(vo, pageBean));
			
			int total=buildingService.BuildingCount();
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
			if(!Tools.isEmpty(building_id)){
				vo.setBuilding_id(Long.parseLong(building_id));
			}
			if(!Tools.isEmpty(building_id)){
				flag=buildingService.updateBuildingVo(vo);
			}else{
				flag=buildingService.addBuilding(vo);
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
				result.put("errorMsg", "该楼宇已存在，请核实后重新录入！");
			}
		} finally {
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
			int delNums = 0;
			if(str != null && str.length <= 1){
				delNums=str.length;
				if(str.length>1){
					for(int i=0;i<delNums;i++){
						flag=buildingService.deleteBuilding(Long.parseLong(str[i]));
					}
				}else{
					flag=buildingService.deleteBuilding(Long.parseLong(delIds));
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
	 * 管理员列表
	 */
	public String queryManage(){
		System.out.println("-----------queryManage-----------");
		List<TeacherVo> Alllist=null;
		TeacherVo tVo=null;
		try{
			System.out.println("---------building_id-----------=="+building_id);
			session.setAttribute("building_id", building_id);
			
			//所有的管理员List
			Alllist=teacherService.queryAllTeacher();
			session.setAttribute("Alllist", Alllist);
			System.out.println("JSON.toJSONString(list)===="+JSON.toJSONString(Alllist));
			
			//对应的管理员
			List<?> teacherIdList=buildingService.queryManager(Long.parseLong(building_id));
			System.out.println("teacherIdList=========="+teacherIdList);
			session.setAttribute("teacherIdList",teacherIdList);
			
			List<Integer> rowList=new ArrayList<Integer>();
			if(null!=Alllist && Alllist.size()>0){
				System.out.println("Alllist.size()======"+Alllist.size());
				for(int i = 0;i<Alllist.size();i++){
					tVo=Alllist.get(i);
					if(teacherIdList!=null&&teacherIdList.size()>0){
						for(int j=0;j<teacherIdList.size();j++){
							if(teacherIdList.get(j).equals(tVo.getTeacher_id())){
								rowList.add(i);
							}
						}
					}
				}
				session.setAttribute("rowList",JSON.toJSONString(rowList));			//将json放到数组中
				System.out.println("JSON.toJSONString(rowList)======"+JSON.toJSONString(rowList));
			}
			ResponseUtil.write(ServletActionContext.getResponse(),JSON.toJSONString(Alllist));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 	将楼宇对应的管理员的行ID保存到session中
	 * */
	public String getRowIdOfSession(){
		System.out.println("-----------getRowIdOfSession-----------");
		List<TeacherVo> Alllist=null;
		TeacherVo tVo=null;
		try{
			System.out.println("---------building_id-----------=="+building_id);
			//所有的管理员List
			Alllist=teacherService.queryAllTeacher();
			System.out.println("JSON.toJSONString(list)===="+JSON.toJSONString(Alllist));
			
			List<?> teacherIdList=buildingService.queryManager(Long.parseLong(building_id));
			System.out.println("teacherIdList=========="+teacherIdList);
			
			List<Integer> rowList=new ArrayList<Integer>();
			if(null!=Alllist&&Alllist.size()>0){
				for(int i=0;i<Alllist.size();i++){
					tVo=Alllist.get(i);
					if(teacherIdList!=null&&teacherIdList.size()>0){
						for(int j=0;j<teacherIdList.size();j++){
							if(teacherIdList.get(j).equals(tVo.getTeacher_id())){
								rowList.add(i);
							}
						}
					}
				}
				System.out.println("--------------------------------------------------------");
				System.out.println("rowList=="+rowList);
				System.out.println("--------------------------------------------------------");
				System.out.println("JSON.toJSONString(rowList)======"+JSON.toJSONString(rowList));
				System.out.println("--------------------------------------------------------");
			}
			
			ResponseUtil.write(ServletActionContext.getResponse(),JSON.toJSONString(rowList));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 	给楼宇增加或者减少楼宇管理员，相当于授权
	 * */
	public String updateManager(){
		try{
			JSONObject result=new JSONObject();
			Long building_id=Long.parseLong(String.valueOf(session.getAttribute("building_id")));
			System.out.println("building_id============"+building_id);
			
			String[] arr=teacherId_box.split(",");
			System.out.println("arr==="+arr);
			if(arr!=null&&arr.length>0){
				System.out.println("arr不为空");
				for(String a:arr){
					System.out.println(a);
				}
			}else{
				System.out.println("arr为空");
			}
			boolean flag=buildingService.addTeacher(arr, building_id);
			if(flag){
				result.put("success","true");
			}else{
				result.put("success","true");
				result.put("errorMsg","修改楼宇管理员失败！");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		this.session=request.getSession();
	}
	
	//get(),set()方法
	public HttpServletRequest getRequest() {
		return request;
	}
	public I_BuildingManageService getBuildingService() {
		return buildingService;
	}
	public void setBuildingService(I_BuildingManageService buildingService) {
		this.buildingService = buildingService;
	}
	public BuildingVo getVo() {
		return vo;
	}
	public void setVo(BuildingVo vo) {
		this.vo = vo;
	}
	public String getBuilding_id() {
		return building_id;
	}
	public void setBuilding_id(String buildingId) {
		building_id = buildingId;
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
	public String getTeacherId_box() {
		return teacherId_box;
	}
	public void setTeacherId_box(String teacherIdBox) {
		teacherId_box = teacherIdBox;
	}

}
