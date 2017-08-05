package com.business.bean;

import com.business.util.BaseEntity;

/*这个数混合的VO*/
public class FixVo extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long student_id;
	private String student_userName;
	private String student_name;
	private String student_sex;
	private String student_remark;
	private String out_date;
	private Long dorm_id;
	private String dorm_name;
	private Long building_id;
	private String building_name;
	
	
	public Long getStudent_id() {
		return student_id;
	}
	public void setStudent_id(Long studentId) {
		student_id = studentId;
	}
	public String getStudent_userName() {
		return student_userName;
	}
	public void setStudent_userName(String studentUserName) {
		student_userName = studentUserName;
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
	public Long getDorm_id() {
		return dorm_id;
	}
	public void setDorm_id(Long dormId) {
		dorm_id = dormId;
	}
	public String getDorm_name() {
		return dorm_name;
	}
	public void setDorm_name(String dormName) {
		dorm_name = dormName;
	}
	public Long getBuilding_id() {
		return building_id;
	}
	public void setBuilding_id(Long buildingId) {
		building_id = buildingId;
	}
	public String getBuilding_name() {
		return building_name;
	}
	public void setBuilding_name(String buildingName) {
		building_name = buildingName;
	}
	public String getStudent_remark() {
		return student_remark;
	}
	public void setStudent_remark(String studentRemark) {
		student_remark = studentRemark;
	}
	public String getOut_date() {
		return out_date;
	}
	public void setOut_date(String outDate) {
		out_date = outDate;
	}
	
}
