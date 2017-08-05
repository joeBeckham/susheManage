package com.business.bean;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.business.util.BaseEntity;

@Entity
@Table(name="dorm_building")
public class DormBuildingVo extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "dorm_id", column = @Column(name = "dorm_id", precision = 9,nullable=true,unique=false, scale = 0)),
			@AttributeOverride(name = "building_id", column = @Column(name = "building_id", precision = 9,nullable=true,unique=false, scale = 0)) })
	private DormBuildingIDPk id;

	public DormBuildingIDPk getId() {
		return id;
	}
	public void setId(DormBuildingIDPk id) {
		this.id = id;
	}
	
	
}
