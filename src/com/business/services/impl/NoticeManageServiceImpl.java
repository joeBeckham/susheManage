package com.business.services.impl;

import java.sql.ResultSet;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import com.business.bean.Tab_Notice;
import com.business.dao.I_NoticeManageDao;
import com.business.services.I_NoticeManageService;
import com.business.util.PageBean;

/**
 * @ClassName: NoticeManageServiceImpl
 * @Description: TODO()
 * @author xbq
 * @date 2016-5-1 上午11:03:43
 *
 */
@Service("noticeService")
public class NoticeManageServiceImpl implements I_NoticeManageService{

	@Resource
	private I_NoticeManageDao noticeDao;
	private  static boolean flag = false;
	
	public ResultSet qryNotNotice(Tab_Notice notice,PageBean pageBean) throws HibernateException {
		ResultSet rs = null;
		rs = noticeDao.qryNotNotice(notice,pageBean);
		return rs;
	}
	
	public int NoticeCount() throws HibernateException{
		int i = noticeDao.NoticeCount();
		return i;
	}

	public boolean addNotice(Tab_Notice notice) throws HibernateException {
		flag = noticeDao.addNotice(notice);
		return flag;
	}

	public boolean uptNotice(Tab_Notice notice) throws HibernateException {
		flag = noticeDao.uptNotice(notice);
		return flag;
	}
	
	public List<Tab_Notice> qryNotOkNotice(Tab_Notice notice) throws HibernateException{
		List<Tab_Notice> list = noticeDao.qryNotOkNotice(notice);
		return list;
	}

}
