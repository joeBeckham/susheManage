package com.business.bean;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.business.util.BaseEntity;

@Entity
@Table(name="student_dorm")
public class StudentDormVo extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "student_id", column = @Column(name = "student_id", precision = 9, scale = 0)),
			@AttributeOverride(name = "dorm_id", column = @Column(name = "dorm_id", precision = 9, scale = 0)) })
	private StudentDormIDPk id;

	public StudentDormIDPk getId() {
		return id;
	}
	public void setId(StudentDormIDPk id) {
		this.id = id;
	}
	
}
