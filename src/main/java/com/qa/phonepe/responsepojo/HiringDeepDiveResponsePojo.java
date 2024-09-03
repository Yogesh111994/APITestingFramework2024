package com.qa.phonepe.responsepojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true) 
public class HiringDeepDiveResponsePojo {

	private Gender gender;
	private InterviewFunnel interviewFunnel;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown = true) 
	public static class Legend {
		private List<String> data;

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown = true) 
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
	@JsonIgnoreProperties(ignoreUnknown = true) 
	public static class Xaxis {
		private String type;
		private List<String> data;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown = true) 
	public static class Yaxis {
		private String type;
		private List<String> data;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown = true) 
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
	@JsonIgnoreProperties(ignoreUnknown = true) 
	 @JsonInclude(JsonInclude.Include.NON_NULL)
	public static class InterviewFunnel {
		private String title;
		private String levelTwoTitle;
		private String funnelSelectionListTitle;
		private List<String> funnelSelectionList;
		private FunnelItems funnelItems;
		private InterviewStagesData interviewStagesData;
		private OffersData offersData;
	}
	@Setter
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	 @JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown = true) 
	public static class FunnelItems {
		@JsonProperty("Director, T&S")
		private List<FunnelItemsList> Directors;
		@JsonProperty("Software Engineer, DevX (3-5 years)")
		private List<FunnelItemsList> engineers;
		@JsonProperty("Product Manager / Senior Product Manager")
		private List<FunnelItemsList> manager;
	}
	
	@Setter
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	 @JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown = true) 
	public static class FunnelItemsList{
		
		private String displayText;
		private String funnelItemKey;
		private Integer percentValue;
	}
	
	@Setter
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown = true) 
	public static class InterviewStagesData {

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
	@JsonIgnoreProperties(ignoreUnknown = true) 
	public static class OffersData {
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
	@JsonIgnoreProperties(ignoreUnknown = true) 
	public static class Columns {
		private String title;
		private String levelTwoTitle;
		private String key;
	}
}
