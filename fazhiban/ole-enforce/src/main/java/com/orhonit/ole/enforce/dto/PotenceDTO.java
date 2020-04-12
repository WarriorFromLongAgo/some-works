package com.orhonit.ole.enforce.dto;

import lombok.Data;

/**
 * 权责信息
 * 包括违则和罚则
 * @author 武跃忠
 *
 */
@Data
public class PotenceDTO {
	
	private String wzContent;
	
	private String wzItem;
	
	private String wzCatalogName;
	
	private String wzItemContent;
	
	private String fzContent;
	
	private String fzCatalogName;
	
	private String fzItem;
	
	private String fzItemContent;
	
	public String getWzItemContent() {
		String[] item = wzItem.split("-");
		String[] itemStr = {"篇","章","节","条","款","项","目"};
		String itemOut = "";
		for(int i=0;i<item.length;i++) {
			if(!item[i].equals("0")) {
				itemOut += "第"+item[i]+itemStr[i];
			}
		}
		itemOut += ":";
		return wzCatalogName + itemOut + wzContent;
	}
	
	public String getFzItemContent() {
		String[] item = fzItem.split("-");
		String[] itemStr = {"篇","章","节","条","款","项","目"};
		String itemOut = "";
		for(int i=0;i<item.length;i++) {
			if(!item[i].equals("0")) {
				itemOut += "第"+item[i]+itemStr[i];
			}
		}
		itemOut += ":";
		return fzCatalogName + itemOut + fzContent;
	}
	
}
