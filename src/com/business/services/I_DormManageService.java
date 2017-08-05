package com.business.services;

import java.sql.ResultSet;
import java.util.List;

import org.hibernate.HibernateException;

import com.business.bean.DormVo;
import com.business.util.PageBean;

public interface I_DormManageService {
	public ResultSet DormList(DormVo vo,String building_id,PageBean pageBean) throws HibernateException;
	public int DormCount() throws HibernateException;
	
	public boolean addDormAndDormBuilding(DormVo vo,Long building_id) throws HibernateException;
	
	public boolean deleteDormAndDormBuilding(Long id) throws HibernateException;
	
	public DormVo queryVo(Long id) throws HibernateException;
	
	public boolean updateDorm(DormVo vo) throws HibernateException;
	
	//根据buildingId查询出对应的dorm列表
	public List<DormVo> queryDormByBuilding(Long building_id) throws HibernateException;
}
