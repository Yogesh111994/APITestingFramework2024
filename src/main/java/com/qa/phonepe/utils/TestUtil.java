package com.qa.phonepe.utils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class TestUtil{

	public <T> List<T> testData(Class<T[]> className, String jsonFilePath) {
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
