package com.business.junit;

import static org.junit.Assert.fail;

import java.sql.ResultSet;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.business.bean.BuildingVo;
import com.business.services.I_BuildingManageService;
import com.business.util.PageBean;

public class BuidlingManageImplTest {

	static I_BuildingManageService teaObj=null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
		teaObj=(I_BuildingManageService) applicationContext.getBean("buildingService");
	}

	@Test
	public void testAddStudent() {
		BuildingVo vo=new BuildingVo();
		vo.setBuilding_name("10栋");
		vo.setBuilding_remark("南苑10栋男生");
		try {
			boolean flag=teaObj.addBuilding(vo);
			System.out.println(flag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteStudent() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteBatch() {
		fail("Not yet implemented");
	}

	@Test
	public void testStudentList() {
		ResultSet rs=null;
		BuildingVo vo=new BuildingVo();
		PageBean pageBean=new PageBean(1,4);
		rs=teaObj.buildingList(vo, pageBean);
		System.out.println(rs);
	}

	@Test
	public void testStudentCount() {
		int i=teaObj.BuildingCount();
		System.out.println("i=="+i);
	}


	@Test
	public void testQueryVo() {
		BuildingVo vo=null;
		try {
			vo = teaObj.queryVo(1L);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(vo);
	}

	@Test
	public void testUpdateTeacherVo() {
		BuildingVo vo=new BuildingVo();
		vo.setBuilding_id(1L);
		vo.setBuilding_name("10栋");
		vo.setBuilding_remark("南苑10栋男生11");
		boolean flag=teaObj.updateBuildingVo(vo);
		System.out.println("falg==="+flag);
	}

}
