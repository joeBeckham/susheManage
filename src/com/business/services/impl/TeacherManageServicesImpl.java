package com.business.services.impl;

import java.sql.ResultSet;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import com.business.bean.TeacherVo;
import com.business.dao.I_TeacherManageDao;
import com.business.services.I_TeacherManageServices;
import com.business.util.PageBean;

@Service("teacherService")
public class TeacherManageServicesImpl implements I_TeacherManageServices {

	@Resource I_TeacherManageDao teacherDao=null;
	boolean flag=false;
	
	public int TeacherCount() throws HibernateException {
		int i=0;
		try{
			i=teacherDao.TeacherCount();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return i;
	}

	public boolean addTeacher(TeacherVo vo) throws HibernateException {
		try{
			flag=teacherDao.addTeacher(vo);
		}catch(Exception ex){
			throw new HibernateException(ex);
		}
		return flag;
	}

	public boolean deleteBatch(String[] arr) throws HibernateException {
		try{
			flag=teacherDao.deleteBatch(arr);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}

	public boolean deleteTeacher(Long id) throws HibernateException {
		try{
			flag=teacherDao.deleteTeacher(id);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}

	public TeacherVo queryVo(Long id) throws HibernateException {
		TeacherVo vo=null;
		try{
			vo=teacherDao.queryVo(id);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return vo;
	}

	public ResultSet teacherList(TeacherVo vo, PageBean pageBean)
			throws HibernateException {
		ResultSet rs=null;
		try{
			rs=teacherDao.teacherList(vo, pageBean);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return rs;
	}

	public boolean updateTeacherVo(TeacherVo vo) throws HibernateException {
		try{
			flag=teacherDao.updateTeacherVo(vo);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}

	public List<TeacherVo> queryAllTeacher() throws HibernateException {
		List<TeacherVo> list=null;
		try{
			list=teacherDao.queryAllTeacher();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return list;
	}

}
