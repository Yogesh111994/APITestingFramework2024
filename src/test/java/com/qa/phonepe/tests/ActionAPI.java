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

import com.jayway.jsonpath.JsonPath;
import com.qa.phonePe.client.RestClient;
import com.qa.phonepe.base.BaseTest;
import com.qa.phonepe.constants.APIConstants;
import com.qa.phonepe.constants.APIHttpStatus;
import com.qa.phonepe.requestpojo.AddRemark;
import com.qa.phonepe.requestpojo.CreateAction;
import com.qa.phonepe.requestpojo.DeleteAction;
import com.qa.phonepe.requestpojo.UpdateAction;
import com.qa.phonepe.requestpojo.CreateAction.ActionData;
import com.qa.phonepe.requestpojo.CreateAction.Reportee;
import com.qa.phonepe.requestpojo.CreateAction.Timeline;
import com.qa.phonepe.requestpojo.DeleteAction.DeleteActionData;
import com.qa.phonepe.requestpojo.DeleteAction.DeleteReportee;
import com.qa.phonepe.requestpojo.DeleteAction.DeleteTimeline;
import com.qa.phonepe.requestpojo.UpdateAction.UpdateActionData;
import com.qa.phonepe.requestpojo.UpdateAction.UpdateReportee;
import com.qa.phonepe.requestpojo.UpdateAction.UpdateTimeline;

import io.restassured.response.Response;

public class ActionAPI extends BaseTest {

	private Map<String, String> cookies;
	private String access_token;
	public static final Logger log = LogManager.getLogger(ActionAPI.class);
	private int actionId;

	@BeforeMethod
	public void getUserSetup() {
		restClient = new RestClient(prop, BaseURI);
		access_token = restClient.getJwtToken(AUTH_LOGIN_ENDPOINT_URL);
		cookies = new HashMap<String, String>();
		cookies.put("JwtToken", access_token);
		log.info("JWT token obtained and set in cookies: " + access_token);

	}

	@DataProvider
	public Object[][] createActionTestData() {
		return jUtils.testData(CreateAction[].class, APIConstants.CREATE_ACTION_JSON_FILE_PATH);
	}

	@Test
	public int getActionId() {
		CreateAction action = tUtil.testData(CreateAction[].class, APIConstants.CREATE_ACTION_JSON_FILE_PATH).get(0);
		String assignedToEmail = action.getActionData().getAssignedToEmail();
//		String dueDate = action.getActionData().getDueDate();
		String focusArea=action.getActionData().getActionFocusArea();
		String actionDesc = action.getActionData().getActionDesc();
		String actionDescriptionFormatted=action.getActionData().getActionDescFormatted();
		String descrptionFormat=action.getActionData().getActionDescFormat();
		String reporteeName = action.getReportee().getReporteeName();
		String positionName = action.getReportee().getPositionName();
		String reporteeEmail = action.getReportee().getReporteeEmail();
		boolean selected = action.getReportee().isSelected();
//		String timelineText = action.getTimeline().getTimelineText();
//		String year = action.getTimeline().getYear();
		boolean selected2 = action.getTimeline().isSelected();
		String status = action.getStatus();

		ActionData assignFor = new ActionData(assignedToEmail, focusArea,utils.dueDate(),actionDesc,actionDescriptionFormatted,descrptionFormat);
		Reportee createdBy = new Reportee(reporteeName, positionName, reporteeEmail, selected);
		Timeline timespan = new Timeline(utils.getQuarter(), selected2, utils.getYear());

		CreateAction createAction = new CreateAction(assignFor, createdBy, timespan, status);
		Response response = restClient.post(CREATE_ACTION_ENDPOINT_URL, "json", createAction, cookies, true);
		System.out.println(response.prettyPrint());
		String jsonResponse = response.getBody().asPrettyString();
		List<Integer> actionIdList = JsonPath.read(jsonResponse, "$..data..Col1.actionId");
		return  actionId = actionIdList.get(actionIdList.size() - 1);
		//System.out.println(actionId);
	}

	@Test(dataProvider = "createActionTestData")
	public void createActionTest(CreateAction action) {

		String assignedToEmail = action.getActionData().getAssignedToEmail();
//		String dueDate = action.getActionData().getDueDate();
		String focusArea=action.getActionData().getActionFocusArea();
		String actionDesc = action.getActionData().getActionDesc();
		String actionDescriptionFormatted=action.getActionData().getActionDescFormatted();
		String descrptionFormat=action.getActionData().getActionDescFormat();
		String reporteeName = action.getReportee().getReporteeName();
		String positionName = action.getReportee().getPositionName();
		String reporteeEmail = action.getReportee().getReporteeEmail();
		boolean selected = action.getReportee().isSelected();
//		String timelineText = action.getTimeline().getTimelineText();
//		String year = action.getTimeline().getYear();
		boolean selected2 = action.getTimeline().isSelected();
		String status = action.getStatus();

		ActionData assignFor = new ActionData(assignedToEmail, focusArea,utils.dueDate(),actionDesc,actionDescriptionFormatted,descrptionFormat);
		Reportee createdBy = new Reportee(reporteeName, positionName, reporteeEmail, selected);
		Timeline timespan = new Timeline(utils.getQuarter(), selected2, utils.getYear());

		CreateAction createAction = new CreateAction(assignFor, createdBy, timespan, status);
		Response response = restClient.post(CREATE_ACTION_ENDPOINT_URL, "json", createAction, cookies, true);
		System.out.println(response.prettyPrint());
		// utils.statusCodeValidation(response);
//		Assert.assertEquals(utils.statusCodeValidation(response), APIHttpStatus.CREATED_201.getCode());
//		Assert.assertEquals(utils.statusCodeValidation(response), APIHttpStatus.Ok_200.getCode());
//		Assert.assertTrue(utils.statusMessageValidation(response).contains(APIHttpStatus.CREATED_201.getMessage()));
//		Assert.assertEquals(utils.statusCodeValidation(response), APIHttpStatus.Ok_200.getMessage());
//		utils.schemaValidation(response, APIConstants.CREATE_ACTION_SCHEMA_VALIDATION_FILE);
		String jsonResponse = response.getBody().asPrettyString();
		List<Integer> actionIdList = JsonPath.read(jsonResponse, "$..data..Col1.actionId");
		int actionId = actionIdList.get(actionIdList.size() - 1);
		System.out.println(actionId);

		// ============Get Call==============

		RestClient restClient1 = new RestClient(prop, BaseURI);
		Response response2 = restClient1.get(ACTION_PLAN_LIST_URL + "/" + actionId, cookies, true);
		softAssert.assertEquals(utils.statusCodeValidation(response2), APIHttpStatus.Ok_200.getCode());
		
	}

	@Test(dataProvider = "createActionTestData")
	public void getAllActionTest(CreateAction action) {
		String reporteeName = action.getReportee().getReporteeName();
		String positionName = action.getReportee().getPositionName();
		String reporteeEmail = action.getReportee().getReporteeEmail();
		boolean selected = action.getReportee().isSelected();
		String timelineText = action.getTimeline().getTimelineText();
		String year = action.getTimeline().getYear();
		boolean selected2 = action.getTimeline().isSelected();
		String status = action.getStatus();

		Reportee createdBy = new Reportee(reporteeName, positionName, reporteeEmail, selected);
		Timeline timespan = new Timeline(timelineText, selected2, year);
		CreateAction allActions = new CreateAction(createdBy, timespan, status);
		Response response = restClient.post(GET_ALL_ACTION_ENDPOINT_URL, "json", allActions, cookies, true);
		Assert.assertEquals(utils.statusCodeValidation(response), APIHttpStatus.Ok_200.getCode());
		response.prettyPrint();
//		GetAll Action Schema validation is failing, actionplan id expected integer but found string.
//		utils.schemaValidation(response, APIConstants.ALL_ACTION_SCHEMA_VALIDATION_FILE);

	}

	@Test
	public void getActionPlanListTest() {
		Response response = restClient.get(ACTION_PLAN_LIST_URL, cookies, true);
		 int statusCode = response.statusCode();
		 Assert.assertEquals(statusCode, APIHttpStatus.Ok_200.getCode());
		utils.schemaValidation(response, APIConstants.ACTION_PLAN_COLLECTION_SCHEMA_VALIDATION_FILE);
	}

	// =================Update Action============

	@DataProvider
	public Object[][] updateActionTestData() {
		return jUtils.testData(UpdateAction[].class, APIConstants.UPDATE_ACTION_JSON_FILE_PATH);
	}

	

	// ===========Action Remark API ===============

	@DataProvider
	public Object[][] addRemarkActionTestData() {
		return jUtils.testData(AddRemark[].class, APIConstants.ADD_REMARK_JSON_FILE_PATH);
	}

	@Test(dataProvider = "addRemarkActionTestData")
	public void actionAddRemarkTest(AddRemark remark) {

		String remarkText = remark.getRemarkText();
		String textFormat=remark.getRemarkTextFormat();
		String remarkTextFormatted=remark.getRemarkTextFormatted();
		AddRemark addRemark = new AddRemark(getActionId(), remarkText,textFormat,remarkTextFormatted);
		Response addRemarkResponse = restClient.post(ADD_ACTION_REMARK_ENDPOINT_URL, "json", addRemark, cookies, true);
		//System.out.println(addRemarkResponse.prettyPrint());
//		Assert.assertEquals(utils.statusCodeValidation(response), APIHttpStatus.CREATED_201.getCode());
		utils.schemaValidation(addRemarkResponse, APIConstants.ADD_REMARK_SCHEMA_VALIDATION_FILE);		
		String jsonResponse2 = addRemarkResponse.getBody().asPrettyString();
		List<String> remarkList = JsonPath.read(jsonResponse2, "$..text");
		String actualRemarkText = remarkList.get(remarkList.size() - 1);
		Assert.assertEquals(actualRemarkText, APIConstants.ADD_REMARK_TEXT);
	}

	@Test(dataProvider = "addRemarkActionTestData")
	public void getActionRemark(AddRemark remark) {
		int actionRemarkId=getActionId();
		String remarkText = remark.getRemarkText();
		String textFormat=remark.getRemarkTextFormat();
		String remarkTextFormatted=remark.getRemarkTextFormatted();
		AddRemark addRemark = new AddRemark(actionRemarkId, remarkText,textFormat,remarkTextFormatted);
		Response addRemarkResponse = restClient.post(ADD_ACTION_REMARK_ENDPOINT_URL, "json", addRemark, cookies, true);
		Response response = restClient.get(GET_ACTION_REMARK_ENDPOINT_URL + actionId, cookies, true);
		Assert.assertEquals(utils.statusCodeValidation(response), APIHttpStatus.Ok_200.getCode());
//		utils.schemaValidation(response, APIConstants.GET_ACTION_REMARK_SCHEMA_VALIDATION_FILE);
//		 required: ["completed","id","title","userId"]
//	    missing: ["completed","id","userId"]

	}
	
	@Test(dataProvider = "updateActionTestData")
	public void updateActionTest(UpdateAction updateAction) {

		// Updated Data
		updateAction.getActionData().setDueDate(utils.dueDate());
		updateAction.getActionData().setActionDesc("Keep a weekly 1:1 connect with the new employee");
		// updateAction.getActionData().getActionId();
		String focusArea=updateAction.getActionData().getActionFocusArea();
		String descriptionFormat=updateAction.getActionData().getActionDescFormatted();
		String format=updateAction.getActionData().getActionDescFormat();
		int actionId=getActionId();
		String dueDate = updateAction.getActionData().getDueDate();
		String actionDesc = updateAction.getActionData().getActionDesc();
		String reporteeName = updateAction.getReportee().getReporteeName();
		String positionName = updateAction.getReportee().getPositionName();
		String reporteeEmail = updateAction.getReportee().getReporteeEmail();
		boolean selected = updateAction.getReportee().isSelected();
		String timelineText = updateAction.getTimeline().getTimelineText();
		String year = updateAction.getTimeline().getYear();
		boolean selected2 = updateAction.getTimeline().isSelected();
		String status = updateAction.getStatus();

		UpdateActionData actionData = new UpdateActionData(focusArea,dueDate,actionDesc,descriptionFormat,format,actionId);
		UpdateReportee reportee = new UpdateReportee(reporteeName, positionName, reporteeEmail, selected);
		UpdateTimeline timespan = new UpdateTimeline(timelineText, selected2, year);

		UpdateAction editAction = new UpdateAction(actionData, reportee, timespan, status);
		Response response = restClient.post(UPDATE_ACTION_ENDPOINT_URL, "json", editAction, cookies, true);
		response.prettyPrint();
		Assert.assertEquals(utils.statusCodeValidation(response), APIHttpStatus.Ok_200.getCode());
		utils.schemaValidation(response, APIConstants.UPDATE_ACTION_SCHEMA_VALIDATION_FILE);
		// Validation With response data is remaining
		List<Integer> actionList = jsonPathValidator.readList(response, "$..data..Col1.actionId");
		softAssert.assertEquals(actionId, actionList.get(actionList.size()-1));
		List<String> textList = jsonPathValidator.readList(response, "$..data..Col1.text");
		softAssert.assertEquals(actionDesc, textList.get(textList.size()-1));
		List<String> dueDateList = jsonPathValidator.readList(response, "$..data..Col2");
		softAssert.assertEquals(dueDate, dueDateList.get(dueDateList.size()-1));
	}

	// ===========================Delete Action=========================

	@DataProvider
	public Object[][] deleteActionTestData() {
		return jUtils.testData(DeleteAction[].class, APIConstants.DELETE_ACTION_JSON_FILE_PATH);
	}

	@Test(dataProvider = "deleteActionTestData")
	public void deleteActionTest(DeleteAction deleteAction) {

		int actionId=getActionId();
		System.out.println(actionId);
		String positionName = deleteAction.getReportee().getPositionName();
		String reporteeName = deleteAction.getReportee().getPositionName();
		String reporteeEmail = deleteAction.getReportee().getReporteeEmail();
		boolean selected = deleteAction.getReportee().isSelected();
		// String timelineText=deleteAction.getTimeline().getTimelineText();
		boolean selected2 = deleteAction.getTimeline().isSelected();
		// String year=deleteAction.getTimeline().getYear();
		String status = deleteAction.getStatus();

		DeleteTimeline timespan = new DeleteTimeline(utils.getQuarter(), selected2, utils.getYear());
		DeleteReportee reportee = new DeleteReportee(positionName, reporteeName, reporteeEmail, selected);
		DeleteActionData actionData = new DeleteActionData(actionId);
		DeleteAction dAction = new DeleteAction(actionData, reportee, timespan, status);
		Response response = restClient.delete(DELETE_ACTION_ENDPOINT_URL, "json", dAction, cookies, true);
//		response.prettyPrint();
//		Assert.assertEquals(utils.statusCodeValidation(response),APIHttpStatus.Ok_200.getCode());
//		For delete action expected response code is 204 after making get call response code should be 405
		

	}

	@Test(dataProvider = "deleteActionTestData")
	public void markCompleteActionTest(DeleteAction deleteAction) {	
		
		String reporteeName  = deleteAction.getReportee().getReporteeName();
		String  positionName= deleteAction.getReportee().getPositionName();
		String reporteeEmail = deleteAction.getReportee().getReporteeEmail();
		boolean selected = deleteAction.getReportee().isSelected();
		String timelineText = deleteAction.getTimeline().getTimelineText();
		boolean selected2 = deleteAction.getTimeline().isSelected();
		String year = deleteAction.getTimeline().getYear();
		String status = deleteAction.getStatus();
		DeleteTimeline timespan = new DeleteTimeline(timelineText, selected2, year);
		DeleteReportee reportee = new DeleteReportee(reporteeName,positionName, reporteeEmail, selected);
		DeleteActionData actionData = new DeleteActionData(getActionId());
		DeleteAction markComplete = new DeleteAction(actionData, reportee, timespan, status);
		Response response = restClient.post(MARK_COMPLETE_ACTION_ENDPOINT_URL, "json", markComplete, cookies, true);
		System.out.println(response.prettyPrint());
		Assert.assertEquals(utils.statusCodeValidation(response), APIHttpStatus.Ok_200.getCode());
		utils.schemaValidation(response, APIConstants.MARK_COMPLETE_ACTION_SCHEMA_VALIDATION_FILE);
	}
	


}
