package com.business.bean;

import javax.persistence.Embeddable;

import com.business.util.BaseEntity;

@Embeddable
public class StudentDormIDPk extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long student_id;
	private Long dorm_id;
	public Long getStudent_id() {
		return student_id;
	}
	public void setStudent_id(Long studentId) {
		student_id = studentId;
	}
	public Long getDorm_id() {
		return dorm_id;
	}
	public void setDorm_id(Long dormId) {
		dorm_id = dormId;
	}
	
}
