package com.business.junit;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.business.bean.DormVo;
import com.business.services.I_DormManageService;

public class DormManageImplTest {

	static I_DormManageService dormObj=null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
		dormObj=(I_DormManageService) applicationContext.getBean("dormService");
	}


	@Test
	public void testDeleteStudent() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteBatch() {
		fail("Not yet implemented");
	}

//	@Test
//	public void testStudentList() {
//		ResultSet rs=null;
//		DormVo vo=new DormVo();
//		PageBean pageBean=new PageBean(1,4);
//		rs=dormObj.DormList(vo, pageBean);
//		System.out.println(rs);
//	}

	@Test
	public void testStudentCount() {
		int i=dormObj.DormCount();
		System.out.println("i=="+i);
	}


	@Test
	public void testQueryVo() {
		DormVo vo=null;
		try {
			vo = dormObj.queryVo(1L);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(vo);
	}

	@Test
	public void testUpdateTeacherVo() {
		
	}

}
