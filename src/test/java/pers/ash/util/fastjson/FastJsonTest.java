package pers.ash.util.fastjson;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

public class FastJsonTest {

	@Test
	public void testJsonToBean(){
		String userString = "{\"id\":1,\"password\":\"123456\",\"username\":\"Asher\"}";
		User user = JSONObject.parseObject(userString, User.class);
		System.out.println(user.toString());
	}
	
	@Test
	public void testBeanToJson(){
		User user = new User(1, "Asher", "123456");
		String userString = JSONObject.toJSONString(user);
		System.out.println(userString);
	}
}
