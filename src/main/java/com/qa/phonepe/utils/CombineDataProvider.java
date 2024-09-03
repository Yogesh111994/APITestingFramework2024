package com.qa.phonepe.utils;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.qa.phonepe.constants.APIConstants;
import com.qa.phonepe.requestpojo.Summary;
import com.qa.phonepe.requestpojo.UpdateAction;


public class CombineDataProvider {

	@DataProvider
	public Object[][] summaryData() {
		TestUtil tUtil = new TestUtil();
		List<Summary> summaryRequest = tUtil.testData(Summary[].class, APIConstants.EXTENDED_SUMMARY_JSON_FILTER_PATH);
		List<UpdateAction> updateActionResponse = tUtil.testData(UpdateAction[].class,
				APIConstants.UPDATE_ACTION_JSON_FILE_PATH);
		var testData = new Object[summaryRequest.size()][2];
		for (int i = 0; i < summaryRequest.size(); i++) {
			testData[i][0] = summaryRequest.get(i);
			testData[i][1] = updateActionResponse.get(i);
		}
		return testData;
	}

//	@Test(dataProvider = "summaryData")
//	public void getSummaryData(Summary request, UpdateAction updateaction) {
//		System.out.println(request.getReportee().getReporteeEmail());
//		System.out.println(updateaction.getActionData().getActionDesc());
//
//	}
}
