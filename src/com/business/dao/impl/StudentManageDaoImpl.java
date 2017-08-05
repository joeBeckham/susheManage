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
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.business.bean.BuildingVo;
import com.business.bean.DormBuildingIDPk;
import com.business.bean.DormBuildingVo;
import com.business.bean.DormVo;
import com.business.bean.FixVo;
import com.business.bean.StudentDormIDPk;
import com.business.bean.StudentDormVo;
import com.business.bean.StudentVo;
import com.business.dao.I_StudentManageDao;
import com.business.util.ChangeNumberUtil;
import com.business.util.PageBean;
import com.business.util.Tools;
/**
 * 
 * @author xbq 
 * @version 1.0
 *
 */

@Repository("studentDao")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class StudentManageDaoImpl implements I_StudentManageDao {

	@Resource SessionFactory sessionFactory=null;
	@Resource JmsTemplate jmsTemplateNotice;//用于推送消息
	private Session session=null;
	Log log=LogFactory.getLog(this.getClass());
	
	/**
	 * 	增加学生
	 * */
	public boolean addStudent(StudentVo vo) throws HibernateException {
		boolean flag=false;
		log.info("[增加数据:用原生sql方法] 实现类开始");
		try{
			session=sessionFactory.getCurrentSession();
			String sql="insert into student(student_id,student_userName,student_name,student_sex,student_class,student_phone,student_state,student_remark,student_institution,student_major";
			if(vo.getStudent_headFlag() != null && !vo.getStudent_headFlag().equals("")){
				sql += ",student_headFlag) values(null,?,?,?,?,?,'未入住',?,?,?,?)";
			}else {
				sql += ") values(null,?,?,?,?,?,'未入住',?,?,?)";
			}
			SQLQuery sqlQuery=session.createSQLQuery(sql);
			int index=0;
			sqlQuery.setString(index++,vo.getStudent_userName());
			sqlQuery.setString(index++,vo.getStudent_name());
			sqlQuery.setString(index++,vo.getStudent_sex());
			sqlQuery.setString(index++,vo.getStudent_class());
			sqlQuery.setString(index++,vo.getStudent_phone());
			sqlQuery.setString(index++,vo.getStudent_remark());
			sqlQuery.setString(index++,vo.getStudent_institution());
			sqlQuery.setString(index++,vo.getStudent_major());
			if(vo.getStudent_headFlag() != null && !vo.getStudent_headFlag().equals("")){
				sqlQuery.setString(index++,vo.getStudent_headFlag());
			}
			int i=sqlQuery.executeUpdate();
			if(i>0){
				flag=true;
			}
			log.info("[增加数据:用原生sql方法] 实现类结束");
		}catch(Exception ex){
			log.error("[增加数据:用原生sql方法] 实现类异常");
			throw new HibernateException(ex);
		}
		return flag;
	}
	
	/**
	 * 	删除学生
	 * */
	//学生表（student）
	public boolean deleteStudent(Long id) throws HibernateException {
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="delete from StudentVo s where s.student_id=?";
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

	//学生-宿舍表（student_dorm）
	public boolean deleteStudent_Dorm(Long student_id) throws HibernateException{
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="delete from StudentDormVo sd where sd.id.student_id=?";
			Query query=session.createQuery(hql);
			query.setLong(0,student_id);
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
	 * 	分页
	 *  @return rs
	 *  @param vo,pageBean
	 *  采用原来的JDBC的方法
	 *  
	 * */
	@SuppressWarnings("deprecation")
	public ResultSet studentList(StudentVo vo,PageBean pageBean) throws HibernateException{
		PreparedStatement ps =null;
		ResultSet rs=null;
		try{
			session=sessionFactory.getCurrentSession();
		    Connection con = session.connection();
		    StringBuffer sql=new StringBuffer("select student_id,student_userName,student_userPass,student_name,student_sex,student_institution,student_major,student_building,student_dorm,student_class,student_phone,student_state,student_remark,out_date,student_headFlag from student where 1=1");
			if(null!=vo){
				if(!Tools.isEmpty(vo.getStudent_name())){
					sql.append(" and student_name like '%").append(vo.getStudent_name()).append("%'");
				}
				if(!Tools.isEmpty(vo.getStudent_state())){
					sql.append(" and student_state='").append(vo.getStudent_state()).append("'");
				}
				if(!Tools.isEmpty(vo.getStudent_institution())){
					sql.append(" and student_institution='").append(vo.getStudent_institution()).append("'");
				}
				if(!Tools.isEmpty(vo.getStudent_class())){
					sql.append(" and student_class='").append(vo.getStudent_class()).append("'");
				}
			}
			if(null!=pageBean){
				sql.append(" limit "+pageBean.getStart()+","+pageBean.getRows());
			}
		    ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return rs;
	}
	
	
	/**
	 * 	利用Hql查出总的记录数				
	 * */
	public int StudentCount() throws HibernateException{
		int i=0;
		try{
			session=sessionFactory.getCurrentSession();
			
			String hql=" from StudentVo";
			Query query = session.createQuery(hql);
	        i =query.list().size();
			
	        //两种方法都可以
//			String hql="select count(*) as total from StudentVo s";
//			Query query=session.createQuery(hql);
//			i=((Long)query.iterate().next()).longValue(); 	
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return i;
		
	}
	
	
	/**
	 * 	分页     缺勤记录  查询   用到
	 *  @return rs
	 *  @param vo,pageBean
	 *  采用原来的JDBC的方法
	 *  
	 * */
	@SuppressWarnings("deprecation")
	public ResultSet studentListLack(StudentVo vo,PageBean pageBean) throws HibernateException{
		PreparedStatement ps =null;
		ResultSet rs=null;
		try{
			session=sessionFactory.getCurrentSession();
		    Connection con = session.connection();
		    StringBuffer sql=new StringBuffer("select student_id,student_name,student_sex,student_institution,student_major,student_building,student_dorm,student_class,student_remark,student_lackBeginTime,student_lackEndTime,student_ifOk from student_lack where 1=1");
			if(null!=vo){
				if(!Tools.isEmpty(vo.getStudent_name())){
					sql.append(" and student_name like '%").append(vo.getStudent_name()).append("%'");
				}
			}
			sql.append(" group by student_lackBeginTime,student_lackEndTime");
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
	 * 	利用Hql查出总的记录数		
	 *   缺勤记录  查询   用到		
	 * */
	@SuppressWarnings("deprecation")
	public int StudentCountLack() throws HibernateException{
		PreparedStatement ps =null;
		ResultSet rs=null;
		Long count = null;
		int lackCount = 0;
		try{
			session=sessionFactory.getCurrentSession();
		    Connection con = session.connection();
		    StringBuffer sql=new StringBuffer("select count(student_id) as student_id from student_lack");
			
		    ps = con.prepareStatement(sql.toString());
		    rs = ps.executeQuery();
		    if(rs.next()){
		    	count = rs.getLong("student_id");
		    }
		    
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		} finally {
			if(count != null){
				lackCount = count.intValue();
			}
		}
		return lackCount;
	}
	
	/**
	 * 插入到 缺勤表
	 * @param vo
	 * @return
	 * @throws HibernateException
	 */
	public boolean addStudentLack(StudentVo vo) throws HibernateException {
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			String sql="insert into student_lack(student_id,student_name,student_sex,student_institution,student_major,student_class,student_building,student_dorm,student_lackBeginTime,student_lackEndTime,student_remark) values(null,?,?,?,?,?,?,?,?,?,?)";
			SQLQuery sqlQuery=session.createSQLQuery(sql);
			int index=0;
			sqlQuery.setString(index++,vo.getStudent_name());
			sqlQuery.setString(index++,vo.getStudent_sex());
			sqlQuery.setString(index++,vo.getStudent_institution());
			sqlQuery.setString(index++,vo.getStudent_major());
			sqlQuery.setString(index++,vo.getStudent_class());
			sqlQuery.setString(index++,vo.getStudent_building());
			sqlQuery.setString(index++,vo.getStudent_dorm());
			sqlQuery.setString(index++,vo.getStudent_lackBeginTime());
			sqlQuery.setString(index++,vo.getStudent_lackEndTime());
			sqlQuery.setString(index++, vo.getStudent_remark());
			int i=sqlQuery.executeUpdate();
			if(i>0){
				flag=true;
			}
		}catch(Exception ex){
			throw new HibernateException(ex);
		}
		return flag;
	}
	
	/**
	 * 	Hql 修改数据
	 * */
	public boolean updateStudentVo(StudentVo vo) throws HibernateException {
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="update StudentVo s set s.student_userName=?,s.student_name=?,s.student_sex=?,s.student_class=?,s.student_phone=?,s.student_remark=?,s.student_institution=?,s.student_major=?,s.student_headFlag=? where s.student_id=?";
			Query query=session.createQuery(hql);
			int index=0;
			query.setString(index++,vo.getStudent_userName());
			query.setString(index++,vo.getStudent_name());
			query.setString(index++,vo.getStudent_sex());
			query.setString(index++,vo.getStudent_class());
			query.setString(index++,vo.getStudent_phone());
			query.setString(index++,vo.getStudent_remark());
			query.setString(index++,vo.getStudent_institution());
			query.setString(index++,vo.getStudent_major());
			query.setString(index++,vo.getStudent_headFlag());
			query.setLong(index++,vo.getStudent_id());
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
	
	// 修改student_lack 表中的  是否确认 标识
	public boolean uptStudentLackOkFlag(String student_id) throws HibernateException{
		boolean flag = false;
		try {
			session = sessionFactory.getCurrentSession();
			String hql = "update student_lack set student_ifOk=? where student_id=?";
			Query query = session.createSQLQuery(hql);
			query.setString(0, "是");
			query.setString(1, student_id);
			int i = query.executeUpdate();
			if(i > 0){
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	// 查询student_lack 表中的  是否确认 标识
	@SuppressWarnings("deprecation")
	public List<String> qryIfOfFlag(String student_name)  throws HibernateException{
		PreparedStatement ps =null;
		ResultSet rs=null;
		String result = null;
		List<String> list = null;
		try{
			session=sessionFactory.getCurrentSession();
		    Connection con = session.connection();
		    StringBuffer sql=new StringBuffer("select student_ifOk from student_lack where student_name = ?");
		    ps = con.prepareStatement(sql.toString());
		    ps.setString(1, student_name);
		    rs = ps.executeQuery();
		    list = new ArrayList<String>();
		    while(rs.next()){
		    	result = new String();
		    	result = rs.getString("student_ifOk");
		    	list.add(result);
		    }
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		} 
		return list;
	}
	
	
	/**
	 * 	Hql 修改数据
	 *  回写学生表 （student）  缺勤标识
	 * */
	public boolean updateLackStudentVo(StudentVo vo) throws HibernateException {
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="update StudentVo s set s.student_lackFlag=? where s.student_id=?";
			Query query=session.createQuery(hql);
			int index=0;
			query.setInteger(index++,1);   // 1表示缺勤
			query.setLong(index++,vo.getStudent_id());
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
	 * 	根据id查询出其对应的Vo...状态：入住，未入住，迁出
	 * */
	public StudentVo getState(Long student_id) throws HibernateException {
		StudentVo vo=null;
		try{
			session=sessionFactory.getCurrentSession();
			vo=(StudentVo) session.get(StudentVo.class,student_id);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return vo;
	}
	
	/**
	 * 	获取学生列表：判断此学生是否存在和是否已经入住。还有进行寝室调换。还有迁出记录
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<?> StudentList(StudentVo vo) throws HibernateException {
		List inDormList=new ArrayList();
		try{
			session=sessionFactory.getCurrentSession();
			String hql="select s.student_id as student_id,d.dorm_id as dorm_id,b.building_id as building_id,s.student_userName as student_userName,s.student_name as student_name," +
					" s.student_sex as student_sex,s.student_class as student_class,s.student_phone as student_phone,s.out_date as out_date, " +
					" s.student_state as student_state,s.student_remark as student_remark,d.dorm_name as dorm_name," +
					" b.building_name as building_name from StudentVo s,DormVo d,BuildingVo b,StudentDormVo sd,DormBuildingVo db" +
					" where s.student_id=sd.id.student_id and d.dorm_id=db.id.dorm_id and b.building_id=db.id.building_id " +
					" and sd.id.dorm_id=db.id.dorm_id";
			//根据学号进行入住，寝室调换，迁出
			if(!Tools.isEmpty(vo.getStudent_userName())){
				hql+=" and s.student_userName='"+vo.getStudent_userName()+"'";
			}
			//迁出时候修改状态
			if(!Tools.isEmpty(vo.getStudent_state())){
				hql+=" and s.student_state='"+vo.getStudent_state()+"'";
			}
//			hql+=" order by s.student_id desc";
			Query query=session.createQuery(hql);
			List<Object[]> list=query.list();
			StudentVo stuVo=null;
			DormVo dormVo=null;
			BuildingVo buiVo=null;
			if(null!=list){
				for(int i=0;i<list.size();i++){
					Object[] arr=list.get(i);
					stuVo=new StudentVo();
					dormVo=new DormVo();
					buiVo=new BuildingVo();
					int index=0;
					stuVo.setStudent_id(Long.parseLong(String.valueOf(arr[index++])));
					dormVo.setDorm_id(Long.parseLong(String.valueOf(arr[index++])));
					buiVo.setBuilding_id(Long.parseLong(String.valueOf(arr[index++])));
					stuVo.setStudent_userName(String.valueOf(arr[index++]));
					stuVo.setStudent_name(String.valueOf(arr[index++]));
					stuVo.setStudent_sex(String.valueOf(arr[index++]));
					stuVo.setStudent_class(String.valueOf(arr[index++]));
					stuVo.setStudent_phone(String.valueOf(arr[index++]));
					stuVo.setOut_date(String.valueOf(arr[index++]));
					stuVo.setStudent_state(String.valueOf(arr[index++]));
					stuVo.setStudent_remark(String.valueOf(arr[index++]));
					dormVo.setDorm_name(String.valueOf(arr[index++]));
					buiVo.setBuilding_name(String.valueOf(arr[index++]));
					inDormList.add(stuVo);
					inDormList.add(dormVo);
					inDormList.add(buiVo);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return inDormList;
	}
	
	/**
	 * 	根据学号查询出对应的id
	 * */
	public List<?> queryStudent_Id(String student_userName) throws HibernateException {
		List<?> student_id=null;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="select student_id from StudentVo where student_userName=?";
			Query query=session.createQuery(hql);
			query.setString(0,student_userName);
			student_id=query.list();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return student_id;
	}
	
	/**
	 * 	学生入住登记
	 * */
	public boolean inDorm(Long student_id,Long dorm_id,Long building_id,String student_building,String student_dorm) throws HibernateException {
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			StudentDormVo vo1=new StudentDormVo();
			StudentDormIDPk pk1=new StudentDormIDPk();
			pk1.setStudent_id(student_id);
			pk1.setDorm_id(dorm_id);
			vo1.setId(pk1);
			
			DormBuildingVo vo2=new DormBuildingVo();
			DormBuildingIDPk pk2=new DormBuildingIDPk();
			pk2.setDorm_id(dorm_id);
			pk2.setBuilding_id(building_id);
			vo2.setId(pk2);
			
			session.merge(vo1);
			session.merge(vo2);
			
			student_building = this.queryBuildingName(building_id);
			student_dorm = this.queryDormName(dorm_id);
			
			String hql="update StudentVo s set s.student_state='入住',s.student_building='" + student_building + "',s.student_dorm='" + student_dorm + "' where s.student_id=?";
			Query query=session.createQuery(hql);
			query.setLong(0,student_id);
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
	 * 根据楼宇id查询出对应的楼宇名称
	 * @param building_id
	 * @return
	 * @throws HibernateException
	 */
	private String queryBuildingName(Long building_id) throws HibernateException {
		String building_name=null;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="select building_name from BuildingVo where building_id=?";
			Query query=session.createQuery(hql);
			query.setLong(0,building_id);
			List<?> buildingList = query.list();
			
			if(buildingList != null && buildingList.size() > 0){
				if(buildingList.get(0) != null){
					building_name= buildingList.get(0).toString();
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return building_name;
	}
	
	/**
	 * 根据宿舍id查询出对应的宿舍名称
	 * @param building_id
	 * @return
	 * @throws HibernateException
	 */
	private String queryDormName(Long dorm_id) throws HibernateException {
		String dorm_name=null;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="select dorm_name from DormVo where dorm_id=?";
			Query query=session.createQuery(hql);
			query.setLong(0,dorm_id);
			List<?> dormList = query.list();
			
			if(dormList != null && dormList.size() > 0){
				if(dormList.get(0) != null){
					dorm_name= dormList.get(0).toString();
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return dorm_name;
	}

	//查询出宿舍人数和宿舍类型（根据宿舍类型得到可住人数）
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int[] queryDorm_peole_num(Long dormId) throws HibernateException{
		int[] arrInt={0,0};
		int people=0;
		String dorm_type=null;
		List allList=new ArrayList();
		DormVo dvo=null;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="select d.dorm_people_num,d.dorm_type from DormVo d where d.dorm_id=?";
			Query query0=session.createQuery(hql);
			query0.setLong(0,dormId);
			List<Object[]> list=query0.list();
			if(list!=null&&list.size()>0){
				dvo=new DormVo();
				Object[] arr=list.get(0);
				dvo.setDorm_people_num(Integer.parseInt(String.valueOf(arr[0])));
				dvo.setDorm_type(String.valueOf(arr[1]));
				allList.add(dvo);
			}
			
			if(allList!=null&&allList.size()>0){
				dvo=(DormVo) allList.get(0);
				people=dvo.getDorm_people_num();
				dorm_type=dvo.getDorm_type();
				int maxPeople=ChangeNumberUtil.toChar(dorm_type);
				arrInt[0]=people;
				arrInt[1]=maxPeople;
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return arrInt;
	}
	
	//学生入住后，宿舍人数要+1
	public boolean updateDorm_peopleNum(Long dormId) throws HibernateException {
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="update DormVo d set d.dorm_people_num=d.dorm_people_num+1 where d.dorm_id=?";
			Query query=session.createQuery(hql);
			query.setLong(0,dormId);
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
	
	
	////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 	寝室调换，对查询出来的进行修改，要修改三张表的数据（先减在增），即为寝室调换
	 * */
	//一、修改student_dorm表
	public boolean exchangeDorm1(Long student_id,Long dorm_id) throws HibernateException {
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			this.deleteDormByStudent(student_id);
			flag=this.addDormByStudent(student_id, dorm_id);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}
	//1.1.先减
	private boolean deleteDormByStudent(Long student_id) throws HibernateException{
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="delete from StudentDormVo sd where sd.id.student_id=?";
			Query query=session.createQuery(hql);
			query.setLong(0,student_id);
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
	//1.2.再增
	private boolean addDormByStudent(Long student_id,Long dorm_id) throws HibernateException{
		boolean flag=true;
		try{
			session=sessionFactory.getCurrentSession();
			StudentDormVo vo=new StudentDormVo();
			StudentDormIDPk pk=new StudentDormIDPk();
			pk.setStudent_id(student_id);
			pk.setDorm_id(dorm_id);
			vo.setId(pk);
			session.persist(vo);
		}catch(Exception ex){
			flag=false;
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}
	
	//二、修改dorm_building表
	public boolean exchangeDorm2(Long dorm_id,Long building_id) throws HibernateException {
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			this.deleteBuildingByDorm(dorm_id);
			flag=this.addBuildingByDorm(dorm_id, building_id);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}
	//2.1.先减
	private boolean deleteBuildingByDorm(Long dorm_id) throws HibernateException{
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="delete from DormBuildingVo db where db.id.dorm_id=?";
			Query query=session.createQuery(hql);
			query.setLong(0,dorm_id);
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
	//2.2.再增
	private boolean addBuildingByDorm(Long dorm_id,Long building_id) throws HibernateException{
		boolean flag=true;
		try{
			session=sessionFactory.getCurrentSession();
			DormBuildingVo vo=new DormBuildingVo();
			DormBuildingIDPk pk=new DormBuildingIDPk();
			pk.setDorm_id(dorm_id);
			pk.setBuilding_id(building_id);
			vo.setId(pk);
			session.persist(vo);
		}catch(Exception ex){
			flag=false;
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}
	//修改备注
	public boolean exchangeDorm3(FixVo vo) throws HibernateException {
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			// 根据id 查询出 楼宇名称 和 宿舍名称
			String buildingName = this.queryBuildingName(vo.getBuilding_id());
			String dormName = this.queryDormName(vo.getDorm_id());
			String hql="update StudentVo s set s.student_remark=?,s.student_building=?,s.student_dorm=? where s.student_id=?";
			Query query=session.createQuery(hql);
			query.setString(0,vo.getStudent_remark());
			query.setString(1,buildingName);
			query.setString(2,dormName);
			query.setLong(3,vo.getStudent_id());
			int j=query.executeUpdate();
			
			if(j>0){
				flag=true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}
////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 	学生迁出登记:根据学生id对学生remark进行修改
	 * */
	public boolean outDorm(Long student_id,String out_date,String student_remark) throws HibernateException {
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="update StudentVo s set s.student_state='迁出',s.student_remark=?,s.out_date=?,s.student_building=?,s.student_dorm=? where s.student_id=?";
			Query query=session.createQuery(hql);
			query.setString(0,student_remark);
			query.setString(1,out_date);
			query.setString(2,"");
			query.setString(3,"");
			query.setLong(4,student_id);
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
	
	//删除中间表对应的数据            和上面的一个方法，合起来   作为学生迁出登记，并在services层中一起调用
	public boolean delete(Long student_id) throws HibernateException{
		boolean flag=false;
		try{
			flag=this.deleteDormByStudent(student_id);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}
	
	//学生迁出宿舍之后，宿舍人数-1
	public boolean deleteDorm_people_num(Long dorm_id){
		boolean flag=false;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="update DormVo d set d.dorm_people_num=d.dorm_people_num-1 where d.dorm_id=?";
			Query query=session.createQuery(hql);
			query.setLong(0,dorm_id);
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

	//根据student_id查询出对应的dorm_id
	public Long queryDormIdByStudentId(Long studentId) throws HibernateException {
		Long dormId=0L;
		try{
			session=sessionFactory.getCurrentSession();
			String hql="select sd.id.dorm_id from StudentDormVo sd where sd.id.student_id=?";
			Query query=session.createQuery(hql);
			query.setLong(0,studentId);
			List<?> list=query.list();
			if(list!=null&&list.size()>0){
				dormId=Long.parseLong(String.valueOf(list.get(0)));
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return dormId;
	}
	
	// 根据 学生 用户名 来查询 是否 是 班级负责人
	@SuppressWarnings("unchecked")
	public String qryVIPStudentByUserName(String userName) throws HibernateException{
		String result = null;
		try {
			session = sessionFactory.getCurrentSession();
			String hql = "select student_headFlag from student where student_userName = ?";
			Query query = session.createSQLQuery(hql);
			query.setString(0, userName);
			List<String> list = query.list();
			if(list != null && list.size() > 0 && (list != null && list.get(0) != null)){
				result = list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	* @Title: qryStudentIfOkByName 
	* @Description: TODO(根据学生姓名 来 查询 缺寝记录表 中的 是否确认标识)
	* @author xbq 
	* @date 2016-5-13 上午10:22:30
	* @param @param studentName
	* @param @return
	* @return String
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public String qryStudentIfOkByName(String studentName) throws HibernateException{
		String resultString = null;
		try {
			session = sessionFactory.getCurrentSession();
			String hql = "select student_ifOk from student_lack where student_name=?";
			Query query = session.createSQLQuery(hql);
			query.setString(1, studentName);
			List<String> list = query.list();
			if(list != null && list.size() > 0){
				resultString = list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}

}
