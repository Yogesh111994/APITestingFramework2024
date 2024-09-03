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
import com.qa.phonepe.requestpojo.TeamMetricsAndHealthDeepDive;
import com.qa.phonepe.requestpojo.TeamMetricsAndHealthDeepDive.Reportee;
import com.qa.phonepe.requestpojo.TeamMetricsAndHealthDeepDive.Span;
import com.qa.phonepe.requestpojo.TeamMetricsAndHealthDeepDive.Timeline;
import com.qa.phonepe.responsepojo.AttritionDeepDiveResponsePojo;
import com.qa.phonepe.responsepojo.AttritionDeepDiveResponsePojo.Columns;
import com.qa.phonepe.responsepojo.AttritionDeepDiveResponsePojo.Series;
import com.qa.phonepe.responsepojo.HealthDeepDiveResponse;
import com.qa.phonepe.responsepojo.HealthDeepDiveResponse.Data;
import com.qa.phonepe.responsepojo.HiringDeepDiveResponsePojo;
import com.qa.phonepe.responsepojo.HiringDeepDiveResponsePojo.FunnelItemsList;
import com.qa.phonepe.responsepojo.StructureDeepDiveResponsePojo;

import io.restassured.response.Response;

public class TeamMetricsAndHealthAPI extends BaseTest {

	private static Map<String, String> cookies;
	private static String access_token;
	public static final Logger log = LogManager.getLogger(ActionAPI.class);

	@BeforeClass
	public void getUserSetup() {
		restClient = new RestClient(prop, BaseURI);
		access_token = restClient.getJwtToken(AUTH_LOGIN_ENDPOINT_URL);
		cookies = new HashMap<String, String>();
		cookies.put("JwtToken", access_token);
	}

	@DataProvider
	public Object[][] getTeamHealthAndMetricsData() {
		return jUtils.testData(TeamMetricsAndHealthDeepDive[].class,
				APIConstants.TEAM_METRICS_AND_HEALTH_DEEP_DIVE_JSON_FILE_PATH);
	}

	@Test
	public void getReporteeMetricsStructureTest() {
		List<TeamMetricsAndHealthDeepDive> metricsDeepDiveRequest = tUtil.testData(TeamMetricsAndHealthDeepDive[].class,
				APIConstants.TEAM_METRICS_AND_HEALTH_DEEP_DIVE_JSON_FILE_PATH);
		List<StructureDeepDiveResponsePojo> structureDeepDiveResponse = tUtil.testData(
				StructureDeepDiveResponsePojo[].class, APIConstants.STRUCTURE_DEEP_DIVE_RESPONSE_JSON_FILE_PATH);
		for (int i = 0; i < structureDeepDiveResponse.size(); i++) {
			var request = metricsDeepDiveRequest.get(i);
			var structureResponse = structureDeepDiveResponse.get(i);
			String reporteeName = request.getReportee().getReporteeName();
			String positionName = request.getReportee().getPositionName();
			String reporteeEmail = request.getReportee().getReporteeEmail();
			boolean selected = request.getReportee().isSelected();
			String timelineText = request.getTimeline().getTimelineText();
			boolean selected2 = request.getTimeline().isSelected();
			String year = request.getTimeline().getYear();
			String spanText = request.getSpan().getSpanText();
			boolean selected3 = request.getSpan().isSelected();

			Reportee reportee = new Reportee(reporteeName, positionName, reporteeEmail, selected);
			Span span = new Span(spanText, selected3);
			Timeline timeline = new Timeline(timelineText, selected2, year);
			TeamMetricsAndHealthDeepDive metrics = new TeamMetricsAndHealthDeepDive(timeline, span, reportee);

			Response response = restClient.post(METRICS_STRUCTURE_DEEPDIVE_ENDPOINT_URL, "json", metrics, cookies,
					selected3);
			//System.out.println(response.prettyPrint());
			Assert.assertEquals(utils.statusCodeValidation(response), APIHttpStatus.Ok_200.getCode());
			utils.schemaValidation(response, APIConstants.STRUCTURE_METRICS_DEEP_DIVE_SCHEMA_VALIDATION_FILE);

			//System.out.println("AOP********************************************************************");

			List<String> expectedAOPSubTitle = structureResponse.getAop().getLegend().getData();
			List<String> actualAOPSubTitle = jsonPathValidator.readList(response, "$..aop.legend.data[*]");
			softAssert.assertEquals(actualAOPSubTitle, expectedAOPSubTitle);
			List<StructureDeepDiveResponsePojo.Series> expectedAOPSeriesList = structureResponse.getAop().getSeries();
			List<StructureDeepDiveResponsePojo.Series> actualAOPSeriesList = jsonPathValidator.readList(response,
					"$..aop.series[*]");
			ObjectMapper objectMapper = new ObjectMapper();
			if (expectedAOPSeriesList.size() == actualAOPSeriesList.size()) {
				for (int j = 0; j < expectedAOPSeriesList.size(); j++) {
					StructureDeepDiveResponsePojo.Series actualList = objectMapper
							.convertValue(actualAOPSeriesList.get(j), StructureDeepDiveResponsePojo.Series.class);
					StructureDeepDiveResponsePojo.Series expectedList = expectedAOPSeriesList.get(j);
					getSeriesListResponse(actualList, expectedList);
				}
			}
			String expectedAOPXaxisType = structureResponse.getAop().getXaxis().getType();
			List<Object> actualAOPXaxisType = jsonPathValidator.readList(response, "$..aop.xaxis.type");
			softAssert.assertEquals(actualAOPXaxisType.get(0), expectedAOPXaxisType);

			List<String> expectedAOPXaxisData = structureResponse.getAop().getXaxis().getData();
			List<String> actualAOPXaxisData = jsonPathValidator.readList(response, "$..aop.xaxis.data[*]");
			softAssert.assertEquals(actualAOPXaxisData, expectedAOPXaxisData);

			String expectedAOPYaxisType = structureResponse.getAop().getYaxis().getType();
			List<Object> actuaAOPlYaxisType = jsonPathValidator.readList(response, "$..aop.yaxis.type");
			softAssert.assertEquals(actuaAOPlYaxisType.get(0), expectedAOPYaxisType);

			List<String> expectedAOPYaxisData = structureResponse.getAop().getYaxis().getData();
			List<String> actualAOPYaxisData = jsonPathValidator.readList(response, "$..aop.yaxis.data[*]");
			softAssert.assertEquals(actualAOPYaxisData, expectedAOPYaxisData);

			//System.out.println("HeadCountChange********************************************************************");

			List<String> expectedheadcountSubTitle = structureResponse.getHeadCountChange().getLegend().getData();
			List<String> actualHeadcountSubTitle = jsonPathValidator.readList(response,
					"$..headCountChange.legend.data[*]");
			softAssert.assertEquals(actualHeadcountSubTitle, expectedheadcountSubTitle);
			List<StructureDeepDiveResponsePojo.Series> expectedHeadcountSeriesList = structureResponse
					.getHeadCountChange().getSeries();
			List<StructureDeepDiveResponsePojo.Series> actualHeadcountSeriesList = jsonPathValidator.readList(response,
					"$..headCountChange.series[*]");
			if (expectedHeadcountSeriesList.size() == actualHeadcountSeriesList.size()) {
				for (int j = 0; j < expectedHeadcountSeriesList.size(); j++) {
					StructureDeepDiveResponsePojo.Series actualList = objectMapper
							.convertValue(actualHeadcountSeriesList.get(j), StructureDeepDiveResponsePojo.Series.class);
					StructureDeepDiveResponsePojo.Series expectedList = expectedHeadcountSeriesList.get(j);
					getSeriesListResponse(actualList, expectedList);
				}
			}
			String expectedHeadcountXaxisType = structureResponse.getHeadCountChange().getXaxis().getType();
			List<Object> actualHeadcountXaxisType = jsonPathValidator.readList(response,
					"$..headCountChange.xaxis.type");
			softAssert.assertEquals(actualHeadcountXaxisType.get(0), expectedHeadcountXaxisType);

			List<String> expectedHeadcountXaxisData = structureResponse.getHeadCountChange().getXaxis().getData();
			List<String> actualHeadcountXaxisData = jsonPathValidator.readList(response,
					"$..headCountChange.xaxis.data[*]");
			softAssert.assertEquals(actualHeadcountXaxisData, expectedHeadcountXaxisData);

			String expectedHeadcountYaxisType = structureResponse.getHeadCountChange().getYaxis().getType();
			List<Object> actualHeadcountYaxisType = jsonPathValidator.readList(response,
					"$..headCountChange.yaxis.type");
			softAssert.assertEquals(actualHeadcountYaxisType.get(0), expectedHeadcountYaxisType);

			List<String> expectedHeadcountYaxisData = structureResponse.getHeadCountChange().getYaxis().getData();
			List<String> actualHeadcountYaxisData = jsonPathValidator.readList(response,
					"$..headCountChange.yaxis.data[*]");
			softAssert.assertEquals(actualHeadcountYaxisData, expectedHeadcountYaxisData);

			System.out.println("Gender********************************************************************");
			List<String> expectedGenderSubTitle = structureResponse.getGender().getLegend().getData();
			List<String> actualGenderSubTitle = jsonPathValidator.readList(response, "$..gender.legend.data[*]");
			softAssert.assertEquals(actualGenderSubTitle, expectedGenderSubTitle);
			List<StructureDeepDiveResponsePojo.Series> expectedGenderSeriesList = structureResponse.getGender()
					.getSeries();
			List<StructureDeepDiveResponsePojo.Series> actualGenderSeriesList = jsonPathValidator.readList(response,
					"$..gender.series[*]");
			if (expectedGenderSeriesList.size() == actualGenderSeriesList.size()) {
				for (int j = 0; j < expectedGenderSeriesList.size(); j++) {
					StructureDeepDiveResponsePojo.Series actualList = objectMapper
							.convertValue(actualGenderSeriesList.get(j), StructureDeepDiveResponsePojo.Series.class);
					StructureDeepDiveResponsePojo.Series expectedList = expectedGenderSeriesList.get(j);
					getSeriesListResponse(actualList, expectedList);
				}
			}
			String expectedGenderXaxisType = structureResponse.getGender().getXaxis().getType();
			List<Object> actualGenderXaxisType = jsonPathValidator.readList(response, "$..gender.xaxis.type");
			softAssert.assertEquals(actualGenderXaxisType.get(0), expectedGenderXaxisType);

			List<String> expectedGenderXaxisData = structureResponse.getGender().getXaxis().getData();
			List<String> actualGenderXaxisData = jsonPathValidator.readList(response, "$..gender.xaxis.data[*]");
			softAssert.assertEquals(actualGenderXaxisData, expectedGenderXaxisData);

			String expectedGenderYaxisType = structureResponse.getGender().getYaxis().getType();
			List<Object> actualGenderYaxisType = jsonPathValidator.readList(response, "$..gender.yaxis.type");
			softAssert.assertEquals(actualGenderYaxisType.get(0), expectedGenderYaxisType);

			List<String> expectedGenderYaxisData = structureResponse.getGender().getYaxis().getData();
			List<String> actualGenderYaxisData = jsonPathValidator.readList(response, "$..gender.yaxis.data[*]");
			softAssert.assertEquals(actualGenderYaxisData, expectedGenderYaxisData);

			//System.out.println("Pyramid********************************************************************");
			List<String> expectedPyramidSubTitle = structureResponse.getPyramid().getLegend().getData();
			List<String> actualPyramidSubTitle = jsonPathValidator.readList(response, "$..pyramid.legend.data[*]");
			softAssert.assertEquals(actualPyramidSubTitle, expectedPyramidSubTitle);
			List<StructureDeepDiveResponsePojo.Series> expectedPyramidSeriesList = structureResponse.getPyramid()
					.getSeries();
			List<StructureDeepDiveResponsePojo.Series> actualPyramidSeriesList = jsonPathValidator.readList(response,
					"$..pyramid.series[*]");
			if (expectedPyramidSeriesList.size() == actualPyramidSeriesList.size()) {
				for (int j = 0; j < expectedPyramidSeriesList.size(); j++) {
					StructureDeepDiveResponsePojo.Series actualList = objectMapper
							.convertValue(actualPyramidSeriesList.get(j), StructureDeepDiveResponsePojo.Series.class);
					StructureDeepDiveResponsePojo.Series expectedList = expectedPyramidSeriesList.get(j);
					getSeriesListResponse(actualList, expectedList);
				}
			}
			String expectedPyramidXaxisType = structureResponse.getPyramid().getXaxis().getType();
			List<Object> actualPyramidXaxisType = jsonPathValidator.readList(response, "$..pyramid.xaxis.type");
			softAssert.assertEquals(actualPyramidXaxisType.get(0), expectedPyramidXaxisType);

			List<String> expectedPyramidXaxisData = structureResponse.getPyramid().getXaxis().getData();
			List<String> actualPyramidXaxisData = jsonPathValidator.readList(response, "$..pyramid.xaxis.data[*]");
			softAssert.assertEquals(actualPyramidXaxisData, expectedPyramidXaxisData);

			String expectedPyramidYaxisType = structureResponse.getPyramid().getYaxis().getType();
			List<Object> actualPyramidYaxisType = jsonPathValidator.readList(response, "$..pyramid.yaxis.type");
			softAssert.assertEquals(actualPyramidYaxisType.get(0), expectedPyramidYaxisType);

			List<String> expectedPyramidYaxisData = structureResponse.getPyramid().getYaxis().getData();
			List<String> actualPyramidYaxisData = jsonPathValidator.readList(response, "$..pyramid.yaxis.data[*]");
			softAssert.assertEquals(actualPyramidYaxisData, expectedPyramidYaxisData);

			softAssert.assertAll();
		}
	}

	@Test
	public void getReporteeMetricsHiringTest() {
		List<TeamMetricsAndHealthDeepDive> metricsDeepDiveRequest = tUtil.testData(TeamMetricsAndHealthDeepDive[].class,
				APIConstants.TEAM_METRICS_AND_HEALTH_DEEP_DIVE_JSON_FILE_PATH);
		List<HiringDeepDiveResponsePojo> hiringDeepDiveResponse = tUtil.testData(HiringDeepDiveResponsePojo[].class,
				APIConstants.HIRING_DEEP_DIVE_RESPONSE_JSON_FILE_PATH);
		for (int i = 0; i < hiringDeepDiveResponse.size(); i++) {
			var request = metricsDeepDiveRequest.get(i);
			var hiringResponse = hiringDeepDiveResponse.get(i);

			String reporteeName = request.getReportee().getReporteeName();
			String positionName = request.getReportee().getPositionName();
			String reporteeEmail = request.getReportee().getReporteeEmail();
			boolean selected = request.getReportee().isSelected();
			String timelineText = request.getTimeline().getTimelineText();
			boolean selected2 = request.getTimeline().isSelected();
			String year = request.getTimeline().getYear();
			String spanText = request.getSpan().getSpanText();
			boolean selected3 = request.getSpan().isSelected();

			Reportee reportee = new Reportee(reporteeName, positionName, reporteeEmail, selected);
			Span span = new Span(spanText, selected3);
			Timeline timeline = new Timeline(timelineText, selected2, year);
			TeamMetricsAndHealthDeepDive hiring = new TeamMetricsAndHealthDeepDive(timeline, span, reportee);

			Response response = restClient.post(METRICS_HIRING_DEEPDIVE_ENDPOINT_URL, "json", hiring, cookies,
					selected3);
			Assert.assertEquals(utils.statusCodeValidation(response), APIHttpStatus.Ok_200.getCode());
			//response.prettyPrint();
			// utils.schemaValidation(response,
			// APIConstants.STRUCTURE_HIRING_DEEP_DIVE_SCHEMA_VALIDATION_FILE);

			System.out.println("gender********************************************************************");
			List<String> expectedGenderSubTitle = hiringResponse.getGender().getLegend().getData();
			List<String> actualGenderSubTitle = jsonPathValidator.readList(response, "$..gender.legend.data[*]");
			softAssert.assertEquals(actualGenderSubTitle, expectedGenderSubTitle);
			List<HiringDeepDiveResponsePojo.Series> expectedGenderSeriesList = hiringResponse.getGender().getSeries();
			List<HiringDeepDiveResponsePojo.Series> actualGenderSeriesList = jsonPathValidator.readList(response,
					"$..gender.series[*]");
			ObjectMapper objectMapper = new ObjectMapper();
			if (expectedGenderSeriesList.size() == actualGenderSeriesList.size()) {
				for (int j = 0; j < expectedGenderSeriesList.size(); j++) {
					HiringDeepDiveResponsePojo.Series actualList = objectMapper
							.convertValue(actualGenderSeriesList.get(j), HiringDeepDiveResponsePojo.Series.class);
					HiringDeepDiveResponsePojo.Series expectedList = expectedGenderSeriesList.get(j);
					getSeriesListResponse(actualList, expectedList);
				}
			}
			String expectedGenderXaxisType = hiringResponse.getGender().getXaxis().getType();
			List<Object> actualGenderXaxisType = jsonPathValidator.readList(response, "$..gender.xaxis.type");
			softAssert.assertEquals(actualGenderXaxisType.get(0), expectedGenderXaxisType);

			List<String> expectedGenderXaxisData = hiringResponse.getGender().getXaxis().getData();
			List<String> actualGenderXaxisData = jsonPathValidator.readList(response, "$..gender.xaxis.data[*]");
			softAssert.assertEquals(actualGenderXaxisData, expectedGenderXaxisData);

			String expectedGenderYaxisType = hiringResponse.getGender().getYaxis().getType();
			List<Object> actualGenderYaxisType = jsonPathValidator.readList(response, "$..gender.yaxis.type");
			softAssert.assertEquals(actualGenderYaxisType.get(0), expectedGenderYaxisType);

			List<String> expectedGenderYaxisData = hiringResponse.getGender().getYaxis().getData();
			List<String> actualGenderYaxisData = jsonPathValidator.readList(response, "$..gender.yaxis.data[*]");
			softAssert.assertEquals(actualGenderYaxisData, expectedGenderYaxisData);

			//System.out.println("Interview Funnel********************************************************************");

			String expectedInterviewFunnelTitle = hiringResponse.getInterviewFunnel().getTitle();
			List<String> actualInterviewFunnelTitle = jsonPathValidator.readList(response, "$..interviewFunnel.title");
			softAssert.assertEquals(actualInterviewFunnelTitle.get(0), expectedInterviewFunnelTitle);
			String expectedInterviewFunnelSubTitle = hiringResponse.getInterviewFunnel().getLevelTwoTitle();
			List<String> actualInterviewFunnelSubTitle = jsonPathValidator.readList(response,
					"$..interviewFunnel.levelTwoTitle");
			softAssert.assertEquals(actualInterviewFunnelSubTitle.get(0), expectedInterviewFunnelSubTitle);
			String expectedInterviewFunnelSelectionListTitle = hiringResponse.getInterviewFunnel()
					.getFunnelSelectionListTitle();
			List<String> actualInterviewFunneSelectionListlTitle = jsonPathValidator.readList(response,
					"$..interviewFunnel.funnelSelectionListTitle");
			softAssert.assertEquals(actualInterviewFunneSelectionListlTitle.get(0),
					expectedInterviewFunnelSelectionListTitle);
			List<String> expectedInterviewFunnelSelectionList = hiringResponse.getInterviewFunnel()
					.getFunnelSelectionList();
			List<String> actualInterviewFunnelSelectionList = jsonPathValidator.readList(response,
					"$..interviewFunnel.funnelSelectionList[*]");
			softAssert.assertEquals(actualInterviewFunnelSelectionList, expectedInterviewFunnelSelectionList);
			List<FunnelItemsList> expectedManagerList = hiringResponse.getInterviewFunnel().getFunnelItems()
					.getManager();
			List<FunnelItemsList> actualManagerList = jsonPathValidator.readList(response,
					"$..funnelItems.['Product Manager / Senior Product Manager'][*]");
			if (expectedManagerList.size() == actualManagerList.size() && expectedManagerList.size() != 0) {
				for (int j = 0; j < expectedManagerList.size(); j++) {
					FunnelItemsList actualList = objectMapper.convertValue(actualManagerList.get(j),
							FunnelItemsList.class);
					FunnelItemsList expectedList = expectedManagerList.get(j);
					getSeriesListResponse(actualList, expectedList);

				}
			}

			List<FunnelItemsList> expectedDirectorList = hiringResponse.getInterviewFunnel().getFunnelItems()
					.getDirectors();
			List<FunnelItemsList> actualDirectorList = jsonPathValidator.readList(response,
					"$..funnelItems.['Director, T&S'][*]");
			if (expectedDirectorList.size() == actualDirectorList.size() && expectedManagerList.size() != 0) {
				for (int j = 0; j < expectedDirectorList.size(); j++) {
					FunnelItemsList actualList = objectMapper.convertValue(actualDirectorList.get(j),
							FunnelItemsList.class);
					FunnelItemsList expectedList = expectedDirectorList.get(j);
					getSeriesListResponse(actualList, expectedList);

				}
			}
			List<FunnelItemsList> expectedEngineersList = hiringResponse.getInterviewFunnel().getFunnelItems()
					.getEngineers();
			List<FunnelItemsList> actualEngineersList = jsonPathValidator.readList(response,
					"$..funnelItems.['Software Engineer, DevX (3-5 years)'][*]");
			if (expectedEngineersList.size() == actualEngineersList.size() && expectedManagerList.size() != 0) {
				for (int j = 0; j < expectedEngineersList.size(); j++) {
					FunnelItemsList actualList = objectMapper.convertValue(actualEngineersList.get(j),
							FunnelItemsList.class);
					FunnelItemsList expectedList = expectedEngineersList.get(j);
					getSeriesListResponse(actualList, expectedList);

				}
			}
			//System.out.println(
			//		"InterviewStagesData ********************************************************************");

			String expectedInterviewStagesTitle = hiringResponse.getInterviewFunnel().getInterviewStagesData()
					.getTitle();
			List<String> actualInterviewStagesTitle = jsonPathValidator.readList(response,
					"$..interviewStagesData.title");
			softAssert.assertEquals(actualInterviewStagesTitle.get(0), expectedInterviewStagesTitle);
			String expectedInterviewStagesSubTitle = hiringResponse.getInterviewFunnel().getInterviewStagesData()
					.getLevelTwoTitle();
			List<String> actualInterviewStagesSubTitle = jsonPathValidator.readList(response,
					"$..interviewStagesData.levelTwoTitle");
			softAssert.assertEquals(actualInterviewStagesSubTitle.get(0), expectedInterviewStagesSubTitle);
//		Integer expectedInterviewStagesOpenCount = hiringResponse.getInterviewFunnel().getInterviewStagesData().getOpenCount();
//		List<Integer> actualInterviewStagesOpenCount = jsonPathValidator.readList(response, "$..interviewStagesData.openCount");
//		softAssert.assertEquals(actualInterviewStagesOpenCount.get(0), expectedInterviewStagesOpenCount);
//		Integer expectedInterviewStagesCompletedCount = hiringResponse.getInterviewFunnel().getInterviewStagesData().getCompletedCount();
//		List<Integer> actualInterviewStagesCompletedCount = jsonPathValidator.readList(response,
//				"$..interviewStagesData.title");
//		softAssert.assertEquals(actualInterviewStagesCompletedCount.get(0), expectedInterviewStagesCompletedCount);
//		Integer expectedInterviewStagesExpiredCount = hiringResponse.getInterviewFunnel().getInterviewStagesData().getExpiredCount();
//		List<Integer> actualInterviewStagesExpiredCount = jsonPathValidator.readList(response, "$..interviewStagesData.expiredCount");
//		softAssert.assertEquals(actualInterviewStagesExpiredCount.get(0), expectedInterviewStagesExpiredCount);

			List<HiringDeepDiveResponsePojo.Columns> expectedInterviewStagesColumnsList = hiringResponse
					.getInterviewFunnel().getInterviewStagesData().getColumns();
			List<HiringDeepDiveResponsePojo.Columns> actualInterviewStagesColumnsList = jsonPathValidator
					.readList(response, "$..interviewStagesData.columns[*]");
			if (expectedInterviewStagesColumnsList.size() == actualInterviewStagesColumnsList.size()
					&& expectedInterviewStagesColumnsList.size() != 0) {
				for (int j = 0; j < expectedInterviewStagesColumnsList.size(); j++) {
					HiringDeepDiveResponsePojo.Columns actualList = objectMapper.convertValue(
							actualInterviewStagesColumnsList.get(j), HiringDeepDiveResponsePojo.Columns.class);
					HiringDeepDiveResponsePojo.Columns expectedList = expectedInterviewStagesColumnsList.get(j);
					getColumnListResponse(actualList, expectedList);
				}
			}
			//System.out.println("Offers data ********************************************************************");
			String expectedOffersDataTitle = hiringResponse.getInterviewFunnel().getOffersData().getTitle();
			List<String> actualOffersDataTitle = jsonPathValidator.readList(response, "$..offersData.title");
			softAssert.assertEquals(actualOffersDataTitle.get(0), expectedOffersDataTitle);
			String expectedOffersDataSubTitle = hiringResponse.getInterviewFunnel().getOffersData().getLevelTwoTitle();
			List<String> actualOffersDataSubTitle = jsonPathValidator.readList(response, "$..offersData.levelTwoTitle");
			softAssert.assertEquals(actualOffersDataSubTitle.get(0), expectedOffersDataSubTitle);

			List<HiringDeepDiveResponsePojo.Columns> expectedOffersDataColumnsList = hiringResponse.getInterviewFunnel()
					.getOffersData().getColumns();
			List<HiringDeepDiveResponsePojo.Columns> actualOffersDataColumnsList = jsonPathValidator.readList(response,
					"$..offersData.columns[*]");
			if (expectedOffersDataColumnsList.size() == actualOffersDataColumnsList.size()
					&& expectedOffersDataColumnsList.size() != 0) {
				for (int j = 0; j < expectedOffersDataColumnsList.size(); j++) {
					HiringDeepDiveResponsePojo.Columns actualList = objectMapper
							.convertValue(actualOffersDataColumnsList.get(j), HiringDeepDiveResponsePojo.Columns.class);
					HiringDeepDiveResponsePojo.Columns expectedList = expectedOffersDataColumnsList.get(j);
					getColumnListResponse(actualList, expectedList);
				}
			}
			softAssert.assertAll();
		}
	}

	@Test
	public void getReporteeMetricsAttritionTest() {
		List<TeamMetricsAndHealthDeepDive> attritionDeepDiveRequest = tUtil.testData(
				TeamMetricsAndHealthDeepDive[].class, APIConstants.TEAM_METRICS_AND_HEALTH_DEEP_DIVE_JSON_FILE_PATH);
		List<AttritionDeepDiveResponsePojo> attritionDeepDiveResponse = tUtil.testData(
				AttritionDeepDiveResponsePojo[].class, APIConstants.ATTRITION_DEEP_DIVE_RESPONSE_JSON_FILE_PATH);
		for (int i = 0; i < attritionDeepDiveRequest.size(); i++) {
			var request = attritionDeepDiveRequest.get(i);
			var attritionResponse = attritionDeepDiveResponse.get(i);

			String reporteeName = request.getReportee().getReporteeName();
			String positionName = request.getReportee().getPositionName();
			String reporteeEmail = request.getReportee().getReporteeEmail();
			boolean selected = request.getReportee().isSelected();
			String timelineText = request.getTimeline().getTimelineText();
			boolean selected2 = request.getTimeline().isSelected();
			String year = request.getTimeline().getYear();
			String spanText = request.getSpan().getSpanText();
			boolean selected3 = request.getSpan().isSelected();

			Reportee reportee = new Reportee(reporteeName, positionName, reporteeEmail, selected);
			Span span = new Span(spanText, selected3);
			Timeline timeline = new Timeline(timelineText, selected2, year);
			TeamMetricsAndHealthDeepDive hiring = new TeamMetricsAndHealthDeepDive(timeline, span, reportee);

			Response response = restClient.post(METRICS_ATTRITION_DEEPDIVE_ENDPOINT_URL, "json", hiring, cookies,
					selected3);
			Assert.assertEquals(utils.statusCodeValidation(response), APIHttpStatus.Ok_200.getCode());
			// response.prettyPrint();
			// utils.schemaValidation(response,
			// APIConstants.STRUCTURE_ATTRITION_DEEP_DIVE_SCHEMA_VALIDATION_FILE);

			//System.out.println("Seperation Reason********************************************************************");
			String expectedTitle = attritionResponse.getSeperationReason().getTitle();
			List<String> actualTitle = jsonPathValidator.readList(response, "$..seperationReason.title");
			softAssert.assertEquals(actualTitle.get(0), expectedTitle);
			String expectedSubTitle = attritionResponse.getSeperationReason().getLevelTwoTitle();
			List<String> actualSubTitle = jsonPathValidator.readList(response, "$..seperationReason.levelTwoTitle");
			softAssert.assertEquals(actualSubTitle.get(0), expectedSubTitle);
//		Integer expectedOpenCount = attritionResponse.getSeperationReason().getOpenCount();
//		List<Integer> actualOpenCount = jsonPathValidator.readList(response, "$..seperationReason.openCount");
//		softAssert.assertEquals(actualOpenCount.get(0), expectedOpenCount);
//		Integer expectedCompletedCount = attritionResponse.getSeperationReason().getCompletedCount();
//		List<Integer> actualCompletedCount = jsonPathValidator.readList(response,
//				"$..seperationReason.completedCount");
//		softAssert.assertEquals(actualCompletedCount.get(0), expectedCompletedCount);
//		Integer expectedExpiredCount = attritionResponse.getSeperationReason().getExpiredCount();
//		List<String> actualExpiredCount = jsonPathValidator.readList(response, "$..seperationReason.expiredCount");
//		softAssert.assertEquals(actualExpiredCount.get(0), expectedExpiredCount);

			List<Columns> expectedColumnsList = attritionResponse.getSeperationReason().getColumns();
			List<Columns> actualColumnsList = jsonPathValidator.readList(response, "$..seperationReason.columns[*]");
			ObjectMapper objectMapper = new ObjectMapper();
			if (expectedColumnsList.size() == actualColumnsList.size() && expectedColumnsList.size() != 0) {
				for (int j = 0; j < expectedColumnsList.size(); j++) {
					Columns actualList = objectMapper.convertValue(actualColumnsList.get(j), Columns.class);
					Columns expectedList = expectedColumnsList.get(j);
					getColumnListResponse(actualList, expectedList);
				}
			}
			//System.out.println("Grade********************************************************************");

			List<String> expectedGradeSubTitle = attritionResponse.getGrade().getLegend().getData();
			List<String> actualGradeSubTitle = jsonPathValidator.readList(response, "$..grade.legend.data[*]");
			softAssert.assertEquals(actualGradeSubTitle, expectedGradeSubTitle);
			List<Series> expectedGradeSeriesList = attritionResponse.getGrade().getSeries();
			List<Series> actualGradeSeriesList = jsonPathValidator.readList(response, "$..grade.series[*]");
			if (expectedGradeSeriesList.size() == actualGradeSeriesList.size()) {
				for (int j = 0; j < expectedGradeSeriesList.size(); j++) {
					Series actualList = objectMapper.convertValue(actualGradeSeriesList.get(j), Series.class);
					Series expectedList = expectedGradeSeriesList.get(j);
					getSeriesListResponse(actualList, expectedList);
				}
			}
			String expectedXaxisType = attritionResponse.getGrade().getXaxis().getType();
			List<Object> actualXaxisType = jsonPathValidator.readList(response, "$..grade.xaxis.type");
			softAssert.assertEquals(actualXaxisType.get(0), expectedXaxisType);

			List<String> expectedXaxisData = attritionResponse.getGrade().getXaxis().getData();
			List<String> actualXaxisData = jsonPathValidator.readList(response, "$..grade.xaxis.data[*]");
			softAssert.assertEquals(actualXaxisData, expectedXaxisData);

			String expectedYaxisType = attritionResponse.getGrade().getYaxis().getType();
			List<Object> actualYaxisType = jsonPathValidator.readList(response, "$..grade.yaxis.type");
			softAssert.assertEquals(actualYaxisType.get(0), expectedYaxisType);

			List<String> expectedYaxisData = attritionResponse.getGrade().getYaxis().getData();
			List<String> actualYaxisData = jsonPathValidator.readList(response, "$..grade.yaxis.data[*]");
			softAssert.assertEquals(actualYaxisData, expectedYaxisData);

			//System.out.println("Performance********************************************************************");
			List<String> expectedPerformanceSubTitle = attritionResponse.getPerformance().getLegend().getData();
			List<String> actualPerformanceSubTitle = jsonPathValidator.readList(response,
					"$..performance.legend.data[*]");
			softAssert.assertEquals(actualPerformanceSubTitle, expectedPerformanceSubTitle);
			List<Series> expectedPerformanceSeriesList = attritionResponse.getPerformance().getSeries();
			List<Series> actualPerformanceSeriesList = jsonPathValidator.readList(response, "$..performance.series[*]");
			if (expectedPerformanceSeriesList.size() == actualPerformanceSeriesList.size()) {
				for (int j = 0; j < expectedPerformanceSeriesList.size(); j++) {
					Series actualList = objectMapper.convertValue(actualPerformanceSeriesList.get(j), Series.class);
					Series expectedList = expectedPerformanceSeriesList.get(j);
					getSeriesListResponse(actualList, expectedList);
				}
			}
			String expectedPerformanceXaxisType = attritionResponse.getPerformance().getXaxis().getType();
			List<Object> actualPerformanceXaxisType = jsonPathValidator.readList(response, "$..performance.xaxis.type");
			softAssert.assertEquals(actualPerformanceXaxisType.get(0), expectedPerformanceXaxisType);

			List<String> expectedPerformanceXaxisData = attritionResponse.getPerformance().getXaxis().getData();
			List<String> actualPerformanceXaxisData = jsonPathValidator.readList(response,
					"$..performance.xaxis.data[*]");
			softAssert.assertEquals(actualPerformanceXaxisData, expectedPerformanceXaxisData);

			String expectedPerformanceYaxisType = attritionResponse.getPerformance().getYaxis().getType();
			List<Object> actualPerformanceYaxisType = jsonPathValidator.readList(response, "$..performance.yaxis.type");
			softAssert.assertEquals(actualPerformanceYaxisType.get(0), expectedPerformanceYaxisType);

			List<String> expectedPerformanceYaxisData = attritionResponse.getPerformance().getYaxis().getData();
			List<String> actualPerformanceYaxisData = jsonPathValidator.readList(response,
					"$..performance.yaxis.data[*]");
			softAssert.assertEquals(actualPerformanceYaxisData, expectedPerformanceYaxisData);

			//System.out.println("gender********************************************************************");
			List<String> expectedGenderSubTitle = attritionResponse.getGender().getLegend().getData();
			List<String> actualGenderSubTitle = jsonPathValidator.readList(response, "$..gender.legend.data[*]");
			softAssert.assertEquals(actualGenderSubTitle, expectedGenderSubTitle);
			List<Series> expectedGenderSeriesList = attritionResponse.getGender().getSeries();
			List<Series> actualGenderSeriesList = jsonPathValidator.readList(response, "$..gender.series[*]");
			if (expectedGenderSeriesList.size() == actualGenderSeriesList.size()) {
				for (int j = 0; j < expectedGenderSeriesList.size(); j++) {
					Series actualList = objectMapper.convertValue(actualGenderSeriesList.get(j), Series.class);
					Series expectedList = expectedGenderSeriesList.get(j);
					getSeriesListResponse(actualList, expectedList);
				}
			}
			String expectedGenderXaxisType = attritionResponse.getGender().getXaxis().getType();
			List<Object> actualGenderXaxisType = jsonPathValidator.readList(response, "$..gender.xaxis.type");
			softAssert.assertEquals(actualGenderXaxisType.get(0), expectedGenderXaxisType);

			List<String> expectedGenderXaxisData = attritionResponse.getGender().getXaxis().getData();
			List<String> actualGenderXaxisData = jsonPathValidator.readList(response, "$..gender.xaxis.data[*]");
			softAssert.assertEquals(actualGenderXaxisData, expectedGenderXaxisData);

			String expectedGenderYaxisType = attritionResponse.getGender().getYaxis().getType();
			List<Object> actualGenderYaxisType = jsonPathValidator.readList(response, "$..gender.yaxis.type");
			softAssert.assertEquals(actualGenderYaxisType.get(0), expectedGenderYaxisType);

			List<String> expectedGenderYaxisData = attritionResponse.getGender().getYaxis().getData();
			List<String> actualGenderYaxisData = jsonPathValidator.readList(response, "$..gender.yaxis.data[*]");
			softAssert.assertEquals(actualGenderYaxisData, expectedGenderYaxisData);

			//System.out.println("AttritionType********************************************************************");
			List<String> expectedAttritionTypeSubTitle = attritionResponse.getAttritionType().getLegend().getData();
			List<String> actualAttritionTypeSubTitle = jsonPathValidator.readList(response,
					"$..attritionType.legend.data[*]");
			softAssert.assertEquals(actualAttritionTypeSubTitle, expectedAttritionTypeSubTitle);
			List<Series> expectedAttritionTypeSeriesList = attritionResponse.getAttritionType().getSeries();
			List<Series> actualAttritionTypeSeriesList = jsonPathValidator.readList(response,
					"$..attritionType.series[*]");
			if (expectedAttritionTypeSeriesList.size() == actualAttritionTypeSeriesList.size()) {
				for (int j = 0; j < expectedAttritionTypeSeriesList.size(); j++) {
					Series actualList = objectMapper.convertValue(actualAttritionTypeSeriesList.get(j), Series.class);
					Series expectedList = expectedAttritionTypeSeriesList.get(j);
					getSeriesListResponse(actualList, expectedList);
				}
			}
			String expectedAttritionTypeXaxisType = attritionResponse.getAttritionType().getXaxis().getType();
			List<Object> actualAttritionTypeXaxisType = jsonPathValidator.readList(response,
					"$..attritionType.xaxis.type");
			softAssert.assertEquals(actualAttritionTypeXaxisType.get(0), expectedAttritionTypeXaxisType);

			List<String> expectedAttritionTypeXaxisData = attritionResponse.getAttritionType().getXaxis().getData();
			List<String> actualAttritionTypeXaxisData = jsonPathValidator.readList(response,
					"$..attritionType.xaxis.data[*]");
			softAssert.assertEquals(actualAttritionTypeXaxisData, expectedAttritionTypeXaxisData);

			String expectedAttritionTypeYaxisType = attritionResponse.getAttritionType().getYaxis().getType();
			List<Object> actualAttritionTypeYaxisType = jsonPathValidator.readList(response,
					"$..attritionType.yaxis.type");
			softAssert.assertEquals(actualAttritionTypeYaxisType.get(0), expectedAttritionTypeYaxisType);

			List<String> expectedAttritionTypeYaxisData = attritionResponse.getAttritionType().getYaxis().getData();
			List<String> actualAttritionTypeYaxisData = jsonPathValidator.readList(response,
					"$..attritionType.yaxis.data[*]");
			softAssert.assertEquals(actualAttritionTypeYaxisData, expectedAttritionTypeYaxisData);

//		checkResponse(attritionDeepDiveResponse.get(i).getGrade(),response,"grade");
//		checkResponse(attritionDeepDiveResponse.get(i).getPerformance(),response,"grade");
//		checkResponse(attritionDeepDiveResponse.get(i).getGender(),response,"grade");
//		checkResponse(attritionDeepDiveResponse.get(i).getAttritionType(),response,"grade");

			softAssert.assertAll();
		}
	}

	@Test
	public void getReporteeHeacol0lthConnectServeyTest() {

		List<TeamMetricsAndHealthDeepDive> attritionDeepDiveRequest = tUtil.testData(
				TeamMetricsAndHealthDeepDive[].class, APIConstants.TEAM_METRICS_AND_HEALTH_DEEP_DIVE_JSON_FILE_PATH);

		List<HealthDeepDiveResponse> healthDeepDiveResponse = tUtil.testData(HealthDeepDiveResponse[].class,
				APIConstants.HEALTH_DEEP_DIVE_RESPONSE_JSON_FILE_PATH);

		for (int i = 0; i < attritionDeepDiveRequest.size(); i++) {
			var healthRequest = attritionDeepDiveRequest.get(i);
			var healthResponse = healthDeepDiveResponse.get(i);

			String reporteeName = healthRequest.getReportee().getReporteeName();
			String positionName = healthRequest.getReportee().getPositionName();
			String reporteeEmail = healthRequest.getReportee().getReporteeEmail();
			boolean selected = healthRequest.getReportee().isSelected();
			String timelineText = healthRequest.getTimeline().getTimelineText();
			boolean selected2 = healthRequest.getTimeline().isSelected();
			String year = healthRequest.getTimeline().getYear();
			String spanText = healthRequest.getSpan().getSpanText();
			boolean selected3 = healthRequest.getSpan().isSelected();

			Reportee reportee = new Reportee(reporteeName, positionName, reporteeEmail, selected);
			Span span = new Span(spanText, selected3);
			Timeline timeline = new Timeline(timelineText, selected2, year);
			TeamMetricsAndHealthDeepDive hiring = new TeamMetricsAndHealthDeepDive(timeline, span, reportee);

			Response response = restClient.post(HEALTH_CONNECT_SURVEY_DEEPDIVE_ENDPOINT_URL, "json", hiring, cookies,
					selected3);
			Assert.assertEquals(utils.statusCodeValidation(response), APIHttpStatus.Ok_200.getCode());
			//response.prettyPrint();
			// utils.schemaValidation(response,
			// APIConstants.HEALTH_CONNECT_SURVEY_DEEP_DIVE_SCHEMA_VALIDATION_FILE);

			//System.out.println("Insights********************************************************************");
			String expectedHealthDisplayLabel = healthResponse.getInsights().getDisplayLabel();
			List<String> actualHealthDisplayLabel = jsonPathValidator.readList(response, "$..insights.displayLabel");
			softAssert.assertEquals(actualHealthDisplayLabel.get(0), expectedHealthDisplayLabel);
			String expectedHealthStatisticsVal = healthResponse.getInsights().getStatisticsVal();
			List<String> actualHealthStatisticsVal = jsonPathValidator.readList(response, "$..insights.statisticsVal");
			softAssert.assertEquals(actualHealthStatisticsVal.get(0), expectedHealthStatisticsVal);
			String expectedHealthDisplayValue = healthResponse.getInsights().getDisplayValue();
			List<String> actualHealthDisplayValue = jsonPathValidator.readList(response, "$..insights.displayValue");
			softAssert.assertEquals(actualHealthDisplayValue.get(0), expectedHealthDisplayValue);

			//System.out.println("Survey********************************************************************");
			String expectedHealthSurveyTitle = healthResponse.getSurvey().getTitle();
			List<String> actualHealthSurveyTitle = jsonPathValidator.readJson(response, "$..survey.title");
			softAssert.assertEquals(actualHealthSurveyTitle.get(0), expectedHealthSurveyTitle);
			String expectedHealthSurveySUbTitle = healthResponse.getSurvey().getLevelTwoTitle();
			List<String> actualHealthSurveySubTitle = jsonPathValidator.readJson(response, "$..survey.levelTwoTitle");
			softAssert.assertEquals(actualHealthSurveySubTitle.get(0), expectedHealthSurveySUbTitle);

			List<HealthDeepDiveResponse.Columns> expectedHealthSurveyColumnsList = healthResponse.getSurvey()
					.getColumns();
			List<HealthDeepDiveResponse.Columns> actualHealthSurveyColumnsList = jsonPathValidator.readList(response,
					"$..survey.columns[*]");
			ObjectMapper objectMapper = new ObjectMapper();
			if (expectedHealthSurveyColumnsList.size() == actualHealthSurveyColumnsList.size()
					&& expectedHealthSurveyColumnsList.size() != 0) {
				for (int j = 0; j < expectedHealthSurveyColumnsList.size(); j++) {
					HealthDeepDiveResponse.Columns actualList = objectMapper
							.convertValue(actualHealthSurveyColumnsList.get(j), HealthDeepDiveResponse.Columns.class);
					HealthDeepDiveResponse.Columns expectedList = expectedHealthSurveyColumnsList.get(j);
					getColumnListResponse(actualList, expectedList);
				}
			}

			List<Data> expectedHealthSurveyDataList = healthResponse.getSurvey().getData();
			List<Map<String, Object>> actualHealthSurveyDataList = jsonPathValidator.readList(response,
					"$..survey..data[*]");
			if (expectedHealthSurveyDataList.size() == actualHealthSurveyDataList.size()
					&& expectedHealthSurveyDataList.size() != 0) {
				for (int j = 0; j < expectedHealthSurveyDataList.size(); j++) {
					Data actualDataObject = objectMapper.convertValue(actualHealthSurveyDataList.get(j), Data.class);
					HealthDeepDiveResponse.Col0 actualCol0Data = actualDataObject.getCol0();
					HealthDeepDiveResponse.Col0 expectedCol0Data = expectedHealthSurveyDataList.get(j).getCol0();
					expectedStatisticsTrendInd(actualCol0Data, expectedCol0Data);
					HealthDeepDiveResponse.Col1 actualCol1Data = actualDataObject.getCol1();
					HealthDeepDiveResponse.Col1 expectedCol1Data = expectedHealthSurveyDataList.get(j).getCol1();
					expectedStatisticsTrendInd(actualCol1Data, expectedCol1Data);
					HealthDeepDiveResponse.Col2 actualCol2Data = actualDataObject.getCol2();
					HealthDeepDiveResponse.Col2 expectedCol2Data = expectedHealthSurveyDataList.get(j).getCol2();
					expectedStatisticsTrendInd(actualCol2Data, expectedCol2Data);
					HealthDeepDiveResponse.Col3 actualCol3Data = actualDataObject.getCol3();
					HealthDeepDiveResponse.Col3 expectedCol3Data = expectedHealthSurveyDataList.get(j).getCol3();
					expectedStatisticsTrendInd(actualCol3Data, expectedCol3Data);
				}
			}
			softAssert.assertAll();
		}
	}

	private void expectedStatisticsTrendInd(HealthDeepDiveResponse.Col0 actualData,
			HealthDeepDiveResponse.Col0 expectedData) {
		String actualDisplayText = actualData.getDisplayText();
		String expctedDisplayText = expectedData.getDisplayText();
		String actualStatisticsVal = actualData.getStatisticsVal();
		String expectedStatisticsVal = expectedData.getStatisticsVal();
		String actualStatisticsTrendInd = actualData.getStatisticsTrendInd();
		String expectedStatisticsTrendInd = expectedData.getStatisticsTrendInd();
		softAssert.assertEquals(actualDisplayText, expctedDisplayText);
		softAssert.assertEquals(actualStatisticsVal, expectedStatisticsVal);
		softAssert.assertEquals(actualStatisticsTrendInd, expectedStatisticsTrendInd);
	}

	private void expectedStatisticsTrendInd(HealthDeepDiveResponse.Col1 actualData,
			HealthDeepDiveResponse.Col1 expectedData) {
		String actualDisplayText = actualData.getDisplayText();
		String expctedDisplayText = expectedData.getDisplayText();
		String actualStatisticsVal = actualData.getStatisticsVal();
		String expectedStatisticsVal = expectedData.getStatisticsVal();
		String actualStatisticsTrendInd = actualData.getStatisticsTrendInd();
		String expectedStatisticsTrendInd = expectedData.getStatisticsTrendInd();
		softAssert.assertEquals(actualDisplayText, expctedDisplayText);
		softAssert.assertEquals(actualStatisticsVal, expectedStatisticsVal);
		softAssert.assertEquals(actualStatisticsTrendInd, expectedStatisticsTrendInd);
	}

	private void expectedStatisticsTrendInd(HealthDeepDiveResponse.Col2 actualData,
			HealthDeepDiveResponse.Col2 expectedData) {
		String actualDisplayText = actualData.getDisplayText();
		String expctedDisplayText = expectedData.getDisplayText();
		String actualStatisticsVal = actualData.getStatisticsVal();
		String expectedStatisticsVal = expectedData.getStatisticsVal();
		String actualStatisticsTrendInd = actualData.getStatisticsTrendInd();
		String expectedStatisticsTrendInd = expectedData.getStatisticsTrendInd();
		softAssert.assertEquals(actualDisplayText, expctedDisplayText);
		softAssert.assertEquals(actualStatisticsVal, expectedStatisticsVal);
		softAssert.assertEquals(actualStatisticsTrendInd, expectedStatisticsTrendInd);
	}

	private void expectedStatisticsTrendInd(HealthDeepDiveResponse.Col3 actualData,
			HealthDeepDiveResponse.Col3 expectedData) {
		String actualDisplayText = actualData.getDisplayText();
		String expctedDisplayText = expectedData.getDisplayText();
		String actualStatisticsVal = actualData.getStatisticsVal();
		String expectedStatisticsVal = expectedData.getStatisticsVal();
		String actualStatisticsTrendInd = actualData.getStatisticsTrendInd();
		String expectedStatisticsTrendInd = expectedData.getStatisticsTrendInd();
		softAssert.assertEquals(actualDisplayText, expctedDisplayText);
		softAssert.assertEquals(actualStatisticsVal, expectedStatisticsVal);
		softAssert.assertEquals(actualStatisticsTrendInd, expectedStatisticsTrendInd);
	}

	private void getColumnListResponse(Columns actualList, Columns expectedList) {
		String actualTitle = actualList.getTitle();
		String expectedTitle = expectedList.getTitle();
		String actualSubTitle = actualList.getLevelTwoTitle();
		String expectedSubTitle = expectedList.getLevelTwoTitle();
		String actualKey = actualList.getKey();
		String expectedKey = expectedList.getKey();
		softAssert.assertEquals(actualTitle, expectedTitle);
		softAssert.assertEquals(actualSubTitle, expectedSubTitle);
		softAssert.assertEquals(actualKey, expectedKey);

	}

	private void getColumnListResponse(HealthDeepDiveResponse.Columns actualList,
			HealthDeepDiveResponse.Columns expectedList) {
		String actualTitle = actualList.getTitle();
		String expectedTitle = expectedList.getTitle();
		String actualSubTitle = actualList.getLevelTwoTitle();
		String expectedSubTitle = expectedList.getLevelTwoTitle();
		String actualKey = actualList.getKey();
		String expectedKey = expectedList.getKey();
		softAssert.assertEquals(actualTitle, expectedTitle);
		softAssert.assertEquals(actualSubTitle, expectedSubTitle);
		softAssert.assertEquals(actualKey, expectedKey);

	}

	private void getColumnListResponse(HiringDeepDiveResponsePojo.Columns actualList,
			HiringDeepDiveResponsePojo.Columns expectedList) {
		String actualTitle = actualList.getTitle();
		String expectedTitle = expectedList.getTitle();
		String actualSubTitle = actualList.getLevelTwoTitle();
		String expectedSubTitle = expectedList.getLevelTwoTitle();
		String actualKey = actualList.getKey();
		String expectedKey = expectedList.getKey();
		softAssert.assertEquals(actualTitle, expectedTitle);
		softAssert.assertEquals(actualSubTitle, expectedSubTitle);
		softAssert.assertEquals(actualKey, expectedKey);

	}

	private void getSeriesListResponse(Series actualList, Series expectedList) {
		String actualName = actualList.getName();
		String expectedName = expectedList.getName();
		List<Integer> actualData = actualList.getData();
		List<Integer> expectedData = expectedList.getData();
		List<Integer> actualValue = actualList.getValues();
		List<Integer> expectedValue = expectedList.getValues();
		String actualType = actualList.getType();
		String expectedType = expectedList.getType();
		softAssert.assertEquals(actualName, expectedName);
		softAssert.assertEquals(actualData, expectedData);
		softAssert.assertEquals(actualValue, expectedValue);
		softAssert.assertEquals(actualType, expectedType);
		softAssert.assertAll();
	}

	private void getSeriesListResponse(StructureDeepDiveResponsePojo.Series actualList,
			StructureDeepDiveResponsePojo.Series expectedList) {
		String actualName = actualList.getName();
		String expectedName = expectedList.getName();
		List<Integer> actualData = actualList.getData();
		List<Integer> expectedData = expectedList.getData();
		List<Integer> actualValue = actualList.getValues();
		List<Integer> expectedValue = expectedList.getValues();
		String actualType = actualList.getType();
		String expectedType = expectedList.getType();
		softAssert.assertEquals(actualName, expectedName);
		softAssert.assertEquals(actualData, expectedData);
		softAssert.assertEquals(actualValue, expectedValue);
		softAssert.assertEquals(actualType, expectedType);
		softAssert.assertAll();
	}

	private void getSeriesListResponse(HiringDeepDiveResponsePojo.Series actualList,
			HiringDeepDiveResponsePojo.Series expectedList) {
		String actualName = actualList.getName();
		String expectedName = expectedList.getName();
		List<Integer> actualData = actualList.getData();
		List<Integer> expectedData = expectedList.getData();
		List<Integer> actualValue = actualList.getValues();
		List<Integer> expectedValue = expectedList.getValues();
		String actualType = actualList.getType();
		String expectedType = expectedList.getType();
		softAssert.assertEquals(actualName, expectedName);
		softAssert.assertEquals(actualData, expectedData);
		softAssert.assertEquals(actualValue, expectedValue);
		softAssert.assertEquals(actualType, expectedType);
		softAssert.assertAll();
	}

	private void getSeriesListResponse(FunnelItemsList actualList, FunnelItemsList expectedList) {

		String actualDisplayText = actualList.getDisplayText();
		String expectedDisplayText = expectedList.getDisplayText();
		String actualFunnelItemKey = actualList.getFunnelItemKey();
		String expectedFunnelItemKey = expectedList.getFunnelItemKey();
		Integer actualPercentValue = actualList.getPercentValue();
		Integer expectedPercentValue = expectedList.getPercentValue();
		softAssert.assertEquals(actualDisplayText, expectedDisplayText);
		softAssert.assertEquals(actualFunnelItemKey, expectedFunnelItemKey);
		softAssert.assertEquals(actualPercentValue, expectedPercentValue);
		softAssert.assertAll();
	}

//	private void checkResponse(AttritionDeepDiveResponsePojo.Grade attritionResponse, Response response,String path) {
//		ObjectMapper objectMapper = new ObjectMapper();
//		List<String> expectedSubTitle = attritionResponse.getLegend().getData();
//		List<String> actualSubTitle = jsonPathValidator.readList(response, "$.."+path+".legend.data[*]");	
//		softAssert.assertEquals(actualSubTitle, expectedSubTitle);
//		
//		List<Series> expectedSeriesList = attritionResponse.getSeries();
//		System.out.println(expectedSeriesList);
//		List<Series> actualSeriesList = jsonPathValidator.readList(response, "$."+path+".series[*]");
//		System.out.println(actualSeriesList);
//		if (expectedSeriesList.size() == actualSeriesList.size() && expectedSeriesList.size()!=0) {
//			for (int j = 0; j < expectedSeriesList.size(); j++) {
//				Series actualList = objectMapper.convertValue(actualSeriesList.get(j),
//						Series.class);
//				Series expectedList = expectedSeriesList.get(j);
//				getSeriesListResponse(actualList, expectedList);
//			}
//		}
//		String expectedXaxisType = attritionResponse.getXaxis().getType();
//		List<Object> actualXaxisType = jsonPathValidator.readList(response, "$.."+path+".xaxis.type");
//		softAssert.assertEquals(actualXaxisType.get(0), expectedXaxisType);
//		
//		List<String> expectedXaxisData = attritionResponse.getXaxis().getData();
//		List<String> actualXaxisData = jsonPathValidator.readList(response, "$.."+path+".xaxis.data[*]");
//		softAssert.assertEquals(actualXaxisData, expectedXaxisData);
//		
//		String expectedYaxisType = attritionResponse.getYaxis().getType();
//		List<Object> actualYaxisType = jsonPathValidator.readList(response, "$.."+path+".yaxis.type");
//		softAssert.assertEquals(actualYaxisType.get(0), expectedYaxisType);
//		
//		List<String> expectedYaxisData = attritionResponse.getYaxis().getData();
//		List<String> actualYaxisData = jsonPathValidator.readList(response, "$.."+path+".yaxis.data[*]");
//		softAssert.assertEquals(actualYaxisData, expectedYaxisData);
//		softAssert.assertAll();
//	}

//	private void checkResponse(AttritionDeepDiveResponsePojo.Performance attritionResponse, Response response,String path) {
//		ObjectMapper objectMapper = new ObjectMapper();
//		List<String> expectedSubTitle = attritionResponse.getLegend().getData();
//		List<String> actualSubTitle = jsonPathValidator.readList(response, "$.."+path+".legend.data[*]");	
//		softAssert.assertEquals(actualSubTitle, expectedSubTitle);
//		
//		List<Series> expectedSeriesList = attritionResponse.getSeries();
//		System.out.println(expectedSeriesList);
//		List<Series> actualSeriesList = jsonPathValidator.readList(response, "$."+path+".series[*]");
//		System.out.println(actualSeriesList);
//		if (expectedSeriesList.size() == actualSeriesList.size() && expectedSeriesList.size()!=0) {
//			for (int j = 0; j < expectedSeriesList.size(); j++) {
//				Series actualList = objectMapper.convertValue(actualSeriesList.get(j),
//						Series.class);
//				Series expectedList = expectedSeriesList.get(j);
//				getSeriesListResponse(actualList, expectedList);
//			}
//		}
//		String expectedXaxisType = attritionResponse.getXaxis().getType();
//		List<Object> actualXaxisType = jsonPathValidator.readList(response, "$.."+path+".xaxis.type");
//		softAssert.assertEquals(actualXaxisType.get(0), expectedXaxisType);
//		
//		List<String> expectedXaxisData = attritionResponse.getXaxis().getData();
//		List<String> actualXaxisData = jsonPathValidator.readList(response, "$.."+path+".xaxis.data[*]");
//		softAssert.assertEquals(actualXaxisData, expectedXaxisData);
//		
//		String expectedYaxisType = attritionResponse.getYaxis().getType();
//		List<Object> actualYaxisType = jsonPathValidator.readList(response, "$.."+path+".yaxis.type");
//		softAssert.assertEquals(actualYaxisType.get(0), expectedYaxisType);
//		
//		List<String> expectedYaxisData = attritionResponse.getYaxis().getData();
//		List<String> actualYaxisData = jsonPathValidator.readList(response, "$.."+path+".yaxis.data[*]");
//		softAssert.assertEquals(actualYaxisData, expectedYaxisData);
//		softAssert.assertAll();
//	}
//	private void checkResponse(AttritionDeepDiveResponsePojo.Gender attritionResponse, Response response,String path) {
//		ObjectMapper objectMapper = new ObjectMapper();
//		List<String> expectedSubTitle = attritionResponse.getLegend().getData();
//		List<String> actualSubTitle = jsonPathValidator.readList(response, "$.."+path+".legend.data[*]");	
//		softAssert.assertEquals(actualSubTitle, expectedSubTitle);
//		
//		List<Series> expectedSeriesList = attritionResponse.getSeries();
//		System.out.println(expectedSeriesList);
//		List<Series> actualSeriesList = jsonPathValidator.readList(response, "$."+path+".series[*]");
//		System.out.println(actualSeriesList);
//		if (expectedSeriesList.size() == actualSeriesList.size() && expectedSeriesList.size()!=0) {
//			for (int j = 0; j < expectedSeriesList.size(); j++) {
//				Series actualList = objectMapper.convertValue(actualSeriesList.get(j),
//						Series.class);
//				Series expectedList = expectedSeriesList.get(j);
//				getSeriesListResponse(actualList, expectedList);
//			}
//		}
//		String expectedXaxisType = attritionResponse.getXaxis().getType();
//		List<Object> actualXaxisType = jsonPathValidator.readList(response, "$.."+path+".xaxis.type");
//		softAssert.assertEquals(actualXaxisType.get(0), expectedXaxisType);
//		
//		List<String> expectedXaxisData = attritionResponse.getXaxis().getData();
//		List<String> actualXaxisData = jsonPathValidator.readList(response, "$.."+path+".xaxis.data[*]");
//		softAssert.assertEquals(actualXaxisData, expectedXaxisData);
//		
//		String expectedYaxisType = attritionResponse.getYaxis().getType();
//		List<Object> actualYaxisType = jsonPathValidator.readList(response, "$.."+path+".yaxis.type");
//		softAssert.assertEquals(actualYaxisType.get(0), expectedYaxisType);
//		
//		List<String> expectedYaxisData = attritionResponse.getYaxis().getData();
//		List<String> actualYaxisData = jsonPathValidator.readList(response, "$.."+path+".yaxis.data[*]");
//		softAssert.assertEquals(actualYaxisData, expectedYaxisData);
//		softAssert.assertAll();
//	}
//	
//	private void checkResponse(AttritionDeepDiveResponsePojo.AttritionType attritionResponse, Response response,String path) {
//		ObjectMapper objectMapper = new ObjectMapper();
//		List<String> expectedSubTitle = attritionResponse.getLegend().getData();
//		List<String> actualSubTitle = jsonPathValidator.readList(response, "$.."+path+".legend.data[*]");	
//		softAssert.assertEquals(actualSubTitle, expectedSubTitle);
//		
//		List<Series> expectedSeriesList = attritionResponse.getSeries();
//		System.out.println(expectedSeriesList);
//		List<Series> actualSeriesList = jsonPathValidator.readList(response, "$."+path+".series[*]");
//		System.out.println(actualSeriesList);
//		if (expectedSeriesList.size() == actualSeriesList.size() && expectedSeriesList.size()!=0) {
//			for (int j = 0; j < expectedSeriesList.size(); j++) {
//				Series actualList = objectMapper.convertValue(actualSeriesList.get(j),
//						Series.class);
//				Series expectedList = expectedSeriesList.get(j);
//				getSeriesListResponse(actualList, expectedList);
//			}
//		}
//		String expectedXaxisType = attritionResponse.getXaxis().getType();
//		List<Object> actualXaxisType = jsonPathValidator.readList(response, "$.."+path+".xaxis.type");
//		softAssert.assertEquals(actualXaxisType.get(0), expectedXaxisType);
//		
//		List<String> expectedXaxisData = attritionResponse.getXaxis().getData();
//		List<String> actualXaxisData = jsonPathValidator.readList(response, "$.."+path+".xaxis.data[*]");
//		softAssert.assertEquals(actualXaxisData, expectedXaxisData);
//		
//		String expectedYaxisType = attritionResponse.getYaxis().getType();
//		List<Object> actualYaxisType = jsonPathValidator.readList(response, "$.."+path+".yaxis.type");
//		softAssert.assertEquals(actualYaxisType.get(0), expectedYaxisType);
//		
//		List<String> expectedYaxisData = attritionResponse.getYaxis().getData();
//		List<String> actualYaxisData = jsonPathValidator.readList(response, "$.."+path+".yaxis.data[*]");
//		softAssert.assertEquals(actualYaxisData, expectedYaxisData);
//		softAssert.assertAll();
//	}
}
