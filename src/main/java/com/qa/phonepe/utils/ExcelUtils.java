package com.qa.phonepe.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtils {

	private static final String EXCEL_SHEET_PATH="";
	private static Workbook workbook;
	private static Sheet sheet;
	
	public static Object[][] getExcelSheet(String sheetName) {
		
		Object [][] data=null;
		
		try {
			FileInputStream fis= new FileInputStream(EXCEL_SHEET_PATH);
		     workbook = WorkbookFactory.create(fis);
		     sheet = workbook.getSheet(sheetName);
		     data= new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		     
		     for(int i=0;i<=sheet.getLastRowNum();i++) {
		    	 for(int j=0;j<=sheet.getRow(0).getLastCellNum();j++) {
		    		 data[i][j]=sheet.getRow(i+1).getCell(j).toString();
		    	 }
		     }
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
}
