package com.business.bean;

import javax.persistence.Embeddable;

import com.business.util.BaseEntity;

@Embeddable
public class DormBuildingIDPk extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long dorm_id;
	private Long building_id;
	public Long getDorm_id() {
		return dorm_id;
	}
	public void setDorm_id(Long dormId) {
		dorm_id = dormId;
	}
	public Long getBuilding_id() {
		return building_id;
	}
	public void setBuilding_id(Long buildingId) {
		building_id = buildingId;
	}
	
}
