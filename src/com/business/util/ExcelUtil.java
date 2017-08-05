package com.business.util;

import java.io.InputStream;
import java.sql.ResultSet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;




public class ExcelUtil {

//	public static void fillExcelData(ResultSet rs,Workbook wb,String[] headers)throws Exception{
//		int rowIndex=0;
//		Sheet sheet=wb.createSheet();
//		Row row=sheet.createRow(rowIndex++);
//		for(int i=0;i<headers.length;i++){
//			row.createCell(i).setCellValue(headers[i]);
//		}
//		while(rs.next()){
//			row=sheet.createRow(rowIndex++);
//			for(int i=0;i<headers.length;i++){
//				row.createCell(i).setCellValue(rs.getObject(i+1).toString());
//			}
//		}
//	}
	
	public static Workbook fillExcelDataWithTemplate(ResultSet rs,String templateFileName)throws Exception{
		InputStream inp = ExcelUtil.class.getResourceAsStream("/com/business/template/"+templateFileName);
		POIFSFileSystem fs = new POIFSFileSystem(inp);
		Workbook wb = new HSSFWorkbook(fs);
		Sheet sheet = wb.getSheetAt(0);
		//获取列数
		int cellNums = sheet.getRow(0).getLastCellNum();
		//System.out.println("列数：：=="+cellNums);
		int rowIndex=1;
		while(rs.next()){
			//ResultSetMetaData rsmd = rs.getMetaData();
			Row row=sheet.createRow(rowIndex++);
			for(int i=0 ; i<cellNums; i++){
				//System.out.println(rsmd.getColumnName(i+1)+":"+rs.getObject(i+1).toString());
				if(rs.getObject(i+1) == null){
					row.createCell(i).setCellValue("");
				} else{
					row.createCell(i).setCellValue(rs.getObject(i+1).toString());
				}
			}
		}
		return wb;
	}
	
	/**
	* @Title: fillExcelDataWithTemplate1 
	* @Description: TODO(这个是原来的   可以正常使用   ，因要备份，所以注释掉)
	* @author xbq 
	* @date 2016-5-18 下午5:28:04
	* @param @param rs
	* @param @param templateFileName
	* @param @return
	* @param @throws Exception
	* @return Workbook
	* @throws
	 */
	/*public static Workbook fillExcelDataWithTemplate(ResultSet rs,String templateFileName)throws Exception{
		InputStream inp = ExcelUtil.class.getResourceAsStream("/com/business/template/"+templateFileName);
		POIFSFileSystem fs = new POIFSFileSystem(inp);
		Workbook wb = new HSSFWorkbook(fs);
		Sheet sheet = wb.getSheetAt(0);
		//获取列数
		int cellNums = sheet.getRow(0).getLastCellNum();
		System.out.println("列数：：=="+cellNums);
		int rowIndex=1;
		while(rs.next()){
			//ResultSetMetaData rsmd = rs.getMetaData();
			Row row=sheet.createRow(rowIndex++);
			for(int i=0;i<cellNums;i++){
				//System.out.println(rsmd.getColumnName(i+1)+":"+rs.getObject(i+1).toString());
				if(rs.getObject(i+1) == null){
					row.createCell(i).setCellValue("");
				} else{
					row.createCell(i).setCellValue(rs.getObject(i+1).toString());
				}
			}
		}
		return wb;
	}*/
	
//	public static String formatCell(HSSFCell hssfCell){
//		if(hssfCell==null){
//			return "";
//		}else{
//			if(hssfCell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN){
//				return String.valueOf(hssfCell.getBooleanCellValue());
//			}else if(hssfCell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
//				return String.valueOf(hssfCell.getNumericCellValue());
//			}else{
//				return String.valueOf(hssfCell.getStringCellValue());
//			}
//		}
//	}
}
