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
public class DeleteAction {

	
	private DeleteActionData actionData;
	private DeleteReportee reportee;
	private DeleteTimeline timeline;
	private String status;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	//@JsonInclude(Include.NON_NULL)
	public static class DeleteActionData{
		private Integer actionId;

	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class DeleteReportee{
		private String reporteeName;
		private String positionName;
		private String reporteeEmail;
		private boolean selected;
		
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class DeleteTimeline{
		private String timelineText;
		private boolean selected;
		private String year;
	}
	
}
