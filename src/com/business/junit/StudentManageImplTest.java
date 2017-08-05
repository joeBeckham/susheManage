package com.business.junit;

import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.business.bean.StudentVo;
import com.business.services.I_StudentManageServices;
import com.business.util.PageBean;

public class StudentManageImplTest {

	static I_StudentManageServices stuObj=null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
		stuObj=(I_StudentManageServices) applicationContext.getBean("studentService");
	}

	@Test
	public void testAddStudent() {
		StudentVo vo=new StudentVo();
		vo.setStudent_userName("14121503931");
		vo.setStudent_userPass("12345");
		try {
			boolean flag=stuObj.addStudent(vo);
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
		StudentVo vo=new StudentVo();
		PageBean pageBean=new PageBean(1,4);
		rs=stuObj.studentList(vo, pageBean);
		System.out.println(rs);
	}

	@Test
	public void testStudentCount() {
		int i=stuObj.StudentCount();
		System.out.println("i=="+i);
	}

	@Test
	public void testUpdateStudentVo() {
		StudentVo vo=new StudentVo();
		vo.setStudent_id(1L);
		vo.setStudent_phone("18390006556");
		vo.setStudent_userName("14121503931");
		vo.setStudent_userPass("12345");
		boolean flag=stuObj.updateStudentVo(vo);
		System.out.println(flag);
	}
	
	@Test
	public void testStudentList11() {
		List<?> list=stuObj.StudentList(new StudentVo());
		System.out.println("list==="+list);
		System.out.println("----------list.size()-----=="+list.size());
		if(list!=null){
			for(int i=0;i<list.size();i++){
				System.out.println(list.get(i));
				System.out.println("00000000000000000000000000000000000000000");
			}
		}
	}
	
	@Test
	public void testgetState() {
		StudentVo vo=stuObj.getState(1L);
		System.out.println("vo==="+vo);
	}
	

}
