package com.orhonit.ole.warn.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="test_doc")
public class TestContent {
	@Id
	@GeneratedValue
	private Integer id;
	
	private String content;
}
