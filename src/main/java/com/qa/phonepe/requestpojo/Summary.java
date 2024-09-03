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
public class Summary {

	private Reportee reportee;
	private Timeline timeline;
	private Span span;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class Reportee {
		private String reporteeName;
		private String positionName;
		private String reporteeEmail;
		private boolean selected;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class Timeline {
		private String timelineText;
		private boolean selected;
		private String year;
		private String timelineKey;
		
		public Timeline(String reporteeName, boolean selected, String year) {
			this.timelineText = reporteeName;
			this.selected = selected;
			this.year = year;
		}

	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class Span {
		private String spanText;
		private boolean selected;

	}

}
