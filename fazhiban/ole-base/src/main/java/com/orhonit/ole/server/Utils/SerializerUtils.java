package com.orhonit.ole.server.Utils;


public class SerializerUtils {

	 private String maxCount = "99999999";
	 private String nowCount = "";
	 private int index = 0;
	 /**
	  * 产生序列号编号
	  * @prefix 序列前缀
	  * @type 样式 0:前缀00000001, 1:前缀_000000001
	  */
	 public synchronized String getMoveOrderNo(String prefix,Integer type) {
//		 long No = 0;
//		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		 String nowdate = sdf.format(new Date());
//		 No = Long.parseLong(nowdate.substring(2,4));
//		 if (!(String.valueOf(No)).equals(dateValue)) {
//			 maxCount = "000000";
//			 dateValue = String.valueOf(No);
//		 }
		 String num = "";
		 num += getNo(nowCount);
		 if(type == 1) {
			 num = prefix + "_" + num;
		 }else if(type == 0) {
			 num = prefix + num;
		 }
		 
		 return num;
	 }

	 /**
	  * 获取最后的序列号
	  * @lastSerializer 最后的序列编号
	  * @prefix 前缀
	  * @index 具体有多少位
	  */                               
	 public synchronized String getMoveOrderNo(String lastSerializer,String prefix,Integer index,Integer type) {
		 nowCount = lastSerializer;
		 this.index = index;
		 if (Integer.valueOf(nowCount) > Integer.valueOf(maxCount)) {
			 //dateValue = nyr;
			 this.index++;
			 maxCount = String.valueOf(nowCount);
		 }
		 //return getMoveOrderNo();
		 return getMoveOrderNo(prefix,type);
	 }
	 
	 
	 /**
	  * 返回添加的数据数+1
	  */
	 public String getNo(String s) {
		 String rs = s;
		 int i = Integer.parseInt(rs);
		 i += 1;
		 rs = "" + i;
		 for (int j = rs.length(); j < index; j++) {
			 rs = "0" + rs;
		 }
		 maxCount = rs;
		 return rs ;
	 }
}
