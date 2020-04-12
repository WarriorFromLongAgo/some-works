package com.orhonit.ole.sys.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ole_ef_seq")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SequenceEntity {

	@Id
	private String seqName;
	
	private String seqValue;
	
	private Date seqDate;
}
