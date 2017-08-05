package com.business.bean;
//楼宇管理员bean
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.business.util.BaseEntity;

@Entity
@Table(name="teacher")
public class TeacherVo extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="teacher_id",length=9,unique=true,nullable=false,scale=0)
	private Long teacher_id;
	@Column(name="teacher_userName",length=22)
	private String teacher_userName;
	@Column(name="teacher_userPass",length=22)
	private String teacher_userPass;
	@Column(name="teacher_name",length=22)
	private String teacher_name;
	@Column(name="teacher_sex",length=5)
	private String teacher_sex;
	@Column(name="teacher_tel",length=22)
	private String teacher_tel;
	
	public Long getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(Long teacherId) {
		teacher_id = teacherId;
	}
	public String getTeacher_userName() {
		return teacher_userName;
	}
	public void setTeacher_userName(String teacherUserName) {
		teacher_userName = teacherUserName;
	}
	public String getTeacher_userPass() {
		return teacher_userPass;
	}
	public void setTeacher_userPass(String teacherUserPass) {
		teacher_userPass = teacherUserPass;
	}
	public String getTeacher_name() {
		return teacher_name;
	}
	public void setTeacher_name(String teacherName) {
		teacher_name = teacherName;
	}
	public String getTeacher_sex() {
		return teacher_sex;
	}
	public void setTeacher_sex(String teacherSex) {
		teacher_sex = teacherSex;
	}
	public String getTeacher_tel() {
		return teacher_tel;
	}
	public void setTeacher_tel(String teacherTel) {
		teacher_tel = teacherTel;
	}
}
