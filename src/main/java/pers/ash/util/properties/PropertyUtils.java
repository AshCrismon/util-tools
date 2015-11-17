package pers.ash.util.properties;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class PropertyUtils {

	private static Properties props = null;
	private static InputStream inStream = null;
	private static OutputStream outStream = null;
	private static Class clazz;
	
	static{
		clazz = PropertyUtils.class;
	}
	
	/**
	 * 设置要操作的属性文件
	 * @param fileName 文件名
	 * @param readOnly 只读
	 */
	public static void setPropertyFile(String fileName, boolean readOnly){
		props = new Properties();
		if (readOnly) {
			try {
				inStream = clazz.getResourceAsStream(getFilePath(fileName));
				props.load(inStream);

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (inStream != null) {
						inStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			String filePath = clazz.getResource(getFilePath(fileName)).getPath();
			try {
				outStream = new FileOutputStream(filePath);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 设置要操作的属性文件,默认为只读操作
	 * @param fileName 文件名
	 */
	public static void setPropertyFile(String fileName){
		setPropertyFile(fileName, true);
	}
	
	
	private static String getFilePath(String fileName){
		if(!fileName.startsWith("/")){
			fileName = "/" + fileName;
		}
		return fileName;
	}
	
	/**
	 * 设置属性property的值为value
	 * @param property 属性名
	 * @param value 属性值
	 * @throws IOException
	 */
	public static void setProperty(String property, String value){
		if (props != null) {
			props.clear();
			props.setProperty(property, value);
			try {
				props.store(outStream, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			throw new NullPointerException("the properties is null");
		}
	}
	
	/**
	 * 设置属性property的值为value
	 * @param property 属性名
	 * @param value 属性值
	 * @throws IOException
	 */
	public static void setProperty(String property, String value, String comments){
		if(props != null){
			props.clear();
			props.setProperty(property, value);
			try {
				props.store(outStream, comments);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			throw new NullPointerException("the properties is null");
		}
	}
	
	/**
	 * 设置多个属性及其属性值
	 * @param properties Map<property, value>
	 * @throws IOException
	 */
	public static void setProperty(Map<String, String> properties){
		if(props != null){
			props.putAll(properties);
			try {
				props.store(outStream, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			throw new NullPointerException("the properties is null");
		}
	}
	
	/**
	 * 关闭输出流
	 */
	public static void close(){
		if(outStream != null){
			try {
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 输出属性值
	 * @param props 属性文件对象
	 */
	public static void print(){
		if (props != null && !props.isEmpty()) {
			for (Entry<Object, Object> entry : props.entrySet()) {
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				System.out.println(key + " = " + value);
			}
		}
	}
	
}
