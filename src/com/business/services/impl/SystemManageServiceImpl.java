package com.business.services.impl;

import java.sql.ResultSet;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import com.business.bean.LoginLogVo;
import com.business.dao.I_SystemManageDao;
import com.business.services.I_SystemManageService;
import com.business.util.PageBean;

@Service("systemService")
public class SystemManageServiceImpl implements I_SystemManageService {

	@Resource I_SystemManageDao systemDao=null;
	
	public String[] login(String flag, String userName, String pass) throws HibernateException {
		String[] arr={"","",""};
		try{
			arr=systemDao.login(flag, userName, pass);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return arr;
	}

	public boolean insertLoginLog(LoginLogVo log) throws HibernateException {
		boolean flag=false;
		try{
			flag=systemDao.insertLoginLog(log);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}

	public int LoginLogCount() throws HibernateException {
		int i=0;
		try{
			i=systemDao.LoginLogCount();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return i;
	}

	public ResultSet logList(LoginLogVo vo, PageBean pageBean)
			throws HibernateException {
		ResultSet rs=null;
		try{
			rs=systemDao.logList(vo, pageBean);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return rs;
	}

	public boolean updateLoginLog(LoginLogVo vo) throws HibernateException {
		boolean flag=false;
		try{
			flag=systemDao.updateLoginLog(vo);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}

	public boolean deleteLoginLog(Long id) throws HibernateException {
		boolean flag=false;
		try{
			flag=systemDao.deleteLoginLog(id);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}

	public boolean updatePassWord(String pass, String roleFlag,String loginId) throws HibernateException {
		boolean flag=false;
		try{
			flag=systemDao.updatePassWord(pass, roleFlag, loginId);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}

	public boolean checkPass(String userId, String newPass,String roleFlag)
			throws HibernateException {
		boolean flag = systemDao.checkPass(userId, newPass, roleFlag);
		return flag;
	}
}
