package com.business.util;

public class ChangeNumberUtil {
	public static int toChar(String ch){
		String str=ch.substring(0,1);
		char cc=str.charAt(0);
		int count=0;
		
		switch (cc) {
		case '一':
			count=1;
			break;
		case '二':
			count=2;
			break;
		case '三':
			count=3;
			break;
		case '四':
			count=4;
			break;
		case '五':
			count=5;
			break;
		case '六':
			count=6;
			break;
		case '七':
			count=7;
			break;
		case '八':
			count=8;
			break;
		case '九':
			count=9;
			break;
			
		default:
			count=0;
			break;
		}
		return count;
	}
}
