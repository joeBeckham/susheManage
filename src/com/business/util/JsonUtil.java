package com.business.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {
	/**
	 * 把ResultSet集合转换成JsonArray数组
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public static JSONArray formatRsToJsonArray(ResultSet rs) throws Exception{
		ResultSetMetaData md=rs.getMetaData();
		int num=md.getColumnCount();
		JSONArray array=new JSONArray();
		while(rs.next()){
			JSONObject mapOdColValues=new JSONObject();
			for(int i=1;i<=num;i++){
				mapOdColValues.put(md.getColumnName(i),rs.getObject(i));				
			}
			array.add(mapOdColValues);
		}
		return array;
	}
	
	
	public static JSONArray formatObjToJsonArray(Object object) throws Exception{
		JSONArray array = JSONArray.fromObject(object);
		return array;
	}
}
