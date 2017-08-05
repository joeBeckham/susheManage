package com.business.job;

import java.util.List;
import java.util.TimerTask;

import javax.annotation.Resource;

import com.business.bean.Tab_Notice;
import com.business.services.I_NoticeManageService;

@SuppressWarnings("unused")
public class RecNoticeJob extends TimerTask{

	
	@Resource
	private I_NoticeManageService noticeService;
	
	private List<Tab_Notice> list = null;
	
	@Override
	public void run() {
	}

	
}
