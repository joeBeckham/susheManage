package com.business.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * @ClassName:XMLUtil
 * @Description: TODO(读写xml文件)
 * @author xbq
 * @date 2016-5-8 上午10:10:36
 * 
 */
public class XMLUtil {

	private static Object obj = new Object();
	private Document oDoc = null;
	public static String filePath = null;
	public String root = null;
	private String charSet = "UTF-8";

	public XMLUtil(String filePath, String rootName) {
		XMLUtil.filePath = filePath;
		this.root = rootName;
		System.out.println("文件绝对路径为：XMLTool.filePath=" + filePath);
		buildFile(filePath, rootName);
	}

	/**
	* @Title: createNodeMultiPropertiesValue 
	* @Description: TODO(设定某个节点元素的一些属性和值     没有该节点则创建。相当于增加)
	* @author xbq 
	* @date 2016-5-8 上午10:18:28
	* @param 
	* @return boolean
	* @throws
	 */
	@SuppressWarnings("rawtypes")
	public boolean createNodeMultiPropertiesValue(String strNodeNamePathPath,Map map) {
		boolean flag = false;
		synchronized (obj) {
			try {

				String as[] = parsePath(strNodeNamePathPath); // root/商业客户/客户*/
				Element element = oDoc.getRootElement();
				Element elementnew = null;

				for (int iLoop = 0; iLoop < as.length; iLoop++) // 一个循环处理一个节点
				{
					if (iLoop == as.length - 1) { /* 如果是最后一个节点 */
						elementnew = new Element(as[iLoop]);

						Iterator it = map.keySet().iterator();
						while (it.hasNext()) {
							String key = (String) it.next();
							String value = (String) map.get(key);
							elementnew.setAttribute(key, value); // 末尾元素设定值
						}

						element.addContent(elementnew);
					} else {// root/商业客户/客户 数组： arr[0]=商业客户 arr[1]=客户
						if (element.getChild(as[iLoop]) == null) // 没有该子节点则创建：有则取之
						{
							element.addContent(new Element(as[iLoop]));
						}
						element = element.getChild(as[iLoop]); // 得到循环的元素：
					}
				}
				saveFile();
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("设定节点属性值出错,  节点路径:" + strNodeNamePathPath);
			}
		}
		return flag;
	}

	// 将节点路径放入到数组中：便于每次检查：根节点除外
	private String[] parsePath(String path) { /* root/商业客户/客户01 */
		String arr[] = path.split("/");
		String returnArr[] = new String[arr.length - 1];
		if (arr[0].equalsIgnoreCase(this.root)) { /* 如果第一个元素是根的话：忽越该元素 */
			for (int i = 0; i < arr.length - 1; i++) {
				returnArr[i] = arr[i + 1];
				System.out.println("需要创建的节点：" + returnArr[i]);
			}
		}
		return returnArr;
	}

	/**
	 * @Title: buildFile
	 * @Description: TODO(读入某个XML文件)
	 * @author xbq
	 * @date 2016-5-8 上午10:17:22
	 * @param @param filePath 创建的xml文件路径
	 * @param @param rootName
	 * @return void
	 * @throws
	 */
	public void buildFile(String filePath, String rootName) {
		SAXBuilder builder = new SAXBuilder();
		synchronized (obj) {
			try {
				XMLFileIsExists(filePath, rootName);
				this.oDoc = builder.build(new FileInputStream(filePath)); /* 根据xml文件路径形成Jdom树 */
				if (this.oDoc == null) {
					System.out.println("读取资源文件出错!!!!!!");
				}
			} catch (Exception ex) {
				System.out.println("读取资源文件出错!!!!!!");
				ex.printStackTrace();
			}
		}
	}

	/**
	 * @Title: XMLFileIsExists
	 * @Description: TODO(有该文件就不创建没有则创建)
	 * @author xbq
	 * @date 2016-5-8 上午10:16:20
	 * @param @param path
	 * @param @param rootName
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	private void XMLFileIsExists(String path, String rootName) throws Exception {
		synchronized (obj) {
			try {
				File file1 = new File(path);
				if (file1.exists()) {
					System.out.println("该" + path + "文件存在");
				} else {
					/* 每次都生成一个新的XML文件 */
					Element root = new Element(rootName); // 生成根元素：
					Document doc = new Document(root);/* 创建JDOM树 */
					Format format = Format.getCompactFormat();/* 创建文档格式化对象 */
					format.setEncoding(charSet); // 设置xml文件的字符为gb2312
					format.setIndent("    "); // 设置xml文件的缩进为4个空格
					XMLOutputter XMLOut = new XMLOutputter(format);// 在元素后换行，每一层元素缩排四格
					XMLOut.output(doc, new FileOutputStream(path));// 输出dom树到xml文件中
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("创建xml出错");
			}
		}
	}

	/**
	* @Title: saveFile 
	* @Description: TODO(保存修改过的xml文件)
	* @author xbq 
	* @date 2016-5-8 上午10:18:28
	* @param 
	* @return void
	* @throws
	 */
	private synchronized void saveFile() {
		{
			BufferedOutputStream bufferout = null;
			try {
				/* 创建保存的xml文件对象 */
				File file = new File(XMLUtil.filePath);
				FileOutputStream fileout = new FileOutputStream(file);
				/* 文档格式化输出 */
				Format format = Format.getCompactFormat();
				format.setEncoding(charSet); // 设置xml文件的字符为utf-8
				format.setIndent("    "); // 设置xml文件的缩进为4个空格
				XMLOutputter xmloutputter = new XMLOutputter(format);
				bufferout = new BufferedOutputStream(fileout);
				/* 写XML文档 */
				xmloutputter.output(oDoc, bufferout);
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				try {
					bufferout.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
