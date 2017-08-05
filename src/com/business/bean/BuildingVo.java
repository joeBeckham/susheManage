package com.business.bean;
//楼宇bean
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.business.util.BaseEntity;

@Entity
@Table(name="building")
public class BuildingVo extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="building_id",length=9,unique=true,nullable=false,scale=0)
	private Long building_id;
	@Column(name="building_name",length=22)
	private String building_name;
	@Column(name="building_remark",length=50)
	private String building_remark;
	
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
	public String getBuilding_remark() {
		return building_remark;
	}
	public void setBuilding_remark(String buildingRemark) {
		building_remark = buildingRemark;
	}
}
