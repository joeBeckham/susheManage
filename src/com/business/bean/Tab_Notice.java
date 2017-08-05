package com.business.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.business.util.BaseEntity;

/**
 * @ClassName: Tab_Notice
 * @Description: TODO(消息实体类)
 * @author xbq
 * @date 2016-4-30 下午1:29:44
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="tab_notice")
public class Tab_Notice extends BaseEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id",length=9,unique=true,nullable=false,scale=0)
	private long id;
	private String title;
	private String content;
	private String send_userName;
	private String send_person;
	private String send_role;
	private String send_time;
	private String rec_userName;
	private String rec_person;
	private String rec_role;
	private String see_state;
	private String reply_state;
	private String ok_state;
	private String msg_type;
	private String guid;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSend_userName() {
		return send_userName;
	}
	public void setSend_userName(String send_userName) {
		this.send_userName = send_userName;
	}
	public String getSend_person() {
		return send_person;
	}
	public void setSend_person(String send_person) {
		this.send_person = send_person;
	}
	public String getSend_role() {
		return send_role;
	}
	public void setSend_role(String send_role) {
		this.send_role = send_role;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public String getRec_userName() {
		return rec_userName;
	}
	public void setRec_userName(String rec_userName) {
		this.rec_userName = rec_userName;
	}
	public String getRec_person() {
		return rec_person;
	}
	public void setRec_person(String rec_person) {
		this.rec_person = rec_person;
	}
	public String getRec_role() {
		return rec_role;
	}
	public void setRec_role(String rec_role) {
		this.rec_role = rec_role;
	}
	public String getSee_state() {
		return see_state;
	}
	public void setSee_state(String see_state) {
		this.see_state = see_state;
	}
	public String getReply_state() {
		return reply_state;
	}
	public void setReply_state(String reply_state) {
		this.reply_state = reply_state;
	}
	public String getOk_state() {
		return ok_state;
	}
	public void setOk_state(String ok_state) {
		this.ok_state = ok_state;
	}
	public String getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
}
