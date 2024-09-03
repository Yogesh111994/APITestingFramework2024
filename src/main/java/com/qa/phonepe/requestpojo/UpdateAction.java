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
public class UpdateAction {

	private UpdateActionData actionData;
	private UpdateReportee reportee;
	private UpdateTimeline timeline;
	private String status;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class UpdateActionData {
		private String actionFocusArea;
		private String dueDate;
		private String actionDesc;
		private String actionDescFormatted;
		private String actionDescFormat;
		private Integer actionId;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class UpdateReportee {
		private String reporteeName;
		private String positionName;
		private String reporteeEmail;
		private boolean selected;

	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class UpdateTimeline {
		private String timelineText;
		private boolean selected;
		private String year;
	}
}
