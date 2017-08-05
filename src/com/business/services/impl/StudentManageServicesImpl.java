package com.business.services.impl;

import java.sql.ResultSet;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.business.bean.FixVo;
import com.business.bean.StudentVo;
import com.business.dao.I_StudentManageDao;
import com.business.services.I_StudentManageServices;
import com.business.util.PageBean;

@Service("studentService")
public class StudentManageServicesImpl implements I_StudentManageServices {

	@Resource I_StudentManageDao studentDao=null;
	boolean flag=false;
	
	public int StudentCount() throws HibernateException {
		int i=0;
		try{
			i=studentDao.StudentCount();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return i;
	}

	public boolean addStudent(StudentVo vo) throws HibernateException {
		try{
			flag=studentDao.addStudent(vo);
		}catch(Exception ex){
			throw new HibernateException(ex);
		}
		return flag;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean deleteStudentAndStudentDorm(Long id) throws HibernateException {
		try{
			flag=studentDao.deleteStudent(id);
			studentDao.deleteStudent_Dorm(id);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}

	public ResultSet studentList(StudentVo vo, PageBean pageBean)
			throws HibernateException {
		ResultSet rs=null;
		try{
			rs=studentDao.studentList(vo, pageBean);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return rs;
	}
	
	public ResultSet studentLackList(StudentVo vo, PageBean pageBean)
			throws HibernateException {
		ResultSet rs=null;
		try{
			rs=studentDao.studentListLack(vo, pageBean);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return rs;
	}
	
	public int StudentLackCount() throws HibernateException {
		int i=0;
		try{
			i=studentDao.StudentCountLack();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return i;
	}
	
	/**
	 * 插入到 缺勤表的同时，回写学生表的缺勤标识
	 * 同一事务，保证同时成功，或者同时失败
	 * @param vo
	 * @return
	 * @throws HibernateException
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean addStudentLack(StudentVo vo) throws HibernateException{
		boolean flag1 = false;
		boolean flag2 = false;
		try{
			flag1 = studentDao.addStudentLack(vo);
			flag2 = studentDao.updateLackStudentVo(vo);
			if(flag1 && flag2){
				flag = true;
			}
		}catch(Exception ex){
			throw new HibernateException(ex);
		}
		return flag;
	}
	
	// 修改student_lack 表中的  是否确认 标识
	public boolean uptStudentLackOkFlag(String student_id) throws HibernateException{
		boolean flag = studentDao.uptStudentLackOkFlag(student_id);
		return flag;
	}
	
	// 查询student_lack 表中的  是否确认 标识
	public List<String> qryIfOfFlag(String student_name)  throws HibernateException{
		List<String> result = studentDao.qryIfOfFlag(student_name);
		return result;
	}

	public boolean updateStudentVo(StudentVo vo) throws HibernateException {
		try{
			flag=studentDao.updateStudentVo(vo);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}
	
	public boolean updateLackStudentVo(StudentVo vo) throws HibernateException {
		try{
			flag=studentDao.updateLackStudentVo(vo);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}

	public List<?> StudentList(StudentVo vo) throws HibernateException {
		List<?> list=null;
		try{
			list=studentDao.StudentList(vo);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return list;
	}


	public List<?> queryStudent_Id(String studentUserName) throws HibernateException {
		List<?> student_Idlist=null;
		try{
			student_Idlist=studentDao.queryStudent_Id(studentUserName);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return student_Idlist;
	}
	
	public boolean exchangeDorm(Long studentId, Long dormId, Long buildingId,FixVo vo) throws HibernateException {
		try{
			boolean flag1=studentDao.exchangeDorm1(studentId,dormId);
			boolean flag2=studentDao.exchangeDorm2(dormId,buildingId);
			boolean flag3=studentDao.exchangeDorm3(vo);
			flag=flag1&&flag2&&flag3;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return flag;
	}

	//根据查询出的宿舍人数 来 进行是否入住
	public boolean ifDorm(Long dormId) throws HibernateException{
		boolean flag=false;
		try{
			int[] arr=studentDao.queryDorm_peole_num(dormId);
			if(arr.length>0&&arr!=null){
				int people=arr[0];
				int maxPeople=arr[1];
				if(people<maxPeople){
					flag=studentDao.updateDorm_peopleNum(dormId);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}
	
	public boolean inDorm(Long studentId, Long dormId, Long buildingId,String student_building,String student_dorm) throws HibernateException {
		try{
			flag=studentDao.inDorm(studentId, dormId, buildingId, student_building ,student_dorm);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return flag;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean outDorm(Long studentId,String outDate, String studentRemark) throws HibernateException {
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		try{
			flag1 = studentDao.outDorm(studentId, outDate, studentRemark);
			// 学生迁出后先得到对应的dorm_id，然后人数-1
			flag2 = this.aftetOutDorm(studentId);
			flag3 = studentDao.delete(studentId);
			if(flag1 && flag2 && flag3){
				flag = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return flag;
	}

	public StudentVo getState(Long studentId) throws HibernateException {
		StudentVo vo=null;
		try{
			vo=studentDao.getState(studentId);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return vo;
	}

	//学生迁出后先得到对应的dorm_id，然后人数-1
	public boolean aftetOutDorm(Long studentId) throws HibernateException {
		boolean flag=false;
		try{
			Long dormId=studentDao.queryDormIdByStudentId(studentId);
			if(dormId!=0){
				flag=studentDao.deleteDorm_people_num(dormId);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		return flag;
	}
	
	
	// 根据 学生 用户名 来查询 是否 是 班级负责人
	public String qryVIPStudentByUserName(String userName) throws HibernateException{
		String result = studentDao.qryVIPStudentByUserName(userName);
		return result;
	}

}
