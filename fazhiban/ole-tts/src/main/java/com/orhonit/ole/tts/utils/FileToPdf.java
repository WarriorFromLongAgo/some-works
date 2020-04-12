package com.orhonit.ole.tts.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.util.ResourceUtils;

import com.aspose.cells.Workbook;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.aspose.words.SaveOptions;

/**
 * 支持txt/word/excel转为PDF
 * @author wuyz
 */
public class FileToPdf {
	/**
	 * 获取words的License
	 * @return
	 */
	private static boolean getWordsLicense() {
		boolean result = false;
		try {
			InputStream is = null ;
			try {
				File f =  ResourceUtils.getFile("classpath:license.xml");
			
				is = new FileInputStream(f);
			} catch( Exception e ) {
				e.printStackTrace();
			}
			if ( is == null ) {
				try {
					is = FileToPdf.class.getClassLoader().getResourceAsStream("/license.xml");
					
				}catch(Exception e ) {
					e.printStackTrace();
				}
			}
			
			License aposeLic = new License();
			aposeLic.setLicense(is);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取cells的License
	 * @return
	 */
	private static boolean getCellsLicense() {
		boolean result = false;
		try {
			InputStream is = null ;
			try {
				File f =  ResourceUtils.getFile("classpath:license.xml");
				is = new FileInputStream(f);
			}catch(Exception e ) {
				e.printStackTrace();
			}
			
			if ( is == null ) {
				try {
					is = FileToPdf.class.getClassLoader().getResourceAsStream("/license.xml");
				}catch(Exception e ) {
					e.printStackTrace();
				}
			}
			com.aspose.cells.License aposeLic = new com.aspose.cells.License();
			aposeLic.setLicense(is);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * excel文件转为PDF
	 * @param excelPath excel文件全路径
	 * @param targePdfPath pdf文件保存全路径
	 */
	public static void XlsxToPdf(String excelPath, String targePdfPath) {
		// 验证License
		if (!getCellsLicense()) {
			return;
		}

		try {
			long old = System.currentTimeMillis();
			Workbook wb = new Workbook(excelPath);// 原始excel路径
			File pdfFile = new File(targePdfPath);// 输出路径
			FileOutputStream fileOS = new FileOutputStream(pdfFile);

			wb.save(fileOS, com.aspose.cells.SaveFormat.PDF);
			fileOS.close();
			long now = System.currentTimeMillis();
			System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param wordPath word文件全路径
	 * @param targePdfPath pdf文件保存全路径
	 */
	public static void DocToPdf(String wordPath, String targePdfPath) {
		// 验证License
		if (!getWordsLicense()) {
			return;
		}
		try {
			long old = System.currentTimeMillis();
			File file = new File(targePdfPath);
			FileOutputStream os = new FileOutputStream(file);
			Document doc = new Document(wordPath);
			doc.save(os, SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML,
											// OpenDocument, PDF, EPUB, XPS, SWF
											// 相互转换
			os.close();
			long now = System.currentTimeMillis();
			System.out.println("共耗时" + ((now - old) / 1000.0) + "秒");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param txtPath txt文件全路径
	 * @param targePdfPath pdf文件保存全路径
	 */
	public static void TxtToPdf(String txtPath, String targePdfPath) {
		// 验证License
		if (!getWordsLicense()) {
			return;
		}

		try {
			long old = System.currentTimeMillis();
			Document doc = new Document();
			DocumentBuilder builder = new DocumentBuilder(doc);
			InputStreamReader isr = new InputStreamReader(new FileInputStream(txtPath), "GBK"); // 或GB2312,GB18030
			BufferedReader read = new BufferedReader(isr);
			String line = null;
			while ((line = read.readLine()) != null) {
				System.out.println(line);
				builder.write(line);
			}
			File file = new File(targePdfPath);
			FileOutputStream os = new FileOutputStream(file);
			doc.save(os, SaveFormat.PDF);
			os.close();
			read.close();
			isr.close();
			long now = System.currentTimeMillis();
			System.out.println("共耗时" + ((now - old) / 1000.0) + "秒");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param htmlContent
	 * @param targePdfPath
	 */
	public static void htmlToDoc(String htmlContent, String targePdfPath) {
		// 验证License
		if (!getWordsLicense()) {
			return;
		}
		try {
			long old = System.currentTimeMillis();
			File file = new File(targePdfPath);
			FileOutputStream os = new FileOutputStream(file);
			
//			Document doc = new Document(wordPath);
			Document doc = new Document();
			
			DocumentBuilder documentBuilder = new DocumentBuilder(doc);
			documentBuilder.insertHtml(htmlContent);
			
			doc.save(os, SaveFormat.DOC);// 全面支持DOC, DOCX, OOXML, RTF HTML,
											// OpenDocument, PDF, EPUB, XPS, SWF
											// 相互转换
			
			doc.save(os, SaveOptions.createSaveOptions(SaveFormat.DOC));
			
			os.close();
			long now = System.currentTimeMillis();
			System.out.println("共耗时" + ((now - old) / 1000.0) + "秒");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}