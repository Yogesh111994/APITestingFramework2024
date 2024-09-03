package com.qa.phonepe.utils;

import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.qa.phonepe.frameworkexception.APIFrameworkException;

import io.restassured.response.Response;

public class JsonPathValidator {

	/**
	 * This method will return single value
	 * @param <T>
	 * @param response
	 * @param jsonPath
	 * @return
	 */
	public <T> T readJson(Response response,String jsonPath) {
		String jsonResponse = response.getBody().asPrettyString();
		try{
			return JsonPath.read(jsonResponse, jsonPath);
		}
		catch(PathNotFoundException e) {
			e.printStackTrace();
			throw new APIFrameworkException(jsonPath+"is not found");
		}
	}
	/**
	 * This method will return List<T>
	 * @param <T>
	 * @param response
	 * @param jsonPath
	 * @return
	 */
	public <T> List<T> readList(Response response,String jsonPath) {
		String jsonResponse = response.getBody().asPrettyString();
		try{
			return JsonPath.read(jsonResponse, jsonPath);
		}
		catch(PathNotFoundException e) {
			e.printStackTrace();
			throw new APIFrameworkException(jsonPath+"is not found");
		}
	}
	
	/**
	 * This method will return List<Map<String,T>>
	 * @param <T>
	 * @param response
	 * @param jsonPath
	 * @return 
	 */
	public <T>List<Map<String,T>> readMapList(Response response,String jsonPath) {
		String jsonResponse = response.getBody().asPrettyString();
		try{
			return JsonPath.read(jsonResponse, jsonPath);
		}
		catch(PathNotFoundException e) {
			e.printStackTrace();
			throw new APIFrameworkException(jsonPath+"is not found");
		}
	}
	
	
}
