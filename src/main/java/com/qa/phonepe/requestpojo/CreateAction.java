package com.qa.phonepe.requestpojo;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CreateAction {

	private ActionData actionData;
	private Reportee reportee;
	private Timeline timeline;
	private String status;
	
	
	public CreateAction(Reportee reportee, Timeline timeline, String status) {
		this.reportee = reportee;
		this.timeline = timeline;
		this.status = status;
	}
	
	//To test Negative test scenario this constructor is created
	
	
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class ActionData{
		private String assignedToEmail;
		private String actionFocusArea;
		private String dueDate;
		private String actionDesc;
		private String actionDescFormatted;
		private String actionDescFormat;
		
		
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
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class Timeline{
		private String timelineText;
		private boolean selected;
		private String year;
	}
	
	
	
}
