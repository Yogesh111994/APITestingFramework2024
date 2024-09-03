package com.qa.phonepe.responsepojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class AttritionDeepDiveResponsePojo {

	private SeperationReason seperationReason;
	private Grade grade;
	private Performance performance;
	private Gender gender;
	private AttritionType attritionType;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class SeperationReason {
		private String title;
		private String levelTwoTitle;
//		private Integer openCount;
//		private Integer completedCount;
//		private Integer expiredCount;
		private List<Columns> columns;
		private List<Object> data;
		private String footer;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class Columns {
		private String title;
		private String levelTwoTitle;
		private String key;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class Grade {
		private Legend legend;
		private List<Series> series;
		private Xaxis xaxis;
		private Yaxis yaxis;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class Legend {
		private List<String> data;

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class Series {
		private String name;
		private List<Integer> data;
		private List<Integer> values;
		private String type;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class Xaxis {
		private String type;
		private List<String> data;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class Yaxis {
		private String type;
		private List<String> data;
	}

	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class Performance {
		private Legend legend;
		private List<Series> series;
		private Xaxis xaxis;
		private Yaxis yaxis;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class Gender {
		private Legend legend;
		private List<Series> series;
		private Xaxis xaxis;
		private Yaxis yaxis;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class AttritionType {
		private Legend legend;
		private List<Series> series;
		private Xaxis xaxis;
		private Yaxis yaxis;
	}
}
