package com.qa.phonePe.client;

import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.qa.phonepe.constants.APIHttpStatus;
import com.qa.phonepe.frameworkexception.APIFrameworkException;
import com.qa.phonepe.requestpojo.LoginCredential;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {

	private static RequestSpecBuilder specBuilder;
	private Properties prop;
	private String BaseURI;
	private boolean isAuthorizationHeaderAdded = false;

	public static final Logger log = LogManager.getLogger(RestClient.class);
	
	public RestClient(Properties prop, String BaseURI) {
		specBuilder = new RequestSpecBuilder();
		this.prop = prop;
		this.BaseURI = BaseURI;
	}

	private void setRequestContent(String contentType) {
		switch (contentType.toLowerCase()) {
		case "json":
			specBuilder.setContentType(ContentType.JSON);
			break;
		case "xml":
			specBuilder.setContentType(ContentType.XML);
			break;
		case "text":
			specBuilder.setContentType(ContentType.TEXT);
			break;
		case "multipart":
			specBuilder.setContentType(ContentType.MULTIPART);
			break;

		default:
			System.out.println("Please pass the right content type" + contentType);
			throw new APIFrameworkException("INVALIDCONTENTTYPE");
		}
	}

//	public void addAuthorization() {
//		if(!isAuthorizationHeaderAdded) {
//		specBuilder.addHeader("Authorization", "Bearer " + prop.getProperty("TokenId"));
//		isAuthorizationHeaderAdded=true;
//		}
//	}

	public void addAuthorization() {
		
		if (!isAuthorizationHeaderAdded) {
			specBuilder.addHeader(prop.getProperty("X-API-KEY").trim(), prop.getProperty("HeaderValue").trim());
			isAuthorizationHeaderAdded = true;

		}
	}

	private RequestSpecification createRequestSpec(Map<String, String> cookieMap) {

		specBuilder.setBaseUri(BaseURI);
		addAuthorization();

		if (cookieMap != null) {
			specBuilder.addCookies(cookieMap);
		}
		return specBuilder.build();
	}

	private RequestSpecification createRequestSpec(Map<String, String> headersMap, Map<String, String> cookieMap) {

		specBuilder.setBaseUri(BaseURI);
		addAuthorization();

		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		if (cookieMap != null) {
			specBuilder.addCookies(cookieMap);
		}
		return specBuilder.build();
	}

	private RequestSpecification createRequestSpec(Map<String, String> headersMap, Map<String, String> cookieMap,
			Map<String, Object> queryParms) {

		specBuilder.setBaseUri(BaseURI);
		addAuthorization();

		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		if (cookieMap != null) {
			specBuilder.addCookies(cookieMap);
		}
		if (queryParms != null) {
			specBuilder.addQueryParams(queryParms);
		}
		return specBuilder.build();
	}

	private RequestSpecification createRequestSpec(Object requestBody, String contentType,
			Map<String, String> cookieMap, Map<String, String> headersMap) {

		specBuilder.setBaseUri(BaseURI);
		addAuthorization();
		setRequestContent(contentType);

		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		if (cookieMap != null) {
			specBuilder.addCookies(cookieMap);
		}

		if (requestBody != null) {
			specBuilder.setBody(requestBody);
		}
		return specBuilder.build();
	}

	private RequestSpecification createRequestSpec(Object requestBody, String contentType,
			Map<String, String> cookieMap) {

		specBuilder.setBaseUri(BaseURI);
		addAuthorization();

		setRequestContent(contentType);
		if (cookieMap != null) {
			specBuilder.addCookies(cookieMap);
		}
		if (requestBody != null) {
			specBuilder.setBody(requestBody);
		}

		return specBuilder.build();
	}

	// HTTP method utils

	// =====================GET===========================

//	public Response get(String serviceUrl, boolean includeAuth, boolean log) {
//		if (log) {
//			return RestAssured.given(createRequestSpec(includeAuth)).log().all().when().get(serviceUrl);
//		}
//		return RestAssured.given(createRequestSpec(includeAuth)).when().get(serviceUrl);
//	}

	public Response get(String serviceUrl, Map<String, String> cookieMap, boolean log) {
		
		if (log) {
			return RestAssured.given(createRequestSpec(cookieMap)).log().all().when().get(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(cookieMap)).when().get(serviceUrl);
	}

	public Response get(String serviceUrl, Map<String, String> headersMap, Map<String, String> cookieMap, boolean log) {
		if (log) {
			
			return RestAssured.given(createRequestSpec(headersMap, cookieMap)).log().all().when().get(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(headersMap, cookieMap)).when().get(serviceUrl);
	}

	public Response get(String serviceUrl, Map<String, String> headersMap, Map<String, String> cookieMap,
			Map<String, Object> queryParms, boolean log) {

		if (log) {
			return RestAssured.given(createRequestSpec(headersMap, cookieMap, queryParms)).log().all().when()
					.get(serviceUrl);
		}

		return RestAssured.given(createRequestSpec(headersMap, cookieMap, queryParms)).when().get(serviceUrl);
	}

	// =====================POST===========================

	public Response post(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap,
			Map<String, String> cookieMap, boolean log) {

		if (log) {
			return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, cookieMap)).log().all()
					.when().post(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, cookieMap)).when()
				.post(serviceUrl);
	}

	public Response post(String serviceUrl, String contentType, Object requestBody, Map<String, String> cookieMap,
			boolean log) {
		
		if (log) {
			return RestAssured.given(createRequestSpec(requestBody, contentType, cookieMap)).log().all().when()
					.post(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, cookieMap)).when().post(serviceUrl);
	}

	// =====================PUT===========================

	public Response put(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap,
			Map<String, String> cookieMap, boolean log) {
		
		if (log) {
			return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, cookieMap)).log().all()
					.when().put(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, cookieMap)).when()
				.put(serviceUrl);
	}

	public Response put(String serviceUrl, String contentType, Object requestBody, Map<String, String> cookieMap,
			boolean log) {
		
		if (log) {
			return RestAssured.given(createRequestSpec(requestBody, contentType, cookieMap)).log().all().when()
					.put(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, cookieMap)).when().put(serviceUrl);
	}

	// =====================PATCH===========================

	public Response patch(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap,
			Map<String, String> cookieMap, boolean log) {
		
		if (log) {
			return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, cookieMap)).log().all()
					.when().patch(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, cookieMap)).when()
				.patch(serviceUrl);
	}

	public Response patch(String serviceUrl, String contentType, Object requestBody, Map<String, String> cookieMap,
			boolean log) {
		
		if (log) {
			return RestAssured.given(createRequestSpec(requestBody, contentType, cookieMap)).log().all().when()
					.patch(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, cookieMap)).when().patch(serviceUrl);
	}

	// =====================DELETE===========================
	public Response delete(String serviceUrl, Map<String, String> cookieMap, boolean log) {
		if (log) {
			
			return RestAssured.given(createRequestSpec(cookieMap)).log().all().when().delete(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(cookieMap)).when().delete(serviceUrl);
	}
	public Response delete(String serviceUrl,String contentType, Object requestBody,Map<String, String> cookieMap, boolean log) {
		if (log) {
			
			return RestAssured.given(createRequestSpec(requestBody, contentType, cookieMap)).log().all().when()
			.delete(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, cookieMap)).when()
				.delete(serviceUrl);
	}
	
	
	

	public String getJwtToken(String serviceURL) {

		LoginCredential cred = new LoginCredential(prop.getProperty("UserId").trim(), prop.getProperty("Password").trim());
		RestAssured.baseURI = prop.getProperty("BaseURI");
		String jwtToken = RestAssured.given().log().all().contentType(ContentType.JSON)
				.header(prop.getProperty("X-API-KEY").trim(), prop.getProperty("HeaderValue").trim()).body(cred).when().log().all()
				.post(serviceURL).then().log().all().assertThat().statusCode(APIHttpStatus.Ok_200.getCode()).extract()
				.path("access_token");

		return jwtToken;

	}

	public void getToken() {

	}

}
