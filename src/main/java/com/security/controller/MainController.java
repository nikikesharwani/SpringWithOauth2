package com.security.controller;


import org.springframework.web.bind.annotation.RequestMapping;


import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.model.TokenResponse;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


@RestController    // This means that this class is a Controller
@RequestMapping(path="/") // This means URL's start with /demo (after Application path)
public class MainController {
	
	OkHttpClient client = new OkHttpClient();
	//private MockMvc mvc;
	
	@GetMapping("hello") // Map ONLY GET Requests
	public String hello(){
		return "Hello";
	}
	
	
	@GetMapping(path="secured/all")
	public String Secured() {
		// This returns a JSON or XML with the users
		return "Secured!!!";
	}
	
//	@PostMapping(path="/getToken")
//	public  String getToken(@RequestParam String username, @RequestParam String password) throws UnsupportedEncodingException, Exception{
//		String authorization = "Basic "
//				+ new String(Base64Utils.encode("my-trusted-client:secret".getBytes()));
//		String contentType = MediaType.APPLICATION_JSON + ";charset=UTF-8";
//
//		// @formatter:off
//		String content = mvc
//				.perform(
//						post("/oauth/token")
//								.header("Authorization", authorization)
//								.contentType(
//										MediaType.APPLICATION_FORM_URLENCODED)
//								.param("username", username)
//								.param("password", password)
//								.param("grant_type", "password")
//								//.param("scope", "read write secret")
//								.param("client_id", "my-trusted-client")
//								.param("client_secret", "secret"))
//				.andExpect(status().isOk())
//				.andExpect(MockMvcResultMatchers.content().contentType(contentType))
//				.andExpect(jsonPath("$.access_token", is(notNullValue())))
//				.andExpect(jsonPath("$.token_type", is(equalTo("bearer"))))
//				.andExpect(jsonPath("$.refresh_token", is(notNullValue())))
//				.andExpect(jsonPath("$.expires_in", is(greaterThan(4000))))
//				.andExpect(jsonPath("$.scope", is(equalTo("read write"))))
//				.andReturn().getResponse().getContentAsString();
//
//		// @formatter:on
//		
//
//		return content.substring(17, 53);
//	}
	
	@PostMapping(path="/getToken")
	public String getToken(@RequestBody TokenResponse responsObj) throws IOException{
		String grant_type = responsObj.getGrant_type();
		String username = responsObj.getUsername();
		String password = responsObj.getPassword();
		String paramURL = "grant_type="+grant_type+"&username="+username+"&password="+password;
		Request request = new Request.Builder()
				  .url("http://localhost:8080/oauth/token?"+paramURL)
				  .post(null)
				  .addHeader("authorization", "Basic bXktdHJ1c3RlZC1jbGllbnQ6c2VjcmV0")
				  .addHeader("content-type", "application/json")
				  .addHeader("cache-control", "no-cache")
				  .build();

		Response response = client.newCall(request).execute();
		return response.body().string();
	}
}
