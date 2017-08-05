package com.business.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChatSetFilter extends HttpServlet implements Filter {

	private static final long serialVersionUID = 1L;
	private FilterConfig filterConfig;
    private String coding="";
    public void init(FilterConfig filterConfig) throws ServletException {
        //初始化
        this.filterConfig = filterConfig;
        //从配置文件中读取字符集
        this.coding=filterConfig.getInitParameter("encoding");
    }

    public void doFilter(ServletRequest request, ServletResponse response,FilterChain filterChain) {
			//进行过滤处理
			try {
			HttpServletRequest req = (HttpServletRequest) request;
			
			/*对所有请求的页面数据指定编码格式：防止中文乱码*/
			req.setCharacterEncoding(coding);
			
			/*
			 * 过滤器只能过滤jsp页面，不能过滤后台代码
			 * 需要检测的条件：所有除登录页面以外的页面都要检测。
			 * 检测session里面设定的值是否正确。如果正确，说明登录过了。
			 * 如果不正确，可能是以下情况：
			 * 1:登陆过但session超时
			 * 2:没有登录，直接绕过了登录页面，输入其他页面地址访问（防盗链）
			 * */
			HttpSession session=req.getSession();
	        String path=req.getContextPath();
	        String loginId=(String)session.getAttribute("loginId"); /*这时如果值为空则说明：没有经过登陆页面*/
	        String  strUrl = req.getRequestURI();	//获取资源的全路径
//	        System.out.println("strUrl=============过滤器执行============="+strUrl);
	        
	        /*需要处理的jsp页面*/
	        if(null!=strUrl&&strUrl.indexOf(".jsp") != -1){
	        	/*不是从登陆页面login.jsp来*/
	        	 if(strUrl.indexOf("login.jsp") == -1)
	 	         {  
//	 	            System.out.println("=======不是从登陆页面来==loginId="+loginId+"======88=====");
	 	            if(null==loginId||loginId.equals("")){  
//	 	            	System.out.println("=======没有登陆=====");
	 	            	response.setContentType("text/html;   charset=UTF-8"); /*设定输出的格式和字符编码格式防止输出的javascrip出现乱码*/
	 	            	PrintWriter out = response.getWriter();
	 	            	String  pathurl=path+"/login.jsp";
	 	            	out.println(this.getJS(pathurl, "您没有登录系统或离开太久，需要重新登陆系统！"));
	 	            	out.close();
	 	            	session.invalidate();
	 	            	return;    
	 	            }      
	 	        }
	        }
	       
			
			/*在处理权发送给下一个过滤器，如果没有下一个过滤器，就将请求发送给目标*/
			filterChain.doFilter(request, response);			
				
			} catch (ServletException sx) {
				sx.printStackTrace();
				filterConfig.getServletContext().log(sx.getMessage());
			} catch (IOException iox) {
				filterConfig.getServletContext().log(iox.getMessage());
			}
	 }
    public void destroy() {
        //销毁过滤器
        this.filterConfig=null;
    }
    
    private String getJS(String url,String alert){
    	String js = "";
        //js = "<%@page language=\"java\" pageEncoding=\"utf-8\" %>";
        js = js + " <script LANGUAGE='JavaScript'>";
        js = js + " alert('" + alert + "');";
        js = js + " top.location='"+url+"';";      //top.location  是跳出框架
        js = js + " </script>";
        return js;
    }
}
