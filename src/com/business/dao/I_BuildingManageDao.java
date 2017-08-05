package com.business.dao;

import java.sql.ResultSet;
import java.util.List;

import org.hibernate.HibernateException;

import com.business.bean.BuildingVo;
import com.business.util.PageBean;

public interface I_BuildingManageDao {
	public ResultSet buildingList(BuildingVo vo,PageBean pageBean) throws HibernateException;
	public int BuildingCount() throws HibernateException;
	
	public boolean addBuilding(BuildingVo vo) throws HibernateException;
	public boolean deleteBuilding(Long id) throws HibernateException;
	public boolean deleteBatch(String arr[]) throws HibernateException;
	public BuildingVo queryVo(Long id) throws HibernateException;
	public boolean updateBuildingVo(BuildingVo vo) throws HibernateException;
	
	//根据楼宇ID查询出对应的楼宇管理员信息
	public List<?> queryManager(Long building_id) throws HibernateException;
	
	//查询出所有的楼宇,在学生action中调用到了，目的是异步刷新下拉框
	public List<BuildingVo> queryAllList() throws HibernateException;
	
	//先减在增，并在服务层调用
	public boolean deleteTeacherByBuilding(Long buildingId) throws HibernateException;
	//给楼宇增加楼宇管理员
	public boolean addTeacherByBuilding(String[] teacher_id,Long building_id) throws HibernateException;
	
	
}
