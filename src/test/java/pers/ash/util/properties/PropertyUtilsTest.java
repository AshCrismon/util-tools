package pers.ash.util.properties;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class PropertyUtilsTest {

	
	@Test
	public void testReadProperties(){
		PropertyUtils.setPropertyFile("test.properties");
		PropertyUtils.print();
		PropertyUtils.close();
	}
	
	@Test
	public void testSetProperty(){
		PropertyUtils.setPropertyFile("test.properties", false);
		PropertyUtils.setProperty("username", "Asher123");
		PropertyUtils.setProperty("password", "123456");
		PropertyUtils.setProperty("email", "asher@163.com");
		PropertyUtils.close();
	}
	
	@Test
	public void testSetPropertyAndComments(){
		PropertyUtils.setPropertyFile("test.properties", false);
		PropertyUtils.setProperty("username", "Asher", "用户名");
		PropertyUtils.setProperty("password", "123456", "密码");
		PropertyUtils.setProperty("email", "asher@163.com", "邮箱");
		PropertyUtils.close();
	}
	
	@Test
	public void testSetPropertyMap(){
		PropertyUtils.setPropertyFile("test.properties", false);
		Map<String, String> props = new HashMap<String, String>();
		props.put("username", "Asher");
		props.put("password", "654123");
		props.put("email", "asher@sina.com");
		PropertyUtils.setProperty(props);
	}
	
}
