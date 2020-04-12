package com.orhonit.ole.common.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import it.sauronsoftware.jave.AudioInfo;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;
import it.sauronsoftware.jave.MultimediaInfo;
import it.sauronsoftware.jave.VideoAttributes;
import it.sauronsoftware.jave.VideoInfo;
import it.sauronsoftware.jave.VideoSize;

public class JaveUtil {
//
//    public static void main(String[] args) throws Exception{
//    	System.out.println(getMp4Info("D:/1.mp4"));
//    }
    
	/**
	 * 获取mp4的信息
	 * @param path
	 * @return
	 */
    public static Map<String,Object> getMp4Info(String path){
    	File file = new File(path);
    	Encoder encoder = new Encoder();            //创建解码器
    	MultimediaInfo info = null;
    	Map<String,Object> res = new HashMap<String,Object>();
		try {
			info = encoder.getInfo(file);
			//获取媒体信息
	    	VideoInfo vi = info.getVideo();             //视频信息
	    	res.put("decoder", vi.getDecoder());        //解码器
	    	res.put("bitRate",vi.getBitRate());        	//比特率
	    	res.put("frameRate",vi.getFrameRate());     //帧率
	    	VideoSize size = vi.getSize();
	    	res.put("width",size.getWidth());        	//视频宽度
	    	res.put("height",size.getHeight());       	//视频高度
	    	String time = "";
	    	if(info.getDuration()/3600000 != 0){
	    		time += info.getDuration()/3600000 + "小时";
	    	}
    		time += (info.getDuration() % 3600000) / 60000 + "分";
    		time += (info.getDuration() % 60000)/1000 + "秒";
	    	res.put("time",time); 	//视频时长
	    	res.put("length",(int)(file.length()/1024f/1024f*100)/100f + "Mb"); 			//视频大小
		} catch (InputFormatException e) {
			e.printStackTrace();
		} catch (EncoderException e) {
			e.printStackTrace();
		}
    	return res;
    }
    
    /**
	 * 获取mp3的信息
	 * @param path
	 * @return
	 */
    public static Map<String,Object> getMp3Info(String path){
    	File file = new File(path);
    	Encoder encoder = new Encoder();            //创建解码器
    	MultimediaInfo info = null;
    	Map<String,Object> res = new HashMap<String,Object>();
		try {
			info = encoder.getInfo(file);
			//获取媒体信息
	    	AudioInfo vi = info.getAudio();             //视频信息
	    	res.put("decoder", vi.getDecoder());        //解码器
	    	res.put("bitRate",vi.getBitRate());        	//比特率
	    	String time = "";
	    	if(info.getDuration()/3600000 != 0){
	    		time += info.getDuration()/3600000 + "小时";
	    	}
    		time += (info.getDuration() % 3600000) / 60000 + "分";
    		time += (info.getDuration() % 60000)/1000 + "秒";
	    	res.put("time",time); 	//视频时长
	    	res.put("length",(int)(file.length()/1024f/1024f*100)/100f + "Mb"); 			//视频大小
		} catch (InputFormatException e) {
			e.printStackTrace();
		} catch (EncoderException e) {
			e.printStackTrace();
		}
    	return res;
    }
    
    /**
     * 保存视频的缩略图
     * 保存格式（path.png）
     * d:/123.mp4.png
     * @param path 文件全路径
     */
	public static void saveSmallImg(String path){
		try {
			File source = new File(path);
			File target = new File(path+".png");//转图片  
			Encoder encoder = new Encoder();
			MultimediaInfo m = encoder.getInfo(source);
			VideoAttributes video = new VideoAttributes();
			video.setCodec("png");//转图片  
			video.setSize(new VideoSize(274, 210));
			EncodingAttributes attrs = new EncodingAttributes();
			attrs.setFormat("image2");//转图片  
			attrs.setOffset(m.getDuration()/2000f);//设置偏移位置，即开始转码位置（3秒）  
			attrs.setDuration(0.01f);//设置转码持续时间（1秒）  
			attrs.setVideoAttributes(video);
			encoder.encode(source, target, attrs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
