package com.business.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.business.bean.BuildingVo;
import com.business.bean.TeacherBuildingIDPk;
import com.business.bean.TeacherBuildingVo;
import com.business.dao.I_BuildingManageDao;
import com.business.util.PageBean;
import com.business.util.Tools;
@Repository("buildingDao")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class BuildingManageDaoImpl implements I_BuildingManageDao {

	private Session session=null;
	@Resource SessionFactory sessionFactory=null;
	Log log=LogFactory.getLog(this.getClass());

	public boolean addBuilding(BuildingVo vo) throws HibernateException {
		boolean flag=true;
		try{
			session=sessionFactory.getCurrentSession();
			session.save(vo);
		}catch(Exception ex){
			flag=false;
			throw new HibernateException(ex);
		}
		return flag;
	}

	public int BuildingCount() throws HibernateException {
		int i=0;
		try{
			session=sessionFactory.getCurrentSession();
			String hql=" from BuildingVo";
			Query query = session.createQuery(hql);
	        i =query.list().size();
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return i;
	}
	
	@SuppressWarnings("deprecation")
	public ResultSet buildingList(BuildingVo vo, PageBean pageBean)
			throws HibernateException {
		PreparedStatement ps =null;
		ResultSet rs=null;
		try{
			session=sessionFactory.getCurrentSession();
		    Connection con = session.connection();
		    StringBuffer sql=new StringBuffer("select building_id,building_name,building_remark from building where 1=1");
		    
			if((null!=vo)&&(!Tools.isEmpty(vo.getBuilding_name()))){
				sql.append(" and building_name like '%").append(vo.getBuilding_name()).append("%'");
			}
			if(null!=pageBean){
				sql.append(" limit "+pageBean.getStart()+","+pageBean.getRows());
			}
		    ps = con.prepareStatement(sql.toString());
			rs=ps.executeQuery();
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return rs;
	}

	public boolean deleteBatch(String[] arr) throws HibernateException {
		log.info("[批量删除数据:用HQL的方式和用 in拼接sql语句] 实现类开始");
		boolean flag=true;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="delete BuildingVo  b where b.building_id in(0";
			for(int m=0;m<arr.length;m++){
				hql+=","+arr[m];
			}
			hql=hql+")";
			Query query=session.createQuery(hql);
			int delInt=query.executeUpdate();
			System.out.println("删除数目："+delInt);
			
			log.info("[批量删除数据:用HQL的方式和用 in拼接sql语句]  实现类成功");
		}catch(Exception ex){
			ex.printStackTrace();
			log.error("[批量删除数据:用HQL的方式和用 in拼接sql语句]  实现类失败，失败原因："+ex);
			flag=true;
			throw new HibernateException(ex);
		}
		return flag;
	}

	public boolean deleteBuilding(Long id) throws HibernateException {
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="delete from BuildingVo b where b.building_id=?";
			Query query=session.createQuery(hql);
			query.setLong(0,id);
			int i=query.executeUpdate();
			if(i>0){
				flag=true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}

	public BuildingVo queryVo(Long id) throws HibernateException {
		BuildingVo vo=null;
		try{
			session=sessionFactory.getCurrentSession();
			vo=(BuildingVo) session.get(BuildingVo.class,id);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return vo;
	}

	public boolean updateBuildingVo(BuildingVo vo) throws HibernateException {
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="update BuildingVo b set b.building_name=?,b.building_remark=? where b.building_id=?";
			Query query=session.createQuery(hql);
			int index=0;
			query.setString(index++,vo.getBuilding_name());
			query.setString(index++,vo.getBuilding_remark());
			query.setLong(index++,vo.getBuilding_id());
			int i=query.executeUpdate();
			if(i>0){
				flag=true;
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}

	/**
	 * 	根据楼宇ID查询出对应的楼宇管理员信息
	 * */
	@SuppressWarnings("rawtypes")
	public List<?> queryManager(Long building_id) throws HibernateException {
		List returnlist=null;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="select tb.id.teacher_id from TeacherBuildingVo tb where tb.id.building_id=?";
			Query query=session.createQuery(hql);
			query.setLong(0,building_id);
			returnlist=query.list();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return returnlist;
	}

	/**
	 * 	查询出所有的building
	 * */
	@SuppressWarnings("unchecked")
	public List<BuildingVo> queryAllList() throws HibernateException {
		List<BuildingVo> buildingList=null;
		try{
			session=sessionFactory.getCurrentSession();
			String hql=" from BuildingVo ";
			Query query=session.createQuery(hql);
			buildingList=query.list();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return buildingList;
	}

	//////////////////////////////////以下两个方法在服务层调用///////////////////////////////////////////
	/**
	 * 	减管理员
	 * */
	public boolean deleteTeacherByBuilding(Long buildingId) throws HibernateException {
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="delete from TeacherBuildingVo tb where tb.id.building_id=?";
			Query query=session.createQuery(hql);
			query.setLong(0,buildingId);
			int i=query.executeUpdate();
			if(i>0){
				flag=true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}
	
	/**
	 * 	给楼宇增加楼宇管理员
	 * */
	public boolean addTeacherByBuilding(String[] teacherId,Long buildingId) throws HibernateException {
		boolean flag=true;
		try{
			session=sessionFactory.getCurrentSession();
			TeacherBuildingIDPk pk=null;
			TeacherBuildingVo vo=null;
			if(null!=teacherId&&teacherId.length>0){
				for(int i=0;i<teacherId.length;i++){
					pk=new TeacherBuildingIDPk();
					pk.setTeacher_id(Long.parseLong(teacherId[i]));
					pk.setBuilding_id(buildingId);
					
					vo=new TeacherBuildingVo();
					vo.setId(pk);
					session.save(vo);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			flag=false;
			throw new HibernateException(ex);
		}
		return flag;
	}

}
