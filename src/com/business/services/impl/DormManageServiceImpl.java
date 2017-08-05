package com.business.services.impl;

import java.sql.ResultSet;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.business.bean.DormVo;
import com.business.dao.I_DormManageDao;
import com.business.services.I_DormManageService;
import com.business.util.PageBean;
@Service("dormService")
public class DormManageServiceImpl implements I_DormManageService {

	@Resource I_DormManageDao dormDao=null;
	boolean flag=false;
	
	public int DormCount() throws HibernateException {
		int i=0;
		try{
			i=dormDao.DormCount();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return i;
	}

	public ResultSet DormList(DormVo vo,String building_id, PageBean pageBean)
			throws HibernateException {
		ResultSet rs=null;
		try{
			rs=dormDao.DormList(vo,building_id, pageBean);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return rs;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean addDormAndDormBuilding(DormVo vo,Long building_id) throws HibernateException {
		try{
			boolean flag1=dormDao.addDorm(vo);
			Long dormId=Long.parseLong(dormDao.getPK());
			boolean flag2=dormDao.addDormBuilding(dormId,building_id);
			if(flag1&&flag2){
				flag=true;
			}
		}catch(Exception ex){
			throw new HibernateException(ex);
		}
		return flag;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean deleteDormAndDormBuilding(Long id) throws HibernateException {
		try{
			boolean flag1=dormDao.deleteDorm(id);
			boolean flag2=dormDao.deleteDormBuilding(id);
			if(flag1&&flag2){
				flag=true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}

	public DormVo queryVo(Long id) throws HibernateException {
		DormVo vo=null;
		try{
			vo=dormDao.queryVo(id);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return vo;
	}

	public boolean updateDorm(DormVo vo) throws HibernateException {
		try{
			flag=dormDao.updateDorm(vo);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}

	public List<DormVo> queryDormByBuilding(Long buildingId) throws HibernateException {
		List<DormVo> list=null;
		try{
			list=dormDao.queryDormByBuilding(buildingId);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return list;
	}
}
