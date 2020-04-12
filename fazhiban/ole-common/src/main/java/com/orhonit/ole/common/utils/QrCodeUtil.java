package com.orhonit.ole.common.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码工具类工具类
 * @author 武跃忠
 *
 */
public class QrCodeUtil {

		
	/**
	 * 范例
	 * FileOutputStream fos;
		try {
			fos = new FileOutputStream("D:/qrcode.png");
			createQrcode("this test",fos);
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	 */
	
	public static void main(String[] args) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("D:/qrcode.png");
			createQrcode("中午",fos);
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static OutputStream createQrcode(String content,OutputStream ops){
		int width = 300;
        int height = 300;
        String format = "png";
        //定义二维码的参数
        Map<Object,Object> map = new HashMap<Object,Object>();
        //设置编码
        map.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //设置纠错等级
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        map.put(EncodeHintType.MARGIN, 2);

        try {
            //生成二维码
            BitMatrix bitMatrix = new MultiFormatWriter().encode(new String(content.getBytes("UTF-8"),"ISO-8859-1"), BarcodeFormat.QR_CODE, width, height);
            //MatrixToImageWriter.writeToPath(bitMatrix, format, file);
            MatrixToImageWriter.writeToStream(bitMatrix, format, ops);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ops;
	}
}
