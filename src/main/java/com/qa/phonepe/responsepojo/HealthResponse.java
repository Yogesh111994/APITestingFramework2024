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
public class HealthResponse {

	private Health health;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public static class Health{
	private SummaryToolTips summaryToolTips;
    private List<CardItem> trends;
    private List<CardItem> highlights;
    private List<CardItem> gaps;
	}
   
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(Include.NON_NULL)
    public static class SummaryToolTips {
        private String  trends;
        private String highlights;
        private String gaps;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(Include.NON_NULL)
    public static class Trends {
       
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
    public static class Highlights {
        
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
    public static class Gaps {
       
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


    }
   
    public static class FunnelItem {
        // Add fields and methods as needed
        // Getters and Setters
    }
}
