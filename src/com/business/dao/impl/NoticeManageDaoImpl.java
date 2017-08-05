package com.business.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.business.bean.Tab_Notice;
import com.business.dao.I_NoticeManageDao;
import com.business.util.PageBean;

/**
 * @ClassName: 
 * @Description: TODO()
 * @author xbq
 * @date 2016-4-30 下午1:39:37
 *
 */
@Repository("noticeDao")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class NoticeManageDaoImpl implements I_NoticeManageDao{

	@Resource
	private SessionFactory sessionFactory;
	private Session session = null;
	private static boolean flag = false;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@SuppressWarnings("deprecation")
	public ResultSet qryNotNotice(Tab_Notice notice,PageBean pageBean) throws HibernateException {
		PreparedStatement ps =null;
		ResultSet rs=null;
		Connection connection = null;
		try {
			session = sessionFactory.getCurrentSession();
			connection = session.connection();
			String sql = "select id,title,content,send_userName,send_person,send_role,send_time,rec_userName,rec_person,rec_role,see_state,reply_state,ok_state,msg_type,guid from tab_notice where 1=1";
			StringBuffer sb = new StringBuffer(sql);
			if(notice != null){
				if(notice.getSend_userName() != null){
					sb.append(" and send_userName='"+notice.getSend_userName()+"'");
				}
				if(notice.getSend_person() != null){
					sb.append(" and send_person='"+notice.getSend_person()+"'");
				}
				if(notice.getRec_userName() != null){
					sb.append(" and rec_userName='"+notice.getRec_userName()+"'");
				}
				if(notice.getRec_role() != null){
					sb.append(" and rec_role='"+notice.getRec_role()+"'");
				}
				if(notice.getSee_state() != null){
					sb.append(" and see_state='"+notice.getSee_state()+"'");
				}
			}
			if(null!=pageBean){
				sb.append(" limit "+pageBean.getStart()+","+pageBean.getRows());
			}
			ps = connection.prepareStatement(sb.toString());
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public int NoticeCount() throws HibernateException{
		int i=0;
		try{
			session=sessionFactory.getCurrentSession();
			String hql=" from Tab_Notice";
			Query query = session.createQuery(hql);
	        i =query.list().size();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return i;
	}

	public boolean addNotice(Tab_Notice notice) throws HibernateException {
		try {
			session = sessionFactory.getCurrentSession();
			String hql = "insert into Tab_Notice(id,title,content,send_userName,send_person,send_role,send_time,rec_userName,rec_person,rec_role,msg_type,guid) values(null,?,?,?,?,?,?,?,?,?,?,?)";
			Query query = session.createSQLQuery(hql);
			int index = 0;
			query.setString(index++, notice.getTitle());
			query.setString(index++, notice.getContent());
			query.setString(index++, notice.getSend_userName());
			query.setString(index++, notice.getSend_person());
			query.setString(index++, notice.getSend_role());
			query.setString(index++, sdf.format(new Date()));
			query.setString(index++, notice.getRec_userName());
			query.setString(index++, notice.getRec_person());
			query.setString(index++, notice.getRec_role());
			query.setString(index++, notice.getMsg_type());
			query.setString(index++, UUID.randomUUID().toString());
			int i = query.executeUpdate();
			if(i > 0){
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 修改 某条消息的 状态
	 */
	public boolean uptNotice(Tab_Notice notice) throws HibernateException {
		try {
			session = sessionFactory.getCurrentSession();
			String hql = "update Tab_Notice set see_state=? where guid=?";
			Query query = session.createQuery(hql);
			query.setString(0, notice.getSee_state());
			query.setString(1, notice.getGuid());
			int i = query.executeUpdate();
			if(i > 0){
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	/**
	 * 查看 楼宇 管理员 没有 查看  的 信息
	 */
	@SuppressWarnings("deprecation")
	public List<Tab_Notice> qryNotOkNotice(Tab_Notice notice) throws HibernateException{
		List<Tab_Notice> list = null;
		Tab_Notice vo = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			session = sessionFactory.getCurrentSession();
			connection = session.connection();
			StringBuffer sql = new StringBuffer("select id,title,content,send_userName,send_person,send_role,send_time,rec_userName,rec_person,rec_role,see_state,reply_state,ok_state,msg_type,guid from tab_notice where 1=1");
			sql.append(" and see_state='否'");
			if(notice != null){
				if(notice.getRec_userName() != null){
					sql.append(" and rec_userName='"+notice.getRec_userName()+"'");
				}
				if(notice.getRec_role() != null){
					sql.append(" and rec_role='"+notice.getRec_role()+"'");
				}
			}
			ps = connection.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			list = new ArrayList<Tab_Notice>();
			int index = 0;
			while(rs.next()){
				index = 1;
				vo = new Tab_Notice();
				vo.setId(rs.getLong(index ++));
				vo.setTitle(rs.getString(index ++));
				vo.setContent(rs.getString(index ++));
				vo.setSend_userName(rs.getString(index ++));
				vo.setSend_person(rs.getString(index ++));
				vo.setSend_role(rs.getString(index ++));
				vo.setSend_time(rs.getString(index ++));
				vo.setRec_userName(rs.getString(index ++));
				vo.setRec_person(rs.getString(index ++));
				vo.setRec_role(rs.getString(index ++));
				vo.setSee_state(rs.getString(index ++));
				vo.setReply_state(rs.getString(index ++));
				vo.setOk_state(rs.getString(index ++));
				vo.setMsg_type(rs.getString(index));
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
}
