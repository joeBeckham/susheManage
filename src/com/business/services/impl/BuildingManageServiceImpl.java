package com.business.services.impl;

import java.sql.ResultSet;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import com.business.bean.BuildingVo;
import com.business.dao.I_BuildingManageDao;
import com.business.services.I_BuildingManageService;
import com.business.util.PageBean;

@Service("buildingService")
public class BuildingManageServiceImpl implements I_BuildingManageService {

	
	@Resource I_BuildingManageDao buildingDao=null;
	boolean flag=false;
	
	public int BuildingCount() throws HibernateException {
		int i=0;
		try{
			i=buildingDao.BuildingCount();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return i;
	}

	public boolean addBuilding(BuildingVo vo) throws HibernateException {
		try{
			flag=buildingDao.addBuilding(vo);
		}catch(Exception ex){
			throw new HibernateException(ex);
		}
		return flag;
	}

	public ResultSet buildingList(BuildingVo vo, PageBean pageBean)
			throws HibernateException {
		ResultSet rs=null;
		try{
			rs=buildingDao.buildingList(vo, pageBean);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return rs;
	}

	public boolean deleteBatch(String[] arr) throws HibernateException {
		try{
			flag=buildingDao.deleteBatch(arr);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}

	public boolean deleteBuilding(Long id) throws HibernateException {
		try{
			flag=buildingDao.deleteBuilding(id);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}

	public BuildingVo queryVo(Long id) throws HibernateException {
		BuildingVo vo=null;
		try{
			vo=buildingDao.queryVo(id);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return vo;
	}

	public boolean updateBuildingVo(BuildingVo vo) throws HibernateException {
		try{
			flag=buildingDao.updateBuildingVo(vo);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}

	public List<?> queryManager(Long buildingId) throws HibernateException {
		List<?> list=null;
		try{
			list=buildingDao.queryManager(buildingId);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return list;
	}

	public List<BuildingVo> queryAllList() throws HibernateException {
		List<BuildingVo> list=null;
		try{
			list=buildingDao.queryAllList();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return list;
	}

	/**
	 * 	给楼宇增加楼宇管理员，相当于授权
	 * */
	public boolean addTeacher(String[] teacherId, Long buildingId) throws HibernateException {
		boolean flag=false;
		try{
			buildingDao.deleteTeacherByBuilding(buildingId);
			flag=buildingDao.addTeacherByBuilding(teacherId, buildingId);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}

}
