package com.qa.phonepe.responsepojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ManagerReporteeListResponse {
	
	private List<Heirarchy> reporteeList;
	private String reporteeListToolTip;
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

	private List<Heirarchy> heirarchy;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class Heirarchy{
		private String reporteeName;
		private String reporteeEmail;
		private String positionName;
		private boolean selected;
		private String value;
		private List<Heirarchy> heirarchy;
		
	}
	
}
