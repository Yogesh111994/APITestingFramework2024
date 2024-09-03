package com.qa.phonepe.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.phonePe.client.RestClient;
import com.qa.phonepe.base.BaseTest;
import com.qa.phonepe.constants.APIConstants;
import com.qa.phonepe.constants.APIHttpStatus;
import com.qa.phonepe.requestpojo.ManagerReporteeList;
import com.qa.phonepe.requestpojo.ManagerReporteeList.Span;
import com.qa.phonepe.requestpojo.ManagerReporteeList.Timeline;
import com.qa.phonepe.responsepojo.ManagerReporteeListResponse;
import com.qa.phonepe.responsepojo.ManagerReporteeListResponse.Heirarchy;

import com.qa.phonepe.responsepojo.TopFilterResponse;
import com.qa.phonepe.responsepojo.TopFilterResponse.TimelineList;

import io.qameta.allure.Feature;
import io.restassured.response.Response;

@Feature("application-filter-controller API")
public class ApplicationFilterAPI extends BaseTest {

	private static Map<String, String> cookies;
	private static String access_token;
	public static final Logger log = LogManager.getLogger(ApplicationFilterAPI.class);

	@BeforeClass
	public void getUserSetup() {
		restClient = new RestClient(prop, BaseURI);
		access_token = restClient.getJwtToken(AUTH_LOGIN_ENDPOINT_URL);
		cookies = new HashMap<String, String>();
		cookies.put("JwtToken", access_token);
	}

	@DataProvider
	public Object[][] filtersData() {
		return jUtils.testData(ManagerReporteeList[].class, APIConstants.APPLICATION_JSON_FILTER_PATH);
	}

	/**
	 * 
	 * @param timelineText
	 * @param year
	 * @param selected
	 * @param timelineKey
	 * @param spanText
	 * @param select
	 */
	@Test
	public void getManagerReporteeListTest() {

		List<ManagerReporteeList> managerReporteeListRequest = tUtil.testData(ManagerReporteeList[].class,
				APIConstants.APPLICATION_JSON_FILTER_PATH);
		List<ManagerReporteeListResponse> managerReporteeListResponse = tUtil
				.testData(ManagerReporteeListResponse[].class, APIConstants.APPLICATION_RESPONSE_JSON_FILE_PATH);

		for (int i = 0; i < managerReporteeListRequest.size(); i++) {
			var managerreporteelistrequest = managerReporteeListRequest.get(i);
			var managerreporteelistresponse = managerReporteeListResponse.get(i);

			String timelineText = managerreporteelistrequest.getTimeline().getTimelineText();
			String year = managerreporteelistrequest.getTimeline().getYear();
			boolean selected = managerreporteelistrequest.getTimeline().isSelected();
			String timelineKey = managerreporteelistrequest.getTimeline().getTimelineKey();
			String spanText = managerreporteelistrequest.getSpan().getSpanText();
			boolean select = managerreporteelistrequest.getSpan().isSelected();
			Span span = new Span(spanText, select);
			Timeline timeline = new Timeline(timelineText, year, selected, timelineKey);
			ManagerReporteeList list = new ManagerReporteeList(timeline, span);
			Response response = restClient.post(MANAGER_REPORTEE_LIST_URL, "json", list, cookies, true);
			// response.prettyPrint();
			Assert.assertEquals(utils.statusCodeValidation(response), APIHttpStatus.Ok_200.getCode());
			utils.schemaValidation(response, APIConstants.MANAGER_REPORTEE_LIST_SCHEMA_VALIDATION_FILE);

			System.out.println("Manager Reportee********************************************************************");
			List<Heirarchy> expectedReporteeList = managerreporteelistresponse.getReporteeList();
			List<Heirarchy> actualReporteeList = jsonPathValidator.readList(response, "$..reporteeList[*]");
			ObjectMapper objectMapper = new ObjectMapper();
			if (expectedReporteeList.size() == actualReporteeList.size()) {
				for (int j = 0; j < expectedReporteeList.size(); j++) {
					// CardItem actualCardItem=actualAttritionList.get(j);
					Heirarchy actualHeirarchyList = objectMapper.convertValue(actualReporteeList.get(j),
							Heirarchy.class);
					Heirarchy expectedHeirarchyList = expectedReporteeList.get(j);
					getReporteeListResponse(actualHeirarchyList, expectedHeirarchyList);

					if (expectedHeirarchyList.getHeirarchy().size() == actualHeirarchyList.getHeirarchy().size()
							|| expectedHeirarchyList.getHeirarchy() != null) {
						getHeirarchyReporteeListResponse(actualHeirarchyList.getHeirarchy(),
								expectedHeirarchyList.getHeirarchy());
					}
					for (int k = 0; k < expectedHeirarchyList.getHeirarchy().size(); k++) {
						if (expectedHeirarchyList.getHeirarchy().get(k).getHeirarchy().size() == actualHeirarchyList
								.getHeirarchy().get(k).getHeirarchy().size()
								&& expectedHeirarchyList.getHeirarchy() != null) {
							getHeirarchyReporteeListResponse(
									actualHeirarchyList.getHeirarchy().get(k).getHeirarchy(),
									expectedHeirarchyList.getHeirarchy().get(k).getHeirarchy());
					}
					}
				}

			}
		}
	}

	/**
	 * 
	 * @param timelineText
	 * @param year
	 * @param selected
	 * @param timelineKey
	 */
	@Test(dataProvider = "filtersData")
	public void getDataFiltersTest(ManagerReporteeList filters) {
		String timelineText = filters.getTimeline().getTimelineText();
		String year = filters.getTimeline().getYear();
		boolean selected = filters.getTimeline().isSelected();
		String timelineKey = filters.getTimeline().getTimelineKey();

		Timeline timeline = new Timeline(timelineText, year, selected, timelineKey);
		Response response = restClient.post(DATA_FILTER_ENDPOINT_URL, "json", timeline, cookies, true);
		response.prettyPrint();
		Assert.assertEquals(utils.statusCodeValidation(response), APIHttpStatus.Ok_200.getCode());
		utils.schemaValidation(response, APIConstants.FILTERS_DATA_SCHEMA_VALIDATION_FILE);

	}

	@Test
	public void getTopFilter() {
		
		List<TopFilterResponse> topFilterResponse = tUtil.testData(TopFilterResponse[].class,
				APIConstants.APPLICATION_FILTER_RESPONSE_JSON_FILE_PATH);
		Response response = restClient.get(GET_TOP_FILTER_ENDPOINT_URL, cookies, true);
		response.prettyPrint();
		Assert.assertEquals(utils.statusCodeValidation(response), APIHttpStatus.Ok_200.getCode());
		utils.schemaValidation(response, APIConstants.TOP_FILTERS_SCHEMA_VALIDATION_FILE);
		String expectedWelcomeText=topFilterResponse.get(0).getWelcomeText();
		List<String> actualWelcomeText=jsonPathValidator.readList(response, "$..welcomeText");
		softAssert.assertEquals(actualWelcomeText.get(0), expectedWelcomeText);
		String expectedReporteeListToolTip=topFilterResponse.get(0).getReporteeListToolTip();
		List<String> actualReporteeListToolTip=jsonPathValidator.readList(response, "$..reporteeListToolTip");
		softAssert.assertEquals(actualReporteeListToolTip.get(0), expectedReporteeListToolTip);
		String expectedSpanListToolTip=topFilterResponse.get(0).getSpanListToolTip();
		List<String> actualSpanListToolTip=jsonPathValidator.readList(response, "$..spanListToolTip");
		softAssert.assertEquals(actualSpanListToolTip.get(0), expectedSpanListToolTip);
		List<String> actualReporteeName=jsonPathValidator.readList(response, "$..reporteeList[*].reporteeName");
		String expectedReporteeName=topFilterResponse.get(0).getReporteeList().get(0).getReporteeName();
		softAssert.assertEquals(actualReporteeName.get(0), expectedReporteeName);
		List<String> actualReporteeEmail=jsonPathValidator.readList(response, "$..reporteeList[*].reporteeEmail");
		String expectedReporteeEmail=topFilterResponse.get(0).getReporteeList().get(0).getReporteeEmail();
		softAssert.assertEquals(actualReporteeEmail.get(0), expectedReporteeEmail);	
		List<String> actualReporteePositionName=jsonPathValidator.readList(response, "$..reporteeList[*].positionName");
		String expectedReporteepositionName=topFilterResponse.get(0).getReporteeList().get(0).getPositionName();
		softAssert.assertEquals(actualReporteePositionName.get(0), expectedReporteepositionName);	
		List<String> actualValue=jsonPathValidator.readList(response, "$..reporteeList[*].value");
		String expectedValue=topFilterResponse.get(0).getReporteeList().get(0).getValue();
		softAssert.assertEquals(actualValue.get(0), expectedValue);	
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<TimelineList> actualTimelineTest=jsonPathValidator.readList(response, "$..timelineList[*]");
		List<TimelineList> expectedTimelineTest=topFilterResponse.get(0).getTimelineList();
		if (expectedTimelineTest.size() == actualTimelineTest.size()) {
			for (int j = 0; j < expectedTimelineTest.size(); j++) {
				TimelineList actualHeirarchyList = objectMapper.convertValue(actualTimelineTest.get(j),
						TimelineList.class);
				TimelineList expectedHeirarchyList = expectedTimelineTest.get(j);
				String actualTimeline=actualHeirarchyList.getTimelineKey();
				String expectedTimeline=expectedHeirarchyList.getTimelineKey();
				softAssert.assertEquals(actualTimeline, expectedTimeline);
				String actualYear=actualHeirarchyList.getYear();
				String expectedYear=expectedHeirarchyList.getYear();
				softAssert.assertEquals(actualYear, expectedYear);
				String actualTimelineKey=actualHeirarchyList.getTimelineKey();
				String expectedTimelineKey=expectedHeirarchyList.getTimelineKey();
				softAssert.assertEquals(actualTimelineKey, expectedTimelineKey);
				boolean actualSelected=actualHeirarchyList.isSelected();
				boolean expectedSelected=expectedHeirarchyList.isSelected();
				softAssert.assertEquals(actualSelected, expectedSelected);
			}
		}
		softAssert.assertAll();
	}

	/**
	 * Before executing this method generate the token id using following userId and
	 * Password UserId=mdpuser02@innoventes.co Password=MDPUser021!
	 * 
	 * @param timelineText
	 * @param year
	 * @param selected
	 * @param timelineKey
	 * @param spanText
	 * @param select
	 */

	@Test
	public void getHRBPReporteeListTest() {
		
		List<ManagerReporteeList> managerReporteeListRequest = tUtil.testData(ManagerReporteeList[].class,
				APIConstants.APPLICATION_JSON_FILTER_PATH);
		//List<ManagerReporteeListResponse> managerReporteeListResponse = tUtil
		//		.testData(ManagerReporteeListResponse[].class, APIConstants.APPLICATION_RESPONSE_JSON_FILE_PATH);

		for (int i = 0; i < managerReporteeListRequest.size(); i++) {
			var managerreporteelistrequest = managerReporteeListRequest.get(i);
		//	var managerreporteelistresponse = managerReporteeListResponse.get(i);
		
		String timelineText = managerreporteelistrequest.getTimeline().getTimelineText();
		String year = managerreporteelistrequest.getTimeline().getYear();
		boolean selected = managerreporteelistrequest.getTimeline().isSelected();
		String timelineKey = managerreporteelistrequest.getTimeline().getTimelineKey();
		String spanText = managerreporteelistrequest.getSpan().getSpanText();
		boolean select = managerreporteelistrequest.getSpan().isSelected();

		Span span = new Span(spanText, select);
		Timeline timeline = new Timeline(timelineText, year, selected, timelineKey);
		ManagerReporteeList list = new ManagerReporteeList(timeline, span);
		Response response = restClient.post(HRBP_REPORTEE_LIST_URL, "json", list, cookies, true);
//		response.prettyPrint();
		Assert.assertEquals(utils.statusCodeValidation(response), APIHttpStatus.Ok_200.getCode());
		utils.schemaValidation(response, APIConstants.HRBP_REPORTEE_LIST_SCHEMA_VALIDATION_FILE);
		
		
		}
	}

	private void getReporteeListResponse(Heirarchy actualHeirarchyList, Heirarchy expectedHeirarchyList) {
		String actualReporteeName = actualHeirarchyList.getReporteeName();
		System.out.println(actualReporteeName);
		String expectedReporteeName = expectedHeirarchyList.getReporteeName();
		System.out.println(expectedReporteeName);
		String actualPositionName = actualHeirarchyList.getPositionName();
		System.out.println(actualPositionName);
		String expectedPositionName = expectedHeirarchyList.getPositionName();
		System.out.println(expectedPositionName);
		String actualReporteeEmail = actualHeirarchyList.getReporteeEmail();
		System.out.println(actualReporteeEmail);
		String expectedReporteeEmail = expectedHeirarchyList.getReporteeEmail();
		System.out.println(expectedReporteeEmail);
		String actualValue = actualHeirarchyList.getValue();
		System.out.println(actualValue);
		String expectedValue = expectedHeirarchyList.getValue();
		System.out.println(expectedValue);
		softAssert.assertEquals(actualReporteeName, expectedReporteeName);
		softAssert.assertEquals(actualPositionName, expectedPositionName);
		softAssert.assertEquals(actualReporteeEmail, expectedReporteeEmail);
		softAssert.assertEquals(actualValue, expectedValue);
		softAssert.assertAll();
	}

	private void getHeirarchyReporteeListResponse(List<Heirarchy> actualHeirarchyList,
			List<Heirarchy> expectedHeirarchyList) {

		for (int k = 0; k < expectedHeirarchyList.size(); k++) {
						Heirarchy actualHeirarchyReporteeList = actualHeirarchyList.get(k);
			// System.out.println(actualHeirarchyReporteeList);
			Heirarchy ExpectedHeirarchyReporteeList = expectedHeirarchyList.get(k);
			// System.out.println(ExpectedHeirarchyReporteeList);
			String actualReporteeName = actualHeirarchyReporteeList.getReporteeName();
			System.out.println(actualReporteeName);
			String exectedReporteeName = ExpectedHeirarchyReporteeList.getReporteeName();
			System.out.println(exectedReporteeName);
			String actualReporteeEmail = actualHeirarchyReporteeList.getReporteeEmail();
			System.out.println(actualReporteeEmail);
			String exectedReporteeEmail = ExpectedHeirarchyReporteeList.getReporteeEmail();
			System.out.println(exectedReporteeEmail);
			String actualReporteePositionName = actualHeirarchyReporteeList.getPositionName();
			System.out.println(actualReporteeName);
			String exectedReporteePositionName = ExpectedHeirarchyReporteeList.getPositionName();
			System.out.println(exectedReporteePositionName);
			String actualValue = actualHeirarchyReporteeList.getValue();
			System.out.println(actualValue);
			String exectedValue = ExpectedHeirarchyReporteeList.getValue();
			System.out.println(exectedValue);
			softAssert.assertEquals(actualReporteeName, exectedReporteeName);
			softAssert.assertEquals(actualReporteeEmail, exectedReporteeEmail);
			softAssert.assertEquals(actualReporteePositionName, exectedReporteePositionName);
			softAssert.assertEquals(actualValue, exectedValue);
			softAssert.assertAll();
			System.out.println("=======================================================");

		}
	}
	
}
