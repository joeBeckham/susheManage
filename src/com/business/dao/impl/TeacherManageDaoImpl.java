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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.business.bean.TeacherVo;
import com.business.dao.I_TeacherManageDao;
import com.business.util.PageBean;
import com.business.util.Tools;

@Repository("teacherDao")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class TeacherManageDaoImpl implements I_TeacherManageDao {

	@Resource SessionFactory sessionFactory=null;
	private Session session=null;
	Log log=LogFactory.getLog(this.getClass());
	
	public boolean addTeacher(TeacherVo vo) throws HibernateException {
		boolean flag=true;
		try{
			session=sessionFactory.getCurrentSession();
			vo.setTeacher_userPass("888888");
			session.save(vo);
		}catch(Exception ex){
			flag=false;
			throw new HibernateException(ex);
		}
		return flag;
	}


	public boolean deleteTeacher(Long id) throws HibernateException {
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="delete from TeacherVo t where t.teacher_id=?";
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
	
	public boolean deleteBatch(String[] arr) throws HibernateException {
		log.info("[批量删除数据:用HQL的方式和用 in拼接sql语句] 实现类开始");
		boolean flag=true;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="delete TeacherVo  t where t.teacher_id in(0";
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
		}
		return flag;
	}

	public TeacherVo queryVo(Long id) throws HibernateException {
		TeacherVo vo=null;
		try{
			session=sessionFactory.getCurrentSession();
			vo=(TeacherVo) session.get(TeacherVo.class,id);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return vo;
	}

	public int TeacherCount() throws HibernateException {
		int i=0;
		try{
			session=sessionFactory.getCurrentSession();
			
			String hql=" from TeacherVo";
			Query query = session.createQuery(hql);
	        i =query.list().size();
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return i;
	}
	
	@SuppressWarnings("deprecation")
	public ResultSet teacherList(TeacherVo vo, PageBean pageBean) throws HibernateException {
		PreparedStatement ps =null;
		ResultSet rs=null;
		try{
			session=sessionFactory.getCurrentSession();
		    Connection con = session.connection();
		    StringBuffer sql=new StringBuffer("select teacher_id,teacher_userName,teacher_userPass,teacher_name,teacher_sex,teacher_tel from teacher where 1=1");
			if((null!=vo)&&(!Tools.isEmpty(vo.getTeacher_name()))){
				sql.append(" and teacher_name like '%").append(vo.getTeacher_name()).append("%'");
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

	public boolean updateTeacherVo(TeacherVo vo) throws HibernateException {
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="update TeacherVo t set t.teacher_userName=?,t.teacher_name=?,t.teacher_sex=?,t.teacher_tel=? where t.teacher_id=?";
			Query query=session.createQuery(hql);
			int index=0;
			query.setString(index++,vo.getTeacher_userName());
			query.setString(index++,vo.getTeacher_name());
			query.setString(index++,vo.getTeacher_sex());
			query.setString(index++,vo.getTeacher_tel());
			query.setLong(index++,vo.getTeacher_id());
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


	@SuppressWarnings("unchecked")
	public List<TeacherVo> queryAllTeacher() throws HibernateException {
		List<TeacherVo> allList=new ArrayList<TeacherVo>();
		TeacherVo vo=null;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="select t.teacher_id,t.teacher_userName,t.teacher_name,t.teacher_sex,t.teacher_tel from TeacherVo t";
			Query query=session.createQuery(hql);
			List<Object[]> list=query.list();
			if(null!=list&&list.size()>0){
				for(int i=0;i<list.size();i++){
					int index=0;
					Object[] arr=list.get(i);
					vo=new TeacherVo();
					vo.setTeacher_id(Long.parseLong(String.valueOf(arr[index++])));
					vo.setTeacher_userName(String.valueOf(arr[index++]));
					vo.setTeacher_name(String.valueOf(arr[index++]));
					vo.setTeacher_sex(String.valueOf(arr[index++]));
					vo.setTeacher_tel(String.valueOf(arr[index++]));
					allList.add(vo);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return allList;
	}

}
