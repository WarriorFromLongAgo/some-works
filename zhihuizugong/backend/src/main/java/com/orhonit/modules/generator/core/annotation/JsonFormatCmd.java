package com.orhonit.modules.generator.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonFormatCmd {
	
	String cmd() default "";
	
	String o() default "";
	
	String okey() default "";
	
	String d() default "";
	
	String dkey() default "";

}
