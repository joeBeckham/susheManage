package com.business.dao;

import java.sql.ResultSet;
import java.util.List;

import org.hibernate.HibernateException;

import com.business.bean.Tab_Notice;
import com.business.util.PageBean;

/**
 * @ClassName: I_NoticeManageDao
 * @Description: TODO(消息 接口)
 * @author xbq
 * @date 2016-4-30 下午1:34:48
 *
 */
public interface I_NoticeManageDao {

	/**
	* @Title: qryNotNotice 
	* @Description: TODO(查询未查看信息)
	* @author xbq 
	* @param @return
	* @param @throws HibernateException
	* @return List<Tab_Notice>
	* @throws
	 */
	public ResultSet qryNotNotice(Tab_Notice notice,PageBean pageBean) throws HibernateException;
	
	public int NoticeCount() throws HibernateException;
	
	public boolean addNotice(Tab_Notice notice) throws HibernateException;
	
	public boolean uptNotice(Tab_Notice notice) throws HibernateException;
	
	public List<Tab_Notice> qryNotOkNotice(Tab_Notice notice) throws HibernateException;
	
	
}
