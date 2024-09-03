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
public class SummaryResponse {

	private Metrics metrics;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class Metrics{
	private SummaryToolTips summaryToolTips;
    private List<CardItem> attritions;
    private List<CardItem> hirings;
    private List<CardItem> structures;
	}
   
    public static class SummaryToolTips {
        
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(Include.NON_NULL)
    public static class Attrition {
       
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
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(Include.NON_NULL)
    public static class Hiring {
        
        private String displayLabel;
        private String displayValue;
        private String statisticsVal;
        private String statisticsTrendInd;
        private String color;
        private String itemChanged;
        private List<CardItem> cardItemList;
        private List<FunnelItem> funnelItemList;
        private String toolTip;

        // Getters and Setters
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(Include.NON_NULL)
    public static class Structure {
       
        private String displayLabel;
        private String displayValue;
        private String statisticsVal;
        private String statisticsTrendInd;
        private String color;
        private String itemChanged;
        private List<CardItem> cardItemList;
        private List<FunnelItem> funnelItemList;
        private String toolTip;

        // Getters and Setters
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(Include.NON_NULL)
    public static class CardItem {
       
        private String displayLabel;
        private String displayValue;
        private String statisticsVal;
        private String statisticsTrendInd;
        private String color;
        private String itemChanged;
        private List<CardItem> cardItemList;
        private List<FunnelItem> funnelItemList;
        private String toolTip;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonInclude(Include.NON_NULL)
        public static class CardItemList{
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
    }
   
    public static class FunnelItem {
        // Add fields and methods as needed
        // Getters and Setters
    }
    
    
}
