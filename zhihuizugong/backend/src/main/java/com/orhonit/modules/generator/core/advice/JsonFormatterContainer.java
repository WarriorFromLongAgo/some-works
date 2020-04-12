package com.orhonit.modules.generator.core.advice;

import com.alibaba.fastjson.support.spring.PropertyPreFilters;

public class JsonFormatterContainer {
	 private Object value;
	    private PropertyPreFilters filters;

	    JsonFormatterContainer(Object body){
	        this.value = body;
	    }

	    public Object getValue() {
	        return this.value;
	    }

	    public void setValue(Object value) {
	        this.value = value;
	    }

	    public PropertyPreFilters getFilters() {
	        return this.filters;
	    }

	    public void setFilters(PropertyPreFilters filters) {
	        this.filters = filters;
	    }

}
