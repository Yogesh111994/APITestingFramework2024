package com.qa.phonepe.constants;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SummaryAPIConstant {

	// ReporteeMetricsSummary API test data

	// Attrition
	public static final String ATTRITION_TEXT = "TTM Attrition (0)";
	public static final String STATISTICSVALUE = "0%";
	public static final String SEPRATION_REASON_TITLE = "Separation Reasons";
	public static List<String> REGRETABLE_UN_REGRETABLE_ATTRITION_SUB_TITLE_TEXT = Arrays
			.asList("Regrettable Attrition (Total 0)", "Un-Regrettable Attrition (Total 0)");

	// Structure
	public static final String SUMMARY_TITLE_TEXT = "Summary";
	public static final String AOP_TEXT = "AOP: 0";
	public static final String POSITION_OCCUPIED = "Occupied Positions: 0 (0%)";
	public static final String PYRAMID_TITLE_TEXT = "Pyramid";
	public static final String GENDER_DIVERSITY_TITLE_TEXT = "Gender Diversity";
	public static final List<String> GENDER_DIVERSITY_SUB_TITLE_TEXT = Arrays.asList("Your Span: 0%",
			"Function Average: 0%");

	// HIRING
	public static final String HIRING_SUMMARY_TITLE_TEXT = "Summary";
	public static final String EXTERNAL_HIRING_NUMBER = "Number of external hires (TTM): (3)";
	public static final String HIRING_FUNNEL_TITLE_TEXT = "Summary of Hiring Funnel";
	public static final List<String> SUMMARY_OF_HIRING_FUNNEL_LIST = Arrays.asList("Hiring not initiated : 0",
			"Job approval pending : 0", "Hiring In-Progress (Positions) : 0", "Offer Stage : 0", "Yet to join : 0");
	public static final String HIRING_IN_PROGRESS_ROLE = "Hiring in Progress (0 Roles)";
	// ==================================================================================

	// HEALTH
	public static final List<String> HEALTH_CARD_TITLE = Arrays.asList("trends", "highlights", "gaps");
	// Trends
	public static final String TRENDS_RESPONDENTS_23_24 = "2023-24 : Respondents - 0 (0%)";
	public static final String TRENDS_RESPONDENTS_23_24_AVERAGE = "86%";
	public static final String TRENDS_RESPONDENTS_22_23 = "2022-23 : Respondents - 0 (0%)";
	public static final String TRENDS_RESPONDENTS_22_23_AVERAGE = "74%";
	public static final String TRENDS_RESPONDENTS_21_22 = "2021-22 : Respondents - 8 (57%)";
	public static final String TRENDS_RESPONDENTS_21_22_AVERAGE = "92%";
	// Highlights
	public static final String TOP_SCORING_TITLE_TEXT = "Top 5 Scoring Areas";
	public static final List<String> TOP_SCORING_AREAS = Arrays.asList("Flexibility to balance work / personal needs",
			"Encouraged to learn from failures", "Clear understanding of Roles / Expectations",
			"Ongoing connects with Managers (Formal / Informal)",
			"Inputs / opinion taken into consideration for my areas of work");
	// Gaps
	public static final String LOW_SCORING_TITLE_TEXT = "Lowest 5 Scoring Areas";
	public static final List<String> LOW_SCORING_AREAS = Arrays.asList(
			"Manager gives guidance for career growth in the Org, in line with tech journey",
			"My manager provided the midyear feedback in line with conversations I had in the last 6 months.",
			"I could identify my strength and development areas based on the feedback received.",
			"Manager gives guidance for career growth in the Org.", "Equipped with tools & resources");
	public static final List<String> LOW_SCORING_AREA_AVERAGES = Arrays.asList("60%", "67%", "67%", "71%", "75%");

	// ============================================================================================================
	// Extended
	// Attrition
	public static final String EXTENDED_ATTRITION_TEXT = "TTM Attrition (155)";
	public static final String EXTENDED_STATISTICSVALUE = "11%";
	public static final String BY_GRADE_TITLE_TEXT = "By Grade (155)";
	public static final String GRADES = "";
	public static final List<String> BY_GRADES = Arrays.asList("Grade 1 : 5", "Grade 2 : 7", "Grade 3 : 37",
			"Grade 4 : 58", "Grade 5 : 24", "Grade 6 : 16", "Grade 7 : 6", "Grade 8 : 1", "Grade 9 : 1");
	public static final String BY_GENDER_TITLE_TEXT = "By Gender (155)";
	public static final String MALE_COUNT = "Male (131)";
	public static final String FEMALE_COUNT = "Female (24)";
	public static final String RESIGNATION_COUNT = "Number of approved resignation: 7";
	public static final String EXTENDED_EARLY_ATTRITION = "Early Attrition (0-6 months): 25";
	public static final String EXTENDED_EARLY_ATTRITION_AVERAGE = "11%";
	public static final String EXTENDED_HIGH_PERFORMANCE_ATTRITION = "High Performers Attrition: 6";
	public static final String EXTENDED_HIGH_PERFORMANCE_ATTRITION_AVERAGE = "4%";
	public static final String SEPRATION_REASON_TITLE_TEXT = "Separation Reasons";
	public static final List<String> SEPRATION_TYPES = Arrays.asList("Regrettable Attrition (Total 88)",
			"Un-Regrettable Attrition (Total 67)");
	public static final List<String> REGRETTABLE_ATTRITION_LIST = Arrays.asList("Career Change (8)",
			"Better Compensation (11)", "Reason not captured (6)", "Health Issues (2)", "Family Support (14)",
			"Moving to a Favorable Location (9)", "Role Clarity Missing (1)", "Better Opportunity (9)",
			"Higher Education (4)", "Own Start-Up/ Business (6)", "Conflict of Interest (1)", "Lacking Recognition (1)",
			"Mutual Separation (4)", "Remote Working (3)", "Limited Org. Impact (1)", "Limited Learning (2)",
			"Career Break (3)", "Culture / Values Mismatch (2)", "Poor Performance (1)");
	public static final List<String> UN_REGRETTABLE_ATTRITION_LIST = Arrays.asList("Limited Org. Impact (1)",
			"Career Change (9)", "Culture / Values Mismatch (1)", "Poor Performance (30)", "Family Support (3)",
			"Career Break (2)", "Conflict of Interest (2)", "Health Issues (1)", "Reason not captured (3)",
			"Mutual Separation (3)", "Integrity / Ethical Issues (3)", "Moving to a Favorable Location (5)",
			"Better Compensation (1)", "Own Start-Up/ Business (1)", "Higher Education (1)", "Lack of Fairness (1)");

	// Structure
	public static final String EXTENDED_SUMMARY_TITLE_TEXT = "Summary";
	public static final String EXTENDED_AOP_TEXT = "AOP: 0";
	public static final String EXTENDED_POSITION_OCCUPIED = "Occupied Positions: 0 (0%)";
	public static final String EXTENDED_PYRAMID_TITLE_TEXT = "Pyramid";
	public static final String EXTENDED_GENDER_DIVERSITY_TITLE_TEXT = "Gender Diversity";
	public static final List<String> EXTENDED_GENDER_DIVERSITY_SUB_TITLE_TEXT = Arrays.asList("Your Span: 0%",
			"Function Average: 0%");

	// Hiring
	public static final String EXTENDED_HIRING_SUMMARY_TITLE_TEXT = "Summary";
	public static final String EXTENDED_EXTERNAL_HIRING_NUMBER = "Number of external hires (TTM): (352)";
	public static final String EXTENDED_HIRING_FUNNEL_TITLE_TEXT = "Summary of Hiring Funnel";
	public static final List<String> EXTENDED_SUMMARY_OF_HIRING_FUNNEL_LIST = Arrays.asList("Hiring not initiated : 0",
			"Job approval pending : 0", "Hiring In-Progress (Positions) : 0", "Offer Stage : 0", "Yet to join : 0");
	public static final String EXTENDED_HIRING_IN_PROGRESS_ROLE = "Hiring in Progress (0 Roles)";

	// ========================================================================================
	// Health
	public static final List<String> EXTENDED_HEALTH_CARD_TITLE = Arrays.asList("trends", "highlights", "gaps");
	// Trends
	public static final String EXTENDED_TRENDS_RESPONDENTS_23_24 = "2023-24 : Respondents - 0 (0%)";
	public static final String EXTENDED_TRENDS_RESPONDENTS_23_24_AVERAGE = "78%";
	public static final String EXTENDED_TRENDS_RESPONDENTS_22_23 = "2022-23 : Respondents - 0 (0%)";
	public static final String EXTENDED_TRENDS_RESPONDENTS_22_23_AVERAGE = "83%";
	public static final String EXTENDED_TRENDS_RESPONDENTS_21_22 = "2021-22 : Respondents - 668 (82%)";
	public static final String EXTENDED_TRENDS_RESPONDENTS_21_22_AVERAGE = "87%";
	// Highlights
	public static final String EXTENDED_TOP_SCORING_TITLE_TEXT = "Top 5 Scoring Areas";
	public static final List<String> EXTENDED_TOP_SCORING_AREAS = Arrays.asList(
			"Inputs / opinion taken into consideration for my areas of work",
			"Encouraged for participation in Formal / Informal meetings", "Equipped with tools & resources",
			"Ongoing connects with Managers (Formal / Informal)", "Clear understanding of Roles / Expectations");
	
}
