package com.qa.phonepe.utils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

	public <T> Object[][] testData(Class<T[]> className, String jsonFilePath) {
		Object[][] testData = null;
		ObjectMapper objectMapper = new ObjectMapper();
		File file = new File(jsonFilePath);
		T[] testCases; 
		try {
		    testCases = objectMapper.readValue(file, className);
			testData = new Object[testCases.length][1];
			for (int i = 0; i < testData.length; i++) {
				testData[i][0] = testCases[i];
			}

		} catch (StreamReadException e) {
			e.printStackTrace();
		} catch (DatabindException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return testData;

	}
	
	public <T> List<T> combineTestData(Class<T[]> className, String jsonFilePath) {
		List<T> testDataList=new LinkedList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		File file = new File(jsonFilePath);
		T[] testCases; 
		try {
		    testCases = objectMapper.readValue(file, className);
			//testData = new JSONObject[testCases.length][1];
			for (int i = 0; i < testCases.length; i++) {
				testDataList.add(testCases[i]);
			}

		} catch (StreamReadException e) {
			e.printStackTrace();
		} catch (DatabindException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return testDataList;

	}
	
	
}
