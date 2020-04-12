package com.orhonit.ole.sys.dto;

import java.util.Date;

import lombok.Data;

@Data
public class SysDictDTO {


	
		private String id;

		private String typeValue;

	    private String enumValue;
		
		private String typeDesc;

	    private String enumDesc;


	    private Date createdDate;


	    private Date lastUpdate;

	    private String lang;

	    private Integer sort;

}
