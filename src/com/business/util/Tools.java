package com.business.util;

public class Tools {
	
	/**
	 * 判断字符串是否为空
	 * @author xubangqi
	 * @param obj
	 * @return boolean
	 */
	public static boolean isEmpty(Object obj){
		if("".equals(obj) || obj==null || "null".equals(obj)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 空对象处理
	 * @author xubangqi
	 * @param obj
	 * @return
	 */
	public static String checkNull(Object obj){
		if("".equals(obj)||null==obj||"null".equals(obj)){
			return "";
		}else{
			return obj.toString();
		}
	}
	
	
	/**
	* @Title: getTwoName 
	* @Description: TODO(截取 带有 逗号的 字符串)
	* @author xbq 
	* @param @param names
	* @param @return
	* @return String
	* @throws
	 */
	public static String getTwoName(String names,int flag,String contents){
		if(flag == 1){
			String contentOne = null;
			if(contents != null){
				if(contents.length() < 8){
					contentOne = contents.substring(0, contents.length());
				}else {
					// 大于8个字符 就截取 前8个 字符
					contentOne = contents.substring(0, 8);
				}
			}
			return contentOne;
		}
		
		String result = null;
		if(names.contains(",")){
			String one = names.substring(0,names.indexOf(",")+1);
			String two = names.substring(names.indexOf(",")+1);
			String second = null;
			String sum = null;
			if(two.contains(",")){
				second = two.substring(0,names.indexOf(","));
				sum = one + second;
				if(one.contains(second)){
					sum = one;
				}
			}else {
				sum = one + two;
				if(one.contains(two)){
					sum = one ;
				}
			}
			result = sum + "[...]";
		}else {
			result = names;
		}
		return result;
	}
}
