package com.business.util;

public class PageBean {
	private int page;	//第几页
	private int rows;	//每页的记录数
	@SuppressWarnings("unused")
	private int start;	//开始页面
	
	public PageBean(int page,int rows){
		this.page=page;
		this.rows=rows;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public int getStart() {
		return (page-1)*rows;
	}
	
	
}
