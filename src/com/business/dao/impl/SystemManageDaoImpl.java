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

import com.business.bean.LoginLogVo;
import com.business.dao.I_SystemManageDao;
import com.business.util.PageBean;
import com.business.util.Tools;

@Repository("systemDao")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class SystemManageDaoImpl implements I_SystemManageDao {

	@Resource SessionFactory sessionFactory=null;
	private Session session=null;
	Log log=LogFactory.getLog(this.getClass());
	
	@SuppressWarnings("unchecked")
	public String[] login(String flag,String userName, String pass) throws HibernateException {
		String[] arr={"","","","",""};
		try{
			session=sessionFactory.getCurrentSession();
			String hql="";
			if("system".equals(flag)){
				hql="select s.system_id,s.system_userName from SystemVo s where s.system_userName=? and s.system_userPass=?";
			}
			if("teacher".equals(flag)){
				hql="select t.teacher_id,t.teacher_userName,t.teacher_name from TeacherVo t where t.teacher_userName=? and t.teacher_userPass=?";
			}
			if("student".equals(flag)){
				hql="select s.student_id,s.student_userName,s.student_name,s.student_state from StudentVo s where s.student_userName=? and s.student_userPass=?";
			}
			Query query=session.createQuery(hql);
			query.setString(0,userName);
			query.setString(1,pass);
			List<Object[]> list=query.list();
			if(null!=list&&list.size()>0){
				Object[] array=list.get(0);
				arr[0]="true";
				arr[1]=String.valueOf(array[0]);
				arr[2]=String.valueOf(array[1]);
				if(!"system".equals(flag)){
					arr[3]=String.valueOf(array[2]);
				}else{
					arr[3]="系统管理员";
				}
				if("student".equals(flag)){
					arr[4]=String.valueOf(array[3]);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return arr;
	}

	/**
	 * 	查询记录数
	 * */
	public int LoginLogCount() throws HibernateException {
		int i=0;
		try{
			session=sessionFactory.getCurrentSession();
			
			String hql=" from LoginLogVo";
			Query query = session.createQuery(hql);
	        i =query.list().size();
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return i;
	}
	 
	/**
	 * 	修改密码
	 * */
	public boolean updatePassWord(String pass,String roleFlag,String loginId) throws HibernateException{
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="update ";
			if("student".equals(roleFlag)){
				hql+=" StudentVo s set s.student_userPass=? where s.student_id=?";
			}
			if("teacher".equals(roleFlag)){
				hql+=" TeacherVo t set t.teacher_userPass=? where t.teacher_id=?";
			}
			if("system".equals(roleFlag)){
				hql+=" SystemVo v set v.system_userPass=? where v.system_id=?";
			}
			Query query=session.createQuery(hql);
			query.setString(0,pass);
			query.setString(1,loginId);
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
	 * 	查询登录日志
	 * */
	@SuppressWarnings("deprecation")
	public ResultSet logList(LoginLogVo vo, PageBean pageBean) throws HibernateException {
		PreparedStatement ps =null;
		ResultSet rs=null;
		try{
			session=sessionFactory.getCurrentSession();
		    Connection con = session.connection();
		    StringBuffer sql=new StringBuffer("select g.id,g.userid,g.username,g.logindate,g.loginip,g.ipaddress from loginlog g where 1=1");
			if((null!=vo)&&(!Tools.isEmpty(vo.getUserName()))){
				sql.append(" and g.username like '%").append(vo.getUserName()).append("%'");
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
	 * 	添加登录日志
	 * */
	public boolean insertLoginLog(LoginLogVo log) throws HibernateException {
		boolean flag=true;
		try{
			session=sessionFactory.getCurrentSession();
			session.save(log);
		}catch(Exception ex){
			ex.printStackTrace();
			flag=false;
			throw new HibernateException(ex);
		}
		return flag;
	}

	/**
	 * 	修改操作
	 * */
	public boolean updateLoginLog(LoginLogVo vo) throws HibernateException {
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="update LoginLogVo g set g.username=?,g.logindate=?,g.loginip=?,g.ipaddress=? where g.id=?";
			Query query=session.createQuery(hql);
			query.setString(0,vo.getUserName());
			query.setString(1,vo.getLoginDate());
			query.setString(2,vo.getLoginIP());
			query.setString(3,vo.getIPaddress());
			query.setLong(4,vo.getId());
			int i=query.executeUpdate();
			if(i>0){
				flag=true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			flag=false;
			throw new HibernateException(ex);
		}
		return flag;
	}

	public boolean deleteLoginLog(Long id) throws HibernateException {
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="delete from LoginLogVo g where g.id=?";
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

	@SuppressWarnings("rawtypes")
	public boolean checkPass(String userId,String newPass,String roleFlag) throws HibernateException {
		List list = null;
		try {
			session = sessionFactory.getCurrentSession();
			String hql = "";
			if(roleFlag != null){
				if(roleFlag.equals("student")){
					hql = "select s.student_userPass from StudentVo s where s.student_id=?";
				}
				if(roleFlag.equals("teacher")){
					hql = "select t.teacher_userPass from TeacherVo t where t.teacher_id=?";
				}
				if(roleFlag.equals("system")){
					hql = "select v.system_userPass from SystemVo v where v.system_id=?";
				}
				Query query = session.createQuery(hql);
				query.setString(0, userId);
				list = query.list();
				if(list != null){
					Object obj = list.get(0);
					if(obj != null){
						String oldPass = (String) obj;
						if(oldPass.equals(newPass)){
							return true;
						}else {
							return false;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
