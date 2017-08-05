package com.business.bean;

import javax.persistence.Embeddable;

import com.business.util.BaseEntity;

@Embeddable
public class TeacherBuildingIDPk extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long teacher_id;
	private Long building_id;
	public Long getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(Long teacherId) {
		teacher_id = teacherId;
	}
	public Long getBuilding_id() {
		return building_id;
	}
	public void setBuilding_id(Long buildingId) {
		building_id = buildingId;
	}
	
}
