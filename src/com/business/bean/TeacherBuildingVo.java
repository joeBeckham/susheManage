package com.business.bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.business.util.BaseEntity;

@Entity
@Table(name="teacher_buidling")
public class TeacherBuildingVo extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private TeacherBuildingIDPk id;

	public TeacherBuildingIDPk getId() {
		return id;
	}
	public void setId(TeacherBuildingIDPk id) {
		this.id = id;
	}
	
}
