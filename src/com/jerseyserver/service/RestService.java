package com.jerseyserver.service;


import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerseyserver.entity.ResponseDTO;
import com.jerseyserver.entity.User;
import com.jerseyserver.util.TextUtil;


@Path("/restService")
public class RestService {
	@POST
	@Path("/getUserText")
	@Produces(MediaType.TEXT_PLAIN)
	/**
	 * 测试返回text文本媒体类型数据的方式
	 * @return "Hello,World!"
	 */
	public String getUserText() {
		return "Hello,World!";
	}

	@GET
	@Path("/getUserXml")
	@Produces(MediaType.APPLICATION_XML)
	/**
	 * 测试返回xml媒体类型数据的方式
	 * @return User object
	 */
	public User getUserXml() {
		User user = new User();
		user.setName("snail");
		user.setAge("22");
		user.setSex("male");
		return user;
	}

	@GET
	@Path("/getUserJson")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * 测试返回json媒体类型数据的方式
	 * @return User object
	 */
	public User getUserJson() {
		User user = new User();
		user.setName("snail");
		user.setAge("22");
		user.setSex("male");
		return user;
	}
	
	@POST
	@Path("/getUserInfo")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * 测试带请求参数时返回json媒体类型数据的方式
	 * @param username
	 * @return
	 */
	public User getUserInfo(@FormParam("username") String username) {
		if (username == null || "".equals(username)) {
			return null;
		}
		return getUserByName(username);		
	}
	
	/**
	 * 通过用户名获取用户
	 * @param username
	 * @return User object
	 */
	private User getUserByName(String username) {
		HashMap<String, User> map = initAllUsers();
		return map.get(username);
	}
	
	/**
	 * 获取所有用户的map
	 * @return 所有用户的map
	 */
	private HashMap<String, User> initAllUsers() {
		HashMap<String, User> map = new HashMap<>();
		
		User user1 = new User();
		user1.setName("Jack");
		user1.setPasswd("Jack");
		user1.setAge(18 + "");
		user1.setSex("男");
		map.put(user1.getName(), user1);
		
		User user2 = new User();
		user2.setName("Alice");
		user2.setPasswd("Alice");
		user2.setAge(18 + "");
		user2.setSex("女");
		map.put(user2.getName(), user2);
		
		User user3 = new User();
		user3.setName("Allen");
		user3.setPasswd("Allen");
		user3.setAge(20 + "");
		user3.setSex("女");
		map.put(user3.getName(), user3);
		
		return map;
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * 用户登录
	 * @param username 用户名
	 * @param password 密码
	 * @return Response object
	 */
	public Response login(@FormParam("username") String username, @FormParam("password") String password) {		
		if (TextUtil.isEmpty(username) || TextUtil.isEmpty(password)) {
			return null;
		}
		User user =  checkUser(username, password);
		if (user == null) {
			return null;
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		GenericEntity<String> payloadEntity;
		try {
			payloadEntity = new GenericEntity<String>(
					mapper.writeValueAsString(new ResponseDTO(200, "ok", user))) {
			};
			return Response.ok(payloadEntity).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return Response
					.ok(" {\"status\": 404,\n\"message\": \"error\",\n\"response\": \"\"}")
					.build();
		}
	}
	
	/**
	 * 验证用户是否存在
	 * @param username
	 * @param password
	 * @return User object
	 */
	private User checkUser(String username, String password) {
		HashMap<String, User> map = initAllUsers();
		User user =  map.get(username);
		if (user != null) {
			String passwd = user.getPasswd();
			if (password.equals(passwd)) {
				return user;
			}
		}
		return null;
	}
		
	@POST
	@Path("/getAllUsers")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * 获取所有用户的集合
	 * @return Response object
	 */
	public Response getAllUsers() {
		ArrayList<User> list = new ArrayList<User>();
		User user1 = new User();
		user1.setName("Jack");
		user1.setPasswd("Jack");
		user1.setAge(18 + "");
		user1.setSex("男");
		list.add(user1);
		
		User user2 = new User();
		user2.setName("Alice");
		user2.setPasswd("Alice");
		user2.setAge(18 + "");
		user2.setSex("女");
		list.add(user2);
		
		User user3 = new User();
		user3.setName("Allen");
		user3.setPasswd("Allen");
		user3.setAge(20 + "");
		user3.setSex("女");
		list.add(user3);
		
		ObjectMapper mapper = new ObjectMapper();
		
		GenericEntity<String> payloadEntity;
		try {
			payloadEntity = new GenericEntity<String>(
					mapper.writeValueAsString(new ResponseDTO(200, "ok", list))) {
			};
			return Response.ok(payloadEntity).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return Response
					.ok(" {\"status\": 404,\n\"message\": \"error\",\n\"response\": \"\"}")
					.build();
		}
	}
}