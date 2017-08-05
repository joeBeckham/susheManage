package com.business.junit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.business.bean.Tab_Notice;
import com.business.services.I_NoticeManageService;

/**
 * @ClassName: 
 * @Description: TODO()
 * @author xbq
 * @date 2016-5-1 上午11:08:55
 *
 */
public class NoticeManageServiceImplTest {

	private static I_NoticeManageService noticeService = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
		noticeService = (I_NoticeManageService) applicationContext.getBean("noticeService");
	}
	
	@Test
	public void testAddNotice() {
		Tab_Notice notice = new Tab_Notice();
		notice.setContent("1243");
		boolean flag = noticeService.addNotice(notice);
		System.out.println(flag);
	}
}
