package com.business.junit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.business.services.I_SystemManageService;
import com.business.util.Tools;

public class SystemManageServiceImplTest {

	static I_SystemManageService obj;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
		obj=(I_SystemManageService) applicationContext.getBean("systemService");
	}

	@Test
	public void testLogin() {
		String flag="system";
		String []arr=obj.login(flag,"admin","admin");
		if(!Tools.isEmpty(arr)){
			for(int i=0;i<arr.length;i++){
				System.out.println("arr["+i+"]===="+arr[i]);
			}
		}
	}
	
	@Test
	public void testCount() {
		int i=obj.LoginLogCount();
		System.out.println(i);
	}
}
