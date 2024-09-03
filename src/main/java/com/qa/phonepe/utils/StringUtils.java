package com.qa.phonepe.utils;

import java.time.LocalDate;
import java.time.Month;

import java.util.List;


import com.qa.phonepe.constants.APIConstants;
import com.qa.phonepe.frameworkexception.APIFrameworkException;
import com.qa.phonepe.responsepojo.HealthResponse;
import com.qa.phonepe.responsepojo.SummaryResponse.CardItem;

import io.restassured.module.jsv.JsonSchemaValidationException;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.asserts.SoftAssert;
public class StringUtils {

	public SoftAssert softAssert;
	public static String getRandomEmail() {
		return "test" + System.currentTimeMillis() + "@apiautomation.com";
	}

	public int statusCodeValidation(Response response) {
		int statusCode = response.statusCode();
		System.out.println(statusCode);
		return statusCode;
	}

	public String statusMessageValidation(Response response) {
		String statusMessage = response.statusLine();
		System.out.println(statusMessage);
		return statusMessage;
	}

	public String getQuarter() {
		LocalDate date = LocalDate.now();
		Month month = date.getMonth();
		System.out.println(month);

		switch (month) {
		case JANUARY: {
			return APIConstants.FIRST_QUARTER;
		}
		case FEBRUARY: {
			return APIConstants.FIRST_QUARTER;
		}
		case MARCH: {
			return APIConstants.FIRST_QUARTER;
		}
		case APRIL: {
			return APIConstants.SECOND_QUARTER;
		}
		case MAY: {
			return APIConstants.SECOND_QUARTER;
		}
		case JUNE: {
			return APIConstants.SECOND_QUARTER;
		}
		case JULY: {
			return APIConstants.THIRD_QUARTER;
		}
		case AUGUST: {
			return APIConstants.THIRD_QUARTER;
		}
		case SEPTEMBER: {
			return APIConstants.THIRD_QUARTER;
		}
		case OCTOBER: {
			return APIConstants.FOURTH_QUARTER;
		}
		case NOVEMBER: {
			return APIConstants.FOURTH_QUARTER;
		}
		case DECEMBER: {
			return APIConstants.FOURTH_QUARTER;
		}

		default:
			throw new IllegalArgumentException("Unexpected value: " + month);
		}
	}

	public String getYear() {
		LocalDate date = LocalDate.now();
		int year = date.getYear();
		return String.valueOf(year);
	}
	
	public String dueDate() {
		LocalDate date = LocalDate.now();
		LocalDate plusDays = date.plusDays(1);
		return String.valueOf(plusDays);
		
	}
	
	public void schemaValidation(Response response, String schemaValidationFileName) {
		try {
			response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaValidationFileName));
		} catch (JsonSchemaValidationException e) {
			System.out.println("Response schema does not match with expected schema");
			e.printStackTrace();
			new APIFrameworkException("ActualExpectedSchemaMismatch");
		}
	}
	
//	public Map<String, String> setJwtTokenCookie(String key, String value) {
//		 Map<String, String> cookies = null;
//	    if (cookies == null) {
//	        cookies = new HashMap<>();
//	    }
//	    cookies.put(key, value);
//	    return cookies;
//	}
	
	public void getCardItemListResponse(List<CardItem> actualCardItems, List<CardItem> expectedCardItems) {
		for (int k = 0; k < expectedCardItems.size(); k++) {
			// System.out.println("*************************************************");
			CardItem actualCard = actualCardItems.get(k);
			System.out.println(actualCard);
			CardItem ExpectedCard = expectedCardItems.get(k);
			System.out.println(ExpectedCard);
			String actualDisplayText = actualCard.getDisplayLabel();
			System.out.println(actualDisplayText);
			String ExectedDisplayText = ExpectedCard.getDisplayLabel();
			System.out.println(ExectedDisplayText);
			String actualDisplayValue = actualCard.getDisplayValue();
			System.out.println(actualDisplayValue);
			String ExpectedDisplayValue = ExpectedCard.getDisplayValue();
			System.out.println(ExpectedDisplayValue);
			softAssert.assertEquals(actualDisplayText, ExectedDisplayText);
			softAssert.assertEquals(actualDisplayValue, ExpectedDisplayValue);
			softAssert.assertAll();
		}
	}
	
	public void getCardItemListHealthResponse(List<HealthResponse.CardItem> actualCardItems, List<HealthResponse.CardItem> expectedCardItems) {
		for (int k = 0; k < expectedCardItems.size(); k++) {
			// System.out.println("*************************************************");
			HealthResponse.CardItem actualCard = actualCardItems.get(k);
			System.out.println(actualCard);
			HealthResponse.CardItem ExpectedCard = expectedCardItems.get(k);
			System.out.println(ExpectedCard);
			String actualDisplayText = actualCard.getDisplayLabel();
			System.out.println(actualDisplayText);
			String ExectedDisplayText = ExpectedCard.getDisplayLabel();
			System.out.println(ExectedDisplayText);
			String actualDisplayValue = actualCard.getDisplayValue();
			System.out.println(actualDisplayValue);
			String ExpectedDisplayValue = ExpectedCard.getDisplayValue();
			System.out.println(ExpectedDisplayValue);
			softAssert.assertEquals(actualDisplayText, ExectedDisplayText);
			softAssert.assertEquals(actualDisplayValue, ExpectedDisplayValue);
			softAssert.assertAll();
		}
	}

	public void getCardResponse(CardItem actualCardItem, CardItem expectedCardItem) {
		String actualDisplayText = actualCardItem.getDisplayLabel();
		System.out.println(actualDisplayText);
		String exectedDisplayText = expectedCardItem.getDisplayLabel();
		System.out.println(actualDisplayText);
		String actualDisplayValue = actualCardItem.getDisplayValue();
		System.out.println(actualDisplayValue);
		String expectedDisplayValue = expectedCardItem.getDisplayValue();
		System.out.println(expectedDisplayValue);

		softAssert.assertEquals(actualDisplayValue, expectedDisplayValue);
		softAssert.assertEquals(actualDisplayText, exectedDisplayText);
		if (!(expectedCardItem.getStatisticsVal() == actualCardItem.getStatisticsVal())) {
			String actualStatisticsval = actualCardItem.getStatisticsVal();
			System.out.println(actualStatisticsval);
			String expectedStatisticsval = expectedCardItem.getStatisticsVal();
			System.out.println(expectedStatisticsval);
			softAssert.assertEquals(actualStatisticsval, expectedStatisticsval);
		}
	}
	
	public void getCardResponse(HealthResponse.CardItem actualCardItem, HealthResponse.CardItem expectedCardItem) {
		String actualDisplayText = actualCardItem.getDisplayLabel();
		System.out.println(actualDisplayText);
		String exectedDisplayText = expectedCardItem.getDisplayLabel();
		System.out.println(actualDisplayText);
		String actualDisplayValue = actualCardItem.getDisplayValue();
		System.out.println(actualDisplayValue);
		String expectedDisplayValue = expectedCardItem.getDisplayValue();
		System.out.println(expectedDisplayValue);

		softAssert.assertEquals(actualDisplayValue, expectedDisplayValue);
		softAssert.assertEquals(actualDisplayText, exectedDisplayText);
		if (!(expectedCardItem.getStatisticsVal() == actualCardItem.getStatisticsVal())) {
			String actualStatisticsval = actualCardItem.getStatisticsVal();
			System.out.println(actualStatisticsval);
			String expectedStatisticsval = expectedCardItem.getStatisticsVal();
			System.out.println(expectedStatisticsval);
			softAssert.assertEquals(actualStatisticsval, expectedStatisticsval);
		}
	}

}
