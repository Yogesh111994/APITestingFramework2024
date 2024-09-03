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
public class AddRemark {

	private Integer actionId;
	private String remarkText;
	private String remarkTextFormat;
	private String remarkTextFormatted;
}
