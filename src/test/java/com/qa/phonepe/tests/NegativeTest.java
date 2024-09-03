package com.qa.phonepe.tests;

import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.phonePe.client.RestClient;
import com.qa.phonepe.base.BaseTest;
import com.qa.phonepe.constants.APIConstants;
import com.qa.phonepe.constants.APIHttpStatus;
import com.qa.phonepe.requestpojo.CreateAction;
import com.qa.phonepe.requestpojo.CreateAction.ActionData;
import com.qa.phonepe.requestpojo.CreateAction.Reportee;
import com.qa.phonepe.requestpojo.CreateAction.Timeline;

import io.restassured.response.Response;

public class NegativeTest extends BaseTest {

	private static Map<String, String> cookies;
	private static String access_token;

	@BeforeClass
	public void getUserSetup() {
		restClient = new RestClient(prop, BaseURI);
		access_token = restClient.getJwtToken(AUTH_LOGIN_ENDPOINT_URL);
		cookies = new HashMap<String, String>();
		cookies.put(prop.getProperty("JwtToken"), access_token);

	}

	@DataProvider
	public Object[][] createActionTestData() {
		return jUtils.testData(CreateAction[].class, APIConstants.CREATE_ACTION_JSON_FILE_PATH);
	}

	@Test(dataProvider = "createActionTestData")
	public void createActionWithIncorrectBody(CreateAction action) {
//		String assignedToEmail = action.getActionData().getAssignedToEmail();
//		String dueDate = action.getActionData().getDueDate();
//		String actionPlanId = action.getActionData().getActionPlanId();
//		String actionDesc = action.getActionData().getActionDesc();
		String reporteeName = action.getReportee().getReporteeName();
		String positionName = action.getReportee().getPositionName();
		String reporteeEmail = action.getReportee().getReporteeEmail();
		boolean selected = action.getReportee().isSelected();
		String timelineText = action.getTimeline().getTimelineText();
		String year = action.getTimeline().getYear();
		boolean selected2 = action.getTimeline().isSelected();
		String status = action.getStatus();

		// ActionData assignFor = new ActionData(assignedToEmail, dueDate, actionPlanId,
		// actionDesc);
		Reportee createdBy = new Reportee(reporteeName, positionName, reporteeEmail, selected);
		Timeline timespan = new Timeline(timelineText, selected2, year);

		CreateAction createAction = new CreateAction(createdBy, timespan, status);
		Response response = restClient.post(CREATE_ACTION_ENDPOINT_URL, "json", createAction, cookies, true);
		Assert.assertEquals(utils.statusCodeValidation(response), APIHttpStatus.BAD_REQUEST_400.getCode());

	}

	@Test
	public void createActionWithIncorrectURL() {
		CreateAction action = tUtil.testData(CreateAction[].class, APIConstants.CREATE_ACTION_JSON_FILE_PATH).get(0);
		String assignedToEmail = action.getActionData().getAssignedToEmail();
//		String dueDate = action.getActionData().getDueDate();
		String focusArea = action.getActionData().getActionFocusArea();
		String actionDesc = action.getActionData().getActionDesc();
		String actionDescriptionFormatted = action.getActionData().getActionDescFormatted();
		String descrptionFormat = action.getActionData().getActionDescFormat();
		String reporteeName = action.getReportee().getReporteeName();
		String positionName = action.getReportee().getPositionName();
		String reporteeEmail = action.getReportee().getReporteeEmail();
		boolean selected = action.getReportee().isSelected();
//		String timelineText = action.getTimeline().getTimelineText();
//		String year = action.getTimeline().getYear();
		boolean selected2 = action.getTimeline().isSelected();
		String status = action.getStatus();

		ActionData assignFor = new ActionData(assignedToEmail, focusArea, utils.dueDate(), actionDesc,
				actionDescriptionFormatted, descrptionFormat);
		Reportee createdBy = new Reportee(reporteeName, positionName, reporteeEmail, selected);
		Timeline timespan = new Timeline(utils.getQuarter(), selected2, utils.getYear());

		CreateAction createAction = new CreateAction(assignFor, createdBy, timespan, status);
		Response response = restClient.post(CREATE_ACTION_ENDPOINT_URL + "ss", "json", createAction, cookies, true);
		Assert.assertEquals(utils.statusCodeValidation(response), APIHttpStatus.NOT_FOUND_404.getCode());

	}

	@Test
	public void createActionWithIncorrectJwtToken() {
		CreateAction action = tUtil.testData(CreateAction[].class, APIConstants.CREATE_ACTION_JSON_FILE_PATH).get(0);

		String assignedToEmail = action.getActionData().getAssignedToEmail();
//		String dueDate = action.getActionData().getDueDate();
		String focusArea = action.getActionData().getActionFocusArea();
		String actionDesc = action.getActionData().getActionDesc();
		String actionDescriptionFormatted = action.getActionData().getActionDescFormatted();
		String descrptionFormat = action.getActionData().getActionDescFormat();
		String reporteeName = action.getReportee().getReporteeName();
		String positionName = action.getReportee().getPositionName();
		String reporteeEmail = action.getReportee().getReporteeEmail();
		boolean selected = action.getReportee().isSelected();
//		String timelineText = action.getTimeline().getTimelineText();
//		String year = action.getTimeline().getYear();
		boolean selected2 = action.getTimeline().isSelected();
		String status = action.getStatus();

		ActionData assignFor = new ActionData(assignedToEmail, focusArea, utils.dueDate(), actionDesc,
				actionDescriptionFormatted, descrptionFormat);
		Reportee createdBy = new Reportee(reporteeName, positionName, reporteeEmail, selected);
		Timeline timespan = new Timeline(utils.getQuarter(), selected2, utils.getYear());

		CreateAction createAction = new CreateAction(assignFor, createdBy, timespan, status);
		Map<String, String> cookie = new HashMap<String, String>();
		cookies.put(prop.getProperty("JwtToken"), APIConstants.DUMMAY_ACCESS_TOKEN);

		Response response = restClient.post(CREATE_ACTION_ENDPOINT_URL, "json", createAction, cookie, true);
		Assert.assertEquals(utils.statusCodeValidation(response), APIHttpStatus.UNAUTHORIZED_401.getCode());

	}

}
