package com.qa.phonepe.requestpojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class TeamMetricsAndHealthDeepDive {
	private Timeline timeline;
	private Span span;
	private Reportee reportee;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static  class Timeline {
		private String timelineText;
		private boolean selected;
		private String year;
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class Span{
		private String spanText;
		private boolean selected;
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class Reportee{
		private String reporteeName;
		private String positionName;
		private String reporteeEmail;
		private boolean selected;
	}
	
	
}
