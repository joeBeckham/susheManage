package com.business.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.business.util.BaseEntity;
@Entity
@Table(name="dorm")
public class DormVo extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="dorm_id",length=20,unique=true,nullable=false,scale=0)
	private Long dorm_id;
	@Column(name="dorm_name",length=20)
	private String dorm_name;
	@Column(name="dorm_type",length=10)
	private String dorm_type;
	@Column(name="dorm_people_num",length=10)
	private int dorm_people_num;
	@Column(name="dorm_tel",length=30)
	private String dorm_tel;
	
	@Column(name="dorm_building",length=10)
	private String dorm_building;
	
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
	public String getDorm_type() {
		return dorm_type;
	}
	public void setDorm_type(String dormType) {
		dorm_type = dormType;
	}
	public int getDorm_people_num() {
		return dorm_people_num;
	}
	public void setDorm_people_num(int dormPeopleNum) {
		dorm_people_num = dormPeopleNum;
	}
	public String getDorm_tel() {
		return dorm_tel;
	}
	public void setDorm_tel(String dormTel) {
		dorm_tel = dormTel;
	}
	public String getDorm_building() {
		return dorm_building;
	}
	public void setDorm_building(String dorm_building) {
		this.dorm_building = dorm_building;
	}
	
}
