package com.business.junit;

import static org.junit.Assert.fail;

import java.sql.ResultSet;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.business.bean.TeacherVo;
import com.business.services.I_TeacherManageServices;
import com.business.util.PageBean;

public class TeacherManageImplTest {

	static I_TeacherManageServices teaObj=null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
		teaObj=(I_TeacherManageServices) applicationContext.getBean("teacherService");
	}

	@Test
	public void testAddStudent() {
		TeacherVo vo=new TeacherVo();
		vo.setTeacher_name("张学友");
		vo.setTeacher_userPass("12345");
		try {
			boolean flag=teaObj.addTeacher(vo);
			System.out.println(flag);
		} catch (Exception e) {
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
		TeacherVo vo=new TeacherVo();
		PageBean pageBean=new PageBean(1,4);
		rs=teaObj.teacherList(vo, pageBean);
		System.out.println(rs);
	}

	@Test
	public void testStudentCount() {
		int i=teaObj.TeacherCount();
		System.out.println("i=="+i);
	}


	@Test
	public void testQueryVo() {
		TeacherVo vo=null;
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
	}

}
