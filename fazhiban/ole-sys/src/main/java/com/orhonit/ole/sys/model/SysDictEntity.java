package com.orhonit.ole.sys.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ole_sys_dict")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDictEntity    implements Serializable{
	
	private static final long serialVersionUID = 6718758558189016630L;
	
	@Id
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
