package com.qa.phonepe.responsepojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class StructureDeepDiveResponsePojo {

	private AOP aop;
	private headCountChange headCountChange;
	private Gender Gender;
	private Pyramid pyramid;
	
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
	public static class AOP {
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
	public static class headCountChange {
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
	public static class Pyramid {
		private Legend legend;
		private List<Series> series;
		private Xaxis xaxis;
		private Yaxis yaxis;
	}
}
