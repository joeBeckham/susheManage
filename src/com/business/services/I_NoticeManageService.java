package com.business.services;

import java.sql.ResultSet;
import java.util.List;

import org.hibernate.HibernateException;

import com.business.bean.Tab_Notice;
import com.business.util.PageBean;

/**
 * @ClassName: 
 * @Description: TODO()
 * @author xbq
 * @date 2016-5-1 上午11:02:26
 *
 */
public interface I_NoticeManageService{

	/**
	* @Title: qryNotNotice 
	* @Description: TODO(查询未查看信息)
	* @author xbq 
	* @param @return
	* @param @throws HibernateException
	* @return ResultSet
	* @throws
	 */
	public ResultSet qryNotNotice(Tab_Notice notice,PageBean pageBean) throws HibernateException;
	
	public int NoticeCount() throws HibernateException;
	
	public boolean addNotice(Tab_Notice notice) throws HibernateException;
	
	public boolean uptNotice(Tab_Notice notice) throws HibernateException;
	
	public List<Tab_Notice> qryNotOkNotice(Tab_Notice notice) throws HibernateException;
}
