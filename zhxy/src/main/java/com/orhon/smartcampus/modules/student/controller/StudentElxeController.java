package com.orhon.smartcampus.modules.student.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.orhon.smartcampus.modules.base.service.IDictionaryService;
import com.orhon.smartcampus.modules.student.entity.SInformation;
import com.orhon.smartcampus.modules.student.service.SIInformationService;
import com.orhon.smartcampus.utils.ExcelUtil;


@RestController
@RequestMapping(value = "/export", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class StudentElxeController<E> {


	@Autowired
	private IDictionaryService dictionaryService;
	
	@Autowired
	private SIInformationService informationService;

	/**
	 * 导出模板
	 * @return
	 */
	@GetMapping(value = "/export")
	public void export(HttpServletResponse response,@RequestParam HashMap<String, Object> maps) throws Exception {
		String[] headers =  ((String) maps.get("headers")).split(",");
		String[] eclass =  ((String) maps.get("eclass")).split(",");
		try {
			ServletOutputStream out = response.getOutputStream();	
			HashMap<String,Object> dictionaryList = dictionaryService.getDictionaryList("student_options");
			ArrayList<String> arrayList = new ArrayList<String>();
			ArrayList<String> array = new ArrayList<String>();
			for (String string : headers) {
				if(string.equals("student_name")) {
					arrayList.add(JSON.parseObject(dictionaryList.get(string).toString()).get("zh").toString());
					arrayList.add(JSON.parseObject(dictionaryList.get(string).toString()).get("zh").toString()+"(蒙文)");
					array.add(string);
					array.add(string+"_mn");
				}else {
					array.add(string);
					arrayList.add(JSON.parseObject(dictionaryList.get(string).toString()).get("zh").toString());
				}
			}			
			ExcelUtil eeu = new ExcelUtil();
			HSSFWorkbook workbook = new HSSFWorkbook();
			List<List<String>> data = new ArrayList<List<String>>();
			String[] array1=arrayList.toArray(new String[arrayList.size()]);
			String[] array2=array.toArray(new String[array.size()]);
			int i = 0;
			for (String string : eclass) {
				eeu.exportExcel(workbook, i,string , array1,array2, data, out,string);
				i++;
			}
			//原理就是将所有的数据一起写入，然后再关闭输入流。
			setResponseHeader(response, "学生导入模板.xls");
			workbook.write(out);
			out.close();		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//发送响应流方法
	public void setResponseHeader(HttpServletResponse response, String fileName) {
		try {
			try {
				fileName = new String(fileName.getBytes(),"ISO8859-1");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setContentType("application/octet-stream;charset=ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}




	/**
	 * 导入
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException 
	 * @throws FileNotFoundException 
	 */
	@PostMapping(value = "/toLead")
	public String xlsx(HttpServletRequest request, Model model,@RequestParam(required = false) MultipartFile file) throws IOException, ParseException {
		if(file.isEmpty()!=true){
			InputStream stream = file.getInputStream();
			try {
				HSSFWorkbook hssfWorkbook = new HSSFWorkbook(stream);
				// 循环工作表Sheet         
				for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
					HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
					if (hssfSheet == null) {
						continue;
					}

					HSSFRow hssfRow = hssfSheet.getRow(1);
					ArrayList<String> list = new ArrayList<String>();
					int j=0;
					for (Cell cell : hssfRow) {
						String value = getValue(hssfRow.getCell(j));
						list.add(value);
						j++;
					}


					// 循环行Row
					for (int rowNum = 3; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
						HSSFRow hssfRow1 = hssfSheet.getRow(rowNum);
						if (hssfRow1 != null) {
							int i=0;
							SInformation information = new SInformation();
							for (String string : list) {
								if(string.equals("student_name")) {
									JSONObject object = new JSONObject();
									object.put("en", "");
									object.put("mn", getValue(hssfRow1.getCell(i+1)));
									object.put("zh", getValue(hssfRow1.getCell(i)));
									information.setStudent_name(object.toString());
								}
								
								if(string.equals("pinyin_name")) {
									String value = getValue(hssfRow1.getCell(i));
									information.setPinyin_name(value);
								}
								
								if(string.equals("student_learncode")) {
									String value = getValue(hssfRow1.getCell(i));
									information.setStudent_learncode(value);
								}
								
								if(string.equals("idcard")) {
									String value = getValue(hssfRow1.getCell(i));
									information.setIdcard(value);
								}
								
								i++;
							}
							informationService.save(information);
						}
						
					}

				}        


			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "2";
		}
		return "1";
	}

	@SuppressWarnings("static-access")     
	private String getValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			// 返回布尔类型的值
			return String.valueOf(hssfCell.getBooleanCellValue());
		}else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			// 返回数值类型的值                 
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			// 返回字符串类型的值
			return String.valueOf(hssfCell.getStringCellValue());
		}         
	}
}
