package com.business.dao;

import java.sql.ResultSet;

import org.hibernate.HibernateException;

import com.business.bean.LoginLogVo;
import com.business.util.PageBean;

public interface I_SystemManageDao {
	
	//根据学号和密码查询出其状态、是否为空、密码等是否有误
	public String[] login(String flag,String userName,String pass) throws HibernateException;
	
	public int LoginLogCount() throws HibernateException;
	//查询登录日志
	public ResultSet logList(LoginLogVo vo, PageBean pageBean) throws HibernateException;
	//添加登录日志
	public boolean insertLoginLog(LoginLogVo log) throws HibernateException;
	
	public boolean updateLoginLog(LoginLogVo vo) throws HibernateException;
	public boolean deleteLoginLog(Long id) throws HibernateException;
	
	//修改密码
	public boolean updatePassWord(String pass,String roleFlag,String userName) throws HibernateException;
	
	// 验证原密码 , 根据用户名 查询 密码
	public boolean checkPass(String userId,String newPass,String roleFlag) throws HibernateException;
}
