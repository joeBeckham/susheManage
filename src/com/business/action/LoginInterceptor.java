package com.business.action;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 拦截器
 * @author Administrator
 *
 */
public class LoginInterceptor implements Interceptor {

	private static final long serialVersionUID = 1L;
	public static String loginPath = "loginPath";

	public void init() {
		// System.out.println("调用拦截器的init()方法");
	}

	public String intercept(ActionInvocation arg0) throws Exception {
		try {
			// System.out.println("------------调用拦截器的intercept方法开始-----------");
			Object obj = arg0.getAction();
			if (obj instanceof SystemManageAction) {
				// System.out.println("---可以正确登录系统---");
				return arg0.invoke(); // 去下一个拦截器
			}

			Map<?, ?> session = arg0.getInvocationContext().getSession();
			String loginId = (String) session.get("loginId");
			if (null != loginId && loginId.length() > 0) {
//				System.out.println("----" + loginId + "----已经登陆了---");
				return arg0.invoke();
			} else {
//				System.out.println("======没有登录====");
				return loginPath;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return loginPath;
	}

	public void destroy() {
//		System.out.println("调用拦截器的destroy()方法");
	}

}
