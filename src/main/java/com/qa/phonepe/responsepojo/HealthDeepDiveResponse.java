package com.qa.phonepe.responsepojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qa.phonepe.responsepojo.SummaryResponse.CardItem;
import com.qa.phonepe.responsepojo.SummaryResponse.FunnelItem;

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
public class HealthDeepDiveResponse {

	private Insights insights;
	private Survey survey;
	
	
	@Getter
	@Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown = true) 
    public static class Insights { 
        private String displayLabel;
        private String displayValue;
        private String statisticsVal;
        private String statisticsTrendInd;
        private String color;
        private String itemChanged;
        private List<CardItem> cardItemList;
        private List<FunnelItem> funnelItemList;
        private String toolTip;
        
    }
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown = true) 
	public static class Survey {
		private String title;
		private String levelTwoTitle;
		private List<Columns> columns;
		private List<Data> data;
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
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown = true) 
	public static class Data{
		@JsonProperty("Col0")
		private Col0 col0;
		@JsonProperty("Col1")
		private Col1 col1;
		@JsonProperty("Col2")
		private Col2 col2;
		@JsonProperty("Col3")
		private Col3 col3;
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown = true) 
	public static class Col0{
		private String color;
		private String displayText;
		private String statisticsVal;
		private String statisticsTrendInd;
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown = true) 
	public static class Col1{
		private String color;
		private String displayText;
		private String statisticsVal;
		private String statisticsTrendInd;
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown = true) 
	public static class Col2{
		private String color;
		private String displayText;
		private String statisticsVal;
		private String statisticsTrendInd;
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown = true) 
	public static class Col3{
		private String color;
		private String displayText;
		private String statisticsVal;
		private String statisticsTrendInd;
	}
	
	
}
