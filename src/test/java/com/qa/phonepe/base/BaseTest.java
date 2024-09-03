package com.qa.phonepe.base;

import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.qa.phonePe.client.RestClient;
import com.qa.phonepe.configuration.ConfigurationManager;
import com.qa.phonepe.requestpojo.CreateAction;
import com.qa.phonepe.utils.JsonPathValidator;
import com.qa.phonepe.utils.JsonUtils;
import com.qa.phonepe.utils.StringUtils;
import com.qa.phonepe.utils.TestUtil;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

public class BaseTest {

	protected ConfigurationManager config;
	protected Properties prop;
	protected RestClient restClient;
	protected String BaseURI;
	protected StringUtils utils;
	protected JsonUtils jUtils;
    protected JsonPathValidator jsonPathValidator;
    protected CreateAction createAction;
    protected SoftAssert softAssert;
    protected TestUtil tUtil;
    
	@BeforeTest
	public void setup() {
		RestAssured.filters(new AllureRestAssured());
		config = new ConfigurationManager();
		prop = config.initProp();
		String BaseURI = prop.getProperty("BaseURI");
		this.BaseURI = BaseURI;
		restClient = new RestClient(prop, BaseURI);
		utils = new StringUtils();
		jUtils= new JsonUtils();
		createAction= new CreateAction();
		softAssert= new SoftAssert();
		jsonPathValidator=new JsonPathValidator();
		 tUtil = new TestUtil();
	}

	public static final String AUTH_LOGIN_ENDPOINT_URL = "/api/v1/auth/login";
	// ===========Action Endpoint URL ====================
	public static final String ACTION_PLAN_LIST_URL = "/ui/apis/getActionPlansList";
	public static final String ACTION_PLAN_COLLECTION_URL = "/api/v1/actionplancollection";
	public static final String CREATE_ACTION_ENDPOINT_URL = "/ui/apis/createAction";
	public static final String GET_ALL_ACTION_ENDPOINT_URL="/api/v1/actions/getall";
	public static final String DELETE_ACTION_ENDPOINT_URL="/api/v1/actions/delete";
	public static final String MARK_COMPLETE_ACTION_ENDPOINT_URL="/api/v1/actions/markcomplete";
	public static final String UPDATE_ACTION_ENDPOINT_URL="/api/v1/actions/update";
	
	//=====================Action Remark==================
	public static final String ADD_ACTION_REMARK_ENDPOINT_URL="/api/v1/actionremarks/add";
	public static final String GET_ACTION_REMARK_ENDPOINT_URL="/api/v1/actionremarks/";
	// ===========Top Filters Endpoint Url================
	public static final String MANAGER_REPORTEE_LIST_URL = "/api/v1/reporteelist/manager";
	public static final String DATA_FILTER_ENDPOINT_URL = "/api/v1/datafilters";
	public static final String GET_TOP_FILTER_ENDPOINT_URL = "/api/v1/topfilters";
	public static final String HRBP_REPORTEE_LIST_URL = "/api/v1/reporteelist/hrbp";
	// ===========Summary Endpoint URL ===================
	public static final String SUMMARY_METRICS_ENDPOINT_URL ="/api/v1/summary/reportee/metrics";
	public static final String SUMMARY_HEALTH_ENDPOINT_URL="/api/v1/summary/reportee/health";
	public static final String SUMMARY_CAPABILITIES_ENDPOINT_URL="/api/v1/summary/reportee/capabilities";
	public static final String SUMMARY_ENDPOINT_URL ="/api/v1/summary";
	// ===========Team Metrics Deep Dive =================
	public static final String METRICS_STRUCTURE_DEEPDIVE_ENDPOINT_URL ="/api/v1/deepdive/reportee/metrics/structure";
	public static final String METRICS_HIRING_DEEPDIVE_ENDPOINT_URL ="/api/v1/deepdive/reportee/metrics/hiring";
	public static final String METRICS_ATTRITION_DEEPDIVE_ENDPOINT_URL ="/api/v1/deepdive/reportee/metrics/attrition";
	// ===========Team Health Deep Dive =================
	public static final String HEALTH_CONNECT_SURVEY_DEEPDIVE_ENDPOINT_URL ="/api/v1/deepdive/reportee/health/connectsurvey";
	
	
}
