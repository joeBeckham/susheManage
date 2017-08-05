package com.business.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.business.util.BaseEntity;

@Entity
@Table(name="system")
public class SystemVo extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="system_id",length=9,nullable=false,unique=true)
	private Long system_id;
	@Column(name="system_userName",length=22)
	private String system_userName;
	@Column(name="system_userPass",length=55)
	private String system_userPass;
	public Long getSystem_id() {
		return system_id;
	}
	public void setSystem_id(Long systemId) {
		system_id = systemId;
	}
	public String getSystem_userName() {
		return system_userName;
	}
	public void setSystem_userName(String systemUserName) {
		system_userName = systemUserName;
	}
	public String getSystem_userPass() {
		return system_userPass;
	}
	public void setSystem_userPass(String systemUserPass) {
		system_userPass = systemUserPass;
	}
	
}
