package com.qa.phonepe.responsepojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.qa.phonepe.responsepojo.ManagerReporteeListResponse.Heirarchy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class TopFilterResponse {

	private String welcomeText;
	private String manager;
	private List<ReporteeList> reporteeList;
	private List<TimelineList> timelineList;
	private List<SpanList> spanList;
	private String reporteeListToolTip;
	private String spanListToolTip;
	private boolean hrbp;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class ReporteeList{
		private String reporteeName;
		private String reporteeEmail;
		private String positionName;
		private boolean selected;
		private String value;
		private List<Heirarchy> heirarchy;
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class TimelineList{
		private String timelineText;
		private String year;
		private boolean selected;
		private String timelineKey;
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class SpanList{
		private String spanText;
		private boolean selected;
	}
}
