package com.qa.phonepe.tests;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.phonePe.client.RestClient;
import com.qa.phonepe.base.BaseTest;
import com.qa.phonepe.constants.APIConstants;
import com.qa.phonepe.constants.APIHttpStatus;
import com.qa.phonepe.constants.SummaryAPIConstant;
import com.qa.phonepe.requestpojo.Summary;
import com.qa.phonepe.requestpojo.TeamMetricsAndHealthDeepDive;
import com.qa.phonepe.requestpojo.Summary.Reportee;
import com.qa.phonepe.requestpojo.Summary.Span;
import com.qa.phonepe.requestpojo.Summary.Timeline;
import com.qa.phonepe.responsepojo.HealthResponse;
import com.qa.phonepe.responsepojo.HealthResponse.Health;
import com.qa.phonepe.responsepojo.SummaryResponse;
import com.qa.phonepe.responsepojo.SummaryResponse.Attrition;
import com.qa.phonepe.responsepojo.SummaryResponse.CardItem;
import com.qa.phonepe.responsepojo.SummaryResponse.Hiring;
import com.qa.phonepe.responsepojo.SummaryResponse.Metrics;

import io.restassured.response.Response;

public class SummaryAPI extends BaseTest {

	private static Map<String, String> cookies;
	private static String access_token;
	public static final Logger log = LogManager.getLogger(SummaryAPI.class);

	@BeforeMethod
	public void getUserSetup() {
		restClient = new RestClient(prop, BaseURI);
		access_token = restClient.getJwtToken(AUTH_LOGIN_ENDPOINT_URL);
		cookies = new HashMap<String, String>();
		cookies.put("JwtToken", access_token);
	}

//	@DataProvider
//	public Object[][] summaryData() {
//		return jUtils.testData(Summary[].class, APIConstants.SUMMARY_JSON_FILTER_PATH);
//	}
//
//	@DataProvider
//	public Object[][] extendedSummaryData() {
//		return jUtils.testData(Summary[].class, APIConstants.EXTENDED_SUMMARY_JSON_FILTER_PATH);
//	}

	@Test
	public void getReporteeMetricsSummary() {
		List<Summary> summaryRequest = tUtil.testData(Summary[].class, APIConstants.SUMMARY_JSON_FILTER_PATH);
		List<SummaryResponse> summaryResponse = tUtil.testData(SummaryResponse[].class,
				APIConstants.METRICS_SUMMARY_RESPONSE_JSON_FILE_PATH);
		for (int i = 0; i < summaryRequest.size(); i++) {
			var request = summaryRequest.get(i);
			// System.out.println(request);
			var summaryresponse = summaryResponse.get(i);
			// SummaryResponse summaryResponse2 = summaryResponse.get(i);
			String reporteeNane = request.getReportee().getReporteeName();
			String positionName = request.getReportee().getPositionName();
			String reporteeEmail = request.getReportee().getReporteeEmail();
			boolean selected = request.getReportee().isSelected();
			String timelineText = request.getTimeline().getTimelineText();
			boolean select = request.getTimeline().isSelected();
			String year = request.getTimeline().getYear();
			String spanText = request.getSpan().getSpanText();
			boolean isSelected = request.getSpan().isSelected();

			Span summarySpan = new Span(spanText, isSelected);
			Timeline summaryTimeline = new Timeline(timelineText, select, year);
			Reportee summaryReportee = new Reportee(reporteeNane, positionName, reporteeEmail, selected);
			Summary summaryText = new Summary(summaryReportee, summaryTimeline, summarySpan);
			Response response = restClient.post(SUMMARY_METRICS_ENDPOINT_URL, "json", summaryText, cookies, true);
			response.prettyPrint();
			Assert.assertEquals(utils.statusCodeValidation(response), APIHttpStatus.Ok_200.getCode());

			// Attrition
			// $..attritions[0]
			System.out.println("Attrition********************************************************************");
			List<CardItem> expectedAttritionList = summaryresponse.getMetrics().getAttritions();
			List<CardItem> actualAttritionList = jsonPathValidator.readList(response, "$..attritions[*]");
			ObjectMapper objectMapper = new ObjectMapper();
			if (expectedAttritionList.size() == actualAttritionList.size()) {
				for (int j = 0; j < expectedAttritionList.size(); j++) {
					// CardItem actualCardItem=actualAttritionList.get(j);
					CardItem actualCardItem = objectMapper.convertValue(actualAttritionList.get(j), CardItem.class);
					CardItem expectedCardItem = expectedAttritionList.get(j);
					getCardResponse(actualCardItem, expectedCardItem);
					if (expectedCardItem.getCardItemList().size() == actualCardItem.getCardItemList().size()
							|| expectedCardItem.getCardItemList() != null) {
						getCardItemListResponse(actualCardItem.getCardItemList(), expectedCardItem.getCardItemList());
					}
				}
			}
			System.out.println("Structure********************************************************************");
			// Structure
			List<CardItem> expectedStructureList = summaryresponse.getMetrics().getStructures();
			List<CardItem> actualStructureList = jsonPathValidator.readList(response, "$..structures[*]");
			if (expectedStructureList.size() == actualStructureList.size()) {
				for (int j = 0; j < expectedAttritionList.size(); j++) {
					CardItem actualCardItem = objectMapper.convertValue(actualStructureList.get(j), CardItem.class);
					CardItem expectedCardItem = expectedStructureList.get(j);
					getCardResponse(actualCardItem, expectedCardItem);
					if (expectedCardItem.getCardItemList().size() == actualCardItem.getCardItemList().size()
							|| expectedCardItem.getCardItemList() != null) {
						getCardItemListResponse(actualCardItem.getCardItemList(), expectedCardItem.getCardItemList());
					}
				}
			}

			System.out.println("hiring********************************************************************");
			List<CardItem> expectedHiringList = summaryresponse.getMetrics().getHirings();
			List<CardItem> actualHiringList = jsonPathValidator.readList(response, "$..hirings[*]");
			if (expectedHiringList.size() == actualHiringList.size()) {
				for (int j = 0; j < expectedAttritionList.size(); j++) {
					CardItem actualCardItem = objectMapper.convertValue(actualHiringList.get(j), CardItem.class);
					CardItem expectedCardItem = expectedHiringList.get(j);
					getCardResponse(actualCardItem, expectedCardItem);
					if (expectedCardItem.getCardItemList().size() == actualCardItem.getCardItemList().size()
							|| expectedCardItem.getCardItemList() != null) {
						getCardItemListResponse(actualCardItem.getCardItemList(), expectedCardItem.getCardItemList());
					}
				}
			}
		}
		softAssert.assertAll();

	}

	@Test
	public void getReporteeHealthSummary() {
		List<TeamMetricsAndHealthDeepDive> healthRequest = tUtil.testData(TeamMetricsAndHealthDeepDive[].class,
				APIConstants.TEAM_METRICS_AND_HEALTH_DEEP_DIVE_JSON_FILE_PATH);
		List<HealthResponse> healthResponse = tUtil.testData(HealthResponse[].class,
				APIConstants.HEALTH_SUMMARY_RESPONSE_JSON_FILE_PATH);
		for (int i = 0; i < healthRequest.size(); i++) {
			var healthrequest = healthRequest.get(i);
			var healthresponse = healthResponse.get(i);

			String reporteeNane = healthrequest.getReportee().getReporteeName();
			String positionName = healthrequest.getReportee().getPositionName();
			String reporteeEmail = healthrequest.getReportee().getReporteeEmail();
			boolean selected = healthrequest.getReportee().isSelected();
			String timelineText = healthrequest.getTimeline().getTimelineText();
			boolean select = healthrequest.getTimeline().isSelected();
			String year = healthrequest.getTimeline().getYear();
			String spanText = healthrequest.getSpan().getSpanText();
			boolean isSelected = healthrequest.getSpan().isSelected();

			Span summarySpan = new Span(spanText, isSelected);
			Timeline summaryTimeline = new Timeline(timelineText, select, year);
			Reportee summaryReportee = new Reportee(reporteeNane, positionName, reporteeEmail, selected);
			Summary summaryText = new Summary(summaryReportee, summaryTimeline, summarySpan);
			Response response = restClient.post(SUMMARY_HEALTH_ENDPOINT_URL, "json", summaryText, cookies, true);
			response.prettyPrint();
//		Assert.assertEquals(utils.statusCodeValidation(response), APIHttpStatus.Ok_200.getCode());
//		utils.schemaValidation(response, APIConstants.METRICS_HEALTH_SCHEMA_VALIDATION_FILE);
			ObjectMapper objectMapper = new ObjectMapper();

			System.out.println("Trends********************************************************************");
			List<HealthResponse.CardItem> expectedTrendsList = healthresponse.getHealth().getTrends();
			List<CardItem> actualTrendsList = jsonPathValidator.readList(response, "$..trends[*]");
			if (expectedTrendsList.size() == actualTrendsList.size()) {
				for (int j = 0; j < expectedTrendsList.size(); j++) {
					HealthResponse.CardItem actualCardItem = objectMapper.convertValue(actualTrendsList.get(j),
							HealthResponse.CardItem.class);
					HealthResponse.CardItem expectedCardItem = expectedTrendsList.get(j);
					getCardResponse(actualCardItem, expectedCardItem);
					if (expectedCardItem.getCardItemList().size() == actualCardItem.getCardItemList().size()
							&& expectedCardItem.getCardItemList() != null) {
						getCardItemListHealthResponse(actualCardItem.getCardItemList(),
								expectedCardItem.getCardItemList());
					}
				}
			}

			System.out.println("Highlights********************************************************************");
			List<HealthResponse.CardItem> expectedHighlightsList = healthresponse.getHealth().getHighlights();
			List<CardItem> actualHighlightsList = jsonPathValidator.readList(response, "$..highlights[*]");
			if (expectedHighlightsList.size() == actualHighlightsList.size()) {
				for (int j = 0; j < expectedTrendsList.size(); j++) {
					HealthResponse.CardItem actualCardItem = objectMapper.convertValue(actualTrendsList.get(j),
							HealthResponse.CardItem.class);
					HealthResponse.CardItem expectedCardItem = expectedTrendsList.get(j);
					getCardResponse(actualCardItem, expectedCardItem);
					if (expectedCardItem.getCardItemList().size() == actualCardItem.getCardItemList().size()
							&& expectedCardItem.getCardItemList() != null) {
						getCardItemListHealthResponse(actualCardItem.getCardItemList(),
								expectedCardItem.getCardItemList());
					}
				}
			}

			System.out.println("Gaps********************************************************************");
			List<HealthResponse.CardItem> expectedGapsList = healthresponse.getHealth().getGaps();
			List<CardItem> actualGapsList = jsonPathValidator.readList(response, "$..gaps[*]");
			if (expectedGapsList.size() == actualGapsList.size()) {
				for (int j = 0; j < expectedTrendsList.size(); j++) {
					HealthResponse.CardItem actualCardItem = objectMapper.convertValue(actualTrendsList.get(j),
							HealthResponse.CardItem.class);
					HealthResponse.CardItem expectedCardItem = expectedTrendsList.get(j);
					getCardResponse(actualCardItem, expectedCardItem);
					if (expectedCardItem.getCardItemList().size() == actualCardItem.getCardItemList().size()
							&& expectedCardItem.getCardItemList() != null) {
						getCardItemListHealthResponse(actualCardItem.getCardItemList(),
								expectedCardItem.getCardItemList());
					}
				}
			}
			softAssert.assertAll();

		}
	}

	@Test
	public void getSummaryData() {
		Response response = restClient.get(SUMMARY_ENDPOINT_URL, cookies, true);
		 response.prettyPrint();
		Assert.assertEquals(utils.statusCodeValidation(response), APIHttpStatus.Ok_200.getCode());
		utils.schemaValidation(response, APIConstants.SUMMARY_SCHEMA_VALIDATION_FILE);
	}

//	public void getExtendedReporteeMetricsSummary(Summary summary) {
//		String reporteeNane = summary.getReportee().getReporteeName();
//		String positionName = summary.getReportee().getPositionName();
//		String reporteeEmail = summary.getReportee().getReporteeEmail();
//		boolean selected = summary.getReportee().isSelected();
//		String timelineText = summary.getTimeline().getTimelineText();
//		boolean select = summary.getTimeline().isSelected();
//		String year = summary.getTimeline().getYear();
//		String spanText = summary.getSpan().getSpanText();
//		boolean isSelected = summary.getSpan().isSelected();
//
//		Span summarySpan = new Span(spanText, isSelected);
//		Timeline summaryTimeline = new Timeline(timelineText, select, year);
//		Reportee summaryReportee = new Reportee(reporteeNane, positionName, reporteeEmail, selected);
//		Summary summaryText = new Summary(summaryReportee, summaryTimeline, summarySpan);
//		Response response = restClient.post(SUMMARY_METRICS_ENDPOINT_URL, "json", summaryText, cookies, true);
//		response.prettyPrint();
//		Assert.assertEquals(utils.statusCodeValidation(response), APIHttpStatus.Ok_200.getCode());
//		// Schema validation is failing as in API response the "statisticsVal" is to be
//		// null but found both string and null
//		// utils.schemaValidation(response,
//		// APIConstants.METRICS_SUMMARY_SCHEMA_VALIDATION_FILE);
//
//		// Attrition card validation
//
//		List<String> extendedAttritionDisplayText = jsonPathValidator.readList(response,
//				"$..attritions[0].displayText");
//		softAssert.assertEquals(extendedAttritionDisplayText.get(0), SummaryAPIConstant.EXTENDED_ATTRITION_TEXT);
//		List<String> extendedStatisticsVal = jsonPathValidator.readList(response, "$..attritions[0].statisticsVal");
//		softAssert.assertEquals(extendedStatisticsVal.get(0), SummaryAPIConstant.EXTENDED_STATISTICSVALUE);
//		List<String> extendedByGradeTitle = jsonPathValidator.readList(response,
//				"$..attritions[0].cardItemList[0].displayText");
//		softAssert.assertEquals(extendedByGradeTitle.get(0), SummaryAPIConstant.BY_GRADE_TITLE_TEXT);
//		List<String> extendedByGrades = jsonPathValidator.readList(response,
//				"$..attritions[0].cardItemList[0].cardItemList[*].displayText");
//		softAssert.assertTrue(extendedByGrades.containsAll(SummaryAPIConstant.BY_GRADES));
//		List<String> extendedByGenderTitle = jsonPathValidator.readList(response,
//				"$..attritions[0].cardItemList[1].displayText");
//		softAssert.assertEquals(extendedByGenderTitle.get(0), SummaryAPIConstant.BY_GENDER_TITLE_TEXT);
//		List<String> extendedMaleCount = jsonPathValidator.readList(response,
//				"$..attritions[0].cardItemList[1].cardItemList[0].displayText");
//		softAssert.assertEquals(extendedMaleCount.get(0), SummaryAPIConstant.MALE_COUNT);
//		List<String> extendedFemaleCount = jsonPathValidator.readList(response,
//				"$..attritions[0].cardItemList[1].cardItemList[1].displayText");
//		softAssert.assertEquals(extendedFemaleCount.get(0), SummaryAPIConstant.FEMALE_COUNT);
//		List<String> extendedResignationCount = jsonPathValidator.readList(response,
//				"$..attritions[0].cardItemList[2].displayText");
//		softAssert.assertEquals(extendedResignationCount.get(0), SummaryAPIConstant.RESIGNATION_COUNT);
//		List<String> extendedEarlyAttrition = jsonPathValidator.readList(response, "$..attritions[1].displayText");
//		softAssert.assertEquals(extendedEarlyAttrition.get(0), SummaryAPIConstant.EXTENDED_EARLY_ATTRITION);
//		List<String> extendedEarlyAttritionAverage = jsonPathValidator.readList(response,
//				"$..attritions[1].statisticsVal");
//		softAssert.assertEquals(extendedEarlyAttritionAverage.get(0),
//				SummaryAPIConstant.EXTENDED_EARLY_ATTRITION_AVERAGE);
//		List<String> extendedHighPerformanceAttrition = jsonPathValidator.readList(response,
//				"$..attritions[2].displayText");
//		softAssert.assertEquals(extendedHighPerformanceAttrition.get(0),
//				SummaryAPIConstant.EXTENDED_HIGH_PERFORMANCE_ATTRITION);
//		List<String> extendedHighPerformanceAttritionAverage = jsonPathValidator.readList(response,
//				"$..attritions[2].statisticsVal");
//		softAssert.assertEquals(extendedHighPerformanceAttritionAverage.get(0),
//				SummaryAPIConstant.EXTENDED_HIGH_PERFORMANCE_ATTRITION_AVERAGE);
//		List<String> extendedSeprationReasonTitleText = jsonPathValidator.readList(response,
//				"$..attritions[3].displayText");
//		softAssert.assertEquals(extendedSeprationReasonTitleText.get(0),
//				SummaryAPIConstant.SEPRATION_REASON_TITLE_TEXT);
//		List<String> extendedSeprationTypes = jsonPathValidator.readList(response,
//				"$..attritions[3].cardItemList[*].displayText");
//		softAssert.assertTrue(extendedSeprationTypes.containsAll(SummaryAPIConstant.SEPRATION_TYPES));
//		List<String> extendedRegrettableAttritionList = jsonPathValidator.readList(response,
//				"$..attritions[3].cardItemList[0].cardItemList[*].displayText");
//		softAssert.assertTrue(
//				extendedRegrettableAttritionList.containsAll(SummaryAPIConstant.REGRETTABLE_ATTRITION_LIST));
//		List<String> extendedUNRegrettableAttritionList = jsonPathValidator.readList(response,
//				"$..attritions[3].cardItemList[1].cardItemList[*].displayText");
//		softAssert.assertTrue(
//				extendedUNRegrettableAttritionList.containsAll(SummaryAPIConstant.UN_REGRETTABLE_ATTRITION_LIST));
//
////		// Structure card validation
//
//		List<String> extendedSummaryTitle = jsonPathValidator.readList(response, "$..structures[0].displayText");
//		softAssert.assertEquals(extendedSummaryTitle.get(0), SummaryAPIConstant.EXTENDED_SUMMARY_TITLE_TEXT);
//		List<String> extendedSubtitleAOP = jsonPathValidator.readList(response,
//				"$..structures[0].cardItemList[0].displayText");
//		softAssert.assertEquals(extendedSubtitleAOP.get(0), SummaryAPIConstant.EXTENDED_AOP_TEXT);
//		List<String> extendedOcupiedPosition = jsonPathValidator.readList(response,
//				"$..structures[0].cardItemList[1].displayText");
//		softAssert.assertEquals(extendedOcupiedPosition.get(0), SummaryAPIConstant.EXTENDED_POSITION_OCCUPIED);
//		List<String> extendedPyramidTitleText = jsonPathValidator.readList(response, "$..structures[1].displayText");
//		softAssert.assertEquals(extendedPyramidTitleText.get(0), SummaryAPIConstant.EXTENDED_PYRAMID_TITLE_TEXT);
//		List<String> extendedGenderDiversityTitle = jsonPathValidator.readList(response,
//				"$..structures[2].displayText");
//		softAssert.assertEquals(extendedGenderDiversityTitle.get(0),
//				SummaryAPIConstant.EXTENDED_GENDER_DIVERSITY_TITLE_TEXT);
//		List<String> extendedGenderDiversitySpanAndFunction = jsonPathValidator.readList(response,
//				"$..structures[2].cardItemList[*].displayText");
//		softAssert.assertTrue(extendedGenderDiversitySpanAndFunction
//				.containsAll(SummaryAPIConstant.EXTENDED_GENDER_DIVERSITY_SUB_TITLE_TEXT));
//
//		// Hiring
//		List<String> extendedHiringSummaryTitle = jsonPathValidator.readList(response, "$..hirings[0].displayText");
//		softAssert.assertEquals(extendedHiringSummaryTitle.get(0),
//				SummaryAPIConstant.EXTENDED_HIRING_SUMMARY_TITLE_TEXT);
//		List<String> externalHiring = jsonPathValidator.readList(response, "$..hirings[0].cardItemList[0].displayText");
//		softAssert.assertEquals(externalHiring.get(0), SummaryAPIConstant.EXTENDED_EXTERNAL_HIRING_NUMBER);
//		List<String> hiringFunnelTitle = jsonPathValidator.readList(response, "$..hirings[1].displayText");
//		softAssert.assertEquals(hiringFunnelTitle.get(0), SummaryAPIConstant.EXTENDED_HIRING_FUNNEL_TITLE_TEXT);
//		List<String> hiringFunnelTitleList = jsonPathValidator.readList(response,
//				"$..hirings[1].cardItemList[*].displayText");
//		softAssert.assertTrue(
//				hiringFunnelTitleList.containsAll(SummaryAPIConstant.EXTENDED_SUMMARY_OF_HIRING_FUNNEL_LIST));
//		List<String> hiringProgress = jsonPathValidator.readList(response, "$..hirings[2].displayText");
//		softAssert.assertEquals(hiringProgress.get(0), SummaryAPIConstant.EXTENDED_HIRING_IN_PROGRESS_ROLE);
//		softAssert.assertAll();
//	}

	private void getCardItemListResponse(List<CardItem> actualCardItems, List<CardItem> expectedCardItems) {
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

	private void getCardItemListHealthResponse(List<HealthResponse.CardItem> actualCardItems,
			List<HealthResponse.CardItem> expectedCardItems) {
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

	private void getCardResponse(CardItem actualCardItem, CardItem expectedCardItem) {
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

	private void getCardResponse(HealthResponse.CardItem actualCardItem, HealthResponse.CardItem expectedCardItem) {
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
