package com.business.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.business.bean.DormBuildingIDPk;
import com.business.bean.DormBuildingVo;
import com.business.bean.DormVo;
import com.business.dao.I_DormManageDao;
import com.business.util.PageBean;
import com.business.util.Tools;

@Repository("dormDao")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class DormManageDaoImpl implements I_DormManageDao {

	private Session session=null;
	@Resource SessionFactory sessionFactory=null;
	Log log=LogFactory.getLog(this.getClass());
	
	public int DormCount() throws HibernateException {
		int i=0;
		try{
			session=sessionFactory.getCurrentSession();
			String hql=" from DormVo";
			Query query = session.createQuery(hql);
	        i =query.list().size();
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return i;
	}
	
	@SuppressWarnings("deprecation")
	public ResultSet DormList(DormVo vo,String building_id, PageBean pageBean)
			throws HibernateException {
		PreparedStatement ps =null;
		ResultSet rs=null;
		try{
			session=sessionFactory.getCurrentSession();
		    Connection con = session.connection();
		    StringBuffer sql=new StringBuffer("SELECT d.dorm_id,d.dorm_building,d.dorm_name,d.dorm_type,d.dorm_people_num,d.dorm_tel  ");

		    if(!Tools.isEmpty(building_id)){
				sql.append(",b.building_id,b.building_name");
			}
			sql.append(" FROM dorm d ");
			if(!Tools.isEmpty(building_id)){
				sql.append(" ,building b,dorm_building db ");
			}
			sql.append(" WHERE 1=1 ");
			if(!Tools.isEmpty(building_id)){
				sql.append(" and d.dorm_id=db.dorm_id AND b.building_id=db.building_id and b.building_id='").append(building_id).append("'");
			}
			
			if((null!=vo)&&(!Tools.isEmpty(vo.getDorm_name()))){
				sql.append(" and d.dorm_name like '%").append(vo.getDorm_name()).append("%'");
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
	
	/**
	 * 	增加
	 * */
	//增加dorm表
	public boolean addDorm(DormVo vo) throws HibernateException {
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			String sql="insert into dorm(dorm_id,dorm_building,dorm_name,dorm_type,dorm_tel,dorm_people_num) values(null,?,?,?,?,0)";
			SQLQuery query=session.createSQLQuery(sql);
			query.setString(0,vo.getDorm_building());
			query.setString(1,vo.getDorm_name());
			query.setString(2,vo.getDorm_type());
			query.setString(3,vo.getDorm_tel());
			int i=query.executeUpdate();
			if(i>0){
				flag=true;
			}
		}catch(Exception ex){
			throw new HibernateException(ex);
		}
		return flag;
	}
	
	//得到主键值，供增加中间表使用
	@SuppressWarnings("rawtypes")
	public String getPK() throws HibernateException{
		String PkId=null;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="SELECT MAX(d.dorm_id) AS pkid FROM DormVo d";
			Query query=session.createQuery(hql);
			List list=query.list();
			if(!Tools.isEmpty(list)&&list.size()>0){
				PkId=list.get(0).toString();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return PkId;
	}
	
	//增加中间表
	public boolean addDormBuilding(Long dormId, Long buildingId) throws HibernateException {
		boolean flag=true;
		try{
			session=sessionFactory.getCurrentSession();
			DormBuildingVo vo=new DormBuildingVo();
			DormBuildingIDPk pk=new DormBuildingIDPk();
			pk.setDorm_id(dormId);
			pk.setBuilding_id(buildingId);
			vo.setId(pk);
			session.save(vo);
		}catch(Exception ex){
			ex.printStackTrace();
			flag=false;
			throw new HibernateException(ex);
		}
		return flag;
	}
	
	/**
	 * 	删除
	 * */
	//删除dorm表中 的数据
	public boolean deleteDorm(Long id) throws HibernateException {
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="delete from DormVo d where d.dorm_id=?";
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
	
	//删除中间表中对应的数据
	public boolean deleteDormBuilding(Long dormId) throws HibernateException {
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="delete from DormBuildingVo db where db.id.dorm_id=?";
			Query query=session.createQuery(hql);
			query.setLong(0,dormId);
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
	 * 	查询Vo
	 * */
	//查询出dorm表
	public DormVo queryVo(Long id) throws HibernateException {
		DormVo vo=null;
		try{
			session=sessionFactory.getCurrentSession();
			vo=(DormVo) session.get(DormVo.class,id);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return vo;
	}
	
	/**
	 * 	修改
	 * */
	//修改dorm表
	public boolean updateDorm(DormVo vo) throws HibernateException {
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="update DormVo b set dorm_name=?,dorm_type=?,dorm_tel=? where dorm_id=?";
			Query query=session.createQuery(hql);
			int index=0;
			query.setString(index++,vo.getDorm_name());
			query.setString(index++,vo.getDorm_type());
			query.setString(index++,vo.getDorm_tel());
			query.setLong(index++,vo.getDorm_id());
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
	 * 	根据buildingId查询出对应的dorm列表
	 * */
	@SuppressWarnings("unchecked")
	public List<DormVo> queryDormByBuilding(Long buildingId) throws HibernateException {
		List<DormVo> returnList=new ArrayList<DormVo>();
		DormVo vo=null;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="select d.dorm_id,d.dorm_name from DormVo d,DormBuildingVo db where d.dorm_id=db.id.dorm_id and db.id.building_id=?";
			Query query=session.createQuery(hql);
			query.setLong(0,buildingId);
			List<Object[]> list=query.list();
			if(null!=list&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Object[] arr=list.get(i);
					vo=new DormVo();
					vo.setDorm_id(Long.parseLong(String.valueOf(arr[0])));
					vo.setDorm_name(String.valueOf(arr[1]));
					returnList.add(vo);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return returnList;
	}
}
