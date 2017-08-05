package com.business.dao;

import java.sql.ResultSet;
import java.util.List;

import org.hibernate.HibernateException;

import com.business.bean.DormVo;
import com.business.util.PageBean;

public interface I_DormManageDao {
	public ResultSet DormList(DormVo vo,String building_id,PageBean pageBean) throws HibernateException;
	public int DormCount() throws HibernateException;
	
	public boolean addDorm(DormVo vo) throws HibernateException;
	public String getPK() throws HibernateException;
	public boolean addDormBuilding(Long dorm_id,Long building_id) throws HibernateException;
	
	public boolean deleteDorm(Long id) throws HibernateException;
	public boolean deleteDormBuilding(Long dorm_id) throws HibernateException;
	
	public DormVo queryVo(Long id) throws HibernateException;
	
	public boolean updateDorm(DormVo vo) throws HibernateException;
	
	//根据buildingId查询出对应的dorm列表
	public List<DormVo> queryDormByBuilding(Long building_id) throws HibernateException;
	
}
