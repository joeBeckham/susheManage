package com.business.dao;

import java.sql.ResultSet;
import java.util.List;

import org.hibernate.HibernateException;

import com.business.bean.TeacherVo;
import com.business.util.PageBean;

public interface I_TeacherManageDao {
	public ResultSet teacherList(TeacherVo vo,PageBean pageBean) throws HibernateException;
	public int TeacherCount() throws HibernateException;
	
	public boolean addTeacher(TeacherVo vo) throws HibernateException;
	public boolean deleteTeacher(Long id) throws HibernateException;
	public boolean deleteBatch(String arr[]) throws HibernateException;
	public TeacherVo queryVo(Long id) throws HibernateException;
	public boolean updateTeacherVo(TeacherVo vo) throws HibernateException;
	
	//查询出所有的list列表
	public List<TeacherVo> queryAllTeacher() throws HibernateException;
}
