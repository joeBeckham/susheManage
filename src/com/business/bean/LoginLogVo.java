package com.business.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.business.util.BaseEntity;

@Entity
@Table(name="loginlog")
public class LoginLogVo extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Long userId;			//用户id
	private String userName;		//用户名称
	private String loginDate;		//登陆时间
	private String loginIP;			//登陆IP
	private String iPaddress;		//IP所属地
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
	public String getLoginIP() {
		return loginIP;
	}
	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}
	public String getIPaddress() {
		return iPaddress;
	}
	public void setIPaddress(String iPaddress) {
		this.iPaddress = iPaddress;
	}

}
