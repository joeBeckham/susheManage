package com.business.bean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.business.util.BaseEntity;

/**
 * @author xbq
 * 学生bean
 */
@SuppressWarnings("serial")
@Entity
@Table(name="student")
public class StudentVo extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="student_id",length=9,unique=true,nullable=false,scale=0)
	private Long student_id;
	@Column(name="student_userName",length=22)
	private String student_userName;
	@Column(name="student_userPass",length=22)
	private String student_userPass;
	@Column(name="student_name",length=22)
	private String student_name;
	@Column(name="student_sex",length=5)
	private String student_sex;
	@Column(name="student_class",length=22)
	private String student_class;
	@Column(name="student_phone",length=22)
	private String student_phone;
	@Column(name="student_state",length=10)
	private String student_state;
	@Column(name="student_remark",length=50)
	private String student_remark;
	@Column(name="out_date",length=50)
	private String out_date;
	
	@Column(name="student_institution",length=20)
	private String student_institution;
	@Column(name="student_major",length=20)
	private String student_major;
	@Column(name="student_building",length=20)
	private String student_building;
	@Column(name="student_dorm",length=10)
	private String student_dorm;
	
	@Column(name="student_lackFlag",length=1)
	private Integer student_lackFlag;
	@Column(name="student_lackBeginTime",length=30)
	private String student_lackBeginTime;
	@Column(name="student_lackEndTime",length=30)
	private String student_lackEndTime;
	@Column(name="student_headFlag",length=10)
	private String student_headFlag;//是否班级负责人
	
	private String student_ifOk; // 是否确认
	

	public Long getStudent_id() {
		return student_id;
	}
	public void setStudent_id(Long studentId) {
		student_id = studentId;
	}
	public String getStudent_phone() {
		return student_phone;
	}
	public void setStudent_phone(String studentPhone) {
		student_phone = studentPhone;
	}
	public String getStudent_remark() {
		return student_remark;
	}
	public void setStudent_remark(String studentRemark) {
		student_remark = studentRemark;
	}
	public String getStudent_userName() {
		return student_userName;
	}
	public void setStudent_userName(String studentUserName) {
		student_userName = studentUserName;
	}
	public String getStudent_userPass() {
		return student_userPass;
	}
	public void setStudent_userPass(String studentUserPass) {
		student_userPass = studentUserPass;
	}
	public String getStudent_name() {
		return student_name;
	}
	public void setStudent_name(String studentName) {
		student_name = studentName;
	}
	public String getStudent_sex() {
		return student_sex;
	}
	public void setStudent_sex(String studentSex) {
		student_sex = studentSex;
	}
	public String getStudent_class() {
		return student_class;
	}
	public void setStudent_class(String studentClass) {
		student_class = studentClass;
	}
	public String getStudent_state() {
		return student_state;
	}
	public void setStudent_state(String studentState) {
		student_state = studentState;
	}
	public String getOut_date() {
		return out_date;
	}
	public void setOut_date(String outDate) {
		out_date = outDate;
	}
	public String getStudent_institution() {
		return student_institution;
	}
	public void setStudent_institution(String student_institution) {
		this.student_institution = student_institution;
	}
	public String getStudent_major() {
		return student_major;
	}
	public void setStudent_major(String student_major) {
		this.student_major = student_major;
	}
	public String getStudent_building() {
		return student_building;
	}
	public void setStudent_building(String student_building) {
		this.student_building = student_building;
	}
	public String getStudent_dorm() {
		return student_dorm;
	}
	public void setStudent_dorm(String student_dorm) {
		this.student_dorm = student_dorm;
	}
	public Integer getStudent_lackFlag() {
		return student_lackFlag;
	}
	public void setStudent_lackFlag(Integer student_lackFlag) {
		this.student_lackFlag = student_lackFlag;
	}
	public String getStudent_lackBeginTime() {
		return student_lackBeginTime;
	}
	public void setStudent_lackBeginTime(String student_lackBeginTime) {
		this.student_lackBeginTime = student_lackBeginTime;
	}
	public String getStudent_lackEndTime() {
		return student_lackEndTime;
	}
	public void setStudent_lackEndTime(String student_lackEndTime) {
		this.student_lackEndTime = student_lackEndTime;
	}
	public String getStudent_headFlag() {
		return student_headFlag;
	}
	public void setStudent_headFlag(String student_headFlag) {
		this.student_headFlag = student_headFlag;
	}
	public String getStudent_ifOk() {
		return student_ifOk;
	}
	public void setStudent_ifOk(String student_ifOk) {
		this.student_ifOk = student_ifOk;
	}
	
}
