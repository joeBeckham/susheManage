package com.business.services;

import java.sql.ResultSet;
import java.util.List;

import org.hibernate.HibernateException;

import com.business.bean.FixVo;
import com.business.bean.StudentVo;
import com.business.util.PageBean;

public interface I_StudentManageServices {
	public boolean addStudent(StudentVo vo) throws HibernateException;
	public boolean deleteStudentAndStudentDorm(Long id) throws HibernateException;
	public boolean updateStudentVo(StudentVo vo) throws HibernateException;
	
	// 缺勤记录中用到
	public boolean updateLackStudentVo(StudentVo vo) throws HibernateException;
	// 缺勤 记录 查询
	public ResultSet studentLackList(StudentVo vo, PageBean pageBean) throws HibernateException;
	public int StudentLackCount() throws HibernateException;
	// 插入到 缺勤表
	public boolean addStudentLack(StudentVo vo) throws HibernateException;
	// 修改student_lack 表中的  是否确认 标识
	public boolean uptStudentLackOkFlag(String student_id) throws HibernateException;
	// 查询student_lack 表中的  是否确认 标识
	public List<String> qryIfOfFlag(String student_name)  throws HibernateException;
	
	public ResultSet studentList(StudentVo vo,PageBean pageBean) throws HibernateException;
	public int StudentCount() throws HibernateException;
	
	///////////////////////////////////////
	/*获取学生列表：判断此学生是否存在和是否已经入住。还有进行寝室调换。还有迁出记录*/
	public List<?> StudentList(StudentVo vo) throws HibernateException;
	/*根据学号查询出对应的id*/
	public List<?> queryStudent_Id(String student_userName) throws HibernateException;
	/*根据id查询出其对应的状态：入住，未入住，迁出*/
	public StudentVo getState(Long student_id) throws HibernateException;
	/*入住登记*/
	public boolean inDorm(Long student_id,Long dorm_id,Long building_id,String student_building,String student_dorm) throws HibernateException;
	//根据查询出的宿舍人数 来 进行是否入住
	public boolean ifDorm(Long dormId) throws HibernateException;
	
	/*寝室调换登记*/
	public boolean exchangeDorm(Long student_id,Long dorm_id,Long building_id,FixVo vo) throws HibernateException;
	
	/*学生迁出登记*/
	public boolean outDorm(Long student_id,String out_date,String student_remark) throws HibernateException;
	//学生迁出后先得到对应的dorm_id，然后人数-1
	public boolean aftetOutDorm(Long student_id) throws HibernateException;
	
	// 根据 学生 用户名 来查询 是否 是 班级负责人
	public String qryVIPStudentByUserName(String userName) throws HibernateException;
	
}
