package com.business.util;

import java.io.FileOutputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.junit.Test;

/**
 * @ClassName: WriteReadXmlUtil
 * @Description: TODO(读写xml文件)
 * @author xbq
 * @date 2016-5-8 上午9:15:34
 *
 */
public class WriteReadXmlUtil {

	public static void writeXml(String sendManValue,String msgTypeValue,String contentValue,String countValue){
		
		try {
			Document doc = new Document();    // 定义一个空的JDOM树
			Element root = new Element("saveMsg");  // 定义一个根元素
			doc.setRootElement(root);     // 将根元素添加到JDOM树中
			
			Element ele01 = new Element("sendMan");   // 创建一个元素
			ele01.addContent(sendManValue);    // 为该元素 设置 元素值
			root.addContent(ele01);   // 将该节点添加到根结点下面
			
			Element ele02 = new Element("msgType");
			ele02.addContent(msgTypeValue);
			root.addContent(ele02);
			
			Element ele03 = new Element("msgContent");
			ele03.addContent(contentValue);
			root.addContent(ele03);
			
			Element ele04 = new Element("msgCount");
			ele04.addContent(countValue);
			root.addContent(ele04);
			
			String path = System.getProperty("user.dir"); // 获取工程的路径
			System.out.println("path===="+path);
			Format format = Format.getCompactFormat(); // xml文件格式化输出
			format.setEncoding("utf-8"); // 设置xml文件的字符为UTF-8
			format.setIndent("    "); // 设置xml文件的缩迚为4个空格
			XMLOutputter outputter = new XMLOutputter(format);
			
			String configPath = path + "\\file\\徐邦启.xml";
			System.out.println("configPath === "+ configPath);
			FileOutputStream outputStream = new FileOutputStream(configPath);
			outputter.output(doc, outputStream);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void readXml(){
		
//		String configPath = System.getProperty("user.dir") + "\\file\\徐邦启.xml"; // 得到文件路径
//		SAXBuilder builder = new SAXBuilder(); // 建立xml解析器
		
		
	}
	
	
	
	@Test
	public void test(){
		writeXml("徐邦启，张三", "迁出宿舍，调换宿舍", "黄飞鸿身份卡何况是福建的师傅会受到风刀霜剑！","2");
	}
}
