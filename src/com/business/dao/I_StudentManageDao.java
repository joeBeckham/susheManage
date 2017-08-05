package com.business.dao;

import java.sql.ResultSet;
import java.util.List;

import org.hibernate.HibernateException;

import com.business.bean.FixVo;
import com.business.bean.StudentVo;
import com.business.util.PageBean;

public interface I_StudentManageDao {
	public boolean addStudent(StudentVo vo) throws HibernateException;
	
	public boolean deleteStudent(Long id) throws HibernateException;
	public boolean deleteStudent_Dorm(Long student_id) throws HibernateException;
	
	public boolean updateStudentVo(StudentVo vo) throws HibernateException;
	
	
	public ResultSet studentList(StudentVo vo,PageBean pageBean) throws HibernateException;
	public int StudentCount() throws HibernateException;
	
	/**
	 * 缺勤记录 查询
	 * @param vo
	 * @param pageBean
	 * @return
	 * @throws HibernateException
	 */
	public ResultSet studentListLack(StudentVo vo,PageBean pageBean) throws HibernateException;
	public int StudentCountLack() throws HibernateException;
	// 插入到 缺勤表
	public boolean addStudentLack(StudentVo vo) throws HibernateException;
	// 回写 student 表的  缺勤 标识
	public boolean updateLackStudentVo(StudentVo vo) throws HibernateException;
	// 修改student_lack 表中的  是否确认 标识
	public boolean uptStudentLackOkFlag(String student_id) throws HibernateException;
	// 查询student_lack 表中的  是否确认 标识
	public List<String> qryIfOfFlag(String student_name)  throws HibernateException;
	
	
	/*获取学生列表：判断此学生是否存在和是否已经入住。还有进行寝室调换。还有迁出记录*/
	public List<?> StudentList(StudentVo vo) throws HibernateException;
	/*根据学号查询出对应的id*/
	public List<?> queryStudent_Id(String student_userName) throws HibernateException;
	
	/*入住登记*/
	public boolean inDorm(Long student_id,Long dorm_id,Long building_id,String student_building,String student_dorm) throws HibernateException;
	//查询出宿舍人数和宿舍类型
	public int[] queryDorm_peole_num(Long dormId)  throws HibernateException;
	//学生入住后，宿舍人数要+1
	public boolean updateDorm_peopleNum(Long dorm_id) throws HibernateException;
	
	//根据student_id查询出对应的dorm_id
	public Long queryDormIdByStudentId(Long student_id) throws HibernateException;
	
	/*根据id查询出其对应的状态：入住，未入住，迁出*/
	public StudentVo getState(Long student_id) throws HibernateException;
	/*寝室调换登记*/
	public boolean exchangeDorm1(Long student_id,Long dorm_id) throws HibernateException;
	public boolean exchangeDorm2(Long dorm_id,Long building_id) throws HibernateException;
	public boolean exchangeDorm3(FixVo vo) throws HibernateException;
	/*学生迁出登记*/
	public boolean delete(Long student_id) throws HibernateException;
	public boolean outDorm(Long student_id,String out_date,String student_remark) throws HibernateException;
	
	//迁出之后的操作
	public boolean deleteDorm_people_num(Long dorm_id) throws HibernateException;
	
	// 根据 学生 用户名 来查询 是否 是 班级负责人
	public String qryVIPStudentByUserName(String userName) throws HibernateException;
	
}
