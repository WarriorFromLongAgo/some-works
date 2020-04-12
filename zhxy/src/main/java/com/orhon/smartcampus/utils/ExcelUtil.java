package com.orhon.smartcampus.utils;


import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

	public class ExcelUtil {  
		  
	    /**     
	     * @Description: 导出Excel 
	     * @param workbook  
	     * @param sheetNum (sheet的位置，0表示第一个表格中的第一个sheet) 
	     * @param sheetTitle  （sheet的名称） 
	     * @param headers    （表格的列标题） 
	     * @param result   （表格的数据） 
	     * @param out  （输出流） 
	     * @throws Exception 
	     */  
	    public static void exportExcel(HSSFWorkbook workbook, int sheetNum,  
	            String sheetTitle, String[] headers1, String[] headers2, List<List<String>> result,  
	            OutputStream out,String name) throws Exception {  
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.createSheet();  
	        workbook.setSheetName(sheetNum, sheetTitle);  
	        // 设置表格默认列宽度为20个字节 
	        CellRangeAddress region1 = new CellRangeAddress(0, 0, (short) 0, (short) headers1.length-1);
	        sheet.addMergedRegion(region1);
	        sheet.setDefaultColumnWidth((short) 30);
	        // 生成一个样式  
	        HSSFCellStyle style = workbook.createCellStyle();  
	        // 设置这些样式  
	        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);  
	        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
	        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
	        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
	        style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
	        style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
	        // 生成一个字体  
	        HSSFFont font = workbook.createFont();  
	        font.setColor(HSSFColor.BLACK.index);  
	        font.setFontHeightInPoints((short) 14);  
	        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
	        // 把字体应用到当前的样式  
	        style.setFont(font);  
	  
	        // 指定当单元格内容显示不下时自动换行  
	        style.setWrapText(true);  
	        
	        // 产生表格标题行  
	        HSSFRow row1 = sheet.createRow(0);
	        row1.setHeight((short)500);
	        HSSFCell cell1 = row1.createCell((short) 0);
	        cell1.setCellStyle(style); 
	        cell1.setCellValue(name);  
	        
	        // 产生表格标题行  
	        HSSFRow row = sheet.createRow(1);
	        row.setHeight((short)500);
	        for (int i = 0; i < headers1.length; i++) {  
	            HSSFCell cell = row.createCell((short) i); 
	            cell.setCellStyle(style);  
	            HSSFRichTextString text = new HSSFRichTextString(headers2[i]);  
	            cell.setCellValue(text.toString());  
	        }  
	  
	        // 产生表格标题行  
	        HSSFRow row2 = sheet.createRow(2);
	        row2.setHeight((short)500);
	        for (int i = 0; i < headers1.length; i++) {  
	            HSSFCell cell2 = row2.createCell((short) i); 
	            cell2.setCellStyle(style);  
	            HSSFRichTextString text = new HSSFRichTextString(headers1[i]);  
	            cell2.setCellValue(text.toString());  
	        }  
	        // 遍历集合数据，产生数据行  
	        if (result != null) {  
	        	int index = 2;  
				for (List<String> m : result) {
					row = sheet.createRow(index);
					int cellIndex = 0;
					for (String str : m) {
						HSSFCell cell = row.createCell((short) cellIndex);
						cell.setCellValue(str.toString());
						cellIndex++;
					}
					index++;
				} 
	        }  
	    }  
	}  