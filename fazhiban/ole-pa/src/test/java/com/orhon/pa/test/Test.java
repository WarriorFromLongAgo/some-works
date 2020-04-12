package com.orhon.pa.test;

import com.orhon.pa.modules.opa.entity.OpaItem;

public class Test {

	public static void main(String[] args) {
		OpaItem oi = new OpaItem();
		oi.setName("1231");
		System.out.println(oi.getName());
		new Test().go(oi);
		System.out.println(oi.getName());
	}
	
	public void go(OpaItem oi){
		oi.setName("321");
	}
}
